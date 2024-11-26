<template>
  <!-- 모달 -->
  <div v-if="isModalVisible" class="modal">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="formModalLabel">
          회원정보수정을 위해서 비밀번호를 입력해주세요.
        </h5>
      </div>
      <div class="modal-body">
        <form
          id="demo-form"
          class="mb-4"
          novalidate="novalidate"
          @keydown.enter.prevent="pwSubmit"
        >
          <div class="form-group row align-items-center">
            <label class="col-sm-3 text-start text-sm-end mb-0">비밀번호</label>
            <div class="col-sm-9">
              <input
                type="password"
                name="mbr_pswrd"
                id="mbr_pswrd"
                class="form-control"
                v-model="inputValue"
                required=""
              />
            </div>
          </div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" @click="closeModal" class="btn btn-light">
          취소
        </button>
        <button type="button" @click="pwSubmit" class="btn btn-dark">
          확인
        </button>
      </div>
    </div>
  </div>

  <div v-if="showPage">
    <div
      class="heading heading-border heading-middle-border"
      style="margin-bottom: 50px"
    >
      <h1 class="font-weight-normal">
        회원정보 수정<strong class="font-weight-extra-bold"></strong>
      </h1>
    </div>
    <div class="d-flex justify-content-center mb-4">
      <div class="profile-image-outer-container">
        <div class="profile-image-inner-container bg-color-dark">
          <!-- 서버에서 이미지 URL을 받은 경우 -->
          <div v-if="result.mbr_img_file_url != null && img == 0">
            <img :src="result.mbr_img_file_url" alt="Profile Image" />
          </div>
          <!-- 서버에서 이미지 URL이 없는 경우, 기본 이미지 표시 -->
          <div v-else>
            <img
              src="@/assets/avatar.jpg"
              alt="Profile Image"
              v-if="img == 0"
            />
          </div>

          <!-- 파일 업로드 후 미리보기 이미지 -->
          <img
            :src="imgUrl"
            alt="Profile Image"
            id="mbrImgFileUrl"
            v-if="img == 1"
          />

          <input
            type="hidden"
            name="mbrImgOrgnlFn"
            id="mbrImgOrgnlFn"
            v-model="mbrImgOrgnlFn"
          />
          <input
            type="hidden"
            name="mbrImgFileUrl"
            id="mbrImgFileUrl"
            v-model="mbrImgFileUrl"
          />

          <!-- 프로필 이미지 업로드 버튼 -->
          <span class="profile-image-button bg-color-dark">
            <i class="fas fa-camera text-light"></i>
          </span>
        </div>

        <!-- 파일 선택 input -->
        <input
          type="file"
          id="fileInput"
          class="form-control profile-image-input"
          @change="handleFileChange"
          accept="image/gif, image/jpeg, image/png"
        />
      </div>
    </div>

    <div class="form-container">
      <div class="col-lg-11">
        <form
          role="form"
          class="needs-validation"
          @submit.prevent="handleSubmit"
        >
          <div class="form-group row mb-4">
            <label
              class="col-lg-3 col-form-label form-control-label line-height-9 pt-2 text-2 required"
              >아이디</label
            >
            <div class="col-lg-7">
              <input
                class="form-control text-3 h-auto py-2"
                type="text"
                name="mbr_id"
                id="mbr_id"
                v-model="mbrId"
                :readonly="repetitionCheck == 1"
              />
              <input type="hidden" name="mbr_sq" id="mbr_sq" v-model="mbrSq" />
            </div>
            <!--아이디 중복체크-->
            <div class="col-lg-2" v-if="repetitionCheck == 0">
              <input
                class="btn btn-modern btn-dark mb-2"
                type="button"
                name="mbr_id"
                value="중복확인"
                @click="idRepetitionCheck"
              />
            </div>
            <div class="col-lg-2" v-if="repetitionCheck == 1">
              <input
                class="btn btn-modern btn-light mb-2"
                type="button"
                name="mbr_id"
                value="사용가능"
                readonly
              />
            </div>
          </div>

          <div class="form-group row">
            <label
              class="col-lg-3 col-form-label form-control-label line-height-9 pt-2 text-2 required"
              >이름</label
            >
            <div class="col-lg-9">
              <input
                class="form-control text-3 h-auto py-2"
                type="text"
                name="mbr_name"
                id="mbr_name"
                v-model="mbrName"
              />
            </div>
          </div>

          <div class="form-group row mb-4">
            <label
              class="col-lg-3 col-form-label form-control-label line-height-9 pt-2 text-2 required"
              >이메일주소</label
            >
            <div class="col-lg-7">
              <input
                class="form-control text-3 h-auto py-2"
                type="email"
                id="mbr_eml_adrs"
                v-model="mbrEml"
                :disabled="emailSent"
                name="mbrEmlAdrs"
              />
            </div>
            <div class="col-lg-2">
              <input
                class="btn btn-modern btn-dark mb-2"
                type="button"
                name="mbr_eml_button"
                value="인증 요청"
                @click="requestAuthCode"
                :disabled="emailSent || loading"
              />
            </div>
            <div
              v-if="loading"
              class="spinner-border text-primary"
              role="status"
            >
              <span class="sr-only">Loading...</span>
            </div>
          </div>

          <div v-if="emailSent" class="form-group row mb-4">
            <label
              class="col-lg-3 col-form-label form-control-label line-height-9 pt-2 text-2 required"
              >인증번호</label
            >
            <div class="col-lg-7">
              <input
                type="text"
                class="form-control auth-code-input"
                v-model="formData.authCode"
                :disabled="emailChack"
              />
            </div>
            <div class="col-lg-2">
              <input
                class="btn btn-modern btn-dark mb-2"
                type="button"
                name="mbr_eml_button"
                value="인 증"
                @click="verifyAuthCode"
                :disabled="emailChack"
              />
            </div>
          </div>

          <div v-if="result.mbr_mp != null">
            <div class="form-group row">
              <label
                class="col-lg-3 col-form-label form-control-label line-height-9 pt-2 text-2 required"
                >휴대폰번호</label
              >
              <div class="col-lg-9">
                <input
                  class="form-control text-3 h-auto py-2"
                  type="text"
                  name="mbr_mp"
                  id="mbr_mp"
                  v-model="mbrMp"
                />
              </div>
            </div>
          </div>
          <div v-else>
            <div class="form-group row">
              <label
                class="col-lg-3 col-form-label form-control-label line-height-9 pt-2 text-2 required"
                >휴대폰번호</label
              >
              <div class="col-lg-9">
                <input
                  class="form-control text-3 h-auto py-2"
                  type="text"
                  name="mbr_mp"
                  id="mbr_mp"
                  value=""
                />
              </div>
            </div>
          </div>

          <div v-if="result.mbr_adrs != null">
            <div class="form-group row mb-4">
              <label
                class="col-lg-3 col-form-label form-control-label line-height-9 pt-2 text-2"
                >주소</label
              >
              <div class="col-lg-7">
                <input
                  class="form-control text-3 h-auto py-2"
                  type="text"
                  name="mbr_adrs_post"
                  id="postcode"
                  placeholder="우편번호"
                  v-model="mbrPost"
                />
              </div>
              <div class="col-lg-2">
                <input
                  class="btn btn-modern btn-light mb-2"
                  type="button"
                  name="mbr_adrs_button"
                  value="검 색"
                  @click="openPostcode"
                />
              </div>
            </div>
          </div>

          <div v-else>
            <div class="form-group row mb-4">
              <label
                class="col-lg-3 col-form-label form-control-label line-height-9 pt-2 text-2"
                >주소</label
              >
              <div class="col-lg-7">
                <input
                  class="form-control text-3 h-auto py-2"
                  type="text"
                  name="mbr_adrs_post"
                  id="postcode"
                  v-model="mbrPost"
                />
              </div>
              <div class="col-lg-2">
                <input
                  class="btn btn-modern btn-light mb-2"
                  type="button"
                  name="mbr_adrs_button"
                  value="검 색"
                  @click="openPostcode"
                />
              </div>
            </div>
          </div>

          <div v-if="result.mbr_adrs != null">
            <div class="form-group row">
              <label
                class="col-lg-3 col-form-label form-control-label line-height-9 pt-2 text-2"
              ></label>
              <div class="col-lg-9">
                <input
                  class="form-control text-3 h-auto py-2"
                  type="text"
                  name="mbr_adrs"
                  id="roadAddress"
                  placeholder="상세주소"
                  v-model="mbrAdrs"
                />
              </div>
            </div>
          </div>
          <div v-else>
            <div class="form-group row">
              <label
                class="col-lg-3 col-form-label form-control-label line-height-9 pt-2 text-2"
              ></label>
              <div class="col-lg-9">
                <input
                  class="form-control text-3 h-auto py-2"
                  type="text"
                  name="mbr_adrs"
                  v-model="mbrAdrs"
                />
              </div>
            </div>
          </div>

          <br />

          <div class="form-group row">
            <div class="button-container">
              <input
                type="submit"
                value="수정하기"
                id="abtn1"
                class="btn btn-modern btn-dark mb-2"
                data-loading-text="Loading..."
              />
              <router-link
                class="btn btn-modern btn-light mb-2"
                to="/mypage/home"
                >취소</router-link
              >
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref, computed } from "vue";
// import store from '@/store'
import { api } from "@/axios";
import { useStore } from "vuex";
import { useRouter, useRoute } from "vue-router";

