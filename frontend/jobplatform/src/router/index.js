import { createRouter, createWebHistory } from 'vue-router';
import UserMainView from '@/views/fo/user/UserMainView.vue';
import MypageView from '@/views/fo/user/MypageView.vue';
import MypageHome from '@/components/fo/user/mypage/MypageHome.vue';
import MypagePPAcception from '@/components/fo/user/mypage/MypagePPAcception.vue';
import EnterMemberRegister from '@/views/fo/enterprise/EnterMemberRegister.vue';
import EnterLogin from '@/views/fo/enterprise/EnterLogin.vue';
import ApplyCompanyList from '@/components/fo/user/mypage/ApplyCompanyList.vue';
import JobPostingListView from '@/views/fo/enterprise/JobPostingListView.vue';
import JobPostingInsert from '@/views/fo/enterprise/JobPostingInsertForm.vue';
import JobPostingDetail from '@/views/fo/enterprise/JobPostingDetailView.vue';
import JobPostingUpdate from '@/views/fo/enterprise/JobPostingUpdateForm.vue';
import ApplyListView from '../views/fo/enterprise/apply/ApplyListView';
import ApplyDetailView from '../views/fo/enterprise/apply/ApplyDetailView';
import ResumeInsertView from '../views/fo/enterprise/resume/ResumeInsertView';
import ResumeListView from '../views/fo/enterprise/resume/ResumeListView';
import ResumeDetailView from '../views/fo/enterprise/resume/ResumeDetailView';
import MypageProposedPositionList from '@/components/fo/user/mypage/MypageProposedPositionList.vue'
import EnterFind from '@/views/fo/enterprise/EnterFind.vue'
import EntMypageView from '@/views/fo/enterprise/EntMypageView.vue'
import EnterInfoUpdate from '@/views/fo/enterprise/EnterInfoUpdate.vue'
import TempTemp from '@/components/fo/enterprise/enterpriseMypage/TempTemp.vue'
import EnterSignOut from '@/views/fo/enterprise/EnterSignOut.vue'
import EnterChangePassword from '@/views/fo/enterprise/EnterChangePassword.vue'
import LoginView from '@/views/fo/common/LoginView.vue';
import EnterMainView from '@/views/fo/enterprise/EnterMainView.vue'
import RecommendResumeList from '@/views/fo/enterprise/RecommendResumeList.vue'


const routes = [
  {
    path: '/',
    component: UserMainView,
  },
  {
    path: '/enter',
    component: EnterMainView
  },
  {
    path: '/mypage',
    component: MypageView,
    children: [
      {
        path: 'home',
        component: MypageHome,
      },
      {
        path: 'ppAcception',
        component: MypagePPAcception,
      },
      {
        path: 'proposedPosition',
        component : MypageProposedPositionList
      },
      // 지연님 컴포넌트 경로
      {
        path: 'applycompanylist',
        component: ApplyCompanyList,
      },
      { path: '/resumes/resume-detail/:resSq', name:'resumeDetailView', component: ResumeDetailView },
      { path: '/resumes/resume-insert', component: ResumeInsertView },
      { path: '/resumes/resume-list', component: ResumeListView },

    ],
  },
  // 기우님 컴포넌트 경로
  { path: '/enter/register', name: 'enterRegister', component: EnterMemberRegister },
  { path: '/enter/login', name: 'enterLogin', component: EnterLogin },
  { path: '/enter/find', name: 'enterFind', component: EnterFind },
  // 세추님 컴포넌트 경로
  {
    path: '/board/list/jobPosting',
    name: JobPostingListView,
    component: JobPostingListView,
  },
  {
    path: '/board/jobPostingInsert',
    name: JobPostingInsert,
    component: JobPostingInsert,
  },
  {
    path: '/board/detail/jobPosting/:jbpSq',
    name: JobPostingDetail,
    component: JobPostingDetail,
  },
  {
    path: '/board/jobPostingUpdate/:jbpSq',
    name: JobPostingUpdate,
    component: JobPostingUpdate,
  },
  // 송영태
  { path: '/applys/apply-list', name: 'applyListView', component: ApplyListView },
  { path: '/applys/apply-detail', name: 'applyDetailView', component: ApplyDetailView },


  // ㅈㅣ연님 경로
  {  path : '/enter',  // 기업 마이페이지
    component : EntMypageView,
    children:[
      { 
        path : 'mypage',  // 기업 마이페이지
        component : TempTemp,
      },
      { 
        path : 'infoUpdate',  // 기업정보 수정
        component : EnterInfoUpdate,
      },
      {
        path : 'signOut',
        component : EnterSignOut,
      },
      {
        path : 'chagePassword',
        component : EnterChangePassword,
      }
    ]
  },
  {
    path : '/login',
    component : LoginView
  },
  {
    path : '/enter/recommendResumeList',
    component : RecommendResumeList
  }
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
  linkActiveClass: 'active',
});

export default router;
