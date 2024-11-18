<template>
  <div class="container mt-5">
    <!-- 게시글작성자와 로그인사용자 같고 채택된 답변이나 자체해결이 되지않았을 때 -->
    <div class="d-flex justify-content-end mt-4" v-if="isLoggedIn && board.brdCndtn == 'N'">
        <button v-show="filterSelect" class="btn btn-primary m-2" v-if="mbrSqCheck(board.mbrSq)" @click="answerSelfSelection(board.brdSq)">
        자체해결
        </button>
        <router-link
          class="btn btn-primary me-2"
          :to="`/board/qna/update/${boardId}`"
          >수정</router-link
        >
        
        <button class="btn btn-danger me-2" @click="boardDelete">삭제</button>
        <button class="btn btn-success" @click="backToList">목록</button>
      </div>

      <!-- 게시글작성자와 로그인사용자 같고 채택된 답변이있거나 자체해결을 했을 때 -->
        <div class="d-flex justify-content-end mt-4" v-if="isLoggedIn && board.brdCndtn !== 'N'">
          <router-link
            class="btn btn-primary me-2"
            :to="`/board/qna/update/${boardId}`"
            >수정</router-link
          >
          
          <button class="btn btn-danger me-2" @click="boardDelete">삭제</button>
          <button class="btn btn-success" @click="backToList">목록</button>
        </div>

      <!-- 게시글작성자와 로그인사용자 다르고, 답변이 채택되지 않았을 때 -->
      <div v-if="loginCheck && board.mbrSq !== member.mbrSq && (board.brdCndtn !== 'Y')" class="d-flex justify-content-end mt-4">
        <button class="btn btn-success" @click="backToList">목록</button>
      </div>

      <!-- 게시글작성자와 로그인사용자 다르고, 답변이 채택되었을 때 -->
        <div v-if="loginCheck && board.mbrSq !== member.mbrSq && (board.brdCndtn == 'Y')" class="d-flex justify-content-end mt-4">
          <button class="btn btn-success" @click="backToList">목록</button>
        </div>

    <!-- 게시글 상세 내용 시작 -->
    <div class="board-detail mt-4 p-4 bg-white rounded shadow-sm">
      <div class="board-header d-flex justify-content-between mb-4">
        <h2 class="board-title">{{ board.brdTtl }}</h2>
        <div class="board-meta text-muted text-right">
          <div><strong>작성자:</strong> {{ board.createdBy }}</div>
          <div>
            <strong>등록일:</strong>
            {{ board.insrtDtm ? board.insrtDtm.slice(0, 10) : "등록일 없음" }}
          </div>
          <div><strong>조회수:</strong> {{ board.brdHits }}</div>
        </div>
      </div>
      
      <hr />
      <div v-show="showBoardContent" class="board-content p-4">
        <div v-html="board.brdCntnt"></div>
      </div>
      
      <hr />

      <div class="container" v-show="loginCheck && (board.brdCndtn === 'N' || board.brdCndtn === 'U') && board.mbrSq !== member.mbrSq">
        <!-- 답글 작성 폼 시작 -->
        <div class="form-container shadow-sm p-2 bg-white rounded mt-4">
          <div class="form-group mb-3">
            <label for="title" class="form-label">제목</label>
            <input
              type="text"
              id="answrTtl"
              v-model="answer.answrTtl"
              placeholder="제목을 입력하세요"
              class="form-control"
            />
          </div>
          <div class="form-group mb-4">
            <label for="contents" class="form-label">내용</label>
            <div class="editer">
              <!-- 툴바 -->
              <div id="toolbar">
                <select class="ql-font"></select>
                <select class="ql-size"></select>
                <button class="ql-bold"></button>
                <button class="ql-italic"></button>
                <button class="ql-underline"></button>
                <button class="ql-strike"></button>
                <select class="ql-color"></select>
                <select class="ql-background"></select>
                <button class="ql-script" value="sub"></button>
                <button class="ql-script" value="super"></button>
                <button class="ql-header" value="1"></button>
                <button class="ql-header" value="2"></button>
                <button class="ql-blockquote"></button>
                <button class="ql-code-block"></button>
                <button class="ql-list" value="ordered"></button>
                <button class="ql-list" value="bullet"></button>
                <button class="ql-indent" value="-1"></button>
                <button class="ql-indent" value="+1"></button>
                <button class="ql-direction" value="rtl"></button>
                <select class="ql-align"></select>
                <button class="ql-link"></button>
                <!-- <button class="ql-image"></button> -->
                <!-- <button class="ql-video"></button> -->
                <button class="ql-clean"></button>
              </div>

              <!-- 에디터 -->
              <div ref="editor" style="height: 300px"></div>
            </div>
          </div>
          <div class="button-container d-flex justify-content-between">
            <button class="btn btn-success" @click="saveAnswer">저장</button>
          </div>
        </div>
        <!-- 답글 작성 폼 끝 -->
        <hr />
      </div>

      <div class="answer-list-container">
    <header class="header">
      <h1>답변</h1>
    </header>

    <div class="answer-content">
      <div v-show="showAnswerList">
        <div class="table-responsive">
          <table class="table table table-striped">
            <thead>
              <tr v-if="answerList.length > 0">
                <th>번호</th>
                <th>제목</th>
                <th>작성자</th>
                <th>등록일</th>
                <th><i class="bi bi-hand-thumbs-up-fill"></i></th>
                <th><i class="bi bi-hand-thumbs-down-fill"></i></th>
                <th>채택</th>
              </tr>
              <tr v-else>
                <th></th>
              </tr>
            </thead>
            <tbody v-if="answerList.length > 0">
              <template v-for="answer in answerList" :key="answer.answrSq">
                <tr>
                  <!-- 답변 정보 -->
                  <td class="small-text">{{ answer.answrSq }}</td>
                  <td class="title-cell">{{ answer.answrTtl }}</td>
                  <td class="small-text">{{ answer.createdBy }}</td>
                  <td class="small-text">{{ answer.insrtDtm.slice(0, 10) }}</td>
                  <td class="small-text">{{ answer.answrRcmndtns }}</td>
                  <td class="small-text">{{ answer.answrNotRcmndtns }}</td>
                  <td v-if="answer.answrSlctnYn == 'Y'" class="small-text">
                    <span class="badge text-bg-success">채택</span>
                  </td>
                  <td v-else></td>
                </tr>
                <!-- 답변 내용 -->
                <tr>
                  <td colspan="8" class="answer-detail">
                    <div v-html="answer.answrCntnt" class="my-5"></div>
                    

                    <!-- 댓글 목록 -->
                    <div class="comments-section" v-if="answer.comments">
                          <h5>댓글</h5>
                          <div v-for="comment in answer.comments" :key="comment.cmntId" class="comment-card">
                            <div class="comment-header">
                              <span class="comment-author">{{ comment.createdBy }}</span>
                              <span class="comment-date">{{ comment.insrtDtm.slice(0, 10) }}</span>
                            </div>
                            <div class="comment-content">{{ comment.cmntCntnt }}</div>
                            <div class="comment-actions">
                              <button v-if="comment.createdBy === member.mbrId" @click="editComment(answer.answrSq, comment)">수정</button>
                              <button v-if="comment.createdBy === member.mbrId" @click="deleteComment(answer.answrSq, comment.cmntId)">삭제</button>
                            </div>
                          </div>
                        </div>

                        <!-- 댓글 작성 -->
                        <div class="comment-input" v-if="loginCheck">
                          <textarea v-model="newComment[answer.answrSq]" class="comment-textarea" placeholder="댓글을 입력하세요"></textarea>
                          <button @click="addComment(answer.answrSq)" class="comment-submit-button">댓글 추가</button>
                        </div>

                    <!-- 수정에디터 -->
                    <div v-show="answer.isEditing" class="form-container shadow-sm p-4 bg-white rounded">
                      <div class="form-group mb-3">
                        <label for="title" class="form-label">제목</label>
                        <input
                          type="text"
                          id="answrTtl"
                          v-model="answer1.answrTtl"
                          placeholder="제목을 입력하세요"
                          class="form-control"
                        />
                      </div>
                      <div class="form-group mb-4">
                        <label for="contents" class="form-label">내용</label>
                      
                        <div class="editor">
                          <!-- 툴바 -->
                          <div id="toolbar3">
                            <select class="ql-font"></select>
                            <select class="ql-size"></select>
                            <button class="ql-bold"></button>
                            <button class="ql-italic"></button>
                            <button class="ql-underline"></button>
                            <button class="ql-strike"></button>
                            <select class="ql-color"></select>
                            <select class="ql-background"></select>
                            <button class="ql-script" value="sub"></button>
                            <button class="ql-script" value="super"></button>
                            <button class="ql-header" value="1"></button>
                            <button class="ql-header" value="2"></button>
                            <button class="ql-blockquote"></button>
                            <button class="ql-code-block"></button>
                            <button class="ql-list" value="ordered"></button>
                            <button class="ql-list" value="bullet"></button>
                            <button class="ql-indent" value="-1"></button>
                            <button class="ql-indent" value="+1"></button>
                            <button class="ql-direction" value="rtl"></button>
                            <select class="ql-align"></select>
                            <button class="ql-link"></button>
                            <!-- <button class="ql-image"></button> -->
                            <!-- <button class="ql-video"></button> -->
                            <button class="ql-clean"></button>
                          </div>

                          <!-- 에디터 -->
                          <div ref="editor3" style="height: 300px"></div>
                        </div>
                      </div>
                      <div class="button-container d-flex justify-content-between">
                        <button class="btn btn-success" @click="saveEditedAnswer(answer)">저장</button>
                        <button class="btn btn-secondary" @click="cancelEdit(answer)">취소</button>
                      </div>
                    </div>

                    

                    <!-- 채택/수정/삭제/추천/비추천 버튼 -->
                    <div class="button-container mt-3 justify-content-end">
                      <button v-if="mbrSqCheck(answer.mbrSq) && !answer.isEditing" @click="startEdit(answer)" class="btn btn-primary btn-sm m-2">
                        수정
                      </button>
                      <button v-if="mbrSqCheck(answer.mbrSq) && answer.answrSlctnYn != 'Y' && !answer.isEditing" @click="deleteAnswer(answer.answrSq)" class="btn btn-danger btn-sm m-2">
                        삭제
                      </button>
                      <button v-show="filterSelect" class="btn btn-primary btn-sm m-2" v-if="mbrSqCheck(board.mbrSq) && !mbrSqCheck(answer.mbrSq)" @click="answerSelection(answer.answrSq, board.brdSq)">
                        채택하기
                      </button>
                      <button v-show="!mbrSqCheck(answer.mbrSq)" v-if="member != null" class="btn btn-primary btn-sm m-2" @click="answerRcmndtn(answer.answrSq)">
                        추천
                      </button>
                      <button v-show="!mbrSqCheck(answer.mbrSq)" v-if="member != null" class="btn btn-danger btn-sm m-2" @click="answerNotRcmndtn(answer.answrSq)">
                        비추천
                      </button>
                    </div>
                  </td>
                </tr>
              </template>
            </tbody>
            <tbody v-else>
              <tr>
                <td colspan="8"><h3>현재 등록된 답변이 없습니다</h3></td>
              </tr>
        </tbody>
          </table>
        </div>
      <div v-if="answerList.length > 0" class="pagination-wrapper">
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
  </div>
    </div>
    <!-- 게시글 상세 내용 끝 -->
  </div>

  <!-- 답변보기 시작 -->
  <div v-show="showAnswer" class="answer">
    <div class="answerModal">
    <div class="container mt-5">
    <section
      class="page-header page-header-modern bg-color-white page-header-lg text-center"
    >
      <div class="container">
        <div class="row">
          <div class="col-md-12 align-self-center p-static order-2 text-center">
            <h1 class="font-weight-bold text-dark">Q&A 게시판 - 답변 보기</h1>
          </div>
        </div>
      </div>
    </section>
        <!-- 답글 상세 내용 시작 -->
        <div class="board-detail mt-4 p-4 bg-white rounded shadow-sm">
      <div class="board-header d-flex justify-content-between mb-4">
        <h2 class="board-title">{{ answer1.answrTtl }}</h2>
        <div class="board-meta text-muted text-right">
          <div><strong>작성자:</strong> {{ answer1.createdBy }}</div>
          <div>
            <strong>등록일:</strong>
            {{ answer1.insrtDtm ? answer1.insrtDtm.slice(0, 10) : "등록일 없음" }}
          </div>
          <div><strong>조회수:</strong> {{ answer1.answrHits }}</div>
          <div><strong>추천수:</strong> {{ answer1.answrRcmndtns }}</div>
          <div><strong>비추천수:</strong> {{ answer1.answrNotRcmndtns }}</div>
        </div>
      </div>
      
      <hr />
      <div class="board-content1 p-4">
        <div v-html="answer1.answrCntnt"></div>
      </div>
   
      <hr />
      
      <div class="d-flex justify-content-end mt-4">
      <button class="btn btn-primary m-2" v-if="mbrSqCheck(answer1.mbrSq)" @click="OpenEditAnswer(answer1.answrSq)">
        수정
      </button>

      <!-- 추가적으로 채택된 답글인지 확인, 됐다면 삭제 불가능 alert -->
      <button class="btn btn-danger m-2" v-if="mbrSqCheck(answer1.mbrSq) && answer1.answrSlctnYn != 'Y'" @click="deleteAnswer(answer1.answrSq)">
        삭제
      </button>
      <button v-show="filterSelect" class="btn btn-primary m-2" v-if="mbrSqCheck(board.mbrSq) && !mbrSqCheck(answer1.mbrSq)" @click="answerSelection(answer1.answrSq, board.brdSq)">
        채택하기
      </button>
      <button v-show=" !mbrSqCheck(answer1.mbrSq)" v-if="member != null" class="btn btn-primary m-2" @click="answerRcmndtn(answer1.answrSq)" >
        추천
      </button> 
      <button v-show=" !mbrSqCheck(answer1.mbrSq)" v-if="member != null" class="btn btn-danger m-2" @click="answerNotRcmndtn(answer1.answrSq)" >
        비추천
      </button> 
      <button class="btn btn-secondary m-2" @click="closeAnswer()">
        닫기
      </button>
      </div>
    
      
    </div>
    </div>
    </div>
  </div>

