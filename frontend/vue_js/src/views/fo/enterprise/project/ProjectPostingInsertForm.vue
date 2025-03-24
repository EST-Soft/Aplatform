<template>
  <section id="examples" class="section section-default">
    <div class="container">
      <section class="card card-admin">
        <header class="card-header">
          <h2 class="card-title">프로젝트 등록</h2>
        </header>
      </section>

      <div class="mb-3">
        <label for="prjctTtl" class="form-label" style="margin-top: 40px;">프로젝트_이름</label>
        <input type="text" v-model="prjctTtl" class="form-control" id="prjctTtl" placeholder="제목을 입력하세요">
      </div>

      <div class="row" style="margin-top: 30px;">
        <!-- 모집 직군 -->
        <div class="col-md-3 mb-3">
          <label for="jobName" class="form-label">모집 직군</label>
          <div id="prjctWork" class="form-control" style="height: auto;">
            <div v-for="job in jobs" :key="job.jobSq" class="form-check">
              <input type="radio" v-model="selectedprjctWork" :value="job.jobSq" class="form-check-input"
                :id="'job-' + job.jobSq" name="jobGroup">
              <label class="form-check-label" :for="'job-' + job.jobSq">{{ job.jobScName }}</label>
            </div>
          </div>
        </div>

        <!-- 근무지역 -->
        <div class="col-md-3 mb-3">
          <label for="prjctLctn" class="form-label">근무지역</label>
          <div id="prjctLctn" class="form-control" style="height: auto;">
            <div v-for="area in areas" :key="area.areaSq" class="form-check">
              <input type="radio" v-model="selectedprjctLctn" :value="area.areaName" class="form-check-input"
                :id="'area-' + area.areaSq" name="areaGroup">
              <label class="form-check-label" :for="'area-' + area.areaSq">{{ area.areaName }}</label>
            </div>
          </div>
        </div>

        <!-- 경력 -->
        <div class="col-md-3 mb-3">
          <label for="crrDrtn" class="form-label">경력</label>
          <select v-model="prjctEsntlCrr" class="form-control" id="prjctEsntlCrr">
            <option value="" disabled selected hidden>원하는 경력을 선택해주세요.</option>
            <option value="초급">초급</option>
            <option value="중급">중급</option>
            <option value="고급">고급</option>
            <option value="상관없음">상관없음</option>
          </select>
        </div>

        <!-- 근무형태 -->
        <div class="col-md-2 mb-3">
          <label for="prjctTpy" class="form-label">근무형태</label>
          <select v-model="prjctTpy" class="form-control" id="prjctTpy" @change="handleTpyChange">
            <option value="" disabled selected hidden>근무 형태 선택</option>
            <option value="정규직">정규직</option>
            <option value="계약직">계약직</option>
            <option value="인턴">인턴</option>
          </select>
        </div>
      </div>

      <div style="display: flex; justify-content: space-around;">

        <div class="col-md-3 mb-3" style="margin-top: 30px;">
          <label for="useSkl" class="form-label" style="min-height: 30px;">사용 기술</label>
          <div class="input-group">
            <input type="text" :value="formattedUseSkills" class="form-control" id="useSkl" @click="openUseSkillsModal"
              placeholder="기술명을 입력하세요" readonly />
          </div>
          <UseSkillsResume :isVisible="showUseSkillsModal" :useSkillsData="useSkillsData"
            @update:isVisible="showUseSkillsModal = $event" @update:useSkillsData="updateUseSkillsData" />
        </div>

        <div class="col-md-3 mb-3">
          <label for="sklName" class="form-label" style=" margin-top: 20px; margin-bottom: 15px;">
            필수 스킬
          </label>
          <label style="font-size: 10px;">&nbsp;* 필수 스킬을 보유한 사람들만 지원 가능</label>

          <!-- 기술들을 콤마로 구분해서 input 안에 표시 -->
          <div class="input-group">
            <input type="text" :value="formattedSkills" class="form-control" id="sklName" @click="openSkillsModal"
              placeholder="기술명을 입력하세요" readonly />
          </div>
        </div>




        <!-- Skills 선택 모달 -->
        <SkillsResume :isVisible="showSkillsModal" :skillsData="skillsData" @update:isVisible="showSkillsModal = $event"
          @update:skillsData="updateSkillsData" />


      </div>


      <div class="row" >
        <div class="col-md-6 mb-3">
          <label for="prjctPrdstrd" class="form-label">프로젝트 시작일</label>
          <input type="datetime-local" v-model="prjctPrdstrd" class="form-control" id="prjctPrdstrd"
            :min="minRegstrStrtDtm">
        </div>
        <div class="col-md-6 mb-3">
          <label for="prjctPrdend" class="form-label">프로젝트 마감일</label>
          <input type="datetime-local" v-model="prjctPrdend" class="form-control" id="prjctPrdend">
        </div>
      </div>
      
      
      
      
    </div>
    <div style="display: flex; justify-content: center; align-items: center;">
    <div class="row" style="width: 1000px;">
    <div class="mb-3">
      <label for="jbpCntnt" class="form-label">추가내용</label>
      <QuillEditorComponent v-model="prjctCntnt" />
    </div>
      
      
      <!-- 프로젝트 공고 시작일 ~ 마감일까지 기간 및 라디오 박스 생성 클릭한 일자들을 선정해서 인터뷰 일정에 저장 -->
      
      <div class="row">
           <div class="col-md-6 mb-3">
             <label class="form-label">프로젝트 공고 시작일</label>
             <input type="date" v-model="prjctStrtDate" class="form-control">
           </div>
           <div class="col-md-6 mb-3">
             <label class="form-label">프로젝트 공고 마감일</label>
             <input type="date" v-model="prjctEndDate" class="form-control">
            </div>
          </div>
          
          <!-- 날짜 선택 -->
          <div v-if="availableDates.length">
            <label class="form-label">인터뷰 제외 날짜 선택</label>
            <select v-model="selectedDate" class="form-select">
              <option v-for="date in availableDates" :key="date" :value="date">
                {{ date }}
              </option>
            </select>
          </div>
          
          <!-- 선택된 날짜의 시간대 라디오 버튼 -->
          <div v-if="selectedDate">
            <h5 class="mt-3">{{ selectedDate }} 인터뷰 제외 시간 선택(날짜별 인터뷰 불가능 시간대)</h5>
            <div class="d-flex flex-wrap">
              <div v-for="time in times" :key="time" class="form-check me-3">
                <input
                type="radio"
                :id="`${selectedDate}-${time}`"
                :value="time"
                v-model="selectedTime"
                :disabled="isTimeExcluded(time)"
                class="form-check-input"
                />
                <label :for="`${selectedDate}-${time}`" class="form-check-label" :class="{ 'text-muted': isTimeExcluded(time) }">
                  {{ time }} <span v-if="isTimeExcluded(time)"></span>
               </label>
             </div>
            </div>
          </div>
          
          
          <!-- 선택한 시간 임시 저장 -->
          <button @click="addExcludedTime" class="btn btn-secondary mt-3">저장</button>
          
          <!-- 임시 저장된 제외 시간 목록 -->
          <div v-if="tempExcludedTimes.length">
            <h5 class="mt-4">저장된 인터뷰 제외 시간</h5>
            <ul class="list-group">
              <li v-for="(item, index) in tempExcludedTimes" :key="index" class="list-group-item d-flex justify-content-between">
                <span>{{ item.date }} - {{ item.time }}</span>
                <button @click="removeExcludedTime(index)" class="btn btn-danger btn-sm">X</button>
              </li>
            </ul>
          </div>
          

         
         <div>
           <!-- 프로젝트 시작일 & 마감일 설정 -->
           
           
           <div class="checkbox-container" style="margin-top: 50px;">
             <label>
               <input type="checkbox" v-model="isChecked" style="margin-top: 10px; margin-bottom: 20px;" />
               필수 스킬을 보유한 회원들에게 메일을 발송합니다.
              </label>
              
              
              
              <div>
                <button @click="submitPost">공고 등록하기</button>
              </div>
            </div>
          </div>
