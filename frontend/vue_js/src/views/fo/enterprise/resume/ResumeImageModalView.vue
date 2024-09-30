<template>
  <div v-if="props.isVisible" class="modal-overlay" @click.self="close">
    <div class="modal-content">
      <span class="close-button" @click="close">&times;</span>
      <div v-if="props.imageSrc == ''">
        <img src="@/assets/avatar.jpg" alt="Modal Image" class="modal-image" @click="triggerFileInput" />
      </div>
      <div v-else>
        <img :src="props.imageSrc" alt="Modal Image" class="modal-image" @click="triggerFileInput" />
      </div>
      <!-- Hidden file input -->
      <input ref="fileInput" type="file" style="display: none;" @change="handleFileChange" />
    </div>
  </div>
</template>

<script setup>
import { ref, defineProps, defineEmits } from 'vue';

const props = defineProps({
  isVisible: {
    type: Boolean,
    required: true
  },
  imageSrc: {
    type: String,
    required: true
  }
});

const emit = defineEmits(['update:isVisible', 'update:imageSrc', 'change-image']);

const fileInput = ref(null);

const close = () => {
  emit('update:isVisible', false);
};

const triggerFileInput = () => {
  fileInput.value.click();
};

const handleFileChange = async (event) => {
  const file = event.target.files[0];
  if (file) {
    emit('change-image', file);
    close();
  }
};
</script>

<style scoped>
.modal-overlay {
  z-index: 100;
  position: fixed;
  top: 50%;
  left: 50%;
  width: 40%;
  height: 40%;
  transform: translate(-50%, -50%);
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
}

.modal-content {
  background: white;
  padding: 20px;
  border-radius: 8px;
  position: relative;
}

.close-button {
  position: absolute;
  top: 10px;
  right: 10px;
  cursor: pointer;
  font-size: 24px;
}

.modal-image {
  max-width: 100%;
  max-height: 100vh;
  cursor: pointer;
}
</style>