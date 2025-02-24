import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';
import moment from 'moment';
import QuillEditorComponent from '../common/Editor';

const EnterJobPostingEdit = () => {
  const { jbpSq } = useParams();
  const navigate = useNavigate();

  const [areas, setAreas] = useState([]);
  const [jobs, setJobs] = useState([]);
  const [selectedWorkAreas, setSelectedWorkAreas] = useState([]);
  const [selectedJobs, setSelectedJobs] = useState([]);
  const [jbpTl, setJbpTl] = useState('');
  const [jbpCntnt, setJbpCntnt] = useState('');
  const [cr, setCr] = useState('');
  const [sklName, setSklName] = useState('');
  const [edctn, setEdctn] = useState('');
  const [workArea, setWorkArea] = useState('');
  const [workForm, setWorkForm] = useState('');
  const [slry, setSlry] = useState('');
  const [workStartTime, setWorkStartTime] = useState('');
  const [workEndTime, setWorkEndTime] = useState('');
  const [picName, setPicName] = useState('');
  const [picMp, setPicMp] = useState('');
  const [picEml, setPicEml] = useState('');
  const [regstrStrtDtm, setRegstrStrtDtm] = useState('');
  const [regstrDlnDtm, setRegstrDlnDtm] = useState('');
  const [interviewAgreement, setInterviewAgreement] = useState(false);

  const minRegstrStrtDtm = moment().format('YYYY-MM-DD');

  useEffect(() => {
    const fetchAreasAndJobs = async () => {
      try {
        const areasResponse = await axios.get('/areas');
        setAreas(areasResponse.data);

        const jobsResponse = await axios.get('/jobs');
        setJobs(jobsResponse.data);

        if (jbpSq) {
          console.log('Calling boardDetail');
          boardDetail(areasResponse.data, jobsResponse.data);
        }
      } catch (error) {
        console.error('데이터를 가져오는 중 오류가 발생했습니다.', error);
      }
    };

    fetchAreasAndJobs();
  }, [jbpSq]);

  const boardDetail = async (areas, jobs) => {
    try {
      console.log('boardDetail function called');
      const response = await axios.get(`/board/detail/jobPosting/${jbpSq}`);
      console.log('response data:', response.data);

      setJbpTl(response.data.jbpTl);
      setJbpCntnt(response.data.jbpCntnt);
      setCr(response.data.cr);
      setSklName(response.data.sklName);
      setEdctn(response.data.edctn);
      setWorkForm(response.data.workForm);
      setSlry(response.data.slry);
      setPicName(response.data.picName);
      setPicMp(response.data.picMp);
      setPicEml(response.data.picEml);
      setRegstrStrtDtm(response.data.regstrStrtDtm);
      setRegstrDlnDtm(response.data.regstrDlnDtm);

      console.log('jbpCntnt:', response.data.jbpCntnt); // jbpCntnt 값을 로그에 출력

      // 직종 정보
      const selectedJobNames = response.data.jobName.map(name =>
        jobs.find(job => job.jobScName === name)
      );
      console.log('selectedJobNames:', selectedJobNames);
      setSelectedJobs(selectedJobNames);

      // 근무지역 정보
      const selectedAreaNames = response.data.workArea.map(name =>
        areas.find(area => area.areaName === name)
      );
      console.log('selectedAreaNames:', selectedAreaNames);
      setSelectedWorkAreas(selectedAreaNames);

      // workHour 분리 후 할당
      if (response.data.workHour) {
        const [startTime, endTime] = response.data.workHour.split('~').map(item => item.trim());
        setWorkStartTime(startTime);
        setWorkEndTime(endTime);
      }
    } catch (error) {
      console.error('에러:', error);
    }
  };

  const submitPost = async () => {
    if (jbpTl.trim() === '' || jbpCntnt.trim() === '') {
      alert('제목과 내용을 확인하세요.');
      return;
    }

    try {
      await axios.post(`/board/jobPostingUpdate/${jbpSq}`, {
        jbpSq,
        jbpTl,
        jbpCntnt,
        cr,
        sklName,
        jobName: selectedJobs.map(job => job.jobScName),
        edctn,
        workArea: selectedWorkAreas.map(area => area.areaName),
        workForm,
        slry: interviewAgreement ? '면접 후 협의' : slry,
        workHour: `${workStartTime} ~ ${workEndTime}`,
        regstrStrtDtm,
        regstrDlnDtm,
        picName,
        picMp,
        picEml,
      });

      alert('게시물이 수정되었습니다.');

      // 폼 리셋
      setJbpTl('');
      setJbpCntnt('');
      setCr('');
      setSklName('');
      setEdctn('');
      setWorkForm('');
      setSlry('');
      setWorkStartTime('');
      setWorkEndTime('');
      setRegstrDlnDtm('');
      setRegstrStrtDtm('');
      setPicName('');
      setPicMp('');
      setPicEml('');
      setInterviewAgreement(false);

      // 페이지 이동
      navigate(`/board/detail/jobPosting/${jbpSq}`);
    } catch (error) {
      console.error('에러:', error);
    }
  };

  return (
    <section id="examples" className="section section-default">
      <div className="container">
        <section className="card card-admin">
          <header className="card-header">
            <h2 className="card-title">공고 수정</h2>
          </header>
        </section>

        <div className="mb-3">
          <label htmlFor="jbpTl" className="form-label">제목</label>
          <input
            type="text"
            value={jbpTl}
            onChange={e => setJbpTl(e.target.value)}
            className="form-control"
            id="jbpTl"
            placeholder="제목을 입력하세요"
          />
        </div>

        <div className="row">
          <div className="col-md-3 mb-3">
            <label htmlFor="jobName" className="form-label">직업명</label>
            <div id="jobName" className="form-control" style={{ height: 'auto' }}>
              {jobs.map(job => (
                <div key={job.jobSq} className="form-check">
                  <input
                    type="checkbox"
                    checked={selectedJobs.includes(job)}
                    onChange={() => {
                      setSelectedJobs(prev =>
                        prev.includes(job) ?
                          prev.filter(j => j !== job) :
                          [...prev, job]
                      );
                    }}
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
            <label htmlFor="cr" className="form-label">경력</label>
            <select
              value={cr}
              onChange={e => setCr(e.target.value)}
              className="form-control"
              id="cr"
            >
              <option value="801">경력무관</option>
              <option value="802">신입</option>
              <option value="803">경력</option>
            </select>
          </div>
          <div className="col-md-3 mb-3">
            <label htmlFor="sklName" className="form-label">기술스택</label>
            <input
              type="text"
              value={sklName}
              onChange={e => setSklName(e.target.value)}
              className="form-control"
              id="sklName"
              placeholder="기술스택을 입력하세요"
            />
          </div>
          <div className="col-md-3 mb-3">
            <label htmlFor="edctn" className="form-label">학력</label>
            <select
              value={edctn}
              onChange={e => setEdctn(e.target.value)}
              className="form-control"
              id="edctn"
            >
              <option value="highSchool">고등학교 졸업</option>
              <option value="college">대학 졸업</option>
              <option value="graduate">대학원 졸업</option>
            </select>
          </div>
        </div>

        <div className="row">
          <div className="col-md-3 mb-3">
            <label htmlFor="workArea" className="form-label">근무지역</label>
            <div id="workArea" className="form-control" style={{ height: 'auto' }}>
              {areas.map(area => (
                <div key={area.areaSq} className="form-check">
                  <input
                    type="checkbox"
                    checked={selectedWorkAreas.includes(area)}
                    onChange={() => {
                      setSelectedWorkAreas(prev =>
                        prev.includes(area) ?
                          prev.filter(a => a !== area) :
                          [...prev, area]
                      );
                    }}
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
            <label htmlFor="workForm" className="form-label">근무형태</label>
            <select
              value={workForm}
              onChange={e => setWorkForm(e.target.value)}
              className="form-control"
              id="workForm"
            >
              <option value="fullTime">정규직</option>
              <option value="partTime">파트타임</option>
              <option value="contract">계약직</option>
            </select>
          </div>

          <div className="col-md-3 mb-3">
            <label htmlFor="slry" className="form-label">급여</label>
            <input
              type="text"
              value={slry}
              onChange={e => setSlry(e.target.value)}
              className="form-control"
              id="slry"
              placeholder="급여를 입력하세요"
            />
          </div>

          <div className="col-md-3 mb-3">
            <label htmlFor="workHour" className="form-label">근무시간</label>
            <input
              type="text"
              value={`${workStartTime} ~ ${workEndTime}`}
              onChange={e => {
                const [startTime, endTime] = e.target.value.split('~').map(item => item.trim());
                setWorkStartTime(startTime);
                setWorkEndTime(endTime);
              }}
              className="form-control"
              id="workHour"
              placeholder="근무시간을 입력하세요"
            />
          </div>
        </div>

        <div className="mb-3">
          <label htmlFor="regstrStrtDtm" className="form-label">등록 시작일</label>
          <input
            type="date"
            value={regstrStrtDtm}
            onChange={e => setRegstrStrtDtm(e.target.value)}
            className="form-control"
            id="regstrStrtDtm"
            min={minRegstrStrtDtm}
          />
        </div>

        <div className="mb-3">
          <label htmlFor="regstrDlnDtm" className="form-label">등록 마감일</label>
          <input
            type="date"
            value={regstrDlnDtm}
            onChange={e => setRegstrDlnDtm(e.target.value)}
            className="form-control"
            id="regstrDlnDtm"
            min={minRegstrStrtDtm}
          />
        </div>

        <div className="mb-3">
          <label htmlFor="picName" className="form-label">담당자 이름</label>
          <input
            type="text"
            value={picName}
            onChange={e => setPicName(e.target.value)}
            className="form-control"
            id="picName"
            placeholder="담당자 이름을 입력하세요"
          />
        </div>

        <div className="mb-3">
          <label htmlFor="picMp" className="form-label">담당자 연락처</label>
          <input
            type="text"
            value={picMp}
            onChange={e => setPicMp(e.target.value)}
            className="form-control"
            id="picMp"
            placeholder="담당자 연락처를 입력하세요"
          />
        </div>

        <div className="mb-3">
          <label htmlFor="picEml" className="form-label">담당자 이메일</label>
          <input
            type="email"
            value={picEml}
            onChange={e => setPicEml(e.target.value)}
            className="form-control"
            id="picEml"
            placeholder="담당자 이메일을 입력하세요"
          />
        </div>

        <div className="form-check mb-3">
          <input
            type="checkbox"
            checked={interviewAgreement}
            onChange={() => setInterviewAgreement(!interviewAgreement)}
            className="form-check-input"
            id="interviewAgreement"
          />
          <label className="form-check-label" htmlFor="interviewAgreement">
            면접 후 협의
          </label>
        </div>

        <div className="mb-3">
          <label htmlFor="jbpCntnt" className="form-label">내용</label>
          <QuillEditorComponent
            value={jbpCntnt}
            onChange={value => {
              console.log('Editor content changed:', value); // Editor에서 변경된 값 로그 출력
              setJbpCntnt(value);
            }}
            isReadOnly={false}
          />
        </div>

        <button type="button" onClick={submitPost} className="btn btn-primary">
          수정 완료
        </button>
      </div>
    </section>
  );
};

export default EnterJobPostingEdit;
