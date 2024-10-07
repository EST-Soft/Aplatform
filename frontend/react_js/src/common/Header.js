import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";

const Header = ({admName}) => {
  const [userType, setUserType] = useState("user");
  const userId = 1; // 임시로 userId를 설정
  
  return (
    <header className="header-effect-shrink" style={{ height: "110px" }}>
      <nav className="navbar navbar-expand-lg navbar-light bg-light">
        <div className="container-fluid">
          <Link className="navbar-brand" to="/">
            <img
              alt="Porto"
              width="105"
              height="55"
              src="/img/jobs-icon.png"
            />
            <span style={{ marginLeft: '-30px', color: 'black', position: 'relative', top: '23px', fontSize: '14px'}}>Admin</span>
          </Link>
          <button
            className="navbar-toggler"
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#navbarNav"
            aria-controls="navbarNav"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <span className="navbar-toggler-icon"></span>
          </button>

          <div className="collapse navbar-collapse" id="navbarNav">
            <ul className="navbar-nav ms-auto"> 
            {admName ? ( // 로그인된 경우 관리자의 이름을 표시
                <li className="nav-item dropdown">
                  <Link
                    className="nav-link dropdown-toggle"
                    to="#"
                    id="navbarDropdown"
                    role="button"
                    data-bs-toggle="dropdown"
                    aria-expanded="false"
                    style={{ fontSize: '25px', textAlign: 'center', marginTop: '20px' }}
                  >
                    {admName} 님 {/* 관리자 이름 표시 */}
                  </Link>
                  <ul className="dropdown-menu" aria-labelledby="navbarDropdown">
                    <li>
                    <Link className="dropdown-item" to={`/admin/MyPage/${userId}`}>
                        마이페이지
                    </Link>

                    </li>
                    <li>
                      <Link className="dropdown-item" to="#">
                        로그아웃
                      </Link>
                    </li>
                  </ul>
                </li>
              ) : (
                <li>
                <Link className="nav-link" to="/admin/login">
                  로그인
                </Link>
              </li>
              )}
            </ul>
          </div>
        </div>
      </nav>
    </header>
  );
};

export default Header;
