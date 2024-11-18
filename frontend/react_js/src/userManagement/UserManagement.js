import React, { useRef,useEffect, useState } from "react";
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
  const { showModal,closeModal } = useModal(); // 모달을 띄우는 함수 가져오기
  //재재하기 form 다루기
  // const initialPunishData = { reason: "잦은 비방과 욕설", content: "댓글 금지", days: 3 };
  // const [punishData, setPunishData] = useState(initialPunishData);
  const reasonRef = useRef(null);
  const contentRef = useRef(null);
  const daysRef = useRef(null);



  // 확장된 행을 추적하는 상태
  const [expandedRow, setExpandedRow] = useState(null);

  //회원별 신고당한 게시글 데이터 (GET 요청으로 받은 데이터)
  const [extraData, setExtraData] = useState([]);
  //신고된 게시글로이동
  const handleButtonClick = (brd_sq , board_type) =>{
    console.log("brd_sq:", brd_sq); // brd_sq 값 확인
    window.location.href = `http://localhost:8080/board/${board_type}/${brd_sq}`;
  }
  //모달창에서 제제하기 버튼 클릭시 회원 재제
  const handlePunish = async(mbr_sq,event ) => {
    event.preventDefault();
    // ref를 통해 각 필드의 값 접근
    const punishData = {
      reason: reasonRef.current.value,
      content: contentRef.current.value,
      days: daysRef.current.value,
      mbr_sq : mbr_sq
    };
    console.log("tjalt!" , mbr_sq);
    console.log("써밋!" ,punishData);
    axios
      .post(`/user/punish`,punishData)
      .then(async(res) =>{
        console.log(res.data.data);
      })
      .catch((err) =>{
        alert(err);
      })
    // try {
    //   const response = await axios.post(`/user/punish?mbr_sq=${mbr_sq}`);
    //   alert(response.data.data);
    // } catch (error) {
    //   alert(error.response?.data?.data || '알 수 없는 오류가 발생했습니다.');
    // }
  };
  

  // 클릭 시 확장된 행을 토글하는 함수
  const handleRowClick = async (idx) => {
    // 확장된 행을 토글
    if (expandedRow === idx) {
      setExpandedRow(null); // 이미 확장된 경우 접기
    } else {
      setExpandedRow(idx); // 새로 클릭한 행을 확장
     
      try {
        // GET 요청 URL 
        const response = await axios
        .get(`/user/report?mbr_id=${idx}`)
        .then((res) => res.data);
        setExtraData(response.data|| []);
      console.log(extraData);
      } catch (error) {
        console.error('Error fetching extra data:', error);
        // 오류 처리 (선택 사항)
        setExtraData([]);
      }
    }

  };

  // 제제하기모달창 오픈
  const handleClick=(mbr_sq , mbr_id,mbr_name) =>{
    const data = {mbr_sq , mbr_id,mbr_name };
    showModal
    (
      "재재하기",
      <ModalContent data={data}/> );
  }
  // 제제하기모달창 닫기
  const handleCloes = (event)=>{
    closeModal();
    
  }
  // 모든유저 불러오기
  const getUserList = async () => {
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
    setCurPage(search.page);
    setPrevBlock(prevBlock);
    setNextBlock(nextBlock);
    setLastPage(totalPageCnt);
    const tmpPages = [];
    for (let i = startPage; i <= endPage; i++) {
      tmpPages.push(i);
    }
    setPageList(tmpPages);

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

  // 검색기능
  const onSearch = () => {
    if (search.sk === "") {
     showModal("검색조건을 선택해주세요!");
      return;
    }else {
      setSearch({ ...search, page: 1 });
      setCurPage(0);
      getUserList();
      
    }
    
  };
  // sorting기능을위한 search
  const goSearch = () => {
    setSearch((prevState) => ({
      ...prevState,  // 기존 값은 그대로 두고
      page: 1        // page만 1로 초기화
    }));
    setCurPage(0);
  };

  //search useEffect
  useEffect(() => {
    getUserList();
  }, [search]);
  
  const ModalContent = ({ data }) => {
    return (
      <div>
        <form onSubmit={(event) => handlePunish(data.mbr_sq, event)}>
          <input type="hidden" value={data.mbr_sq}/>
          <p>아이디 &nbsp; : &nbsp;<strong>{data.mbr_id}</strong> </p>
          <p>이름 &nbsp;:&nbsp; <strong>{data.mbr_name}</strong> </p>
          <p>사유 &nbsp;:&nbsp; 
            <select name="reason" ref={reasonRef}>
              <option value="잦은 비방과 욕설">잦은 비방과 욕설</option>
              <option value="신고누적">신고누적</option>
              <option value="타인에게 불쾌감을 주는 행동">타인에게 불쾌감을 주는 행동</option>
              <option value="광고성 게시글">광고성 게시글</option>
            </select> </p>
        <p>재재내용 &nbsp;:&nbsp; 
          <select name="content" ref={contentRef}>
          <option value="댓글 금지">댓글금지</option>
          <option value="게시판 글쓰기 금지">게시판 글쓰기 금지</option>
          <option value="로그인정지">로그인정지</option>
          <option value="영구정지">영구정지</option>
        </select> </p>
        <p>재재내용 &nbsp;:&nbsp; 
          <select name="days" ref={daysRef}>
          <option value='3'>3일</option>
          <option value='5'>5일</option>
          <option value='10'>10일</option>
          <option value='15'>15일</option>
        </select> </p>
        <button type="submit"> 재재</button> 
        <button type="button" onClick={handleCloes}> 닫기</button>
        </form>
      </div>
    );
  };

  return (
    <section className="card">
      <header className="card-header">
        <div className="card-actions">
          <a href="#" className="card-action card-action-toggle" data-card-toggle=""></a>
          <a href="#" className="card-action card-action-dismiss" data-card-dismiss=""></a>
        </div>

        <h2 className="card-title">개인회원 관리</h2>
      </header>
      <div className="card-body">
        <div className="table-responsive">
            <UserSortControls  onChange={onChange} goSearch={goSearch}/>
          <table className="table table-responsive-lg table-bordered table-striped table-sm mb-0">
            <thead>
              
              <tr>
                <th className="userData">순번</th>
                <th className="userData">성별</th>
                <th className="userData">ID</th>
                <th className="userData">이름</th>
                <th className="userData">휴대폰</th>
                <th className="userData">생년월일</th>
                <th className="userData">Email</th>
                <th className="userData">개인정보<br/> 동의여부</th>
                <th className="userData">포지션제안<br/> 수락여부</th>
                <th className="userData">가입일</th>
                <th className="userData">사용여부</th>
                <th className="userData">재재여부</th>
                <th className="userData">신고횟수</th>
                <th className="userData"> 재재하기</th>
              </tr>
            </thead>
            <tbody>
            {userList.map((u) => (
          <React.Fragment key={u.idx}>
            {/* 메인 행 */}
            <tr onClick={() => handleRowClick(u.idx)}>
              <td className="userData">{u.idx}</td>
              <td className="userData">{u.gender}</td>
              <td className="userData">{u.id}</td>
              <td className="userData">{u.name}</td>
              <td className="userData">{u.phone}</td>
              <td className="userData">{u.birth}</td>
              <td className="userData">{u.email}</td>
              <td className="userData">{u.privacy_yn}</td>
              <td className="userData">{u.position_yn}</td>
              <td className="userData">{u.joinDate}</td>
              <td className="userData">{u.use_yn}</td>
              <td className="userData">재재여부</td>
              <td className="userData">{u.report_count}</td>
              <td className="userData">
              <button className="btn btn-primary" onClick={() => handleClick(u.idx, u.id, u.name)}>
                  재재
                </button>
                
              </td>
              
            </tr>

            {/* 확장된 행 아래에 추가 데이터 표시 */}
            {expandedRow === u.idx && (
              <tr>
                <td colSpan="13" style={{ backgroundColor: 'white' }}>
                  {/* 추가된 테이블 */}
                  <table border="1" style={{ width: '100%', borderCollapse: 'collapse',backgroundColor: 'white' }}>
                    <thead>
                      <tr style={{ backgroundColor: '#f2f2f2' }}>
                        <th>순번</th>
                        <th>신고자</th>
                        <th>신고내용</th>
                        <th>사유</th>
                        <th>신고일</th>
                        <th>블라인드하기</th>
                      </tr>
                    </thead>
                    <tbody>
                      {extraData.map((report) => (
                        <tr key={report.idx}>
                          <td>{report.idx}
                          </td>
                          <td>{report.reporter_mbr}</td>
                          <td>{report.content}<br/>
                          <button className="btn btn-primary" onClick={() => handleButtonClick(report.brd_sq , report.board_type)}>해당게시물로이동</button></td>
                          <td>{report.reason}</td>
                          <td>{report.report_date}</td>
                          <td>
                            
                            <button className="btn btn-secondary">블라인트처리</button></td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                </td>
              </tr>
            )}
          </React.Fragment>
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
}
  

export default UserManagement;
