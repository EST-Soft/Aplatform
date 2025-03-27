<template>
    <button @click="toggleScrap" class="btn btn-outline-warning btn-sm">
        <i class="bi" :class="scrapped ? 'bi-star-fill' : 'bi-star'"></i>
    </button>
</template>

<script setup>
import { onMounted, ref, watch, defineProps, defineEmits } from 'vue';
import { api } from '@/axios.js';
import { showAlert } from '../../../utill/utillModal';

const props = defineProps({
    mbrSq: Number,
    jbpSq: Number,
    scrapped: Boolean
})

const emit = defineEmits(['update:scrapped']);
const scrapped = ref(props.scrapped);

onMounted(() => {
    console.log('스크랩의 props', props);
})

watch (() => props.scrapped, (newValue) => {
    scrapped.value = newValue;
})

const toggleScrap = async () => {
    try {
        const endpoint = scrapped.value
        ? `/user/mypage/scrap/delete/${props.mbrSq}/${props.jbpSq}`
        : `/user/mypage/scrap/insert/${props.mbrSq}/${props.jbpSq}`;

        const response = await api.$post(endpoint);

        scrapped.value = !scrapped.value;

        // 부모컴포넌트에게 변경 알림
        emit('update:scrapped', scrapped.value);

        showAlert(response);
    } catch (error) {
        showAlert(error.response.data);
        console.log(error.response.data);
    }
}
</script>