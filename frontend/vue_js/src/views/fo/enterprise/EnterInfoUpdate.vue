<template>
    <section class="page-header page-header-modern bg-color-grey page-header-lg">
        <div class="container">
            <div class="row">
                <div class="col-md-12 align-self-center p-static order-2 text-center">
                    <h1 class="font-weight-bold text-dark">기업정보 수정</h1>
                </div>
            </div>
        </div>
    </section>

    <div class="d-flex justify-content-center mb-4">
        <div class="profile-image-outer-container">
            <div @click="uploadImg" class="profile-image-inner-container bg-color-primary">
                <img src="@/assets/avatar.jpg" alt="Profile Image" v-if="img==0">
                <img :src="imgUrl" alt="Profile Image" v-if="img==1">
                <span class="profile-image-button bg-color-dark">
                    <i class="fas fa-camera text-light"></i>
                </span>
            </div>
                <!-- <input type="file" id="file" class="form-control profile-image-input"             
                    @change="uploadImg"
                    accept="image/gif, image/jpeg, image/png"> -->
        </div>
    </div>

<div class="form-container">
    <div class="col-lg-11">
        <form role="form" class="needs-validation" @submit.prevent="handleSubmit">
            <div class="form-group row">
                <label class="col-lg-3 col-form-label form-control-label line-height-9 pt-2 text-2 required">
                    아이디</label>
                <div class="col-lg-9">
                    <input class="form-control text-3 h-auto py-2" type="text" name="entrprsId" v-model="entrprsId" readonly>
                </div>
                <!--아이디 중복체크-->
                <!-- <div class="col-lg-7" v-if="repetitionCheck == 1">
                    <input class="form-control text-3 h-auto py-2" type="text" name="entrprsId" :value="entrprsId" readonly>
                </div> -->
                <!-- <div class="col-lg-2" v-if="repetitionCheck == 0">
                    <input class="btn btn-danger text-3 h-auto py-2" 
                    type="button" name="entrprsId" value="중복확인"
                    @click="idRepetitionCheck">
                </div>
                <div class="col-lg-2" v-if="repetitionCheck == 1">
                    <input class="btn btn-primary text-3 h-auto py-2" 
                    type="button" name="entrprsId" value="사용가능"
                    readonly>
                </div> -->

            </div>
            <div class="form-group row">
                <label class="col-lg-3 col-form-label form-control-label line-height-9 pt-2 text-2 required">
                    기업명</label>
                <div class="col-lg-9">
                    <input class="form-control text-3 h-auto py-2" type="text" name="entrprsName" v-model="entrprsName" required>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-lg-3 col-form-label form-control-label line-height-9 pt-2 text-2">기업홈페이지</label>
                <div class="col-lg-9">
                    <input class="form-control text-3 h-auto py-2" type="url" name="entrprsHp" v-model="entrprsHp">
                </div>
            </div>
            <div class="form-group row mb-4">
                <label class="col-lg-3 col-form-label form-control-label line-height-9 pt-2 text-2 required">기업 주소</label>
                <div class="col-lg-7">
                    <input class="form-control text-3 h-auto py-2" type="text" name="entrprsAdrs" v-model="zonecode"
                        placeholder="우편번호">
                </div>
                <div class="col-lg-2">
                    <input class="btn btn-primary text-3 h-auto py-2" 
                    type="button" name="entrprsId" value="검 색"
                    @click="openPostcode">
                </div>
            </div>

            <div class="form-group row">
                <label
                    class="col-lg-3 col-form-label form-control-label line-height-9 pt-2 text-2 "></label>
                <div class="col-lg-9">
                    <input class="form-control text-3 h-auto py-2" type="text" name="entrprsPicName" 
                    placeholder="상세주소" v-model="entrprsAdrs">
                </div>
            </div>
            
            <div class="form-group row">
                <label
                    class="col-lg-3 col-form-label form-control-label line-height-9 pt-2 text-2 required">기업 담당자</label>
                <div class="col-lg-9">
                    <input class="form-control text-3 h-auto py-2" type="text" name="entrprsPicName" v-model="entrprsPicName" required >
                </div>
            </div>
            <div class="form-group row">
                <label
                    class="col-lg-3 col-form-label form-control-label line-height-9 pt-2 text-2 required">기업 담당자 전화번호</label>
                <div class="col-lg-9">
                    <input class="form-control text-3 h-auto py-2" type="text" name="entrprsPicMp" v-model="entrprsPicMp" required>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-lg-3 col-form-label form-control-label line-height-9 pt-2 text-2 required">기업 담당자 이메일주소
                    </label>
                <div class="col-lg-9">
                    <input class="form-control text-3 h-auto py-2" type="text" name="entrprsPicEml" v-model="entrprsPicEml"
                        required>
                </div>
            </div>
            <div class="form-group row">
                <div class="form-group col-lg-9">
                </div >

                <div class="d-flex justify-content-center">
                    <div class="form-group ">
                        <input type="button" value="cancel" class="btn btn-danger btn-modern float-end"
                        data-loading-text="Loading...">
                    </div>
                    <div class="form-group">
                        <input type="submit" value="Save" class="btn btn-primary btn-modern float-end"
                        data-loading-text="Loading...">
                    </div>
                </div>

            </div>
        </form>
    </div>
