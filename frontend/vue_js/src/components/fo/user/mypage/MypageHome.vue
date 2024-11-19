<template>
  <div v-if="result && Object.keys(result).length > 0">
    <!-- 첫 번째 섹션 (이미지 및 기본 정보) -->
    <div
      class="row"
      style="padding-bottom: 20px; border-bottom: 1px solid #eaeaea"
    >
      <div class="col-md-3 d-flex align-items-center justify-content-center">
        <div class="thumb-info-side-image-wrapper">
          <img
            src="@/assets/profile.webp"
            class="img-fluid"
            alt=""
            style="width: 140px"
          />
        </div>
      </div>
      <div class="col-md-9">
        <div class="card mb-3">
          <div class="card-body">
            <h2 class="font-weight-bold" style="margin-bottom: 10px">
              {{ result.rsmInfo.rsm_ttl }}
            </h2>
            <p>
              - 최종 수정일자 : {{ formatDateYMD(result.rsmInfo.updt_dtm) }}
            </p>
            <!--
            <p>
              - 포지션 수락 여부 :
              {{
                result.mbrInfo.pstn_prpsl_accept_yn === "Y" ? "수락" : "거절"
              }}
            </p>-->
            <div class="row" style="display: flex; align-items: center">
              <div class="col" style="white-space: nowrap">
                이름 : <strong>{{ result.mbrInfo.mbr_name }}</strong>
              </div>
              <div class="col" style="white-space: nowrap">
                연락처 : <strong>{{ result.mbrInfo.mbr_mp }}</strong>
              </div>
              <div class="col" style="white-space: nowrap">
                이메일 : <strong>{{ result.mbrInfo.mbr_eml_adrs }}</strong>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 두 번째 섹션 (이력서 및 공고 관련 데이터) -->
    <div class="row" style="padding-top: 20px">
      <div class="col-md-4">
        <div class="card mb-3">
          <div class="card-body text-center">
            <h5 class="card-title"><strong>등록한 이력서</strong></h5>
            <p class="card-text">{{ result.myState.rsm_cnt }}</p>
          </div>
        </div>
      </div>

      <div class="col-md-4">
        <div class="card mb-3">
          <div class="card-body text-center">
            <h5 class="card-title"><strong>스크랩 공고</strong></h5>
            <p class="card-text">{{ result.myState.scrap_cnt }}</p>
          </div>
        </div>
      </div>
      <div class="col-md-4">
        <div class="card mb-3">
          <div class="card-body text-center">
            <h5 class="card-title"><strong>받은 제안</strong></h5>
            <p class="card-text">{{ result.myState.prpsl_cnt }}</p>
          </div>
        </div>
      </div>
    </div>
    <br />
    <div>
      <!-- 조회수 높은 채용 공고 -->
      <h3 style="margin-bottom: 20px">
        지금 가장 인기있는 <strong>조회수 높은</strong> 공고예요.
      </h3>
      <div class="card-body text-center">
        <div v-if="topJobPostings.length > 0">
          <div class="top-job-posting-container">
            <!-- 각 채용 공고마다 router-link를 적용하여 카드 전체를 클릭 가능하게 만듬 -->
            <div
              v-for="jobPosting in topJobPostings"
              :key="jobPosting.jbpSq"
              class="top-job-posting-card"
            >
              <!-- router-link를 카드 전체에 적용 -->
              <router-link
                :to="'/board/detail/jobPosting/' + jobPosting.jbpSq"
                class="text-decoration-none"
              >
                <h3 style="color: black">{{ jobPosting.enterpriseName }}</h3>
                <p style="color: black">{{ jobPosting.jbpTtl }}</p>
                <p>{{ jobPosting.jobName.join(", ") }}</p>
                <!--<p>{{ jobPosting.hits }} 조회수</p>-->
                <p>{{ jobPosting.jbpDesc }}</p>
              </router-link>
            </div>
          </div>
        </div>
        <div v-else>
          <p>조회수 높은 채용 공고가 없습니다.</p>
        </div>
      </div>
    </div>

    <!-- 세 번째 섹션 (지원 상태 카드) -->
    <div
      class="row"
      style="padding-top: 20px; border-bottom: 1px solid #eaeaea"
    >
      <div class="col">
        <div class="card">
          <div class="card-body">
            <h5 class="card-title mb-4">지원 상태</h5>

            <!-- 지원 상태 항목들 -->
            <div class="row">
              <!-- 전체 항목 -->
              <div class="col-md-2 mb-3">
                <div class="status-card">
                  <strong>전체</strong>
                  <p class="mb-0">{{ applyStateSum }}</p>
                </div>
              </div>

              <!-- 진행중 항목 -->
              <div class="col-md-2 mb-3">
                <div class="status-card">
                  <strong>진행중</strong>
                  <p class="mb-0">
                    열람: {{ result.EachCndtnApplyCount.cndtn_read }}
                  </p>
                  <p class="mb-0">
                    미열람: {{ result.EachCndtnApplyCount.cndtn_unrd }}
                  </p>
                </div>
              </div>

              <!-- 면접 항목 -->
              <div class="col-md-2 mb-3">
                <div class="status-card">
                  <strong>면접</strong>
                  <p class="mb-0">
                    대기: {{ result.EachCndtnApplyCount.cndtn_iw }}
                  </p>
                  <p class="mb-0">
                    완료: {{ result.EachCndtnApplyCount.cndtn_ic }}
                  </p>
                </div>
              </div>

              <!-- 불합격 항목 -->
              <div class="col-md-2 mb-3">
                <div class="status-card">
                  <strong>불합격</strong>
                  <p class="mb-0">
                    {{ result.EachCndtnApplyCount.cndtn_pass }}
                  </p>
                </div>
              </div>

              <!-- 합격 항목 -->
              <div class="col-md-2 mb-3">
                <div class="status-card">
                  <strong>합격</strong>
                  <p class="mb-0">{{ result.EachCndtnApplyCount.cndtn_np }}</p>
                </div>
              </div>

              <!-- 마감 항목 -->
              <div class="col-md-2 mb-3">
                <div class="status-card">
                  <strong>마감</strong>
                  <p class="mb-0">(준비중)</p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 네 번째 섹션 (캘린더) -->
    <div class="row" style="padding-top: 20px">
      <Calendar
        :events="calendarEvents"
        :customButtons="customButtons"
        ref="fc"
      />
    </div>
  </div>

  <div v-else>
    <!-- 데이터가 없을 때 기본 메시지 및 이미지 표시 -->
    <div
      class="row"
      style="padding-bottom: 20px; border-bottom: 1px solid #eaeaea"
    >
      <div class="col-md-3 d-flex align-items-center justify-content-center">
        <div class="thumb-info-side-image-wrapper">
          <img
            src="@/assets/avatar.jpg"
            class="img-fluid rounded-circle mb-4"
            alt=""
            style="width: 140px"
          />
        </div>
      </div>
      <div class="col-md-9">
        <div class="card mb-3">
          <div class="card-body">
            <h2 class="font-weight-bold" style="margin-bottom: 10px">
              대표 이력서를 등록해주세요.
            </h2>
          </div>
        </div>
      </div>
    </div>

    <!-- 두 번째 섹션 (빈 값으로 표시) -->
    <div class="row" style="padding-top: 20px">
      <div class="col-md-3">
        <div class="card mb-3">
          <div class="card-body text-center">
            <h5 class="card-title">등록한 이력서</h5>
            <p class="card-text">0</p>
          </div>
        </div>
      </div>
      <div class="col-md-3">
        <div class="card mb-3">
          <div class="card-body text-center">
            <h5 class="card-title">추천 공고</h5>
            <p class="card-text">(로직 준비중)</p>
          </div>
        </div>
      </div>
      <div class="col-md-3">
        <div class="card mb-3">
          <div class="card-body text-center">
            <h5 class="card-title">스크랩 공고</h5>
            <p class="card-text">0</p>
          </div>
        </div>
      </div>
      <div class="col-md-3">
        <div class="card mb-3">
          <div class="card-body text-center">
            <h5 class="card-title">받은 제안</h5>
            <p class="card-text">0</p>
          </div>
        </div>
      </div>
    </div>

    <!-- 세 번째 섹션 (빈 값으로 표시) -->
    <div
      class="row"
      style="padding-top: 20px; border-bottom: 1px solid #eaeaea"
    >
      <div class="col">
        <div class="card">
          <div class="card-body">
            <h5 class="card-title">지원 상태</h5>
            <div class="row">
              <div class="col-md-3">
                <strong>전체</strong>
                <p>0</p>
              </div>
              <div class="col-md-2">
                <strong>진행중</strong>
                <p>열람: 0</p>
                <p>미열람: 0</p>
              </div>
              <div class="col-md-2">
                <strong>면접</strong>
                <p>대기: 0</p>
                <p>완료: 0</p>
              </div>
              <div class="col-md-2">
                <strong>불합격</strong>
                <p>0</p>
              </div>
              <div class="col-md-2">
                <strong>합격</strong>
                <p>0</p>
              </div>
              <div class="col-md-1">
                <strong>마감</strong>
                <p>(준비중)</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 네 번째 섹션 (캘린더) -->
    <div class="row" style="padding-top: 20px">
      <Calendar
        :events="calendarEvents"
        :customButtons="customButtons"
        ref="fc"
      />
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref, computed } from "vue";
import Calendar from "./MypageCalendar.vue";
import { api } from "@/axios";
import { formatDateYMD } from "@/tools";
import { useStore } from "vuex";
const store = useStore();
let topJobPostings = ref([]); // 조회수 높은 3개의 채용 공고를 저장
let result = ref({});
let applyStateSum = computed(() => getApplyStateSum());
let calendarEvents = computed(() =>
  makeCalendarDatas(result.value.calendarData)
);
let fc = ref(null);
let month = new Date().getMonth() + 1;
let checkedMonth = [month];

