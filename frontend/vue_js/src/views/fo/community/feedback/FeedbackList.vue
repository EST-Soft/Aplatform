<template>
  <div class="board-list-container">
    <header class="header">
      <h1>피드백 게시판</h1>
    </header>

    <div class="board-content">
      <div class="search-box">
        <div class="search-area mb-4">
          <select v-model="search.sk" class="search-select">
            <option value="">-선택-</option>
            <option value="title">제목</option>
            <option value="contents">내용</option>
          </select>
          <input
            type="text"
            v-model="search.sv"
            class="search-input"
            @keydown.enter="onSearch"
            placeholder="검색어를 입력하세요"
          />
          <button class="btn btn-primary" @click="onSearch">검색</button>
        </div>
        <div class="d-flex justify-content-end mb-4" v-if="isLoggedIn">
          <router-link class="btn btn-primary" to="/board/write"
            >글쓰기</router-link
          >
        </div>
      </div>
      <div class="table-responsive">
        <table class="table table-hover table-striped">
          <thead>
            <tr>
              <th>제목</th>
              <th>작성자</th>
              <th>등록일</th>
              <th>조회수</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="board in boardList" :key="board.brdSq">
              <td class="title-cell">
                <router-link :to="`/board/feedback/${board.brdSq}`">
                  {{ board.brdTtl }}
                </router-link>
              </td>
              <td class="small-text">{{ board.createdBy }}</td>
              <td class="small-text">{{ board.insrtDtm.slice(0, 10) }}</td>
              <td class="small-text">{{ board.brdHits }}</td>
            </tr>
          </tbody>
        </table>
      </div>
      <div class="pagination-wrapper">
        <ul class="pagination justify-content-center">
          <li class="page-item" :class="{ disabled: curPage === 1 }">
            <button
              class="page-link"
              @click="goToPage(1)"
              :disabled="curPage === 1"
            >
              &lt;&lt;
            </button>
          </li>
          <li class="page-item" :class="{ disabled: curPage === 1 }">
            <button
              class="page-link"
              @click="goToPage(curPage - 1)"
              :disabled="curPage === 1"
            >
              &lt;
            </button>
          </li>
          <li
            v-for="(page, index) in pageList"
            :key="index"
            class="page-item"
            :class="{ active: curPage === page }"
          >
            <button class="page-link" @click="goToPage(page)">
              {{ page }}
            </button>
          </li>
          <li class="page-item" :class="{ disabled: curPage === lastPage }">
            <button
              class="page-link"
              @click="goToPage(curPage + 1)"
              :disabled="curPage === lastPage"
            >
              &gt;
            </button>
          </li>
          <li class="page-item" :class="{ disabled: curPage === lastPage }">
            <button
              class="page-link"
              @click="goToPage(lastPage)"
              :disabled="curPage === lastPage"
            >
              &gt;&gt;
            </button>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import { api } from "../../../../axios";
import { showAlert } from "../../../../utill/utillModal";
import { useStore } from "vuex";

// import { useRouter } from "vue-router";
const store = useStore();
const member = computed(() => store.getters.getMember);
const isLoggedIn = computed(() => member.value !== null);
// const router = useRouter();
const boardList = ref([]);
const pageList = ref([]);
const curPage = ref(0);
const prevBlock = ref(0);
const nextBlock = ref(0);
const lastPage = ref(0);
const search = ref({ page: 1, sk: "", sv: "", brdgnrCd: "feedback" });

const getBoardList = async () => {
  console.log(search.value);
  const queryString = Object.entries(search.value)
    .map((e) => e.join("="))
    .join("&");
  try {
    const data = await api.$get("/board?" + queryString);
    boardList.value = data.data || [];
    console.log(boardList);
    if (data.pagination) {
      const {
        endPage,
        nextBlock: nextPageBlock,
        prevBlock: previousBlock,
        startPage,
        totalPageCnt,
      } = data.pagination;
      curPage.value = search.value.page;
      prevBlock.value = previousBlock;
      nextBlock.value = nextPageBlock;
      lastPage.value = totalPageCnt;
      const tmpPages = [];
      for (let i = startPage; i <= endPage; i++) {
        tmpPages.push(i);
      }
      pageList.value = tmpPages;
    } else {
      console.error("Pagination data is missing.");
    }
  } catch (error) {
    console.error("Failed to fetch board list:", error);
  }
};

const onSearch = () => {
  if (search.value.sk === "" || search.value.sv === "") {
    showAlert("검색 조건과 검색어를 모두 입력하세요.");
    return;
  }
  search.value.page = 1;
  getBoardList();
};

// const moveToWrite = () => {
//   router.push("write");
// };

onMounted(() => {
  getBoardList();
});
const goToPage = (page) => {
  if (page >= 1 && page <= lastPage.value) {
    search.value.page = page;
    getBoardList();
  }
};
</script>

<style scoped>
.board-list-container {
  max-width: 1000px;
  margin: 0 auto;
  padding: 20px;
}

.header {
  text-align: center;
  margin-bottom: 20px;
}

.board-content {
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  padding: 20px;
  text-align: center;
}

.table {
  width: 100%;
  margin-bottom: 20px;
}

.table th,
.table td {
  vertical-align: middle;
}
.title-cell {
  width: 70%;
}
.title-cell:hover {
  cursor: pointer;
}
.title-cell a {
  color: #007bff;
  text-decoration: none;
}

.title-cell a:hover {
  text-decoration: underline;
}

.small-text {
  font-size: 0.875rem;
}

.pagination-wrapper {
  margin-top: 20px;
}

.page-link {
  color: #007bff;
  text-decoration: none;
}

.page-item.active .page-link {
  background-color: #007bff;
  border-color: #007bff;
  color: #fff;
}

.search-area {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
  padding: 20px;
  display: flex;
  justify-content: center;
}

.search-select,
.search-input {
  margin-right: 10px;
  padding: 5px;
}

.btn-primary {
  padding: 5px 10px;
}
.justify-content-end {
  align-items: center;
}
.search-box {
  display: flex;
  justify-content: space-between;
}
</style>