</template>

<script setup>
import { ref, computed, onMounted, nextTick } from "vue";
import { useRoute, useRouter } from "vue-router";
import { api } from "@/axios";
import { showAlert, showConfirm } from "../../../../utill/utillModal";
import { useStore } from "vuex";
import Quill from "quill";
import Filter from 'badwords-ko';

const store = useStore();
const member = computed(() => store.getters.getMember);
const quillInstance = ref(null);
const editor = ref(null);
const answer = ref({});
const newComment = ref({});  // 각 답변의 새로운 댓글 입력값을 저장

const mbrSqCheck = (answerMbrSq) => {
  return member.value != null && answerMbrSq === member.value.mbrSq;
};

// 답글 작성 폼 입력값 처리
const saveAnswer = async () => {
  const filter = new Filter();
  const contentHtml = quillInstance.value.root.innerHTML.trim();
  const sanitizedContent = contentHtml.replace(/<p><br><\/p>/g, "").trim();

  const isProfaneTitle = filter.isProfane(answer.value.answrTtl);
  const isProfaneContent = filter.isProfane(sanitizedContent);

  if (isProfaneTitle) {
    showAlert("사용할 수 없는 단어가 제목에 포함되어 있습니다. 제목을 수정해주세요.");
    return;
  }

  if (isProfaneContent) {
    showAlert("사용할 수 없는 단어가 내용에 포함되어 있습니다. 내용을 수정해주세요.");
    return;
  }

  if (!answer.value.answrTtl || !sanitizedContent) {
    showAlert(!answer.value.answrTtl ? "제목을 입력하세요" : "내용을 입력하세요");
    return;
  }

  showConfirm("답변을 등록 하시겠습니까?", async () => {
    if (member.value.mbrSq) {
      answer.value.mbrSq = member.value.mbrSq;
    } else if (member.value.pk) {
      answer.value.entrprsSq = member.value.pk;
    }
    try {
      answer.value.answrCntnt = contentHtml;
      answer.value.brdSq = board.value.brdSq;
      await api.$post("/answer", answer.value);
      showAlert("답변이 등록되었습니다.");
      setTimeout(() => {
        window.location.href = `/board/qna/` + board.value.brdSq;
      }, 2000);
    } catch (error) {
      console.error("Error saving Answer: ", error);
    }
  });
};

