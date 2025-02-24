<template>
    <div>
      <h1>Q&A Answer</h1>
      <form @submit.prevent="submitAnswer">
        <label for="title">Title:</label>
        <input v-model="title" id="title" type="text" required />
  
        <label for="content">Content:</label>
        <textarea v-model="content" id="content" required></textarea>
  
        <button type="submit">Submit</button>
      </form>
  
      <!-- 모달 컴포넌트 사용 -->
      <textModal />
  
      <!-- 버튼 클릭 시 모달 표시 -->
      <button @click="openModal">답변하기</button>
    </div>
 
  </template>
  
  <script>
  import { mapActions } from 'vuex';
  import textModal from '@/components/modal/textModal.vue';
  
  export default {
    components: {
      textModal
    },
    data() {
      return {
        title: '',
        content: ''
      };
    },
    methods: {
      ...mapActions('showModal', ['showModal']),
      submitAnswer() {
        // 글 작성 로직 구현
  
        // 모달 표시
        this.showModal({
          content: 'Your answer has been submitted successfully!',
          type: 'alert'
        });
  
        // 필요시 페이지 이동 방지
      },
      openModal() {
        // 모달을 표시하는 로직
        this.showModal({
          content: 'Are you sure you want to proceed?',
          type: 'confirm',
          onConfirm: this.handleConfirm,
          onCancel: this.handleCancel
        });
      },
      handleConfirm() {
        // Confirm 클릭 시의 로직
        console.log('Confirmed!');
      },
      handleCancel() {
        // Cancel 클릭 시의 로직
        console.log('Cancelled!');
      }
    }
  };
  </script>
  
  <style scoped>
  /* 스타일을 여기에 추가할 수 있습니다. */
  </style>