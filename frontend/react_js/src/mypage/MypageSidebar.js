import React from "react"
import { Link } from 'react-router-dom';

const MypageSidebar = () =>{


    return(


    <div className="col-lg-3">
            <aside className="sidebar" style={{position: 'fixed'}}>
                <h5 className="font-weight-semi-bold pt-4">마이페이지</h5>
                <ul className="nav nav-list flex-column mb-5">
                    <li className="nav-item"><Link className="nav-link" to="/mypage/home">현황 및 일정</Link></li>
                    <li className="nav-item"><Link className="nav-link">이력서 관리</Link>
                        <ul>
                            <li className="nav-item"><Link className="nav-link" to="/mypage/resume-form">이력서 등록</Link></li>
                            <li className="nav-item"><Link className="nav-link" to="/mypage/resume-list">이력서 목록</Link></li>
                        </ul>
                    </li>
                    <li className="nav-item"><Link className="nav-link" to="/mypage/applycompanylist">입사지원현황</Link></li>
                    <li className="nav-item"><Link className="nav-link">포지션 제안</Link>
                        <ul>
                            <li className="nav-item"><Link className="nav-link" to="/mypage/ppAcception">포지션 제안설정</Link></li>
                            <li className="nav-item"><Link className="nav-link" to="/mypage/proposedPosition">제안 받은 포지션</Link></li>
                        </ul>
                    </li>
                    <li className="nav-item"><Link className="nav-link" to="#">스크랩 공고</Link></li>
                    <li className="nav-item">
                        <Link className="nav-link" to="#">회원정보 관리</Link>
                        <ul>
                            <li className="nav-item"><Link className="nav-link" to="#">회원정보 수정</Link></li>
                            <li className="nav-item"><Link className="nav-link" to="#">회원정보 변경</Link></li>
                            <li className="nav-item"><Link className="nav-link" to="#">회원탈퇴</Link></li>
                        </ul>
                    </li>
                </ul>
            </aside>
        </div>
    );

};


    

    export default MypageSidebar;
