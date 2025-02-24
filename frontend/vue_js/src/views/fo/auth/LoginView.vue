<template>
  <div class="container mt-5">
    <section
      class="page-header page-header-modern bg-color-grey page-header-lg text-center"
    >
      <div class="container">
        <div class="row">
          <div class="col-md-12 align-self-center p-static order-2 text-center">
            <h1 class="font-weight-bold text-dark">일반회원 로그인</h1>
          </div>
        </div>
      </div>
    </section>

    <section class="loginSection">
      <div class="card shadow-lg p-3 mb-5 bg-white rounded">
        <div class="card-body">
          <div class="row mb-4">
            <div class="col-6" style="padding: 0">
              <router-link
                class="btn btn-outline-primary rounded-0 mb-2 w-100"
                to="/user/login"
              >
                일반회원
              </router-link>
            </div>
            <div class="col-6" style="padding: 0">
              <router-link
                class="btn btn-outline-primary rounded-0 mb-2 w-100"
                to="/enter/login"
              >
                기업회원
              </router-link>
            </div>
          </div>
          <!-- 로그인 폼 시작 -->
          <form @submit.prevent="LoginSubmit">
            <div class="form-group mb-3">
              <label class="form-label mb-1 text-2">아이디</label>
              <input
                type="text"
                maxlength="100"
                class="form-control text-3 h-auto py-2"
                name="mbrId"
                v-model="mbrId"
                placeholder="아이디를 입력해주세요"
                autocomplete="off"
                @focus="handleFocus"
                @blur="handleBlur"
              />
            </div>
            <div class="form-group mb-3">
              <label class="form-label mb-1 text-2">비밀번호</label>
              <input
                type="password"
                maxlength="100"
                class="form-control text-3 h-auto py-2"
                name="mbrPswrd"
                v-model="mbrPswrd"
                placeholder="비밀번호를 입력해주세요"
                autocomplete="off"
                @focus="handleFocus"
                @blur="handleBlur"
              />
            </div>
            <div v-if="pwError" class="text-danger mt-1">{{ pwError }}</div>
            <div class="d-flex justify-content-center">
              <input
                type="submit"
                value="로그인"
                class="btn btn-primary w-100 mb-3"
                data-loading-text="Loading..."
              />
            </div>
          </form>
          <!-- 로그인 폼 끝 -->

          <ul class="d-flex justify-content-between nav nav-pills" id="mainNav">
            <li class="dropdown pe-5">
              <router-link to="/user/signup" class="dropdown-item">
                회원가입
              </router-link>
            </li>
            <li>
              <router-link to="/user/find" class="dropdown-item">
                아이디, 비밀번호 찾기
              </router-link>
            </li>
          </ul>
          <hr />
          <div class="align-self-center">
            <ul
              class="d-flex justify-content-center nav nav-pills"
              id="mainNav"
            >
              <!-- <li class="dropdown">
                <a href="#" @click="naverLogin" class="dropdown-item">
                  <img
                    :src="require('@/assets/naverlogo/btnG_아이콘원형.png')"
                    class="socialLogo"
                  />
                </a> -->
              <!-- </li> -->
            </ul>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
// import axios from "axios";
// import { ref, computed } from "vue";
import { ref } from "vue";
import { useRouter } from "vue-router";
import { showAlert } from "../../../utill/utillModal";
import { api } from "../../../axios";
import store from "../../../store";
// import store from "@/store";

const router = useRouter();
const pwError = ref("");
const mbrId = ref("");
const mbrPswrd = ref("");
// const doneTodosCount = computed(() => {
//   return store.getters.enterMember;
// });

const handleFocus = (event) => {
  event.target.placeholder = "";
};

const handleBlur = (event) => {
  event.target.placeholder =
    event.target.name === "id"
      ? "아이디를 입력해주세요"
      : "비밀번호를 입력해주세요";
};

const LoginSubmit = async () => {
  const loginData = {
    mbrId: mbrId.value,
    mbrPswrd: mbrPswrd.value,
  };

  if (mbrId.value === "" && mbrPswrd.value === "") {
    showAlert("입력해 주세요");
    return;
  }
  try {
    const res = await api.$post("/member/login", loginData);
    console.log(res);
    // 정상적으로 로그인된 경우
    if (res) {
      showAlert(`로그인 되었습니다. ${res.mbrName} 님 환영합니다.`);
      console.log(res);
      // 사용자 정보를 Vuex 스토어에 저장

      store.commit("setMember", res);
      store.commit("changeUserType", "user");

      // 기업회원인 경우 기업회원 정보를 스토어에 저장
      // if (res.data.userType === "enter") {
      //   store.commit("changeUserType", "enter");
      //   store.commit("setEnter", res.data.enterMember); // 기업회원 정보 저장
      // } else {
      //   store.commit("changeUserType", "user");
      // }

      // 홈 페이지로 리다이렉트
      router.push("/");
    }
  } catch (error) {
    // 에러 처리 로직
    if (error.response && error.response.status) {
      switch (error.response.status) {
        case 403: // 금지된 요청, 탈퇴한 회원 또는 사용 중지된 계정
          pwError.value = "계정이 탈퇴되었거나 사용이 중지되었습니다.";
          break;
        case 404: // 찾을 수 없음, 잘못된 아이디 또는 비밀번호
          pwError.value = "잘못된 아이디 또는 비밀번호입니다.";
          break;
        case 400: // 잘못된 요청, 아이디나 비밀번호가 빈 경우
          pwError.value = "아이디와 비밀번호를 입력해주세요.";
          break;
        default: // 기타 에러
          pwError.value = "로그인에 실패했습니다. 다시 시도해주세요.";
      }
    } else {
      // 네트워크 오류 등 기타 에러 처리
      pwError.value = "서버와의 통신에 실패했습니다. 다시 시도해주세요.";
    }
  }

  // axios
  //   .post("http://localhost:80/enter/login", loginData, {
  //     withCredentials: true,
  //   })

  //   .then(async (response) => {
  //     store.commit("setEnter", response.data); // store에 기업 정보 저장(pk, id)

  //     console.log(doneTodosCount.value);

  //     await router.push("/");
  //   })
  //   .catch((error) => {
  //     alert(error);
  //   });
};

// https://henniee.tistory.com/m/238 링크 참조
// const client_id = "TIwA7WnbAvnjEwnbPGZm"; // 본인 또는 회사 아이디로 교체
// const redirect_uri = "http://localhost:8080/member/loginNCallback";

// state 난수 생성
// const generateRandomState = () => {
//   const characters =
//     "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
//   let state = "";
//   for (let i = 0; i < 16; i++) {
//     const randomIndex = Math.floor(Math.random() * characters.length);
//     state += characters.charAt(randomIndex);
//   }
//   return state;
// };

// const naverLogin = async () => {
//   const state = generateRandomState();
//   const apiURL = `https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=${client_id}&redirect_uri=${redirect_uri}&state=${state}`;

//   window.location.href = apiURL;
// };
</script>

<style scoped>
.form-control {
  box-shadow: none;
}
.loginSection {
  display: flex;
  justify-content: center;
  align-items: center;
}
.socialLogo {
  max-width: 45px;
  height: auto;
}
</style>
