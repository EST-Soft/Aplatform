import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link, useNavigate } from 'react-router-dom';
import SearchBar from '../components/SearchBar';

const BoardList = () => {
  //페이지 이동 객체 선언
  const navigate = useNavigate();
  //변수 선언
  const [boardList, setBoardList] = useState([]);   //없다면 []을 기본값으로
  const [pageList, setPageList] = useState([]);

  const [curPage, setCurPage] = useState(0); //현재 페이지 세팅
  const [prevBlock, setPrevBlock] = useState(0); //이전 페이지 블록
  const [nextBlock, setNextBlock] = useState(0); //다음 페이지 블록
  const [lastPage, setLastPage] = useState(0); //마지막 페이지

  const [search, setSearch] = useState({
    page: 1,
    sk: '',
    sv: '',
  });

  const getBoardList = async () => {
    // debugger;
    if (search.page === curPage) return; //현재 페이지와 누른 페이지가 같으면 return
    //검색어 쿼리 문자열로 변경
    const queryString = Object.entries(search)
      .map((e) => e.join('=')) //각각 요소를 a=1 형태로 조인
      .join('&');             //a=1&b=1 로 다시 조인

    const resp = await (
      await axios.get('/board?' + queryString)
    ).data; // 2) 게시글 목록 데이터에 할당

    setBoardList(resp.data || []); // 3) 게시글 결과값 boardList 변수에 할당 
    const pngn = resp.pagination;

    const { endPage, nextBlock, prevBlock, startPage, totalPageCnt } = pngn;
    //페이징 셋팅 
    setCurPage(search.page);    //현재페이지
    setPrevBlock(prevBlock);    //이전버튼
    setNextBlock(nextBlock);    //다음버튼
    setLastPage(totalPageCnt);   //총 페이지
    //페이지 변수
    const tmpPages = [];
    for (let i = startPage; i <= endPage; i++) {
      tmpPages.push(i);
    }
    //페이지 정보 셋팅
    setPageList(tmpPages);
  };
  //글쓰기 눌렀을 때 쓰기 화면으로 이동
  const moveToWrite = () => {
    navigate('/write');
  };
  //클릭 이벤트
  const onClick = (event) => {  //event.target에서 name과 value만 가져오기
    let value = event.target.value;
    setSearch({
      ...search,
      page: value,
    });

    getBoardList();
  };
  //변경 이벤트
  const onChange = (event) => {
    const { value, name } = event.target;
    setSearch({
      ...search,
      [name]: value,
    });
  };
  //검색함수
  const onSearch = () => {
    //검색어 없을 시 
    if (search.sk === '') {
      alert('검색조건을 선택해주세요!');
      return;
    } else {
      //if (search.sk !== '' && search.sv !== '') {
      setSearch({
        ...search,
        page: 1,
      });
      setCurPage(0);
      getBoardList();
      //}
    }

  };

  useEffect(() => {
    getBoardList(); // 1) 게시글 목록 조회 함수 호출
  }, [search]);

  return (
    <div>
      <header className="header">
        <h1>일반게시판</h1>
      </header>
      <div>
        <div className="d-flex justify-content-end">
          <a className="btn btn-primary" onClick={moveToWrite}>글쓰기</a>
        </div>
        <div className="container py-4">
          <table className="post-table">
            <thead>
              <tr>
                <th>제목</th>
                <th>작성자</th>
                <th>등록일</th>
                {/* <th>조회수</th>
            <th>댓글수</th> */}
              </tr>
            </thead>
            <tbody>
              {boardList.map((board) => (
                // 4) map 함수로 데이터 출력
                <tr key={board.idx}>
                  <td><Link to={`/board/${board.idx}`}>{board.title}</Link></td>
                  <td>{board.createdBy}</td>
                  <td>{board.createdAt.slice(0, 10)}</td>
                  {/* <td>{board.createdBy}</td>
              <td>{board.createdBy}</td> */}
                </tr>
              ))}
            </tbody>
          </table>
          <div className="pagination-wrapper">
            <ul>
              <button onClick={onClick} value={1}>
                &lt;&lt;
              </button>
              <button onClick={onClick} value={prevBlock}>
                &lt;
              </button>
              {pageList.map((page, index) => (
                <button key={index} onClick={onClick} value={page}>
                  {page}
                </button>
              ))}
              <button onClick={onClick} value={nextBlock}>
                &gt;
              </button>
              <button onClick={onClick} value={lastPage}>
                &gt;&gt;
              </button>
            </ul>
          </div>
          <br />
          <div className="search-bar">
            {/* 검색 부분 컴포넌트로 대체 */}
            <SearchBar onChange={onChange} onSearch={onSearch} />
          </div>
          <br />
        </div>
      </div>
    </div>
  );
};

export default BoardList;