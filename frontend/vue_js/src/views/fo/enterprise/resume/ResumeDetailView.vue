<template>
    <div>
        <DetailResume />
    </div>
    <div class="col-lg-12">
        <div class="float-end">
            <router-link class="btn btn-outline btn-xl btn-light mb-2" to="/resumes/resume-list">확인</router-link>
        </div>
    </div>
</template>

<script setup>
import DetailResume from '../../../../components/fo/enterprise/resume/DetailResume.vue';

</script>

<style scoped></style>
