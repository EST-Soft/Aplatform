import React, { useEffect, useState } from "react";
import axios from "axios";
import { Link, useNavigate } from "react-router-dom";
import SearchBar from "../components/SearchBar";
import styled from "styled-components";
import { useModal } from "../components/ModalProvider";

const Section = styled.div`
  margin: 20px auto;

  button {
    margin: 2px;
    padding: 4px 8px; // 패딩을 줄여서 버튼 크기 조절
    font-size: 0.875rem; // 글자 크기 줄이기
  }
`;

const Container = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

const Table = styled.table`
  width: 100%;
  border-collapse: collapse;
  tbody,
  td,
  tfoot,
  th,
  thead,
  tr {
    border-style: double;
    border-width: 3px;
  }
  thead th {
    background-color: #343a406e !important;
  }
  th,
  td {
    padding: 10px;
    text-align: center;
  }

  thead th {
    background-color: #343a40;
    color: white;
  }

  tbody tr:nth-child(odd) {
    background-color: #f9f9f9;
  }

  tbody tr:hover {
    background-color: #f1f1f1;
  }

  td:first-child {
    width: 70%;
  }

  td:nth-child(2),
  td:nth-child(3) {
    width: 15%;
  }

  a {
    color: #007bff;
    text-decoration: none;

    &:hover {
      text-decoration: underline;
    }
  }
`;

const BoardList = () => {
  const navigate = useNavigate();
  const [boardList, setBoardList] = useState([]);
  const [pageList, setPageList] = useState([]);
  const [curPage, setCurPage] = useState(0);
  const [prevBlock, setPrevBlock] = useState(0);
  const [nextBlock, setNextBlock] = useState(0);
  const [lastPage, setLastPage] = useState(0);
  const [search, setSearch] = useState({ page: 1, sk: "", sv: "" });
  const { showModal } = useModal();

  const getBoardList = async () => {
    console.log("서치", search.page);
    if (search.page === curPage) return;
    const queryString = Object.entries(search)
      .map((e) => e.join("="))
      .join("&"); 
      
    const resp = await axios
      .get("/board?" + queryString)
      .then((res) => res.data);
    setBoardList(resp.data || []);
    const { endPage, nextBlock, prevBlock, startPage, totalPageCnt } =
      resp.pagination;
    console.log(resp.pagination);
    setCurPage(search.page);
    setPrevBlock(prevBlock);
    setNextBlock(nextBlock);
    setLastPage(totalPageCnt);
    const tmpPages = [];
    for (let i = startPage; i <= endPage; i++) {
      tmpPages.push(i);
    }
    console.log("tempages", tmpPages);
    setPageList(tmpPages);
  };

  const moveToWrite = () => {
    navigate("/write");
  };

  const onClick = (event) => {
    let value = event.target.value;
    setSearch({ ...search, page: value });
    getBoardList();
  };

  const onChange = (event) => {
    const { value, name } = event.target;
    setSearch({ ...search, [name]: value });
  };

  const onSearch = () => {
    if (search.sk === "") {
      showModal("검색조건을 선택해주세요!");
      return;
    } else {
      setSearch({ ...search, page: 1 });
      setCurPage(0);
      getBoardList();
    }
  };

  useEffect(() => {
    getBoardList();
  }, [search]);

  return (
    <div>
      <header className="header">
        <h1>일반게시판</h1>
      </header>
      <div>
        <div className="d-flex justify-content-end">
          <button className="btn btn-primary" onClick={moveToWrite}>
            글쓰기
          </button>
        </div>
        <Container className="container py-4">
          <Table className="table table-hover table-striped">
            <thead>
              <tr>
                <th>제목</th>
                <th>작성자</th>
                <th>등록일</th>
              </tr>
            </thead>
            <tbody>
              {boardList.map((board) => (
                <tr key={board.idx}>
                  <td>
                    <Link to={`/board/${board.idx}`}>{board.title}</Link>
                  </td>
                  <td>{board.createdBy}</td>
                  <td>{board.createdAt.slice(0, 10)}</td>
                </tr>
              ))}
            </tbody>
          </Table>
          <Section className="pagination-wrapper">
            <ul>
              <button onClick={onClick} value={1} className="btn btn-primary">
                &lt;&lt;
              </button>
              <button
                onClick={onClick}
                value={prevBlock}
                className="btn btn-primary"
              >
                &lt;
              </button>
              {pageList.map((page, index) => (
                <button
                  key={index}
                  onClick={onClick}
                  value={page}
                  className="btn btn-primary"
                >
                  {page}
                </button>
              ))}
              <button
                onClick={onClick}
                value={nextBlock}
                className="btn btn-primary"
              >
                &gt;
              </button>
              <button
                onClick={onClick}
                value={lastPage}
                className="btn btn-primary"
              >
                &gt;&gt;
              </button>
            </ul>
          </Section>
          <br />
          <div className="search-bar">
            <SearchBar onChange={onChange} onSearch={onSearch} />
          </div>
          <br />
        </Container>
      </div>
    </div>
  );
};

export default BoardList;
