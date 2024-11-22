<template>
  <div class="board-list-container">
    <header class="header bg-light border-bottom shadow-sm py-3">
      <div class="container text-center">
        <h1 class="h3 text-primary mb-0">Q&A 게시판</h1>
      </div>
    </header>

    <div class="board-content">
      <div class="search-area mb-1">
        <div class="row align-items-center">
          <!-- 검색 옵션 -->
          <div class="col-auto">
            <select v-model="search.sk" class="form-select form-select">
              <option value="">검색 옵션</option>
              <option value="title">제목</option>
              <option value="contents">내용</option>
            </select>
          </div>

          <!-- 검색어 입력 -->
          <div class="col">
            <input
              type="text"
              v-model="tempSearch.sv"
              class="form-control form-control"
              @keydown.enter="onSearch"
              placeholder="검색어를 입력하세요"
            />
          </div>

          <!-- 검색 버튼 -->
          <div class="col-auto">
            <button class="btn btn-primary btn" @click="onSearch">검색</button>
          </div>
        </div>
      </div>

      <!-- 글쓰기 버튼 -->
      <div class="d-flex justify-content-end mb-3" v-if="isLoggedIn">
        <router-link to="/board/qna/write" class="btn btn-primary btn-sm"
          >글쓰기</router-link
        >
      </div>

      <!-- 채택여부와 최신순 드롭다운 -->
      <div class="sort-options d-flex justify-content-end mb-3">
        <label for="selection" class="me-2 fw-bold">채택여부:</label>
        <select
          v-model="search.selection"
          class="form-select form-select-sm w-auto me-2"
        >
          <option value="">전체</option>
          <option value="inprgrs">진행중</option>
          <option value="rslvd">자체해결</option>
          <option value="acpt">채택완료</option>
          <option value="unrslvd">미해결</option>
        </select>
        <label for="sort" class="me-2 fw-bold ms-2">정렬:</label>
        <select v-model="search.sort" class="form-select form-select-sm w-auto">
          <option value="">최신순</option>
          <option value="earliest">오래된순</option>
          <option value="view">조회순</option>
        </select>
      </div>

      <div class="table-responsive">
        <table class="table table-striped table-hover align-middle text-center">
          <thead class="table-light">
            <tr>
              <th style="width: 46%">제목</th>
              <th style="width: 10%">작성자</th>
              <th style="width: 15%">등록일</th>
              <th style="width: 7%">조회수</th>
              <th style="width: 7%">답글수</th>
              <th style="width: 15%">채택여부</th>
            </tr>
          </thead>

          <!-- 게시글이 존재할 때 -->
          <tbody v-if="boardList && boardList.length > 0">
            <tr v-for="board in boardList" :key="board.brdSq">
              <!-- 제목 -->
              <td class="fw-bold">
                <router-link
                  :to="`/board/qna/${board.brdSq}`"
                  class="text-decoration-none text-dark"
                >
                  {{ board.brdTtl }}
                </router-link>
              </td>
              <!-- 작성자 -->
              <td class="small">{{ board.createdBy }}</td>
              <!-- 등록일 -->
              <td class="small">{{ board.insrtDtm.slice(0, 10) }}</td>
              <!-- 조회수 -->
              <td class="small">{{ board.brdHits }}</td>
              <!-- 답글수 -->
              <td class="small">{{ board.answerCount }}</td>
              <!-- 채택 여부 -->
              <td>
                <span v-if="board.brdCndtn === 'N'" class="badge bg-success"
                  >진행중</span
                >
                <span v-else-if="board.brdCndtn === 'Y'" class="badge bg-danger"
                  >채택완료</span
                >
                <span
                  v-else-if="board.brdCndtn === 'S'"
                  class="badge bg-primary"
                  >자체해결</span
                >
                <span v-else class="badge bg-warning">미해결</span>
              </td>
            </tr>
          </tbody>

          <!-- 게시글이 존재하지 않을 때 -->
          <tbody v-else>
            <tr>
              <td colspan="6" class="text-center text-muted py-3">
                검색 결과가 없습니다.
              </td>
            </tr>
          </tbody>
        </table>
      </div>

      <div class="pagination-wrapper" v-if="boardList && boardList.length > 0">
        <nav>
          <ul class="pagination justify-content-center">
            <li class="page-item" :class="{ disabled: curPage === 1 }">
              <button
                class="page-link"
                @click="goToPage(1)"
                :disabled="curPage === 1"
              >
                &laquo;
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
              v-for="page in pageList"
              :key="page"
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
                &raquo;
              </button>
            </li>
          </ul>
        </nav>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import { api } from "../../../../axios";
