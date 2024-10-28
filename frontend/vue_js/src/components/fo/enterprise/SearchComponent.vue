<template>
  <div class="search-area">
    <select v-model="searchField">
      <option value="jbpTl_jbpCntnt">제목+내용</option>
      <option value="jbpTl">제목</option>
      <option value="jbpCntnt">내용</option>
    </select>
    <input
      type="search"
      v-model="searchTerm"
      placeholder="검색어를 입력하세요"
    />
    <!-- :class="{ shake: searchTermError }" -->
    <button @click="performSearch" class="btn btn-primary">검색</button>
    <!-- <p v-if="searchTermError" class="error-message">검색어를 입력해주세요.</p> -->
  </div>
</template>

<script setup>
/* eslint-disable */
import { ref } from "vue";
import { api } from "@/axios.js";
import { useRouter } from "vue-router";
import { showAlert } from "../../../utill/utillModal";

const emit = defineEmits(["searchResult"]);

const searchTerm = ref("");
const searchField = ref("jbpTl_jbpCntnt");
// const searchTermError = ref(false);

const router = useRouter();

const performSearch = () => {
  if (searchTerm.value === "") {
    showAlert("검색어를 입력해 주세요");
    // searchTermError.value = true;
    return;
  } else {
    // searchTermError.value = false;
  }

  api
    .$get("/board/search", {
      params: {
        searchTerm: searchTerm.value,
        searchField: searchField.value,
      },
    })
    .then((response) => {
      console.log("Search response:", response);
      emit("searchResult", response);

      // 쿼리스트링을 주소에 추가
      router.push({
        path: router.currentRoute.value.path,
        query: {
          searchTerm: searchTerm.value,
          searchField: searchField.value,
        },
      });
    })
    .catch((error) => {
      console.error("Error searching:", error);
    });
};
</script>

<style scoped>
.search-area {
  margin-bottom: 20px;
}

.error-message {
  color: red;
  margin-top: 5px;
}

/* .shake {
  animation: shake 0.5s ease;
}

@keyframes shake {
  0%,
  100% {
    transform: translateX(0);
  }
  10%,
  30%,
  50%,
  70%,
  90% {
    transform: translateX(-10px);
  }
  20%,
  40%,
  60%,
  80% {
    transform: translateX(10px);
  }
} */
</style>
