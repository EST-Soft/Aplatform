<template>
    <div class="modal-wrap" v-if="isLogin === 'member'" v-show="modalCheck">
        <div class="modal-container" v-if="!profileModalCheck && !profileDetailModalCheck">
            <h3>프로젝트 지원서</h3>
            <h4>이름 : {{ state.member.mbrName }}</h4>
            <h4>주민번호 앞자리 : {{ state.member.mbrBd }}</h4>
            <h4 class="align-items-center">전화번호 :
                <span v-if="!isPhoneNumberEditing">{{ state.member.mbrMp }}</span>
                <input size=13 maxlength=13 class="phoneNum" v-if="isPhoneNumberEditing" type="text"
                    v-model="state.member.mbrMp" />
                <button class="btn btn-outline-dark modify" @click="toggleEditPhoneNumber">{{ isPhoneNumberEditing ?
                    '취소' : '수정' }}</button>
                <button class="btn btn-outline-dark modify" v-if="isPhoneNumberEditing"
                    @click="saveUpdatedPhoneNumber">저장</button>
            </h4>
            <h4>프로필 : <span> {{ representativeResume.length > 0 ? representativeResume[0].rsmTtl : '대표 프로필 없음'
            }}</span><a @click="profileModalOpen"> (재선택)</a></h4>
            <div class="modal-btn d-grid gap-2 d-md-flex justify-content-md-end">
                <button @click="applyModalOpen" class="btn btn-outline-dark">닫기</button>
                <button @click="applyModalOpen" class="btn btn-outline-dark">확인</button>
            </div>
        </div>
        <div class="modal-container" v-if="profileModalCheck && !profileDetailModalCheck">
            <h3>프로필 선택</h3>
            <h4 v-for="(resume, index) in state.resumes" :key="resume.rsmSq"><a @click="selectProfile(index)">{{ index +
                1 }}. {{ resume.rsmTtl }} </a><span v-if="resume.rsmRprsntvYn === 'y'"
                    class="badge text-bg-primary title"> 대표 프로필 </span></h4>
            <div class="modal-btn d-grid gap-2 d-md-flex justify-content-md-end">
                <button @click="profileModalOpen" class="btn btn-outline-dark">뒤로가기</button>
            </div>
        </div>
        <div class="modal-container" v-if="profileModalCheck && profileDetailModalCheck">
            <h3>프로필 상세보기</h3>
            <h4>프로필 제목</h4>
            <h4>이름 : {{ selectProfileDetail?.rsmName }}</h4>
            <h4>생년월일 : {{ selectProfileDetail?.rsmBd }}</h4>
            <h4>연락처 : {{ selectProfileDetail?.rsmMp }}</h4>
            <h4>이메일 : {{ selectProfileDetail?.rsmEml }}</h4>
            <h4>최종 학력 : {{ formatEducation(selectProfileDetail?.rsmFnlEdctnCode) }}</h4>
            <h4>희망 연봉 : {{ formatNumberWithCommas(selectProfileDetail?.rsmEs) }} 원</h4>
            <div class="modal-btn d-grid gap-2 d-md-flex justify-content-md-end">
                <button class="btn btn-outline-dark" @click="changeRepresentativeResume(selectProfileDetail)">대표프로필
                    설정</button>
                <button @click="profileDetailModalOpen" class="btn btn-outline-dark">뒤로가기</button>
            </div>
        </div>
    </div>
    <div class="modal-wrap" v-if="isLogin === 'enter'" v-show="modalCheck">
        <div class="modal-container" v-if="!profileModalCheck && !profileDetailModalCheck">
            <h3>프로젝트 지원서</h3>
            <div class="form-check h5">
                <input class="form-check-input" type="checkbox" value="" id="flexCheck1"
                    v-model="checkedItems.flexCheck1" @change="handleCheck('flexCheck1')">
                <label class="form-check-label" for="flexCheck1">
                    이름 : ~~~ ,등급 : ~~, 기술 : ~~~
                </label>
            </div>
            <div class="form-check h5">
                <input class="form-check-input" type="checkbox" value="" id="flexCheck2"
                    v-model="checkedItems.flexCheck2" @change="handleCheck('flexCheck2')">
                <label class="form-check-label" for="flexCheck2">
                    이름 : ~~~ ,등급 : ~~, 기술 : ~~~
                </label>
            </div>
            <div class="form-check h5">
                <input class="form-check-input" type="checkbox" value="" id="flexCheck3"
                    v-model="checkedItems.flexCheck3" @change="handleCheck('flexCheck3')">
                <label class="form-check-label" for="flexCheck3">
                    이름 : ~~~ ,등급 : ~~, 기술 : ~~~
                </label>
            </div>
            <div class="form-check h5">
                <input class="form-check-input" type="checkbox" value="" id="flexCheck4"
                    v-model="checkedItems.flexCheck4" @change="handleCheck('flexCheck4')">
                <label class="form-check-label" for="flexCheck4">
                    이름 : ~~~ ,등급 : ~~, 기술 : ~~~
                </label>
            </div>
            <div class="modal-btn d-grid gap-2 d-md-flex justify-content-md-end">
                <button @click="applyModalOpen" class="btn btn-outline-dark">닫기</button>
                <button @click="applyModalOpen" class="btn btn-outline-dark">확인</button>
            </div>
        </div>
        <div class="modal-container" v-if="profileModalCheck && !profileDetailModalCheck">
            <h3>프로필 선택</h3>
            <h4><a @click="profileDetailModalOpen">프로필 1 </a><span> 대표 프로필 </span></h4>
            <h4><a @click="profileDetailModalOpen">프로필 2 </a></h4>
            <h4><a @click="profileDetailModalOpen">프로필 3 </a></h4>
            <div class="modal-btn d-grid gap-2 d-md-flex justify-content-md-end">
                <button @click="profileModalOpen" class="btn btn-outline-dark">뒤로가기</button>
            </div>
        </div>
        <div class="modal-container" v-if="profileModalCheck && profileDetailModalCheck">
            <h3>프로필 상세보기</h3>
            <h4>안녕하세요 개발자 백바울입니다.</h4>
            <div class="modal-btn d-grid gap-2 d-md-flex justify-content-md-end">
                <button class="btn btn-outline-dark">대표프로필 설정</button>
                <button @click="profileDetailModalOpen" class="btn btn-outline-dark">뒤로가기</button>
            </div>
        </div>
    </div>
    <section class="page-header page-header-modern bg-color-grey page-header-md">
        <div class="container">
            <div class="row">
                <div class="col-md-12 align-self-center p-static order-2 text-center">
                    <h1 class="text-dark font-weight-bold text-8">프로젝트 상세보기</h1>
                </div>
                <div class="col-md-12 align-self-center order-1">
                    <ul class="breadcrumb d-block text-center">
                        <li><a href="/">Home</a></li>
                        <li class="active">프로젝트 상세보기</li>
                    </ul>
                </div>
            </div>
        </div>
    </section>
    <section class="card card-admin">
        <div class="container-md themed-container">
            <div class="row mb-3">
                <div class="col-11 themed-grid-col">
                    <h1>{{ state.project.prjctTtl }} / {{ state.project.entrprsName }}</h1>
                </div>
                <div class="col-1 themed-grid-col d-flex align-items-center justify-content-center">
                    <i :class="isScrapped ? 'bi bi-heart-fill' : 'bi bi-heart'" @click="toggleHeart"
                        class="heart-icon"></i>
                </div>

            </div>

        </div>

        <div class="container-md themed-container">
            <div class="row mb-3">
                <div class="col-4 themed-grid-col">
                    <h2>경력 : {{ state.project.prjctEsntlCrr }}</h2>
                </div>
                <div class="col-4 themed-grid-col">
                    <h2>학력 : </h2>
                </div>
                <div class="col-4 themed-grid-col">
                    <h2>위치 : {{ state.project.prjctLctn }}</h2>
                </div>
                <div class="col-4 themed-grid-col">
                    <h2>근무형태 : {{ state.project.prjctTpy }}</h2>
                </div>
                <div class="col-4 themed-grid-col">
                    <h2>기간 : {{ state.project.prjctPrd }}</h2>
                </div>
                <div class="col-4 themed-grid-col">
                    <h2>모집 직군 : {{ state.project.prjctWork }}</h2>
                </div>
            </div>
        </div>

        <div class="container-md themed-container">
            <div class="row mb-3">
                <div class="col-12 themed-grid-col">
                    <h2>사용기술 : {{ state.project.prjctUseSkl }}</h2>
                </div>
                <div class="col-12 themed-grid-col">
                    <h2>자격요건 : {{ state.project.prjctEsntlSkl }}</h2>
                </div>
                <div class="col-12 themed-grid-col">
                    <h2>인터뷰 기간 : {{ getDate(state.project.prjctStrt) }} ~ {{ getDate(state.project.prjctEnd) }}</h2>
                </div>
            </div>
        </div>

        <div id="btn" class="container-md themed-container d-flex justify-content-end">
            <button type="button" class="btn btn-outline-dark" @click="applyModalOpen" v-show="isLogin != null">프로젝트
                지원</button>
        </div>
    </section>
