<template>
  <div class="container mt-5">
    <section
      class="page-header page-header-modern bg-color-grey page-header-lg text-center"
    >
      <div class="container">
        <div class="row">
          <div class="col-md-12 align-self-center p-static order-2 text-center">
            <h1 class="font-weight-bold text-dark">QnA 게시판 - 글 작성</h1>
          </div>
        </div>
      </div>
    </section>
    <!-- 게시글 작성 폼 시작 -->
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
        <button class="btn btn-success" @click="saveBoard">저장</button>
        <button class="btn btn-secondary" @click="backToList">취소</button>
      </div>
    </div>
    <!-- 게시글 작성 폼 끝 -->
  </div>
</template>

<script setup>
import { computed, ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { api } from "../../../../axios";
import { showAlert, showConfirm } from "../../../../utill/utillModal";
import { useStore } from "vuex";
import Quill from "quill";

const store = useStore();
// Vue Router를 사용하여 페이지 이동을 처리
const router = useRouter();
const member = computed(() => store.getters.getMember);
console.log(member.value.mbrSq);
// 게시글 정보를 관리할 상태 선언
const editor = ref(null);

const quillInstance = ref(null); // Quill 인스턴스를 저장할 ref

onMounted(() => {
  quillInstance.value = new Quill(editor.value, {
    theme: "snow",
    modules: {
      toolbar: "#toolbar", // 커스텀 툴바만 사용
    },
  });
});
const board = ref({
  brdTtl: "",
  brdCntnt: "",
  brdGnrCd: "qna",
});

// 게시글 작성 폼 입력값 처리
const saveBoard = async () => {
  const contentHtml = quillInstance.value.root.innerHTML.trim();
  // 불필요한 비어있는 태그들을 제거
  const sanitizedContent = contentHtml.replace(/<h2><br><\/h2>/g, "").trim();

  if (!board.value.brdTtl || !sanitizedContent) {
    showAlert(!board.value.brdTtl ? "제목을 입력하세요" : "내용을 입력하세요");
    return;
  }

  showConfirm("게시글을 등록 하시겠습니까?", async () => {
    if (member.value.mbrSq) {
      board.value.mbrSq = member.value.mbrSq;
    } else if (member.value.pk) {
      board.value.entrprsSq = member.value.pk;
    }
    console.log(board);
    try {
      board.value.brdCntnt = contentHtml;
      await api.$post("/board", board.value);
      showAlert("등록되었습니다.");
      setTimeout(() => {
        router.push("/board/qna"); // 게시글 목록으로 이동
      }, 2000);
    } catch (error) {
      console.error("Error saving board:", error);
    }
  });

  //   }
};

// 게시글 목록으로 돌아가는 함수

const backToList = () => {
  showConfirm("작성을 취소 하시겠습니까?", () => {
    router.push("/board/qna");
  });
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
</style>
