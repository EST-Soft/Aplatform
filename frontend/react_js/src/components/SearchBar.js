import React from 'react';
import styled from 'styled-components';

const SearchContainer = styled.div`
  display: flex;
  align-items: center;
  
  .search-select,
  .search-input,
  .btn-primary {
    margin-right: 10px; // 오른쪽에 간격 추가
  }

  .search-input {
    flex: 1; // 필요한 경우 입력 필드의 너비를 늘릴 수 있습니다.
  }
`;

const SearchBar = ({ onChange, onSearch }) => {
    return (
        <SearchContainer>
            <select name='sk' className="search-select" onChange={onChange}>
                <option value="">-선택-</option>
                <option value="title">제목</option>
                <option value="contents">내용</option>
            </select>
            <input type="text" name="sv" id="" className="search-input" onKeyDown={e => e.key === 'Enter' && onSearch()} onChange={onChange} placeholder='검색어를 입력하세요'/>
            <button className="btn btn-primary" onClick={onSearch}>검색</button>
        </SearchContainer>
    );
};

export default SearchBar;
