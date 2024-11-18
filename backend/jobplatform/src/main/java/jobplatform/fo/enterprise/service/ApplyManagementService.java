package jobplatform.fo.enterprise.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

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
	
	private final ApplyMapper applyMapper;
	private final CommonCodeMapper commonCodeMapper;
	
	public ApplyManagementService(ApplyMapper applyMapper, CommonCodeMapper commonCodeMapper) {
		this.applyMapper = applyMapper;
		this.commonCodeMapper = commonCodeMapper;
	}
	
	// 지원자 리스트 불러오기 (공고번호 jbp_sq 기준)
public Map<String, Object> findApplyData(ApplySearchDataDTO applySearchDataDTO) throws SQLException, IOException {  
   // System.out.println("서비스에서 받는 DTO" + applySearchDataDTO);    

    // 총 데이터 개수 조회 (공고번호 jbp_sq로 필터링)
    int totalCount = applyMapper.loadApplyListCount(applySearchDataDTO);
    
    
    // 페이지네이션 정보 생성 (기본 1페이지 = 데이터 10개 / 페이지 그룹 = 5페이지)
    Pagination pagination = new Pagination(totalCount, applySearchDataDTO.getPageNo());
   
    
    // 조회 결과를 담을 Map 초기화
    Map<String, Object> resultMap = new HashMap<>();
  
    
    // 페이지네이션 정보 추가
    resultMap.put("paginationData", pagination);
    
    // 검색 및 정렬 정보를 추가
    resultMap.put("searchData", applySearchDataDTO);
    //System.out.println("dddddddd" + applySearchDataDTO.getJbp_sq());
    
    // 공고번호에 맞는 지원자 리스트 정보 조회 (jbp_sq로 필터링)
    List<ApplyListVO> applyListVO = applyMapper.selectApplyListData(applySearchDataDTO.getJbp_sq());
    System.out.println("applyListVO: " + applyListVO);
    

    // 조회된 지원자 리스트 추가
    resultMap.put("applyDatas", applyListVO);
    
    // 코드 정보 가져오기 (지원 상태 등)
    List<CommonCodeVO> commonCodeListVO = commonCodeMapper.selectCommonCodeApplyCategoryData();
    
    // 상태 코드 정보 추가
    resultMap.put("applyConditions", commonCodeListVO);

    // 디버깅 출력 (필요한 경우 제거)
    
    System.out.println("applyListVO.size(): " + applyListVO.size());
    System.out.println("pagination: " + pagination);
    System.out.println("pagination.getTotalDataCount(): " + pagination.getTotalDataCount());
    System.out.println("applySearchDataDTO: " + applySearchDataDTO);
    

    return resultMap;
}

	
	// 지원 상세보기
	public ApplyDetailDataVO findApplyDetailData(int apy_sq) throws SQLException, IOException {
		System.out.println("서비스 도착 : " + apy_sq);
		
		ApplyDetailDataVO applyDetailDataVO = applyMapper.selectApplyDetailData(apy_sq);
		
		return applyDetailDataVO;
	}
	
	// 지원자 상태 변경
	public Boolean modifyApplyCondition(ApplyConditionDataDTO applyConditionDataDTO) throws SQLException, IOException {
		Boolean result = false;
		
		if(applyMapper.updateApplyCondition(applyConditionDataDTO) == 1) {
			result = true;
		} else {
			//실패 예외
		}

		return result;
	}
}
