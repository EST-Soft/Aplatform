import React, { useState, useEffect } from 'react';
// import JoinCompanyCondition from '@/components/fo/user/mypage/JoinCompanyCondition';
// import ViewPaginate from '@/components/fo/user/common/ViewPaginate';
import axios from 'axios';
import './ApplyCompanyList.css';

const ApplyCompanyList = () => {
  const [applyInfo, setApplyInfo] = useState([]);
  const [pageCount, setPageCount] = useState(0);
  const [currentPage, setCurrentPage] = useState(1);
  const [select, setSelect] = useState('전체');
  const [totalApplyCount, setTotalApplyCount] = useState(0);
  const [cancelApplyCnt, setCancelApplyCnt] = useState(0);
  const [resumeReadCnt, setResumeReadCnt] = useState(0);
  const [resumeNotReadCnt, setResumeNotReadCnt] = useState(0);
  const [searchType, setSearchType] = useState('');
  const [search, setSearch] = useState('');

  const itemsPerPage = 5;
  const mbrSq = 1;

  useEffect(() => {
    // 주석 처리된 데이터 불러오기 코드
    // axios.post('http://localhost:80/mypage/getApplyList', { mbrSq })
    //   .then((res) => {
    //     console.log(res.data);
    //     if (res.data === null || res.data.length === 0) {
    //       alert("사용자 없음");
    //       setApplyInfo([]);
    //       setTotalApplyCount('');
    //     } else {
    //       setApplyInfo(res.data.applyInfo);
    //       setTotalApplyCount(res.data.totalApplyCount);
    //       setCancelApplyCnt(res.data.cancelApplyCnt);
    //       setResumeReadCnt(res.data.resumeReadCnt);
    //       setResumeNotReadCnt(res.data.resumeNotReadCnt);
    //       setPageCount(Math.ceil(res.data.applyInfo.length / itemsPerPage));
    //     }
    //   })
    //   .catch((error) => {
    //     console.log(error);
    //   });
  }, []);

  const filteredApplyInfo = applyInfo.filter((item) => {
    if (select === '진행중') {
      return item.jbp_cndtn === '진행중';
    } else if (select === '마감') {
      return item.jbp_cndtn === '마감';
    } else {
      return true;
    }
  });

  const handlePageChange = (pageNum) => {
    setCurrentPage(pageNum);
  };

  const paginatedApplyInfo = filteredApplyInfo.slice(
    (currentPage - 1) * itemsPerPage,
    currentPage * itemsPerPage
  );

  const boardSearch = () => {
    if (search.length === 0) {
      alert("검색어를 입력해 주세요");
      return;
    } else if (searchType.length === 0) {
      alert("조회할 내역을 선택해 주세요");
      return;
    }

    // 주석 처리된 검색 조회 코드
    // axios.post("http://localhost:80/mypage/getSearchList", {
    //   serchType: searchType,
    //   search: search,
    //   mbrSq: mbrSq
    // })
    //   .then((res) => {
    //     console.log(res);
    //     setCurrentPage(1);
    //     if (res.data === null || res.data.length === 0) {
    //       alert("사용자 없음");
    //       setApplyInfo([]);
    //       setTotalApplyCount('');
    //     } else {
    //       setApplyInfo(res.data.applyInfo);
    //       setTotalApplyCount(res.data.totalApplyCount);
    //       setCancelApplyCnt(res.data.cancelApplyCnt);
    //       setResumeReadCnt(res.data.resumeReadCnt);
    //       setResumeNotReadCnt(res.data.resumeNotReadCnt);
    //       setPageCount(Math.ceil(res.data.applyInfo.length / itemsPerPage));
    //     }
    //   })
    //   .catch((error) => {
    //     console.log(error);
    //   });
  };

  const applyCancelCnt = (data) => {
    console.log("emit:" + data);
    setCancelApplyCnt(cancelApplyCnt + 1);
    setTotalApplyCount(totalApplyCount - 1);
  };

  return (
    <div>
      <section className="page-header page-header-modern bg-color-grey page-header-lg">
        <div className="container">
          <div className="row">
            <div className="col-md-12 align-self-center p-static order-2 text-center">
              <h1 className="font-weight-bold text-dark">입사지원 현황</h1>
            </div>
          </div>
        </div>
      </section>

      <div
        className="header-nav-features header-nav-features-no-border col-lg-5 col-xl-6 px-0 ms-0 mb-4"
        style={{ display: 'flex' }}
      >
        <div className="header-nav-feature pe-lg-4">
          <div className="search-with-select">
            <div className="search-form-wrapper input-group">
              <input
                className="form-control text-1"
                type="search"
                value={search}
                onChange={(e) => setSearch(e.target.value)}
                placeholder="검색할 내용을 입력하세요"
              />
              <div className="search-form-select-wrapper">
                <div className="custom-select-1">
                  <select
                    value={searchType}
                    onChange={(e) => setSearchType(e.target.value)}
                    className="form-control form-select"
                  >
                    <option value="jbpTl">공고 제목</option>
                    <option value="rsmName">이력서 제목</option>
                  </select>
                </div>
                <button className="btn" type="button" aria-label="Search" onClick={boardSearch}>
                  <i className="icons icon-magnifier header-nav-top-icon text-color-dark"></i>
                </button>
              </div>
            </div>
          </div>
        </div>
        <div className="custom-select-1 ml-10">
          <select
            className="form-control form-select"
            value={select}
            onChange={(e) => setSelect(e.target.value)}
          >
            <option value="전체" selected>
              전체 공고
            </option>
            <option value="진행중">진행중인 공고</option>
            <option value="마감">마감된 공고</option>
          </select>
        </div>
      </div>
      <div className="mb-3">
        <span className="bg-white p-2 border">지원완료 : {totalApplyCount}</span>
        <span className="bg-white p-2 border">열 람 : {resumeReadCnt}</span>
        <span className="bg-white p-2 border">미열람 : {resumeNotReadCnt}</span>
        <span className="bg-white p-2 border">지원 취소 {cancelApplyCnt}</span>
      </div>

      {/* {filteredApplyInfo.length > 0 && (
        <>
          {paginatedApplyInfo.map((item) => (
            <JoinCompanyCondition key={item.apySq} data={item} onApplyCancelCnt={applyCancelCnt} />
          ))}
        </>
      )} */}
      {filteredApplyInfo.length === 0 && (
        <h4>조회된 내용이 없습니다</h4>
      )}
      {/* {filteredApplyInfo.length !== 0 && (
        <ViewPaginate
          pageCount={pageCount}
          currentPage={currentPage}
          onPageNum={handlePageChange}
        />
      )} */}
    </div>
  );
};

export default ApplyCompanyList;
