<template>
  <div class="container mt-5">
    <section
      class="page-header page-header-modern bg-color-grey page-header-lg"
    >
      <div class="container">
        <div class="row">
          <div class="col-md-12 align-self-center p-static order-2 text-center">
            <h1 class="font-weight-bold text-dark">일반회원 가입</h1>
          </div>
        </div>
      </div>
    </section>

    <div class="form-container">
      <form @submit.prevent="handleSignup">
        <div
          v-for="(user, index) in userFields"
          :key="index"
          class="form-group"
        >
          <label>{{ user.label }}</label>
          <input
            :type="user.type"
            class="form-control"
            :name="user.name"
            v-model="formData[user.name]"
            :placeholder="user.placeholder ? user.placeholder : ''"
            @input="checkValidity(user.name)"
          />
          <!-- 유효성 검사 오류 메시지 표시 -->
          <div
            v-if="validationErrors[user.name]"
            :class="
              validationErrors[user.name]?.err ? 'text-danger' : 'text-success'
            "
          >
            {{ validationErrors[user.name]?.message }}
          </div>
        </div>

        <!-- 성별 체크 -->
        <div class="form-group">
          <label>성별</label>
          <div class="d-flex align-items-center gender">
            <input
              type="radio"
              id="male"
              value="m"
              v-model="formData.gndrTypCd"
            />
            <label for="male">남성</label>
            <input
              type="radio"
              id="female"
              value="f"
              v-model="formData.gndrTypCd"
              class="ml-3"
            />
            <label for="2">여성</label>
          </div>
        </div>

        <!-- 이메일 인증 -->
        <div class="form-group">
          <label>이메일</label>
          <div class="d-flex align-items-center">
            <input
              type="email"
              class="form-control email-input"
              v-model="formData.mbrEmlAdrs"
              :disabled="emailSent"
              name="mbrEmlAdrs"
            />
            <button
              type="button"
              class="btn btn-primary ml-2 auth-button"
              @click="requestAuthCode"
              :disabled="emailSent || loading"
            >
              인증
            </button>
          </div>
          <div v-if="loading" class="spinner-border text-primary" role="status">
            <span class="sr-only">Loading...</span>
          </div>
        </div>

        <div v-if="emailSent" class="form-group">
          <label>인증번호</label>
          <div class="d-flex align-items-center">
            <input
              type="text"
              class="form-control auth-code-input"
              v-model="formData.authCode"
              :disabled="emailChack"
            />
            <button
              type="button"
              class="btn btn-primary ml-2 auth-button"
              @click="verifyAuthCode"
              :disabled="emailChack"
            >
              확인
            </button>
          </div>
          <!-- 이메일 수신 동의 -->
          <div class="form-group">
            <label>이메일 수신 동의</label>
            <div class="d-flex align-items-center">
              <input
                type="checkbox"
                v-model="formData.mbrEmlRcvYn"
                true-value="Y"
                false-value="N"
              />
              <label class="ml-2">동의합니다</label>
            </div>
          </div>
        </div>

        <!-- 약관 동의 -->
        <div class="form-group">
          <label>약관동의</label>
          <div class="d-flex align-items-center">
            <input
              type="checkbox"
              v-model="agreed"
              :disabled="true"
              name="mbrPrvcyTrmsChck"
            />
            <button
              type="button"
              class="btn btn-link"
              @click="showModal = true"
            >
              약관 보기
            </button>
          </div>
        </div>
        <!-- 포지션 제안 수락 동의 -->
        <div class="form-group">
          <label>포지션 제안 수락 동의</label>
          <div class="d-flex align-items-center">
            <input
              type="checkbox"
              v-model="formData.pstnPrpslAcceptYn"
              true-value="Y"
              false-value="N"
            />
            <label class="ml-2">동의합니다</label>
          </div>
        </div>

        <button type="submit" class="btn btn-primary">회원가입완료</button>
      </form>
    </div>

    <b-modal v-model="showModal" title="개인정보 수집 이용 동의" centered>
      <div class="modal-body">
        <TermsText />
      </div>
      <template #footer>
        <button
          type="button"
          class="btn btn-secondary"
          @click="handleTermsDisagreement"
        >
          동의안함
        </button>
        <button
          type="button"
          class="btn btn-primary"
          @click="handleTermsAgreement"
        >
          동의하기
        </button>
      </template>
    </b-modal>
  </div>