const showAnswer = ref(false);
const answer1 = ref({});

const updateHits = async (answrSq) => {
  try {
    await api.$post(`/answer/updateHits/${answrSq}`);
  } catch (error) {
    showAlert("조회수 업데이트 오류");
  }
};

// 추천할 때는 조회수 업데이트 안 되게
const showAnswerDetail = async (answrSq) => {
  try {
    if (!showAnswer.value) {
      await updateHits(answrSq);
    }
    const data = await api.$get(`/answer/detail/${answrSq}`);
    answer1.value = data;
    showAnswer.value = false;
    await getAnswerList();
  } catch (error) {
    console.error("답글 가져오기 실패", error);
  }
};

const editor3 = ref(null);
const quillInstance3 = ref(null);

// 수정 모드 시작 함수
const startEdit = async (answer) => {
  answer.isEditing = true;
  await nextTick();

  if (editor3.value && !quillInstance3.value) {
    try {
      quillInstance3.value = new Quill(editor3.value, {
        theme: 'snow',
        modules: {
          toolbar: '#toolbar3'
        }
      });
      quillInstance3.value.root.innerHTML = answer.answrCntnt;
    } catch (error) {
      console.error("Quill 인스턴스 생성 오류:", error);
    }
  } else if (quillInstance3.value) {
    quillInstance3.value.root.innerHTML = answer.answrCntnt;
  }
};

