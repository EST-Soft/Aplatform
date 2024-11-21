<template>
  <div class="container mt-5">
    <!-- 게시글작성자와 로그인사용자 같고 채택된 답변이나 자체해결이 되지않았을 때 -->
    <div
      class="d-flex flex-wrap justify-content-end gap-2 mt-4"
      v-if="isLoggedIn && board.brdCndtn == 'N'"
    >
      <button
        class="btn btn-outline-primary"
        v-if="mbrSqCheck(board.mbrSq)"
        @click="answerSelfSelection(board.brdSq)"
      >
        자체해결
      </button>
      <router-link
        class="btn btn-outline-secondary"
        :to="`/board/qna/update/${boardId}`"
        >수정</router-link
      >
      <button class="btn btn-outline-danger" @click="boardDelete">삭제</button>
      <button class="btn btn-outline-success" @click="backToList">목록</button>
    </div>

    <!-- 게시글작성자와 로그인사용자 같고 채택된 답변이있거나 자체해결을 했을 때 -->
    <div
      class="d-flex flex-wrap justify-content-end gap-2 mt-4"
      v-if="isLoggedIn && board.brdCndtn !== 'N'"
    >
      <router-link
        class="btn btn-outline-secondary"
        :to="`/board/qna/update/${boardId}`"
        >수정</router-link
      >
      <button class="btn btn-outline-danger" @click="boardDelete">삭제</button>
      <button class="btn btn-outline-success" @click="backToList">목록</button>
    </div>

    <!-- 게시글작성자와 로그인사용자 다르고, 답변이 채택되지 않았을 때 -->
    <div
      class="d-flex flex-wrap justify-content-end gap-2 mt-4"
      v-if="
        loginCheck && board.mbrSq !== member.mbrSq && board.brdCndtn !== 'Y'
      "
    >
      <button class="btn btn-outline-success" @click="backToList">목록</button>
      <button
        class="btn btn-outline-danger"
        @click="openReportModal('게시글', board.brdSq, 'POST')"
      >
        <i class="bi bi-exclamation-circle"></i> 신고
      </button>
      <button
        class="btn btn-outline-warning"
        @click="toggleScrap('POST', board.brdSq)"
      >
        <i class="bi" :class="board.isScraped ? 'bi-star-fill' : 'bi-star'"></i>
        {{ board.isScraped ? "스크랩 취소" : "스크랩" }}
      </button>
    </div>

    <!-- 게시글작성자와 로그인사용자 다르고, 답변이 채택되었을 때 -->
    <div
      class="d-flex flex-wrap justify-content-end gap-2 mt-4"
      v-if="loginCheck && board.mbrSq !== member.mbrSq && board.brdCndtn == 'Y'"
    >
      <button class="btn btn-outline-success" @click="backToList">목록</button>
      <button
        class="btn btn-outline-danger"
        @click="openReportModal('게시글', board.brdSq, 'POST')"
      >
        <i class="bi bi-exclamation-circle"></i> 신고
      </button>
      <button
        class="btn btn-outline-warning"
        @click="toggleScrap('POST', board.brdSq)"
      >
        <i class="bi" :class="board.isScraped ? 'bi-star-fill' : 'bi-star'"></i>
        {{ board.isScraped ? "스크랩 취소" : "스크랩" }}
      </button>
    </div>

    <div v-show="isReportModalOpen" class="reportModal">
      <div class="modal-content">
        <h5 class="fw-bold mb-3">{{ reportTarget.type }} 신고하기</h5>
        <p>신고 사유를 선택하세요:</p>
        <div class="d-flex flex-column gap-2 mb-3">
          <label class="form-check">
            <input
              type="checkbox"
              value="스팸/광고"
              v-model="reportReasons"
              class="form-check-input"
            />
            스팸/광고
          </label>
          <label class="form-check">
            <input
              type="checkbox"
              value="부적절한 내용"
              v-model="reportReasons"
              class="form-check-input"
            />
            부적절한 내용
          </label>
          <label class="form-check">
            <input
              type="checkbox"
              value="욕설/비방"
              v-model="reportReasons"
              class="form-check-input"
            />
            욕설/비방
          </label>
          <label class="form-check">
            <input
              type="checkbox"
              value="기타"
              v-model="reportReasons"
              class="form-check-input"
            />
            기타
          </label>
        </div>
        <textarea
          v-model="additionalReason"
          placeholder="추가 사유를 입력하세요"
          class="form-control"
          rows="3"
        ></textarea>
        <div class="d-flex justify-content-between mt-3">
          <button class="btn btn-primary" @click="submitReport">신고</button>
          <button class="btn btn-secondary" @click="closeReportModal">
            취소
          </button>
        </div>
      </div>
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

      <div
        class="container"
        v-show="
          loginCheck &&
          (board.brdCndtn === 'N' || board.brdCndtn === 'U') &&
          board.mbrSq !== member.mbrSq
        "
      >
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
            <div class="editor">
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
        <h3 class="fw-bold my-4">답변</h3>

        <!-- 답변 목록 -->
        <div v-if="answerList.length > 0">
          <div
            v-for="answer in answerList"
            :key="answer.answrSq"
            class="answer-card p-4 mb-4 bg-white rounded shadow"
          >
            <!-- 답변 헤더 -->
            <div class="d-flex justify-content-between align-items-center mb-3">
              <!-- 답변 제목과 채택 버튼 -->
              <div class="d-flex align-items-center">
                <h4 class="fw-bold text-dark me-3 mb-0">
                  {{ answer.answrTtl }}
                </h4>
                <button
                  v-if="
                    mbrSqCheck(board.mbrSq) &&
                    !mbrSqCheck(answer.mbrSq) &&
                    filterSelect &&
                    board.brdCndtn !== 'S'
                  "
                  class="btn btn-success btn-sm"
                  @click="answerSelection(answer.answrSq, board.brdSq)"
                >
                  채택
                </button>
                <span
                  v-if="answer.answrSlctnYn === 'Y'"
                  class="badge bg-success ms-3"
                  style="font-size: 0.9rem"
                >
                  채택됨
                </span>
              </div>

              <!-- 작성자와 날짜 -->
              <div class="text-muted small">
                작성자: {{ answer.createdBy }} / 날짜:
                {{ answer.insrtDtm.slice(0, 10) }}
              </div>
            </div>

            <!-- 답변 내용 -->
            <div v-html="answer.answrCntnt" class="mb-3"></div>

            <!-- 답변 상태 및 버튼 그룹 -->
            <div class="d-flex justify-content-between align-items-center">
              <!-- 추천/비추천 -->
              <div>
                <button
                  class="btn btn-outline-success btn-sm me-2"
                  @click="answerRcmndtn(answer.answrSq)"
                >
                  <i class="bi bi-hand-thumbs-up"></i> 추천 ({{
                    answer.answrRcmndtns
                  }})
                </button>
                <button
                  class="btn btn-outline-danger btn-sm"
                  @click="answerNotRcmndtn(answer.answrSq)"
                >
                  <i class="bi bi-hand-thumbs-down"></i> 비추천 ({{
                    answer.answrNotRcmndtns
                  }})
                </button>
              </div>

              <!-- 액션 버튼 -->
              <div class="d-flex gap-2">
                <button
                  class="btn btn-outline-warning btn-sm"
                  @click="toggleScrap('ANSWER', answer.answrSq)"
                >
                  <i
                    class="bi"
                    :class="answer.isScraped ? 'bi-star-fill' : 'bi-star'"
                  ></i>
                  {{ answer.isScraped ? "스크랩 취소" : "스크랩" }}
                </button>
                <button
                  class="btn btn-danger btn-sm"
                  @click="openReportModal('답글', answer.answrSq, 'ANSWER')"
                >
                  <i class="bi bi-exclamation-circle"></i> 신고
                </button>
                <button
                  v-if="mbrSqCheck(answer.mbrSq)"
                  class="btn btn-primary btn-sm"
                  @click="startEdit(answer)"
                >
                  수정
                </button>
                <button
                  v-if="mbrSqCheck(answer.mbrSq) && answer.answrSlctnYn !== 'Y'"
                  class="btn btn-danger btn-sm"
                  @click="deleteAnswer(answer.answrSq)"
                >
                  삭제
                </button>
              </div>
            </div>

            <!-- 수정 폼 -->
            <div v-if="answer.isEditing" class="mt-3">
              <div class="form-group mb-3">
                <label for="title" class="form-label">제목</label>
                <input
                  type="text"
                  id="answrTtl"
                  v-model="answer.answrTtl"
                  class="form-control"
                  placeholder="제목을 입력하세요"
                />
              </div>
              <div class="form-group mb-4">
                <label for="contents" class="form-label">내용</label>
                <div id="editor3" style="height: 200px"></div>
              </div>
              <div class="d-flex justify-content-end gap-2">
                <button
                  class="btn btn-success"
                  @click="saveEditedAnswer(answer)"
                >
                  저장
                </button>
                <button class="btn btn-secondary" @click="cancelEdit">
                  취소
                </button>
              </div>
            </div>

            <hr />

            <!-- 댓글 섹션 -->
            <div class="comments-section">
              <h6 class="fw-bold">댓글</h6>

              <!-- 댓글 목록 -->
              <div
                v-if="answer.comments && answer.comments.length > 0"
                class="mt-3"
              >
                <div
                  v-for="comment in answer.comments"
                  :key="comment.cmntSq"
                  class="p-3 border rounded shadow-sm mb-3"
                >
                  <div
                    class="d-flex justify-content-between align-items-center"
                  >
                    <!-- 작성자와 날짜 -->
                    <div>
                      <strong>{{ comment.createdBy }}</strong>
                      <span class="text-muted ms-2 small">
                        {{ comment.insrtDtm.slice(0, 10) }}
                      </span>
                    </div>

                    <!-- 신고 및 수정/삭제 버튼 -->
                    <div class="d-flex gap-2">
                      <button
                        class="btn btn-outline-danger btn-sm"
                        @click="
                          openReportModal('댓글', comment.cmntSq, 'COMMENT')
                        "
                      >
                        신고
                      </button>
                      <button
                        v-if="mbrSqCheck(comment.mbrSq)"
                        class="btn btn-outline-primary btn-sm"
                        @click="startEditComment(comment)"
                      >
                        수정
                      </button>
                      <button
                        v-if="mbrSqCheck(comment.mbrSq)"
                        class="btn btn-outline-danger btn-sm"
                        @click="deleteComment(answer.answrSq, comment.cmntSq)"
                      >
                        삭제
                      </button>
                    </div>
                  </div>

                  <!-- 댓글 내용 -->
                  <div v-if="!comment.isEditing" class="mt-2">
                    {{ comment.cmntCntnt }}
                  </div>

                  <!-- 댓글 수정 -->
                  <div v-else class="mt-2">
                    <textarea
                      v-model="comment.editContent"
                      class="form-control"
                      rows="2"
                      placeholder="댓글을 수정하세요"
                    ></textarea>
                    <div class="d-flex justify-content-end gap-2 mt-2">
                      <button
                        class="btn btn-success btn-sm"
                        @click="saveCommentEdit(answer.answrSq, comment)"
                      >
                        저장
                      </button>
                      <button
                        class="btn btn-secondary btn-sm"
                        @click="cancelCommentEdit(comment)"
                      >
                        취소
                      </button>
                    </div>
                  </div>
                </div>
              </div>

              <!-- 댓글 없음 -->
              <div v-else class="text-muted mt-2">등록된 댓글이 없습니다.</div>

              <!-- 댓글 작성 -->
              <div class="mt-3">
                <textarea
                  v-model="newComment[answer.answrSq]"
                  class="form-control"
                  placeholder="댓글을 입력하세요"
                  rows="3"
                ></textarea>
                <div class="d-flex justify-content-end mt-2">
                  <button
                    class="btn btn-primary"
                    @click="addComment(answer.answrSq)"
                  >
                    댓글 추가
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- 답변 없음 -->
        <div v-else class="text-muted">등록된 답변이 없습니다.</div>

        <!-- 페이지네이션 -->
        <div v-if="answerList.length > 0" class="pagination-wrapper mt-4">
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
  </div>

  <div class="modal">
    <div class="modal-content">
      <h5>{{ reportTarget.type }} 신고하기</h5>
      <p>신고 사유를 선택하세요:</p>
      <label>
        <input type="checkbox" value="스팸/광고" v-model="reportReasons" />
        스팸/광고
      </label>
      <label>
        <input type="checkbox" value="부적절한 내용" v-model="reportReasons" />
        부적절한 내용
      </label>
      <label>
        <input type="checkbox" value="욕설/비방" v-model="reportReasons" />
        욕설/비방
      </label>
      <label>
        <input type="checkbox" value="기타" v-model="reportReasons" />
        기타
      </label>
      <textarea
        v-model="additionalReason"
        placeholder="추가 사유를 입력해주세요"
        class="form-control mt-2"
        rows="3"
      ></textarea>
      <div class="button-container mt-3">
        <button class="btn btn-primary me-2" @click="submitReport">신고</button>
        <button class="btn btn-secondary" @click="closeReportModal">
          취소
        </button>
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

