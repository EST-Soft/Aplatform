import React from 'react';

const SearchBar = ({ onChange, onSearch }) => {
    return (
        <div>
            <select name='sk' className="search-select" onChange={onChange}>
                <option value="">-선택-</option>
                <option value="title">제목</option>
                <option value="contents">내용</option>
            </select>
            <input type="text" name="sv" id="" className="search-input" onChange={onChange} />
            <button className="btn btn-primary" onClick={onSearch}>검색</button>
        </div>
    );
};

export default SearchBar;
