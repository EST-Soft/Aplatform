import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";

const Header = () => {
//   const [userType, setUserType] = useState("admin"); // 기본값을 'user'로 설정
//   const [enterpriseData, setEnterpriseData] = useState(null);
//   const [matchingResumes, setMatchingResumes] = useState([]);
//   const navigate = useNavigate();

//   useEffect(() => {
//     // API 호출로 사용자 타입을 가져오는 예제
//     axios
//       .get("/api/user/status")
//       .then((response) => {
//         setUserType(response.data.userType || "admin"); // 'admin'로 기본값 설정
//       })
//       .catch((error) => {
//         console.error("Error fetching user status:", error);
//       });
//   }, []);

//   useEffect(() => {
//     // API 호출로 기업 메인 페이지 데이터를 가져오는 예제
//     if (userType === "enter") {
//       axios
//         .get("/enterprise", { params: { entprs_sq: 123 } }) // 예시로 entprs_sq = 123 사용
//         .then((response) => {
//           setEnterpriseData(response.data);
//         })
//         .catch((error) => {
//           console.error("Error fetching enterprise main page data:", error);
//         });
//     }
//   }, [userType]);

//   const fetchMatchingResumes = (jbp_sq) => {
//     axios
//       .get(`/enterprise/matchingResume/${jbp_sq}`)
//       .then((response) => {
//         setMatchingResumes(response.data);
//       })
//       .catch((error) => {
//         console.error("Error fetching matching resumes:", error);
//       });
//   };

//   const changeUserType = (type) => {
//     // 서버에 사용자 타입 변경 요청을 보냅니다
//     // axios.post('/api/user/change', { userType: type })
//     //   .then(() => {
//     setUserType(type);
//     if (type === "enter") {
//       navigate("/enter");
//     } else if (type === "user") {
//       navigate("/");
//     }
//     //   })
//     //   .catch(error => {
//     //     console.error('Error changing user type:', error);
//     //   });
//   };

  return (
    <header
      id="header"
      className="header-effect-shrink"
      data-plugin-options="{'stickyEnabled': true, 'stickyEffect': 'shrink', 'stickyEnableOnBoxed': true, 'stickyEnableOnMobile': false, 'stickyChangeLogo': true, 'stickyStartAt': 120, 'stickyHeaderContainerHeight': 70}"
      style={{ height: "110px" }}
    >
      <div className="header-body border-top-0" style={{ position: "fixed" }}>
        <div className="header-container container" style={{ height: "100px" }}>
          <div className="header-row">
            <div className="header-column">
              <div className="header-row">
                <div
                  className="header-logo"
                  style={{ width: "100px", height: "48px" }}
                >
                  <Link to="/">
                    <img
                      alt="Porto"
                      width="100"
                      height="48"
                      data-sticky-width="82"
                      data-sticky-height="40"
                      src="/img/jobs-icon.png"
                    />
                  </Link>
                </div>
                {/* <div
                  className="ms-3"
                  style={{ cursor: "pointer" }}
                  onClick={() => changeUserType("user")}
                >
                  일반회원
                </div>
                <div
                  className="ms-3"
                  style={{ cursor: "pointer" }}
                  onClick={() => changeUserType("enter")}
                >
                  기업회원
                </div> */}
              </div>
            </div>
            <div
              className="header-column justify-content-end"
            //   style={{ display: userType === "user" ? "block" : "none" }}
            >
              <div className="header-row">
                <div className="header-nav header-nav-line header-nav-bottom-line header-nav-bottom-line-no-transform header-nav-bottom-line-active-text-dark header-nav-bottom-line-effect-1 order-2 order-lg-1">
                  <div className="header-nav-main header-nav-main-square header-nav-main-dropdown-no-borders header-nav-main-effect-2 header-nav-main-sub-effect-1">
                    <nav className="collapse">
                      <ul className="nav nav-pills" id="mainNav">
                        <li className="dropdown">
                          <Link
                            to="/"
                            className="dropdown-item dropdown-toggle font-weight-bold"
                          >
                            HOME
                            <i className="fas fa-chevron-down"></i>
                          </Link>
                        </li>
                        <li className="dropdown">
                          <Link
                            to="/user/jobinfo"
                            className="dropdown-item dropdown-toggle font-weight-bold"
                          >
                            채용정보
                            <i className="fas fa-chevron-down"></i>
                          </Link>
                        </li>
                        <li className="dropdown">
                          <Link
                            className="dropdown-item dropdown-toggle font-weight-bold"
                            to="#"
                          >
                            게시판
                            <i className="fas fa-chevron-down"></i>
                          </Link>
                          <ul className="dropdown-menu">
                            <li>
                              <Link className="dropdown-item" to="/board">
                                일반게시판
                              </Link>
                            </li>
                            <li>
                              <Link className="dropdown-item" to="/board">
                                피드백게시판
                              </Link>
                            </li>
                            <li>
                              <Link className="dropdown-item" to="/board">
                                Q&A게시판
                              </Link>
                            </li>
                          </ul>
                        </li>
                        <li className="dropdown">
                          <Link
                            to="#"
                            className="dropdown-item dropdown-toggle font-weight-bold "
                          >
                            사용자관리
                            <i className="fas fa-chevron-down"></i>
                          </Link>
                          <ul className="dropdown-menu">
                            <li>
                              <Link className="dropdown-item" to="/userManagement/UserManagement">
                                개인회원 관리
                              </Link>
                            </li>
                            <li>
                              <Link className="dropdown-item" to="/userManagement/EnterpriseManagement">
                                기업회원 관리
                              </Link>
                            </li>
                            <li>
                              <Link className="dropdown-item" to="/userManagement/AdminManagement">
                                관리자 관리
                              </Link>
                            </li>
                            <li>
                              <Link className="dropdown-item" to="/userManagement/AdminMypage">
                                마이페이지
                              </Link>
                            </li>
                          </ul>
                        </li>
                      </ul>
                    </nav>
                  </div>
                  <button
                    className="btn header-btn-collapse-nav"
                    data-bs-toggle="collapse"
                    data-bs-target=".header-nav-main nav"
                  >
                    <i className="fas fa-bars"></i>
                  </button>
                </div>
                <div className="header-nav-features header-nav-features-no-border header-nav-features-lg-show-border order-1 order-lg-2">
                  <div className="header-nav-feature header-nav-features-search d-inline-flex">
                    <Link className="nav-link" to="/admin/login">
                      LOGIN
                    </Link>
                  </div>
                  <div className="header-nav-feature header-nav-features-cart d-inline-flex ms-2">
                    <span
                      className="header-nav-features-toggle text-decoration-none me-3"
                      data-focus="headerSearch"
                      aria-label="Search"
                    >
                      admin
                    </span>
                    <Link
                      to="/admin/logout"
                      className="header-nav-features-toggle"
                      aria-label=""
                    >
                      로그아웃
                    </Link>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </header>
  );
};

export default Header;
