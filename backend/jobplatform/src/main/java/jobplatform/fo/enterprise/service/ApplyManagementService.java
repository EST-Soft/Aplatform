package jobplatform.fo.enterprise.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jobplatform.fo.enterprise.common.Pagination;
import jobplatform.fo.enterprise.domain.dto.ApplyConditionDataDTO;
import jobplatform.fo.enterprise.domain.dto.ApplySearchDataDTO;
import jobplatform.fo.enterprise.domain.mapper.ApplyMapper;
import jobplatform.fo.enterprise.domain.mapper.CommonCodeMapper;
import jobplatform.fo.enterprise.domain.vo.ApplyDetailDataVO;
import jobplatform.fo.enterprise.domain.vo.ApplyListVO;
import jobplatform.fo.enterprise.domain.vo.CommonCodeVO;

@Service
public class ApplyManagementService {
	
    @Autowired
    private ApplyMapper applyMapper;

    @Autowired
    private CommonCodeMapper commonCodeMapper;
	
    /**
     * 지원자 리스트 불러오기 (공고번호 jbp_sq 기준)
     */
    public Map<String, Object> findApplyData(ApplySearchDataDTO applySearchDataDTO) throws SQLException, IOException {
        Map<String, Object> response = new HashMap<>();
        String condition = applySearchDataDTO.getCondition();
    
        // 'unrd'와 같은 문자열도 처리 가능하도록 로직 수정
        if ("unrd".equals(condition)) {
            // 문자열 값에 대한 처리
            System.out.println("미리 읽지 않은 상태: " + condition);
        } else if (condition.matches("\\d+")) {  // 숫자일 경우
            System.out.println("숫자 조건 처리: " + condition);
        } else {
            // 그 외의 값들에 대해 적절히 처리
            System.out.println("기타 조건 처리: " + condition);
        }
        if (applySearchDataDTO.getJbp_sq() == 0 || applySearchDataDTO.getJbp_sq() <= 0) {
            response.put("error", "Invalid jbp_sq");
            return response;  // 유효하지 않으면 빈 결과 반환
        }
    
        // 이후 데이터 처리 및 반환
        int totalCount = applyMapper.loadApplyListCount(applySearchDataDTO);
        Pagination pagination = new Pagination(totalCount, applySearchDataDTO.getPageNo());
        response.put("paginationData", pagination);
        response.put("searchData", applySearchDataDTO);
    
        List<ApplyListVO> applyListVO = applyMapper.selectApplyListData(applySearchDataDTO);
        if (applyListVO == null) {
            applyListVO = new ArrayList<>();
        }
        response.put("applyDatas", applyListVO);
    
        List<CommonCodeVO> commonCodeListVO = commonCodeMapper.selectCommonCodeApplyCategoryData();
        response.put("applyConditions", commonCodeListVO != null ? commonCodeListVO : new ArrayList<>());
    
        return response;
    }
    
    

    /**
     * 지원 상세보기
     */
    public ApplyDetailDataVO findApplyDetailData(long apy_sq) throws SQLException, IOException {
        System.out.println("지원 상세보기 요청: " + apy_sq);
        ApplyDetailDataVO applyDetailDataVO = applyMapper.selectApplyDetailData(apy_sq);
        return applyDetailDataVO != null ? applyDetailDataVO : new ApplyDetailDataVO();
    }

    /**
     * 지원자 상태 변경
     */
    public Boolean modifyApplyCondition(ApplyConditionDataDTO applyConditionDataDTO) throws SQLException, IOException {
    if (applyConditionDataDTO == null) {
        System.out.println("applyConditionDataDTO is null");
    } else {
        System.out.println("지원자 상태 변경 요청: " + applyConditionDataDTO);
    }
    int updatedRows = applyMapper.updateApplyCondition(applyConditionDataDTO);
    return updatedRows == 1;
}

}
