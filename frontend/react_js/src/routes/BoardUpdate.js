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
  //게시물 상세보기 로 이동
  const backToDetail = () => {
    navigate('/board/' + idx);
  };

  useEffect(() => {
    getBoard();
  }, []);

  return (
    <div>
      <div>
        <span>제목</span>
        <input type="text" name="title" value={title} onChange={onChange} />
      </div>
      <br />
      <div>
        <span>작성자</span>
        <input type="text" name="createdBy" value={createdBy} readOnly={true} />
      </div>
      <br />
      <div>
        <span>내용</span>
        <textarea
          name="contents"
          cols="30"
          rows="10"
          value={contents}
          onChange={onChange}
        ></textarea>
      </div>
      <br />
      <div>
        <button onClick={updateBoard}>수정</button>
        <button onClick={backToDetail}>취소</button>
      </div>
    </div>
  );
};

export default BoardUpdate;