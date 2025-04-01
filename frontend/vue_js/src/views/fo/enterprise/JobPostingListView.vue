<template>
  <div>
    <section class="page-header page-header-modern bg-color-grey page-header-md">
      <div class="container">
        <div class="row">
          <div class="col-md-12 align-self-center p-static order-2 text-center">
            <h1 class="text-dark font-weight-bold text-8">채용 정보 목록</h1>
          </div>
          <div class="col-md-12 align-self-center order-1">
            <ul class="breadcrumb d-block text-center">
              <li><a href="/">Home</a></li>
              <li class="active" v-show="activeFilter === 'organization'">소속 채용 정보 목록</li>
              <li class="active" v-show="activeFilter === 'project'">프로젝트 채용 정보 목록</li>
            </ul>
          </div>
        </div>
      </div>
    </section>

    <section class="card card-admin">
      <div class="container py-4">
        <!-- 소속/프로젝트 버튼 -->

        <div class="btn-group" role="group" aria-label="Basic radio toggle button group">
          <input type="radio" class="btn-check" name="btnradio" id="btnradio1" autocomplete="off" checked
            @click="setFilter('organization')">
          <label class="btn btn-outline-primary" for="btnradio1">소속 채용</label>
          <input type="radio" class="btn-check" name="btnradio" id="btnradio2" autocomplete="off"
            @click="setFilter('project')">
          <label class="btn btn-outline-primary" for="btnradio2">프로젝트 채용</label>
        </div>

        <OrganizationRecruitment v-if="activeFilter === 'organization'" />

        <ProjectRecruitment v-if="activeFilter === 'project'" />

      </div>
    </section>
  </div>
</template>

<script setup>
// import BasePagination from "@/components/common/BasePagination.vue";
// import SearchComponent from '@/components/fo/enterprise/SearchComponent.vue';
import OrganizationRecruitment from './OrganizationRecruitment.vue';
import ProjectRecruitment from './ProjectRecruitment.vue';
import { ref } from 'vue';

const activeFilter = ref('organization');

function setFilter(filter) {
  // console.log('필터버튼클릭');
  this.activeFilter = filter;
  // console.log('현재 필터는?', this.activeFilter);
}

</script>

<style scoped>
.list-container {
  margin-bottom: 60px;
  /* 페이지네이션 영역과의 여백을 추가 */
  position: relative;
  /* 부모 요소에 상대적인 위치 설정 */
}

.list-header {
  display: flex;
}

.list-header>div {
  margin-left: 50px;
}

.list-body {
  margin-top: 50px;
}

.custom {
  background-color: #ffffff;
  border-radius: 0.5rem;
  padding: 1rem;
  width: 100%;
  border: 1px solid #002C7B;
  text-align: left;
  position: relative;
  height: 350px;
}

.custom .profile-image {
  width: 230px;
  height: 180px;
  overflow: hidden;
  display: flex;
  justify-content: center;
  align-items: center;
}

.custom .profile-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  /* 이미지가 영역을 꽉 채우도록 설정 */
}

/* flexbox로 아이템들을 4개씩 나누기 */
.row {
  display: flex;
  flex-wrap: wrap;
  gap: 15px;
  /* 아이템 사이의 간격 */
}

.col-md-3 {
  flex: 1 1 22%;
  /* 한 줄에 4개의 아이템 배치 */
  max-width: 22%;
}

/* 화면 크기가 작아지면 한 줄에 하나만 표시되도록 설정 */
/* 모바일 환경에서 mouse event를 클릭으로 바꾸고, overlay 숨기기 */
@media (max-width: 768px) {
  .custom {
    flex-direction: column;
    /* 세로로 배치 */
    align-items: center;
    /* 중앙 정렬 */
    text-align: center;
    /* 텍스트 중앙 정렬 */
    height: 300px;
  }

  .custom .profile-image {
    margin-right: 0;
    margin-bottom: 10px;
  }

  .col-md-3 {
    flex: 1 1 100%;
    /* 한 줄에 하나만 표시 */
    max-width: 100%;
  }


  /* 클릭 시 overlay 나타나게 하기 */
  .custom.clicked .detail-overlay {
    display: block;
    /* overlay를 보이도록 설정 */
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    /* custom 영역의 크기에 맞게 overlay를 덮도록 */
    background-color: rgba(0, 0, 0, 0.5);
    /* 반투명 배경 */
    z-index: 10;
    /* overlay가 custom 영역 위로 올라오도록 */
  }

  /* 모바일에서는 hover를 막고, 클릭으로 처리 */
  .custom {
    pointer-events: auto;
    /* 마우스 오버를 클릭으로 변환 */
  }
}

/* desktop에서는 hover 상태로 overlay 활성화 */
@media (min-width: 769px) {
  .custom:hover .detail-overlay {
    display: block;
  }
}

.search-options {
  margin-top: 20px;
  display: flex;
  align-items: center;
}

.search-options button {
  padding: 8px 16px;
  font-size: 16px;
  background-color: #3498db;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  margin-right: 10px;
}

.search-options button:hover {
  background-color: #2980b9;
}

.dropdown-wrapper {
  position: relative;
  /* 부모 요소에 대해 상대 위치 */
}

.dropdown {
  position: absolute;
  top: 100%;
  /* 버튼 바로 아래에 위치 */
  left: 0;
  background-color: #f9f9f9;
  min-width: 160px;
  box-shadow: 0px 8px 16px rgba(0, 0, 0, 0.2);
  z-index: 1;
  margin-top: 5px;
  max-height: 200px;
  /* 최대 높이 설정 */
  overflow-y: auto;
}

.dropdown label {
  display: block;
  padding: 10px;
}

.dropdown label:hover {
  background-color: #f1f1f1;
}

.pagenation-wrapper {
  position: absolute;
  width: 100%;
  z-index: 1000;
  bottom: -50px;
  /* 페이지네이션 위치 조정 */
}

.container {
  position: relative;
  margin-bottom: 50px;
}

.router-link {
  display: inline-block;
  padding: 10px 20px;
  font-size: 16px;
  text-decoration: none;
  box-sizing: border-box;
  transition: all 0.3s ease;
}

.router-link:hover {
  background-color: #ddd;
}

.details-overlay {
  position: absolute;
  inset: 0;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background-color: rgba(0, 0, 0, 0.7);
  color: white;
  font-size: 12px;
  padding: 10px;
  border-radius: 5px;
  text-align: center;
}

.profile-image-container {
  position: relative;
  display: inline-block;
  transition: 0.3s ease;
}

.profile-image {
  width: 80px;
  height: 80px;
  border-radius: 50%;
  overflow: hidden;
  margin-bottom: 10px;
}

.profile-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: opacity 0.3s ease;
}

.profile-image-container:hover .profile-image img {
  opacity: 0.6;
  /* 마우스 오버 시 어두워짐 */
}

.d-day {
  position: absolute;
  top: 5px;
  right: 5px;
  background-color: rgba(0, 0, 0, 0.7);
  color: white;
  font-size: 12px;
  padding: 5px;
  border-radius: 5px;
}

.small-image,
.small-heart-image {
  width: 48px;
  height: 48px;
}
</style>
