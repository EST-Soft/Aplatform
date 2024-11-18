import React, { useEffect, useState } from "react";
import axios from "axios";
import { Link, useNavigate } from "react-router-dom";
import SearchBar from "../components/UserSearchBar";
import styled from "styled-components";
import { useModal } from "../components/ModalProvider";
import './UserManagement.css';
import UserSortControls from "../components/UserSortControls";


const Section = styled.div`
  margin: 20px auto;

  button {
    margin: 2px;
    padding: 4px 8px; // 패딩을 줄여서 버튼 크기 조절
    font-size: 0.875rem; // 글자 크기 줄이기
  }
`;


const UserManagement = () => {
  const navigate = useNavigate();
  const [userList, setUserList] = useState([]);
  const [pageList, setPageList] = useState([]);
  const [curPage, setCurPage] = useState(0);
  const [prevBlock, setPrevBlock] = useState(0);
  const [nextBlock, setNextBlock] = useState(0);
  const [lastPage, setLastPage] = useState(0);
  const [search, setSearch] = useState({ page: 1, sk: "", sv: "", sortKey:""});
  const { showModal } = useModal();
  
  const getUserList = async () => {
    console.log('user 조회 시작', search.page);
    if (search.page === curPage) return;
    const queryString = Object.entries(search)
      .map((e) => e.join("="))
      .join("&");
    const resp = await axios
      .get("/user?" + queryString)
      .then((res) => res.data);
    setUserList(resp.data || []);
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

    console.log(resp.data);
  };

  const onClick = (event) => {
    let value = event.target.value;
    setSearch({ ...search, page: value });
    getUserList();
  };

  const onChange = (event) => {
    const { value, name } = event.target;
    setSearch({ ...search, [name]: value });
  };

  const onSearch = () => {
    if (search.sk === "") {
      // showModal("검색조건을 선택해주세요!");
      return;
    }else {
      setSearch({ ...search, page: 1 });
      setCurPage(0);
      getUserList();
      
    }
    
  };
  const goSearch = () => {
    setSearch((prevState) => ({
      ...prevState,  // 기존 값은 그대로 두고
      page: 1        // page만 1로 초기화
    }));
    setCurPage(0);
  };
  


  useEffect(() => {
    console.log("Updated search state:", search);
    getUserList();
  }, [search]);



  return (
    <section class="card">
      <header class="card-header">
        <div class="card-actions">
          <a href="#" class="card-action card-action-toggle" data-card-toggle=""></a>
          <a href="#" class="card-action card-action-dismiss" data-card-dismiss=""></a>
        </div>

        <h2 class="card-title">개인회원 관리</h2>
      </header>
      <div class="card-body">
        <div class="table-responsive">
            <UserSortControls  onChange={onChange} goSearch={goSearch}/>
          <table class="table table-responsive-lg table-bordered table-striped table-sm mb-0">
            <thead>
              
              <tr>
                <th class="userData">순번</th>
                <th class="userData">성별</th>
                <th class="userData">ID</th>
                <th class="userData">이름</th>
                <th class="userData">휴대폰</th>
                <th class="userData">생년월일</th>
                <th class="userData">Email</th>
                <th class="userData">개인정보 동의여부</th>
                <th class="userData">포지션제안 수락여부</th>
                <th class="userData">가입일</th>
                <th class="userData">사용여부</th>
                <th class="userData">재재여부</th>
                <th class="userData">신고횟수</th>
              </tr>
            </thead>
            <tbody>
              {userList.map((u) =>(
                
              <tr key = {u.idx}>
                <td class="userData">{u.idx}</td>
                <td class="userData">{u.gender}</td>
                <td class="userData">{u.id}</td>
                <td class="userData">{u.name}</td>
                <td class="userData">{u.phone}</td>
                <td class="userData">{u.birth}</td>
                <td class="userData">{u.email}</td>
                <td class="userData">{u.privacy_yn}</td>
                <td class="userData">{u.position_yn}</td>
                <td class="userData">{u.joinDate}</td>
                <td class="userData">{u.use_yn}</td>
                <td class="userData">재재여부</td>
                <td class="userData">{u.report_count}</td>
                
              </tr>
              ))}

            </tbody>
          </table>

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
          

          
        </div>
      </div>
    </section>
  );
};

export default UserManagement;
