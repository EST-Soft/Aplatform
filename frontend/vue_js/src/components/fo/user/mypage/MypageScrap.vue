<template>
<section class="call-to-action with-full-borders mb-2">
    <table class="table">
        <thead>
            <tr>
                <td>
                    {{ props.jobPostingData.entrprs_name }}
                </td>
                <td colspan="5">
                    {{ props.jobPostingData.jbp_ttl }}
                </td>
                <th>
                    ♡
                </th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>
                    {{ props.jobPostingData.work_area }}
                </td>
                <td>
                    {{ findCodeName(props.jobPostingData.crr_drtn) }}
                </td>
                <td>
                    {{ findCodeName(props.jobPostingData.edctn) }}
                </td>
                <td>
                    {{ props.jobPostingData.work_form }}
                </td>
                <td>
                    {{ fomatDate(props.jobPostingData.regstr_strt_dtm) }} 부터
                </td>
                <td>
                    {{ fomatDate(props.jobPostingData.regstr_dln_dtm) }} 까지
                </td>
                <td>
                    <button v-if="props.jobPostingData.regstr_dln_dtm >= new Date().toISOString()" @click="$emit('scrapApy', props.jobPostingData.jbp_sq)" class="btn btn-modern btn-light mb-2">지원하기</button>
                    <button v-else class="btn btn-modern btn-dark mb-2">지원불가</button>
                </td>
            </tr>
        </tbody>
    </table>
</section>

</template>

<script setup>
import { defineProps, onMounted, ref } from "vue";
import { api } from '@/axios';  // API 인스턴스 임포트

const props = defineProps(["jobPostingData"]); 
const result = ref({commonCodeList: []});

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


</script>

<style scoped></style>