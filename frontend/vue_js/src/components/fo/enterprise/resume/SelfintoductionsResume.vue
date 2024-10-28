<template>
  <div>
    <section class="call-to-action with-full-borders mb-2">
      <div class="col-lg-12">
        <div class="form-group col">
          <div class="col-lg-5 form-check-inline">
            <input type="text" maxlength="50" class="form-control text-3 h-auto py-2" placeholder="제목"
              v-model="localData.siTtl" @input="emitUpdate" required />
          </div>
          <div class="col-lg-1 form-check-inline">
            <div class="btn btn-primary btn-circle mb-2 imgIcon" @click="handleRemove">
              <i class="fa fa-minus"></i>
            </div>
          </div>
        </div>
      </div>
      <div class="col-lg-12">
        <div class="form-group col">
          <textarea maxlength="5000" rows="8" class="form-control text-3 h-auto py-2" placeholder="내용"
            v-model="localData.siCntnt" @input="emitUpdate" required></textarea>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
import { ref, defineProps, defineEmits, watch } from "vue";

// props
const props = defineProps({
  data: Object,
  index: Number,
});

// emit
const emit = defineEmits(['update', 'remove']);

const localData = ref({ ...props.data });

// 데이터 변경
const emitUpdate = () => {
  emit('update', props.index, localData.value);
};

const handleRemove = () => {
  emit('remove', props.index);
};

watch(() => props.data, (newValue) => {
  localData.value = { ...newValue };
});

</script>

<style scoped></style>