onMounted(async () => {
  // Fetching My Page Data
  try {
    const response = await api.$get("/user/mypage/", {
      params: {
        mbr_sq: store.state.member.mbrSq, // Passing member ID from Vuex
        month: month, // Passing the current month
      },
    });
    result.value = response || {}; // Store the result or an empty object if no response
  } catch (error) {
    console.error("API 호출 오류:", error);
    result.value = {}; // Set empty object in case of an error
  }

  // Fetching Top 3 Job Postings
  try {
    const jobPostingResponse = await api.$get("/user/mypage/top");
    topJobPostings.value = jobPostingResponse || []; // Set the top job postings
  } catch (error) {
    console.error("채용 공고 조회 오류:", error);
    topJobPostings.value = []; // Set empty array in case of an error
  }
  return {
    result,
    topJobPostings,
    month, // if you need to display or use month
  };
});

function getApplyStateSum() {
  if (!result.value || !result.value.EachCndtnApplyCount) {
    return 0;
  }

  let sum = 0;
  for (const key in result.value.EachCndtnApplyCount) {
    sum += result.value.EachCndtnApplyCount[key];
  }
  return sum;
}

function makeCalendarDatas(toParsingData) {
  if (!toParsingData) {
    return [];
  }

  let events = [];

  for (const temp of toParsingData) {
    let event = {
      id: "",
      title: "",
      date: "",
      backgroundColor: "",
      description: "",
    };

    event.id = temp.jbp_sq;
    switch (temp.tbl_type) {
      case "S":
        event.title = "스크랩공고마감";
        event.backgroundColor = "#d3d3d3";
        break;
      case "I":
        event.title = "면접";
        event.backgroundColor = "#add8e6";
        break;
      case "PP":
        event.title = "제안받은공고마감";
        event.backgroundColor = "#f08080";
        break;
      case "AC":
        event.title = "지원공고";
        event.backgroundColor = "#87cefa";
        break;
    }
    event.date = temp.dtm;
    event.description = temp.jbp_ttl;

    events.push(event);
  }

  return events;
}

