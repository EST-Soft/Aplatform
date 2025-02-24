<template>
  <div class="col pb-3 pt-1">
    <h4 class="mb-0">
      자기소개서 &nbsp;
      <a href="#" class="btn btn-primary btn-circle mb-2" @click="addComponents()">
        <i class="fa fa-plus"></i>
      </a>
    </h4>
    <hr class="mt-1 mb-2" />
    <div>
      <div v-for="(selfintoductionData, index) in selfintoductionDatas" :key="index">
        <section class="call-to-action with-full-borders mb-2">
          <div class="col-lg-12">
            <div class="form-group col">
              <div class="col-lg-5 form-check-inline">
                <input type="text" maxlength="50" class="form-control text-3 h-auto py-2" name="title" placeholder="제목"
                  v-model="selfintoductionData.title" required />
              </div>
              <div class="col-lg-1 form-check-inline">
                <a class="btn btn-primary btn-circle mb-2 imgIcon" @click="removeComponents(selfintoductionData.id)">
                  <i class="fa fa-minus"></i>
                </a>
              </div>
            </div>
          </div>
          <div class="col-lg-12">
            <div class="form-group col">
              <textarea maxlength="5000" rows="8" class="form-control text-3 h-auto py-2" name="content"
                placeholder="내용" v-model="selfintoductionData.content" required></textarea>
            </div>
          </div>
        </section>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";

const selfintoductionDatas = ref([]);
let count = 1;

// 입력창 생성
const addComponents = () => {
  selfintoductionDatas.value.push({ id: count, title: "", content: "" });

  count++;
  console.log(selfintoductionDatas.value);
};

// 입력창 삭제
const removeComponents = (event) => {
  let index = -1;

  selfintoductionDatas.value.forEach((selfintoductionData, dataIndex) => {
    if (selfintoductionData.id == event) {
      index = dataIndex;
      return;
    }
  });

  selfintoductionDatas.value.splice(index, 1);
};
</script>

<style scoped></style>
