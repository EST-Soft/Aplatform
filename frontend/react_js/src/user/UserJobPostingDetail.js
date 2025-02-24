import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';
import QuillEditorComponent from '../common/Editor';
import '../common/Editor.css'

const UserJobPostingDetail = () => {
  const [loading, setLoading] = useState(true);
  const [jbp, setJbp] = useState({
    jbpSq: 0,
    enterpriseName: "",
    jbpTl: "",
    jbpCntnt: "",
    hits: 0,
    cr: "",
    edctn: "",
    workArea: "",
    jobName: "",
    workForm: "",
    slry: "",
    regstrStrtDtm: ""
  });
  const [isEditable, setIsEditable] = useState(true);

  const { jbpSq } = useParams();
  const navigate = useNavigate();

  const formatDate = (dateString) => {
    const date = new Date(dateString);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
  };

  const fetchJobPostingDetail = async () => {
    try {
      const response = await axios.get(`/board/detail/jobPosting/${jbpSq}`);
      setJbp(response.data);
    } catch (error) {
      console.error('공고 세부 정보 가져오기 오류:', error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchJobPostingDetail();
  }, [jbpSq]);

  const goUpdatePage = () => {
    setIsEditable(false);
    navigate(`/board/jobPostingUpdate/${jbp.jbpSq}`);
  };

  const confirmDelete = () => {
    if (window.confirm('게시글을 삭제 하시겠습니까?')) {
      deleteBoard();
      alert('게시글이 삭제되었습니다.');
    }
  };

  const deleteBoard = async () => {
    try {
      await axios.delete(`/board/jobPostingDelete/${jbp.jbpSq}`);
      console.log('게시글이 삭제 되었습니다');
      navigate('/board/list/jobPosting');
    } catch (error) {
      console.error('게시글 삭제 실패:', error);
    }
  };

  const applyJob = () => {
    const resumeId = prompt('이력서 번호를 입력하세요:');
    if (resumeId) {
      insertApply(resumeId);
    }
  };

  const insertApply = async (resumeId) => {
    const applyData = {
      resume: { rsmSq: resumeId },
      jobPosting: { jbpSq: jbp.jbpSq },
      apyDtm: new Date().toISOString()
    };

    try {
      const response = await axios.post('/apply/insert', applyData);
      alert(response.data);
    } catch (error) {
      console.error('입사지원 오류:', error);
      alert('입사지원을 처리하는 중 오류가 발생했습니다.');
    }
  };

  const educationMapping = {
    '606': '학력무관',
    '605': '고등학교 이상',
    '604': '대학교(2,3년제) 이상',
    '603': '대학교(4년제) 이상',
    '602': '석사',
    '601': '박사'
  };

  const careerMapping = {
    '801': '경력무관',
    '802': '신입',
    '803': '경력'
  };

  const getEducationText = (value) => {
    return educationMapping[value] || value;
  };

  const getCareerText = (value) => {
    return careerMapping[value] || value;
  };

  return (
    <section id="examples" className="section section-default">
      <div className="container">
        <section className="card card-admin">
          <header className="card-header">
            <div className="card-actions">
              <a href="#" className="card-action card-action-toggle" data-card-toggle=""></a>
              <a href="#" className="card-action card-action-dismiss" data-card-dismiss=""></a>
            </div>
            <h2 className="card-title">공고</h2>
          </header>
        </section>

        {loading ? (
          <div className="text-center">Loading...</div>
        ) : (
          <>
            <div className="row mt-3">
              <div className="col-md-3">
                <div className="mb-3">
                  <label htmlFor="jbpTl" className="form-label">제목</label>
                  <input type="text" value={jbp.jbpTl} className="form-control" id="jbpTl" placeholder="제목" readOnly />
                </div>
              </div>
              <div className="col-md-3">
                <div className="mb-3">
                  <label htmlFor="enterpriseName" className="form-label">기업 이름</label>
                  <input type="text" value={jbp.enterpriseName} className="form-control" id="enterpriseName" placeholder="기업 이름" readOnly />
                </div>
              </div>
              <div className="col-md-2">
                <div className="mb-3">
                  <label className="form-label">조회수: {jbp.hits}</label>
                </div>
              </div>
              <div className="col-md-4">
                <div className="mb-3">
                  <label className="form-label">접수시작일: {formatDate(jbp.regstrStrtDtm)}</label>
                </div>
              </div>
            </div>

            <div className="row mt-3">
              <div className="col-md-4">
                <div className="mb-3">
                  <label htmlFor="cr" className="form-label">경력</label>
                  <input type="text" value={getCareerText(jbp.cr)} className="form-control" id="cr" placeholder="경력" readOnly />
                </div>
              </div>
              <div className="col-md-4">
                <div className="mb-3">
                  <label htmlFor="edctn" className="form-label">학력</label>
                  <input type="text" value={getEducationText(jbp.edctn)} className="form-control" id="edctn" placeholder="학력" readOnly />
                </div>
              </div>
              <div className="col-md-4">
                <div className="mb-3">
                  <label htmlFor="workArea" className="form-label">근무지역</label>
                  <input type="text" value={jbp.workArea} className="form-control" id="workArea" placeholder="근무지역" readOnly />
                </div>
              </div>
            </div>

            <div className="row mt-3">
              <div className="col-md-4">
                <div className="mb-3">
                  <label htmlFor="workForm" className="form-label">근무형태</label>
                  <input type="text" value={jbp.workForm} className="form-control" id="workForm" placeholder="근무형태" readOnly />
                </div>
              </div>
              <div className="col-md-4">
                <div className="mb-3">
                  <label htmlFor="slry" className="form-label">급여</label>
                  <input type="text" value={jbp.slry} className="form-control" id="slry" placeholder="급여" readOnly />
                </div>
              </div>
              <div className="col-md-4">
                <div className="mb-3">
                  <label htmlFor="jobName" className="form-label">모집 직군</label>
                  <input type="text" value={jbp.jobName} className="form-control" id="jobName" placeholder="직종" readOnly />
                </div>
              </div>
            </div>
            
            <div className="col-md-12">
              <div className="mb-1">
                <label htmlFor="jbpCntnt" className="form-label">공고 내용</label>
                <QuillEditorComponent value={jbp.jbpCntnt} isReadOnly={isEditable} />
              </div>
            </div>

            <div className="row mt-3">
              <div className="col d-flex justify-content-end">
                <button className="btn btn-primary" onClick={applyJob}>입사지원</button>
              </div>
            </div>

            <div className="row mt-3">
              <div className="col d-flex justify-content-end">
                {/* 일반 회원이 공고상세를 보는데 수정 및 삭제 버튼의 필요여부가 없다 생각해 우선 주석 처리 */}
                <button className="btn btn-success" onClick={goUpdatePage}>수정</button>
                <button className="btn btn-danger ms-2" onClick={confirmDelete}>삭제</button>
              </div>
            </div>
          </>
        )}
      </div>
    </section>
  );
};

export default UserJobPostingDetail;