</template>

<script setup>
import { ref } from "vue";
import TermsText from "./TermText.vue"; // 적절한 경로로 변경하세요
import { BModal } from "bootstrap-vue-3";
import { showAlert } from "../../../utill/utillModal";
import { api } from "../../../axios";
import router from "../../../router";
const validationErrors = ref({});
const loading = ref(false);
const emailChack = ref(false);
const showModal = ref(false);
const emailCode = ref("");
const agreed = ref(false);
const emailSent = ref(false);
const emailError = ref("");
const formData = ref({
  gndrTypCd: "", //성별
  mbrBd: "", //생일
  mbrId: "",
  mbrPswrd: "",
  // confirmPassword: "",
  mbrName: "",
  mbrMp: "",
  mbrEmlAdrs: "",
  mbrEmlRcvChck: "N", //이메일수신동의 y/n
  mbrPrvcyTrmsChck: "N", //약관동의 y/n
  pstnPrpslAcceptYn: "N", //제안 수락동의 y/n
  authCode: "",
});

const userFields = ref([
  {
    label: "아이디",
    name: "mbrId",
    type: "text",
    placeholder: "10자 이내 영어숫자만 가능합니다",
  },
  { label: "비밀번호", name: "mbrPswrd", type: "password" },
  { label: "비밀번호 확인", name: "confirmPassword", type: "password" },
  { label: "이름", name: "mbrName", type: "text" },
  {
    label: "휴대폰번호",
    name: "mbrMp",
    type: "text",
    placeholder: "01012341234",
  },
  { label: "생년월일", name: "mbrBd", type: "text", placeholder: "예) 990406" },
]);

const checkValidity = async (fieldName) => {
  switch (fieldName) {
    case "confirmPassword":
      validationErrors.value[fieldName] =
        formData.value.mbrPswrd !== formData.value.confirmPassword
          ? {
              message: "비밀번호가 일치하지 않습니다.",
              err: true,
            }
          : { message: "비밀번호가 일치합니다." };
      break;
    case "mbrMp":
      // if (!/^\d{0,11}$/.test(formData.value.mbrMp)) {
      //   validationErrors.value[fieldName] = {
      //     message: "11자리의 숫자만 입력할 수 있습니다.",
      //     err: true,
      //   };
      // }
      formData.value.mbrMp = formData.value.mbrMp
        .replace(/\D/g, "")
        .slice(0, 11); // 숫자 이외의 문자 제거 및 11자리까지 제한
      break;
    case "mbrName":
      formData.value.mbrName = formData.value.mbrName
        .replace(/[^가-힣]/g, "")
        .slice(0, 7); // 7자리까지만

      break;
    case "mbrId":
      formData.value.mbrId = formData.value.mbrId
        .replace(/[^a-zA-Z0-9]/g, "") // 영어와 숫자 이외의 문자를 제거
        .slice(0, 10); // 최대 10자까지 제한

      try {
        const res = await api.$post("/member/idCheck", {
          mbrId: formData.value.mbrId,
        });
        if (res === 1) {
          validationErrors.value[fieldName] = {
            message: "이미 존재하는 아이디입니다.",
            err: true,
          };
        } else {
          validationErrors.value[fieldName] = {
            message: "사용 가능한 아이디입니다.",
          };
        }
      } catch (error) {
        console.error("서버 요청 중 오류가 발생했습니다:", error);
        validationErrors.value[fieldName] = {
          message: "서버 오류가 발생했습니다.",
          err: true,
        };
      }
      break;

    case "mbrBd":
      // if (!/^\d{0,6}$/.test(formData.value.mbrBd)) {
      //   validationErrors.value[fieldName] = {
      //     message: "6자리의 숫자만 입력할 수 있습니다.",
      //     err: true,
      //   };
      // }
      formData.value.mbrBd = formData.value.mbrBd
        .replace(/\D/g, "")
        .slice(0, 6); // 7자리까지만
      break;
  }
};
const validateEmail = (email) => {
  const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return re.test(email);
};

