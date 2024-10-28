import { useState } from "react";
import { Link } from "react-router-dom";
import styled from "styled-components";
import UserIdForm from "./UserIdForm";
import { useModal } from "../components/ModalProvider";
import UserPwForm from "./UserPwForm";

const FindSection = styled.div`
  .card-body {
    width: 400px;
  }
  .row .col-6 {
    text-align: center;
  }
`;
export default function UserFindForm() {
  const [idPwCheck, setIdPwCheck] = useState(true);
  const { showModal } = useModal();

  const handleLoginSubmit = (e, formType) => {
    e.preventDefault();
    if (formType === "id") {
      // 아이디 찾기 로직
      showModal("아이디 찾기 로직 실행");
    } else {
      // 비밀번호 찾기 로직
      showModal("비밀번호 찾기 로직 실행");
    }
  };
  return (
    <FindSection>
      <section className="page-header page-header-modern bg-color-grey page-header-lg">
        <div className="container">
          <div className="row">
            <div className="col-md-12 align-self-center p-static order-2 text-center">
              <h1 className="font-weight-bold text-dark">계정정보 찾기</h1>
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
                  <div className="row">
                    <div className="col-6" style={{ padding: 0 }}>
                      <Link
                        className="btn btn-outline btn-primary rounded-0 mb-2"
                        onClick={(e) => setIdPwCheck(true)}
                      >
                        아이디찾기
                      </Link>
                    </div>
                    <div className="col-6" style={{ padding: 0 }}>
                      <Link
                        className="btn btn-outline btn-primary rounded-0 mb-2"
                        onClick={(e) => setIdPwCheck(false)}
                      >
                        비밀번호찾기
                      </Link>
                    </div>
                  </div>
                  {/* 로그인 or pw 폼 시작 */}

                  {idPwCheck ? (
                    <UserIdForm handleLoginSubmit={handleLoginSubmit} />
                  ) : (
                    <UserPwForm handleLoginSubmit={handleLoginSubmit} />
                  )}
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>
    </FindSection>
  );
}
