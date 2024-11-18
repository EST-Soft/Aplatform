<template>
    <div class="col-lg-3" v-if="!isNavVisible">
        <aside class="sidebar">
            <h5 class="font-weight-extra-bold pt-2">마이페이지</h5>
            <ul class="nav nav-list flex-column mb-5 pt-2">
                <li class="nav-item"><router-link class="nav-link" to="/mypage/home" id="menu1">현황 및 일정</router-link></li>
                <li class="nav-item"><router-link class="nav-link" to="/resumes/resume-insert" id="menu2">이력서 관리</router-link>
                    <ul>
                        <li class="nav-item"><router-link class="nav-link" to="/resumes/resume-insert">이력서 등록</router-link></li>
                        <li class="nav-item"><router-link class="nav-link" to="/resumes/resume-list">이력서 목록</router-link></li>
                    </ul>
                </li>
                <li class="nav-item"><router-link class="nav-link" to="/mypage/applycompanylist" id="menu3">입사지원현황</router-link></li>
                <li class="nav-item"><router-link class="nav-link" to="/mypage/JobViewList" id="menu4">최근 본 공고</router-link></li>
                <!-- 포지션 숨김 처리 -->
<!--                <li class="nav-item"><router-link class="nav-link" to="/mypage/ppAcception" id="menu4">포지션 제안</router-link>-->
<!--                    <ul>-->
<!--                        <li class="nav-item"><router-link class="nav-link" to="/mypage/ppAcception">포지션 제안 설정</router-link></li>-->
<!--                        <li class="nav-item"><router-link class="nav-link" to="/mypage/proposedPosition">제안 받은 포지션</router-link></li>-->
<!--                    </ul>-->
<!--                </li>-->
                <li class="nav-item"><router-link class="nav-link" to="/mypage/scrapList" id="menu5">스크랩 공고</router-link></li>
                <li class="nav-item"><router-link class="nav-link"  :to="{ name: 'userInfmtn', query: { modal: 'true' } }" id="menu6">회원정보 관리</router-link>
                    <ul>
                        <li class="nav-item"><router-link class="nav-link" :to="{ name: 'userInfmtn', query: { modal: 'true' } }">회원정보 수정</router-link></li>
                        <li class="nav-item"><router-link class="nav-link" to="/mypage/userPwChange">비밀번호 변경</router-link></li>
                        <li class="nav-item"><router-link class="nav-link" to="/mypage/userSingout">회원탈퇴</router-link></li>
                    </ul>
                </li>
            </ul>
        </aside>
    </div>

    <div class="col-lg-3" v-if="isNavVisible">
        <button class="btn btn-arrow-effect-1 bg-transparent text-primary border-0 p-0 me-3" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasScrolling" aria-controls="offcanvasScrolling" style="margin-bottom: 30px;">MENU<i class="fas fa-arrow-right ms-2"></i></button>

        <div class="offcanvas offcanvas-start" data-bs-scroll="true" data-bs-backdrop="false" tabindex="-1" id="offcanvasScrolling" aria-labelledby="offcanvasScrollingLabel" style="width: 50%;">
            <div class="offcanvas-header">
                <h5 class="offcanvas-title" id="offcanvasScrollingLabel">마이페이지</h5>
                <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
            </div>
            <div class="offcanvas-body">
                <aside class="sidebar" style="margin-bottom: 80px;">
                    <ul class="nav nav-list flex-column mb-5">
                        <li class="nav-item" data-bs-toggle="offcanvas" data-bs-target="#offcanvasScrolling" aria-controls="offcanvasScrolling"><router-link class="nav-link" to="/mypage/home" id="menu1">현황 및 일정</router-link></li>
                        <li class="nav-item" data-bs-toggle="offcanvas" data-bs-target="#offcanvasScrolling" aria-controls="offcanvasScrolling"><router-link class="nav-link" to="/resumes/resume-insert" id="menu2">이력서 관리</router-link>
                            <ul>
                                <li class="nav-item" data-bs-toggle="offcanvas" data-bs-target="#offcanvasScrolling" aria-controls="offcanvasScrolling"><router-link class="nav-link" to="/resumes/resume-insert">이력서 등록</router-link></li>
                                <li class="nav-item" data-bs-toggle="offcanvas" data-bs-target="#offcanvasScrolling" aria-controls="offcanvasScrolling"><router-link class="nav-link" to="/resumes/resume-list">이력서 목록</router-link></li>
                            </ul>
                        </li>
                        <li class="nav-item" data-bs-toggle="offcanvas" data-bs-target="#offcanvasScrolling" aria-controls="offcanvasScrolling"><router-link class="nav-link" to="/mypage/applycompanylist" id="menu3">입사지원현황</router-link></li>
                        <li class="nav-item" data-bs-toggle="offcanvas" data-bs-target="#offcanvasScrolling" aria-controls="offcanvasScrolling"><router-link class="nav-link" to="/mypage/ppAcception" id="menu4">포지션 제안</router-link>
                            <ul>
                                <li class="nav-item" data-bs-toggle="offcanvas" data-bs-target="#offcanvasScrolling" aria-controls="offcanvasScrolling"><router-link class="nav-link" to="/mypage/ppAcception">포지션 제안 설정</router-link></li>
                                <li class="nav-item" data-bs-toggle="offcanvas" data-bs-target="#offcanvasScrolling" aria-controls="offcanvasScrolling"><router-link class="nav-link" to="/mypage/proposedPosition">제안 받은 포지션</router-link></li>
                            </ul>
                        </li>
                        <li class="nav-item" data-bs-toggle="offcanvas" data-bs-target="#offcanvasScrolling" aria-controls="offcanvasScrolling"><router-link class="nav-link" to="/mypage/scrapList" id="menu5">스크랩 공고</router-link></li>
                        <li class="nav-item" data-bs-toggle="offcanvas" data-bs-target="#offcanvasScrolling" aria-controls="offcanvasScrolling"><router-link class="nav-link"  :to="{ name: 'userInfmtn', query: { modal: 'true' } }" id="menu6">회원정보 관리</router-link>
                            <ul>
                                <li class="nav-item" data-bs-toggle="offcanvas" data-bs-target="#offcanvasScrolling" aria-controls="offcanvasScrolling"><router-link class="nav-link" :to="{ name: 'userInfmtn', query: { modal: 'true' } }">회원정보 수정</router-link></li>
                                <li class="nav-item" data-bs-toggle="offcanvas" data-bs-target="#offcanvasScrolling" aria-controls="offcanvasScrolling"><router-link class="nav-link" to="/mypage/userPwChange">비밀번호 변경</router-link></li>
                                <li class="nav-item" data-bs-toggle="offcanvas" data-bs-target="#offcanvasScrolling" aria-controls="offcanvasScrolling"><router-link class="nav-link" to="/mypage/userSingout">회원탈퇴</router-link></li>
                            </ul>
                        </li>
                    </ul>
                </aside>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue';

const isNavVisible = ref(window.innerWidth <= 991);

const handleResize = () => {
  isNavVisible.value = window.innerWidth <= 991;
};

onMounted(() => {
  window.addEventListener('resize', handleResize);
});

onUnmounted(() => {
  window.removeEventListener('resize', handleResize);
});
</script>

<style scoped>
    #menu1, #menu2, #menu3, #menu4, #menu5, #menu6{
        font-weight: bold;
        font-size: 100%;
        padding-left: 25px;
    }
    #menu1::before, #menu2::before, #menu3::before, #menu4::before, #menu5::before, #menu6::before {
        content: none; /* ::before 가상 요소의 내용을 제거 */
    }
    .font-weight-semi-bold.pt-4{
        padding: 0.5rem 0;
    }
    /* 네비게이션 링크 기본 스타일 */
    .sidebar {
        position: sticky;
        top: 130px;
    }
</style>