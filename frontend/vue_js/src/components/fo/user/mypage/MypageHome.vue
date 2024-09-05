<template>
  <div v-if="result && Object.keys(result).length > 0">
    <div
      class="row"
      style="padding-bottom: 20px; border-bottom: 1px solid #eaeaea"
    >
      <div
        class="col-3"
        style="display: flex; align-items: center; justify-content: center"
      >
        <div class="thumb-info-side-image-wrapper">
          <img
            src="@/assets/profile.webp"
            class="img-fluid"
            alt=""
            style="width: 140px"
          />
        </div>
        <!-- 나중에 야돈대신 들어갈 자리 :src="{{result.rsmInfo.rsm_img_file_url}}" -->
      </div>
      <div class="col-9 table-container">
        <table class="table table-bordered" style="margin-bottom: 0">
          <thead>
            <tr>
              <th colspan="3">
                <h2 class="font-weight-bold" style="margin-bottom: 10px">
                  {{ result.rsmInfo.rsm_ttl }}
                </h2>
                <span
                  >-최종 수정일자&nbsp;&nbsp;:&nbsp;&nbsp;{{
                    formatDateYMD(result.rsmInfo.updt_dtm)
                  }}</span
                ><span style="margin-left: 20px"
                  >-포지션 수락 여부&nbsp;&nbsp;:&nbsp;&nbsp;{{
                    (result.mbrInfo.pstn_prpsl_accept_yn = "Y"
                      ? "수락"
                      : "거절")
                  }}</span
                >
              </th>
            </tr>
          </thead>
          <tbody style="text-align: center">
            <tr>
              <th>
                {{ result.mbrInfo.mbr_name }}
              </th>
              <th>
                {{ result.mbrInfo.mbr_mp }}
              </th>
              <th>
                {{ result.mbrInfo.mbr_eml_adrs }}
              </th>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <div class="row" style="padding-top: 20px">
      <table class="table table-bordered" style="text-align: center">
        <tr>
          <th>
            등록한 이력서&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{
              result.myState.rsm_cnt
            }}
          </th>
          <th>추천 공고&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(로직 준비중)</th>
          <th>
            스크랩 공고&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{
              result.myState.scrap_cnt
            }}
          </th>
          <th>
            받은 제안&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{
              result.myState.prpsl_cnt
            }}
          </th>
        </tr>
      </table>
    </div>
    <div
      class="row"
      style="padding-top: 20px; border-bottom: 1px solid #eaeaea"
    >
      <div class="col pb-3">
        <table class="table table-bordered table-apply-state">
          <thead>
            <tr>
              <th rowspan="2">전체</th>
              <th colspan="2">진행중</th>
              <th colspan="2">면접</th>
              <th rowspan="2">불합격</th>
              <th rowspan="2">합격</th>
              <th rowspan="2">마감</th>
            </tr>
            <tr>
              <th>열람</th>
              <th>미열람</th>
              <th>대기</th>
              <th>완료</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>
                {{ applyStateSum }}
              </td>
              <td>
                {{ result.EachCndtnApplyCount.cndtn_402 }}
              </td>
              <td>
                {{ result.EachCndtnApplyCount.cndtn_401 }}
              </td>
              <td>
                {{ result.EachCndtnApplyCount.cndtn_405 }}
              </td>
              <td>
                {{ result.EachCndtnApplyCount.cndtn_406 }}
              </td>
              <td>
                {{ result.EachCndtnApplyCount.cndtn_408 }}
              </td>
              <td>
                {{ result.EachCndtnApplyCount.cndtn_407 }}
              </td>
              <td>(준비중)</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
    <div class="row" style="padding-top: 20px">
      <Calendar
        :events="calendarEvents"
        :customButtons="customButtons"
        ref="fc"
      />
    </div>
  </div>
  <div v-else>
    <div
      class="row"
      style="padding-bottom: 20px; border-bottom: 1px solid #eaeaea"
    >
      <div
        class="col-3"
        style="display: flex; align-items: center; justify-content: center"
      >
        <div class="thumb-info-side-image-wrapper">
          <img
            src="@/assets/avatar.jpg"
            class="img-fluid rounded-circle mb-4"
            alt=""
            style="width: 140px"
          />
        </div>
        <!-- 나중에 야돈대신 들어갈 자리 :src="{{result.rsmInfo.rsm_img_file_url}}" -->
      </div>
      <div class="col-9 table-container">
        <table class="table table-bordered" style="margin-bottom: 0">
          <thead>
            <tr>
              <th colspan="3">
                <h2 class="font-weight-bold" style="margin-bottom: 10px">대표 이력서를 등록해주세요.</h2>
              </th>
            </tr>
          </thead>
          <tbody style="text-align: center">
            <tr>
              <th></th>
              <th></th>
              <th></th>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
    
    <div class="row" style="padding-top: 20px">
      <table class="table table-bordered" style="text-align: center">
        <tr>
          <th>
            등록한 이력서&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;0
          </th>
          <th>추천 공고&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(로직 준비중)</th>
          <th>
            스크랩 공고&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;0
          </th>
          <th>
            받은 제안&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;0
          </th>
        </tr>
      </table>
    </div>
    <div
      class="row"
      style="padding-top: 20px; border-bottom: 1px solid #eaeaea"
    >
      <div class="col pb-3">
        <table class="table table-bordered table-apply-state">
          <thead>
            <tr>
              <th rowspan="2">전체</th>
              <th colspan="2">진행중</th>
              <th colspan="2">면접</th>
              <th rowspan="2">불합격</th>
              <th rowspan="2">합격</th>
              <th rowspan="2">마감</th>
            </tr>
            <tr>
              <th>열람</th>
              <th>미열람</th>
              <th>대기</th>
              <th>완료</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>0</td>
              <td>0</td>
              <td>0</td>
              <td>0</td>
              <td>0</td>
              <td>0</td>
              <td>0</td>
              <td>(준비중)</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
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

let result = ref({});
let applyStateSum = computed(() => getApplyStateSum());
let calendarEvents = computed(() =>
  makeCalendarDatas(result.value.calendarData)
);
let fc = ref(null);
let month = new Date().getMonth() + 1;
let checkedMonth = [month];

onMounted(async () => {
  try {
    const response = await api.$get("/user/mypage/", {
      params: {
        mbr_sq: store.state.member.mbrSq,
        month: month,
      },
    });
    result.value = response.data || {};  // 데이터를 받지 못했을 때 기본 빈 객체로 설정
  } catch (error) {
    console.error("API 호출 오류:", error);
    result.value = {};  // 오류가 발생했을 때 기본 빈 객체로 설정
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
.table-container {
  display: flex;
  align-items: center; /* 세로 축 가운데 정렬 */
  justify-content: center; /* 가로 축 가운데 정렬 */
}

.table-apply-state th,
.table-apply-state td {
  text-align: center;
  vertical-align: middle;
}
</style>