</div>
</div>




  </section>
</template>


<script setup>



import { ref, computed, onMounted, watch } from 'vue';
import { api } from '@/axios.js';
// import { useRouter } from 'vue-router';
import QuillEditorComponent from '@/components/common/Editor.vue';
import dayjs from "dayjs"; // 날짜 계산을 쉽게 하기 위한 라이브러리
import duration from "dayjs/plugin/duration";
import store from '../../../../store';
import { showAlert } from '../../../../utill/utillModal';
import SkillsResume from '../../../../components/fo/enterprise/resume/SkillsResume.vue';
import UseSkillsResume from '../../../../components/fo/enterprise/resume/UseSkillsResume.vue';

dayjs.extend(duration);

const areas = ref([]);
const jobs = ref([]);


// const jbpSq = ref(0);
const entrprsSq = store.getters.getMember.pk;
const prjctTtl = ref('');
const prjctTpy = ref("");
const prjctPrdstrd = ref("");
const prjctPrdend = ref("");
const prjctEsntlCrr = ref("");
const prjctCntnt = ref("");


// 기술들 문자열 저장
const formattedSkills = ref(""); // 선택된 기술들을 문자열로 저장
const formattedUseSkills = ref(""); // 선택된 기술들을 문자열로 저장

// 기술 모달 표시 여부
const showSkillsModal = ref(false);
const showUseSkillsModal = ref(false);