import Filter from "badwords-ko";

const store = useStore();
const member = computed(() => store.getters.getMember);

const answer = ref({});
const newComment = ref({});

const editor = ref(null);
const quillInstance = ref(null);
const quillInstance3 = ref(null);
const editingAnswerId = ref(null);

const isReportModalOpen = ref(false); // 모달 상태
const reportReasons = ref([]); // 신고 사유
const additionalReason = ref(""); // 추가 사유 입력
const reportTarget = ref({}); // 신고 대상 정보

// 스크랩 상태 가져오기
const fetchScrapStatus = async (scrapTargetSq, scrapTargetType) => {
  if (!scrapTargetSq || !scrapTargetType) {
    console.warn("스크랩 상태 확인에 필요한 값이 없습니다.");
    return;
  }

  console.log("스크랩 대상 ID:", scrapTargetSq);
  console.log("스크랩 대상 유형:", scrapTargetType);

  try {
    const response = await api.$get(`/api/scrap/exists`, {
      params: {
        mbrSq: member.value.mbrSq, // 로그인 사용자 ID
        scrapTargetSq: scrapTargetSq, // 대상 ID
        scrapTargetType: scrapTargetType, // 대상 유형 ('POST', 'ANSWER')
      },
    });

    if (scrapTargetType === "POST") {
      board.value.isScraped = response; // 게시글 스크랩 상태 저장
      console.log("POST 스크랩 상태:", board.value.isScraped);
    } else if (scrapTargetType === "ANSWER") {
      const targetAnswer = answerList.value.find(
        (answer) => answer.answrSq === scrapTargetSq
      );
      if (targetAnswer) {
        targetAnswer.isScraped = response; // 답변 스크랩 상태
      }
    }
  } catch (error) {
    console.error("스크랩 상태 조회 실패:", error);
  }
};

