<!-- 타입스크립트 사용 -->
<template>
    <button class="btn btn-primary" @click="applyJob">소속지원</button>
    <!-- 입사지원 모달 컴포넌트 -->
    <JobApplicationModal
      v-if="props.isModalOpen"
      @close="closeApplicationModal"
      @submit="handleSubmit"
    />
</template>
<script setup>

import JobApplicationModal from './JobApplicationModal.vue';
import { computed, defineProps, defineEmits } from 'vue';
import { showAlert } from '../../../utill/utillModal';
import { useStore } from 'vuex';

const store = useStore();
// const member = computed(() => store.getters.getMember);
const isMember = computed(() => {
  return store.getters.getMember?.mbrSq != null;
});

const props = defineProps({
  isModalOpen: {
      type: Boolean,
      default: false,
      required: true
    }
});

// 소속 신청 관련
const emit = defineEmits(['open-modal', 'close-modal', 'submit'])

// 소속 지원 클릭 시 모달 표시
const applyJob = () => {
  if (!isMember.value) {
    showAlert('로그인이 필요한 서비스입니다.');
    return;
  }

  emit('open-modal');
}

// 소속 지원 모달 닫기
const closeApplicationModal = () => {
  emit('close-modal');
}

// 지원서 제출
const handleSubmit = () => {
  emit('submit');
}
</script>