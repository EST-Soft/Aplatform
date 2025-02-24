import React, { useState, useEffect, useMemo } from 'react';
// import ChartComponent from './ChartComponent';
// import { formatDateYMD } from '../utils';

const EnterMainView = () => {
  const [result, setResult] = useState(null);
  const [entprsSq, setEntprsSq] = useState(1);
  
  // Mock data
  const mockResult = {
    myJobPostSimpleData: [
      { entrprs_sq: '1', jbp_sq: '101', jbp_tl: 'Frontend Developer', insrt_dtm: '2024-07-01', jbp_cndtn: 702 },
      { entrprs_sq: '1', jbp_sq: '102', jbp_tl: 'Backend Developer', insrt_dtm: '2024-07-05', jbp_cndtn: 701 },
      { entrprs_sq: '1', jbp_sq: '103', jbp_tl: 'Full Stack Developer', insrt_dtm: '2024-07-10', jbp_cndtn: 703 },
    ]
  };

  const mockJobPosts = ['101', '102', '103'];

  useEffect(() => {
    // Replace this with actual data fetching logic
    // const fetchData = async () => {
    //   const response = await api.$get("/enterprise", { params: { entprs_sq: entprsSq } });
    //   setResult(response);
    // };
    // fetchData();
    
    // Using mock data instead
    setResult(mockResult);
  }, [entprsSq]);

  const jobPosts = useMemo(() => {
    if (!result) return [];
    return result.myJobPostSimpleData.map(post => post.jbp_sq);
  }, [result]);

  if (!result) return <div>Loading...</div>;

  return (
    <div className="container py-4">
      <div className="row mt-5">
        <div className="col-6" style={{ borderRight: '1px solid #eaeaea' }}>
          <div className="mb-3">
            <h4 style={{ marginBottom: 0 }}>
              <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="currentColor" className="bi bi-arrow-right-square-fill" viewBox="0 0 16 16">
                <path d="M0 14a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2a2 2 0 0 0-2 2zm4.5-6.5h5.793L8.146 5.354a.5.5 0 1 1 .708-.708l3 3a.5.5 0 0 1 0 .708l-3 3a.5.5 0 0 1-.708-.708L10.293 8.5H4.5a.5.5 0 0 1 0-1" />
              </svg>
              &nbsp;&nbsp;
              최근 등록한 공고
            </h4>
          </div>
          <table className="table table-bordered" style={{ textAlign: 'center' }}>
            <thead>
              <tr>
                <th>공고ID</th>
                <th>공고 이름</th>
                <th>등록일자</th>
                <th>상태</th>
              </tr>
            </thead>
            <tbody>
              {result.myJobPostSimpleData.map((jobPost, i) => (
                <tr key={i}>
                  <td>{`${jobPost.entrprs_sq} - ${jobPost.jbp_sq}`}</td>
                  <td>{jobPost.jbp_tl}</td>
                  {/* <td>{formatDateYMD(jobPost.insrt_dtm)}</td> */}
                  <td>
                    {jobPost.jbp_cndtn === 701 && '예정'}
                    {jobPost.jbp_cndtn === 702 && '진행중'}
                    {jobPost.jbp_cndtn === 703 && '접수마감'}
                    {jobPost.jbp_cndtn === 704 && '종료'}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
        <div className="col-6">
          <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
            <div className="mb-3" style={{ display: 'inline-block' }}>
              <h4 style={{ marginBottom: 0 }}>
                <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="currentColor" className="bi bi-arrow-right-square-fill" viewBox="0 0 16 16">
                  <path d="M0 14a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2a2 2 0 0 0-2 2zm4.5-6.5h5.793L8.146 5.354a.5.5 0 1 1 .708-.708l3 3a.5.5 0 0 1 0 .708l-3 3a.5.5 0 0 1-.708-.708L10.293 8.5H4.5a.5.5 0 0 1 0-1" />
                </svg>
                &nbsp;&nbsp;
                공고별 인재 추천
              </h4>
            </div>
            <select>
              {mockJobPosts.map(postID => (
                <option key={postID} value={postID}>{`${entprsSq} - ${postID}`}</option>
              ))}
            </select>
          </div>
          <table className="table table-bordered" style={{ textAlign: 'center' }}>
            <thead>
              <tr>
                <th>인 재</th>
                <th>이력서 제목</th>
                <th>등록일</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>홍길동</td>
                <td>뽑아주세용</td>
                <td>2024.07.08</td>
              </tr>
              <tr>
                <td>김남길</td>
                <td>vue 초 고 수</td>
                <td>2024.07.01</td>
              </tr>
              <tr>
                <td>고승원</td>
                <td>스프링 끝판왕</td>
                <td>2024.07.08</td>
              </tr>
              <tr>
                <td>김동수</td>
                <td>신입 개발자입니다</td>
                <td>2024.06.18</td>
              </tr>
              <tr>
                <td>스티븐</td>
                <td>경력 20년 게임 개발자</td>
                <td>2024.07.04</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
      <div className="row mt-5">
        {/* <ChartComponent /> */}
      </div>
    </div>
  );
};

export default EnterMainView;
