import React, { useState, useEffect } from 'react';
// import JoinCompanyCondition from '@/components/fo/user/mypage/JoinCompanyCondition';
// import ViewPaginate from '@/components/fo/user/common/ViewPaginate';
import axios from 'axios';
import './ApplyCompanyList.css';

const ApplyCompanyList = () => {
  const [applyInfo, setApplyInfo] = useState([]);
  const [pageCount, setPageCount] = useState(0);
  const [currentPage, setCurrentPage] = useState(1);
  const [select, setSelect] = useState('?†ÑÏ≤?');
  const [totalApplyCount, setTotalApplyCount] = useState(0);
  const [cancelApplyCnt, setCancelApplyCnt] = useState(0);
  const [resumeReadCnt, setResumeReadCnt] = useState(0);
  const [resumeNotReadCnt, setResumeNotReadCnt] = useState(0);
  const [searchType, setSearchType] = useState('');
  const [search, setSearch] = useState('');

  const itemsPerPage = 5;
  const mbrSq = 1;

  useEffect(() => {
    // Ï£ºÏÑù Ï≤òÎ¶¨?êú ?ç∞?ù¥?Ñ∞ Î∂àÎü¨?ò§Í∏? ÏΩîÎìú
    // axios.post('http://localhost:80/mypage/getApplyList', { mbrSq })
    //   .then((res) => {
    //     console.log(res.data);
    //     if (res.data === null || res.data.length === 0) {
    //       alert("?Ç¨?ö©?ûê ?óÜ?ùå");
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
    if (select === 'ÏßÑÌñâÏ§?') {
      return item.jbp_cndtn === 'ÏßÑÌñâÏ§?';
    } else if (select === 'ÎßàÍ∞ê') {
      return item.jbp_cndtn === 'ÎßàÍ∞ê';
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
      alert("Í≤??Éâ?ñ¥Î•? ?ûÖ?†•?ï¥ Ï£ºÏÑ∏?öî");
      return;
    } else if (searchType.length === 0) {
      alert("Ï°∞Ìöå?ï† ?Ç¥?ó≠?ùÑ ?Ñ†?Éù?ï¥ Ï£ºÏÑ∏?öî");
      return;
    }

    // Ï£ºÏÑù Ï≤òÎ¶¨?êú Í≤??Éâ Ï°∞Ìöå ÏΩîÎìú
    // axios.post("http://localhost:80/mypage/getSearchList", {
    //   serchType: searchType,
    //   search: search,
    //   mbrSq: mbrSq
    // })
    //   .then((res) => {
    //     console.log(res);
    //     setCurrentPage(1);
    //     if (res.data === null || res.data.length === 0) {
    //       alert("?Ç¨?ö©?ûê ?óÜ?ùå");
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
              <h1 className="font-weight-bold text-dark">?ûÖ?Ç¨Ïß??õê ?òÑ?ô©</h1>
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
                placeholder="Í≤??Éâ?ï† ?Ç¥?ö©?ùÑ ?ûÖ?†•?ïò?Ñ∏?öî"
              />
              <div className="search-form-select-wrapper">
                <div className="custom-select-1">
                  <select
                    value={searchType}
                    onChange={(e) => setSearchType(e.target.value)}
                    className="form-control form-select"
                  >
                    <option value="jbpTl">Í≥µÍ≥† ?†úÎ™?</option>
                    <option value="rsmName">?ù¥?†•?Ñú ?†úÎ™?</option>
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
            <option value="?†ÑÏ≤?" selected>
              ?†ÑÏ≤? Í≥µÍ≥†
            </option>
            <option value="ÏßÑÌñâÏ§?">ÏßÑÌñâÏ§ëÏù∏ Í≥µÍ≥†</option>
            <option value="ÎßàÍ∞ê">ÎßàÍ∞ê?êú Í≥µÍ≥†</option>
          </select>
        </div>
      </div>
      <div className="mb-3">
        <span className="bg-white p-2 border">Ïß??õê?ôÑÎ£? : {totalApplyCount}</span>
        <span className="bg-white p-2 border">?ó¥ ?ûå : {resumeReadCnt}</span>
        <span className="bg-white p-2 border">ÎØ∏Ïó¥?ûå : {resumeNotReadCnt}</span>
        <span className="bg-white p-2 border">Ïß??õê Ï∑®ÏÜå {cancelApplyCnt}</span>
      </div>

      {/* {filteredApplyInfo.length > 0 && (
        <>
          {paginatedApplyInfo.map((item) => (
            <JoinCompanyCondition key={item.apySq} data={item} onApplyCancelCnt={applyCancelCnt} />
          ))}
        </>
      )} */}
      {filteredApplyInfo.length === 0 && (
        <h4>Ï°∞Ìöå?êú ?Ç¥?ö©?ù¥ ?óÜ?äµ?ãà?ã§</h4>
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