// 수정 저장 함수
const saveEditedAnswer = (answer) => {
  answer.answrCntnt = quillInstance3.value.root.innerHTML;
  answer.isEditing = false;
};

// 수정 취소 함수
const cancelEdit = (answer) => {
  answer.isEditing = false;
};

const goToPage = (page) => {
  if (page >= 1 && page <= lastPage.value) {
    search.value.page = page;
    getAnswerList();
  }
};

const isLoggedIn = computed(() => {
  if (!member.value) {
    return false;
  }
  const memberId = member.value.mbrId || member.value.entrprsId;
  return board.value.createdBy === memberId;
});

const loginCheck = computed(() => !!member.value);

const route = useRoute();
const router = useRouter();
const board = ref({});
const boardId = ref(route.params.id);

const boardDelete = () => {
  showConfirm(
    "정말 삭제하시겠습니까? 삭제한 데이터는 복구할 수 없습니다.",
    async () => {
      try {
        await api.$delete(`board/${boardId.value}`);
        showAlert("삭제 완료되었습니다.");
        setTimeout(() => {
          router.push("/board/qna");
        }, 2000);
      } catch (error) {
        console.error("삭제 실패:", error);
        showAlert("삭제에 실패하였습니다.");
      }
    }
  );
};

const answerList = ref([
  { answrSq: 1, answrTtl: "제목", answrCntnt: "내용", createdBy: "작성자", insrtDtm: "2024-11-13", isEditing: false }
]);
const pageList = ref([]);
const curPage = ref(1);
const prevBlock = ref(0);
const nextBlock = ref(0);
const lastPage = ref(0);
const search = ref({ page: 1 });
const filterSelect = ref(true);

