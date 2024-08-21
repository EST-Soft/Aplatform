const state = {
  modalContent: null,
  modalType: "alert", // 기본값: 'alert', 다른 옵션: 'confirm'
  onConfirm: null,
  onCancel: null,
};

const mutations = {
  setModalContent(state, { content, type, onConfirm, onCancel }) {
    state.modalContent = content;
    state.modalType = type || "alert";
    state.onConfirm = onConfirm || null;
    state.onCancel = onCancel || null;
  },
  clearModalContent(state) {
    state.modalContent = null;
    state.modalType = "alert";
    state.onConfirm = null;
    state.onCancel = null;
  },
};

const actions = {
  showModal({ commit }, { content, type, onConfirm, onCancel }) {
    commit("setModalContent", { content, type, onConfirm, onCancel });
  },
  closeModal({ commit }) {
    commit("clearModalContent");
  },
  confirmModal({ commit, state }) {
    if (state.onConfirm) state.onConfirm();
    commit("clearModalContent");
  },
  cancelModal({ commit, state }) {
    if (state.onCancel) state.onCancel();
    commit("clearModalContent");
  },
};

export default {
  namespaced: true,
  state,
  mutations,
  actions,
};
