// ModalProvider.js
import React, { useContext, useState, createContext } from "react";
import { Modal as BootstrapModal } from "react-bootstrap";
import styled from "styled-components";

// 모달 컨텍스트 생성
const ModalContext = createContext();

// 모달 컨텍스트 사용 커스텀 훅
export const useModal = () => useContext(ModalContext);

// 모달 프로바이더 컴포넌트
export const ModalProvider = ({ children }) => {
  const [modalContent, setModalContent] = useState(null);

  const showModal = (content) => setModalContent(content);
  const closeModal = () => setModalContent(null);

  return (
    <ModalContext.Provider value={{ showModal, closeModal }}>
      {children}
      <StyledModal show={!!modalContent} onHide={closeModal} centered>
        <StyledModal.Header closeButton>
          <StyledModal.Title>메세지 알림</StyledModal.Title>
        </StyledModal.Header>
        <StyledModal.Body>{modalContent}</StyledModal.Body>
        <StyledModal.Footer>
          <button onClick={closeModal}>닫기</button>
        </StyledModal.Footer>
      </StyledModal>
    </ModalContext.Provider>
  );
};

// 스타일드 컴포넌트로 모달 스타일 정의
//   const { showModal } = useModal();
//    전역사용시   const { showModal } = useModal(); 가져와서 showModal("입력할 메시지 보내면 됨")

const StyledModal = styled(BootstrapModal)`
  .modal-content {
    border-radius: 0;
  }
`;

StyledModal.Header = styled(BootstrapModal.Header)`
  border-bottom: none;
`;

StyledModal.Title = styled(BootstrapModal.Title)`
  font-size: 1.5rem;
  font-weight: bold;
`;

StyledModal.Body = styled(BootstrapModal.Body)`
  text-align: start;
  height: 100px;
  padding: 20px;
  font-size: 1rem;
`;

StyledModal.Footer = styled(BootstrapModal.Footer)`
  border-top: none;
  justify-content: flex-end;

  button {
    background-color: #007bff;
    border: none;
    border-radius: 5px;
    color: white;
    cursor: pointer;

    &:hover {
      background-color: #0056b3;
    }
  }
`;
