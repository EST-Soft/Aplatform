<template>
    <div class="heading heading-border heading-middle-border">
        <h1 class="font-weight-normal">비밀번호 변경<strong class="font-weight-extra-bold"></strong></h1>
    </div>
      <div class="form-container">
        <form @submit.prevent="onSubmit">
            <div class="row">
            <div class="form-group col">
                <div class="form-group row">
                    <label class="col-lg-3 col-form-label form-control-label line-height-9 pt-2 text-2 required">기존 번호</label>
                    <div class="col-lg-9">
                        <input class="form-control text-3 h-auto py-2" type="password" name="password" value="" required="">
                    </div>
                </div>
            </div>
          </div>
          <div class="row">
            <div class="form-group col">
                <div class="form-group row">
                    <label class="col-lg-3 col-form-label form-control-label line-height-9 pt-2 text-2 required">변경할 번호</label>
                    <div class="col-lg-9">
                        <input class="form-control text-3 h-auto py-2" type="password" name="password" value="" required="">
                    </div>
                </div>
            </div>
          </div>
          <div class="row">
            <div class="form-group col">
                <div class="form-group row">
                    <label class="col-lg-3 col-form-label form-control-label line-height-9 pt-2 text-2 required">번호 재확인</label>
                    <div class="col-lg-9">
                        <input class="form-control text-3 h-auto py-2" type="password" name="password" value="" required="">
                    </div>
                </div>
            </div>
          </div>
          <br>
          <div v-if="pwError" class="text-danger mt-1">{{ pwError }}</div>
          <div class="button-wrapper">
            <button type="submit" class="btn btn-outline btn-dark btn-xl mb-2">
              비밀번호 재설정
            </button>
          </div>
        </form>
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
        display: flex;
        justify-content: center;
        max-width: 800px;
        margin: 0 auto;
        padding: 20px;
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
  .button-wrapper {
    display: flex;
    justify-content: center;
  }
  .row{
    width: 500px;
  }
  </style>
  