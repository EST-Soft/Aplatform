<template>
  <div class="container mt-5">
    <section
      class="page-header page-header-modern bg-color-grey page-header-lg text-center"
    >
      <div class="container">
        <div class="row">
          <div class="col-md-12 align-self-center p-static order-2 text-center">
            <h1 class="font-weight-bold text-dark">일반게시판 - 글 보기</h1>
          </div>
        </div>
      </div>
    </section>

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
      <div class="board-content p-4">
        <div v-html="board.brdCntnt"></div>
      </div>
      <hr />

      <div class="d-flex justify-content-end mt-4" v-if="isLoggedIn">
        <router-link
          class="btn btn-primary me-2"
          :to="`/board/update/${boardId}`"
          >수정</router-link
        >
        <button class="btn btn-danger me-2" @click="boardDelete">삭제</button>
        <button class="btn btn-secondary" @click="backToList">취소</button>
      </div>
      <div class="comments-section">
        <div v-if="loginCheck" class="comment-input">
          <textarea
            v-model="newComment"
            placeholder="댓글을 입력하세요"
            class="comment-textarea"
          ></textarea>
          <button @click="submitComment" class="comment-submit-button">
            댓글 달기
          </button>
        </div>

        <div
          v-for="comment in board.comments"
          :key="comment.cmntSq"
          class="comment-card"
        >
          <div class="comment-header">
            <p class="comment-author">
              작성자:
              <span v-if="comment.mbrSq">
                {{ comment.member.mbrId }}
              </span>
              <span v-else>
                {{ comment.entMember.entrprsId }}
              </span>
            </p>
            <p class="comment-date">{{ formatDate(comment.insrtDtm) }}</p>
          </div>
          <div class="comment-content">
            <textarea
              v-if="editingCommentId === comment.cmntSq"
              v-model="editedContent"
              class="comment-textarea"
            ></textarea>
            <p v-else>
              {{ comment.cmntCntnt }}
            </p>
          </div>
          <!-- 수정/삭제 버튼 표시 -->
          <div
            v-if="isCommentOwner(comment) && comment.dltYn === 'N'"
            class="comment-actions"
          >
            <button
              v-if="editingCommentId !== comment.cmntSq"
              @click="editComment(comment)"
              class="btn btn-sm btn-secondary"
            >
              수정
            </button>
            <button
              v-else
              @click="updateComment(comment)"
              class="btn btn-sm btn-primary"
            >
              확인
            </button>
            <button
              v-if="editingCommentId === comment.cmntSq"
              @click="cancelEdit"
              class="btn btn-sm btn-warning"
            >
              취소
            </button>
            <button
              @click="deleteComment(comment.cmntSq)"
              class="btn btn-sm btn-danger"
            >
              삭제
            </button>
          </div>
        </div>
      </div>
    </div>
    <!-- 게시글 상세 내용 끝 -->
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import { useRoute, useRouter } from "vue-router";
import { api } from "@/axios";
import { showAlert, showConfirm } from "../../../../utill/utillModal";
import { useStore } from "vuex";

const newComment = ref("");
const editingCommentId = ref(null);
const editedContent = ref("");

const store = useStore();
const member = computed(() => store.getters.getMember);
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

// const isCommentOwner = (comment) => {
//   if (!member.value) return false; // 비로그인 상태 처리
//   return (
//     comment.mbrSq === member.value.mbrSq ||
//     comment.entrprsSq === member.value.pk
//   );
// };

const editComment = (comment) => {
  editingCommentId.value = comment.cmntSq;
  editedContent.value = comment.cmntCntnt;
};

const cancelEdit = () => {
  editingCommentId.value = null;
  editedContent.value = "";
};

const updateComment = async (comment) => {
  console.log("진입", comment);
  showConfirm("수정 하시겠습니까?", async () => {
    try {
      const data = {
        cmntSq: comment.cmntSq,
        cmntCntnt: editedContent.value,
      };
      const res = await api.$patch(`/comment`, data);
      if (res) {
        showAlert("댓글이 수정되었습니다.");
        comment.cmntCntnt = editedContent.value;
        cancelEdit();
      }
    } catch (error) {
      showAlert("댓글 수정에 실패하였습니다.");
    }
  });
};

const deleteComment = async (cmntSq) => {
  showConfirm("정말 삭제하시겠습니까?", async () => {
    try {
      const res = await api.$delete(`/comment/${cmntSq}`);
      if (res) {
        showAlert("댓글이 삭제되었습니다.");
        // UI에서 해당 댓글의 내용을 "삭제된 댓글 입니다"로 변경
        const comment = board.value.comments.find(
          (comment) => comment.cmntSq === cmntSq
        );
        if (comment) {
          board.value.comments = board.value.comments.filter(
            (cmnt) => cmnt !== comment
          );
        }
      }
    } catch (error) {
      showAlert("댓글 삭제에 실패하였습니다.");
    }
  });
};

const submitComment = async () => {
  if (newComment.value.trim() === "") {
    showAlert("댓글을 입력하세요.");
    return;
  }

  const mbrSq = member.value.mbrSq || null;
  const entrprsSq = member.value.pk || null;

  const data = {
    cmntCntnt: newComment.value,
    brdSq: board.value.brdSq,
    mbrSq: mbrSq,
    entrprsSq: entrprsSq,
  };

  try {
    const res = await api.$post("/comment", data);
    if (res) {
      showAlert("댓글이 등록되었습니다.");

      const newCommentData = {
        cmntSq: res, // 서버에서 반환된 새로운 댓글의 ID
        mbrSq: mbrSq,
        entrprsSq: entrprsSq,
        cmntCntnt: newComment.value,
        insrtDtm: new Date().toISOString(),
        member: mbrSq ? member.value : null,
        entMember: entrprsSq ? member.value : null,
        dltYn: "N",
      };

      // 추가된 댓글을 현재 화면에 바로 반영
      board.value.comments.push(newCommentData);

      // 입력 필드 초기화
      newComment.value = "";
    }
  } catch (error) {
    showAlert("댓글 등록에 실패하였습니다.");
    console.error("댓글 등록 실패:", error);
  }

  newComment.value = "";
};

// 댓글 소유자 확인 함수
const isCommentOwner = (comment) => {
  if (!member.value) return false; // 비로그인 상태 처리
  return (
    comment.mbrSq === member.value.mbrSq ||
    comment.entrprsSq === member.value.pk
  );
};

const formatDate = (date) => {
  const options = {
    year: "numeric",
    month: "long",
    day: "numeric",
    hour: "2-digit",
    minute: "2-digit",
  };
  return new Date(date).toLocaleString(undefined, options);
};

const boardDelete = () => {
  showConfirm(
    "정말 삭제하시겠습니까? 삭제한 데이터는 복구할 수 없습니다.",
    async () => {
      try {
        await api.$delete(`board/${boardId.value}`);
        showAlert("삭제 완료되었습니다.");
        setTimeout(() => {
          router.push("/board");
        }, 2000);
      } catch (error) {
        console.error("삭제 실패:", error);
        showAlert("삭제에 실패하였습니다.");
      }
    }
  );
};

onMounted(async () => {
  try {
    const response = await api.$get(`/board/${boardId.value}`);
    board.value = response;
  } catch (error) {
    console.error("Failed to fetch board details:", error);
  }
});

const backToList = () => {
  router.push("/board");
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
  min-height: 300px; /* 내용이 충분히 공간을 차지하도록 */
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
</style>