// 스크랩 추가/취소
const toggleScrap = async (type, id) => {
  try {
    const isScraped =
      type === "POST"
        ? board.value.isScraped
        : answerList.value.find((a) => a.answrSq === id)?.isScraped;

    if (isScraped) {
      // 스크랩 취소
      await api.$patch(`/api/scrap/${id}`);
      console.log(`스크랩 취소 요청: ${id}`);
      showAlert("스크랩이 취소되었습니다.");
    } else {
      // 스크랩 추가
      const params = new URLSearchParams();
      params.append("mbrSq", member.value.mbrSq);
      params.append("scrapTargetSq", id);
      params.append("scrapTargetType", type);

      await api.$post("/api/scrap", params, {
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
      });
      showAlert("스크랩이 추가되었습니다.");
    }

    // 상태 업데이트
    if (type === "POST") {
      board.value.isScraped = !board.value.isScraped;
    } else if (type === "ANSWER") {
      const targetAnswer = answerList.value.find(
        (answer) => answer.answrSq === id
      );
      if (targetAnswer) targetAnswer.isScraped = !targetAnswer.isScraped;
    }
  } catch (error) {
    console.error("스크랩 처리 실패:", error);
    showAlert("스크랩 처리 중 문제가 발생했습니다.");
  }
};