const store = useStore();
const router = useRouter();

const repetitionCheck = ref(0); //아이디 중복체크 여부 0-사용가능 / 1-사용불가능
const idck = ref(0); //아이디 체크 버튼 클릭 여부
const emlck = ref(0); //이메일 인증 번호 클릭 여부

const imgUrl = ref(""); // 이미지 임시url 저장

const img = ref(0);

const file = ref(""); // 파일저장

const mbrSq = ref("");
const mbrId = ref(""); //회원 아이디
const mbrName = ref(""); //회원 이름
const mbrEml = ref(""); // 회원 이메일
const mbrMp = ref(""); //회원 전화번호
const mbrPost = ref(""); //회원 우편번호
const mbrAdrs = ref(""); //회원 주소
const mbrImgOrgnlFn = ref(""); //이미지명
const mbrImgFileUrl = ref(""); //이미지 url

const loading = ref(false);
const emailChack = ref(false);
const emailCode = ref("");
const emailSent = ref(false);
const emailError = ref("");
const formData = ref({
  mbrEmlAdrs: "",
  authCode: "",
});

let result = ref({});
const member = computed(() => store.getters.getMember);

// 모달 상태와 입력값
const isModalVisible = ref(false);
const inputValue = ref("");
const showPage = ref(false);

// Vue Router의 현재 쿼리 파라미터를 사용
const route = useRoute();

