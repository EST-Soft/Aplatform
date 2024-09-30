import React, { useState } from "react";
import { Link } from "react-router-dom";
import AdminSidebar from "../admin/AdminSidebar";

const JobPosting = () => {
  const [userType, setUserType] = useState("user");
  const [searchQuery, setSearchQuery] = useState("");

  const handleSearchChange = (e) => {
    setSearchQuery(e.target.value);
  };

  const handleSearchSubmit = (e) => {
    e.preventDefault();
    // 검색 기능 구현
    console.log("검색어:", searchQuery);
  };

  return (
    <div className="container">
      <div className="d-flex">

        <AdminSidebar />

        <main className="flex-grow-1 ms-3">


            <div>
                <span style={{fontSize:'20px' , fontWeight:'bold'}}>공고 관리</span>
            </div>

            <hr
                style={{
                margin: "1rem 0",
                height: "6px", 
                border: "none", 
                background: "linear-gradient(to right, skyblue 40%, #ccc 50%)", 
                }}
            />

          <form className="d-flex mb-3" onSubmit={handleSearchSubmit}>
            <select className="form-select me-2" aria-label="Search Options" style={{width:'150px', textAlign:'center'}}>
                <option value="">-선택-</option>
                <option value="">글쓴이</option>
                <option value="">내용</option>
            </select>


            <input
              className="form-control me-2"
              type="search"
              placeholder="검색어를 입력하세요"
              aria-label="Search"
              value={searchQuery}  
              onChange={handleSearchChange}
            />
            
            <button className="btn btn-outline-success" type="submit" style={{color:'white', background:'skyblue',border:'none'}}>
              검색
            </button>
          </form>


          <div className="table-responsive">
            <table className="table">
              <thead>
                <tr>
                  <th scope="col">번호</th>
                  <th scope="col">제목</th>
                  <th scope="col">작성자</th>
                  <th scope="col">작성 날짜</th>
                  <th scope="col">컬럼 26개다..</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>1</td>
                  <td><Link to="/board/job/1">첫 번째 게시글</Link></td>
                  <td>홍길동</td>
                  <td>2024-09-19</td>
                  <td>100</td>
                </tr>
                <tr>
                  <td>2</td>
                  <td><Link to="/board/job/2">두 번째 게시글</Link></td>
                  <td>김철수</td>
                  <td>2024-09-18</td>
                  <td>200</td>
                </tr>
              </tbody>
            </table>
          </div>
        </main>
      </div>
    </div>
  );
};

export default JobPosting;