// 모달 열기
const openReportModal = (type, id, category) => {
  reportTarget.value = { type, id, category };
  isReportModalOpen.value = true;
};

// 모달 닫기
const closeReportModal = () => {
  isReportModalOpen.value = false;
  reportReasons.value = [];
  additionalReason.value = "";
};

// 신고 처리
const submitReport = async () => {
  if (reportReasons.value.length === 0 && !additionalReason.value.trim()) {
    alert("신고 사유를 입력하거나 선택해주세요.");
    return;
  }

  const issueReason = [...reportReasons.value, additionalReason.value]
    .filter(Boolean)
    .join(", ");

  try {
    await api.$post("/issues", {
      brdSq: reportTarget.value.id, // 신고 대상 ID
      brdTypCode: reportTarget.value.category, // 유형 코드 (POST, ANSWER, COMMENT)
      issueTyp: reportTarget.value.category, // 신고 카테고리
      issueRsn: issueReason, // 신고 사유
      mbrSq: member.value.mbrSq, // 로그인 사용자 ID
    });
    showAlert(`${reportTarget.value.type}이(가) 신고되었습니다.`);
  } catch (error) {
    console.error("신고 처리 실패:", error);
    showAlert(`${reportTarget.value.type} 신고에 실패했습니다.`);
  } finally {
    closeReportModal();
  }
};

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
    showAlert(
      "사용할 수 없는 단어가 제목에 포함되어 있습니다. 제목을 수정해주세요."
    );
    return;
  }

  if (isProfaneContent) {
    showAlert(
      "사용할 수 없는 단어가 내용에 포함되어 있습니다. 내용을 수정해주세요."
    );
    return;
  }

  if (!answer.value.answrTtl || !sanitizedContent) {
    showAlert(
      !answer.value.answrTtl ? "제목을 입력하세요" : "내용을 입력하세요"
    );
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
      }, 500);
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

