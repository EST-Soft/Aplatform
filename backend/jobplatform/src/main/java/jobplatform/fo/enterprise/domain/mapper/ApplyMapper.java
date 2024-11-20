package jobplatform.fo.enterprise.domain.mapper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import jobplatform.fo.enterprise.domain.dto.ApplyConditionDataDTO;
import jobplatform.fo.enterprise.domain.dto.ApplySearchDataDTO;
import jobplatform.fo.enterprise.domain.vo.ApplyDetailDataVO;
import jobplatform.fo.enterprise.domain.vo.ApplyListVO;

@Mapper
public interface ApplyMapper {
    
    // 지원자 목록 갯수 조회
    int loadApplyListCount(ApplySearchDataDTO applySearchDataDTO);

    // 지원자 목록 조회 (map -> jbp_sq가 long으로 변경)
    List<ApplyListVO> selectApplyListData(ApplySearchDataDTO applySearchDataDTO); // jbp_sq의 타입을 long으로 수정
    
    // 지원자 디테일 데이터 얻기
    ApplyDetailDataVO selectApplyDetailData(long apy_sq) throws SQLException, IOException;
    
    // 지원자 상태 변경
    int updateApplyCondition(ApplyConditionDataDTO applyConditionDataDTO) throws SQLException, IOException;
}