// fullcalendar 커스텀버튼
let customButtons = {
  myPrev: {
    text: "<",
    click: function () {
      fc.value.calendar.getApi().prev();
      month--;
      fetchCalendarData();
    },
  },
  myNext: {
    text: ">",
    click: function () {
      fc.value.calendar.getApi().next();
      month++;
      fetchCalendarData();
    },
  },
};
// 달력 월 이동시 해당 월 데이터 가져오기
async function fetchCalendarData() {
  if (!checkedMonth.includes(month)) {
    const selectedMonthData = await api.$get("/user/mypage/calendar", {
      params: { month: month },
    });
    console.log("진입");
    for (const event of makeCalendarDatas(selectedMonthData)) {
      fc.value.calendar.getApi().addEvent(event);
    }
  }
  checkedMonth.push(month);
}
</script>

<style>
/* 카드의 기본 스타일 */
.card {
  border: 1px solid #eaeaea;
  border-radius: 8px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

/* 각 상태 항목 카드 스타일 */
.status-card {
  background-color: #f9f9f9;
  padding: 20px; /* 패딩을 더 크게 해서 콘텐츠를 감쌈 */
  border-radius: 5px;
  text-align: center;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  transition: background-color 0.3s ease;

  /* 고정 크기 */
  width: 100px; /* 고정된 너비 */
  height: 100px; /* 고정된 높이 */
  padding: 5%;

  /* 텍스트가 넘치지 않도록 처리 */
  display: flex;
  flex-direction: column;
  justify-content: center; /* 세로 방향으로 텍스트를 가운데 정렬 */
  align-items: center; /* 가로 방향으로 텍스트를 가운데 정렬 */
  overflow: hidden; /* 콘텐츠가 넘칠 경우 숨김 */
}

/* 카드 제목 스타일 */
.card-title {
  font-weight: bold;
  font-size: 1.25rem;
}
h5.card-title {
  margin-left: 5px;
}

/* 조회수 높은 채용 공고 카드들을 가로로 나열하는 컨테이너 */
.top-job-posting-container {
  display: flex;
  justify-content: flex-start; /* 카드들을 왼쪽 정렬 */
  gap: 20px; /* 카드들 간의 간격 */
  flex-wrap: wrap; /* 화면 크기에 따라 카드들이 자동으로 줄 바꿈 */
  padding-bottom: 10px;
}

/* 조회수 높은 채용 공고 카드 스타일 */
.top-job-posting-card {
  background-color: #fff;
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 15px;
  width: 250px; /* 카드 너비를 고정 설정 */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  text-align: center; /* 카드 내 텍스트 가운데 정렬 */
  display: flex;
  flex-direction: column; /* 세로로 배치 */
  justify-content: space-between;
}
/* router-link로 감싼 카드에 대한 기본 스타일 */
.router-link-active {
  text-decoration: none;
}
/* 카드 호버 시 효과 */
.top-job-posting-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
}

/* 카드 내 제목 스타일 */
.top-job-posting-card h3 {
  font-size: 1.1rem;
  font-weight: 600;
  margin: 10px 0;
  word-wrap: break-word; /* 긴 제목이 자동으로 줄 바꿈되도록 */
  text-overflow: ellipsis; /* 제목이 길 경우 '...'으로 표시 */
  overflow: hidden;
}

/* 조회수 스타일 */
.top-job-posting-card p {
  font-size: 0.9rem;
  color: #555;
  margin: 5px 0;
  display: block; /* p 태그를 block으로 설정 */
}

/* 작은 화면에서 카드 크기 조정 */
@media (max-width: 767px) {
  /* 작은 화면에서 .top-job-posting-card의 너비를 줄여서 한 줄에 여러 카드가 들어가도록 설정 */
  .top-job-posting-card {
    width: 180px; /* 작은 화면에서 너비를 줄임 */
  }
}
</style>
