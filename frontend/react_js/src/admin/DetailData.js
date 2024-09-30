import React from 'react';
import { Link, useParams } from 'react-router-dom';
import DetailList from './DetailList';

const DetailData = () => {
    const { type, id } = useParams(); 

    
    // 데이터 매핑 <임시데이터> 나중에 DB데이터 가져와야합니다 -> DetailList 

    const dataMapping = {
        JobPosting: [
            { label: "번호", value: 2 },
            { label: "제목", value:"게시글 제목"},
            { label: "작성자", value: "홍길동" },
            { label: "작성 날짜", value: "2024-09-19" },
            { label: "내용", value: "여기에 공고 내용이 들어갑니다." },
            { label: "조회수", value: 100 },
            { label: "상태", value: "진행중" },
        ],
        MemberAccount: [
            { label: "번호", value: 1 },
            { label: "이름", value: "홍길동" },
            { label: "ID", value: "id123" },
            { label: "가입 날짜", value: "2024-01-01" },
            { label: "이메일", value: "hong@example.com" },
            { label: "전화번호", value: "010-1234-5678" },
            { label: "상태", value: "활성" },
        ],
        EnterPrise: [
            { label: "번호", value: 1 },
            { label: "업체명", value: "Sample Company" },
            { label: "이메일", value: "contact@sample.com" },
            { label: "역할", value: "파트너" },
            { label: "가입 날짜", value: "2024-01-01" },
            { label: "전화번호", value: "010-9876-5432" },
            { label: "상태", value: "활성" },
        ],
        Manager: [
            { label: "번호", value: 1 },
            { label: "관리자", value: "관리자1" },
            { label: "역할", value: "주 관리자" },
            { label: "날짜", value: "2024-01-01" },
            { label: "이메일", value: "admin1@example.com" },
            { label: "전화번호", value: "010-1234-5678" },
            { label: "상태", value: "활성" },
        ],
        Qna: [
            { label: "번호", value: "qna123" },
            { label: "질문자", value: "홍길동" },
            { label: "질문 내용", value: "질문이 무엇인가요?" },
            { label: "답변 상태", value: "답변 대기" },
            { label: "작성 날짜", value: "2024-09-19" },
            { label: "조회수", value: 10 },
            { label: "카테고리", value: "일반" },
        ],
        General: [
            { label: "번호", value: 1 },
            { label: "제목", value:"게시글 제목" },
            { label: "작성자", value: "이순신" },
            { label: "작성 날짜", value: "2024-09-20" },
            { label: "내용", value: "여기에 일반 게시글 내용이 들어갑니다." },
            { label: "조회수", value: 50 },
            { label: "상태", value: "완료" },
        ],
        FeedBack: [
            { label: "번호", value: 1 },
            { label: "제목", value: "피드백 제목" },
            { label: "작성자", value: "홍길동" },
            { label: "작성 날짜", value: "2024-09-21" },
            { label: "내용", value: "여기에 피드백 내용이 들어갑니다." },
            { label: "조회수", value: 5 },
            { label: "상태", value: "처리 완료" },
        ],

        MyPage: [
            { label: "번호", value: id || 1 }, 
            { label: "이름", value: "홍길동" },
            { label: "이메일", value: "hong@example.com" },
            { label: "가입 날짜", value: "2024-01-01" },
            { label: "전화번호", value: "010-1234-5678" },
            { label: "상태", value: "활성" },
        ],
    };

    const data = dataMapping[type] || [];
    const title = type === 'JobPosting' ? '공고 관리 상세' : 
                  type === 'MemberAccount' ? '회원 관리 상세' :
                  type === 'EnterPrise' ? '기업 관리 상세' :
                  type === 'Manager' ? '관리자 관리 상세' :
                  type === 'Qna' ? 'Q&A 상세' :
                  type === 'General' ? '일반 게시글 상세' :
                  type === 'MyPage' ? '마이페이지' :
                  '피드백 상세';

    console.log('type:', type);
    console.log('id:', id);
    console.log('data:', data);

    return <DetailList title={title} data={data} />;
};

export default DetailData;