const handleTermsAgreement = () => {
  formData.value.mbrPrvcyTrmsChck = "Y";
  agreed.value = true;
  showModal.value = false;
};

const handleTermsDisagreement = () => {
  agreed.value = false;
  showModal.value = false;
};

const requestAuthCode = () => {
  if (!formData.value.mbrEmlAdrs) {
    emailError.value = "이메일을 입력해주세요.";
    return;
  } else if (!validateEmail(formData.value.mbrEmlAdrs)) {
    emailError.value = "유효한 이메일 주소를 입력해주세요.";
    return;
  }
  emailError.value = "";

  // 이메일로 인증번호 요청 로직 추가
  const map = {
    mbrEmlAdrs: formData.value.mbrEmlAdrs,
  };
  loading.value = true;
  api
    .$post("/member/emlRegister", map)
    .then((response) => {
      emailCode.value = response.key;
      emailSent.value = true;
      showAlert("인증번호가 이메일로 전송되었습니다.");
      console.log(response.key);
    })
    .catch((error) => {
      console.error("이메일 전송 중 오류 발생:", error);
      if (error.response && error.response.data) {
        showAlert(error.response.data.error);
      } else {
        showAlert("이메일 전송 중 오류가 발생했습니다.");
      }
      // 전송 실패 시 버튼 및 입력 필드를 다시 활성화
      emailSent.value = false;
    })
    .finally(() => {
      loading.value = false;
    });
};

const verifyAuthCode = () => {
  if (!formData.value.authCode) {
    showAlert("인증번호를 입력해주세요.");
    return;
  }
  // 인증번호 확인 로직 추가
  if (emailCode.value === formData.value.authCode.trim("")) {
    showAlert("인증번호가 확인되었습니다.");
    emailChack.value = true;
  } else {
    showAlert("인증번호가 맞지 않습니다.");
  }
};

const handleSignup = async () => {
  // 폼데이터에서 빈 값 체크
  for (const value of Object.values(formData.value)) {
    if (!value || value.trim() === "") {
      showAlert("입력하지 않은 정보가 있습니다.");
      return;
    }
  }

  // 약관 동의 및 이메일 인증 여부 확인
  if (!agreed.value) {
    showAlert("약관 동의를 하셔야 합니다.");
    return;
  }
  if (!emailSent.value || !emailChack.value) {
    showAlert("이메일 인증을 완료해주세요.");
    return;
  }

  console.log(formData.value);
  // 회원가입 로직 처리
  try {
    const res = await api.$post("/member/register", formData.value);
    showAlert(res + "님 로그인 페이지로 이동합니다."); // 사용자에게 환영 메시지를 알림으로 표시
    setTimeout(() => {
      router.push("/user/login");
    }, 2000);
  } catch (error) {
    console.error("로그인 오류:", error);
    showAlert("로그인에 실패했습니다. 다시 시도해주세요.");
  }
};
</script>

<style scoped>
form {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 30px auto;
}
.form-container {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
  background-color: #fff;
  border-radius: 5px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}
.form-group {
  width: 70%;
  input {
    box-shadow: none;
  }
}
.email-input,
.auth-code-input {
  flex: 1;
}
.auth-button {
  flex-shrink: 0;
}
.modal-body {
  max-height: 400px; /* 모달 높이 제한 */
  overflow-y: auto; /* 세로 스크롤바 추가 */
}
.modal-dialog {
  max-width: 80%; /* 모달의 최대 너비를 화면의 80%로 제한 */
}
.gender {
  label {
    margin: 10px;
  }
}
</style>
