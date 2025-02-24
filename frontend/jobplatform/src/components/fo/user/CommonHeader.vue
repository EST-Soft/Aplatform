<template>
    <header id="header" class="header-effect-shrink" data-plugin-options="{'stickyEnabled': true, 'stickyEffect': 'shrink', 'stickyEnableOnBoxed': true, 'stickyEnableOnMobile': false, 'stickyChangeLogo': true, 'stickyStartAt': 120, 'stickyHeaderContainerHeight': 70}" style="height: 110px;">
        <div class="header-body border-top-0" style="position: fixed;">
            <div class="header-container container" style="height: 100px;">
                <div class="header-row">
                    <div class="header-column">
                        <div class="header-row">
                            <div class="header-logo" style="width: 100px; height: 48px;">
                                <router-link to="/">
                                    <img alt="Porto" width="100" height="48" data-sticky-width="82" data-sticky-height="40" src="/img/jobs-icon.png">
                                </router-link>
                            </div>
                            <div class="ms-3" @click="changeUserType('user')" style="cursor: pointer;">일반회원</div>
                            <div class="ms-3" @click="changeUserType('enter')" style="cursor: pointer;">기업회원</div>
                        </div>
                    </div>
                    <div class="header-column justify-content-end" v-show="userType == 'user'">
                        <div class="header-row">
                            <div class="header-nav header-nav-line header-nav-bottom-line header-nav-bottom-line-no-transform header-nav-bottom-line-active-text-dark header-nav-bottom-line-effect-1 order-2 order-lg-1">
                                <div class="header-nav-main header-nav-main-square header-nav-main-dropdown-no-borders header-nav-main-effect-2 header-nav-main-sub-effect-1">
                                    <nav class="collapse">
                                        <ul class="nav nav-pills" id="mainNav">
                                            <li class="dropdown">
                                                <router-link to="/" class="dropdown-item dropdown-toggle font-weight-bold">
                                                    HOME
                                                <i class="fas fa-chevron-down"></i></router-link>
                                            </li>
                                            <li class="dropdown">
                                                <router-link to="/board/list/jobPosting" class="dropdown-item dropdown-toggle font-weight-bold">
                                                    채용정보
                                                <i class="fas fa-chevron-down"></i></router-link>
                                            </li>
                                            <li class="dropdown">
                                                <a class="dropdown-item dropdown-toggle font-weight-bold" href="elements.html">
                                                    게시판
                                                <i class="fas fa-chevron-down"></i></a>
                                                <ul class="dropdown-menu">
                                                    <li>
                                                        <a class="dropdown-item" href="index.html">
                                                            일반게시판
                                                        </a>
                                                    </li>
                                                    <li>
                                                        <a class="dropdown-item" href="index.html">
                                                            피드백게시판
                                                        </a>
                                                    </li>
                                                    <li>
                                                        <a class="dropdown-item" href="index.html">
                                                            Q&A게시판
                                                        </a>
                                                    </li>
                                                </ul>
                                            </li>
                                            <li class="dropdown">
                                                <router-link to="/mypage/home" class="dropdown-item dropdown-toggle font-weight-bold ">
                                                    마이페이지
                                                <i class="fas fa-chevron-down"></i></router-link>
                                            </li>
                                        </ul>
                                    </nav>
                                </div>
                                <button class="btn header-btn-collapse-nav" data-bs-toggle="collapse" data-bs-target=".header-nav-main nav">
                                    <i class="fas fa-bars"></i>
                                </button>
                            </div>
                            <div class="header-nav-features header-nav-features-no-border header-nav-features-lg-show-border order-1 order-lg-2">
                                <div class="header-nav-feature header-nav-features-search d-inline-flex">
                                    <router-link class="nav-link" to="/login">LOGIN</router-link>
                                </div>
                                <div class="header-nav-feature header-nav-features-cart d-inline-flex ms-2">
                                    <span class="header-nav-features-toggle text-decoration-none me-3" data-focus="headerSearch" aria-label="Search" >홍길동님</span>
                                    <a href="#" class="header-nav-features-toggle" aria-label="">
                                        로그아웃
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="header-column justify-content-end" v-show="userType == 'enter'">
                        <div class="header-row">
                            <div class="header-nav header-nav-line header-nav-bottom-line header-nav-bottom-line-no-transform header-nav-bottom-line-active-text-dark header-nav-bottom-line-effect-1 order-2 order-lg-1">
                                <div class="header-nav-main header-nav-main-square header-nav-main-dropdown-no-borders header-nav-main-effect-2 header-nav-main-sub-effect-1">
                                    <nav class="collapse">
                                        <ul class="nav nav-pills" id="mainNav">
                                            <li class="dropdown">
                                                <router-link to="/enter" class="dropdown-item dropdown-toggle font-weight-bold">
                                                    HOME
                                                <i class="fas fa-chevron-down"></i></router-link>
                                            </li>
                                            <li class="dropdown">
                                                <router-link to="/board/list/jobPosting" class="dropdown-item dropdown-toggle font-weight-bold">
                                                    공고관리
                                                <i class="fas fa-chevron-down"></i></router-link>
                                            </li>
                                            <li class="dropdown">
                                                <router-link to="/enter/recommendResumeList" class="dropdown-item dropdown-toggle font-weight-bold">
                                                    인재리스트
                                                <i class="fas fa-chevron-down"></i></router-link>
                                            </li>
                                            <li class="dropdown">
                                                <router-link to="/enter/mypage" class="dropdown-item dropdown-toggle font-weight-bold ">
                                                    마이페이지
                                                <i class="fas fa-chevron-down"></i></router-link>
                                            </li>
                                        </ul>
                                    </nav>
                                </div>
                                <button class="btn header-btn-collapse-nav" data-bs-toggle="collapse" data-bs-target=".header-nav-main nav">
                                    <i class="fas fa-bars"></i>
                                </button>
                            </div>
                            <div class="header-nav-features header-nav-features-no-border header-nav-features-lg-show-border order-1 order-lg-2">
                                <div class="header-nav-feature header-nav-features-search d-inline-flex">
                                    <router-link class="nav-link" to="/enter/login">LOGIN</router-link>
                                </div>
                                <div class="header-nav-feature header-nav-features-cart d-inline-flex ms-2">
                                    <span class="header-nav-features-toggle text-decoration-none me-3" data-focus="headerSearch" aria-label="Search" >ESTsoft</span>
                                    <a href="#" class="header-nav-features-toggle" aria-label="">
                                        로그아웃
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </header>
</template>

<script setup>
    import { computed } from 'vue';
    import { useStore } from 'vuex';
    import { useRouter } from "vue-router";

    const store = useStore();
    const router = useRouter();

    let userType = computed(() => store.getters.getUserType);

    function changeUserType(userType){
        store.commit("changeUserType", userType);
        if(userType == 'enter'){
            router.push('/enter');
        }
        if(userType == 'user'){
            router.push('/');
        }
    }
</script>