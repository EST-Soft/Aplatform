<template>    
    <div class="container py-2 mb-3" style="background-color: #f4f4f4;">
        <div class="row align-items-center">
            <div class="col-12 pb-3 ">
                <div class="d-flex justify-content-between align-items-center mb-2">
                    <span class="badge text-white p-2 bg-primary" v-if="props.data.jbp_cndtn == '701'">
                        예정</span>
                    <span class="badge text-white p-2 bg-primary" v-else-if="props.data.jbp_cndtn == '702'">
                    진행중</span> 
                    <span class="badge text-white p-2 bg-danger" v-else-if="props.data.jbp_cndtn == '703'">
                        접수마감</span>
                        <span class="badge text-white p-2 bg-danger" v-else-if="props.data.jbp_cndtn == '704'">
                            종료</span>
                    <span class="bg-white p-2 border" style="margin-left: auto;">지원자 수: {{  applyCount }}</span>
                </div>
                <h5 class="mb-2">{{ props.data.entrprs_name }}</h5>
                <p class="mb-2"><router-link :to="`/board/detail/jobPosting/${props.data.jbp_sq}`">{{ props.data.jbp_ttl }}</router-link></p>
                <a href="#" class="popup-with-zoom-anim text-primary">지원한 이력서 | {{ props.data.rsm_ttl }}</a>
                <hr class="my-2">
                <div class="d-flex justify-content-between align-items-center pt-2">
                    <div class="d-flex align-items-center">
                        <p class="mb-0">지원일 | {{  props.data.apy_dtm }}</p>
                        <div v-if="props.data.code_name == '미열람'">
                            <span class="badge bg-white text-primary ms-2 p-2 border">미열람</span>
                        </div>
                        <div v-else>
                            <span class="badge bg-white text-primary ms-2 p-2 border">열람</span>
                        </div>
                    </div>
                    {{ props.data.code_name }}
                    {{ applyCancel }}
                    <div v-if="applyCancel === '지원취소'">
                        <button class="btn btn-gray btn-sm border">취소완료</button>
                    </div>  
                    <div v-else-if="applyCancel !=='지원취소'">
                        <button class="btn btn-danger btn-sm" @click="delapply">지원취소</button>
                    </div>
                    
                </div>
            </div>
        </div>
    </div>





    
</template>

<script setup>
import axios from 'axios';
// import { apply } from 'core-js/fn/reflect';
import { defineProps, ref, onMounted ,defineEmits } from 'vue';

const props = defineProps(['data'])

const applyCancel = ref(''); // 지원취소
const applyCount = ref(''); //공고 지원자 수
const apySq = ref('');

 
const emit = defineEmits(["applyCancelCnt"]);

onMounted( () => {
    applyCancel.value = props.data.code_name;
    applyCount.value = props.data.apply_count;
    apySq.value = props.data.apy_sq;
    console.log(props.data.code_name)
   
})


const delapply = () => { // 지원취소
    console.log(props.data.apy_sq);
    if(confirm("정말 취소하시겠습니까?") == true){
        axios.post("http://localhost:80/mypage/delApply",{apySq : props.data.apy_sq})
    .then(res => {
        console.log(res);
        applyCancel.value = '지원취소'
        applyCount.value = applyCount.value-1;
        emit("applyCancelCnt","지원취소완료");
        
    })
    .catch((error) => {
        console.log(error);
    });
    }else{
        return;
    }


};


</script>

<style scoped>
.border-bottom {
    border-bottom: 1px solid #ddd;
}
.badge {
    font-size: 14px;
}

p a {
    color: gray;
}
</style>