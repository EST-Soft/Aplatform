<template>
  <div class="col-lg-4">
    <ul class="pagination justify-content-center">
      <li class="page-item" v-if="props.paginationData.startNumOfPageGroups > props.paginationData.showPageGroupsCount"
        :value="props.paginationData.endNumOfPageGroups - props.paginationData.showPageGroupsCount"
        @click="$emit('changePageNo', props.paginationData.endNumOfPageGroups - props.paginationData.showPageGroupsCount)">
        <a href="#" class="page-link">
          <i class="fas fa-angle-left"></i>
        </a>
      </li>
      <li class="page-item"
        :class="[props.paginationData.startNumOfPageGroups + index == props.paginationData.pageGroupsOfCurrentPage ? 'active' : '']"
        v-for="(page, index) in props.paginationData.showPageGroupsCount"
        :key="props.paginationData.startNumOfPageGroups + index" :value="props.paginationData.startNumOfPageGroups + index"
        @click="$emit('changePageNo', props.paginationData.startNumOfPageGroups + index )">
        <a href="#" class="page-link">{{ props.paginationData.startNumOfPageGroups + index }}</a>
      </li>
      <li class="page-item"
        v-if="props.paginationData.endNumOfPageGroups < props.paginationData.totalPageGroupsCount"
        :value="props.paginationData.startNumOfPageGroups + props.paginationData.showPageGroupsCoun"
        @click="$emit('changePageNo', props.paginationData.startNumOfPageGroups + props.paginationData.showPageGroupsCount)">
        <a href="#" class="page-link">
          <i class="fas fa-angle-right"></i>
        </a>
      </li>
    </ul>
  </div>
</template>

<script setup>
import { defineProps } from "vue";

const props = defineProps(["paginationData"]);
</script>

<style scoped></style>
