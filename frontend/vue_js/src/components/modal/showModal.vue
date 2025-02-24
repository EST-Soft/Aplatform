<template>
  <BModal
    v-model="isVisible"
    :title="modalType === 'confirm' ? '확인' : '알림'"
    centered
  >
    <div class="d-block text-center">
      <p>{{ content }}</p>
    </div>
    <template #footer>
      <BButton
        v-if="modalType === 'confirm'"
        variant="secondary"
        @click="cancel"
      >
        취소
      </BButton>
      <BButton variant="primary" @click="confirmOrClose">
        {{ modalType === "confirm" ? "확인" : "닫기" }}
      </BButton>
    </template>
  </BModal>
</template>

<script>
import { mapState, mapActions } from "vuex";
import { BModal, BButton } from "bootstrap-vue-3";

export default {
  name: "ModalComponent",
  components: {
    BModal,
    BButton,
  },
  computed: {
    ...mapState("showModal", [
      "modalContent",
      "modalType",
      "onConfirm",
      "onCancel",
    ]),
    isVisible: {
      get() {
        return !!this.modalContent;
      },
      set(value) {
        if (!value) {
          this.closeModal();
        }
      },
    },
    content() {
      return this.modalContent;
    },
  },
  methods: {
    ...mapActions("showModal", ["closeModal", "confirmModal", "cancelModal"]),
    confirmOrClose() {
      if (this.modalType === "confirm") {
        this.confirm();
      } else {
        this.closeModal();
      }
    },
    confirm() {
      this.confirmModal();
    },
    cancel() {
      this.cancelModal();
    },
  },
};
</script>
