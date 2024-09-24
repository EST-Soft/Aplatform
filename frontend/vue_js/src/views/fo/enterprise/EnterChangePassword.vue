<template>
    <section class="page-header page-header-modern bg-color-grey page-header-lg">
        <div class="container">
            <div class="row">
                <div class="col-md-12 align-self-center p-static order-2 text-center">
                    <h1 class="font-weight-bold text-dark">비밀번호 변경</h1>
                </div>
            </div>
        </div>
    </section>


    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-body">
                        <p class="text-dark">
                            지금까지 이용해주셔서 감사 드립니다. 탈퇴하기 전 아래 유의사항을 확인해 주세요.
                        </p>
                        <p class="text-muted">
                            탈퇴하신 아이디는 복구가 불가능하며, 추후 동일한 아이디로 재가입이 되지 않습니다.
                            잡코리아와 알바몬 사이트에서 동시에 탈퇴됩니다.
                        </p>
                        <p class="text-muted">
                            이력서 정보, 구직 활동내역, 유료서비스, e-머니가 모두 삭제되며, 삭제된 데이터는 복구되지 않습니다.
                            회원탈퇴 시 등록한 게시물은 삭제되지 않으므로, 삭제를 원하시면 회원탈퇴 전에 삭제해 주시기 바랍니다.
                            소셜 로그인 회원의 경우, 모든 정보가 삭제되며 같은 소셜 아이디로 재가입 시 신규 회원으로 가입됩니다.
                        </p>

                        <form @submit.prevent="handleSubmit">
                            <div class="form-group">
                                <label for="userId">기존비밀번호(필수)</label>
                                <input type="password" class="form-control"  v-model="entrprsPswrd" required>
                            </div>
                            <div class="form-group">
                                <label for="applicant">변경할 비밀번호(필수)</label>
                                <input type="password" class="form-control"  v-model="entrprsPswrdChng" 
                                placeholder="영문, 숫자를 포함한 4자 이상 20자 이내"  required>
                            </div>
                            <div class="form-group">
                                <label for="applicant">변경된 비밀번호 확인(필수)</label>
                                <input type="password" class="form-control"  v-model="entrprsPswrdChngCheck" 
                                placeholder="영문, 숫자를 포함한 4자 이상 20자 이내"  required>
                            </div>
                            <div class="form-check">
                                <input type="checkbox" class="form-check-input"  v-model="agree" required>
                                <label class="form-check-label" for="agree">유의사항을 모두 확인 했으며, 이에 동의합니다.</label>
                            </div>
                            <button type="submit" class="btn btn-danger mt-3" :disabled="!agree">변경하기</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>

import { onMounted , ref , computed} from 'vue';
import store from '@/store';
import { api } from '@/axios';



const agree = ref(''); // 유의사항 동의
const entrprsPswrd = ref(''); // 기존 비밀번호
const entrprsPswrdChng = ref(''); // 변경할 비밀번호
const entrprsPswrdChngCheck = ref(''); // 변경할 비밀번호 확인
const entrprsSp = ref(''); //기업회원순번


const errorEntrprsPswrdChng = ref(''); //변경할 비밀번호 에러
const errorPswrdConfirm =ref(''); // 비밀번호 형식 에러

onMounted(async() => {
    const test = computed(()=>{

return store.getters.enterMember
})
console.log(test.value.pk);
entrprsSp.value = test.value.pk;
})



// 비밀번호 변경하기
 const handleSubmit = async() => {
    console.log("비밀번호 변경");

    if (!/^[A-Za-z0-9]{4,20}$/.test(entrprsPswrdChng.value)) {
      errorEntrprsPswrdChng.value =
        "변경할 비밀번호 형식이 올바르지 않습니다.";
            return alert(errorEntrprsPswrdChng.value);
  
    } 

    if (!/^[A-Za-z0-9]{4,20}$/.test(entrprsPswrdChngCheck.value)) {
        errorEntrprsPswrdChng.value =
        "변경할 비밀번호 형식이 올바르지 않습니다.";
            return alert(errorEntrprsPswrdChng.value);
  
    } 
  
    if (entrprsPswrdChng.value !== entrprsPswrdChngCheck.value) {
      errorPswrdConfirm.value = "변경할 비밀번호가 일치하지 않습니다.";
           return alert(errorPswrdConfirm.value);
    }

    const data = {
        entrprsPswrd : entrprsPswrd.value,
        entrprsPswrdChng : entrprsPswrdChng.value,
        entrprsPswrdChngCheck : entrprsPswrdChngCheck.value,
        entrprsSp : entrprsSp.value,
    }

    try{
    const res = await api.$post('/enter/enterChangPassword', data );
            console.log(res);
            if(res == '수정완료'){
                alert('비밀번호가 변경되었습니다.');
                entrprsPswrd.value = '';
                entrprsPswrdChng.value = '';
                entrprsPswrdChngCheck.value = '';

            }else if(res == '비밀번호 불일치'){
                alert('비밀번호가 일치하지 않습니다.');
                entrprsPswrd.value = '';
                entrprsPswrdChng.value = '';
                entrprsPswrdChngCheck.value = '';
            }
            
    }catch(error){
        console.error(error);
    } 

 }

</script>

<style>

</style>