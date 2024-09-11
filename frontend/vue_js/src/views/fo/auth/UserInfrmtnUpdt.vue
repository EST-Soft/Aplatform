<template>
    <div class="heading heading-border heading-middle-border">
        <h1 class="font-weight-normal">회원정보 수정<strong class="font-weight-extra-bold"></strong></h1>
    </div>
    <div class="d-flex justify-content-center mb-4">
        <div class="profile-image-outer-container">
            <div class="profile-image-inner-container bg-color-dark">
                <img src="@/assets/avatar.jpg" alt="Profile Image" v-if="img==0">
                <img :src="imgUrl" alt="Profile Image" id="mbrImgFileUrl" v-if="img==1">
                <input type="hidden" name="mbrImgOrgnlFn" id="mbrImgOrgnlFn">
                <input type="hidden" name="mbrImgFileUrl" id="mbrImgFileUrl">
                <span class="profile-image-button bg-color-dark">
                    <i class="fas fa-camera text-light"></i>
                </span>
            </div>
            <input type="file" id="fileInput" class="form-control profile-image-input" @change="handleFileChange" accept="image/gif, image/jpeg, image/png">
        </div>
    </div>

    <div class="form-container">
        <div class="col-lg-11">
            <form role="form" class="needs-validation" @submit.prevent="handleSubmit">
                <div class="form-group row mb-4">
                    <label class="col-lg-3 col-form-label form-control-label line-height-9 pt-2 text-2 required">아이디</label>
                    <div class="col-lg-7">
                        <input class="form-control text-3 h-auto py-2" type="text" name="mbr_id" id="mbr_id" v-model="mbrId" :readonly="repetitionCheck == 1">
                        <input type="hidden" name="mbr_sq" id="mbr_sq" v-model="mbrSq">
                    </div>
                    <!--아이디 중복체크-->
                    <div class="col-lg-2" v-if="repetitionCheck == 0">
                        <input class="btn btn-modern btn-dark mb-2" type="button" name="mbr_id" value="중복확인"  @click="idRepetitionCheck">
                    </div>
                    <div class="col-lg-2" v-if="repetitionCheck == 1">
                        <input class="btn btn-modern btn-light mb-2" type="button" name="mbr_id" value="사용가능" readonly>
                    </div>
                </div>

                <div class="form-group row">
                    <label class="col-lg-3 col-form-label form-control-label line-height-9 pt-2 text-2 required">이름</label>
                    <div class="col-lg-9">
                        <input class="form-control text-3 h-auto py-2" type="text" name="mbr_name" id="mbr_name" v-model="mbrName">
                    </div>
                </div>
                
                <div v-if="result.mbr_eml_adrs != null">
                    <div class="form-group row">
                        <label class="col-lg-3 col-form-label form-control-label line-height-9 pt-2 text-2 required">이메일주소</label>
                        <div class="col-lg-9">
                            <input class="form-control text-3 h-auto py-2" type="email" name="mbr_eml_adrs" id="mbr_eml_adrs" v-model="mbrEml">
                        </div>
                    </div>
                </div>
                <div v-else>
                    <div class="form-group row">
                        <label class="col-lg-3 col-form-label form-control-label line-height-9 pt-2 text-2 required">이메일주소</label>
                        <div class="col-lg-9">
                            <input class="form-control text-3 h-auto py-2" type="email" name="mbr_eml_adrs" id="mbr_eml_adrs" value="">
                        </div>
                    </div>
                </div>

                <div v-if="result.mbr_mp != null">
                    <div class="form-group row">
                        <label class="col-lg-3 col-form-label form-control-label line-height-9 pt-2 text-2 required">휴대폰번호</label>
                        <div class="col-lg-9">
                            <input class="form-control text-3 h-auto py-2" type="text" name="mbr_mp" id="mbr_mp" v-model="mbrMp">
                        </div>
                    </div>
                </div>
                <div v-else>
                    <div class="form-group row">
                        <label class="col-lg-3 col-form-label form-control-label line-height-9 pt-2 text-2 required">휴대폰번호</label>
                        <div class="col-lg-9">
                            <input class="form-control text-3 h-auto py-2" type="text" name="mbr_mp" id="mbr_mp" value="">
                        </div>
                    </div>
                </div>
                
                <div v-if="result.mbr_adrs != null">
                    <div class="form-group row mb-4">
                        <label class="col-lg-3 col-form-label form-control-label line-height-9 pt-2 text-2">주소</label>
                        <div class="col-lg-7">
                            <input class="form-control text-3 h-auto py-2" type="text" name="mbr_adrs_post" id="postcode" placeholder="우편번호" v-model="mbrPost">
                        </div>
                        <div class="col-lg-2">
                            <input class="btn btn-modern btn-light mb-2" type="button" name="mbr_adrs_button" value="검 색" @click="openPostcode">
                        </div>
                    </div>
                </div>
                <div v-else>
                    <div class="form-group row mb-4">
                        <label class="col-lg-3 col-form-label form-control-label line-height-9 pt-2 text-2">주소</label>
                        <div class="col-lg-7">
                            <input class="form-control text-3 h-auto py-2" type="text" name="mbr_adrs_post" id="postcode" value="">
                        </div>
                        <div class="col-lg-2">
                            <input class="btn btn-modern btn-light mb-2" type="button" name="mbr_adrs_button" value="검 색" @click="openPostcode">
                        </div>
                    </div>
                </div>

                <div v-if="result.mbr_adrs != null">
                    <div class="form-group row">
                        <label class="col-lg-3 col-form-label form-control-label line-height-9 pt-2 text-2"></label>
                        <div class="col-lg-9">
                            <input class="form-control text-3 h-auto py-2" type="text" name="mbr_adrs" id="roadAddress" placeholder="상세주소" v-model="mbrAdrs">
                        </div>
                    </div>
                </div>
                <div v-else>
                    <div class="form-group row">
                        <label class="col-lg-3 col-form-label form-control-label line-height-9 pt-2 text-2"></label>
                        <div class="col-lg-9">
                            <input class="form-control text-3 h-auto py-2" type="text" name="mbr_adrs" value="">
                        </div>
                    </div>
                </div>

                <br>
                
                <div class="form-group row">
                    <div class="button-container">
                        <input type="submit" value="수정하기" id="abtn1" class="btn btn-modern btn-light mb-2" data-loading-text="Loading...">
                        <router-link class="btn btn-modern btn-dark mb-2" to="/mypage/home">취소</router-link>
                    </div>
                </div>
            </form>
        </div>
    </div>
