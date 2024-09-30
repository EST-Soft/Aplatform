import React, { useState } from "react";
import { Link } from "react-router-dom";
import AdminSidebar from "./AdminSidebar";

const JobPostingView = () => {
  const [userType, setUserType] = useState("user");
  
  return (
    <div className="container">
      <div className="d-flex">

        <AdminSidebar />

        <main className="flex-grow-1 ms-3">


            <div>
                <span style={{fontSize:'20px' , fontWeight:'bold'}}>공고 관리 상세</span>
            </div>

            <hr
                style={{
                margin: "1rem 0",
                height: "6px", 
                border: "none", 
                background: "linear-gradient(to right, skyblue 40%, #ccc 50%)", 
                }}
            />

          

<div className="table-responsive">
  <table className="table">
    <thead>
      <tr>
        <th scope="col">항목</th>
        <th scope="col">게시글 1</th>
       
      </tr>
    </thead>
    <tbody>
      <tr>
        <td>번호</td>
        <td>1</td>
      
      </tr>
      <tr>
        <td>제목</td>
        <td><Link to="/board/job/1">첫 번째 게시글</Link></td>
        
      </tr>
      <tr>
        <td>작성자</td>
        <td>홍길동</td>
       
      </tr>
      <tr>
        <td>작성 날짜</td>
        <td>2024-09-19</td>
     
      </tr>
      <tr>
        <td>컬럼 26개다..</td>
        <td>100</td>
       
      </tr>
    </tbody>
  </table>
</div>

        </main>
      </div>
    </div>
  );
};

export default JobPostingView;
