<template>
  <div class="container mt-5">
    <section
      class="page-header page-header-modern bg-color-grey page-header-lg"
    >
      <div class="container">
        <div class="row">
          <div class="col-md-12 align-self-center p-static order-2 text-center">
            <h1 class="font-weight-bold text-dark">유저 계정정보 찾기</h1>
          </div>
        </div>
      </div>
    </section>

    <div class="loginSection">
      <div class="card mb-4">
        <div class="card-body">
          <div class="row">
            <div class="col">
              <div class="contact-form form-style-3">
                <div class="row" v-if="!isPwResetMode">
                  <div class="col-6" style="padding: 0">
                    <button
                      class="btn btn-outline rounded-0 mb-2"
                      :class="{
                        'btn-primary': idPwCheck,
                        'btn-dark': !idPwCheck,
                      }"
                      @click="setIdPwCheck(true)"
                    >
                      아이디찾기
                    </button>
                  </div>
                  <div class="col-6" style="padding: 0">
                    <button
                      class="btn btn-outline rounded-0 mb-2"
                      :class="{
                        'btn-primary': !idPwCheck,
                        'btn-dark': idPwCheck,
                      }"
                      @click="setIdPwCheck(false)"
                    >
                      비밀번호찾기
                    </button>
                  </div>
                </div>
                <!-- 로그인 or pw 폼 시작 -->

                <component
                  :is="
                    isPwResetMode
                      ? 'UserPwReset'
                      : idPwCheck
                      ? 'UserIdFind'
                      : 'UserPwFind'
                  "
                  :mbrSq="mbrSq"
                  :handle-login-submit="handleLoginSubmit"
                />
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { ref } from "vue";
import { showAlert } from "../../../../utill/utillModal";
import UserIdFind from "./UserIdFind.vue";
import UserPwFind from "./UserPwFind.vue";
import { api } from "../../../../axios";
import UserPwReset from "./UserPwReset.vue";

export default {
  name: "UserFindMain",
  components: {
    UserIdFind,
    UserPwFind,
    UserPwReset,
  },
  setup() {
    const isPwResetMode = ref(false); // 비밀번호 재설정 모드 상태
    const mbrSq = ref(""); // 여기서 res 데이터를 저장할 변수

    const idPwCheck = ref(true);
    const setIdPwCheck = (value) => {
      idPwCheck.value = value;
    };

    const handleLoginSubmit = async (data, formType) => {
      console.log("진입");
      if (formType === "id") {
        //아이디 찾기 로직으로 얼럿창에 해당 아이디 표시출력.
        try {
          const res = await api.$post("/member/findId", data);
          showAlert("고객님의 아이디는: " + res);
        } catch (error) {
          if (error.response && error.response.status === 400) {
            showAlert(error.response.data); // 서버에서 받은 오류 메시지 표시
          } else {
            showAlert("알 수 없는 오류가 발생했습니다."); // 일반적인 오류 처리
          }
        }
      } else {
        //비밀번호 찾기 로직시 비밀번호 재설정 페이지 로드.
        try {
          const res = await api.$post("/member/findPswrd", data);
          mbrSq.value = res;
          isPwResetMode.value = true; // 비밀번호 재설정 모드로 전환
        } catch (error) {
          if (error.response && error.response.status === 400) {
            showAlert(error.response.data); // 서버에서 받은 오류 메시지 표시
          } else {
            showAlert("알 수 없는 오류가 발생했습니다."); // 일반적인 오류 처리
          }
        }
        // showAlert("비밀번호 찾기 로직 실행");
      }
    };

    return {
      isPwResetMode,
      idPwCheck,
      setIdPwCheck,
      handleLoginSubmit,
      mbrSq,
    };
  },
};
</script>

<style scoped>
.btn-outline {
  font-size: 12px;
}
.card-body {
  width: 400px;
}
.row {
  padding: 20px;
}
.row .col-6 {
  text-align: center;
}
.btn-primary {
  background-color: #007bff;
  color: white;
}
.btn-secondary {
  background-color: #f8f9fa;
  color: #000;
}
.loginSection {
  display: flex;
  justify-content: center;
}
</style>
