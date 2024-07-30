// Redux Tollkit 설치 npm install @reduxjs/toolkit react-redux
import { configureStore, createSlice } from '@reduxjs/toolkit';

// 초기 상태 정의
const initialState = {
  userType: 'user',  // 일반회원 : user, 기업회원 : enter
  member: JSON.parse(sessionStorage.getItem("member")) || null, // 사용자 정보 저장할 상태
  enterMember: [] // 배열 형태로 저장
};

// createSlice를 사용하여 리듀서와 액션 생성
const userSlice = createSlice({
  name: 'user',
  initialState,
  reducers: {
    changeUserType(state, action) {
      state.userType = action.payload;
    },
    setEnter(state, action) {
      state.enterMember = action.payload;
    },
    setMember(state, action) {
      state.member = action.payload;
      sessionStorage.setItem("member", JSON.stringify(action.payload));
    },
    clearMember(state) {
      state.member = null;
      sessionStorage.removeItem("member");
    },
    updateMemberInfo(state, action) {
      state.member = { ...state.member, ...action.payload };
      sessionStorage.setItem("member", JSON.stringify(state.member));
    }
  }
});

// 액션 생성자 내보내기
export const { changeUserType, setEnter, setMember, clearMember, updateMemberInfo } = userSlice.actions;

// Redux 스토어 생성
const store = configureStore({
  reducer: userSlice.reducer
});

export default store;
