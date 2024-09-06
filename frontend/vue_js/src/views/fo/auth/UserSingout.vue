<template>
    <div class="heading heading-border heading-middle-border">
        <h1 class="font-weight-normal">회원 탈퇴<strong class="font-weight-extra-bold"></strong></h1>
    </div>
    <div class="container mt-5">
        <section class="section section-default section-center">
            <div class="container">
                <div class="row">
                    <div class="col">
                        <h3 class="mb-3">지금까지 이용해주셔서 감사 드립니다.</h3>
                        <h5>탈퇴하기 전 아래 유의사항을 확인해 주세요.</h5>
                        <p class="mb-0 text-color-dark">탈퇴하신 아이디는 복구가 불가능하며, 추후 동일한 아이디로 재가입이 되지 않습니다.</p>
                        <p class="mb-0 text-color-dark">잡코리아와 알바몬 사이트에서 동시에 탈퇴됩니다.</p>
                        <p class="mb-0 text-color-dark">이력서 정보, 구직 활동내역, 유료서비스, e-머니가 모두 삭제되며, 삭제된 데이터는 복구되지 않습니다. <br>
                            회원탈퇴 시 등록한 게시물은 삭제되지 않으므로, 삭제를 원하시면 회원탈퇴 전에 삭제해 주시기 바랍니다.</p>
                    </div>
                </div>
            </div>
        </section>
        <div class="row justify-content-center">
            <div class="col-md-8">
                <form @submit.prevent="handleSubmit">
                    <div class="form-group row">
                        <label class="col-lg-3 col-form-label form-control-label line-height-9 pt-2 text-2 required">아이디</label>
                        <div class="col-lg-9">
                            <input class="form-control text-3 h-auto py-2" type="text" name="mbr_id" id="mbr_id" :value="member.mbrId" required="" readonly>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-lg-3 col-form-label form-control-label line-height-9 pt-2 text-2 required">비밀번호</label>
                        <div class="col-lg-9">
                            <input class="form-control text-3 h-auto py-2" type="password" name="cmbr_pswrd" id="mbr_pswrd" v-model="mbrPswrd" value="" required="">
                        </div>
                    </div>
                    <br>
                    <div class="custom-control custom-checkbox">
                        <input type="checkbox" class="custom-control-input" id="agree" v-model="agree" required>
                        <label class="custom-control-label text-2" for="agree" style="padding-left: 5px;">유의사항을 모두 확인 했으며, 이에 동의합니다.</label>
                    </div>
                    <br>
                    <div class="button-wrapper">
                        <button type="submit" class="btn btn-outline btn-dark btn-xl mb-2" :disabled="!agree">탈퇴하기</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</template>

<script setup>

import { onMounted, ref ,computed} from 'vue';
import store from '@/store';
import axios from 'axios';
import { useStore } from "vuex";
import { useRouter } from 'vue-router';

const agree = ref(''); // 약관동의
const mbrPswrd = ref('');
const ustore = useStore();
const router = useRouter();

const member = computed(() => store.getters.getMember);
// const isLoggedIn = computed(() => member.value !== null);

onMounted(async() => {
});

// 탈퇴하기
const handleSubmit = async() => {
    if(confirm("정말 탈퇴하시겠습니까?")==true){ // 탈퇴신청 여부 

        const data = {
            mbrPswrd : mbrPswrd.value,
            mbrId : member.value?.mbrId
        }

        try{
            const res = await axios.put('http://localhost:80/member/delete', data);
            console.log(res.data);
            const responseData = res.data;
            
            if (responseData === '회원을 찾을 수 없습니다.') {
                alert("비밀번호가 일치하지 않습니다.");
                mbrPswrd.value = '';
            } else if (responseData === '회원 탈퇴 중 오류가 발생했습니다.') {
                alert("다시 시도해 주십시오.");
                mbrPswrd.value = '';
            } else if (responseData === '탈퇴완료') {
                alert('탈퇴 되었습니다');
                logout();
            }
        }catch(error){
            console.error(error);
            if (error.response) {
                // 서버가 상태 코드로 응답했지만 요청이 실패한 경우
                console.error("Error response:", error.response.data);
                console.error("Error status:", error.response.status);
                console.error("Error headers:", error.response.headers);
                alert(`서버 오류: ${error.response.data}`);
            } else if (error.request) {
                // 요청이 만들어졌지만 응답이 없는 경우
                console.error("Error request:", error.request);
                alert("서버 응답이 없습니다.");
            } else {
                // 오류를 발생시킨 요청을 설정할 때 문제가 발생한 경우
                console.error("Error message:", error.message);
                alert("요청 설정 중 오류가 발생했습니다.");
            }
        }
    }else{ // 취소
        mbrPswrd.value = '';
        return false;
    }
}

function logout() {
    router.push('/').then(() => {
        ustore.commit("clearMember");
        ustore.commit("changeUserType", "user");
    })
}
</script>

<style>
.row.justify-content-center{
    margin-top: 100px;
}
.button-wrapper, .custom-control {
    display: flex;
    justify-content: center;
    margin-top: 10px;
  }
.form-control:focus {
    background-color: #ffffff;
    box-shadow: none;
}
.form-control {
    box-shadow: none;
    width: 350px;
  }
</style>