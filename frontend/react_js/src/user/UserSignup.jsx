import React, { useState } from "react";
import { Modal, Button } from "react-bootstrap";
import styled from "styled-components";
import { useModal } from "../components/ModalProvider";
import TermsText from "../components/TermsText";

const FormContainer = styled.div`
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
  background-color: #fff;
  border-radius: 5px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
`;

const StyledModal = styled(Modal)`
  .modal-dialog {
    max-width: 80%; /* 모달의 최대 너비를 화면의 80%로 제한 */
  }
  .modal-content {
    border-radius: 0;
  }
  .modal-header,
  .modal-footer {
    border: none;
  }
`;

const StyledModalBody = styled(Modal.Body)`
  max-height: 400px; /* 모달 높이 제한 */
  overflow-y: auto; /* 세로 스크롤바 추가 */
`;

const TermsButton = styled(Button)`
  margin-left: 10px;
`;

export default function UserSignup() {
  const [modal, setmodal] = useState(false);
  const [agreed, setAgreed] = useState(false);
  const [formData, setFormData] = useState({
    userId: "",
    password: "",
    confirmPassword: "",
    name: "",
    phone: "",
    email: "",
    authCode: "",
  });
  const { showModal } = useModal();

  const handleInputChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleTermsAgreement = () => {
    setAgreed(true);
    setmodal(false);
  };

  const handleSignup = () => {
    if (!agreed) {
      showModal("약관 동의를 하셔야 합니다.");
      return;
    } else {
      console.log(formData);
      // 회원가입 로직 처리
    }
  };

  return (
    <>
      <section className="page-header page-header-modern bg-color-grey page-header-lg">
        <div className="container">
          <div className="row">
            <div className="col-md-12 align-self-center p-static order-2 text-center">
              <h1 className="font-weight-bold text-dark">일반회원 가입</h1>
            </div>
          </div>
        </div>
      </section>

      <FormContainer>
        <form>
          {[
            { label: "아이디", name: "userId", type: "text" },
            { label: "비밀번호", name: "password", type: "password" },
            {
              label: "비밀번호 확인",
              name: "confirmPassword",
              type: "password",
            },
            { label: "가입자명", name: "name", type: "text" },
            { label: "휴대폰번호", name: "phone", type: "text" },
            { label: "이메일", name: "email", type: "email" },
            { label: "인증번호", name: "authCode", type: "text" },
          ].map((user, index) => (
            <div className="form-group" key={index}>
              <label>{user.label}</label>
              <input
                type={user.type}
                className="form-control"
                name={user.name}
                value={formData[user.name]}
                onChange={handleInputChange}
              />
            </div>
          ))}
          <div className="form-group">
            <label>이메일 인증</label>
            <div>
              <input
                type="email"
                className="form-control"
                name="email"
                value={formData.email}
                onChange={handleInputChange}
              />
              <Button variant="primary">인증</Button>
            </div>
          </div>
          <div className="form-group">
            <label>약관동의</label>
            <div className="d-flex align-items-center">
              <input
                type="checkbox"
                checked={agreed}
                onChange={() => setAgreed(!agreed)}
                readOnly
              />
              <TermsButton variant="link" onClick={() => setmodal(true)}>
                약관 보기
              </TermsButton>
            </div>
          </div>
          <Button variant="primary" onClick={handleSignup}>
            회원가입완료
          </Button>
        </form>
      </FormContainer>

      <StyledModal show={modal} onHide={() => setmodal(false)} centered>
        <StyledModal.Header closeButton>
          <StyledModal.Title>개인정보 수집 이용 동의</StyledModal.Title>
        </StyledModal.Header>
        <StyledModalBody>
          {/* 약관 내용 */}
          <TermsText />
        </StyledModalBody>
        <StyledModal.Footer>
          <Button variant="secondary" onClick={() => setmodal(false)}>
            닫기
          </Button>
          <Button variant="primary" onClick={handleTermsAgreement}>
            동의하기
          </Button>
        </StyledModal.Footer>
      </StyledModal>
    </>
  );
}
