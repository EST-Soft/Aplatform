<template>
    <div class="row">
        <h1 class="form-group col-md-12">
            <strong class="font-weight-extra-bold"> 이력서 목록 </strong>
        </h1>
        <hr class="gradient" />
    </div>

    <div class="row">
        <div class="form-group col-md-10"></div>
        <!-- 코드화 안되어있어서 하드코딩 -->
        <div class="form-group col-md-2">
            <select class="form-select form-control h-auto py-2" @change="changeSort($event)">
                <option value="desc">내림차순</option>
                <option value="asc">올림차순</option>
            </select>
        </div>
    </div>

    <div class="row">
        <!-- 자료없을때 예외 -->
        <div v-if="resumeListData.resumeDatas.length == 0">
            <strong class="font-weight-extra-bold"> 자료가 없습니다. </strong>
        </div>
        <!-- 자료있을때 for -->
        <div v-else>
            <div v-for="resumeData in resumeListData.resumeDatas" :key="resumeData.rsm_sq">
                <ResumeDatas :resumeData="resumeData" @modify-representative="modifyRepresentative"
                    @delete-resumes="deleteResumes" @copy-resumes="copyResumes" />
            </div>
        </div>
    </div>
    <br/>
    <div class="row" id="pagination-container">
        <div class="pagination-wrapper" v-if="resumeListData.paginationData.totalDataCount > 1">
            <PaginationData :paginationData="resumeListData.paginationData" @change-page-no="changePageNo" />
        </div>
    </div>
</template>

<script setup>
import PaginationData from "@/components/fo/enterprise/common/PaginationData.vue";
import ResumeDatas from "@/components/fo/enterprise/resume/ResumeDatas.vue";
import axios from "axios";

import { onMounted, ref, computed } from "vue";
import { useStore } from "vuex";

const store = useStore();

const resumeListData = ref({
    resumeDatas: [],
    paginationData: {},
    searchData: {},
});

const member = computed(() => store.getters.getMember);
const isLoggedIn = computed(() => member.value !== null);

onMounted(() => {
    if (isLoggedIn.value && member.value) {
        resumeListData.value.searchData = { 
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
    await axios.get("http://localhost:80/resumes/resume-list/" + resumeListData.value.searchData.mbr_sq + "/" + resumeListData.value.searchData.sort + "/" + resumeListData.value.searchData.pageNo)
        .then((success) => {
            console.log('axios 성공' + success.data);
            resumeListData.value = success.data;
        })
        .catch((error) => {
            console.log('axios 실패' + error.data);

        });
};

// 이벤트 함수
// 페이지네이션 페이지 변경 클릭
const changePageNo = (emit) => {
    resumeListData.value.searchData.pageNo = emit;
    callAxios();
};
// 정렬 select 변경
const changeSort = (event) => {
    resumeListData.value.searchData.sort = event.target.value;
    resumeListData.value.searchData.pageNo = 1;
    callAxios();
};

// 대표 이력서 변경 클릭 (rsm_sq 들고 Axios)
const modifyRepresentative = async (emit) => {
    await axios.patch("http://localhost:80/resumes/representative/" + emit)
        .then((success) => {
            console.log('axios 성공' + success);
            callAxios();
        })
        .catch((error) => {
            console.log('axios 실패' + error);
        });
};

// 이력서 복제 (rsm_sq 들고 Axios)
const copyResumes = async (emit) => {
    await axios.post("http://localhost:80/resumes/copy/" + emit)
        .then((success) => {
            console.log('axios 성공' + success);
            callAxios();
        })
        .catch((error) => {
            console.log('axios 실패' + error);
            alert("복제가 불가능합니다.");
        });
};
// 이력서 수정 (rsm_sq 들고 페이지 이동)

// 이력서 삭제 (rsm_sq 들고 Axios)
const deleteResumes = async (emit) => {
    await axios.delete("http://localhost:80/resumes/" + emit)
        .then((success) => {
            console.log('axios 성공' + success);
            callAxios();
        })
        .catch((error) => {
            console.log('axios 실패' + error);
        });
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