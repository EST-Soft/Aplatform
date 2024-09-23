<template>
<section class="call-to-action with-full-borders mb-2">
    <div class="container text-center">
        <div class="row pb-3">
            <div class="col-auto">
                {{ props.jobPostingData.entrprs_name }}
            </div>
            <div class="col">
                <router-link :to="`/board/detail/jobPosting/${props.jobPostingData.jbp_sq}`">{{ props.jobPostingData.jbp_ttl }}</router-link>
            </div>
            <div class="col-1" @click="toggleImage">
                <img :src="currentImage" alt="" style="width: 20px;">
            </div>
        </div>
        <div class="row d-flex flex-wrap align-items-center">
            <div class="col-auto">
                {{ props.jobPostingData.work_area }}
            </div>
            <div class="col">
                {{ findCodeName(props.jobPostingData.crr_drtn) }}
            </div>
            <div class="col">
                {{ findCodeName(props.jobPostingData.edctn) }}
            </div>
            <div class="col">
                {{ props.jobPostingData.work_form }}
            </div>
            <div class="col-auto">
                {{ fomatDate(props.jobPostingData.regstr_strt_dtm) }} 부터
            </div>
            <div class="col-auto">
                {{ fomatDate(props.jobPostingData.regstr_dln_dtm) }} 까지
            </div>
            <div class="col-md-auto">
                <button v-if="props.jobPostingData.regstr_dln_dtm >= new Date().toISOString()" @click="$emit('scrapApy', props.jobPostingData.jbp_sq)" class="btn btn-modern btn-light mb-2">지원하기</button>
                <button v-else class="btn btn-modern btn-dark mb-2">지원불가</button>
            </div>
        </div>
    </div>
</section>

</template>

<script setup>
import { defineProps, onMounted, ref, computed } from "vue";
import { api } from '@/axios';  // API 인스턴스 임포트
import { useStore } from "vuex";

const store = useStore();

const props = defineProps(["jobPostingData"]); 
const result = ref({commonCodeList: []});
const member = computed(() => store.getters.getMember);

onMounted(async () => {
    try {
        result.value = await api.$get(`/user/mypage/scrap/commonCode`);
    } catch (error) {
        console.error('Failed to fetch resume details:', error);
    }
});

// 코드 ID에 해당하는 코드 이름 찾기
const findCodeName = (codeId) => {
    const code = result.value.commonCodeList.find(item => item.code_id === codeId);
    return code ? code.code_name : 'N/A'; // 값이 없을 경우 'N/A' 반환
};

// 포멧 함수
// 날짜 포멧 변경
const fomatDate = (dateTime) => {
    const returnData = new Date(dateTime).toLocaleDateString();

    return returnData;
};

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
    const jbp_sq = props.jobPostingData.jbp_sq;

    // 이미지 상태에 따라 다른 서버 요청 전송
    if (newImage === require('@/assets/bht.png')) {
        // bht.png로 변경될 때의 서버 요청
        sendImageChangeToServer(mbr_sq, jbp_sq, 'scrap/delete'); // 스크랩 삭제
    } else {
        // sht.png로 변경될 때의 서버 요청
        sendImageChangeToServer(mbr_sq, jbp_sq, 'scrap/insert'); // 스크랩 추가
    }
};

// 스크랩 요청
const sendImageChangeToServer = async (mbr_sq, jbp_sq, endpoint) => {
    try {
        const response = await api.$post(`/user/mypage/${endpoint}/${mbr_sq}/${jbp_sq}`);
        console.log('서버 응답:', response.data);
    } catch (error) {
        console.error('실패:', error);
    }
};

</script>

<style scoped></style>