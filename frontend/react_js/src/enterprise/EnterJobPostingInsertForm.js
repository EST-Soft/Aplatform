import React, { useState, useEffect } from 'react';
import '../common/Editor.css'
import QuillEditorComponent from '../common/Editor';
import axios from 'axios';

const EnterJobPostingForm = () => {
    const [areas, setAreas] = useState([]);
  const [jobs, setJobs] = useState([]);
  const [selectedWorkAreas, setSelectedWorkAreas] = useState([]);
  const [selectedJobs, setSelectedJobs] = useState([]);

  const [jbpTl, setJbpTl] = useState('');
  const [jbpCntnt, setJbpCntnt] = useState('');
  const [cr, setCr] = useState(801);
  const [sklName, setSklName] = useState('');
  const [edctn, setEdctn] = useState(606);
  const [workForm, setWorkForm] = useState('정규직');
  const [slry, setSlry] = useState('');
  const [workStartTime, setWorkStartTime] = useState('');
  const [workEndTime, setWorkEndTime] = useState('');
  const [regstrStrtDtm, setRegstrStrtDtm] = useState('');
  const [regstrDlnDtm, setRegstrDlnDtm] = useState('');
  const [picName, setPicName] = useState('');
  const [picMp, setPicMp] = useState('');
  const [picEml, setPicEml] = useState('');
  const [interviewAgreement, setInterviewAgreement] = useState(false);

  const today = new Date();
  const minRegstrStrtDtm = `${today.getFullYear()}-${String(today.getMonth() + 1).padStart(2, '0')}-${String(today.getDate()).padStart(2, '0')}`;

  // useEffect(() => {
  //   // 데이터 가져오기 주석 처리
  //   const fetchAreasAndJobs = async () => {
  //     try {
  //       const areasResponse = await axios.get('/areas');
  //       setAreas(areasResponse.data);

  //       const jobsResponse = await axios.get('/jobs');
  //       setJobs(jobsResponse.data);
  //     } catch (error) {
  //       console.error('데이터를 가져오는 중 오류가 발생했습니다.', error);
  //     }
  //   };
  //   fetchAreasAndJobs();
  // }, []);

  const submitPost = async () => {
    // 필수 입력 값 확인
    if (jbpTl.trim() === '' || jbpCntnt.trim() === '') {
      alert('제목과 내용을 확인하세요.');
      return;
    }
  
    try {
      // 데이터 전송
      const response = await axios.post('/board/jobPostingInsert', {
        jbpTl,
        jbpCntnt,
        cr,
        sklName,
        edctn,
        workForm,
        slry: interviewAgreement ? '면접 후 협의' : slry + " 만원",
        workHour: `${workStartTime} ~ ${workEndTime}`,
        regstrStrtDtm,
        regstrDlnDtm,
        picName,
        picMp,
        picEml,
        jobName: selectedJobs.map(job => job.jobScName),
        workArea: selectedWorkAreas.map(area => area.areaName),
      });
   
  
      // 성공 메시지 및 폼 초기화
      alert('공고가 등록되었습니다.');
      clearForm();
  
    } catch (error) {
      // 에러 처리
      console.error('게시물 작성 오류:', error);
  
      // 에러 응답이 있는 경우
      if (error.response) {
        const { status, data } = error.response; 
        console.error(`서버 상태 코드: ${status}`);
        console.error(`서버 응답 데이터: ${data}`);
        alert(`게시물 작성 오류: ${data.message || '알 수 없는 오류가 발생했습니다.'}`);
        
      // 요청이 전송되었지만 응답이 없는 경우
      } else if (error.request) {
        console.error('요청 오류:', error.request);
        alert('서버 응답이 없습니다. 네트워크를 확인해 주세요.');
  
      // 설정 오류인 경우
      } else {
        console.error('설정 오류:', error.message);
        alert('게시물 작성 중 오류가 발생했습니다. 다시 시도해주세요.');
      }
    }
  };



  // ---------------------- submitPost test중 (이놈이 찐)------------------
  // const submitPost = () => {
  //   if (jbpTl.trim() === '' || jbpCntnt.trim() === '') {
  //     alert('제목과 내용을 확인하세요.');
  //     return;
  //   }

  //   // 데이터 전송 주석 처리
  //   axios.post('/board/jobPostingInsert', {
  //     jbpTl,
  //     jbpCntnt,
  //     cr,
  //     sklName,
  //     edctn,
  //     workForm,
  //     slry: interviewAgreement ? '면접 후 협의' : slry + " 만원",
  //     workHour: `${workStartTime} ~ ${workEndTime}`,
  //     regstrStrtDtm,
  //     regstrDlnDtm,
  //     picName,
  //     picMp,
  //     picEml,
  //     jobName: selectedJobs.map(job => job.jobScName),
  //     workArea: selectedWorkAreas.map(area => area.areaName),
  //   })
  //   .then(response => {
  //     alert('공고가 등록되었습니다.');
  //     clearForm();
  //   })
  //   .catch(error => {
  //     console.error('게시물 작성 오류:', error);
  //     alert('게시물 작성 중 오류가 발생했습니다. 다시 시도해주세요.');
  //   });
  // };

  const clearForm = () => {
    setJbpTl('');
    setJbpCntnt('');
    setCr(801);
    setSklName('');
    setEdctn(606);
    setWorkForm('정규직');
    setSlry('');
    setWorkStartTime('');
    setWorkEndTime('');
    setRegstrStrtDtm('');
    setRegstrDlnDtm('');
    setPicName('');
    setPicMp('');
    setPicEml('');
    setInterviewAgreement(false);
    setSelectedJobs([]);
    setSelectedWorkAreas([]);
  };

  return (
    <section id="examples" className="section section-default">
      <div className="container">
        <section className="card card-admin">
          <header className="card-header">
            <h2 className="card-title">공고 등록</h2>
          </header>
        </section>

        <div className="mb-3">
          <label htmlFor="jbpTl" className="form-label">제목</label>
          <input
            type="text"
            value={jbpTl}
            onChange={(e) => setJbpTl(e.target.value)}
            className="form-control"
            id="jbpTl"
            placeholder="제목을 입력하세요"
          />
        </div>

        <div className="row">
          <div className="col-md-3 mb-3">
            <label htmlFor="jobName" className="form-label">모집 직군</label>
            <div id="jobName" className="form-control" style={{ height: 'auto' }}>
              {jobs.map((job) => (
                <div key={job.jobSq} className="form-check">
                  <input
                    type="checkbox"
                    checked={selectedJobs.includes(job)}
                    onChange={() =>
                      setSelectedJobs((prev) =>
                        prev.includes(job)
                          ? prev.filter((j) => j !== job)
                          : [...prev, job]
                      )
                    }
                    className="form-check-input"
                    id={`job-${job.jobSq}`}
                  />
                  <label className="form-check-label" htmlFor={`job-${job.jobSq}`}>
                    {job.jobScName}
                  </label>
                </div>
              ))}
            </div>
          </div>

          <div className="col-md-3 mb-3">
            <label htmlFor="workArea" className="form-label">근무지역</label>
            <div id="workArea" className="form-control" style={{ height: 'auto' }}>
              {areas.map((area) => (
                <div key={area.areaSq} className="form-check">
                  <input
                    type="checkbox"
                    checked={selectedWorkAreas.includes(area)}
                    onChange={() =>
                      setSelectedWorkAreas((prev) =>
                        prev.includes(area)
                          ? prev.filter((a) => a !== area)
                          : [...prev, area]
                      )
                    }
                    className="form-check-input"
                    id={`area-${area.areaSq}`}
                  />
                  <label className="form-check-label" htmlFor={`area-${area.areaSq}`}>
                    {area.areaName}
                  </label>
                </div>
              ))}
            </div>
          </div>

          <div className="col-md-3 mb-3">
            <label htmlFor="sklName" className="form-label">요구 스킬</label>
            <input
              type="text"
              value={sklName}
              onChange={(e) => setSklName(e.target.value)}
              className="form-control"
              id="sklName"
              placeholder="기술명을 입력하세요"
            />
          </div>
        </div>

        <div className="row">
          <div className="col-md-3 mb-3">
            <label htmlFor="cr" className="form-label">경력</label>
            <select
              value={cr}
              onChange={(e) => setCr(e.target.value)}
              className="form-control"
              id="cr"
            >
              <option value="801">경력무관</option>
              <option value="802">신입</option>
              <option value="803">경력</option>
            </select>
          </div>
          <div className="col-md-3 mb-3">
            <label htmlFor="edctn" className="form-label">학력</label>
            <select
              value={edctn}
              onChange={(e) => setEdctn(e.target.value)}
              className="form-control"
              id="edctn"
            >
              <option value="606">학력무관</option>
              <option value="605">고등학교 이상</option>
              <option value="604">대학교(2,3년제) 이상</option>
              <option value="603">대학교(4년제) 이상</option>
              <option value="602">석사</option>
              <option value="601">박사</option>
            </select>
          </div>

          <div className="col-md-2 mb-3">
            <label htmlFor="workForm" className="form-label">근무형태</label>
            <select
              value={workForm}
              onChange={(e) => setWorkForm(e.target.value)}
              className="form-control"
              id="workForm"
            >
              <option value="정규직">정규직</option>
              <option value="계약직">계약직</option>
              <option value="인턴">인턴</option>
            </select>
          </div>

          <div className="col-md-3 mb-3">
            <label className="form-label">급여</label>
            <div className="input-group">
              <input
                type="text"
                value={slry}
                onChange={(e) => setSlry(e.target.value)}
                className="form-control"
                id="slry"
                placeholder="연봉"
                disabled={interviewAgreement}
              />
              <div className="input-group-text">
                <input
                  type="checkbox"
                  checked={interviewAgreement}
                  onChange={() => setInterviewAgreement((prev) => !prev)}
                />{' '}
                면접 후 협의
              </div>
            </div>
          </div>

          <div className="col-md-2 mb-3">
            <label htmlFor="workStartTime" className="form-label">출근 시간</label>
            <input
              type="time"
              value={workStartTime}
              onChange={(e) => setWorkStartTime(e.target.value)}
              className="form-control"
              id="workStartTime"
            />
          </div>
          <div className="col-md-2 mb-3">
            <label htmlFor="workEndTime" className="form-label">퇴근 시간</label>
            <input
              type="time"
              value={workEndTime}
              onChange={(e) => setWorkEndTime(e.target.value)}
              className="form-control"
              id="workEndTime"
            />
          </div>
        </div>

        <div className="mb-3">
          <label htmlFor="jbpCntnt" className="form-label">내용</label>
          <QuillEditorComponent value={jbpCntnt} onChange={setJbpCntnt} />
        </div>

        <div className="row">
          <div className="col-md-6 mb-3">
            <label htmlFor="regstrStrtDtm" className="form-label">공고 시작일</label>
            <input
              type="date"
              value={regstrStrtDtm}
              onChange={(e) => setRegstrStrtDtm(e.target.value)}
              className="form-control"
              id="regstrStrtDtm"
              min={minRegstrStrtDtm}
            />
          </div>
          <div className="col-md-6 mb-3">
            <label htmlFor="regstrDlnDtm" className="form-label">공고 마감일</label>
            <input
              type="date"
              value={regstrDlnDtm}
              onChange={(e) => setRegstrDlnDtm(e.target.value)}
              className="form-control"
              id="regstrDlnDtm"
              min={minRegstrStrtDtm}
            />
          </div>
        </div>

        <div className="row">
          <div className="col-md-4 mb-3">
            <label htmlFor="picName" className="form-label">담당자 이름</label>
            <input
              type="text"
              value={picName}
              onChange={(e) => setPicName(e.target.value)}
              className="form-control"
              id="picName"
              placeholder="담당자 이름을 입력하세요"
            />
          </div>
          <div className="col-md-4 mb-3">
            <label htmlFor="picMp" className="form-label">담당자 연락처</label>
            <input
              type="text"
              value={picMp}
              onChange={(e) => setPicMp(e.target.value)}
              className="form-control"
              id="picMp"
              placeholder="담당자 연락처를 입력하세요"
            />
          </div>
          <div className="col-md-4 mb-3">
            <label htmlFor="picEml" className="form-label">담당자 이메일</label>
            <input
              type="email"
              value={picEml}
              onChange={(e) => setPicEml(e.target.value)}
              className="form-control"
              id="picEml"
              placeholder="담당자 이메일을 입력하세요"
            />
          </div>
        </div>

        <div>
          <button onClick={submitPost}>작성</button>
        </div>
      </div>
    </section>
  );
};

export default EnterJobPostingForm;