</template>

<script setup>
import { onMounted , ref, computed} from 'vue';
// import store from '@/store'
import { api } from "@/axios";
import { useStore } from "vuex";
import axios from 'axios';
import { useRouter } from "vue-router";

const store = useStore();
const router = useRouter();

const repetitionCheck = ref(0) //아이디 중복체크 여부 0-사용가능 / 1-사용불가능

const imgUrl = ref(''); // 이미지 임시url 저장

const img = ref(0);

const file = ref(''); // 파일저장

const mbrSq = ref('');
const mbrId = ref(''); //회원 아이디
const mbrName = ref(''); //회원 이름
const mbrEml = ref(''); // 회원 이메일
const mbrMp = ref(''); //회원 전화번호
const mbrPost = ref(''); //회원 우편번호
const mbrAdrs = ref(''); //회원 주소

let result = ref({});
const member = computed(() => store.getters.getMember);

onMounted(async () => {
  try {
    const response = await api.$get('/user/mypage/infrmtn', {
      params: {
        mbr_sq: store.state.member.mbrSq,
      },
    });
    result.value = response || {};  // 데이터를 받지 못했을 때 기본 빈 객체로 설정
    console.log(result.value);
    mbrSq.value = member.value.mbrSq;
    mbrId.value = member.value.mbrId;
    mbrName.value = result.value.mbr_name;
    mbrEml.value = result.value.mbr_eml_adrs;
    mbrMp.value = result.value.mbr_mp;
    mbrAdrs.value = result.value.mbr_adrs.substr(6);
    mbrPost.value = result.value.mbr_adrs.substring(0,5);
    
  } catch (error) {
    console.error("API 호출 오류:", error);
    result.value = {};  // 오류가 발생했을 때 기본 빈 객체로 설정
  }
});

// daum 주소검색 api
const openPostcode = (()=> {
    new window.daum.Postcode({
        oncomplete: (data) => {
            // 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var roadAddr = data.roadAddress; // 도로명 주소 변수
            var extraRoadAddr = ''; // 참고 항목 변수

            // 법정동명이 있을 경우 추가한다. (법정리는 제외)
            // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
            if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                extraRoadAddr += data.bname;
            }
            // 건물명이 있고, 공동주택일 경우 추가한다.
            if(data.buildingName !== '' && data.apartment === 'Y'){
                extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
            }
            // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
            if(extraRoadAddr !== ''){
                extraRoadAddr = ' (' + extraRoadAddr + ')';
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.getElementById('postcode').value = data.zonecode;
            document.getElementById('roadAddress').value = roadAddr;
        }
    }).open();
})

// 파일 선택 핸들러
const handleFileChange = (e) => {

    file.value = e.target.files[0]; // 선택된 파일 저장
    if (file.value) {
        const reader = new FileReader();
        reader.onloadend = () => {
        imgUrl.value = reader.result; // 미리보기 URL 설정
        img.value = 1; // 이미지 표시 상태
        };
        reader.readAsDataURL(file.value); // 파일을 Data URL로 읽기
    } else {
        img.value = 0; // 이미지 표시 상태 초기화
    }
};


