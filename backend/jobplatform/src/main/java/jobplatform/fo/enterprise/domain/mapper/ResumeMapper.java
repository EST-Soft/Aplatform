package jobplatform.fo.enterprise.domain.mapper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import jobplatform.fo.enterprise.domain.dto.ResumeSearchDataDTO;
import jobplatform.fo.enterprise.domain.vo.ResumeListVO;


@Mapper
public interface ResumeMapper {

	// 페이지네이션을 위한 데이터 수 얻기
	int loadResumeListCount(ResumeSearchDataDTO resumeSearchDataDTO) throws SQLException, IOException;
	// 이력서 리스트 데이터 얻기
	List<ResumeListVO> selectResumeData(Map<String, Object> map) throws SQLException, IOException;
	// 이력서 대표여부 변경
	int updateResumeyRepresentative(int rsm_sq) throws SQLException, IOException;
	// 이력서 삭제
	int deleteResumeFullData(int rsm_sq) throws SQLException, IOException;
	// 이력서 첨부파일 삭제
	int delectRsmAtchmnt(int rsm_sq) throws SQLException, IOException;
	// 이력서 자기소개서 삭제
	int delectRsmSi(int rsm_sq) throws SQLException, IOException;
	// 이력서 학력 삭제
	int delectRsmEdctn(int rsm_sq) throws SQLException, IOException;
	// 이력서 자격증 삭제
	int delectRsmCrtfct(int rsm_sq) throws SQLException, IOException;
	// 이력서 경력 삭제
	int delectRsmCr(int rsm_sq) throws SQLException, IOException;
	
	// 이력서 복사
	// 이력서 메인테이블 복사
	int insertCopyResume(int rsm_sq) throws SQLException, IOException;


}
