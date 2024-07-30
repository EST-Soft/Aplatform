import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const BoardWrite = () => {
  //화면이동 객체 선언
  const navigate = useNavigate();
  //변수 선언 (게시글 정보)
  const [board, setBoard] = useState({
    title: '',
    createdBy: '',
    contents: '',
  });

  const { title, createdBy, contents } = board; //비구조화 할당

  const onChange = (event) => {
    const { value, name } = event.target; //event.target에서 name과 value만 가져오기
    setBoard({
      ...board,
      [name]: value,
    });
  };

  const saveBoard = async () => {
    await axios.post(`/board`, board).then((res) => {
      alert('등록되었습니다.');
      navigate('/board');
    });
  };

  const backToList = () => {
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
          <input
            type="text"
            name="title"
            value={title}
            onChange={onChange}
            placeholder='제목을 입력하세요'
            className='form-control' />
        </div>
        <div className='form-group'>
          <span>작성자</span>
          <input
            type="text"
            name="createdBy"
            value={createdBy}
            onChange={onChange}
            placeholder='작성자를 입력하세요'
            className='form-control'
          />
        </div>
        <div className='form-group'>
          <span>내용</span>
          <textarea
            name="contents"
            cols="30"
            rows="10"
            value={contents}
            onChange={onChange}
            placeholder='내용을 입력하세요'
            className='form-control'
          ></textarea>
        </div>
        <div className='button-container'>
          <button className='btn btn-primary' onClick={saveBoard}>저장</button>
          <button className='btn btn-primary' onClick={backToList}>취소</button>
        </div>
      </div>

    </div>
  );
};

export default BoardWrite;