import React, { useState, useEffect } from 'react';
// import JoinCompanyCondition from '@/components/fo/user/mypage/JoinCompanyCondition';
// import ViewPaginate from '@/components/fo/user/common/ViewPaginate';
import axios from 'axios';
import './ApplyCompanyList.css';

const ApplyCompanyList = () => {
  const [applyInfo, setApplyInfo] = useState([]);
  const [pageCount, setPageCount] = useState(0);
  const [currentPage, setCurrentPage] = useState(1);
  const [select, setSelect] = useState('? μ²?');
  const [totalApplyCount, setTotalApplyCount] = useState(0);
  const [cancelApplyCnt, setCancelApplyCnt] = useState(0);
  const [resumeReadCnt, setResumeReadCnt] = useState(0);
  const [resumeNotReadCnt, setResumeNotReadCnt] = useState(0);
  const [searchType, setSearchType] = useState('');
  const [search, setSearch] = useState('');

  const itemsPerPage = 5;
  const mbrSq = 1;

  useEffect(() => {
    // μ£Όμ μ²λ¦¬? ?°?΄?° λΆλ¬?€κΈ? μ½λ
    // axios.post('http://localhost:80/mypage/getApplyList', { mbrSq })
    //   .then((res) => {
    //     console.log(res.data);
    //     if (res.data === null || res.data.length === 0) {
    //       alert("?¬?©? ??");
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
    if (select === 'μ§νμ€?') {
      return item.jbp_cndtn === 'μ§νμ€?';
    } else if (select === 'λ§κ°') {
      return item.jbp_cndtn === 'λ§κ°';
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
      alert("κ²???΄λ₯? ?? ₯?΄ μ£ΌμΈ?");
      return;
    } else if (searchType.length === 0) {
      alert("μ‘°ν?  ?΄?­? ? ??΄ μ£ΌμΈ?");
      return;
    }

    // μ£Όμ μ²λ¦¬? κ²?? μ‘°ν μ½λ
    // axios.post("http://localhost:80/mypage/getSearchList", {
    //   serchType: searchType,
    //   search: search,
    //   mbrSq: mbrSq
    // })
    //   .then((res) => {
    //     console.log(res);
    //     setCurrentPage(1);
    //     if (res.data === null || res.data.length === 0) {
    //       alert("?¬?©? ??");
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
              <h1 className="font-weight-bold text-dark">??¬μ§?? ??©</h1>
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
                placeholder="κ²???  ?΄?©? ?? ₯??Έ?"
              />
              <div className="search-form-select-wrapper">
                <div className="custom-select-1">
                  <select
                    value={searchType}
                    onChange={(e) => setSearchType(e.target.value)}
                    className="form-control form-select"
                  >
                    <option value="jbpTl">κ³΅κ³  ? λͺ?</option>
                    <option value="rsmName">?΄? ₯? ? λͺ?</option>
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
            <option value="? μ²?" selected>
              ? μ²? κ³΅κ³ 
            </option>
            <option value="μ§νμ€?">μ§νμ€μΈ κ³΅κ³ </option>
            <option value="λ§κ°">λ§κ°? κ³΅κ³ </option>
          </select>
        </div>
      </div>
      <div className="mb-3">
        <span className="bg-white p-2 border">μ§???λ£? : {totalApplyCount}</span>
        <span className="bg-white p-2 border">?΄ ? : {resumeReadCnt}</span>
        <span className="bg-white p-2 border">λ―Έμ΄? : {resumeNotReadCnt}</span>
        <span className="bg-white p-2 border">μ§?? μ·¨μ {cancelApplyCnt}</span>
      </div>

      {/* {filteredApplyInfo.length > 0 && (
        <>
          {paginatedApplyInfo.map((item) => (
            <JoinCompanyCondition key={item.apySq} data={item} onApplyCancelCnt={applyCancelCnt} />
          ))}
        </>
      )} */}
      {filteredApplyInfo.length === 0 && (
        <h4>μ‘°ν? ?΄?©?΄ ??΅??€</h4>
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
