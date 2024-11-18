import React from 'react';
import styled from 'styled-components';
const SortControll = styled.div`
  display: flex;
  align-items: center;
`;



const UserSortControls = ({ onChange, goSearch }) => {
    const handleSelectChange =(e) =>{
        const value = e.target.value;
        onChange(e);
         // 1초 뒤에 goSearch 호출
    setTimeout(() => {
        console.log("goSearch 호출됨");
        goSearch();
      }, 200);
    }
    
      return (
        <SortControll>
          <select name='sortKey' onChange={handleSelectChange}>
            <option value="">-선택-</option>
            <option value="report_count">신고회수순</option>
            <option value="user.insrt_dtm-DESC">회원가입 최근순</option>
            <option value="user.insrt_dtm-ASC">회원가입 과거순</option>
            <option value="user.mbr_id">아이디 순</option>
            <option value="user.mbr_name">이름 순</option>
          </select>
        </SortControll>
      );
    };
export default UserSortControls;