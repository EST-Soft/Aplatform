<template>
  <div class="board-list-container">
    <header class="header">
      <h1>Q&A 게시판</h1>
    </header>

    <div class="board-content">
      <div class="search-box">

        <div class="search-box">

        <!-- 로그인된 경우 글쓰기 버튼을 추가 -->
          <div class="d-flex justify-content-end mb-2" v-if="isLoggedIn">
            <router-link class="btn btn-primary" to="/board/qna/write">글쓰기</router-link>
          </div>

          <div class="search-area row">
            <div class="col-auto">
              <select v-model="search.sk" class="search-select">
                <option value="">검색 옵션</option>
                <option value="title">제목</option>
                <option value="contents">내용</option>
              </select>
            </div>
            <div class="col-6"> 
              <input
                type="text"
                v-model="search.sv"
                class="search-input form-control"
                @keydown.enter="onSearch"
                placeholder="검색어를 입력하세요"
              />
            </div>
            <div class="col-auto">
              <button class="btn btn-primary" @click="onSearch">검색</button>
            </div>
          </div>

          <div class="sort-options mb-2 d-flex justify-content-end">
            <select v-model="search.selection" class="search-select me-1">
              <option value="">채택여부</option>
              <option value="inprgrs">진행중</option>
              <option value="rslvd">자체해결</option>
              <option value="acpt">채택완료</option>
              <option value="unrslvd">미해결</option>
            </select>

            <select v-model="search.sort" class="search-select">
              <option value="">최신순</option>
              <option value="earliest">오래된순</option>
              <option value="view">조회순</option>
            </select>
          </div>
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
              <th>답글수</th>
              <th>채택여부</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="board in boardList" :key="board.brdSq">
              <td class="title-cell">
                <router-link :to="`/board/qna/${board.brdSq}`">
                  {{ board.brdTtl }}
                </router-link>
              </td>
              <td class="small-text">{{ board.createdBy }}</td>
              <td class="small-text">{{ board.insrtDtm.slice(0, 10) }}</td>
              <td class="small-text">{{ board.brdHits }}</td>
              <td class="small-text">{{ board.answerCount }}</td>
              <td v-if="board.brdCndtn === null || board.brdCndtn == 'N'" class="small-text">
                <span class="badge text-bg-success">진행중</span>
              </td>
              <td v-if="board.brdCndtn == 'Y'" class="small-text">
                <span class="badge text-bg-danger">채택완료</span>
              </td>
              <td v-if="board.brdCndtn == 'S'" class="small-text">
                <span class="badge text-bg-primary">자체해결</span>
              </td>
              <td v-if="board.brdCndtn === 'U'" class="small-text">
                <span class="badge text-bg-warning">미해결</span>
              </td>
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
              &raquo;
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
const search = ref({ page: 1, sk: "", sv: "", brdTypCode: "qna", sort: "", selection: "" });

const getBoardList = async () => {
  const queryString = Object.entries(search.value)
    .map((e) => e.join("="))
    .join("&");
  try {
    console.log(queryString);
    const data = await api.$get("/board?" + queryString);
    boardList.value = data.data || [];

    console.log(boardList.value.map(board => board));

    const answerPromises = boardList.value.map(board =>
      api.$get("/answer/" + board.brdSq)
        .then(answer => {
          console.log("answer.checkAnswer:", answer);
          return {
            brdSq: board.brdSq,
            answerCheck: answer.checkAnswer || 0,
            answerCount: answer.countAnswer || 0
          };
        })
        .catch(error => {
          console.error(error);
          return {
            brdSq: board.brdSq,
            answerCheck: 0,
            answerCount: 0
          };
        })
    );
    

    const answers = await Promise.all(answerPromises);

    for(const board of boardList.value){
      try{
        const answer = answers.find(a => a.brdSq === board.brdSq);
        board.answerCheck = answer ? answer.answerCheck : 0;
        board.answerCount = answer ? answer.answerCount : 0;

      }catch(error){
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

        // 문제 발생 가능성: endPage가 totalPageCnt보다 클 수 있음
        console.log("startPage:", startPage, "endPage:", endPage, "totalPageCnt:", totalPageCnt);

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

const goToPage = (page) => {
  if (page >= 1 && page <= lastPage.value) {
    search.value.page = page;
    getBoardList();
  }
};

watch(
  () => search.value.sort,
  () => {
    getBoardList();
  }
)

watch(
  () => search.value.selection,
  () => {
    getBoardList();
  }
)

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

.search-select{
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
