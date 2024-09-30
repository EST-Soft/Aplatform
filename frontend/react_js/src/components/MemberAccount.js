import React, { useState } from "react";
import { Link } from "react-router-dom";
import AdminSidebar from "../admin/AdminSidebar";
import List from '../admin/List';

const MemberAccount = () => {
  const [userType, setUserType] = useState("memberAccount"); // 초기값을 memberAccount로 설정
  const [searchQuery, setSearchQuery] = useState("");

  const handleSearchChange = (e) => {
    setSearchQuery(e.target.value);
  };

  const handleSearchSubmit = (e) => {
    e.preventDefault();
    console.log("검색어:", searchQuery);
  };

  const dataMapping = {
    memberAccount: [
      { name: '홍길동', id: 'id123' },
      { name: '김철수', id: 'id456' },
    ],
    JobPosting: [
      { title: '첫 번째 게시글', author: '홍길동', date: '2024-09-19' },
      { title: '두 번째 게시글', author: '김철수', date: '2024-09-18' },
    ],
    user: [
      { username: 'user1', email: 'user1@example.com', role: 'Admin' },
    ],
  };

  const data = dataMapping[userType];

  return (
    <div className="container">
      <div className="d-flex">
        <AdminSidebar />
        <main className="flex-grow-1 ms-3">
          <div>
            <span style={{ fontSize: '20px', fontWeight: 'bold' }}>회원 관리</span>
          </div>
          <hr
            style={{
              margin: "1rem 0",
              height: "6px",
              border: "none",
              background: "linear-gradient(to right, skyblue 40%, #ccc 50%)",
            }}
          />

          
          <List columns={null} data={data} userType={userType} />


        </main>
      </div>
    </div>
  );
};

export default MemberAccount;
