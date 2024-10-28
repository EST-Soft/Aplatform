import React from 'react';

const JobPostings = () => {
  return (
    <div className="job-postings">
      <div className="sidebar">
        <div className="keyword">요즘 뜨는 채용 키워드</div>
        <div className="keyword">지금 뜨는 공고</div>
        <div className="keyword">맞춤형 공고</div>
      </div>
      <div className="postings">
        <h2>지금 뜨는 공고</h2>
        <ul>
          <li>대성 한의원 간호사 신입</li>
          <li>대성 소프트 md 정규직</li>
          <li>대성 전자 사무디 현지 근무 신입</li>
          <li>대성 마트 관리직 채용</li>
          <li>대성 K-POP 댄스 학원 강사</li>
        </ul>
      </div>
    </div>
  );
};

export default JobPostings;
