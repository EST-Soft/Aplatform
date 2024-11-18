import React from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './Board.css'; // 외부 CSS 파일을 이용하여 스타일링

const Board = ({ idx, title, contents, createdBy, createdAt, hits }) => {
  const navigate = useNavigate();
  
  const moveToUpdate = () => {
    navigate('/update/' + idx);
  };

  const deleteBoard = async () => {
    if (window.confirm('정말로 삭제하시겠습니까?')) {
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
    <div className="board-container">
      <header className="board-header">
        <h1>게시물 보기</h1>
      </header>
      <div className="board-content">
        <div className="board-title">
          <span>제목:</span>
          <h2>{Board.title}</h2>
        </div>
        <div className="post-meta">
          <span>작성자: {Board.createdBy}</span>
          <span>작성일: {Board.createdAt ? Board.createdAt.slice(0, 10) : ''}</span>
          <span>조회수: {Board.hits}</span>
        </div>
        <hr />
        <div className="board-body">
          <p>{Board.contents}</p>
        </div>
        <div className="button-container">
          <button className="btn btn-primary" onClick={moveToUpdate}>수정</button>
          <button className="btn btn-danger" onClick={deleteBoard}>삭제</button>
          <button className="btn btn-secondary" onClick={moveToList}>목록</button>
        </div>
      </div>
    </div>
  );
};

export default Board;