</template>



<script setup>
import { onMounted, reactive, ref, watch, computed } from 'vue';
import { useRoute } from 'vue-router';
import { api } from '../../../axios';
import moment from 'moment';
import { useStore } from 'vuex';

const route = useRoute();
const prjctSq = ref(route.params.prjctSq);

const state = reactive({
    project: {},
    member: {},
    resumes: [],
});

// 로그인 유저 정보 불러오기
const fetchMember = async () => {
    if (store.getters.getUserType === 'user') {
        const mbrId = store.getters.getMember?.mbrId;
        try {
            const response = await api.$get(`/member/detail/${mbrId}`)
            state.member = response;
            response.mbrMp = formattedPhoneNumber(response.mbrMp);

            await fetchResumes();

        } catch (error) {
            console.error('에러 메시지 : ', error);
        }
    }
}


// 프로젝트 정보 불러오기
const fetchProject = async () => {
    try {
        const response = await api.$get(`/project/${prjctSq.value}`);
        state.project = response;
        // console.log(response);
    } catch (error) {
        console.error('에러 메시지 : ', error);
    }
}


// 이력서 정보 불러오기
const fetchResumes = async () => {
    try {
        const response = await api.$get(`/resumes/${state.member.mbrSq}`)
        state.resumes = response;
        // console.log(response);
    } catch (error) {
        console.log("에러메시지 : " + error);
    }
}