// 기술
const skillsData = ref([]);
const useSkillsData = ref([]);


// 메일 수신 여부
const isChecked = ref(false); // 기본값: false



// 선택된 날짜
const selectedDate = ref("");

// 선택 가능한 시간 리스트
const times = ref(["09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00"]);

// 제외할 시간 목록 (DB에서 가져오는 데이터)
const excludedTimes = ref({});

// 임시 저장 목록 (사용자가 선택한 데이터)
const tempExcludedTimes = ref([]);

// 선택된 시간
const selectedTime = ref(null);

// 프로젝트 기간 내 날짜 리스트 (주말 제외)
const availableDates = computed(() => {
  if (!prjctStrtDate.value || !prjctEndDate.value) return [];

  const start = new Date(prjctStrtDate.value);
  const end = new Date(prjctEndDate.value);
  const dates = [];


  while (start <= end) {
    const dayOfWeek = start.getDay(); // 0: 일요일, 6: 토요일
    if (dayOfWeek !== 0 && dayOfWeek !== 6) {
      // 주말 제외 (일요일, 토요일)
      const formattedDate = start.toISOString().split("T")[0]; // YYYY-MM-DD 형식
      dates.push(formattedDate);
    }
    start.setDate(start.getDate() + 1);
  }

  return dates;
});


// 특정 날짜에 대한 제외 시간 확인
const isTimeExcluded = (time) => {
  return (
    excludedTimes.value[selectedDate.value]?.includes(time) || 
    tempExcludedTimes.value.some(item => item.date === selectedDate.value && item.time === time)
  );
};

// 선택한 시간 임시 저장
const addExcludedTime = () => {
  if (!selectedDate.value || !selectedTime.value) {
    alert("날짜와 시간을 선택해주세요.");
    return;
  }

  // 중복 방지
  if (isTimeExcluded(selectedTime.value)) {
    alert("이미 선택된 시간입니다.");
    return;
  }

  tempExcludedTimes.value.push({
    date: selectedDate.value,
    time: selectedTime.value,
  });

  selectedTime.value = null; // 선택 초기화
};

// 선택한 시간 삭제
const removeExcludedTime = (index) => {
  tempExcludedTimes.value.splice(index, 1);
};




watch(skillsData, (newSkills) => {
  console.log("Updated skillsData:", newSkills); // 디버깅용 콘솔 출력
  if (Array.isArray(newSkills)) {
    console.log("sdfdgffdg" + formattedSkills.value);

    formattedSkills.value = newSkills.map(skill => skill.sklScName).join(", ");
  } else {
    console.log("sdfdgffdg" + formattedSkills.value);
    formattedSkills.value = ""; // 배열이 아닐 경우 초기화
  }
}, { deep: true });

