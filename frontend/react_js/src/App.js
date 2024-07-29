
import { Route, Routes, useLocation } from 'react-router-dom';

import React from 'react';
import SidebarLayout from './mypage/SidebarLayout';
import MypageHome from './mypage/MypageHome';
import ResumeForm from './mypage/ResumeInsertView';
import ResumeList from './mypage/ResumeListView';
import PositionProposal from './mypage/MypagePPAcception';
import PositionProposalList from './mypage/MypageProposedPositionList';
import ApplyCompanyList from './mypage/ApplyCompanyList';
import ResumeDetail from './mypage/ResumeDetails';


import JobInfo from './user/JobPostingListView';
import UserLoginForm from './user/UserLoginForm';
import UserJobPostingDetail from './user/UserJobPostingDetail';
// 기업 관련 import
import EnterMainView from './enterprise/EnterMainView';
import EnterMypage from './enterprise/EnterMypageView';
import EnterSidebarLayout from './enterprise/EnterSidebarLayout';
import EnterInfoUpdate from './enterprise/EnterInfoUpdate';
import EnterChangePassword from './enterprise/EnterChangePassword';
import EnterSignOut from './enterprise/EnterSignOut';
import EnterJobInfo from './enterprise/EnterJobPostingList';
import EnterRcmndResumeList from './enterprise/EnterRecommendResumeList';
import EnterLoginForm from './enterprise/EnterLoginForm';
import EnterSignUp from './member/EnterMemberRegister';
import EnterJobPostingForm from './enterprise/EnterJobPostingInsertForm';
import EnterJobPostingDetail from './enterprise/EnterJobPostingDetail';
import EnterJobPostingEdit from './enterprise/EnterJobPostingUpdateForm';
import 'bootstrap/dist/css/bootstrap.min.css';
// import Header from "./layout/Header";
import './App.css';

function Test() {
  const location = useLocation();

  const showSidebar = ['/mypage/home', '/mypage/resume-form', '/mypage/resume-list', '/mypage/ppAcception',
    '/mypage/proposedPosition', '/mypage/applycompanylist', '/resumeDetailView/:rsm_sq'
  ].includes(location.pathname);

  // 기업회원 사이드바 표시 조건
  const showSidebarEnter = ['/enter/mypage', '/enter/infoUpdate', '/enter/chahgePassword', '/enter/EnterSignOut'].includes(location.pathname);
  // 사이드바 레이아웃 결정
  const isEnterprise = location.pathname.startsWith('/enter');

  return (
    <>
        {isEnterprise ? (
          // 기업회원 사이드바 레이아웃
          <EnterSidebarLayout showSidebarEnter={showSidebarEnter}>
            <Routes>
              <Route path="/enter" element={<EnterMainView />} />
              <Route path="/enter/mypage" element={<EnterMypage />} />
              <Route path="/enter/infoUpdate" element={<EnterInfoUpdate />} />
              <Route path="/enter/chahgePassword" element={<EnterChangePassword />} />
              <Route path="/enter/EnterSignOut" element={<EnterSignOut />} />
              <Route path="/enter/EnterjobPostinglist" element={<EnterJobInfo />} />
              <Route path="/enter/EnterrecmndResumeList" element={<EnterRcmndResumeList />} />
              <Route path="/enter/login" element={<EnterLoginForm />} />
              <Route path="/enter/signup" element={<EnterSignUp />} />
              <Route path="/enter/jobPostingInsert" element={<EnterJobPostingForm />} />
              <Route path="/board/detail/jobPosting/:jbpSq" element={<EnterJobPostingDetail />} />
             

               
            </Routes>
          </EnterSidebarLayout>
        ) : (
          // 일반회원 사이드바 레이아웃
          <SidebarLayout showSidebar={showSidebar}>
            <Routes>
              {/* <Route path="/" element={<Home />} />
              <Route path="/board" element={<BoardList />} />
              <Route path="/board/:idx" element={<BoardDetail />} />
              <Route path="/write" element={<BoardWrite />} />
              <Route path="/update/:idx" element={<BoardUpdate />} /> */}
              {/* <Route path="/" element={<UserMainView />} /> */}
              <Route path="/user/jobinfo" element={<JobInfo />} />
              <Route path="/user/login" element={<UserLoginForm />} />
              <Route path="/board/detail/jobPosting/:jbpSq" element={<UserJobPostingDetail />} />
              <Route path="/board/jobPostingUpdate/:jbpsq" element={<EnterJobPostingEdit />} />
              <Route path="/mypage/home" element={<MypageHome />} />
            
              <Route path="/mypage/resume-form" element={<ResumeForm />} />
              <Route path="/mypage/resume-list" element={<ResumeList />} />
              <Route path="/mypage/ppAcception" element={<PositionProposal />} />
              <Route path="/mypage/proposedPosition" element={<PositionProposalList />} />
              <Route path="/mypage/applycompanylist" element={<ApplyCompanyList />} />
              <Route path="/resumeDetailView/:rsm_sq" element={<ResumeDetail />} />
              
           
            </Routes>
          </SidebarLayout>
        )}
    </>
  );
}


export default Test;