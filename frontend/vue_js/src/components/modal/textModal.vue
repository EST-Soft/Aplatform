<template>
  <div v-if="isVisible" class="modal">
    <div class="modal-content">
      <span @click="closeModal" class="close-button">&times;</span>
      <div v-if="modalType === 'alert'">
        <p>{{ modalContent }}</p>
        <button @click="confirmModal">OK</button>
      </div>
      <div v-if="modalType === 'confirm'">
        <p>{{ modalContent }}</p>
        <button @click="confirmModal">Yes</button>
        <button @click="cancelModal">No</button>
      </div>
      <div v-if="modalType === 'editor'">
        <p>{{ modalContent.message }}</p>
        <!-- Use the correct import for QuillEditor -->
        <quill-editor v-model="editorContent" />
        <button @click="confirmModal">Save</button>
        <button @click="cancelModal">Cancel</button>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex';
import { quillEditor } from 'vue3-quill'; // Use 'quillEditor' instead of 'QuillEditor'

export default {
  components: {
    quillEditor, // Register it as quillEditor
  },
  computed: {
    ...mapState('showModal', ['modalContent', 'modalType']),
    isVisible() {
      return this.modalContent !== null;
    },
    editorContent: {
      get() {
        console.log('Editor Content (get):', this.modalContent?.editorContent);
        return this.modalContent?.editorContent || '';
      },
      set(value) {
        console.log('Editor Content (set):', value);
        if (this.modalContent) {
          this.modalContent.editorContent = value;
        }
      },
    },
  },
  methods: {
    ...mapActions('showModal', ['closeModal', 'confirmModal', 'cancelModal']),
  },
};
</script>

<style scoped>
/* Add your modal styles here */
</style>
