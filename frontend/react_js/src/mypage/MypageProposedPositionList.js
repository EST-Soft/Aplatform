import React from 'react';
import './MypageProposedPositionList.css';
// import { formatDateYMD } from '@/tools';
// import Paginate from 'react-paginate';

const PositionProposalList = () => {
  // 데이터 주석 처리, 필요한 상태만 유지
  const result = {
    totalPostsCount: 1,
    jobPosts: [
      {
        entrprs_name: 'Enterprise 1',
        jbp_ttl: 'Job Title 1',
        hits: 123,
        slry: '50,000',
        edctn: 'Bachelor',
        crr: '3 years',
        work_form: 'Full-time',
        prpsl_dtm: '2023-07-21',
        regstr_dln_dtm: '2023-08-21',
        areas: ['Seoul', 'Busan'],
        jobs: ['Developer', 'Manager'],
        skills: ['JavaScript', 'React'],
      },
    ],
  };

  // Pagination handler
  const handlePageClick = (data) => {
    console.log(`Selected page: ${data.selected + 1}`);
  };

  return (
    <div>
      {result && result.totalPostsCount !== 0 ? (
        <div>
          <div className="row">
            <div style={{ display: 'flex', justifyContent: 'right' }}>
              <input
                type="text"
                maxLength="80"
                className="form-control text-1"
                placeholder="검색어 입력"
                style={{ width: '140px' }}
              />
              <span className="center" style={{ marginLeft: '10px' }}>
                <i className="fas fa-search" style={{ width: 'max-content', fontSize: '20px' }}></i>
              </span>
            </div>
          </div>
          <div className="row" style={{ marginTop: '20px' }}>
            {result.jobPosts.map((post, i) => (
              <div
                key={i}
                style={{ borderBottom: '1px solid #eae', marginBottom: '16px' }}
              >
                <table
                  className="table table-bordered"
                  style={{ marginBottom: 10, tableLayout: 'fixed', width: '100%' }}
                >
                  <thead>
                    <tr>
                      <th colSpan="2" style={{ fontSize: 'large' }}>
                        {post.entrprs_name}
                      </th>
                      <th colSpan="3">{post.jbp_ttl}</th>
                      <th colSpan="1" style={{ textAlign: 'center' }}>
                        조회수&nbsp;&nbsp;{post.hits}
                      </th>
                    </tr>
                  </thead>
                  <tbody style={{ textAlign: 'center' }}>
                    <tr style={{ verticalAlign: 'middle' }}>
                      <th>급여&nbsp;&nbsp;{post.slry}</th>
                      <th>{post.edctn}</th>
                      <th>{post.cr}</th>
                      <th>{post.work_form}</th>
                      <th style={{ lineHeight: '1.2em' }}>
                        제안일
                        <br />
                        {/* {formatDateYMD(post.prpsl_dtm)} */}
                      </th>
                      <th style={{ lineHeight: '1.2em' }}>
                        마감일
                        <br />
                        {/* {formatDateYMD(post.regstr_dln_dtm)} */}
                      </th>
                    </tr>
                    <tr>
                      <th>근무지역</th>
                      <th
                        colSpan="2"
                        style={{
                          textAlign: 'left',
                          whiteSpace: 'nowrap',
                          overflow: 'hidden',
                          textOverflow: 'ellipsis',
                        }}
                      >
                        {post.areas ? (
                          post.areas.map((area, i) => (
                            <React.Fragment key={i}>
                              {area}
                              {i < post.areas.length - 1 && ', '}
                            </React.Fragment>
                          ))
                        ) : (
                          '해당사항없음'
                        )}
                      </th>
                      <th>직종</th>
                      <th
                        colSpan="2"
                        style={{
                          textAlign: 'left',
                          whiteSpace: 'nowrap',
                          overflow: 'hidden',
                          textOverflow: 'ellipsis',
                        }}
                      >
                        {post.jobs ? (
                          post.jobs.map((job, i) => (
                            <React.Fragment key={i}>
                              {job}
                              {i < post.jobs.length - 1 && ', '}
                            </React.Fragment>
                          ))
                        ) : (
                          '해당사항없음'
                        )}
                      </th>
                    </tr>
                    <tr>
                      <th>필요스킬</th>
                      <th
                        colSpan="4"
                        style={{
                          textAlign: 'left',
                          whiteSpace: 'nowrap',
                          overflow: 'hidden',
                          textOverflow: 'ellipsis',
                        }}
                      >
                        {post.skills ? (
                          post.skills.map((skill, i) => (
                            <React.Fragment key={i}>
                              {skill}
                              {i < post.skills.length - 1 && ', '}
                            </React.Fragment>
                          ))
                        ) : (
                          '해당사항없음'
                        )}
                      </th>
                      <th>
                        <button className="btn btn-outline btn-primary" style={{ padding: '4px 8px' }}>
                          수 락
                        </button>
                        <button
                          className="btn btn-outline btn-secondary"
                          style={{ padding: '4px 8px', marginLeft: '10px' }}
                          // onClick={() => refuseProposedPostion(post.pstn_prpsl_sq)}
                        >
                          거 절
                        </button>
                      </th>
                    </tr>
                  </tbody>
                </table>
              </div>
            ))}
          </div>
          <div className="row" style={{ marginTop: '35px' }}>
            <div className="col-3"></div>
            <div className="col-6" style={{ display: 'flex', justifyContent: 'center' }}>
              {/* <Paginate
                pageCount={result.totalPostsCount}
                pageRangeDisplayed={5}
                marginPagesDisplayed={0}
                previousLabel={'<<'}
                nextLabel={'>>'}
                onPageChange={handlePageClick}
                containerClassName={'pagination'}
                pageClassName={'pointer'}
                previousClassName={'pointer'}
                nextClassName={'pointer'}
                breakClassName={'dispalyNone pointer'}
              /> */}
            </div>
            <div className="col-3"></div>
          </div>
        </div>
      ) : (
        <div className="row" style={{ textAlign: 'center' }}>
          <h3>
            <br />
            <br />
            <br />
            <br />
            제안 받은 포지션이 없습니다.
          </h3>
        </div>
      )}
    </div>
  );
};

export default PositionProposalList;