// Save 버튼
const handleSubmit = async(e) => {
    e.preventDefault(); // 폼 제출 기본 동작 방지

    const mbr_id = document.getElementById('mbr_id');
    const mbr_name = document.getElementById('mbr_name');
    const mbr_mp = document.getElementById('mbr_mp');
    const mbr_eml_adrs = document.getElementById('mbr_eml_adrs');
    const mbrImgOrgnlFn = document.getElementById('mbrImgOrgnlFn');
    const mbrImgFileUrl = document.getElementById('mbrImgFileUrl');
    if (!mbr_id.value || !mbr_name.value || !mbr_mp.value || !mbr_eml_adrs.value) {
    alert('필수 항목을 모두 입력해 주세요.');
    return;
  }

  if (!/^01([0|1|6|7|8|9]?)?([0-9]{3,4})?([0-9]{4})$/.test(mbr_mp.value)) {
        return alert('휴대폰 번호 형식이 올바르지 않습니다. \n (-를 제외한 10자리 또는 11자리 숫자)');
    }

  // 파일 업로드
  try {
    const formData = new FormData();
    if (file.value) {
      formData.append('file', file.value);
    }

    const response = await axios.post('http://localhost:80/file/upload-image', formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    });

     // 서버 응답 처리
     const newImageUrl = response.data.imgFileUrl;
        imgUrl.value = newImageUrl;
        mbrImgOrgnlFn.value = file.value ? file.value.name : '';
        mbrImgFileUrl.value = newImageUrl;
    } catch (error) {
        console.error('Error uploading file:', error);
        img.value = 0; // 이미지 표시 상태 초기화
    }

  try {
    const data = {
      mbrSq : mbrSq.value,
      mbrId: mbrId.value,
      mbrName: mbrName.value,
      mbrEmlAdrs: mbrEml.value,
      mbrMp: mbrMp.value,
      mbrPswrd: mbrPost.value,
      mbrAdrs: mbrAdrs.value,
      mbrImgOrgnlFn: mbrImgOrgnlFn.value,
      mbrImgFileUrl: mbrImgFileUrl.value
    };

    // console test
    console.log(data);

    try{
    const res = await axios.post('http://localhost:80/member/update',  data);
            console.log(res.data);
            if(res.data == '수정완료'){
                alert('수정 완료 되었습니다.');
                router.push('/mypage/home');
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

//이미지 업로드
// const uploadImg = async (e) => {
    // const mbrImgOrgnlFn = document.getElementById('mbrImgOrgnlFn');
    // const mbrImgFileUrl = document.getElementById('mbrImgFileUrl');
    // if (file.value) {
        //     let url = URL.createObjectURL(file.value);
        //     imgUrl.value = url;
    //     img.value = 1;
    //     console.log(imgUrl);
    //     console.log(imgUrl.value);
    //     console.log(file);
    //     mbrImgOrgnlFn.value = file.value.name;
    // } else {
        //     console.error('No file selected');
        //     img.value = 0;
        // }

    // const fileInput = e.target; // 파일 입력 요소
    // const file = fileInput.files[0]; // 선택된 파일

    // if (file) {
    //     try {
    //         // 파일 리더 생성
    //         const reader = new FileReader();

    //         // 파일 리더가 파일을 읽은 후 처리
    //         reader.onloadend = async () => {
    //             try {
    //                 // FormData 객체 생성
    //                 const formData = new FormData();
    //                 formData.append('file', file);

    //                 // 서버에 파일 업로드
    //                 const response = await axios.post('http://localhost:80/file/upload-image', formData, {
    //                     headers: {
    //                         'Content-Type': 'multipart/form-data'
    //                     }
    //                 });

    //                 // 서버가 반환한 이미지 URL을 업데이트
    //                 const newImageUrl = response.data.imgFileUrl; // 서버에서 반환하는 URL 필드 이름에 맞게 수정하세요.
    //                 imgUrl.value = newImageUrl; // Vue 데이터 속성에 이미지 URL 설정
    //                 mbrImgFileUrl.value = newImageUrl;
    //                 mbrImgOrgnlFn.value = file.name; // 파일 이름 설정
    //                 img.value = 1; // 이미지 표시 상태로 변경
    //                 console.log(response.data);

    //             } catch (error) {
    //                 console.error('Error uploading file:', error);
    //                 img.value = 0; // 이미지 표시 상태를 기본으로 변경
    //             }
    //         };

    //         // 파일을 Data URL로 읽어 미리보기
    //         reader.readAsDataURL(file);

    //     } catch (error) {
    //         console.error('Error reading file:', error);
    //     }
    // } else {
    //     console.error('No file selected');
    //     img.value = 0; // 이미지 표시 상태를 기본으로 변경
    // }
    
// };

//아이디 중복확인
const idRepetitionCheck = async() => {
    const mbr_id = document.getElementById('mbr_id');
    console.log("중복확인")
    if(!mbr_id.value){
    alert("아이디를 입력해 주세요");
    return;
    }
      
    try{
    const res = await axios.post('http://localhost:80/member/idCheck', {mbrId : mbr_id.value});
            console.log(res.data);
            if(res.data == 0){

                repetitionCheck.value = 1;
            }else{
                alert('중복된 아이디 입니다.')
                repetitionCheck.value = 0;
            }

    }catch(error){
        console.error(error);
    }       
};

</script>

<style>
.form-container {
    display: flex;
    justify-content: center;
    max-width: 600px;
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
  
.button-container {
    display: flex;
}

#abtn1, #abtn2 {
    width: 110px;
}

</style>