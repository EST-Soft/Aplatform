<template>
    <div class="heading heading-border heading-middle-border" style="margin-bottom: 50px;">
        <h1 class="font-weight-normal">스크랩 공고<strong class="font-weight-extra-bold"></strong></h1>
    </div>
    <div class="row align-items-center justify-content-end">
        <div class="form-group col-md-2">
            <select class="form-select form-control h-auto py-2" @change="changeSort($event)">
                <option value="desc">스크랩일순</option>
                <option value="asc">등록일순</option>
            </select>
        </div>
        <div class="form-group col-md-3">
            <div class="search-with-select">
                <div class="search-form-wrapper input-group" style="margin-left: 30px;">
                    <input class="form-control text-1" type="search" v-model="search" placeholder="검색창(제목+내용)"/>
                    <div class="search-form-select-wrapper">
                        <button class="btn" type="button" aria-label="Search" @click="ScrapSearch">
                        <i class="icons icon-magnifier header-nav-top-icon text-color-dark"></i>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>


    <div class="row">
        <!-- 자료없을때 예외 -->
        <div v-if="scrapListData.scrapDatas.length == 0">
            <strong class="font-weight-extra-bold"> 자료가 없습니다. </strong>
        </div>
        <!-- 자료있을때 for -->
        <div v-else>
            <div v-for="jobPostingData in scrapListData.jobPostingData" :key="jobPostingData.jbp_sq">
                <MypageScrap :jobPostingData="jobPostingData"/>
            </div>
        </div>
    </div>
    <br/>
    <div class="row" id="pagination-container">
        <div class="pagination-wrapper" v-if="scrapListData.paginationData.totalDataCount > 1">
            <PaginationData :paginationData="scrapListData.paginationData" @change-page-no="changePageNo" />
        </div>
    </div>
</template>

<script setup>
import PaginationData from "@/components/fo/enterprise/common/PaginationData.vue";
import MypageScrap from "../../../components/fo/user/mypage/MypageScrap.vue";
import axios from "axios";

import { onMounted, ref, computed } from "vue";
import { useStore } from "vuex";

const store = useStore();

const scrapListData = ref({
    scrapDatas: [],
    paginationData: {},
    searchData: {},
    jobPostingData: {}
});

const member = computed(() => store.getters.getMember);
const isLoggedIn = computed(() => member.value !== null);

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

// axios 함수
const callAxios = async () => {
    // 리스트 뿌려주는 기본 axios
    await axios.get("http://localhost:80/user/mypage/scrap/scrapList-list/" + scrapListData.value.searchData.mbr_sq + "/" + scrapListData.value.searchData.sort + "/" + scrapListData.value.searchData.pageNo)
        .then((success) => {
            console.log('axios 성공' + success.data);
            scrapListData.value = success.data;
        })
        .catch((error) => {
            console.log('axios 실패' + error.data);

        });
};

// 이벤트 함수
// 페이지네이션 페이지 변경 클릭
const changePageNo = (emit) => {
    scrapListData.value.searchData.pageNo = emit;
    callAxios();
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