// 대표 이력서 필터링
const representativeResume = computed(() => {
    return state.resumes.filter(resume => resume.rsmRprsntvYn === 'y');
});

// 프로필 상세보기
const selectedResumeIndex = ref(null);

function selectProfile(index) {
    selectedResumeIndex.value = index;
    profileDetailModalOpen();
}

const selectProfileDetail = computed(() => {
    if (selectedResumeIndex.value !== null) {
        return state.resumes[selectedResumeIndex.value];
    }

    return null;
})

// 학력 포매팅 함수
function formatEducation(edu) {
    if (edu === 'did') {
        return '박사';
    } if (edu === 'mid') {
        return '석사';
    } if (edu === 'unvrsty') {
        return '대학교(4년제)';
    } if (edu === 'jc') {
        return '대학교(2,3년제)';
    } if (edu === 'hs') {
        return '고등학교';
    } if (edu === 'ni') {
        return '미입력';
    } if (edu === 'ednm') {
        return '학력무관';
    }
}

function formatNumberWithCommas(number) {
    return number.toLocaleString('en-US');
}


onMounted(() => {
    fetchProject();
    fetchMember();
})

// 대표 프로필 변경
async function changeRepresentativeResume(selectProfileDetail) {
    try {
        const mbrSq = state.member.mbrSq;
        const rsmSq = selectProfileDetail.rsmSq;
        console.log("mbrSq :", mbrSq);
        console.log("rsmSq :", rsmSq);
        const result = await api.$patch(`/resumes/${mbrSq}/${rsmSq}`);
        await fetchResumes();
        if (result) {
            console.log("업데이트 완료");
        }
    } catch (error) {
        console.error('에러메시지 : ' + error);
    }
    profileDetailModalOpen();
}

// 날짜 형식

function getDate(date) {
    const formatDate = moment(date).format("YYYY-MM-DD");
    return formatDate;
}

// 모달창 관련
const modalCheck = ref(false);

function applyModalOpen() {
    modalCheck.value = !modalCheck.value;
    isPhoneNumberEditing.value = false;
}

const profileModalCheck = ref(false);

function profileModalOpen() {
    profileModalCheck.value = !profileModalCheck.value;
}
const profileDetailModalCheck = ref(false);

function profileDetailModalOpen() {

    profileDetailModalCheck.value = !profileDetailModalCheck.value;
}

const checkedItems = reactive({
    flexCheck1: false,
    flexCheck2: false,
    flexCheck3: false,
    flexCheck4: false,
});

function handleCheck(id) {
    if (checkedItems[id]) {  // 체크된 경우(true)만 실행
        console.log(`${id}가 체크되었습니다!`);
        profileModalCheck.value = !profileModalCheck.value;
    }
}


// 모달 창 오픈 시 스크롤 제한
watch(modalCheck, () => {
    document.documentElement.style.overflow = modalCheck.value ? 'hidden' : 'auto';
});

