<template>
            <div class="modal-wrap" v-show="modalCheck">
        <div class="modal-container" v-if="!profileModalCheck && !profileDetailModalCheck">
            <div class="d-flex justify-content-end">
                <button type="button" class="btn-close" aria-label="Close" @click="applyModalOpen"></button>
            </div>
            <h3>프로젝트 지원서</h3>
            <br>
            <h4>이름 : {{ state.member.mbrName }}</h4>
            <h4>주민등록번호 : {{ state.member.mbrBd }} - {{ state.member.gndrTypCd === 'm' || state.member.gndrTypCd === 'M' ? "1" : "2"}}XXXXXX</h4>
            <h4 class="align-items-center">전화번호 :
                <span v-if="!isPhoneNumberEditing">{{ state.member.mbrMp }}</span>
                <input size=13 maxlength=13 class="phoneNum" v-if="isPhoneNumberEditing" type="text"
                    v-model="state.member.mbrMp" />
                <button class="btn btn-outline-dark modify" @click="toggleEditPhoneNumber">{{ isPhoneNumberEditing ?
                    '취소' : '수정' }}</button>
                <button class="btn btn-outline-dark modify" v-if="isPhoneNumberEditing"
                    @click="saveUpdatedPhoneNumber">저장</button>
            </h4>
            <h4>프로필 : <span v-if="state.changeResume?.rsmTtl">
                    {{ state.changeResume.rsmTtl }}
                </span>
                <span v-else>
                    {{ representativeResume.length > 0 ? representativeResume[0].rsmTtl : '대표 프로필 없음' }}
                </span> (<a @click="profileModalOpen">재선택</a>)
            </h4>
            <div class="modal-btn d-grid gap-2 d-md-flex justify-content-md-end">
                <button @click="(representativeResume?.length ?? 0) > 0 || (state.changeResume && Object.keys(state.changeResume).length > 0) ? projectApply() : showSetResume()
                    " class="btn btn-outline-dark">
                    지원하기
                </button>

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
            <h4>{{ selectProfileDetail?.rsmTtl }}</h4>
            <h4>이름 : {{ selectProfileDetail?.rsmName }}</h4>
            <h4>생년월일 : {{ selectProfileDetail?.rsmBd }}</h4>
            <h4>연락처 : {{ selectProfileDetail?.rsmMp }}</h4>
            <h4>이메일 : {{ selectProfileDetail?.rsmEml }}</h4>
            <h4>최종 학력 : {{ formatEducation(selectProfileDetail?.rsmFnlEdctnCode) }}</h4>
            <h4>희망 연봉 : {{ formatNumberWithCommas(selectProfileDetail?.rsmEs) }} 원</h4>
            <div class="modal-btn d-grid gap-2 d-md-flex justify-content-md-end">
                <button class="btn btn-outline-dark" @click="changeResume">프로필 변경</button>
                <button @click="profileDetailModalOpen" class="btn btn-outline-dark">뒤로가기</button>
            </div>
        </div>
    </div>
    <section class="call-to-action with-full-borders mb-2">
        <div class="container text-center">
            <div class="row pb-3">
                <div class="col-auto">
                    {{ props.projectPostingData.entrprsName }}
                </div>
                <div class="col">
                    <router-link :to="`/projectDetail/${props.projectPostingData.prjctSq}`">{{
                        props.projectPostingData.prjctTtl }}</router-link>
                </div>
                <div class="col-1" @click="toggleImage">
                    <img :src="currentImage" alt="" style="width: 20px;">
                </div>
            </div>
            <div class="row d-flex flex-wrap align-items-center">
                <div class="col-auto">
                    {{ props.projectPostingData.prjctLctn }}
                </div>
                <div class="col">
                    {{ props.projectPostingData.prjctTpy }}
                </div>
                <div class="col-auto">
                    {{ props.projectPostingData.prjctPrd }}
                </div>
                <div class="col-md-auto">
                    <button v-if="props.projectPostingData.prjctEndDate >= new Date().toISOString()"
                        class="btn btn-modern btn-light mb-2" @click="applyModalOpen" >지원하기</button>
                    <button v-else class="btn btn-modern btn-dark mb-2">지원불가</button>
                </div>
            </div>
        </div>
    </section>

</template>

<script setup>
import { defineProps, ref, computed, reactive, onMounted, watch } from "vue";
import { api } from '@/axios';  // API 인스턴스 임포트
import { useStore } from "vuex";
import { showAlert, showConfirm } from "../../../../utill/utillModal";
const store = useStore();

const state = reactive({
    resumes: [],
    changeResume: {},
    member: {},
});

