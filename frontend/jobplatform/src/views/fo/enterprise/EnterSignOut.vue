<template>
    <section class="page-header page-header-modern bg-color-grey page-header-lg">
        <div class="container">
            <div class="row">
                <div class="col-md-12 align-self-center p-static order-2 text-center">
                    <h1 class="font-weight-bold text-dark">회원 탈퇴</h1>
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
                                <label for="userId">아이디(필수)</label>
                                <input type="text" class="form-control" v-model="entrprsId" required readonly>
                            </div>
                            <div class="form-group">
                                <label for="applicant">비밀번호(필수)</label>
                                <input type="password" class="form-control" v-model="entrprsPswrd" placeholder="비밀번호를 입력해주세요" required>
                            </div>
                            <div class="form-check">
                                <input type="checkbox" class="form-check-input" id="agree" v-model="agree" required>
                                <label class="form-check-label" for="agree" >유의사항을 모두 확인 했으며, 이에 동의합니다.</label>
                            </div>
                            <button type="submit" class="btn btn-danger mt-3" :disabled="!agree">탈퇴하기</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>

import { onMounted, ref ,computed} from 'vue';
import store from '@/store';
import {api} from '@/axios';

const entrprsId = ref(''); // 기업회원 아이디
const entrprsPswrd = ref('');// 기업회원 비밀번호
const agree = ref(''); // 약관동의
const entrprsSq = ref('');


onMounted(async() => {

    const test = computed(()=> {
        return store.getters.enterMember
    })
entrprsSq.value = test.value.pk;
console.log(entrprsSq.value);

    try{
    const res = await api.$get('/enter/getEnterInfo', {
                params: {
                    entrprsSq : entrprsSq.value,
                }
            });
            entrprsId.value = res.entrprs_id;
            console.log(res);

    }catch(error){
        console.error(error);
    }
});

// 탈퇴하기
const handleSubmit = async() => {
    if(confirm("정말 탈퇴하시겠습니까?")==true){ // 탈퇴신청 여부 

            const data = {
            entrprsPswrd : entrprsPswrd.value,
            entrprsSq : entrprsSq
        }

        try{
        const res = await api.$post('/enter/enterSignOut', data);
            console.log(res);
            if(res == '비밀번호 불일치'){
                alert("비밀번호가 일치하지 않습니다.")
                entrprsPswrd.value = '';
            }

            if(res == '탈퇴완료'){
                alert('탈퇴 되었습니다')
                location.href = "http://localhost:8080/enter/login";
            }

        }catch(error){
            console.error(error);
        }
    }else{ // 취소
        entrprsPswrd.value = '';
        return false;
    }


}

</script>

<style>
/* .page-header {
    margin-bottom: 30px;
}

.container {
    max-width: 600px;
}

.card {
    border: none;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.card-body {
    padding: 30px;
}

.form-check-label {
    font-size: 0.9rem;
}

.btn-danger {
    background-color: #dc3545;
    border-color: #dc3545;
}

.btn-danger:disabled {
    background-color: #e4e6eb;
    border-color: #e4e6eb;
} */
</style>