// 댓글 추가
const addComment = async (answrSq) => {
  const content = newComment.value[answrSq];
  if (!content) return;

  // 로그인 사용자 정보에서 mbrSq 가져오기
  const mbrSq = member.value?.mbrSq;
    if (!mbrSq) {
      showAlert("로그인 사용자 정보를 확인할 수 없습니다.");
      return;
    }

  try {
    await api.$post(`/comments/answer/${answrSq}`, { cmntCntnt: content, mbrSq: mbrSq });
    fetchComments(answrSq);
    newComment.value[answrSq] = ''; 
  } catch (error) {
    showAlert("댓글 추가 실패");
    console.error("Error adding comment:", error);
  }
};


// 댓글 조회
const fetchComments = async (answrSq) => {
  console.log("fetchComments 호출됨 - answrSq:", answrSq);
  try {
    const response = await api.$get(`comments/answer/${answrSq}`);
    console.log("response 값은 ? " + response.data);
    const answer = answerList.value.find(a => a.answrSq === answrSq);
    if (answer) answer.comments = response.data;
  } catch (error) {
    showAlert("댓글 로딩 실패");
    console.error("Error fetching comments:", error);
  }
};


// 댓글 수정
const editComment = (answrSq, comment) => {
  const updatedContent = prompt("댓글을 수정하세요:", comment.cmntCntnt);
  if (!updatedContent) return;

  api.$put(`/comments/${comment.cmntId}`, updatedContent)
    .then(() => fetchComments(answrSq))
    .catch(error => {
      showAlert("댓글 수정 실패");
      console.error("Error updating comment:", error);
    });
};


