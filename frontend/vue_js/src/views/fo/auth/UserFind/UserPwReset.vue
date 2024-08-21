<template>
  <div class="contact-form form-style-3">
    <h1>[유저]새 비밀번호 설정</h1>
    <hr />
  </div>
  <br />
  <form @submit.prevent="onSubmit">
    <div class="row">
      <div class="form-group col">
        <label class="form-label mb-1 text-2">새 비밀번호</label>
        <input
          type="password"
          maxlength="100"
          class="form-control text-3 h-auto py-2"
          v-model="newPassword"
          placeholder="새 비밀번호를 입력하세요"
          autocomplete="off"
        />
      </div>
      <div class="form-group col">
        <label class="form-label mb-1 text-2">비밀번호 확인</label>
        <input
          type="password"
          maxlength="100"
          class="form-control text-3 h-auto py-2"
          v-model="confirmPassword"
          placeholder="비밀번호를 확인"
          autocomplete="off"
        />
      </div>
    </div>
    <div class="d-flex justify-content-center">
      <input
        type="submit"
        value="비밀번호 변경"
        class="btn btn-primary w-100 mb-3"
      />
    </div>
  </form>
</template>

<script setup>
import { ref } from "vue";
import { showAlert } from "../../../../utill/utillModal";
import { defineProps } from "vue";
import { api } from "../../../../axios";
import router from "../../../../router";

const newPassword = ref("");
const confirmPassword = ref("");
const props = defineProps({
  mbrSq: {
    type: Number,
    required: true,
  },
  handleLoginSubmit: Function,
});
const data = ref({
  mbrSq: props.mbrSq,
  mbrPswrd: "",
});
const onSubmit = async () => {
  console.log(data.value);
  if (newPassword.value === "" || confirmPassword.value === "") {
    showAlert("비밀번호를 입력하세요.");
    return;
  }

  if (newPassword.value !== confirmPassword.value) {
    showAlert("비밀번호가 일치하지 않습니다.");
    return;
  }
  data.value.mbrPswrd = newPassword.value;
  // 비밀번호 변경 로직을 여기에 추가하세요.
  console.log(data.value);
  try {
    const result = await api.$patch("/member/PswrdReset", data.value);
    if (result) {
      showAlert("비밀번호가 성공적으로 변경되었습니다.");
      router.push("/member/login");
    }
  } catch (error) {
    showAlert(error);
  }
};
</script>

<style scoped>
.contact-form {
  text-align: center;
}
</style>
