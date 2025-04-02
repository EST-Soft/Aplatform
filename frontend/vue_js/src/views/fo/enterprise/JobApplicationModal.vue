<!-- 타입스크립트 사용 -->
<template>
    <div class="modal-overlay">
      <div class="modal-container">
        <div class="modal-header">
          <h3>입사지원 신청서</h3>
          <button class="close-btn" @click="close">×</button>
        </div>
        <div class="modal-body">
          <div class="form-group mb-3">
            <label for="name" class="form-label">이름</label>
            <input type="text" id="mbrName" v-model="form.mbrName" class="form-control" readonly />
          </div>
          
          <div class="form-group mb-3">
            <label for="idNumber" class="form-label">주민번호 앞자리</label>
            <input type="text" id="mbrBd" v-model="form.mbrBd" class="form-control" readonly />
          </div>
          
          <div class="form-group mb-3">
            <label for="phone" class="form-label">전화번호</label>
            <input type="tel" id="mbrMp" v-model="form.mbrMp" class="form-control" 
              placeholder="연락 가능한 전화번호를 입력하세요" />
          </div>
          
          <div class="form-group mb-3">
            <label class="form-label">프로필</label>
            <div class="d-flex align-items-center">
              <div class="selected-resume me-3" @click="openResumeSelector">
                <div v-if="form.selectedResume" class="resume-preview">
                  <span>{{ form.selectedResume.rsmTtl }}</span>
                </div>
                <div v-else class="resume-placeholder">
                  프로필을 선택하세요
                </div>
              </div>
              <button class="btn btn-outline-primary" @click="openResumeSelector">
                프로필 선택
              </button>
            </div>
          </div>
          
          <!-- 프로필 선택기 모달 -->
          <div v-if="showResumeSelector" class="resume-selector">
            <div class="resume-selector-header">
              <h4>프로필 선택</h4>
              <button class="close-btn" @click="closeResumeSelector">×</button>
            </div>
            <div class="resume-selector-body">
              <div v-if="resumeListData.resumeDatas.length == 0" class="text-center py-3">
                사용 가능한 프로필이 없습니다.
              </div>
              <div v-else class="resume-list">
                <div v-for="resumeData in resumeListData.resumeDatas" :key="resumeData.rsmSq" 
                    class="resume-item" 
                    :class="{ 'selected': form.selectedResume?.rsmSq === resumeData.rsmSq }"
                    @click="selectResume(resumeData)">
                  <span>{{ resumeData.rsmTtl }}</span>
                </div>
              </div>
            </div>
            <div class="row" id="pagination-container">
                <div class="pagination-wrapper" v-if="resumeListData.paginationData.totalDataCount > 1">
                    <PaginationData :paginationData="resumeListData.paginationData" @change-page-no="changePageNo" />
                </div>
            </div>
          </div>
        </div>
        
        <div class="modal-footer">
          <button class="btn btn-secondary me-2" @click="close">취소</button>
          <button class="btn btn-primary" @click="submit" :disabled="!isFormValid">지원하기</button>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, computed, defineEmits, onMounted } from 'vue';
  import { api } from '@/axios.js';
  import { useStore } from 'vuex';
  import PaginationData from "@/components/fo/enterprise/common/PaginationData.vue";
  import { showAlert } from '@/utill/utillModal';
  import { useRoute } from 'vue-router';

  const route = useRoute();
  
  const emit = defineEmits(['close', 'submit']);
  const store = useStore();
  
  // 폼 데이터
  const form = ref({
    mbrName: '',
    mbrBd: '',
    mbrMp: '',
    selectedResume: null
  });
  
  // 프로필 선택기 상태
  const showResumeSelector = ref(false);
  const loading = ref(false);
  
  const member = store.getters.getMember;

  // 사용자의 이력서/프로필 목록 가져오기
  const fetchResumes = async () => {
    loading.value = true;
    try {
      if (loading.value && member?.mbrSq) {
        const response = await api.$get(`/apply/${member.mbrSq}`);
        form.value.mbrName = response.mbrName;
        form.value.mbrBd = response.mbrBd;
        form.value.mbrMp = response.mbrMp;
        form.value.selectedResume = response.selectedResume;
      }
    } catch (error) {
      console.error('이력서 목록을 가져오는 중 오류가 발생했습니다:', error);
    } finally {
      loading.value = false;
    }
  };
  
  // 모달 열기/닫기 처리
  const close = () => {
    emit('close');
  };
  
  const resumeListData = ref({
    resumeDatas: [],
    paginationData: {},
    searchData: {},
});

  // 프로필 선택기 열기/닫기
  const openResumeSelector = async () => {
    showResumeSelector.value = true;

    resumeListData.value.searchData = {
            mbr_sq: member.mbrSq,
            sort: "desc",
            pageNo: 1
        };
    callAxios();
  };
  
  const callAxios = async () => {
    // 리스트 뿌려주는 기본 axios
    await api.$get("/resumes/resume-list/" + resumeListData.value.searchData.mbr_sq + "/" + resumeListData.value.searchData.sort + "/" + resumeListData.value.searchData.pageNo)
        .then((success) => {
            console.log('리스트 뿌려 주기 성공', success);
            resumeListData.value = success;
        })
        .catch((error) => {
            console.log('리스트 뿌려 주기 실패' + error);
        });
  };

  const changePageNo = (emit) => {
    resumeListData.value.searchData.pageNo = emit;
    callAxios();
};

  const closeResumeSelector = () => {
    showResumeSelector.value = false;
  };
  
  // 프로필 선택
  const selectResume = (resume) => {
    form.value.selectedResume = resume;
    closeResumeSelector();
  };
  
  // 폼 유효성 검사
  const isFormValid = computed(() => {
    return (
      form.value.mbrName !== '' &&
      form.value.mbrBd !== '' &&
      form.value.mbrMp !== '' &&
      form.value.selectedResume !== null
    );
  });
  
  // 지원 신청 제출
  const submit = async () => {
    if (!isFormValid.value) {
      showAlert('빈 칸이 존재합니다.');
      return;
    }
    
    try {
      const applyData = {
        resume: { rsmSq: form.value.selectedResume.rsmSq },
        jobPosting: { jbpSq: route.params.jbpSq },
        apyDtm: new Date().toISOString()
      };
      
      const response = await api.$post('/apply/insert', applyData, {
          params: {
            mbrSq: member.mbrSq,
            mbrMp: form.value.mbrMp
          }
        }
      );
      emit('submit', response);
    } catch (error) {
      showAlert(error.response.data);
    }
  };
  
  // 컴포넌트 마운트 시 데이터 로드
  onMounted(() => {
    fetchResumes();
  });
  </script>
  
  <style scoped>
  .modal-overlay {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background-color: rgba(0, 0, 0, 0.5);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 1000;
  }
  
  .modal-container {
    background-color: white;
    border-radius: 8px;
    width: 500px;
    max-width: 95%;
    max-height: 90vh;
    overflow-y: auto;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
  }
  
  .modal-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 16px;
    border-bottom: 1px solid #e9ecef;
  }
  
  .modal-body {
    padding: 16px;
    position: relative;
  }
  
  .modal-footer {
    display: flex;
    justify-content: flex-end;
    padding: 16px;
    border-top: 1px solid #e9ecef;
  }
  
  .close-btn {
    background: none;
    border: none;
    font-size: 24px;
    cursor: pointer;
    color: #6c757d;
  }
  
  .selected-resume {
    border: 1px solid #ced4da;
    border-radius: 4px;
    padding: 8px 12px;
    min-width: 200px;
    min-height: 40px;
    cursor: pointer;
    display: flex;
    align-items: center;
  }
  
  .resume-placeholder {
    color: #6c757d;
  }
  
  .resume-selector {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    background-color: white;
    border-radius: 8px;
    width: 90%;
    max-height: 80%;
    overflow-y: auto;
    z-index: 1100;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  }
  
  .resume-selector-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 12px 16px;
    border-bottom: 1px solid #e9ecef;
  }
  
  .resume-selector-body {
    padding: 16px;
  }
  
  .resume-list {
    display: flex;
    flex-direction: column;
    gap: 8px;
  }
  
  .resume-item {
    padding: 12px;
    border: 1px solid #e9ecef;
    border-radius: 4px;
    cursor: pointer;
    transition: background-color 0.2s;
  }
  
  .resume-item:hover {
    background-color: #f8f9fa;
  }
  
  .resume-item.selected {
    background-color: #e2f0ff;
    border-color: #3b82f6;
  }

  #pagination-container {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
  }

  .pagination-wrapper {
      display: flex;
      justify-content: center;
  }
  </style>