const props = defineProps({
    projectPostingData: Object
});
const member = computed(() => store.getters.getMember);

onMounted(() => {
    fetchLoginInfo();
    console.log("멤버정보:" , member.value);
})


// 이미지 상태 관리
const currentImage = ref(require('@/assets/sht.png'));

// 이미지 클릭 시 호출될 함수
const toggleImage = () => {
    const newImage = currentImage.value === require('@/assets/sht.png')
        ? require('@/assets/bht.png') // 다른 이미지 경로로 변경
        : require('@/assets/sht.png'); // 원래 이미지로 되돌림

    // 이미지 상태 업데이트
    currentImage.value = newImage;

    const mbr_sq = member.value.mbrSq;
    const prjctSq = props.projectPostingData.prjctSq;

    // 이미지 상태에 따라 다른 서버 요청 전송
    if (newImage === require('@/assets/bht.png')) {
        // bht.png로 변경될 때의 서버 요청
        sendImageChangeToServer(mbr_sq, prjctSq, 'remove/user'); // 스크랩 삭제
    } else {
        // sht.png로 변경될 때의 서버 요청
        sendImageChangeToServer(mbr_sq, prjctSq, 'create/user'); // 스크랩 추가
    }
};

// 스크랩 요청
const sendImageChangeToServer = async (mbr_sq, prjctSq, endpoint) => {
    try {
        if (endpoint === 'remove/user') {
            await api.$delete(`/scrap/${endpoint}/${mbr_sq}/${prjctSq}`);
        }
        else {
            await api.$post(`/scrap/${endpoint}/${mbr_sq}/${prjctSq}`);
        }

    } catch (error) {
        console.error('실패:', error);
    }
};

// 프로필 변경
function changeResume() {
    state.changeResume = selectProfileDetail;
    console.log(state.changeResume);
    profileModalCheck.value = false;
    profileDetailModalCheck.value = false;
}

// 프로젝트 지원
const projectApply = async () => {
    const isAlreadyApplied = await checkApplyProject();
    if (isAlreadyApplied) return; // 이미 지원했다면 실행 중단

    showConfirm("지원하시겠습니까?", async () => {
        try {
            // 일반 회원 로그인일 때
            if (store.getters.getUserType === 'user') {
                let data = {};
                if (Object.keys(state.changeResume).length > 0) {
                    data = {
                        mbrSq: state.changeResume.mbrSq,
                        rsmSq: state.changeResume.rsmSq,
                        prjctSq: props.projectPostingData.prjctSq,
                    };
                    console.log("바뀐 프로필로 지원합니다: ", data);
                } else {
                    data = {
                        mbrSq: representativeResume.value[0].mbrSq,
                        rsmSq: representativeResume.value[0].rsmSq,
                        prjctSq: props.projectPostingData.prjctSq,
                    };
                    console.log("대표 프로필로 지원합니다: ", data);
                }
                await api.$post(`/project-applies`, data);
                showAlert("지원 성공 !");
            }
        } catch (error) {
            console.log("에러메시지", error);
            showAlert("지원에 실패하였습니다.");
        }
    });
};

// // 해당 프로젝트 지원 했는지 체크
const checkApplyProject = async () => {
    try {
        if (store.getters.getUserType === 'user') {
            const response = await api.$get(`/project-applies/userApplyCheck/${state.member.mbrSq}/${props.projectPostingData.prjctSq}`);
            if (response) {
                showAlert("이미 지원한 프로젝트입니다.");
                return true; // 이미 지원했으면 true 반환
            }
        }
    } catch (error) {
        console.log("에러메시지: ", error);
    }
    return false; // 지원 안 했으면 false 반환
};

// 대표 프로필 미설정 or 선택 프로필 미설정
function showSetResume() {
    showAlert("프로필 설정이 필요합니다.");
}

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

// 모달 창 오픈 시 스크롤 제한
watch(modalCheck, () => {
    document.documentElement.style.overflow = modalCheck.value ? 'hidden' : 'auto';
});

// 이력서 정보 불러오기
const fetchResumes = async () => {
    try {
        const response = await api.$get(`/resumes/${state.member.mbrSq}`)
        state.resumes = response;
        console.log("이력서" , response);
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

// 로그인 유저 정보 불러오기
const fetchLoginInfo = async () => {
    try {
            const mbrId = store.getters.getMember?.mbrId;
            const response = await api.$get(`/member/detail/${mbrId}`);
            state.member = response;
            response.mbrMp = formattedPhoneNumber(response.mbrMp);
            console.log(state.member);
        await fetchResumes();
    } catch (error) {
        console.error('에러 메시지 : ', error);
    }

}
</script>

<style scoped>
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