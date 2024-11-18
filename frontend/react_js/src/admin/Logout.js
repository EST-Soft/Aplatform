import React, { useEffect } from 'react';
import { useDispatch } from 'react-redux';
import { changeUserType, clearMember } from '../store/store';  // store에서 clearMember 액션 임포트
import { useNavigate } from 'react-router-dom';  // 리디렉션을 위한 navigate 사용

const Logout =()=>{
    const dispatch = useDispatch();
    const navigate = useNavigate();  // useNavigate 훅을 사용하여 페이지 이동
    


    useEffect(() => {
        console.log("로그아웃중")
        // 로그아웃 처리: Redux 상태에서 사용자 정보 제거
        dispatch(clearMember());
        dispatch(changeUserType('not_login'));
        // 세션 스토리지에서 사용자 정보 제거
        sessionStorage.removeItem("member");
    
        // 로그아웃 후 메인 페이지로 리디렉션
        navigate('/');
      }, [dispatch, navigate]);

      return(
        <div>
            <h1>로그아웃 중...</h1>
            <p>잠시만 기다려 주세요. 로그아웃이 완료되면 로그인 페이지로 이동합니다.</p>
        </div>

      );
}

export default Logout;