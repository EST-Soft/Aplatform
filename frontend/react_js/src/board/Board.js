import React from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
//기본 게시물 컴포넌트
const Board = ({ idx, title, contents, createdBy, createdAt, hits }) => {
  const navigate = useNavigate();
  //수정버튼
  const moveToUpdate = () => {
    navigate('/update/' + idx);
  };
  //삭제버튼 함수
  const deleteBoard = async () => {
    if (window.confirm('게시글을 삭제하시겠습니까?')) {
      //idx는 게시물 번호
      await axios.delete(`/board/${idx}`).then((res) => {
        alert('삭제되었습니다.');
        navigate('/board');
      });
    }
  };

  const moveToList = () => {
    navigate('/board');
  };

  return (
    <div>
      <header className="header">
        <h1>일반게시판</h1>
      </header>
      <div className='form-container'>
        <div className='form-group'>
          <span>제목</span>
          <h2>{title}</h2>
          <div className="post-meta">
            <span>작성자: {createdBy}</span>
            <span>등록일: {createdAt ? createdAt.slice(0, 10) : ''}</span>
            <span>조회수: {hits}</span>
          </div>
          
          <hr />
          <p className='form-group'>{contents}</p>
        </div>
        <div className='button-container'>
          {/* 로그인 여부 따져서 수정/삭제 버튼 가려야함(일단 로그인 된 가정) */}
          <button className='btn btn-primary' onClick={moveToUpdate}>수정</button>
          <button className='btn btn-primary' onClick={deleteBoard}>삭제</button>
          <button className='btn btn-primary' onClick={moveToList}>목록</button>
        </div>
      </div>
    </div>
  );
};

export default Board;