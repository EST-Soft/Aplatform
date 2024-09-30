package jobplatform.fo.enterprise.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jobplatform.fo.enterprise.domain.dto.ApplyConditionDataDTO;
import jobplatform.fo.enterprise.domain.dto.ApplySearchDataDTO;
import jobplatform.fo.enterprise.domain.vo.ApplyDetailDataVO;
import jobplatform.fo.enterprise.service.ApplyManagementService;

@RestController
public class ApplyManagementController {
	
	private final ApplyManagementService applyManagementService;
	
	
	public ApplyManagementController(ApplyManagementService applyManagementService) {
		this.applyManagementService = applyManagementService;
	}
	
	// 지원자 리스트 불러오기 (일반화 완료)
	@GetMapping("/applys/apply-list/{jbp_sq}/{division}/{condition}/{sort}/{pageNo}")
	public ResponseEntity<Map<String, Object>> findApplyListData(
			@PathVariable(name = "jbp_sq", required = false) int jbp_sq,
			@PathVariable(name = "division", required = false) String division,
			@PathVariable(name = "condition", required = false) int condition,
			@PathVariable(name = "sort", required = false) String sort,
			@PathVariable(name = "pageNo", required = false) int pageNo
			) {
		//검색 정보 DTO (공고번호, 구분(지원apply / 제안proposal), 상태, 정렬, 페이지번호)
		ApplySearchDataDTO applySearchDataDTO = new ApplySearchDataDTO(jbp_sq, division, condition, sort, pageNo);
		
		Map<String, Object> map = null;
		HttpStatus httpStatus = null;
	
		try {
			// 지원자 리스트 얻기
			map = applyManagementService.findApplyData(applySearchDataDTO);
			httpStatus = HttpStatus.OK;
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			httpStatus = HttpStatus.BAD_GATEWAY;
		}

		return new ResponseEntity<Map<String, Object>>(map, httpStatus);
	}
	
	// 지원자 상세 가져오기
	@GetMapping("/applys/apply-detail/{apy_sq}")
	public ResponseEntity<ApplyDetailDataVO> findApplyDetailData(@PathVariable(name = "apy_sq", required = false) int apy_sq) {
		
		ApplyDetailDataVO applyDetailData = null;
		HttpStatus httpStatus = null;
		
		try {
			// 지원자 상세 데이터 받아오기
			applyDetailData = applyManagementService.findApplyDetailData(apy_sq);
			httpStatus = HttpStatus.OK;
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			httpStatus = HttpStatus.BAD_GATEWAY;
		}
		
		return new ResponseEntity<ApplyDetailDataVO>(applyDetailData, httpStatus);
	}
	
	// 지원자 상태변경 
	@PatchMapping("/applys/condition/{apy_sq}/{apy_cndtn}")
	public ResponseEntity<HttpStatus> modifyApplyCondition(
			@PathVariable(name = "apy_sq", required = false) int apy_sq,
			@PathVariable(name = "apy_cndtn", required = false) int apy_cndtn,
			@RequestParam Timestamp intv_dtm
			) {
		
		ApplyConditionDataDTO applyConditionDataDTO = new ApplyConditionDataDTO(apy_sq, apy_cndtn, intv_dtm);
		
		HttpStatus httpStatus = null;
		
		try {
			//상태 변경 + 특정 상태시 면접일시 같이 입력
			Boolean result = applyManagementService.modifyApplyCondition(applyConditionDataDTO);
			if(result) {
				httpStatus = HttpStatus.OK;
			} else {
				httpStatus = HttpStatus.NOT_FOUND;
			}
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			httpStatus = HttpStatus.BAD_GATEWAY; 
		}
		
		return new ResponseEntity<HttpStatus>(httpStatus);
	}
	
}
