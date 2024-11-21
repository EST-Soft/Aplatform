import { createRouter, createWebHistory } from "vue-router";
import store from "@/store"; // Vuex 스토어를 가져옵니다
import UserMainView from "@/views/fo/user/UserMainView.vue";
import MypageView from "@/views/fo/user/MypageView.vue";
import MypageHome from "@/components/fo/user/mypage/MypageHome.vue";
import MypagePPAcception from "@/components/fo/user/mypage/MypagePPAcception.vue";
import EnterMemberRegister from "@/views/fo/enterprise/EnterMemberRegister.vue";
import EnterLogin from "@/views/fo/enterprise/EnterLogin.vue";
import ApplyCompanyList from "@/components/fo/user/mypage/ApplyCompanyList.vue";
import JobPostingListView from "@/views/fo/enterprise/JobPostingListView.vue";
import JobPostingInsert from "@/views/fo/enterprise/JobPostingInsertForm.vue";
import JobPostingDetail from "@/views/fo/enterprise/JobPostingDetailView.vue";
import JobPostingUpdate from "@/views/fo/enterprise/JobPostingUpdateForm.vue";
import ApplyListView from "../views/fo/enterprise/apply/ApplyListView";
import ApplyDetailView from "../views/fo/enterprise/apply/ApplyDetailView";
import ResumeInsertView from "../views/fo/enterprise/resume/ResumeInsertView";
import ResumeListView from "../views/fo/enterprise/resume/ResumeListView";
import ResumeDetailView from "../views/fo/enterprise/resume/ResumeDetailView";
import MypageJobView from "@/components/fo/user/mypage/MypageJobView.vue";
import MypageProposedPositionList from "@/components/fo/user/mypage/MypageProposedPositionList.vue";
// import EnterFind from "@/views/fo/enterprise/EnterFind.vue";
import EntMypageView from "@/views/fo/enterprise/EntMypageView.vue";
import EnterInfoUpdate from "@/views/fo/enterprise/EnterInfoUpdate.vue";
// import TempTemp from "@/components/fo/enterprise/enterpriseMypage/TempTemp.vue";
import EnterSignOut from "@/views/fo/enterprise/EnterSignOut.vue";
import EnterChangePassword from "@/views/fo/enterprise/EnterChangePassword.vue";
import EnterMainView from "@/views/fo/enterprise/EnterMainView.vue";
import RecommendResumeList from "@/views/fo/enterprise/RecommendResumeList.vue";
import LoginView from "../views/fo/auth/LoginView.vue";
import UserSignup from "../views/fo/auth/UserSignup.vue";
import UserFindMain from "../views/fo/auth/UserFind/UserFindMain.vue";
import UserPwUpdate from "../views/fo/auth/UserFind/UserPwUpdate.vue";
import BoardList from "../views/fo/community/board/BoardList.vue";
import FeedbackList from "../views/fo/community/feedback/FeedbackList.vue";
import QnAList from "../views/fo/community/qna/QnaList.vue";
import BoardDetail from "../views/fo/community/board/BoardDetail.vue";
import BoardWrite from "../views/fo/community/board/BoardWrite.vue";
import BoardUpdate from "../views/fo/community/board/BoardUpdate.vue";
import EnterFindMain from "../views/fo/auth/EnterFind/EnterFindMain.vue";
import QnADetail from "../views/fo/community/qna/QnADetail.vue";
import QnAUpdate from "../views/fo/community/qna/QnAUpdate.vue";
import QnaWrite from "../views/fo/community/qna/QnaWrite.vue";
import FeedbackWrite from "../views/fo/community/feedback/FeedbackWrite.vue";
import FeedbackDetail from "../views/fo/community/feedback/FeedbackDetail.vue";
import FeedbackUpdate from "../views/fo/community/feedback/FeedbackUpdate.vue";
import UserInfrmtnUpdt from "../views/fo/auth/UserInfrmtnUpdt.vue";
import UserPwChange from "../views/fo/auth/UserFind/UserPwChange.vue";
import UserSingout from "../views/fo/auth/UserSingout.vue";
import ScrapListView from "../views/fo/user/ScrapListView.vue";
import EnterJobPostingList from "../views/fo/enterprise/EnterJobPostingList.vue";

// import store from "@/store"; // Vuex 스토어를 직접 가져옵니다.

