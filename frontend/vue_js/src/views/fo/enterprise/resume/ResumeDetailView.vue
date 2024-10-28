<template>
    <div>
        <DetailResume />
    </div>
    <div class="col-lg-12">
        <div class="float-end">
            <button @click="goBack" class="btn btn-outline btn-xl btn-light mb-2">확인</button>
        </div>
    </div>
</template>

<script setup>
import DetailResume from '../../../../components/fo/enterprise/resume/DetailResume.vue';
import { useRouter } from 'vue-router';

const router = useRouter();

const goBack = () => {
  router.back(); // 또는 router.go(-1);
};

</script>

<style scoped>
    button{
        margin-right: 40px;
    }
</style>