// 댓글 수정 시작
const startEditComment = (comment) => {
  comment.isEditing = true;
  comment.editContent = comment.cmntCntnt; // 기존 내용을 복사
};

// 댓글 수정 저장
const saveCommentEdit = async (answrSq, comment) => {
  if (!comment.editContent.trim()) {
    showAlert("수정할 내용을 입력하세요.");
    return;
  }

  try {
    // 댓글 수정 요청
    const response = await api.$put(`/comments/${comment.cmntSq}`, {
      cmntCntnt: comment.editContent.trim(),
    });

    // 수정된 댓글 반영 (response가 댓글 객체 전체를 반환한다고 가정)
    if (response && response.data) {
      Object.assign(comment, response.data); // 기존 객체 유지하며 새로운 데이터만 업데이트
    } else {
      comment.cmntCntnt = comment.editContent.trim(); // 기본적으로 입력값 사용
    }

    comment.isEditing = false; // 수정 상태 종료
    showAlert("댓글이 수정되었습니다.");
  } catch (error) {
    console.error("댓글 수정 실패:", error);
    showAlert("댓글 수정에 실패했습니다.");
  }
};

// 댓글 수정 취소
const cancelCommentEdit = (comment) => {
  comment.isEditing = false;
  comment.editContent = ""; // 임시 내용 초기화
};

