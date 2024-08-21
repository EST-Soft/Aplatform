<template>
  <div class="col-lg-3">
    <aside class="sidebar" style="position: fixed">
      <h5 class="font-weight-semi-bold pt-4">기업정보 관리</h5>
      <ul class="nav nav-list flex-column mb-5">
        <li class="nav-item">
          <router-link class="nav-link" to="/enter/mypage/infoUpdate"
            >기업정보 수정</router-link
          >
        </li>
        <li class="nav-item">
          <router-link class="nav-link" to="/enter/mypage/chagePassword"
            >비밀번호 변경</router-link
          >
        </li>
        <li class="nav-item">
          <router-link class="nav-link" to="/enter/mypage/signOut"
            >회원탈퇴</router-link
          >
        </li>
      </ul>
    </aside>
  </div>
</template>
