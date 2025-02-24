<template>
    <section id="examples" class="section section-default">
        <div class="container">
            <section class="card card-admin">
                <header class="card-header">
                    <h2 class="card-title">공고 수정</h2>
                </header>
            </section>

            <div class="mb-3">
                <label for="jbpTl" class="form-label">제목</label>
                <input type="text" v-model="jbpTl" class="form-control" id="jbpTl" placeholder="제목을 입력하세요">
            </div>

            <div class="row">
                <div class="col-md-3 mb-3">
          <label for="jobName" class="form-label">직업명</label>
          <div id="jobName" class="form-control" style="height: auto;">
            <div v-for="job in jobs" :key="job.jobSq" class="form-check">
              <input type="checkbox" v-model="selectedJobs" :value="job" class="form-check-input" :id="'job-' + job.jobSq">
              <label class="form-check-label" :for="'job-' + job.jobSq">{{ job.jobScName }}</label>
            </div>
          </div>
        </div>
                <div class="col-md-3 mb-3">
                    <label for="cr" class="form-label">경력</label>
                    <select v-model="cr" class="form-control" id="cr">
                        <option value="801">경력무관</option>
                        <option value="802">신입</option>
                        <option value="803">경력</option>
                    </select>
                </div>
                <div class="col-md-3 mb-3">
                    <label for="edctn" class="form-label">학력</label>
                    <select v-model="edctn" class="form-control" id="edctn">
                        <option value="600">학력무관</option>
                        <option value="605">고등학교 이상</option>
                        <option value="604">대학교(2,3년제) 이상</option>
                        <option value="603">대학교(4년제) 이상</option>
                        <option value="602">석사</option>
                        <option value="601">박사</option>
                    </select>
                </div>
                <div class="col-md-3 mb-3">
                    <label for="sklName" class="form-label">기술명</label>
                    <input type="text" v-model="sklName" class="form-control" id="sklName" placeholder="기술명을 입력하세요">
                </div>
            </div>

            <div class="row">
                <div class="col-md-3 mb-3">
          <label for="workArea" class="form-label">근무지역</label>
          <div id="workArea" class="form-control" style="height: auto;">
            <div v-for="area in areas" :key="area.areaSq" class="form-check">
              <input type="checkbox" v-model="selectedWorkAreas" :value="area" class="form-check-input" :id="'area-' + area.areaSq">
              <label class="form-check-label" :for="'area-' + area.areaSq">{{ area.areaName }}</label>
            </div>
          </div>
        </div>
                <div class="col-md-2 mb-3">
                    <label for="workForm" class="form-label">근무형태</label>
                    <select v-model="workForm" class="form-control" id="workForm">
                        <option value="정규직">정규직</option>
                        <option value="계약직">계약직</option>
                        <option value="인턴">인턴</option>
                    </select>
                </div>
                <div class="col-md-3 mb-3">
                    <label class="form-label">급여</label>
                    <div class="input-group">
                        <input type="text" v-model="slry" class="form-control" id="slry" placeholder="연봉" :disabled="interviewAgreement">
                        <div class="input-group-text">
                            <input type="checkbox" v-model="interviewAgreement"> 면접 후 협의
                        </div>
                    </div>
                </div>
                <div class="col-md-2 mb-3">
                    <label for="workStartTime" class="form-label">출근 시간</label>
                    <input type="time" v-model="workStartTime" class="form-control" id="workStartTime">
                </div>
                <div class="col-md-2 mb-3">
                    <label for="workEndTime" class="form-label">퇴근 시간</label>
                    <input type="time" v-model="workEndTime" class="form-control" id="workEndTime">
                </div>
            </div>

            <div class="mb-3">
                <label for="jbpCntnt" class="form-label">내용</label>
                <QuillEditorComponent v-model="jbpCntnt" :isReadOnly="false"/>
            </div>
            <div class="row">
                <div class="col-md-6 mb-3">
                    <label for="regstrStrtDtm" class="form-label">공고 시작일</label>
                    <input type="date" v-model="regstrStrtDtm" class="form-control" id="regstrStrtDtm" :min="minRegstrStrtDtm">
                </div>
                <div class="col-md-6 mb-3">
                    <label for="regstrDlnDtm" class="form-label">공고 마감일</label>
                    <input type="date" v-model="regstrDlnDtm" class="form-control" id="regstrDlnDtm" :min="minRegstrStrtDtm">
                </div>
            </div>

            <div class="row">
                <div class="col-md-4 mb-3">
                    <label for="picName" class="form-label">담당자 이름</label>
                    <input type="text" v-model="picName" class="form-control" id="picName" placeholder="담당자 이름을 입력하세요">
                </div>
                <div class="col-md-4 mb-3">
                    <label for="picMp" class="form-label">담당자 연락처</label>
                    <input type="text" v-model="picMp" class="form-control" id="picMp" placeholder="담당자 연락처를 입력하세요">
                </div>
                <div class="col-md-4 mb-3">
                    <label for="picEml" class="form-label">담당자 이메일</label>
                    <input type="email" v-model="picEml" class="form-control" id="picEml" placeholder="담당자 이메일을 입력하세요">
                </div>
            </div>

            <div>
                <button @click="submitPost">작성</button>
            </div>
        </div>
    </section>
