import React, { useState, useEffect, useCallback, useMemo } from 'react';
import './MypagePPAcception.scoped.css';

const PositionProposal = () => {
  const [parentAreaCode, setParentAreaCode] = useState(101000);
  const [selectedJobLC, setSelectedJobLC] = useState('IT데이터');
  const [ppAcceptYN, setPpAcceptYN] = useState(false);
  const [checkedArea, setCheckedArea] = useState([]);
  const [checkedJob, setCheckedJob] = useState([]);
  const [selectedArea, setSelectedArea] = useState([]);
  const [selectedJob, setSelectedJob] = useState([]);
  
  // 주석 처리된 데이터 로드 및 업데이트 코드
  /*
  useEffect(() => {
    const fetchData = async () => {
      const response = await api.$get('/user/mypage/ppAcception', {
        params: { mbr_sq: 1 },
      });
      setResult(response.data);
      setPpAcceptYN(response.data.ppAcceptYN === 'Y');
      setSelectedArea(response.data.areaList.filter(area => response.data.selectedAreas.includes(area.area_sq)));
      setSelectedJob(response.data.jobList.filter(job => response.data.selectedJobs.includes(job.job_sq)));
    };
    fetchData();
  }, []);

  useEffect(() => {
    const updateAcceptance = async () => {
      if (ppAcceptYN) {
        await api.$patch('/user/mypage/ppAcception/Y', { mbr_sq: 1 });
      } else {
        await api.$patch('/user/mypage/ppAcception/N', { mbr_sq: 1 });
      }
    };
    updateAcceptance();
  }, [ppAcceptYN]);
  */

  const parentAreaList = useMemo(() => {
    return []; // 샘플 데이터 대신 빈 배열로 처리
  }, []);

  const filteredAreaList = useMemo(() => {
    if (parentAreaCode === 0) return [];
    return []; // 샘플 데이터 대신 빈 배열로 처리
  }, [parentAreaCode]);

  const filteredJobList = useMemo(() => {
    if (selectedJobLC === '') return [];
    return []; // 샘플 데이터 대신 빈 배열로 처리
  }, [selectedJobLC]);

  const handleAreaChange = useCallback((area) => {
    setCheckedArea(prev =>
      prev.includes(area)
        ? prev.filter(a => a !== area)
        : [...prev, area]
    );
  }, []);

  const handleJobChange = useCallback((job) => {
    setCheckedJob(prev =>
      prev.includes(job)
        ? prev.filter(j => j !== job)
        : [...prev, job]
    );
  }, []);

  const handleSubmit = async () => {
    try {
      // 주석 처리된 데이터 전송 코드
      /*
      await api.$post('/user/mypage/ppAcception?mbr_sq=1', {
        checkedArea,
        checkedJob
      });
      */
      setSelectedArea(checkedArea);
      setSelectedJob(checkedJob);
      window.scrollTo({ top: 0, behavior: 'smooth' });
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <div>
      <div>
        <div className="row">
          <div className="col-6">
            <h4 style={{ marginBottom: 0, fontSize: '23px', fontWeight: 'bolder' }}>
              포지션 제안 받기
            </h4>
          </div>
          <div className="col-6" style={{ display: 'flex' }}>
            <input
              type="checkbox"
              checked={ppAcceptYN}
              onChange={() => setPpAcceptYN(!ppAcceptYN)}
              style={{ marginLeft: 'auto', zoom: 1.7 }}
            />
          </div>
        </div>

        {/* 대표이력서 row */}
        <div className="row" style={{ marginTop: '35px' }}>
          <div className="col-6" style={{ paddingTop: '7px' }}>
            <h4 style={{ marginBottom: 0 }}>
              <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="currentColor" className="bi bi-arrow-right-square-fill" viewBox="0 0 16 16">
                <path d="M0 14a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2a2 2 0 0 0-2 2zm4.5-6.5h5.793L8.146 5.354a.5.5 0 1 1 .708-.708l3 3a.5.5 0 0 1 0 .708l-3 3a.5.5 0 0 1-.708-.708L10.293 8.5H4.5a.5.5 0 0 1 0-1" />
              </svg>
              &nbsp;&nbsp;대표이력서
            </h4>
          </div>
          <div className="col-6" style={{ display: 'flex', padding: 0 }}>
            <button className="btn btn-outline btn-rounded btn-primary mb-2" style={{ marginLeft: 'auto', fontSize: 'medium', padding: '2px 10px' }}>
              변경
            </button>
          </div>
        </div>
        <div className="row" style={{ marginTop: '10px', border: '1px solid #eaeaea', padding: '20px 10px' }}>
          <div className="col-3" style={{ display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
            <div className="thumb-info-side-image-wrapper">
              <img src="/assets/profile.webp" className="img-fluid" alt="" style={{ width: '100px' }} />
            </div>
            {/* 나중에 야돈대신 들어갈 자리 :src="{{result.rsmInfo.rsm_img_file_url}}" */}
          </div>
          <div className="col-9 table-container">
            <table className="table">
              <thead>
                <tr>
                  <th colSpan="3">
                    <h2 className="font-weight-bold" style={{ marginBottom: '10px' }}>이력서 제목</h2>
                  </th>
                </tr>
              </thead>
              <tbody style={{ textAlign: 'left' }}>
                <tr>
                  <th>등록일자 : 2024-01-01</th>
                  <th>최종수정일자 : 2024-01-15</th>
                  <th></th>
                </tr>
              </tbody>
            </table>
          </div>
          {/* 저장된 지역 보여주기 */}
          {selectedArea.length !== 0 && (
            <div>
              {selectedArea.map((area, i) => (
                <span key={i} className="showChecked">
                  {area.area_name}
                </span>
              ))}
            </div>
          )}
          {/* 저장된 직업 보여주기 */}
          {selectedJob.length !== 0 && (
            <div>
              {selectedJob.map((job, i) => (
                <span key={i} className="showChecked">
                  {job.job_sc_name}
                </span>
              ))}
            </div>
          )}
        </div>

        <div className={ppAcceptYN ? '' : 'disabled'} style={{ marginTop: '30px' }}>
          {/* 제안 받을 근무지역 선택 row */}
          <div className="row" style={{ marginTop: '25px' }}>
            <div className="col">
              <h4 style={{ marginBottom: 0 }}>
                <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="currentColor" className="bi bi-arrow-right-square-fill" viewBox="0 0 16 16">
                  <path d="M0 14a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2a2 2 0 0 0-2 2zm4.5-6.5h5.793L8.146 5.354a.5.5 0 1 1 .708-.708l3 3a.5.5 0 0 1 0 .708l-3 3a.5.5 0 0 1-.708-.708L10.293 8.5H4.5a.5.5 0 0 1 0-1" />
                </svg>
                &nbsp;&nbsp;제안 받을 근무지역 선택
              </h4>
            </div>
          </div>
          {/* 선택한 지역 보여주기 */}
          {checkedArea.length !== 0 && (
            <div>
              {checkedArea.map((area, i) => (
                <span key={i} className="showChecked">
                  {area.area_name}
                  <i className="fas fa-times" onClick={() => handleAreaChange(area)} style={{ cursor: 'pointer' }}></i>
                </span>
              ))}
              <span className="showChecked" style={{ cursor: 'pointer', backgroundColor: '#eaeaea' }} onClick={() => setCheckedArea([])}>지역초기화</span>
            </div>
          )}
          <div className="row" style={{ marginTop: '20px', border: '1px solid #eaeaea', padding: '15px' }}>
            <div className="col-3 scrollbar" style={{ borderRight: '1px solid #eaeaea' }}>
              <ul style={{ margin: 0, padding: 0 }}>
                {parentAreaList.map((parentArea, i) => (
                  <li
                    key={i}
                    onClick={() => setParentAreaCode(parentArea.area_sq)}
                    className={parentAreaCode === parentArea.area_sq ? 'selected' : ''}
                    style={{ cursor: 'pointer', marginBottom: '10px', listStyle: 'none', textAlign: 'center' }}
                  >
                    {parentArea.area_name.split("전체").join('')}
                  </li>
                ))}
              </ul>
            </div>
            <div className="col-9 scrollbar">
              {parentAreaCode !== 0 && (
                <div className="row">
                  {filteredAreaList.map((area, i) => (
                    <div className="col-4" key={i} style={{ marginBottom: '9px' }}>
                      <span>
                        <input
                          type="checkbox"
                          id={area.area_sq}
                          value={area}
                          checked={checkedArea.includes(area)}
                          onChange={() => handleAreaChange(area)}
                          style={{ cursor: 'pointer' }}
                        />
                        <label htmlFor={area.area_sq} style={{ cursor: 'pointer' }}>&nbsp;&nbsp;{area.area_name}</label>
                      </span>
                    </div>
                  ))}
                </div>
              )}
            </div>
          </div>

          {/* 제안 받을 직업 row */}
          <div className="row" style={{ marginTop: '25px' }}>
            <div className="col">
              <h4 style={{ marginBottom: 0 }}>
                <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="currentColor" className="bi bi-arrow-right-square-fill" viewBox="0 0 16 16">
                  <path d="M0 14a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2a2 2 0 0 0-2 2zm4.5-6.5h5.793L8.146 5.354a.5.5 0 1 1 .708-.708l3 3a.5.5 0 0 1 0 .708l-3 3a.5.5 0 0 1-.708-.708L10.293 8.5H4.5a.5.5 0 0 1 0-1" />
                </svg>
                &nbsp;&nbsp;제안 받을 직업
              </h4>
            </div>
          </div>
          {/* 선택한 직업 보여주기 */}
          {checkedJob.length !== 0 && (
            <div>
              {checkedJob.map((job, i) => (
                <span key={i} className="showChecked">
                  {job.job_sc_name}
                  <i className="fas fa-times" onClick={() => handleJobChange(job)} style={{ cursor: 'pointer' }}></i>
                </span>
              ))}
              <span className="showChecked" style={{ cursor: 'pointer', backgroundColor: '#eaeaea' }} onClick={() => setCheckedJob([])}>직업초기화</span>
            </div>
          )}
          <div className="row" style={{ marginTop: '20px', border: '1px solid #eaeaea', padding: '15px' }}>
            <div className="col-3 scrollbar" style={{ borderRight: '1px solid #eaeaea' }}>
              <ul style={{ margin: 0, padding: 0 }}>
                <li
                  onClick={() => setSelectedJobLC('IT데이터')}
                  className={selectedJobLC === 'IT데이터' ? 'selected' : ''}
                  style={{ cursor: 'pointer', marginBottom: '10px', listStyle: 'none', textAlign: 'center' }}
                >
                  IT데이터
                </li>
              </ul>
            </div>
            <div className="col-9 scrollbar">
              {selectedJobLC !== '' && (
                <div className="row">
                  {filteredJobList.map((job, i) => (
                    <div className="col-4" key={i} style={{ marginBottom: '9px' }}>
                      <span>
                        <input
                          type="checkbox"
                          id={job.job_sq}
                          value={job}
                          checked={checkedJob.includes(job)}
                          onChange={() => handleJobChange(job)}
                          style={{ cursor: 'pointer' }}
                        />
                        <label htmlFor={job.job_sq} style={{ cursor: 'pointer' }}>&nbsp;&nbsp;{job.job_sc_name}</label>
                      </span>
                    </div>
                  ))}
                </div>
              )}
            </div>
          </div>

          {/* 보내기 버튼 */}
          <div className="row" style={{ marginTop: '25px', display: 'flex', justifyContent: 'center' }}>
            <button className="btn btn-outline btn-rounded btn-primary mb-2" style={{ width: '100px' }} onClick={handleSubmit}>
              저장
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default PositionProposal;
