<template>
  <div class="col-lg-4">
    <h4 class="mb-0">
      첨부파일 &nbsp;
      <a href="#" class="btn btn-primary btn-circle mb-2" @click="addComponents()">
        <i class="fa fa-plus"></i>
      </a>
    </h4>
    <hr class="mt-1 mb-2" />
    <div>
      <div v-for="(attachmentsData, index) in attachmentsDatas" :key="index">
        <!-- <AttachmentsDatas :id="attachmentsData.id" @remove-components="removeComponents" /> -->
        <div>
          <a class="btn btn-outline btn-rounded btn-light" @click="removeComponents(attachmentsData.id)"> 컴포먼트 </a>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
// import AttachmentsDatas from "./AttachmentsDatas.vue";

const attachmentsDatas = ref([]);
let count = 1;

// 입력창 생성
const addComponents = () => {
  attachmentsDatas.value.push({ id: count });
  count++;
};
// 입력창 생성
const removeComponents = (event) => {
  let index = 0;

  attachmentsDatas.value.forEach((attachmentsData, dataIndex) => {
    if (attachmentsData.id == event) {
      index = dataIndex;
      return;
    }
  });

  attachmentsDatas.value.splice(index, 1);
};
</script>

<style scoped></style>