const updateSkillsData = (newSkills) => {
  const sk = Object.values(newSkills).flat();
  if (Array.isArray(sk)) {
    skillsData.value = [...sk]; // ✅ 배열일 경우만 업데이트
  } else {
    console.error("updateSkillsData received a non-array value:", sk);
  }
};


watch(useSkillsData, (newSkills) => {
  if (Array.isArray(newSkills)) {


    formattedUseSkills.value = newSkills.map(skill => skill.sklScName).join(", ");
  } else {
    formattedUseSkills.value = ""; // 배열이 아닐 경우 초기화
  }
}, { deep: true });


const updateUseSkillsData = (newSkills) => {
  const sk = Object.values(newSkills).flat();
  if (Array.isArray(sk)) {
    useSkillsData.value = [...sk]; // ✅ 배열일 경우만 업데이트
  } else {
    console.error("updateSkillsData received a non-array value:", sk);
  }
};



// 스킬
const openSkillsModal = () => {
  showSkillsModal.value = true;
}; // openSkillsModal
const openUseSkillsModal = () => {
  showUseSkillsModal.value = true;
}; // openSkillsModal

// 시작일 지정시 마감일은 시작일 이후만 지정가능
document.addEventListener("DOMContentLoaded", function () {
  const startInput = document.getElementById("prjctPrdstrd");
  const endInput = document.getElementById("prjctPrdend");

  startInput.addEventListener("change", function () {
    if (startInput.value) {
      const startDate = new Date(startInput.value);
      startDate.setMinutes(startDate.getMinutes() + 1); // 시작일 이후로 설정

      endInput.min = formatDateTime(startDate); // 마감일 최소값 설정

      // 기존 마감일이 최소값보다 작다면 업데이트
      if (endInput.value && new Date(endInput.value) < startDate) {
        endInput.value = formatDateTime(startDate);
      }
    }
  });

  function formatDateTime(date) {
    const yyyy = date.getFullYear();
    const mm = String(date.getMonth() + 1).padStart(2, "0");
    const dd = String(date.getDate()).padStart(2, "0");
    const hh = String(date.getHours()).padStart(2, "0");
    const mi = String(date.getMinutes()).padStart(2, "0");
    return `${yyyy}-${mm}-${dd}T${hh}:${mi}`;
  }
});



document.addEventListener("DOMContentLoaded", function () {
  const startInput = document.getElementById("prjctStrtDate");
  const endInput = document.getElementById("prjctEndDate");

  startInput.addEventListener("change", function () {
    if (startInput.value) {
      const startDate = new Date(startInput.value);
      startDate.setMinutes(startDate.getMinutes() + 1); // 시작일 이후로 설정

      endInput.min = formatDateTime(startDate); // 마감일 최소값 설정

      // 기존 마감일이 최소값보다 작다면 업데이트
      if (endInput.value && new Date(endInput.value) < startDate) {
        endInput.value = formatDateTime(startDate);
      }
    }
  });

  function formatDateTime(date) {
    const yyyy = date.getFullYear();
    const mm = String(date.getMonth() + 1).padStart(2, "0");
    const dd = String(date.getDate()).padStart(2, "0");
    const hh = String(date.getHours()).padStart(2, "0");
    const mi = String(date.getMinutes()).padStart(2, "0");
    return `${yyyy}-${mm}-${dd}T${hh}:${mi}`;
  }
});




// const interviewAgreement = ref(false);
// const router = useRouter();

// 현재 날짜 이전은 선택 불가
const minRegstrStrtDtm = computed(() => {
  const today = new Date();
  const year = today.getFullYear();
  const month = String(today.getMonth() + 1).padStart(2, '0');
  const day = String(today.getDate()).padStart(2, '0');
  const hours = String(today.getHours()).padStart(2, '0');
  const minutes = String(today.getMinutes()).padStart(2, '0');
  return `${year}-${month}-${day}T${hours}:${minutes}`; // YYYY-MM-DDTHH:MM 형식
});

