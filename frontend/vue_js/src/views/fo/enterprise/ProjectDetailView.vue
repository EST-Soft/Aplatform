<template>
    <div class="modal-wrap" v-show="modalCheck">
        <div class="modal-container">
            <h3 class="position-absolute top-30 start-50 translate-middle">프로젝트 지원서</h3>
            <h4>이름 : 백바울</h4>
            <h4>주민번호 앞자리 : 950822</h4>
            <h4>전화번호 : 010-5555-5555</h4>
            <h4>프로필 : 프로필 1 <a>(재선택)</a></h4>

            <div class="modal-btn d-grid gap-2 d-md-flex justify-content-md-end">
                <button @click="modalOpen" class="btn btn-outline-dark">닫기</button>
                <button @click="modalOpen" class="btn btn-outline-dark">확인</button>
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
                    <h1>{{ state.item.prjctTtl }} / {{ state.item.entrprsName }}</h1>
                </div>
                <div class="col-1 themed-grid-col d-flex align-items-center justify-content-center">
                    <i :class="isFilled ? 'bi bi-heart-fill' : 'bi bi-heart'" @click="toggleHeart"
                        class="heart-icon"></i>
                </div>

            </div>

        </div>

        <div class="container-md themed-container">
            <div class="row mb-3">
                <div class="col-4 themed-grid-col">
                    <h2>경력 : {{ state.item.prjctEsntlCrr }}</h2>
                </div>
                <div class="col-4 themed-grid-col">
                    <h2>학력 : </h2>
                </div>
                <div class="col-4 themed-grid-col">
                    <h2>위치 : {{ state.item.prjctLctn }}</h2>
                </div>
                <div class="col-4 themed-grid-col">
                    <h2>근무형태 : {{ state.item.prjctTpy }}</h2>
                </div>
                <div class="col-4 themed-grid-col">
                    <h2>기간 : {{ state.item.prjctPrd }}</h2>
                </div>
                <div class="col-4 themed-grid-col">
                    <h2>모집 직군 : {{ state.item.prjctWork }}</h2>
                </div>
            </div>
        </div>

        <div class="container-md themed-container">
            <div class="row mb-3">
                <div class="col-12 themed-grid-col">
                    <h2>사용기술 : {{ state.item.prjctUseSkl }}</h2>
                </div>
                <div class="col-12 themed-grid-col">
                    <h2>자격요건 : {{ state.item.prjctEsntlSkl }}</h2>
                </div>
                <div class="col-12 themed-grid-col">
                    <h2>인터뷰 기간 : {{ getDate(state.item.prjctStrt) }} ~ {{ getDate(state.item.prjctEnd) }}</h2>
                </div>
            </div>
        </div>

        <div id="btn" class="container-md themed-container d-flex justify-content-end">
            <button type="button" class="btn btn-outline-dark" @click="modalOpen">프로젝트 지원</button>
        </div>

    </section>
</template>



<script setup>
import { onMounted, reactive, ref, watch } from 'vue';
import { useRoute } from 'vue-router';
import { api } from '../../../axios';
import moment from 'moment';

const route = useRoute();
const prjctSq = ref(route.params.prjctSq);

const state = reactive({
    item: {},
});


const fetchItems = async () => {
    try {
        const response = await api.$get(`/project/${prjctSq.value}`);
        state.item = response;
        console.log(response);
    } catch (error) {
        console.error('에러 메시지 : ', error);
    }
}

onMounted(() => {
    fetchItems();
})

// 날짜 형식

function getDate(date) {
    const formatDate = moment(date).format("YYYY-MM-DD");
    return formatDate;
}

// 스크랩 관련 
const isFilled = ref(false);

function toggleHeart() {
    isFilled.value = !isFilled.value;
}


// 모달창 관련
const modalCheck = ref(false);

function modalOpen() {
    modalCheck.value = !modalCheck.value;
}

// 모달 창 오픈 시 스크롤 제한
watch(modalCheck, () => {
    document.documentElement.style.overflow = modalCheck.value ? 'hidden' : 'auto';
});


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
    bottom: 20px; /* 하단에서 20px 간격 */
    right: 20px;  /* 우측에서 20px 간격 */
    display: flex;
    gap: 10px;  /* 버튼 간 간격 */
}

a{
    cursor: pointer;   
}
a:hover{
    text-decoration: underline !important;
}
</style>