onMounted(async () => {
  // 쿼리 파라미터에 'modal=true'가 있으면 모달을 열기
  if (route.query.modal === "true") {
    isModalVisible.value = true;
  }
  try {
    const response = await api.$get("/user/mypage/infrmtn", {
      params: {
        mbr_sq: store.state.member.mbrSq,
      },
    });
    result.value = response || {}; // 데이터를 받지 못했을 때 기본 빈 객체로 설정
    console.log(result.value);
    mbrSq.value = member.value.mbrSq;
    mbrId.value = member.value.mbrId;
    mbrName.value = result.value.mbr_name;
    mbrEml.value = result.value.mbr_eml_adrs;
    mbrMp.value = result.value.mbr_mp;
    mbrAdrs.value = result.value.mbr_adrs.substr(6);
    mbrPost.value = result.value.mbr_adrs.substring(0, 5);
    mbrImgOrgnlFn.value = result.value.mbrImgOrgnlFn;
    mbrImgFileUrl.value = result.value.mbrImgFileUrl;
  } catch (error) {
    console.error("API 호출 오류:", error);
    result.value = {}; // 오류가 발생했을 때 기본 빈 객체로 설정
  }
});

// 모달 닫기
const closeModal = () => {
  router.push("/mypage/home");
};

// 제출 처리
const pwSubmit = async () => {
  if (inputValue.value == "") {
    alert("비밀번호를 입력해주세요.");
    return;
  }

  try {
    const response = await api.$get("/member/pwCk", {
      params: {
        mbr_id: store.state.member.mbrId,
        mbr_pw: inputValue.value,
      },
    });
    console.log(response);
    if (response === "성공") {
      showPage.value = true;
      isModalVisible.value = false;
    } else {
      alert("비밀번호가 틀렸습니다.");
      showPage.value = false;
    }
  } catch (error) {
    console.error("API 호출 오류:", error);
  }
};

