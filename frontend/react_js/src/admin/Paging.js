import React from "react";
import Pagination from "react-js-pagination";
import styles from './Paging.module.css';

const Paging = ({ activePage, itemsCountPerPage, totalItemsCount, pageRangeDisplayed, onChange }) => {
  return (

    <div className={styles.pagination}>
      <Pagination
        activePage={activePage} // 현재 페이지
        itemsCountPerPage={itemsCountPerPage} // 한 페이지에 보여줄 아이템 갯수
        totalItemsCount={totalItemsCount} // 총 아이템 갯수
        pageRangeDisplayed={pageRangeDisplayed} // paginator의 페이지 범위
        prevPageText={"‹"} // "이전"을 나타낼 텍스트
        nextPageText={"›"} // "다음"을 나타낼 텍스트
        onChange={onChange} // 페이지 변경을 핸들링하는 함수
        itemClass={styles.pageItem} // 페이지 아이템 클래스
        linkClass={styles.pageLink} // 링크 클래스
      />
    </div>
    
  );
};

export default Paging;
