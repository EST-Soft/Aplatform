<template>
  <div class="container mt-5">
    <section
      class="page-header page-header-modern bg-color-grey page-header-lg"
    >
      <div class="container">
        <div class="row">
          <div class="col-md-12 align-self-center p-static order-2 text-center">
            <h1 class="font-weight-bold text-dark">비밀번호 재설정</h1>
          </div>
        </div>
      </div>
    </section>

    <div class="form-container">
      <form @submit.prevent="onSubmit">
        <div class="row">
          <div class="form-group col">
            <label class="form-label mb-1 text-2">새로운 비밀번호</label>
            <input
              type="password"
              maxlength="100"
              class="form-control text-3 h-auto py-2"
              name="newPw"
              placeholder="새로운 비밀번호를 입력해주세요"
              autocomplete="off"
              v-model="newPw"
              @focus="handleFocus"
              @blur="handleBlur"
            />
          </div>
        </div>
        <div class="row">
          <div class="form-group col">
            <label class="form-label mb-1 text-2">비밀번호 확인</label>
            <input
              type="password"
              maxlength="100"
              class="form-control text-3 h-auto py-2"
              name="checkPw"
              placeholder="비밀번호를 다시 입력해주세요"
              autocomplete="off"
              v-model="checkPw"
              @focus="handleFocus"
              @blur="handleBlur"
              @change="validatePassword"
            />
          </div>
        </div>
        <div v-if="pwError" class="text-danger mt-1">{{ pwError }}</div>
        <div class="d-flex justify-content-center">
          <button type="submit" class="btn btn-primary w-100 mb-3">
            비밀번호 재설정
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import { ref } from "vue";
import { showAlert } from "../../../../utill/utillModal";
import { useRouter } from "vue-router";

export default {
  name: "UserPwUpdate",
  setup() {
    const newPw = ref("");
    const checkPw = ref("");
    const pwError = ref("");
    const router = useRouter();
    const onSubmit = () => {
      if (newPw.value !== checkPw.value) {
        showAlert("비밀번호가 일치하지 않습니다.");
        return;
      }
      // 비밀번호 재설정 로직 추가
      showAlert("비밀번호가 성공적으로 재설정되었습니다.");
      router.push("/user/login");
    };
    const validatePassword = () => {
      if (newPw.value !== checkPw.value) {
        pwError.value = "비밀번호가 일치하지 않습니다.";
      } else {
        pwError.value = "";
      }
    };
    const handleFocus = (event) => {
      event.target.placeholder = "";
    };

    const handleBlur = (event) => {
      event.target.name === "newPw"
        ? (event.target.placeholder = "새로운 비밀번호를 입력해주세요")
        : (event.target.placeholder = "비밀번호를 다시 입력해주세요");
    };

    return {
      onSubmit,
      handleBlur,
      handleFocus,
      newPw,
      checkPw,
      pwError,
      validatePassword,
      router,
    };
  },
};
</script>

<style scoped>
.form-container {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
  background-color: #fff;
  border-radius: 5px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.form-group {
  width: 100%;
  margin-bottom: 15px;
}

.form-control {
  box-shadow: none;
  width: 280px;
}

.form-control:focus {
  background-color: #f4f4f4;
}

button {
  width: 100%;
  padding: 10px;
  background-color: #007bff;
  border: none;
  border-radius: 5px;
  color: white;
  cursor: pointer;
}

button:hover {
  background-color: #0056b3;
}
</style>