const fetchAreasAndJobs = async () => {
  try {
    const areasResponse = await api.$get('/areas');
    areas.value = areasResponse;
    console.log(areasResponse);

    const jobsResponse = await api.$get('/jobs');
    jobs.value = jobsResponse;
    console.log(jobsResponse);

  } catch (error) {
    console.error('데이터를 가져오는 중 오류가 발생했습니다.', error);
  }
};


onMounted(() => {
  console.log('Component mounted');
  fetchAreasAndJobs();
});
const selectedprjctWork = ref("");
const selectedprjctLctn = ref("");
const prjctStrtDate = ref("");
const prjctEndDate = ref("");




// ✅ 날짜를 `YYYY-MM-DD` 형식으로 변환
const formatDate = (date) => (date ? dayjs(date).format("YYYY-MM-DD") : "");

// ✅ 개월 수 계산 후 문자열 생성
const prjctPrd = computed(() => {
  if (!prjctPrdstrd.value || !prjctPrdend.value) return "";

  const startDate = dayjs(prjctPrdstrd.value);
  const endDate = dayjs(prjctPrdend.value);
  const diffDays = endDate.diff(startDate, "day"); // 전체 일수 차이
  const diffMonths = endDate.diff(startDate, "month"); // 개월 수

  let durationText = "";
  if (diffMonths > 0) {
    durationText = `${diffMonths}개월 `;
  }
  if (diffDays % 30 > 0) {
    durationText += `${diffDays % 30}일`;
  }

  return `${formatDate(prjctPrdstrd.value)} ~ ${formatDate(prjctPrdend.value)} (${durationText.trim()})`;
});


const submitPost = () => {
  // if (prjctTtl.value.trim() === '' || jbpCntnt.value.trim() === '') {
  //   alert('제목과 내용을 확인하세요.');
  //   return;
  // }
  console.table(tempExcludedTimes.value);

  const formattedInterviews = tempExcludedTimes.value.map(item => 
  `${item.date} ${item.time}`
);


  api.$post('/project', {
    entrprsSq: entrprsSq,
    prjctTtl: prjctTtl.value,
    prjctTpy: prjctTpy.value,
    prjctWork: selectedprjctWork.value,
    prjctUseSkl: formattedUseSkills.value,
    prjctEsntlSkl: formattedSkills.value,
    prjctEsntlCrr: prjctEsntlCrr.value,
    prjctStrtDate: prjctStrtDate.value.split('T')[0],
    prjctEndDate: prjctEndDate.value.split('T')[0],
    prjctPrd: prjctPrd.value,
    prjctLctn: selectedprjctLctn.value,
    check: isChecked.value,
    prjctCntnt: prjctCntnt.value,
    prjctInterv: formattedInterviews

  })

    .then(response => {
      console.log("📌 서버 응답 데이터:", response.data);
      showAlert('프로젝트 공고가 등록되었습니다.');

    })
    .catch(error => {
      console.error("🚨 API 요청 실패:", error);
    });
};

</script>

<style>
.form-control {
  background-color: #f9f9f9;
  min-width: 160px;
  box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
  max-height: 200px;
  /* 드롭다운의 최대 높이 설정 */
  overflow-y: auto;
  /* 세로 스크롤 처리 */
}

/* Quill 에디터 스타일 수정 */
.quill-editor .ql-container {
  background-color: #f9f9f9;
  min-width: 160px;
  box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
  height: 200px;
}

.quill-editor .ql-editor {
  min-height: 200px;
  max-height: 400px;
  /* 에디터의 최대 높이 설정 */
  overflow-y: auto;
  /* 세로 스크롤 처리 */
}

/* ✅ 부모 컨테이너를 화면 중앙 정렬 */
.container {
 
}

/* ✅ 체크박스와 버튼을 감싸는 컨테이너 */
.checkbox-container {
  text-align: center;
  /* 내부 텍스트 중앙 정렬 */
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 10px;
  box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.1);
}
</style>