const saveEditedAnswer = async (answer) => {
  if (quillInstance3.value) {
    answer.answrCntnt = quillInstance3.value.root.innerHTML.trim();
  }

  try {
    await api.$patch(`/answer`, answer);
    showAlert("답변이 수정되었습니다.");
    cancelEdit(); // 수정 완료 후 창 닫기
  } catch (error) {
    console.error("답변 수정 실패:", error);
    showAlert("답변 수정에 실패했습니다.");
  }
};

const cancelEdit = () => {
  if (editingAnswerId.value !== null) {
    // 수정 상태 해제
    const answer = answerList.value.find(
      (a) => a.answrSq === editingAnswerId.value
    );
    if (answer) {
      answer.isEditing = false;
    }

    // Quill 인스턴스 제거
    if (quillInstance3.value) {
      quillInstance3.value = null;
    }

    // 현재 수정 중인 답변 ID 초기화
    editingAnswerId.value = null;
  }
};

// 수정 모드 시작 함수
const startEdit = async (answer) => {
  // 기존 수정 창 닫기
  if (editingAnswerId.value !== null) {
    cancelEdit(); // 현재 수정 중인 답글 초기화
  }

  // 현재 수정 중인 답변 ID 설정
  editingAnswerId.value = answer.answrSq;

  // 수정 상태 활성화
  answer.isEditing = true;

  // DOM이 렌더링될 때까지 대기
  await nextTick();

  // Quill 인스턴스 생성
  if (!quillInstance3.value) {
    const editorElement = document.querySelector("#editor3");
    if (editorElement) {
      quillInstance3.value = new Quill(editorElement, {
        theme: "snow",
        modules: {
          toolbar: "#toolbar3",
        },
      });
    }
  }

  // 수정 중인 답변 내용 로드
  if (quillInstance3.value) {
    quillInstance3.value.root.innerHTML = answer.answrCntnt || "";
  }
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
const board = ref({
  isScraped: false, // 스크랩 여부 초기화
  brdTtl: "",
  brdCntnt: "",
  createdBy: "",
  brdCndtn: "",
  // 추가 속성들
});
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
  {
    answrSq: 1,
    answrTtl: "제목",
    answrCntnt: "내용",
    createdBy: "작성자",
    insrtDtm: "2024-11-13",
    isEditing: false,
  },
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
    await api.$post(`/comments/answer/${answrSq}`, {
      cmntCntnt: content,
      mbrSq: mbrSq,
    });
    fetchComments(answrSq);
    newComment.value[answrSq] = "";
  } catch (error) {
    showAlert("댓글 추가 실패");
    console.error("Error adding comment:", error);
  }
};

