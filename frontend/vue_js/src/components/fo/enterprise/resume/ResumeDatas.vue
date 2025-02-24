<template>
  <section class="call-to-action with-full-borders mb-2">
    <div class="row col-sm-12 col-lg-12">
      <div class="col-sm-9 col-lg-9">
        <div class="call-to-action-content">
          <h3>
            <!-- <router-link class="font-weight-extra-bold" :to="dtlInfo">{{ props.resumeData.rsm_tl }}</router-link> -->
            <a class="font-weight-extra-bold" @click="toDtlPage()">{{ props.resumeData.rsm_ttl }}</a>
          </h3>
        </div>
      </div>
      <div class="col-sm-3 col-lg-3">
        <div class="call-to-action-btn">
          <a v-if="props.resumeData.rsm_rprsntv_yn === 'Y'" href="#"
            class="btn btn-rounded btn-quaternary mb-2">대표이력서</a>
          <a v-if="props.resumeData.rsm_rprsntv_yn === 'N'" href="#"
            class="btn btn-outline btn-rounded btn-quaternary mb-2"
            @click="$emit('modifyRepresentative', props.resumeData.rsm_sq)">대표이력서 설정</a>
        </div>
      </div>
    </div>
    <div class="row col-sm-12 col-lg-12">
      <div class="col-sm-4 col-lg-4">
        <p v-if="!props.resumeData.updt_dtm || props.resumeData.updt_dtm.length === 0" class="mb-0">{{ fomatDate(props.resumeData.insrt_dtm) }}</p>
        <p v-else class="mb-0">{{ fomatDate(props.resumeData.updt_dtm) }}</p>
      </div>
      <div class="col-sm-5 col-lg-5"></div>
      <div class="col-sm-1 col-lg-1">
        <div class="call-to-action-btn">
          <a class="btn btn-outline btn-rounded btn-primary mb-2"
            @click="$emit('copyResumes', props.resumeData.rsm_sq)">복제</a>
        </div>
      </div>
      <div class="col-sm-1 col-lg-1">
        <div class="call-to-action-btn">
          <a href="#" class="btn btn-outline btn-rounded btn-tertiary mb-2"
            @click="$emit('modifyResumes', props.resumeData.rsm_sq)">수정</a>
        </div>
      </div>
      <div class="col-sm-1 col-lg-1">
        <div class="call-to-action-btn">
          <a href="#" class="btn btn-outline btn-rounded btn-secondary mb-2"
            @click="$emit('deleteResumes', props.resumeData.rsm_sq)">삭제</a>
        </div>
      </div>
    </div>
  </section>

</template>

<script setup>
import { defineProps } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();
const props = defineProps(["resumeData"]);

const dtlInfo = {
  name :'resumeDetailView',
  params : {
    resSq : props.resumeData.rsm_sq
  }
}

const toDtlPage = () => {
  
  router.push(dtlInfo);
};

// 포멧 함수
// 날짜 포멧 변경
const fomatDate = (dateTime) => {
  const returnData = new Date(dateTime).toLocaleDateString();

  return returnData;
};


</script>

<style scoped></style>