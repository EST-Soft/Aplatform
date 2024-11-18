import React from "react"
import { Link } from 'react-router-dom';

const AdminSidebar = () =>{


    return(


    <div className="col-lg-3">
            <aside className="sidebar">
                <h5 className="font-weight-semi-bold pt-4">사용자관리</h5>
                <ul className="nav nav-list flex-column mb-5">
                    <li className="nav-item"><Link className="nav-link" to="/userManagement/UserManagement">개인회원 관리</Link></li>
                    <li className="nav-item"><Link className="nav-link" to="/userManagement/EnterpriseManagement"> 기업회원 관리</Link></li>
                    <li className="nav-item"><Link className="nav-link" to="/userManagement/AdminManagement">관리자관리</Link></li>
                    <li className="nav-item"><Link className="nav-link" to="/userManagement/AdminMypage">마이페이지</Link></li>
                </ul>
            </aside>
        </div>
    );

};


    

    export default AdminSidebar;
