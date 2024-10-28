import React from "react"    
import { Link } from "react-router-dom"

const EnterMypageSidebar = () =>{

    return(
        <div className="col-lg-3">
        <aside className="sidebar" style={{position: "fixed"}}>
            <h5 className="font-weight-semi-bold pt-4">기업정보 관리</h5>
            <ul className="nav nav-list flex-column mb-5">
                <li className="nav-item"><Link className="nav-link" to="/enter/infoUpdate">기업정보 수정</Link ></li>
                <li className="nav-item"><Link className="nav-link" to="/enter/chahgePassword">비밀번호 변경</Link ></li>
                <li className="nav-item"><Link className="nav-link" to="/enter/EnterSignOut">회원탈퇴</Link ></li>
            </ul>
        </aside>
    </div>
    );
};

export default EnterMypageSidebar;