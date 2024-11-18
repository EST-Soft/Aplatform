import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import Board from './Board';

const BoardDetail = () => {
  const { idx } = useParams(); // /board/:idx와 동일한 변수명으로 데이터를 꺼낼 수 있습니다.
  const [loading, setLoading] = useState(true);
  const [board, setBoard] = useState({});
  const getBoard = async () => {
    const resp = await axios.get(`/boyard/${idx}`).data;
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
          idx={Board.idx}
          title={Board.title}
          contents={Board.contents}
          createdBy={Board.createdBy}
          createdAt={Board.createdAt}
          // hits={Board.brdHits}
        />
      )}
    </div>
  );
};

export default BoardDetail;