<template>
  <div v-if="isVisible" class="modal-overlay" @click.self="close">
    <div class="modal-content">
      <div class="file-upload">
        <div class="file-list mt-3">
          <h5>업로드된 파일 목록:</h5>
          <div style="min-height: 150px;">
            <ul>
              <li v-for="(file, index) in fileDatas" :key="index">
                {{ file.name }}
                <button class="btn btn-danger btn-sm ms-2" @click="removeFile(index)">삭제</button>
              </li>
            </ul>
          </div>
        </div>
        <input id="addFile" ref="fileInput" type="file" multiple @change="handleFileChange" style="display: none;" />
        <div class="button-group">
          <button class="btn btn-secondary mt-3" @click="triggerFileInput">첨부</button>
          <button class="btn btn-secondary mt-3" @click="close">닫기</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, defineProps, defineEmits, watch } from "vue";

const fileDatas = ref([]);
const fileInput = ref(null);

const props = defineProps({
  isVisible: {
    type: Boolean,
    required: true
  },
  attachmentDatas: {
    type: Array,
    required: true
  }
});

console.log(props.isVisible)

const emit = defineEmits(['update:isVisible', 'update:attachmentDatas']);

const handleFileChange = (event) => {
  fileDatas.value = Array.from(event.target.files);
  console.log(fileDatas.value)
};

const removeFile = (index) => {
  fileDatas.value.splice(index, 1);
};

const triggerFileInput = () => {
  event.preventDefault();
  fileInput.value.click();
};

/* const clearFiles = () => {
  b.value = [];
}; */

const close = () => {
  emit('update:isVisible', false);
};

watch(fileDatas, (newFiles) => {
  emit('update:attachmentDatas', newFiles);
});

</script>

<style scoped>
.modal-overlay {
  position: fixed;
  z-index: 10000;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-content {
  background: #fff;
  padding: 20px;
  border-radius: 8px;
  width: 90%;
  max-width: 500px;
}

.file-upload {
  margin-bottom: 20px;
}

.file-list ul {
  list-style-type: none;
  padding: 0;
}

.file-list li {
  margin-bottom: 5px;
}

.btn-danger {
  cursor: pointer;
}

.btn-secondary {
  cursor: pointer;
}

.button-group {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}
</style>
