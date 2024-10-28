import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import Board from './Board';

const BoardDetail = () => {
  const { idx } = useParams(); // /board/:idx와 동일한 변수명으로 데이터를 꺼낼 수 있습니다.
  const [loading, setLoading] = useState(true);
  const [board, setBoard] = useState({});
  const getBoard = async () => {
    const resp = await (await axios.get(`/board/${idx}`)).data;
    console.log("디테일 데이터", resp)
    setBoard(resp);
    setLoading(false);
  };

  useEffect(() => {
    getBoard();
  }, []);

  return (
    <div>
      {loading ? (
        <h2>loading...</h2>
      ) : (
        <Board
          idx={board.brdSq}
          title={board.brdTtl}
          contents={board.contents}
          createdBy={board.createdBy}
          createdAt={board.insrtDtm}
          hits={board.brdHits}
        />
      )}
    </div>
  );
};

export default BoardDetail;