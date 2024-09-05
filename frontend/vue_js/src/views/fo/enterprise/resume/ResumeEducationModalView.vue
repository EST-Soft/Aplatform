<template>
    <div v-if="props.isVisible" class="modal-education" @click.self="closeModal">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">학력 추가</h5>
            <button type="button" class="btn-close" @click="closeModal"></button>
          </div>
          <div class="modal-body">
            <form @submit.prevent="addEducation">
              <div class="mb-3">
                <label for="schoolName" class="form-label">학교 이름</label>
                <input type="text" id="schoolName" v-model="newEducation.schoolName" class="form-control" required />
                <SearchPopup></SearchPopup>
              </div>
              <div class="mb-3">
                <label for="department" class="form-label">학과</label>
                <input type="text" id="department" v-model="newEducation.department" class="form-control" required />
              </div>
              <div class="mb-3">
                <label for="status" class="form-label">졸업 상태</label>
                <select id="status" v-model="newEducation.status" class="form-select" required>
                  <option value="" disabled>선택하세요</option>
                  <option value="졸업">졸업</option>
                  <option value="재학">재학</option>
                  <option value="휴학">휴학</option>
                </select>
              </div>
              <div class="mb-3">
                <label for="startDate" class="form-label">입학 날짜</label>
                <input type="date" id="startDate" v-model="newEducation.startDate" class="form-control" required />
              </div>
              <div class="mb-3">
                <label for="endDate" class="form-label">졸업 날짜</label>
                <input type="date" id="endDate" v-model="newEducation.endDate" class="form-control" />
              </div>
              <button type="submit" class="btn btn-primary">추가</button>
            </form>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, defineEmits, defineProps } from 'vue';
  import SearchPopup from '../../../../components/fo/user/common/SearchPopup.vue';
  
  // Props
  const props = defineProps({
    isVisible: Boolean
  });
  
  // Emits
  const emit = defineEmits(['update:isVisible', 'update:educationList']);
  
  // Data
  const newEducation = ref({
    schoolName: '',
    department: '',
    status: '',
    startDate: '',
    endDate: ''
  });
  
  // Methods
  const closeModal = () => {
    emit('update:isVisible', false);
  };
  
  const addEducation = () => {
    emit('update:educationList', newEducation.value);
    closeModal();
  };
  </script>
  
  <style scoped>
  .modal-education {
    display: flex;
    z-index: 100000;
    align-items: center;
    justify-content: center;
    position: fixed;
    top: 50%;
    left: 50%;
    width: 50%;
    height: 95%;
    transform: translate(-50%, -50%);
    background-color: rgba(255, 255, 255, 1);
    border: 1px solid rgba(0, 0, 0, 1)
  }
  </style>
  