// daum 주소검색 api
const openPostcode = () => {
  new window.daum.Postcode({
    oncomplete: (data) => {
      var roadAddr = data.roadAddress;
      var extraRoadAddr = "";

      if (data.bname !== "" && /[동|로|가]$/g.test(data.bname)) {
        extraRoadAddr += data.bname;
      }

      if (data.buildingName !== "" && data.apartment === "Y") {
        extraRoadAddr +=
          extraRoadAddr !== "" ? ", " + data.buildingName : data.buildingName;
      }

      if (extraRoadAddr !== "") {
        extraRoadAddr = " (" + extraRoadAddr + ")";
      }

      // Update Vue's reactive properties directly
      mbrPost.value = data.zonecode; // Using 'ref' value directly
      mbrAdrs.value = roadAddr + extraRoadAddr; // Using 'ref' value directly
    },
  }).open();
};

const validateEmail = (email) => {
  const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return re.test(email);
};

const requestAuthCode = () => {
  const emailValue = mbrEml.value.trim();
  if (emailValue === "") {
    emailError.value = "이메일을 입력해주세요.";
    emlck.value = 2;
    alert(emailError.value);
    return;
  } else if (!validateEmail(emailValue)) {
    emailError.value = "유효한 이메일 주소를 입력해주세요.";
    emlck.value = 2;
    alert(emailError.value);
    return;
  }
  emailError.value = "";

  // 이메일로 인증번호 요청 로직 추가
  const map = {
    mbrEmlAdrs: mbrEml.value,
  };
  loading.value = true;
  api
    .$post("/member/emlRegister", map)
    .then((response) => {
      emailCode.value = response.key;
      emailSent.value = true;
      alert("인증번호가 이메일로 전송되었습니다.");
      console.log(response.key);
      emlck.value = 2;
    })
    .catch((error) => {
      console.error("이메일 전송 중 오류 발생:", error);
      if (error.response && error.response.data) {
        alert(error.response.data.error);
      } else {
        alert("이메일 전송 중 오류가 발생했습니다.");
      }
      // 전송 실패 시 버튼 및 입력 필드를 다시 활성화
      emailSent.value = false;
    })
    .finally(() => {
      loading.value = false;
    });
};

const verifyAuthCode = () => {
  if (!formData.value.authCode) {
    alert("인증번호를 입력해주세요.");
    return;
  }
  // 인증번호 확인 로직 추가
  if (emailCode.value === formData.value.authCode.trim("")) {
    emlck.value = 0;
    alert("인증번호가 확인되었습니다.");
    emailChack.value = true;
  } else {
    alert("인증번호가 맞지 않습니다.");
  }
};

// 파일 선택 핸들러
const handleFileChange = async (e) => {
  const selectedFile = e.target.files[0]; // 선택된 파일 저장
  if (selectedFile) {
    const reader = new FileReader();
    reader.onload = function (event) {
      imgUrl.value = event.target.result; // FileReader로 얻은 Data URL을 imgUrl에 설정
      img.value = 1; // 이미지 표시 상태 (업로드 전 미리보기)
    };
    reader.readAsDataURL(selectedFile); // 선택된 파일을 Data URL로 읽기

    const formData = new FormData();
    formData.append("file", selectedFile);

    try {
      // 이미지 파일을 서버에 업로드
      const response = await api.$post("/file/upload-image", formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      });

      console.log("서버 응답:", response);

      // 서버 응답에서 URL을 확인하여 이미지 URL 설정
      if (response && response.imgFileUrl) {
        const imageUrl = response.imgFileUrl;
        imgUrl.value = imageUrl; // 실제 이미지 URL로 설정
        img.value = 1; // 이미지 표시 상태 (업로드 완료 후)
      } else {
        throw new Error("서버에서 이미지 URL을 반환하지 않았습니다.");
      }

      // 서버에서 반환된 이미지 URL을 hidden input에 설정
      mbrImgFileUrl.value = response.imgFileUrl; // imgFileUrl을 hidden input에 설정
      mbrImgOrgnlFn.value = selectedFile.name; // selectedFile.name을 사용하여 파일명 설정
    } catch (error) {
      console.error("이미지 업로드 중 오류 발생:", error);
      alert("이미지 업로드 중 오류가 발생했습니다. 다시 시도해주세요.");
      img.value = 0; // 이미지 표시 상태 초기화
    }
  }
};