// 로그인 상태 확인
const store = useStore();

const isLogin = computed(() => {
    if (store.getters.getMember?.entrprsId != null) {
        // console.log("entrprsId : " + store.getters.getMember?.entrprsId);
        // console.log("유저타입 " + store.getters.getUserType);
        return "enter";
    }
    if (store.getters.getMember?.mbrId != null) {
        // console.log("mbrId : " + store.getters.getMember?.mbrId);
        // console.log("유저타입 " + store.getters.getUserType);
        return "member";
    }
    return null;
});


// 휴대폰 번호 포매팅 함수
function formattedPhoneNumber(phoneNumber) {
    // 숫자만 남기기
    const cleaned = phoneNumber.replace(/\D/g, '');

    // 10자리 또는 11자리 번호에 대해 포맷팅
    if (cleaned.length <= 3) {
        return cleaned;
    } else if (cleaned.length <= 6) {
        return `${cleaned.slice(0, 3)}-${cleaned.slice(3, 6)}`;
    } else if (cleaned.length <= 10) {
        return `${cleaned.slice(0, 3)}-${cleaned.slice(3, 6)}-${cleaned.slice(6, 10)}`;
    } else {
        return `${cleaned.slice(0, 3)}-${cleaned.slice(3, 7)}-${cleaned.slice(7, 11)}`;
    }
}

// 휴대폰 번호 포매팅 초기화
function orginalPhoneNumber(phoneNumber) {
    // 숫자만 남기기
    const cleaned = phoneNumber.replace(/\D/g, '');

    return cleaned;
}

// 휴대폰 번호 수정
const isPhoneNumberEditing = ref(false);

function toggleEditPhoneNumber() {

    isPhoneNumberEditing.value = !isPhoneNumberEditing.value;

    if (isPhoneNumberEditing.value) {
        state.member.mbrMp = orginalPhoneNumber(state.member.mbrMp);
    }
    else {
        state.member.mbrMp = formattedPhoneNumber(state.member.mbrMp);
    }
}

async function saveUpdatedPhoneNumber() {

    try {
        const result = await api.$patch("/member/mbrMpReset", state.member);
        if (result) {
            console.log(state.member);
        }
    } catch (error) {
        console.error("에러메시지 : " + error);
    }

    isPhoneNumberEditing.value = false;
}


// 스크랩 관련 
const isScrapped = ref(false);

// const fetchScrap = async () => {
//     try {
//         const response = await api
//     } catch (error) {
//         console.error("에러메시지", error);
//     }
// }

function toggleHeart() {
    isScrapped.value = !isScrapped.value;
}


</script>

<style scoped>
.container {
    position: relative;
    margin-bottom: 50px;
}

.container-md {
    background-color: lightgray;
    margin-bottom: 20px;
}

h1,
h2 {
    margin-top: 15px;
}



.heart-icon {
    margin-top: 15px;
    font-size: xx-large;
    cursor: pointer;
    /* 클릭 가능하도록 설정 */
}


.bi-heart-fill {
    /* 크기 조정 가능 */
    color: red;
    /* 하트 색상 조정 가능 */
}

#btn {
    background-color: white;
}


/* 모달 관련 */
.modal-wrap {
    position: fixed;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.4);
    z-index: 999;
}

/* modal or popup */
.modal-container {
    position: relative;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    height: 500px;
    width: 550px;
    background: #fff;
    border-radius: 10px;
    padding: 20px;
    box-sizing: border-box;
}

.modal-btn {
    position: absolute;
    bottom: 20px;
    /* 하단에서 20px 간격 */
    right: 20px;
    /* 우측에서 20px 간격 */
    display: flex;
    gap: 10px;
    /* 버튼 간 간격 */
}

.apply-modal-container h3 {
    text-align: center;
    /* 텍스트 중앙 정렬 */
}

a {
    cursor: pointer;
}

a:hover {
    text-decoration: underline !important;
}

.phoneNum {
    border-width: 0 0 1px;
}

.phoneNum:focus {
    outline: none;
    margin-left: 5px;
    /* 입력 필드와 버튼 사이 간격 조정 */
}

.modify {
    height: 100%;
    /* 다른 요소와 높이를 맞추기 */
    padding: 5px 10px;
    /* 버튼의 내부 여백 조정 */
    margin-left: 5px;
    vertical-align: middle;
    /* 버튼이 텍스트와 정렬되도록 설정 */
}

.title {
    vertical-align: middle;
    height: auto;
    margin-left: 5px;
}

h4 {
    margin-top: 8px;
}
</style>