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
	
	// 지원자 리스트 불러오기(정렬, 초기화면 일반화)
	public Map<String, Object> findApplyData(ApplySearchDataDTO applySearchDataDTO) throws SQLException, IOException {		
		// 총 데이터 갯수
		int totalCount= applyMapper.loadApplyListCount(applySearchDataDTO);
		
		// 페이지네이션 정보 생성 (기본 1페이지 = 데이터10 / 페이지그룹= 페이지5)
		Pagination pagination = new Pagination(totalCount, applySearchDataDTO.getPageNo());
			
		Map<String, Object> map = new HashMap<String, Object>();
		//페이지네이션 정보 map 추가
		map.put("paginationData", pagination);
		
		// 검색/정렬 정보 map 추가
		map.put("searchData", applySearchDataDTO);
		
		//-----------------------에러!!!--------------------------------------------------------------
		// 지원자 리스트 정보 얻기
		List<ApplyListVO> applyListVO = applyMapper.selectApplyListData(map);
		
		// 지원자 리스트 정보 map 추가
		map.put("applyDatas", applyListVO);
		
		// 코드 정보 가져오기 (지원취소 빼고)		
		List<CommonCodeVO> commonCodeListVO = commonCodeMapper.selectCommonCodeApplyCategoryData();
		//코드 정보 map 추가
		map.put("applyConditions", commonCodeListVO);
		
		System.out.println(applyListVO);
		System.out.println(applyListVO.size());
		System.out.println(pagination);
		System.out.println(pagination.getTotalDataCount());
		System.out.println(applySearchDataDTO);
//		System.out.println(commonCodeListVO);
		
		return map;
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
