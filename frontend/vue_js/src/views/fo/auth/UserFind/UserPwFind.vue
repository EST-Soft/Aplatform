<template>
  <form @submit.prevent="onSubmit">
    <div class="row">
      <div class="form-group col">
        <label class="form-label mb-1 text-2">아이디</label>
        <input
          type="text"
          maxlength="100"
          class="form-control text-3 h-auto py-2"
          name="mbrId"
          placeholder="아이디를 입력해주세요"
          autocomplete="off"
          v-model="formData.mbrId"
          @focus="handleFocus"
          @blur="handleBlur"
        />
      </div>
    </div>
    <div class="row">
      <div class="form-group col">
        <label class="form-label mb-1 text-2">이름</label>
        <input
          type="text"
          maxlength="100"
          class="form-control text-3 h-auto py-2"
          name="mbrName"
          placeholder="이름을 입력해주세요"
          autocomplete="off"
          v-model="formData.mbrName"
          @focus="handleFocus"
          @blur="handleBlur"
        />
      </div>
    </div>
    <div class="row">
      <div class="form-group col">
        <label class="form-label mb-1 text-2">이메일</label>
        <div class="d-flex align-items-center">
          <input
            type="email"
            maxlength="100"
            class="form-control text-3 h-auto py-2 email-input"
            name="email"
            placeholder="이메일을 입력해주세요"
            autocomplete="off"
            v-model="formData.mbrEmlAdrs"
            :disabled="emailSent || loading"
            @focus="handleFocus"
            @blur="handleBlur"
          />
          <button
            type="button"
            class="btn btn-primary ml-2 auth-button"
            @click="requestAuthCode"
            :disabled="emailSent || loading"
          >
            인증
          </button>
          <div
            v-if="loading"
            class="spinner-border text-primary ml-2"
            role="status"
          >
            <span class="sr-only">Loading...</span>
          </div>
        </div>
        <div v-if="emailError" class="text-danger mt-1">{{ emailError }}</div>
      </div>
    </div>
    <div v-if="emailSent" class="form-group">
      <label class="form-label mb-1 text-2">인증번호</label>
      <div class="d-flex align-items-center">
        <input
          type="text"
          class="form-control text-3 h-auto py-2 auth-code-input"
          v-model="formData.authCode"
          :disabled="emailSentCheck"
        />
        <button
          type="button"
          class="btn btn-primary ml-2 auth-button"
          @click="verifyAuthCode"
          :disabled="emailSentCheck"
        >
          확인
        </button>
      </div>
    </div>
    <div class="d-flex justify-content-center">
      <input
        type="submit"
        value="비밀번호찾기"
        class="btn btn-primary w-100 mb-3"
        data-loading-text="Loading..."
      />
    </div>
  </form>
</template>

<script>
import { ref } from "vue";
import { showAlert } from "../../../../utill/utillModal";
import { api } from "../../../../axios";

export default {
  name: "UserPwFind",
  props: ["handleLoginSubmit"],

  setup(props) {
    const emailError = ref("");
    const loading = ref(false);
    const emailCode = ref("");
    const emailSent = ref(false);
    const authCodeVerified = ref(false);
    const emailSentCheck = ref(false);
    const formData = ref({
      mbrId: "",
      mbrName: "",
      mbrEmlAdrs: "",
      authCode: "",
    });

    const validateEmail = (mbrEmlAdrs) => {
      const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      return re.test(mbrEmlAdrs);
    };

    const requestAuthCode = () => {
      console.log(formData.value);
      if (!formData.value.mbrEmlAdrs) {
        emailError.value = "이메일을 입력해주세요.";
        return;
      } else if (!validateEmail(formData.value.mbrEmlAdrs)) {
        emailError.value = "유효한 이메일 주소를 입력해주세요.";
        return;
      }
      emailError.value = "";
      loading.value = true; // 로딩 시작
      // 이메일로 인증번호 요청 로직 추가
      api
        .$post("http://localhost:80/member/emlRegister", {
          mbrEmlAdrs: formData.value.mbrEmlAdrs,
          purpose: "check",
        })
        .then((res) => {
          console.log(res.key);
          emailCode.value = res.key;
          emailSent.value = true;
          showAlert("인증번호가 이메일로 전송되었습니다.");
        })
        .catch((error) => {
          console.error("이메일 전송 중 오류 발생:", error);
          alert("이메일 전송 중 오류가 발생했습니다.");
        })
        .finally(() => {
          loading.value = false; // 로딩 종료
        });
    };

    const verifyAuthCode = () => {
      if (!formData.value.authCode) {
        showAlert("인증번호를 입력해주세요.");
        return;
      }
      // 인증번호 확인 로직 추가
      authCodeVerified.value = true;
      emailSentCheck.value = true;
      showAlert("인증번호가 확인되었습니다.");
    };

    const handleFocus = (event) => {
      event.target.placeholder = "";
    };

    const handleBlur = (event) => {
      event.target.placeholder =
        event.target.name === "name"
          ? "아이디를 입력해주세요"
          : "이메일을 입력해주세요";
    };

    const onSubmit = (e) => {
      e.preventDefault();
      const data = {
        mbrName: formData.value.mbrName,
        mbrId: formData.value.mbrId,
        mbrEmlAdrs: formData.value.mbrEmlAdrs,
      };
      if (!emailSent.value || !authCodeVerified.value) {
        showAlert("이메일 인증을 완료해주세요.");
        return;
      }
      if (!formData.value.mbrId) {
        showAlert("아이디를 입력해 주세요.");
        return;
      }
      if (!formData.value.mbrName) {
        showAlert("이름을 입력해 주세요.");
        return;
      }
      props.handleLoginSubmit(data, "pw");
    };
    return {
      loading,
      emailCode,
      emailError,
      emailSent,
      emailSentCheck,
      authCodeVerified,
      formData,
      requestAuthCode,
      verifyAuthCode,
      handleFocus,
      handleBlur,
      onSubmit,
    };
  },
};
</script>

<style scoped>
.form-control {
  box-shadow: none;
  &:focus {
    background-color: #f4f4f4;
  }
}
.email-input {
  flex: 1;
}

.auth-button {
  white-space: nowrap;
  height: 100%; /* 버튼 높이를 인풋과 동일하게 설정 */
}
</style>
