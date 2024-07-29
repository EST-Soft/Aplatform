import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import axios from 'axios';

const BoardUpdate = () => {
  const navigate = useNavigate();
  const { idx } = useParams(); // /update/:idx와 동일한 변수명으로 데이터를 꺼낼 수 있습니다.
  const [board, setBoard] = useState({
    idx: 0,
    title: '',
    createdBy: '',
    createdAt: '',
    contents: '',
  });

  const { title, createdBy, createdAt, contents } = board; //비구조화 할당

  const onChange = (event) => {
    const { value, name } = event.target; //event.target에서 name과 value만 가져오기
    setBoard({
      ...board,
      [name]: value,
    });
  };
  //게시물 정보 셋팅 함수
  const getBoard = async () => {
    const resp = await (await axios.get(`/board/${idx}`)).data;
    setBoard(resp.data);
  };
  //게시물 수정 함수
  const updateBoard = async () => {
    await axios.patch(`/board`, board).then((res) => {
      alert('수정되었습니다.');
      navigate('/board/' + idx);
    });
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
  //게시물 상세보기 로 이동
  const backToDetail = () => {
    navigate('/board/' + idx);
  };

  useEffect(() => {
    getBoard();
  }, []);

  return (
    <div className='write-post-container'>
      <header className="header">
        <h1>일반게시판</h1>
      </header>
      <div className='form-container'>
        <div className='form-group'>
          <span>제목</span>
          <input type="text" name="title" value={title} onChange={onChange} className='form-control' />
          <div className="post-meta">
            <span>작성자: {createdBy}</span>
            <span>등록일: {createdAt.slice(0, 10)}</span>
            <span>조회수: 24</span>
          </div>
        </div>
        <div className='form-group'>
          <span>내용</span>
          <textarea
            name="contents"
            cols="30"
            rows="10"
            value={contents}
            onChange={onChange}
            className='form-control'
          ></textarea>
        </div>
        <br />
        <div className='button-container'>
          <button className='btn btn-primary' onClick={updateBoard}>수정</button>
          <button className='btn btn-primary' onClick={deleteBoard}>삭제</button>
          <button className='btn btn-primary' onClick={backToDetail}>취소</button>
        </div>
      </div>
    </div>
  );
};

export default BoardUpdate;