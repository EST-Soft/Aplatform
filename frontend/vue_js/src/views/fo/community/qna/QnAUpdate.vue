<template>
  <div class="container mt-5">
    <section
      class="page-header page-header-modern bg-color-grey page-header-lg text-center"
    >
      <div class="container">
        <div class="row">
          <div class="col-md-12 align-self-center p-static order-2 text-center">
            <h1 class="font-weight-bold text-dark">QnA게시판 - 글 수정</h1>
          </div>
        </div>
      </div>
    </section>
    <!-- 게시글 수정 폼 시작 -->
    <div class="form-container shadow-sm p-4 bg-white rounded">
      <div class="form-group mb-3">
        <label for="title" class="form-label">제목</label>
        <input
          type="text"
          id="brdTtl"
          v-model="board.brdTtl"
          placeholder="제목을 입력하세요"
          class="form-control"
        />
      </div>

      <div class="form-group mb-4">
        <label for="contents" class="form-label">내용</label>
        <!-- <textarea
          id="brdCntnt"
          v-model="board.brdCntnt"
          placeholder="내용을 입력하세요"
          class="form-control"
          rows="10"
        ></textarea> -->
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
        <button class="btn btn-success" @click="updateBoard">수정</button>
        <button class="btn btn-secondary" @click="backToList">취소</button>
      </div>
    </div>
    <!-- 게시글 수정 폼 끝 -->
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import { useRouter, useRoute } from "vue-router";
import { api } from "../../../../axios";
import { showAlert, showConfirm } from "../../../../utill/utillModal";
import { useStore } from "vuex";
import Quill from "quill";
import "quill/dist/quill.snow.css";

const router = useRouter();
const route = useRoute();
const boardId = route.params.id;
const store = useStore();
const member = computed(() => store.getters.getMember);

const board = ref({
  brdSq: boardId,
  brdTtl: "",
  brdCntnt: "",
});

const editor = ref(null);
const quillInstance = ref(null);

onMounted(() => {
  quillInstance.value = new Quill(editor.value, {
    theme: "snow",
    modules: {
      toolbar: "#toolbar",
    },
  });

  // Quill 초기화가 완료된 후에 데이터를 로드합니다.
  loadBoard();
});

const loadBoard = async () => {
  try {
    const res = await api.$get(`/board/${boardId}`);
    board.value = res;

    const memberId = member.value.mbrId || member.value.entrprsId;
    if (memberId !== board.value.createdBy) {
      showAlert("잘못된 진입경로 입니다.");
      router.push("/board");
      return;
    }

    if (quillInstance.value && board.value.brdCntnt) {
      quillInstance.value.root.innerHTML = board.value.brdCntnt;
    }
  } catch (error) {
    console.error("Error loading board:", error);
    showAlert("게시글을 불러오는 데 실패했습니다.");
  }
};

const updateBoard = async () => {
  const contentHtml = quillInstance.value.root.innerHTML.trim();

  // 빈 태그를 제거하는 정규식: 빈 h2, p 등의 태그를 처리
  const sanitizedContent = contentHtml
    .replace(/<h2><br><\/h2>|<p><br><\/p>/g, "")
    .trim();

  if (!board.value.brdTtl || !sanitizedContent) {
    showAlert(!board.value.brdTtl ? "제목을 입력하세요" : "내용을 입력하세요");
    return;
  }

  showConfirm("게시글을 수정 하시겠습니까?", async () => {
    try {
      board.value.brdCntnt = contentHtml;
      await api.$patch(`/board`, board.value);
      showAlert("수정되었습니다.");
      setTimeout(() => {
        window.location.href = `/board/qna/${boardId}`;
      }, 2000);
    } catch (error) {
      console.error("Error updating board:", error);
    }
  });
};

const backToList = () => {
  showConfirm(
    "수정을 취소 하시겠습니까? 수정한 내용은 복구가 안됩니다.",
    () => {
      window.location.href = `/board/qna/${boardId}`;
    }
  );
};
</script>

<style scoped>
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
</style>
