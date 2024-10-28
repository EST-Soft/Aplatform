import store from "@/store";

export function showAlert(message) {
  store.dispatch("showModal/showModal", { content: message, type: "alert" });
}

export function showConfirm(message, onConfirm, onCancel) {
  store.dispatch("showModal/showModal", {
    content: message,
    type: "confirm",
    onConfirm: onConfirm,
    onCancel: onCancel,
  });
}

export function showEditor(message, onConfirm, onCancel) {
  store.dispatch("showModal/showModal", {
    content: { message, editorContent: '' },
    type: "editor",
    onConfirm: onConfirm,
    onCancel: onCancel,
  });
}
