<template>
    <section id="examples" class="section section-default">
      <div class="container">
        <section class="card card-admin">
          <header class="card-header">
            <div class="card-actions">
              <a href="#" class="card-action card-action-toggle" data-card-toggle=""></a>
              <a href="#" class="card-action card-action-dismiss" data-card-dismiss=""></a>
            </div>
            <h2 class="card-title">공고</h2>
          </header>
        </section>
  
        <div v-if="loading" class="text-center">Loading...</div>
        <div v-else>
          <!-- 첫 번째 줄 -->
          <div class="row mt-3">
            <div class="col-md-3">
              <div class="mb-3">
                <label for="jbpTl" class="form-label">제목</label>
                <input type="text" v-model="jbp.jbpTl" class="form-control" id="jbpTl" placeholder="제목" readonly />
              </div>
            </div>
            <div class="col-md-3">
              <div class="mb-3">
                <label for="enterpriseName" class="form-label">기업 이름</label>
                <input type="text" v-model="jbp.enterpriseName" class="form-control" id="enterpriseName" placeholder="기업 이름" readonly />
              </div>
            </div>
            <div class="col-md-2">
              <div class="mb-3">
                <label class="form-label">조회수: {{ jbp.hits }}</label>
              </div>
            </div>
            <div class="col-md-4">
              <div class="mb-3">
                <label class="form-label">접수시작일: {{ formatDate(jbp.regstrStrtDtm) }}</label>
              </div>
            </div>
          </div>
  
          <!-- 두 번째 줄 -->
          <div class="row mt-3">
            <div class="col-md-4">
              <div class="mb-3">
                <label for="cr" class="form-label">경력</label>
                <input type="text" :value="careerText " class="form-control" id="cr" placeholder="경력" readonly />
              </div>
            </div>
            <div class="col-md-4">
              <div class="mb-3">
                <label for="edctn" class="form-label">학력</label>
                <input type="text" :value="educationText" class="form-control" id="edctn" placeholder="학력" readonly />
              </div>
            </div>
            <div class="col-md-4">
              <div class="mb-3">
                <label for="workArea" class="form-label">근무지역</label>
                <input type="text" v-model="jbp.workArea" class="form-control" id="workArea" placeholder="근무지역" readonly />
              </div>
            </div>
          </div>
  
          <!-- 세 번째 줄 -->
          <div class="row mt-3">
            <div class="col-md-4">
              <div class="mb-3">
                <label for="workForm" class="form-label">근무형태</label>
                <input type="text" v-model="jbp.workForm" class="form-control" id="workForm" placeholder="근무형태" readonly />
              </div>
            </div>
            <div class="col-md-4">
              <div class="mb-3">
                <label for="slry" class="form-label">급여</label>
                <input type="text" v-model="jbp.slry" class="form-control" id="slry" placeholder="급여" readonly />
              </div>
            </div>
            <div class="col-md-4">
              <div class="mb-3">
                <label for="slry" class="form-label">모집 직군</label>
                <input type="text" v-model="jbp.jobName" class="form-control" id="jobName" placeholder="직종" readonly />
              </div>
            </div>
          </div>
          <div class="col-md-12">
            <div class="mb-1">
              <label for="jbpCntnt" class="form-label">공고 내용</label>
              <QuillEditorComponent v-model="jbp.jbpCntnt" :isReadOnly="isEditable" />
            </div>
          </div>
          
          <!-- 입사지원 버튼 -->
        <div class="row mt-3">
          <div class="col d-flex justify-content-end">
            <button class="btn btn-primary" @click="applyJob">입사지원</button>
          </div>
        </div>

          <!-- 수정, 삭제 버튼 -->
          <div class="row mt-3">
            <div class="col d-flex justify-content-end">
              <button class="btn btn-success" @click="goUpdatePage">수정</button>
              <button class="btn btn-danger ms-2" @click="confirmDelete">삭제</button>
            </div>
          </div>
        </div>
      </div>
    </section>
  </template>
  
  <script setup>
  import { ref, onMounted, computed } from 'vue';
  import { api } from '@/axios.js';
  import { useRoute, useRouter } from 'vue-router';
  import QuillEditorComponent from '@/components/common/Editor.vue';

  const isEditable = ref(true);

  const route = useRoute();
  const router = useRouter();
  
  const loading = ref(true);

  const jbp = ref({
    jbpSq: 0,
    enterpriseName: "",
    jbpTl: "",
    jbpCntnt: "",
    hits: 0,
    cr: "",
    edctn: "",
    workArea: "",
    jobName: "",
    workForm: "",
    slry: "",
    regstrStrtDtm: ""
  });
  
  const formatDate = (dateString) => {
    const date = new Date(dateString);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
  };
  
  const fetchJobPostingDetail = async () => {
    const jbpSq = route.params.jbpSq;
  
    try {
      const response = await api.$get(`/board/detail/jobPosting/${jbpSq}`);
      jbp.value = response;
    } catch (error) {
      console.error('Error fetching job posting detail:', error);
    } finally {
      loading.value = false;
    }
  };
  
  onMounted(() => {
    fetchJobPostingDetail();
  });
  
  const goUpdatePage = () => {
    isEditable.value = false;
    router.push(`/board/jobPostingUpdate/${jbp.value.jbpSq}`);
  };
  
  const confirmDelete = () => {
    if (window.confirm('게시글을 삭제 하시겠습니까?')) {
      deleteBoard();
      alert('게시글이 삭제되었습니다.');
    }
  };
  
  const deleteBoard = async () => {
    try {
      await api.$delete(`/board/jobPostingDelete/${jbp.value.jbpSq}`);
      console.log('게시글이 삭제 되었습니다');
      router.push('/board/list/jobPosting');
    } catch (error) {
      console.error('게시글 삭제가 실패 되었습니다:', error);
    }
  };

  const applyJob = () => {
  const resumeId = prompt('이력서 번호를 입력하세요:');
  if (resumeId) {
    insertApply(resumeId);
  }
};

const insertApply = async (resumeId) => {
  const applyData = {
    resume: { rsmSq: resumeId }, 
    jobPosting: { jbpSq: jbp.value.jbpSq },
    apyDtm: new Date().toISOString()
  };

  try {
    const response = await api.$post('/apply/insert', applyData);
    alert(response); 
  } catch (error) {
    console.error('Error applying job:', error);
    alert('입사지원을 처리하는 중 오류가 발생했습니다.');
  }
};

// 학력 매핑 테이블
const educationMapping = {
  '606': '학력무관',
  '605': '고등학교 이상',
  '604': '대학교(2,3년제) 이상',
  '603': '대학교(4년제) 이상',
  '602': '석사',
  '601': '박사'
};

// 경력 매핑 테이블
const careerMapping = {
  '801': '경력무관',
  '802': '신입',
  '803': '경력'
};

// 학력 텍스트 반환 함수
const getEducationText = (value) => {
  return educationMapping[value] || value;
};

// 경력 텍스트 반환 함수
const getCareerText = (value) => {
  return careerMapping[value] || value;
};

const educationText = computed(() => getEducationText(jbp.value.edctn));
const careerText = computed(() => getCareerText(jbp.value.cr));

  </script>
  
  <style scoped>
  /* 추가적인 스타일 정의 */
  </style>
  