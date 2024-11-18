import React, { useState, useEffect } from "react";
import axios from "axios";
import PaginationData from "../common/PaginationData";
import ResumeCard from "./ResumeDatas"; // 변경: ResumeDatas 대신 ResumeCard로 수정

const ResumeList = () => {
  const [resumeListData, setResumeListData] = useState({
    resumeDatas: [],
    paginationData: {},
    searchData: {
      mbr_sq: 1,
      sort: "desc",
      pageNo: 1,
    },
  });

  useEffect(() => {
    callAxios();
  }, [resumeListData.searchData.pageNo, resumeListData.searchData.sort]);

  const callAxios = async () => {
    try {
      const response = await axios.get(
        `http://localhost:3000/resumes/resume-list/${resumeListData.searchData.mbr_sq}/${resumeListData.searchData.sort}/${resumeListData.searchData.pageNo}`
      );
      console.log("API Response Data:", response.data); // 응답 데이터 확인
      setResumeListData(response.data);
    } catch (error) {
      console.error("API 호출 실패", error);
    }
  };

  // 이벤트 함수
  // 페이지네이션 페이지 변경 클릭
  const changePageNo = (pageNo) => {
    setResumeListData((prevData) => ({
      ...prevData,
      searchData: {
        ...prevData.searchData,
        pageNo: pageNo,
      },
    }));
  };

  // 정렬 select 변경
  const changeSort = (event) => {
    setResumeListData((prevData) => ({
      ...prevData,
      searchData: {
        ...prevData.searchData,
        sort: event.target.value,
        pageNo: 1,
      },
    }));
  };

  //대표이력서 변경 설정 (경로 주소는 3000으로 해줘야한다 react에서 기본적인 port번호라 package.json내에 proxy 주소랑은 별개다. )
  const modifyRepresentative = async (id) => {
    try {
      await axios.patch(`http://localhost:3000/resumes/representative/${id}`);
      callAxios();
    } catch (error) {
      console.error("API 호출 실패", error);
    }
  };

  // 이력서 복제 (rsm_sq 들고 Axios)
  const copyResumes = async (id) => {
    try {
      await axios.post(`http://localhost:3000/resumes/copy/${id}`);
      callAxios();
    } catch (error) {
      console.error("API 호출 실패", error);
    }
  };

  // 이력서 수정 (rsm_sq 들고 페이지 이동)


  // 이력서 삭제 (rsm_sq 들고 Axios)
  const deleteResumes = async (id) => {
    try {
      await axios.delete(`http://localhost:3000/resumes/${id}`);
      callAxios();
    } catch (error) {
      console.error("API 호출 실패", error);
    }
  };

  return (
    <div>
      <div className="row">
        <h1 className="form-group col-md-12">
          <strong className="font-weight-extra-bold">이력서 목록</strong>
        </h1>
        <hr className="gradient" />
      </div>

      <div className="row">
        <div className="form-group col-md-10"></div>
        <div className="form-group col-md-2">
          <select
            className="form-select form-control h-auto py-2"
            onChange={changeSort}
            value={resumeListData.searchData.sort}
          >
            <option value="desc">내림차순</option>
            <option value="asc">올림차순</option>
          </select>
        </div>
      </div>

      <div className="row">
        {resumeListData.resumeDatas.length === 0 ? (
          <div>
            <strong className="font-weight-extra-bold">자료가 없습니다.</strong>
          </div>
        ) : (
          resumeListData.resumeDatas.map((resumeData) => (
            <ResumeCard
              key={resumeData.rsm_sq}
              resumeData={resumeData}
              onModifyRepresentative={modifyRepresentative} // 수정된 부분
              onCopyResumes={copyResumes}
              onModifyResumes={() => {/* 구현 필요 */}}
              onDeleteResumes={deleteResumes}
            />
          ))
        )}
      </div>

      <hr className="gradient" />

      <div className="row">
        {resumeListData.paginationData.totalDataCount !== undefined && resumeListData.paginationData.totalDataCount !== 0 && (
          <PaginationData
            paginationData={resumeListData.paginationData}
            changePageNo={changePageNo}
          />
        )}
      </div>
    </div>
  );
};

export default ResumeList;