</template>

<script setup>
/* eslint-disable */
import { ref, onMounted, isReadonly } from 'vue';
import { api } from '@/axios.js';
import { useRouter, useRoute } from 'vue-router';
import moment from 'moment';
import QuillEditorComponent from '@/components/common/Editor.vue';


const route = useRoute();
const router = useRouter();

const areas = ref([]);
const jobs = ref([]);
const selectedWorkAreas = ref([]);
const selectedJobs = ref([]);

const jbpTl = ref('');
const jbpCntnt = ref('');
const cr = ref(0);
const sklName = ref('');
const jobName = ref('');
const edctn = ref(0);
const workArea = ref('');
const workForm = ref('');
const slry = ref('');
const workStartTime = ref('');
const workEndTime = ref('');
const workHour = ref('');
const picName = ref('');
const picMp = ref('');
const picEml = ref('');
const regstrStrtDtm = ref('');
const regstrDlnDtm = ref('');

const interviewAgreement = ref(false);

const jbpSq = route.params.jbpSq;

const minRegstrStrtDtm = ref(moment().format('YYYY-MM-DD'));

const fetchAreasAndJobs = async () => {
  try {
    const areasResponse = await api.$get('/areas');
    areas.value = areasResponse;

    const jobsResponse = await api.$get('/jobs');
    jobs.value = jobsResponse;

    boardDetail(); 
  } catch (error) {
    console.error('데이터를 가져오는 중 오류가 발생했습니다.', error);
  }
};


onMounted(() => {
  console.log('Component mounted');
  fetchAreasAndJobs();
});

const boardDetail = () => {
    api.$get(`/board/detail/jobPosting/${jbpSq}`)
    .then(response => {
        jbpTl.value = response.jbpTl;
        jbpCntnt.value = response.jbpCntnt;
        cr.value = response.cr;
        sklName.value = response.sklName;
        // 직종 정보
        selectedJobs.value = response.jobName.map(name => {
        return jobs.value.find(job => job.jobScName === name);
      });
        edctn.value = response.edctn;
        // 근무지역 정보
        selectedWorkAreas.value = response.workArea.map(name => {
        return areas.value.find(area => area.areaName === name);
      });
        workForm.value = response.workForm;
        slry.value = response.slry;
        // workHour 분리 후 할당
        if (response.workHour) {
            const [startTime, endTime] = response.workHour.split('~').map(item => item.trim());
            workStartTime.value = startTime;
            workEndTime.value = endTime;
        }
        regstrStrtDtm.value = response.regstrStrtDtm;
        regstrDlnDtm.value = response.regstrDlnDtm;
        picName.value = response.picName;
        picMp.value = response.picMp;
        picEml.value = response.picEml;
    })
    .catch(error => {
        console.error('에러:', error);
    });
}

onMounted(() => {
    boardDetail();
});



const submitPost = () => {
    if (jbpTl.value.trim() === '' || jbpCntnt.value.trim() === '') {
        alert('제목과 내용을 확인하세요.');
        return;
    }

    api.$post(`/board/jobPostingUpdate/${jbpSq}`, {
        jbpSq : jbpSq,
        jbpTl: jbpTl.value,
        jbpCntnt: jbpCntnt.value,
        cr: cr.value,
        sklName: sklName.value,
        jobName: selectedJobs.value.map(job => job.jobScName),
        edctn: edctn.value,
        workArea: selectedWorkAreas.value.map(area => area.areaName),
        workForm: workForm.value,
        slry: interviewAgreement.value ? '면접 후 협의' : slry.value,
        workHour: `${workStartTime.value} ~ ${workEndTime.value}`,
        regstrStrtDtm : regstrStrtDtm.value,
        regstrDlnDtm : regstrDlnDtm.value,
        picName: picName.value,
        picMp: picMp.value,
        picEml: picEml.value,
    })
    .then(response => {
        console.log(response);
        alert('게시물이 수정되었습니다.');

        // 폼 리셋
        jbpTl.value = '';
        jbpCntnt.value = '';
        cr.value = '';
        sklName.value = '';
        jobName.value = '';
        edctn.value = '';
        workArea.value = '';
        workForm.value = '';
        slry.value = '';
        workStartTime.value = '';
        workEndTime.value = '';
        regstrDlnDtm.value = '';
        regstrStrtDtm.value = '';
        picName.value = '';
        picMp.value = '';
        picEml.value = '';
        interviewAgreement.value = false;

        // 페이지 이동
        router.push(`/board/detail/jobPosting/${jbpSq}`); 
    })
    .catch(error => {
        console.error('에러:', error);
    });
}
</script>