// 댓글 삭제
const deleteComment = async (answrSq, cmntSq) => {
  showConfirm("댓글을 삭제하시겠습니까?", async () => {
    try {
      await api.$delete(`/comments/${cmntSq}`);
      fetchComments(answrSq);
    } catch (error) {
      showAlert("댓글 삭제 실패");
      console.error("Error deleting comment:", error);
    }
  });
};


const getAnswerList = async () => {
  const queryString = Object.entries(search.value)
    .map((e) => e.join("="))
    .join("&");
  try {
    const data = await api.$get("/answer/qna/" + route.params.id + "?" + queryString);
    answerList.value = data.data || [];
    
    // 답변 리스트 내 각 답변의 댓글 불러오기
    answerList.value.forEach(answer => fetchComments(answer.answrSq));

    if (answerList.value.some(answer => answer.answrSlctnYn === 'Y')) {
      filterSelect.value = false;
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
    console.error("Failed to fetch answer list:", error);
  }
};


const answerSelection = (answrSq, brdSq) => {
  showConfirm("답변을 채택하시겠습니까?", async () => {
    try {
      await api.$patch(`/answer/selection/${answrSq}/${brdSq}`);
      showAlert("답변이 채택됐습니다");
      getAnswerList();
    } catch (error) {
      showAlert("채택 실패", error);
    }
  });
};

const answerSelfSelection = (brdSq) => {
  showConfirm("자체해결 하시겠습니까?", async () => {
    try {
      await api.$patch(`/board/selection/${brdSq}`);
      showAlert("자체해결 됐습니다");
      window.location.href = `/board/qna/${brdSq}`;
      getAnswerList();
    } catch (error) {
      showAlert("자체해결 실패", error);
    }
  });
};

const answerRcmndtn = async (answrSq) => {
  const sq = ref(0);
  sq.value = member.value.mbrSq || member.value.pk;

  showConfirm("답변을 추천하시겠습니까?", async () => {
    try {
      const res1 = await api.$post("/answer/recommendationCheck?answrSq=" + `${answrSq}&mbrSq=` + sq.value);

      if (res1 >= 1) {
        showConfirm("이미 추천된 답변입니다. 추천을 취소하시겠습니까?", async () => {
          try {
            await api.$post("/answer/recommendation/" + `${answrSq}?mbrSq=` + sq.value + "&value=-1");
            showAlert("답글 추천이 취소됐습니다");
            showAnswerDetail(answrSq);
          } catch (error) {
            showAlert("추천 취소 중 오류 발생", error);
          }
        });
      } else {
        try {
          await api.$post("/answer/recommendation/" + `${answrSq}?mbrSq=` + sq.value + "&value=1");
          showAlert("답글이 추천됐습니다");
          showAnswerDetail(answrSq);
        } catch (error) {
          showAlert("추천 중 오류 발생", error);
        }
      }
    } catch (error) {
      showAlert("추천 실패");
    }
  });
};

const answerNotRcmndtn = async (answrSq) => {
  const sq = ref(0);
  sq.value = member.value.mbrSq || member.value.pk;

  showConfirm("답변을 비추천하시겠습니까?", async () => {
    try {
      const res1 = await api.$post("/answer/notRecommendationCheck?answrSq=" + `${answrSq}&mbrSq=` + sq.value);

      if (res1 >= 1) {
        showConfirm("이미 비추천된 답변입니다. 비추천을 취소하시겠습니까?", async () => {
          try {
            await api.$post("/answer/notRecommendation/" + `${answrSq}?mbrSq=` + sq.value + "&value=-1");
            showAlert("답글 비추천이 취소됐습니다");
            showAnswerDetail(answrSq);
          } catch (error) {
            showAlert("비추천 취소 중 오류 발생", error);
          }
        });
      } else {
        try {
          await api.$post("/answer/notRecommendation/" + `${answrSq}?mbrSq=` + sq.value + "&value=1");
          showAlert("답글이 비추천됐습니다");
          showAnswerDetail(answrSq);
        } catch (error) {
          showAlert("비추천 중 오류 발생", error);
        }
      }
    } catch (error) {
      showAlert("비추천 실패");
    }
  });
};

const deleteAnswer = (answrSq) => {
  showConfirm("삭제된 답글은 복구할 수 없습니다! 답변을 삭제하시겠습니까?", async () => {
    try {
      await api.$delete(`/answer/delete/${answrSq}`);
      showAlert("답글이 삭제됐습니다");
      getAnswerList();
      closeAnswer();
    } catch (error) {
      showAlert("답글 삭제 실패", error);
    }
  });
};

onMounted(async () => {
  await nextTick();
  await getAnswerList();
  console.log("answerList 초기값:", answerList.value);
  try {
    quillInstance.value = new Quill(editor.value, {
      theme: "snow",
      modules: {
        toolbar: "#toolbar",
      },
    });
    const response = await api.$get(`/board/${boardId.value}`);
    board.value = response;
  } catch (error) {
    console.error("Failed to fetch board details:", error);
  }
});

const backToList = () => {
  router.push("/board/qna");
};

const showBoardContent = ref(true);
const showAnswerList = ref(true);

const closeAnswer = () => {
  showAnswer.value = false;
};

</script>


<style>
section {
  padding: 30px 0 !important;
}

.board-detail {
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1) !important;
}

