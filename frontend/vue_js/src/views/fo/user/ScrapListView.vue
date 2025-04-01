<template>
  <div class="heading heading-border heading-middle-border" style="margin-bottom: 50px;">
    <h1 class="font-weight-normal">스크랩 공고<strong class="font-weight-extra-bold"></strong></h1>
  </div>
  <div class="row align-items-center d-flex mb-3">
    <div class="btn-group form-group col-md-5 me-auto p-2" role="group" aria-label="Basic radio toggle button group">
      <input type="radio" class="btn-check" name="btnradio" id="btnradio1" autocomplete="off" checked
        @click="setFilter('organization')">
      <label class="btn btn-outline-primary" for="btnradio1">소속 채용</label>
      <input type="radio" class="btn-check" name="btnradio" id="btnradio2" autocomplete="off"
        @click="setFilter('project')">
      <label class="btn btn-outline-primary" for="btnradio2">프로젝트 채용</label>
    </div>
    <div class="form-group col-md-2 p-2">
      <select class="form-select form-control h-auto py-2" @change="changeSort($event)">
        <option value="desc">스크랩일순</option>
        <option value="asc">등록일순</option>
      </select>
    </div>
    <div class="form-group col-md-3 p-2">
      <div class="search-with-select">
        <div class="search-form-wrapper input-group" style="margin-left: 30px;">
          <input class="form-control text-1" type="search" v-model="search" placeholder="검색창(제목+내용)" />
          <div class="search-form-select-wrapper">
            <button class="btn" type="button" aria-label="Search" @click="ScrapSearch">
              <i class="icons icon-magnifier header-nav-top-icon text-color-dark"></i>
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>

  <!-- 기업 스크랩 -->
  <div class="row" v-if="activeFilter === 'organization'">
    <!-- 자료없을때 예외 -->
    <div v-if="!hasScrapData">
      <strong class="font-weight-extra-bold"> 자료가 없습니다. </strong>
    </div>
    <!-- 자료있을때 for -->
    <div v-else>
      <div v-for="jobPostingData in scrapListData.jobPostingData" :key="jobPostingData.jbp_sq">
        <MypageScrap :jobPostingData="jobPostingData" @scrap-apy="scrapApy"
          @update:scrapped="(newValue) => jobPostingData.scrapped = newValue" v-if="jobPostingData.scrapped" />
      </div>
    </div>
  </div>

  <!-- 프로젝트 스크랩 -->
  <div class="row" v-if="activeFilter === 'project'">
    <!-- 자료없을때 예외 -->
    <div v-if="scrapListData.scrapDatas.length == 0">
      <strong class="font-weight-extra-bold"> 자료가 없습니다. </strong>
    </div>
    <!-- 자료있을때 for -->
    <div v-else>
      <div v-for="projectPostingData in scrapListData.projectPostingData" :key="projectPostingData.prjctSq">
        <MypageScrapProject :projectPostingData="projectPostingData" />
      </div>
    </div>
  </div>
  <br />
  <div class="row" id="pagination-container">
    <div class="pagination-wrapper" v-if="scrapListData.paginationData.totalDataCount > 1">
      <PaginationData :paginationData="scrapListData.paginationData" @change-page-no="changePageNo" />
    </div>
  </div>
</template>

<script setup>
import PaginationData from "@/components/fo/enterprise/common/PaginationData.vue";
import MypageScrap from "../../../components/fo/user/mypage/MypageScrap.vue";
import { api } from '@/axios';

import { onMounted, ref, computed } from "vue";
import { useStore } from "vuex";
import MypageScrapProject from "../../../components/fo/user/mypage/MypageScrapProject.vue";

const store = useStore();

const scrapListData = ref({
  scrapDatas: [],
  paginationData: {},
  searchData: {},
  jobPostingData: {},
  projectPostingData: {},
});

const member = computed(() => store.getters.getMember);
const isLoggedIn = computed(() => member.value !== null);

const activeFilter = ref('organization');

function setFilter(filter) {
  // console.log('필터버튼클릭');
  this.activeFilter = filter;
  if (filter === "organization") {
    callAxios();
  }
  if (filter === "project") {
    fetchProjectScrap();
  }
  // console.log('현재 필터는?', this.activeFilter);
}

onMounted(() => {
  if (isLoggedIn.value && member.value) {
    scrapListData.value.searchData = {
      mbr_sq: member.value.mbrSq, // member.value로 접근
      sort: "desc",
      pageNo: 1
    };
    callAxios();
  }
});

// axios 함수 (프로젝트용)
const fetchProjectScrap = async () => {
  // 리스트 뿌려주는 기본 axios
  await api.$get("/user/mypage/scrap/project-scrapList-list/" + scrapListData.value.searchData.mbr_sq + "/" + scrapListData.value.searchData.sort + "/" + scrapListData.value.searchData.pageNo)
    .then((success) => {
      console.log('axios 성공', scrapListData.value);
      scrapListData.value = success;
      console.log("success : ", success);
    })
    .catch((error) => {
      console.log('axios 실패' + error);

    });
};

// axios 함수
const callAxios = async () => {
  // 리스트 뿌려주는 기본 axios
  await api.$get("/user/mypage/scrap/scrapList-list/" + scrapListData.value.searchData.mbr_sq + "/" + scrapListData.value.searchData.sort + "/" + scrapListData.value.searchData.pageNo)
    .then((success) => {
      console.log('axios 성공', scrapListData.value);
      scrapListData.value = success;
      console.log("success : ", success);
    })
    .catch((error) => {
      console.log('axios 실패' + error);

    });
};

const hasScrapData = computed(() => {
  if (!scrapListData.value.jobPostingData) return false;
  return Object.values(scrapListData.value.jobPostingData).some(item => item.scrapped);
})

// 스크랩 공고에 지원
const scrapApy = async (emit) => {
  const resumeId = prompt('이력서 번호를 입력하세요:');
  if (resumeId) {
    insertApply(resumeId, emit);
  }
};

const insertApply = async (resumeId, emit) => {

  const applyData = {
    resume: { rsmSq: resumeId },
    jobPosting: { jbpSq: emit },
    apyDtm: new Date().toISOString()
  };
  try {
    const response = await api.$post('/apply/insert', applyData);
    alert(response);
  } catch (error) {
    // 서버에서 보낸 오류 메시지를 활용
    if (error.response) {
      const errorMessage = error.response;

      if (error.response.status === 404) {
        alert("이력서 찾을 수 없음");
      } else if (error.response.status === 400) {
        alert("이미 지원한 이력서입니다.");
      } else {
        alert('입사지원을 처리하는 중 오류가 발생했습니다: ' + errorMessage);
      }
    } else {
      console.error('Error applying job:', error);
      alert('입사지원을 처리하는 중 오류가 발생했습니다.');
    }
  }
};

// 이벤트 함수
// 페이지네이션 페이지 변경 클릭
const changePageNo = (emit) => {
  if (activeFilter.value === "organization") {
    scrapListData.value.searchData.pageNo = emit;
    callAxios();
  }
  if (activeFilter.value === "project") {
    scrapListData.value.searchData.pageNo = emit;
    fetchProjectScrap();
  }
};

// 정렬 select 변경
const changeSort = (event) => {
  scrapListData.value.searchData.sort = event.target.value;
  scrapListData.value.searchData.pageNo = 1;
  callAxios();

};

</script>

<style scoped>
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