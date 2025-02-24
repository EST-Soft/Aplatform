import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { useModal } from "../components/ModalProvider";

const SearchComponent = ({ onSearchResult }) => {
  const [searchTerm, setSearchTerm] = useState("");
  const [searchField, setSearchField] = useState("jbpTl_jbpCntnt");
  const [searchTermError, setSearchTermError] = useState(false);
  const navigate = useNavigate();
  const { showModal } = useModal();
  const performSearch = async () => {
    if (searchTerm === "") {
      setSearchTermError(true);
      showModal("검색어를 입력해 주세요");
      return;
    } else {
      setSearchTermError(false);
    }

    try {
      const response = await axios.get("/board/search", {
        params: {
          searchTerm: searchTerm,
          searchField: searchField,
        },
      });
      console.log("Search response:", response);
      onSearchResult(response.data);

      // 쿼리스트링을 주소에 추가
      navigate({
        pathname: navigate.location.pathname,
        search: `?searchTerm=${searchTerm}&searchField=${searchField}`,
      });
    } catch (error) {
      console.error("Error searching:", error);
    }
  };

  return (
    <div className="search-area">
      <select
        value={searchField}
        onChange={(e) => setSearchField(e.target.value)}
      >
        <option value="jbpTl_jbpCntnt">제목+내용</option>
        <option value="jbpTl">제목</option>
        <option value="jbpCntnt">내용</option>
      </select>
      <input
        type="search"
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)}
        onKeyDown={(e) => e.key === "Enter" && performSearch()}
        placeholder="검색어를 입력하세요"
        className={searchTermError ? "shake" : ""}
      />
      <button onClick={performSearch} className="btn btn-primary">
        검색
      </button>
      {/* {searchTermError && <p className="error-message">검색어를 입력해주세요.</p>} */}
    </div>
  );
};

export default SearchComponent;
