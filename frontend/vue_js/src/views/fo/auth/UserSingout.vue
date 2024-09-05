<template>
    <div class="heading heading-border heading-middle-border">
        <h1 class="font-weight-normal">회원 탈퇴<strong class="font-weight-extra-bold"></strong></h1>
    </div>

    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-body">
                        <h3 class="text-dark">
                            지금까지 이용해주셔서 감사 드립니다.</h3>
                            <br>
                        <h6> 탈퇴하기 전 아래 유의사항을 확인해 주세요.</h6>
                        <br>
                        <p class="text-muted">
                            탈퇴하신 아이디는 복구가 불가능하며, 추후 동일한 아이디로 재가입이 되지 않습니다.
                            잡코리아와 알바몬 사이트에서 동시에 탈퇴됩니다.
                        </p>
                        <p class="text-muted">
                            이력서 정보, 구직 활동내역, 유료서비스, e-머니가 모두 삭제되며, 삭제된 데이터는 복구되지 않습니다. <br>
                            회원탈퇴 시 등록한 게시물은 삭제되지 않으므로, 삭제를 원하시면 회원탈퇴 전에 삭제해 주시기 바랍니다.</p>
                        <hr>
                        <br>

                        <form @submit.prevent="handleSubmit">
                            <div class="form-group row">
                                <label class="col-lg-3 col-form-label form-control-label line-height-9 pt-2 text-2 required">아이디</label>
                                <div class="col-lg-9">
                                    <input class="form-control text-3 h-auto py-2" type="text" name="text" :value="member.mbrId" required="" readonly>
                                </div>
                            </div>
                            <div class="form-group row">
                                <label class="col-lg-3 col-form-label form-control-label line-height-9 pt-2 text-2 required">비밀번호</label>
                                <div class="col-lg-9">
                                    <input class="form-control text-3 h-auto py-2" type="password" name="password" value="" required="">
                                </div>
                            </div>
                            <br>
                            <div class="form-check">
                                <input type="checkbox" class="form-check-input" id="agree" v-model="agree" required>
                                <label class="form-check-label" for="agree" >유의사항을 모두 확인 했으며, 이에 동의합니다.</label>
                            </div>
                            <br>
                            <div class="button-wrapper">
                                <button type="submit" class="btn btn-outline btn-dark btn-xl mb-2" :disabled="!agree">탈퇴하기</button>
                            </div>
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
// import axios from 'axios';
// import { useStore } from "vuex";
// const store = useStore();

const agree = ref(''); // 약관동의

const member = computed(() => store.getters.getMember);
// const isLoggedIn = computed(() => member.value !== null);

onMounted(async() => {

//     const test = computed(()=> {
//         return store.getters.enterMember
//     })
// entrprsSq.value = test.value.pk;
// console.log(entrprsSq.value);

//     try{
//     const res = await axios.get('http://localhost:80/enter/getEnterInfo', {
//                 params: {
//                     entrprsSq : entrprsSq.value,
//                 }
//             });
//             entrprsId.value = res.data.entrprs_id;
//             console.log(res.data);

//     }catch(error){
//         console.error(error);
//     }
});

// 탈퇴하기
const handleSubmit = async() => {
    // if(confirm("정말 탈퇴하시겠습니까?")==true){ // 탈퇴신청 여부 

    //         const data = {
    //         entrprsPswrd : entrprsPswrd.value,
    //         entrprsSq : entrprsSq
    //     }

    //     try{
    //     const res = await axios.post('http://localhost:80/enter/enterSignOut', data);
    //         console.log(res.data);
    //         if(res.data == '비밀번호 불일치'){
    //             alert("비밀번호가 일치하지 않습니다.")
    //             entrprsPswrd.value = '';
    //         }

    //         if(res.data == '탈퇴완료'){
    //             alert('탈퇴 되었습니다')
    //             location.href = "http://localhost:8080/enter/login";
    //         }

    //     }catch(error){
    //         console.error(error);
    //     }
    // }else{ // 취소
    //     entrprsPswrd.value = '';
    //     return false;
    // }


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
.button-wrapper {
    display: flex;
    justify-content: center;
  }
.form-control:focus {
    background-color: #f4f4f4;
}
</style>