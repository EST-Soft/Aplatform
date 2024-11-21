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
                <img src="@/assets/avatar.jpg" alt="Profile Image" v-if="img == 0">
                <img :src="imgUrl" alt="Profile Image" v-if="img == 1">
                <span class="profile-image-button bg-color-dark">
                    <i class="fas fa-camera text-light"></i>
                </span>
            </div>
            <input type="file" id="file" class="form-control profile-image-input"
                name="entrprsImg"
                @change="uploadImg"
                accept="image/gif, image/jpeg, image/png">
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
                    <label class="col-lg-3 col-form-label form-control-label line-height-9 pt-2 text-2 "></label>
                    <div class="col-lg-9">
                        <input class="form-control text-3 h-auto py-2" type="text" name="entrprsPicName"
                        placeholder="상세주소" v-model="entrprsAdrs">
                    </div>
                </div>

                <div class="form-group row">
                    <label class="col-lg-3 col-form-label form-control-label line-height-9 pt-2 text-2 required">기업 담당자</label>
                    <div class="col-lg-9">
                        <input class="form-control text-3 h-auto py-2" type="text" name="entrprsPicName" v-model="entrprsPicName" required >
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-lg-3 col-form-label form-control-label line-height-9 pt-2 text-2 required">기업 담당자 전화번호</label>
                    <div class="col-lg-9">
                        <input class="form-control text-3 h-auto py-2" type="text" name="entrprsPicMp" v-model="entrprsPicMp" required>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-lg-3 col-form-label form-control-label line-height-9 pt-2 text-2 required">기업 담당자 이메일주소</label>
                    <div class="col-lg-9">
                        <input class="form-control text-3 h-auto py-2" type="text" name="entrprsPicEml" v-model="entrprsPicEml" required>
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
</template>

<script setup>
import { onMounted, ref, computed } from 'vue';
import store from '@/store'
import { api } from '@/axios';

const entrprsSq = ref(''); 
const entrprsId = ref(''); 
const entrprsName = ref(''); 
const zonecode = ref(''); 
const entrprsAdrs = ref(''); 
const entrprsHp = ref(''); 
const entrprsPicName = ref(''); 
const entrprsPicEml = ref(''); 
const entrprsPicMp = ref(''); 
const logoFileUrl = ref(''); 
const imgUrl = ref(''); 
const img = ref(0); 


onMounted(async () => {
    const test = computed(() => {
        return store.getters.getMember;
    });
    console.log("pk:" + test.value.pk);

    try {
        const res = await api.$get('/enter/getEnterInfo', {
            params: {
                entrprsSq: test.value.pk
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
        imgUrl.value = res.logo_file_url; 
        logoFileUrl.value = res.logo_file_url; 
        img.value = res.logo_file_url ? 1 : 0;
    } catch (error) {
        console.error(error);
    }
});

// daum 주소검색 API
const openPostcode = () => {
    new window.daum.Postcode({
        oncomplete: (data) => {
            zonecode.value = data.zonecode;
            entrprsAdrs.value = data.roadAddress;
        }
    }).open();
};

// Save 버튼
const handleSubmit = async () => {
    if (!entrprsId.value || !entrprsName.value || !entrprsPicName.value || !entrprsPicMp.value || !entrprsPicEml.value) {
        alert('필수 항목을 모두 입력해 주세요.');
        return;
    }

    if (!/^01([0|1|6|7|8|9]?)?([0-9]{3,4})([0-9]{4})$/.test(entrprsPicMp.value)) {
        alert('전화번호 형식이 올바르지 않습니다.');
        return;
    }
    
    if (!logoFileUrl.value) {
        alert('이미지 URL을 확인 중입니다. 다시 시도해 주세요.');
        return;
    }

    try {
        const data = {
            entrprsSq: entrprsSq.value,
            entrprsId: entrprsId.value,
            entrprsName: entrprsName.value,
            entrprsHp: entrprsHp.value,
            entrprsPicName: entrprsPicName.value,
            entrprsPicMp: entrprsPicMp.value,
            entrprsPicEml: entrprsPicEml.value,
            entrprsAdrs: entrprsAdrs.value,
            logoFileUrl: logoFileUrl.value, // 업로드된 이미지 URL을 저장
        };

        await api.$post('/enter/updateEnterInfo', data);
        alert('기업 정보가 성공적으로 업데이트되었습니다.');
    } catch (error) {
        console.error('기업 정보 수정 중 오류가 발생했습니다:', error);
        alert('기업 정보 수정에 실패했습니다.');
    }
};

// 이미지 업로드 함수
const uploadImg = async (e) => {
    const file = e.target.files[0];

    if (file) {
        let url = URL.createObjectURL(file);
        imgUrl.value = url;
        img.value = 1;

        const formData = new FormData();
        formData.append("file", file);

        try {
            const res = await api.$post('/file/upload-image', formData, {
                headers: {
                    "Content-Type": "multipart/form-data",
                }
            });
            console.log(res);
            imgUrl.value = res.fileUrl;
            logoFileUrl.value = res.fileUrl; 
        } catch (error) {
            console.error("이미지 업로드 실패", error);
            alert("이미지 업로드에 실패했습니다.");
        }
    } else {
        console.error('파일이 선택되지 않았습니다.');
        img.value = 0;
    }
};
</script>