const routes = [
  { path: "/:pathMatch(.*)*", redirect: "/" }, // 존재하지 않는 경로일 때 메인 페이지로 리디렉션
  {
    path: "/",
    component: UserMainView,
  },

  {
    path: "/mypage",
    component: MypageView,
    children: [
      {
        path: "home",
        component: MypageHome,
      },
      {
        path: "ppAcception",
        component: MypagePPAcception,
      },
      {
        path: "proposedPosition",
        component: MypageProposedPositionList,
      },
      // 수진 - 최근 본 공고
      {
        path: "JobViewList",
        component: MypageJobView,
      },
      // 지연님 컴포넌트 경로
      {
        path: "applycompanylist",
        component: ApplyCompanyList,
      },
      {
        path: "/resumes/resume-detail/:resSq",
        name: "resumeDetailView",
        component: ResumeDetailView,
      },
      { path: "/resumes/resume-insert", component: ResumeInsertView },
      { path: "/resumes/resume-list", component: ResumeListView },

      { path: "userInfmtn", name: "userInfmtn", component: UserInfrmtnUpdt },
      { path: "userPwChange", component: UserPwChange },
      { path: "userSingout", component: UserSingout },
      { path: "scrapList", name: "scrapList", component: ScrapListView },
    ],
  },
  // 기우님 컴포넌트 경로
  {
    path: "/enter",
    children: [
      { path: "home", component: EnterMainView },
      {
        path: "signup",
        name: "enterRegister",
        component: EnterMemberRegister,
      },
      { path: "login", name: "enterLogin", component: EnterLogin },
      { path: "find", name: "EnterFindMain", component: EnterFindMain },
      {
        path: "mypage", // 기업 마이페이지
        component: EntMypageView,
        children: [
          {
            path: "infoUpdate", // 기업정보 수정
            component: EnterInfoUpdate,
          },
          {
            path: "signOut",
            component: EnterSignOut,
          },
          {
            path: "chagePassword",
            component: EnterChangePassword,
          },
        ],
      },
    ],
  },

  // 세추님 컴포넌트 경로
  {
    path: "/board",
    children: [
      // {
      //   path: "",
      //   component: BoardList,
      // },
      // {
      //   path: ":id",
      //   component: BoardDetail,
      // },
      // {
      //   path: "update/:id",
      //   component: BoardUpdate,
      // },
      // {
      //   path: "write",
      //   component: BoardWrite,
      // },
      {
        path: "general",
        children: [
          {
            path: "",
            component: BoardList,
          },
          {
            path: ":id",
            component: BoardDetail,
          },
          {
            path: "write",
            component: BoardWrite,
          },
          {
            path: "update/:id",
            component: BoardUpdate,
          },
        ],
      },
      {
        path: "feedback",
        children: [
          {
            path: "",
            component: FeedbackList,
          },
          {
            path: ":id",
            component: FeedbackDetail,
          },
          {
            path: "write",
            component: FeedbackWrite,
          },
          {
            path: "update/:id",
            component: FeedbackUpdate,
          },
        ],
      },
      {
        path: "qna",

        children: [
          {
            path: "",
            component: QnAList,
          },
          {
            path: ":id",
            component: QnADetail,
          },
          {
            path: "update/:id",
            component: QnAUpdate,
          },
          {
            path: "write",
            component: QnaWrite,
          },
        ],
      },

      {
        path: "list/jobPosting",
        name: JobPostingListView,
        component: JobPostingListView,
      },
      {
        path: "jobPostingInsert",
        name: JobPostingInsert,
        component: JobPostingInsert,
      },
      {
        path: "detail/jobPosting/:jbpSq",
        name: JobPostingDetail,
        component: JobPostingDetail,
        props: true,
      },
      {
        path: "jobPostingUpdate/:jbpSq",
        name: JobPostingUpdate,
        component: JobPostingUpdate,
      },
    ],
  },
  // 송영태
  {
    path: "/applys/apply-list/:jbpSq",
    name: "applyListView",
    component: ApplyListView,
  },
  {
    path: "/applys/apply-detail",
    name: "applyDetailView",
    component: ApplyDetailView,
  },

  // ㅈㅣ연님 경로

  {
    path: "/user",
    children: [
      {
        path: "login", //
        component: LoginView,
      },
      {
        path: "signup",
        component: UserSignup,
      },
      {
        path: "find",
        component: UserFindMain,
      },
    ],
  },
  {
    path: "/pwupdate",
    component: UserPwUpdate,
  },
  {
    path: "/enter/recommendResumeList",
    component: RecommendResumeList,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
  linkActiveClass: "active",
});
// // 글로벌 네비게이션 가드를 사용합니다.
// router.beforeEach((to, from, next) => {
//   const userType = store.getters.getUserType;
//   console.log(userType);

//   if (to.path.startsWith("/board") && userType === "enter") {
//     alert("접근이 제한되었습니다.");
//     next(false); // 접근 차단
//   } else {
//     next(); // 접근 허용
//   }
// });

// 라우터 인스턴스 생성
router.beforeEach((to, from, next) => {
  const userType = store.getters.getUserType; // Vuex에서 사용자 유형 확인

  // 특정 경로에 대해 기업용 컴포넌트 설정
  if (to.path === "/board/list/jobPosting") {
    if (userType === "enter") {
      // 기업 로그인인 경우에는 EnterJobPostingList로 컴포넌트 변경
      const route = to.matched.find(route => route.path === "/board/list/jobPosting");
      if (route) {
        route.components.default = EnterJobPostingList; // 기업용 컴포넌트로 교체
      }
    } else {
      // 일반 로그인 및 비로그인 사용자의 경우 기본 컴포넌트로 유지
      const route = to.matched.find(route => route.path === "/board/list/jobPosting");
      if (route) {
        route.components.default = JobPostingListView; // 기본 컴포넌트로 설정
      }
    }
  }
  next(); // 경로에 따른 컴포넌트 설정 후 진행
});

export default router;
