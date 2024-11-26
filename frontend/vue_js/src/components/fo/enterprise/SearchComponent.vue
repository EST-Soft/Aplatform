<template>
  <div class="search-area">
    <!-- 검색 옵션 선택 -->
    <select v-model="searchField">
      <option value="jbpTtl_jbpCntnt">제목+내용</option>
      <option value="jbpTtl">제목</option>
      <option value="jbpCntnt">내용</option>
    </select>

    <!-- 검색어 입력 -->
    <input
      type="search"
      v-model="searchTerm"
       @keydown.enter="performSearch"
      placeholder="검색어를 입력하세요"
    />

    <!-- 검색 버튼 -->
    <button @click="performSearch" class="btn btn-primary">검색</button>
  </div>
</template>

<script setup>
/* eslint-disable */
import { ref } from "vue";
import { api } from "@/axios.js";  // API 요청을 위한 axios 인스턴스
import { useRouter } from "vue-router";  // vue-router를 통해 URL 변경
import { showAlert } from "../../../utill/utillModal";  // 경고 메시지 표시 유틸리티

const emit = defineEmits(["searchResult"]);  // 부모 컴포넌트로 검색 결과를 전달하는 이벤트

// 검색어와 검색 조건을 저장하는 변수들
const searchTerm = ref("");
const searchField = ref("jbpTtl_jbpCntnt");  // 기본 값은 제목+내용

const router = useRouter();  // vue-router 사용

// 검색 결과를 저장하는 변수
const searchResults = ref([]);

// 검색 수행 함수
const performSearch = () => {
  console.log("검색어:", searchTerm.value);
  console.log("검색 조건:", searchField.value);

  if (!searchTerm.value || !searchField.value) {
    showAlert("검색어를 입력해 주세요");
    return;
  }
  searchResults.value = [];

  api
    .$get("/board/search", {
      params: {
        searchTerm: searchTerm.value,
        searchField: searchField.value,
      },
    })
    .then((response) => {
      console.log("전체 응답:", response);  // 응답 구조 확인

      // 응답이 정상적으로 올 경우
      if (response) {
        searchResults.value = response;  // 응답 객체 전체를 searchResults에 저장
        emit("searchResult", response);  // 부모 컴포넌트로 전달
      } else {
        console.error("결과가 없습니다:", response);
        showAlert("검색 결과가 없습니다.");
      }

      // URL에 검색 상태를 반영
      router.push({
        path: router.currentRoute.value.path,
        query: {
          searchTerm: searchTerm.value,
          searchField: searchField.value,
        },
      });

      // 검색 후 입력값 초기화
      searchTerm.value = "";  // 검색어 초기화
    })
    .catch((error) => {
      console.error("검색 중 오류 발생:", error);
      showAlert("검색 중 오류가 발생했습니다.");
    });
};

</script>

<style scoped>
.search-area {
  margin-bottom: 20px;
}

/* 검색어가 비어 있으면 경고 메시지를 띄울 스타일 (선택 사항) */
.error-message {
  color: red;
  margin-top: 5px;
}
</style>