.board-header {
  align-items: flex-start;
}

.board-title {
  font-size: 1.8rem;
  font-weight: bold;
  flex: 1;
  text-align: center;
}

.board-meta {
  text-align: right;
  font-size: 0.9rem;
  display: flex;
  div {
    margin: 10px;
  }
}

.board-content {
  font-size: 1rem;
  line-height: 1.6;
  color: #333;
  min-height: 300px;
  text-align: left;
}

.board-content1 {
  font-size: 1rem;
  line-height: 1.6;
  color: #333;
  min-height: 200px;
  text-align: left;
  max-height: 200px; /* 최대 높이 설정 */
  overflow-y: auto; /* 수직 스크롤 활성화 */
  overflow-x: hidden; /* 수평 스크롤 숨김 */
}

hr {
  margin: 20px 0;
}

.button-container {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}

.button-container .btn {
  min-width: 100px;
  padding: 10px 20px;
}

/* 댓글 */
.comments-section {
  margin-top: 20px;
}

.comment-input {
  margin-bottom: 20px;
}

.comment-textarea {
  width: 100%;
  height: 80px;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  resize: none;
  margin-bottom: 10px;
}

.comment-submit-button {
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.comment-submit-button:hover {
  background-color: #0056b3;
}

.comment-card {
  background-color: #f9f9f9;
  padding: 15px;
  border-radius: 8px;
  margin-bottom: 15px;
}

.comment-header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
}

