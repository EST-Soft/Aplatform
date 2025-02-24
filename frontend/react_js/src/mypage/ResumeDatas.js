import React from 'react';
import { useNavigate } from 'react-router-dom';

const ResumeCard = ({ resumeData, onModifyRepresentative, onCopyResumes, onModifyResumes, onDeleteResumes }) => {
  const navigate = useNavigate();

  const toDtlPage = () => {
    navigate(`/resumeDetailView/${resumeData.rsm_sq}`);
  };

  // 포멧 함수
  const formatDate = (dateTime) => {
    return new Date(dateTime).toLocaleDateString();
  };

  return (
    <section className="call-to-action with-full-borders mb-2">
      <div className="row col-sm-12 col-lg-12">
        <div className="col-sm-9 col-lg-9">
          <div className="call-to-action-content">
            <h3>
              <a className="font-weight-extra-bold" onClick={toDtlPage}>
                {resumeData.rsm_ttl}
              </a>
            </h3>
          </div>
        </div>
        <div className="col-sm-3 col-lg-3">
          <div className="call-to-action-btn">
            {resumeData.rsm_rprsntv_yn === 'Y' ? (
              <a href="#" className="btn btn-rounded btn-quaternary mb-2">
                대표이력서
              </a>
            ) : (
              <a
                href="#"
                className="btn btn-outline btn-rounded btn-quaternary mb-2"
                onClick={() => onModifyRepresentative(resumeData.rsm_sq)} // 수정된 부분
              >
                대표이력서 설정
              </a>
            )}
          </div>
        </div>
      </div>
      <div className="row col-sm-12 col-lg-12">
        <div className="col-sm-4 col-lg-4">
          <p className="mb-0">{formatDate(resumeData.updt_dtm)}</p>
        </div>
        <div className="col-sm-5 col-lg-5"></div>
        <div className="col-sm-1 col-lg-1">
          <div className="call-to-action-btn">
            <a
              href="#"
              className="btn btn-outline btn-rounded btn-primary mb-2"
              onClick={() => onCopyResumes(resumeData.rsm_sq)} // 수정된 부분
            >
              복제
            </a>
          </div>
        </div>
        <div className="col-sm-1 col-lg-1">
          <div className="call-to-action-btn">
            <a
              href="#"
              className="btn btn-outline btn-rounded btn-tertiary mb-2"
              onClick={() => onModifyResumes(resumeData.rsm_sq)} // 수정된 부분
            >
              수정
            </a>
          </div>
        </div>
        <div className="col-sm-1 col-lg-1">
          <div className="call-to-action-btn">
            <a
              href="#"
              className="btn btn-outline btn-rounded btn-secondary mb-2"
              onClick={() => onDeleteResumes(resumeData.rsm_sq)} // 수정된 부분
            >
              삭제
            </a>
          </div>
        </div>
      </div>
    </section>
  );
};

export default ResumeCard;
