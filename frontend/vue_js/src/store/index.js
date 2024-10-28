import { createStore } from "vuex";
import showModal from "./modules/showModal";

// store 생성
const store = createStore({
  modules: {
    showModal,
  },
  state: {
    userType: sessionStorage.getItem("userType") || "", // 세션 스토리지에서 userType을 가져오도록 수정
    member: JSON.parse(sessionStorage.getItem("member")) || null, // 사용자 정보 저장할 상태
    enterMember: [], // 배열 형태로 저장
  },
  mutations: {
    changeUserType(state, userType) {
      state.userType = userType;
      sessionStorage.setItem("userType", userType); // userType을 세션 스토리지에 저장
    },
    setEnter(state, payLoad) {
      state.enterMember = payLoad;
    },
    setMember(state, payLoad) {
      state.member = payLoad;
      sessionStorage.setItem("member", JSON.stringify(payLoad));
    },
    clearMember(state) {
      state.member = null;
      sessionStorage.removeItem("member"); // 세션 스토리지에서 'member' 삭제
    },
    updateMemberInfo(state, updatedInfo) {
      state.member = { ...state.member, ...updatedInfo };
      sessionStorage.setItem("member", JSON.stringify(state.member));
    },
  },
  getters: {
    getUserType(state) {
      return state.userType;
    },
    isLoginMember(state) {
      return state.member !== null;
    },
    isLoginEnter(state) {
      return state.enterMember !== null;
    },
    getMember(state) {
      return state.member; // 사용자 정보 반환
    },
    enterMember(state) {
      return state.enterMember; // 사용자 정보 반환
    },
  },
});

export default store;
