import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import store from "../store/store";

const UserLoginForm = () => {
  const [entrprsId, setEntrprsId] = useState("");
  const [entrprsPswrd, setEntrprsPswrd] = useState("");
  const navigate = useNavigate();

  // 로그인 폼 제출 핸들러
  const handleLoginSubmit = async (event) => {
    event.preventDefault();

    // 데이터가 필요한 부분 주석 처리
    const loginData = {
      entrprsId,
      entrprsPswrd,
    };

    axios
      .post("/user/login", loginData, {
        withCredentials: true,
      })
      .then(async (response) => {
        store.commit("setEnter", response.data); // store에 기업 정보 저장(pk, id)
        await navigate("/");
      })
      .catch((error) => {
        console.error(error.response); // 에러 응답을 출력
        alert(error.response?.data?.message || error.message);
        //alert(error);
      });
  };

  // 네이버 로그인 핸들러
  //const handleNaverLogin = () => {
    //   // 데이터가 필요한 부분 주석 처리
    //   // const client_id = "TIwA7WnbAvnjEwnbPGZm"; // 본인 또는 회사 아이디로 교체
    //   // const redirect_uri = "http://localhost:8080/member/loginNCallback";
    //   // const generateRandomState = () => {
    //   //   const characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    //   //   let state = "";
    //   //   for (let i = 0; i < 16; i++) {
    //   //     const randomIndex = Math.floor(Math.random() * characters.length);
    //   //     state += characters.charAt(randomIndex);
    //   //   }
    //   //   return state;
    //   // };
    //   // const state = generateRandomState();
    //   // const apiURL = `https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=${client_id}&redirect_uri=${redirect_uri}&state=${state}`;
    //   // window.location.href = apiURL;
  //};

  return (
    <>
      <section className="page-header page-header-modern bg-color-grey page-header-lg">
        <div className="container">
          <div className="row">
            <div className="col-md-12 align-self-center p-static order-2 text-center">
              <h1 className="font-weight-bold text-dark">일반회원 로그인</h1>
            </div>
          </div>
        </div>
      </section>

      <section className="loginSection">
        <div className="card mb-4">
          <div className="card-body">
            <div className="row">
              <div className="col">
                <div className="contact-form form-style-3">
                  <div className="contact-form-success alert alert-success d-none mt-4">
                    <strong>Success!</strong> Your message has been sent to us.
                  </div>
                  <div className="contact-form-error alert alert-danger d-none mt-4">
                    <strong>Error!</strong> There was an error sending your
                    message.
                    <span className="mail-error-message text-1 d-block"></span>
                  </div>
                  <div className="row">
                    <div className="col-6" style={{ padding: 0 }}>
                      <Link
                        className="btn btn-outline btn-primary rounded-0 mb-2"
                        style={{ width: "100%" }}
                        to="/user/login"
                      >
                        일반회원
                      </Link>
                    </div>
                    <div className="col-6" style={{ padding: 0 }}>
                      <Link
                        className="btn btn-outline btn-primary rounded-0 mb-2"
                        style={{ width: "100%" }}
                        to="/enter/login"
                      >
                        기업회원
                      </Link>
                    </div>
                  </div>
                  {/* 로그인 폼 시작 */}
                  <form onSubmit={handleLoginSubmit}>
                    <div className="row">
                      <div className="form-group col">
                        <label className="form-label mb-1 text-2">아이디</label>
                        <input
                          type="text"
                          maxLength="100"
                          className="form-control text-3 h-auto py-2"
                          name="id"
                          value={entrprsId}
                          onChange={(e) => setEntrprsId(e.target.value)}
                          placeholder="아이디를 입력해주세요"
                          autoComplete="off"
                          onFocus={(e) => (e.target.placeholder = "")}
                          onBlur={(e) =>
                            (e.target.placeholder = "아이디를 입력해주세요")
                          }
                        />
                      </div>
                    </div>
                    <div className="row">
                      <div className="form-group col">
                        <label className="form-label mb-1 text-2">
                          비밀번호
                        </label>
                        <input
                          type="password"
                          maxLength="100"
                          className="form-control text-3 h-auto py-2"
                          placeholder="비밀번호를 입력해주세요"
                          autoComplete="off"
                          value={entrprsPswrd}
                          onChange={(e) => setEntrprsPswrd(e.target.value)}
                          onFocus={(e) => (e.target.placeholder = "")}
                          onBlur={(e) =>
                            (e.target.placeholder = "비밀번호를 입력해주세요")
                          }
                        />
                      </div>
                    </div>
                    <div className="d-flex justify-content-center">
                      <input
                        type="submit"
                        value="로그인"
                        className="btn btn-primary w-100 mb-3"
                        data-loading-text="Loading..."
                      />
                    </div>
                  </form>
                  {/* 로그인 폼 끝 */}

                  <ul
                    className="d-flex justify-content-between nav nav-pills"
                    id="mainNav"
                  >
                    <li className="dropdown pe-5">
                      <Link to="/user/signup" className="dropdown-item">
                        회원가입
                      </Link>
                    </li>
                    <li>
                      <Link to="/user/find" className="dropdown-item">
                        아이디, 비밀번호 찾기
                      </Link>
                    </li>
                  </ul>
                  <hr />
                  {/* <div className="align-self-center">
                    <ul
                      className="d-flex justify-content-center nav nav-pills"
                      id="mainNav"
                    >
                      <li className="dropdown">
                        <a
                          href="#"
                          onClick={handleNaverLogin}
                          className="dropdown-item"
                        >
                          <img
                            src="path_to_placeholder_image/naverlogo.png"
                            alt="Naver Logo"
                            className="socialLogo"
                          />
                        </a>
                      </li>
                    </ul>
                  </div> */}
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>
    </>
  );
};

export default UserLoginForm;
