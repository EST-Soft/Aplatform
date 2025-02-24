<template>
  <div class="col-lg-4">
    <ul class="pagination justify-content-center">
      <!-- 이전 페이지 그룹 버튼 -->
      <li class="page-item" 
          v-if="props.paginationData.startNumOfPageGroups > 1"
          @click="$emit('changePageNo', props.paginationData.startNumOfPageGroups - 1)">
        <a href="#" class="page-link">
          <i class="fas fa-angle-left"></i>
        </a>
      </li>

      <!-- 페이지 번호 표시 -->
      <li class="page-item"
          v-for="index in pageGroupCount"
          :key="props.paginationData.startNumOfPageGroups + index - 1"
          :class="{ active: props.paginationData.startNumOfPageGroups + index - 1 === props.paginationData.pageNo }"
          @click="$emit('changePageNo', props.paginationData.startNumOfPageGroups + index - 1)">
        <a href="#" class="page-link">{{ props.paginationData.startNumOfPageGroups + index - 1 }}</a>
      </li>

      <!-- 다음 페이지 그룹 버튼 -->
      <li class="page-item" 
          v-if="props.paginationData.endNumOfPageGroups < props.paginationData.totalPageCount"
          @click="$emit('changePageNo', props.paginationData.endNumOfPageGroups + 1)">
        <a href="#" class="page-link">
          <i class="fas fa-angle-right"></i>
        </a>
      </li>
    </ul>
  </div>
</template>

<script setup>
import { defineProps, computed } from "vue";

const props = defineProps({
  paginationData: {
    type: Object,
    required: true, // props가 필수로 들어와야 함을 명시
  },
});

// 현재 페이지 그룹의 시작 번호
const pageGroupStart = computed(() => props.paginationData.startNumOfPageGroups);

// 페이지 그룹에 따라 보여줄 페이지 수를 계산
const pageGroupCount = computed(() => {
  const totalPages = props.paginationData.totalPageCount;
  const startPage = pageGroupStart.value;
  const endPage = Math.min(startPage + 4, totalPages); // 5개까지 보여주기
  return endPage - startPage + 1; // 보여줄 페이지 수
});
</script>

<style scoped></style>