// 댓글 조회
const fetchComments = async (answrSq) => {
  try {
    const response = await api.$get(`/comments/answer/${answrSq}`);
    const comments = response.data || response; // 응답 데이터 확인
    const answer = answerList.value.find((a) => a.answrSq === answrSq);
    if (answer) {
      answer.comments = comments; // 댓글 데이터 전체를 매핑
    }
  } catch (error) {
    console.error("댓글 로딩 실패:", error);
  }
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
    const data = await api.$get(
      "/answer/qna/" + route.params.id + "?" + queryString
    );
    answerList.value = data.data || [];

    // 답변 리스트 내 각 답변의 댓글 불러오기
    answerList.value.forEach((answer) => fetchComments(answer.answrSq));

    if (answerList.value.some((answer) => answer.answrSlctnYn === "Y")) {
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
      const res1 = await api.$post(
        "/answer/recommendationCheck?answrSq=" + `${answrSq}&mbrSq=` + sq.value
      );

      if (res1 >= 1) {
        showConfirm(
          "이미 추천된 답변입니다. 추천을 취소하시겠습니까?",
          async () => {
            try {
              await api.$post(
                "/answer/recommendation/" +
                  `${answrSq}?mbrSq=` +
                  sq.value +
                  "&value=-1"
              );
              showAlert("답글 추천이 취소됐습니다");
              showAnswerDetail(answrSq);
            } catch (error) {
              showAlert("추천 취소 중 오류 발생", error);
            }
          }
        );
      } else {
        try {
          await api.$post(
            "/answer/recommendation/" +
              `${answrSq}?mbrSq=` +
              sq.value +
              "&value=1"
          );
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
      const res1 = await api.$post(
        "/answer/notRecommendationCheck?answrSq=" +
          `${answrSq}&mbrSq=` +
          sq.value
      );

      if (res1 >= 1) {
        showConfirm(
          "이미 비추천된 답변입니다. 비추천을 취소하시겠습니까?",
          async () => {
            try {
              await api.$post(
                "/answer/notRecommendation/" +
                  `${answrSq}?mbrSq=` +
                  sq.value +
                  "&value=-1"
              );
              showAlert("답글 비추천이 취소됐습니다");
              showAnswerDetail(answrSq);
            } catch (error) {
              showAlert("비추천 취소 중 오류 발생", error);
            }
          }
        );
      } else {
        try {
          await api.$post(
            "/answer/notRecommendation/" +
              `${answrSq}?mbrSq=` +
              sq.value +
              "&value=1"
          );
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
  showConfirm(
    "삭제된 답글은 복구할 수 없습니다! 답변을 삭제하시겠습니까?",
    async () => {
      try {
        await api.$delete(`/answer/delete/${answrSq}`);
        showAlert("답글이 삭제됐습니다");
        getAnswerList();
        closeAnswer();
      } catch (error) {
        showAlert("답글 삭제 실패", error);
      }
    }
  );
};

onMounted(async () => {
  try {
    await nextTick();

    // 게시글 정보 가져오기
    const boardResponse = await api.$get(`/board/${boardId.value}`);
    board.value = boardResponse;

    // Quill 에디터 초기화
    quillInstance.value = new Quill(editor.value, {
      theme: "snow",
      modules: {
        toolbar: "#toolbar",
      },
    });

    // 게시글 스크랩 상태 확인
    if (boardId.value) {
      await fetchScrapStatus(boardId.value, "POST");
    }

    // 답변 목록 가져오기
    await getAnswerList();

    // 답변 스크랩 상태 확인
    if (answerList.value && answerList.value.length > 0) {
      for (const answer of answerList.value) {
        await fetchScrapStatus(answer.answrSq, "ANSWER");
      }
    }
  } catch (error) {
    console.error("Failed to initialize page:", error);
  }
});

const backToList = () => {
  router.push("/board/qna");
};

const showBoardContent = ref(true);

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
  max-width: 90%;
  text-align: center;
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

@media (max-width: 768px) {
  /* 모바일 화면에 대한 스타일 */
  .answerModal {
    margin-top: 50px;
    width: 100%;
    max-height: 90%;
    overflow-y: auto;
  }

  .answerModal {
    width: 100%;
  }

  .page-header {
    width: 100%;
  }
}

@media (min-width: 576px) {
  .container-sm,
  .container {
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

.slide-btn {
  display: flex;
  justify-content: center;
  align-items: center;
}

.ql-size-large {
  font-size: 2rem;
}

.ql-size-huge {
  font-size: 3rem;
}

.reportModal {
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

.reportModal .modal-content {
  background: white;
  padding: 20px;
  border-radius: 8px;
  max-width: 400px;
  width: 100%;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  text-align: center;
}
</style>