// Save 버튼
const handleSubmit = async (e) => {
  e.preventDefault(); // 폼 제출 기본 동작 방지

  const mbr_id = document.getElementById("mbr_id");
  const mbr_name = document.getElementById("mbr_name");
  const mbr_mp = document.getElementById("mbr_mp");
  const mbr_eml_adrs = document.getElementById("mbr_eml_adrs");
  const mbrImgOrgnlFn = document.getElementById("mbrImgOrgnlFn");
  const mbrImgFileUrl = document.getElementById("mbrImgFileUrl");
  if (
    !mbr_id.value ||
    !mbr_name.value ||
    !mbr_mp.value ||
    !mbr_eml_adrs.value
  ) {
    alert("필수 항목을 모두 입력해 주세요.");
    return;
  } else if (idck.value == 2) {
    alert("아이디 인증을 해주세요.");
    return;
  } else if (emlck.value == 2) {
    alert("이메일 인증을 해주세요.");
    return;
  }

  if (!/^01([0|1|6|7|8|9]?)?([0-9]{3,4})?([0-9]{4})$/.test(mbr_mp.value)) {
    return alert(
      "휴대폰 번호 형식이 올바르지 않습니다. \n (-를 제외한 10자리 또는 11자리 숫자)"
    );
  }

  if (file.value && file.value.size > 0) {
    // 파일 업로드
    try {
      const formData = new FormData();
      if (file.value) {
        formData.append("file", file.value);
      }

      const response = await api.$post("/file/upload-image", formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      });

      // 서버 응답 처리
      const newImageUrl = response.imgFileUrl;
      imgUrl.value = newImageUrl;
      mbrImgOrgnlFn.value = file.value ? file.value.name : "";
      mbrImgFileUrl.value = newImageUrl;
    } catch (error) {
      console.error("Error uploading file:", error);
      img.value = 0; // 이미지 표시 상태 초기화
    }
  }

  try {
    const data = {
      mbrSq: mbrSq.value,
      mbrId: mbrId.value,
      mbrName: mbrName.value,
      mbrEmlAdrs: mbrEml.value,
      mbrMp: mbrMp.value,
      mbrPswrd: mbrPost.value,
      mbrAdrs: mbrAdrs.value,
      mbrImgOrgnlFn: mbrImgOrgnlFn.value,
      mbrImgFileUrl: mbrImgFileUrl.value,
    };

    // console test
    console.log(data);

    try {
      const res = await api.$post("/member/update", data);
      console.log(res);
      if (res == "수정완료") {
        alert("수정 완료 되었습니다.");
        if (repetitionCheck.value == 1) {
          router.push("/").then(() => {
            store.commit("clearMember");
            store.commit("changeUserType", "user");
          });
        } else {
          router.push("/mypage/home");
        }
      } else {
        alert("수정 실패");
      }
    } catch (error) {
      console.error(error);
    }
  } catch (error) {
    console.log(error);
  }
};

//아이디 중복확인
const idRepetitionCheck = async () => {
  const mbr_id = document.getElementById("mbr_id");
  console.log("중복확인");
  if (!mbr_id.value) {
    alert("아이디를 입력해 주세요");
    return;
  }

  try {
    const res = await api.$post("/member/idCheck", { mbrId: mbr_id.value });
    console.log(res);
    if (res == 0) {
      idck.value = 0;
      repetitionCheck.value = 1;
    } else {
      alert("중복된 아이디 입니다.");
      idck.value = 2;
      repetitionCheck.value = 0;
    }
  } catch (error) {
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
  justify-content: center;
}

#abtn1,
#abtn2 {
  width: 110px;
}
</style>

<style scoped>
.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
}

.modal-content {
  background: white;
  padding: 20px;
  border-radius: 8px;
  text-align: center;
  width: 80%; /* 모달 너비 조정 */
  max-width: 500px; /* 최대 너비 조정 */
}
</style>