import { showAlert } from "../../../../utill/utillModal";
import { useStore } from "vuex";
import { watch } from "vue";

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
const search = ref({
  page: 1,
  sk: "",
  sv: "",
  brdTypCode: "qna",
  sort: "",
  selection: "",
});
const tempSearch = ref({
  sv: "",
});

const getBoardList = async () => {
  const queryString = Object.entries(search.value)
    .map((e) => e.join("="))
    .join("&");
  try {
    const data = await api.$get("/board?" + queryString);
    boardList.value = data.data || [];

    const answerPromises = boardList.value.map((board) =>
      api
        .$get("/answer/" + board.brdSq)
        .then((answer) => {
          return {
            brdSq: board.brdSq,
            answerCheck: answer.checkAnswer || 0,
            answerCount: answer.countAnswer || 0,
          };
        })
        .catch((error) => {
          console.error(error);
          return {
            brdSq: board.brdSq,
            answerCheck: 0,
            answerCount: 0,
          };
        })
    );

    const answers = await Promise.all(answerPromises);

    for (const board of boardList.value) {
      try {
        const answer = answers.find((a) => a.brdSq === board.brdSq);
        board.answerCheck = answer ? answer.answerCheck : 0;
        board.answerCount = answer ? answer.answerCount : 0;
      } catch (error) {
        console.error("값 추가중 오류 발생", error);
        board.answerCheck = 0;
        board.countAnswer = 0;
      }
    }
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
  // 검색 옵션과 검색어가 모두 입력되어야 검색 실행
  if (!search.value.sk.trim() || !tempSearch.value.sv.trim()) {
    showAlert("검색 조건과 검색어를 모두 입력하세요.");
    return;
  }

  // 검색어를 실제 검색 상태에 반영
  search.value.sv = tempSearch.value.sv.trim();
  search.value.page = 1; // 페이지를 첫 번째로 초기화
  getBoardList();
};

// const moveToWrite = () => {
//   router.push("write");
// };

const goToPage = (page) => {
  if (page >= 1 && page <= lastPage.value) {
    search.value.page = page;
    getBoardList();
  }
};

watch(
  () => search.value.selection,
  () => {
    search.value.page = 1; // 페이지 초기화
    getBoardList();
  }
);

watch(
  () => search.value.sort,
  () => {
    search.value.page = 1; // 페이지 초기화
    getBoardList();
  }
);

onMounted(() => {
  getBoardList();
});
</script>

<style scoped>
.board-list-container {
  max-width: 1100px;
  margin: 0 auto;
  padding: 20px;
}

.header {
  background-color: #f8f9fa; /* 부트스트랩 기본 밝은 배경 */
  border-bottom: 1px solid #dee2e6; /* 구분선 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* 부드러운 그림자 */
}

.header h1 {
  font-weight: bold;
  font-family: "Arial", sans-serif; /* 세련된 폰트 */
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
  width: 60%;
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
  padding: 20px;
  display: flex;
  justify-content: center;
}

.search-select {
  padding: 5px;
}

.search-input {
  padding: 5px;
}

.btn-primary {
  padding: 5px 10px;
}
.justify-content-end {
  align-items: center;
}
.search-box {
  justify-content: space-between;
}
</style>
