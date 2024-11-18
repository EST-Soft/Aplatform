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
      <div class="col-md-3">
        <div class="card mb-3">
          <div class="card-body text-center">
            <h5 class="card-title"><strong>등록한 이력서</strong></h5>
            <p class="card-text">{{ result.myState.rsm_cnt }}</p>
          </div>
        </div>
      </div>
      <div class="col-md-3">
        <div class="card mb-3">
          <div class="card-body text-center">
            <h5 class="card-title"><strong>스크랩 공고</strong></h5>
            <p class="card-text">{{ result.myState.scrap_cnt }}</p>
          </div>
        </div>
      </div>
      <div class="col-md-3">
        <div class="card mb-3">
          <div class="card-body text-center">
            <h5 class="card-title"><strong>받은 제안</strong></h5>
            <p class="card-text">{{ result.myState.prpsl_cnt }}</p>
          </div>
        </div>
      </div>
    </div>
    <div class="col-md-3">
      <div class="card mb-3">
        <div class="card-body text-center">
          <h5 class="card-title"><strong>추천 공고</strong></h5>
          <!-- 추천된 채용 공고 리스트 -->
          <div v-if="recommendations.value && recommendations.value.length > 0">
            <ul class="list-unstyled">
              <li
                v-for="recommendation in recommendations.value"
                :key="recommendation.jbpSq"
                class="mb-3"
              >
                <div class="card shadow-sm">
                  <div class="card-body">
                    <h6 class="card-title">{{ recommendation.jbpTtl }}</h6>
                    <p class="card-text">
                      <strong>{{ recommendation.matchSkills }}</strong> 기술
                      일치
                    </p>
                    <!-- 공고 상세보기 링크 추가 가능 -->
                    <a
                      :href="'/job-detail/' + recommendation.jbpSq"
                      class="btn btn-primary btn-sm"
                    >
                      공고 상세보기
                    </a>
                  </div>
                </div>
              </li>
            </ul>
          </div>
          <div v-else>
            <p class="text-muted">추천된 채용 공고가 없습니다.</p>
          </div>
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

// 마이페이지 관련 데이터
let result = ref({});
let recommendations = ref([]); // 추천 공고 리스트를 저장할 ref 변수
let applyStateSum = computed(() => getApplyStateSum());
let calendarEvents = computed(() =>
  makeCalendarDatas(result.value.calendarData)
);
let fc = ref(null);
let month = new Date().getMonth() + 1;
let checkedMonth = [month];

onMounted(async () => {
  try {
    // 마이페이지 데이터 호출
    const response = await api.$get("/user/mypage/", {
      params: {
        mbr_sq: store.state.member.mbrSq,
        month: month,
      },
    });
    result.value = response || {}; // 마이페이지 데이터 설정

    // 선택된 기술 코드와 이력서 순번 가져오기
    const selectedSkills = store.state.selectedSkills; // 선택된 기술 코드들
    const rsmSq = store.state.member.rsmSq; // 이력서 순번

    // 추천 공고 API 호출
    const recommendationsResponse = await api.get(`/recommendations/${rsmSq}`, {
      params: { selectedSkills },
    });

    // 추천 공고 리스트 저장
    recommendations.value = recommendationsResponse.data;
  } catch (error) {
    console.error("API 호출 오류:", error);

    // 오류 발생 시 추천 공고 데이터 비우기
    recommendations.value = []; // 추천 공고 데이터 오류 처리
  }
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

// FullCalendar 커스텀버튼
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

// 달력 월 이동 시 해당 월 데이터 가져오기
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

/* 각 항목에 대한 스타일 */
.mb-0 {
  margin-bottom: 0;
}

@media (max-width: 767px) {
  /* 작은 화면에서 카드의 크기를 조정 */
  .status-card {
    padding: 8px;
  }
}
</style>
