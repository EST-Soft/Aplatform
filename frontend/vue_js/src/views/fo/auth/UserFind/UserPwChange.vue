<template>
    <div class="heading heading-border heading-middle-border" style="margin-bottom: 100px;">
        <h1 class="font-weight-normal">비밀번호 변경<strong class="font-weight-extra-bold"></strong></h1>
    </div>
      <div class="form-container">
        <form @submit.prevent="onSubmit">
            <div class="row">
            <div class="form-group col">
                <div class="form-group row">
                    <label class="col-lg-3 col-form-label form-control-label line-height-9 pt-2 text-2">기존 비밀번호</label>
                    <div class="col-lg-9">
                        <input class="form-control text-3 h-auto py-2" type="password" id="currentPw" name="currentPw" value="" v-model="currentPw">
                    </div>
                </div>
            </div>
          </div>
          <div class="row">
            <div class="form-group col">
                <div class="form-group row">
                    <label class="col-lg-3 col-form-label form-control-label line-height-9 pt-2 text-2">변경 비밀번호</label>
                    <div class="col-lg-9">
                        <input class="form-control text-3 h-auto py-2" type="password" id="newPw" name="newPw" value="" v-model="newPw" @focus="handleFocus" @blur="handleBlur">
                    </div>
                </div>
            </div>
          </div>
          <div class="row">
            <div class="form-group col">
                <div class="form-group row">
                    <label class="col-lg-3 col-form-label form-control-label line-height-9 pt-2 text-2">변경 번호 재확인</label>
                    <div class="col-lg-9">
                        <input class="form-control text-3 h-auto py-2" type="password" id="checkPw" name="checkPw" value="" v-model="checkPw" @focus="handleFocus" @blur="handleBlur" @change="validatePassword">
                    </div>
                </div>
            </div>
          </div>
          <div v-if="pwError" class="text-danger mt-1">{{ pwError }}</div>
          <div class="button-wrapper" style="margin-top: 50px;">
            <button type="submit" class="btn btn-outline btn-dark btn-xl mb-2">
              비밀번호 재설정
            </button>
          </div>
        </form>
      </div>
  </template>
  
  <script>
  import { ref, computed } from "vue";
  import { showAlert } from "../../../../utill/utillModal";
  import { useRouter } from "vue-router";
  import { api } from "@/axios";
// import store from '@/store'
  import { useStore } from "vuex";

  export default {
    name: "UserPwChange",
    setup() {

        const currentPw = ref("");
        const newPw = ref("");
        const checkPw = ref("");
        const pwError = ref("");
        const router = useRouter();
        const store = useStore();

        const member = computed(() => store.getters['getMember']);

        const onSubmit = async () => {
        if (newPw.value !== checkPw.value) {
            showAlert('비밀번호가 일치하지 않습니다.');
            return;
        }

        if(currentPw.value == "" || newPw.value == "" || checkPw.value == ""){
          showAlert('비어있는 입력칸이 있습니다.');
          return;
        }
        const params = {
            mbrId: member.value.mbrId,
            currentPassword: currentPw.value,
            newPassword: newPw.value
        };
        
        try {
            const response = await api.$put('/member/pwUpdate', params);

            console.log(response);

            if (response.status === 200) {
                showAlert(response.message); // 성공 메시지 표시
                logout();
            } else if (response.status === 300) {
                showAlert(response.message); // 실패 메시지 표시
            } else {
                showAlert('알 수 없는 오류가 발생했습니다.'); // 기타 오류 처리
            }
            
        } catch (error) {
            if (error.response.data.status == 400) {
            // 서버 응답이 있는 경우
            console.error('Error response:', error.response.data);
            showAlert(`${error.response.data.message}`);
            } else if (error.request) {
            // 요청이 있었지만 응답이 없는 경우
            console.error('Error request:', error.request);
            showAlert('서버 응답이 없습니다.');
            } else {
            // 요청을 설정할 때 문제가 발생한 경우
            console.error('Error message:', error.message);
            showAlert('요청 설정 중 오류가 발생했습니다.');
            }
        }
        };

        function logout() {
            router.push('/user/login').then(() => {
            store.commit("clearMember");
            store.commit("changeUserType", "user");
        })
}

      const validatePassword = () => {
        if (newPw.value !== checkPw.value) {
          pwError.value = "변경할 비밀번호가 일치하지 않습니다.";
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
        currentPw,
        checkPw,
        pwError,
        validatePassword,
        router,
        member,
      };
    },
  };
  </script>
  