</div>
<!-- <div class="form-group">
    <input type="button" value="도로검색" class="btn btn-primary btn-modern float-end"
    @click="openAddress">
</div> -->
   
</template>

<script setup>
import { onMounted , ref , computed} from 'vue';
import store from '@/store'
import { api } from '@/axios';
import { showAlert } from '../../../utill/utillModal';
// import { useStore } from 'vuex';

const entrprsSq = ref(''); //기업순번
const entrprsId = ref(''); // 기업아이디
const entrprsName = ref(''); // 기업명
const zonecode = ref(''); // 우편번호
const entrprsAdrs = ref(''); // 상세주소
const entrprsHp = ref(''); // 기업홈페이지
const entrprsPicName = ref(''); // 담당자 이름
const entrprsPicEml = ref(''); // 담당자 이메일주소
const entrprsPicMp = ref(''); //담당자 전화번호

//const repetitionCheck = ref(0) //아이디 중복체크 여부 0-사용가능 / 1-사용불가능

const imgUrl = ref(''); // 이미지 임시url 저장

const img = ref(0);

// const file = ref(''); // 파일저장

const errorEntrprsPicMp = ref(''); // 핸드폰 번호 에러







onMounted(async() => {
    const test = computed(()=>{

return store.getters.getMember
})
console.log("pk:" + test.value.pk);

    try{
    const res = await api.$get('/enter/getEnterInfo', {
                params: {
                    entrprsSq : test.value.pk
                }
            });
    console.log(res);
    entrprsSq.value = res.entrprs_sq;
    entrprsId.value = res.entrprs_id;
    entrprsName.value = res.entrprs_name;
    entrprsAdrs.value = res.entrprs_adrs;
    entrprsHp.value = res.entrprs_hp;
    entrprsPicName.value = res.entrprs_pic_name;
    entrprsPicEml.value = res.entrprs_pic_eml;
    entrprsPicMp.value = res.entrprs_pic_mp;
    zonecode.value = res.entrprs_zip_code;


    }catch(error){
        console.error(error);
    }
});

// daum 주소검색 api
const openPostcode = (()=> {
    new window.daum.Postcode({
        oncomplete : (data) => {
            zonecode.value = data.zonecode;
            entrprsAdrs.value = data.roadAddress;
        }
        
    }).open();
})


// Save 버튼
const handleSubmit = async() => {
    if (!entrprsId.value || !entrprsName.value || !entrprsPicName.value || !entrprsPicMp.value || !entrprsPicEml.value) {
    alert('필수 항목을 모두 입력해 주세요.');
    return;
  }



  if (!/^01([0|1|6|7|8|9]?)?([0-9]{3,4})?([0-9]{4})$/.test(entrprsPicMp.value)) {
    errorEntrprsPicMp.value =
        "휴대폰 번호 형식이 올바르지 않습니다. (-를 제외한 10자리 또는 11자리 숫자)";
                return alert(errorEntrprsPicMp.value);
    }

  try {
    const data = {
      entrprsSq : entrprsSq.value,
      entrprsId: entrprsId.value,
      entrprsName: entrprsName.value,
      entrprsAdrs: entrprsAdrs.value,
      entrprsHp: entrprsHp.value,
      entrprsPicName: entrprsPicName.value,
      entrprsPicEml: entrprsPicEml.value,
      entrprsPicMp: entrprsPicMp.value,
      entrprsZipCode : zonecode.value
      
    };

    // console test
    console.log(data);

    try{
    const res = await api.$post('/enter/updateEnterInfo',  data);
            console.log(res);
            if(res == '수정완료'){
                alert('수정완료 되었습니다.');
            }else{
                alert('수정 실패');
            }

    }catch(error){
        console.error(error);
    } 

  }catch(error){
    console.log(error);
  }
};



// const uploadImg = async (e) => {
//      file.value = e.target.files[0]; // files 배열에서 첫 번째 파일 선택
//     if (file.value) {
//         let url = URL.createObjectURL(file.value);
//         imgUrl.value = url;
//         img.value = 1;
//         console.log(imgUrl);
//         console.log(imgUrl.value);
//         console.log(file);
//     } else {
//         console.error('No file selected');
//         img.value = 0;
//     }
// };

const uploadImg = async () => {
    showAlert("현재 준비중인 기능입니다");
}

//아이디 중복확인
// 아이디중복확인
// const idRepetitionCheck = async() => {
//     console.log("중복확인")
//     if(!entrprsId.value){
//     alert("아이디를 입력해 주세요");
//     return;
//     }
      
//     try{
//     const res = await axios.post('http://localhost:80/enter/idRepetitionCheck', {entrprsId : entrprsId.value});
//             console.log(res.data);
//             if(res.data == '사용가능'){
//                 repetitionCheck.value = 1;
//             }else{
//                 alert('중복된 아이디 입니다.')
//                 repetitionCheck.value = 0;
//             }

//     }catch(error){
//         console.error(error);
//     }       
// };


// 주소검색 api
// const openAddress = () => {
    
// }

</script>

<style>
.form-container {
    display: flex;
    justify-content: center;
}

</style>