.comment-author {
  font-weight: bold;
}

.comment-date {
  font-size: 0.9em;
  color: #666;
}

.comment-content {
  font-size: 1em;
}

.comment-actions {
  margin-top: 10px;
  display: flex;
  gap: 10px;
}

.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000; /* 모달이 다른 콘텐츠 위에 오도록 */
}

.modal-content {
  background: white;
  padding: 20px;
  border-radius: 5px;
  width: 300px;
  position: relative;
}

.close-button {
  position: absolute;
  top: 10px;
  right: 10px;
  font-size: 20px;
  cursor: pointer;
}

textarea {
  width: 100%;
  height: 100px;
  margin-top: 10px;
}

.answer {
  height: 1000px; /* 고정된 높이 설정 */
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed; /* 위치를 고정시킴 */
  top: 0; /* 상단에 붙이기 */
  left: 0; /* 좌측에 붙이기 */
  right: 0; /* 우측에 붙이기 */
  bottom: 0; /* 하단에 붙이기 */

  z-index: 1000; /* 다른 요소들보다 위에 위치하도록 설정 */
}

.answerModal {
  height: 800px; /* 모달의 높이 설정 */
  width: 60%; /* 모달의 너비 설정 */
  /* background-color: #F2F2F2; */
  background-color: white;
  text-align: center; /* 텍스트 중앙 정렬 */
  margin: auto; /* 자동 마진으로 중앙에 배치 */
}

@media (max-width: 768px) { /* 모바일 화면에 대한 스타일 */
  .answerModal {
    margin-top: 50px;
    width: 100%;
    max-height: 90%;
    overflow-y: auto;
  }

  .answerModal{
    width: 100%;
  }

  .page-header{
    width: 100%;
  }
}

@media (min-width: 576px) {
    .container-sm, .container {
        max-width: 100%;
    }
}

section {
  padding: 30px 0 !important;
}

.form-container {
  flex-direction: column;
}

.form-group {
  margin-bottom: 20px;
}

.form-group label {
  font-weight: bold;
}

.form-control {
  border-radius: 5px;
  width: 100%; /* 이 속성으로 입력 필드가 컨테이너 너비를 채우게 합니다 */
}

.button-container {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}

.button-container .btn {
  min-width: 100px; /* 버튼 너비를 고정합니다 */
  padding: 10px 20px;
}

/* 에디터 컨테이너 스타일 */
#editor {
  border: 1px solid #ccc;
  padding: 10px;
  background-color: #fff;
}

/* 툴바 버튼 스타일 */
#toolbar .ql-formats button,
#toolbar .ql-formats select {
  margin-right: 5px;
}

/* 툴바 레이아웃 */
#toolbar {
  border: 1px solid #ccc;
  background-color: #f3f3f3;
  padding: 8px;
  margin-bottom: 10px;
}



.answer-list-container {
  max-width: 1100px;
  margin: 0 auto;
  padding: 20px;
}

.header {
  text-align: center;
  margin-bottom: 20px;
}

.answer-content {
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
  width: 50%;
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

.btn-primary {
  padding: 5px 10px;
}
.justify-content-end {
  align-items: center;
}


.slide-btn{
  display: flex;
  justify-content: center;
  align-items: center;
}

.ql-size-large{
  font-size: 2rem;
}

.ql-size-huge{
  font-size: 3rem;
}


</style>
