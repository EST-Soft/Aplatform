package jobplatform.fo.enterprise.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import jobplatform.fo.enterprise.domain.dto.ResumeDataDTO;
import jobplatform.fo.enterprise.domain.dto.ResumeSearchDataDTO;
import jobplatform.fo.enterprise.domain.dto.SelfintoductionsDataDTO;
import jobplatform.fo.enterprise.domain.entity.ResumeEntity;
import jobplatform.fo.enterprise.service.ResumeManagementService;
import jobplatform.fo.user.domain.entity.MemberEntity;

@RestController
public class ResumeManagementController {
	
	private final ResumeManagementService resumeManagementService;

	public ResumeManagementController(ResumeManagementService resumeManagementService) {
		this.resumeManagementService = resumeManagementService;
	}  //inputResumeFullData
	
	// 이력서 목록 불러오기 (정렬 일반화)
	@GetMapping("/resumes/resume-list/{mbr_sq}/{sort}/{pageNo}")
	public ResponseEntity<Map<String, Object>> findResumeListData(
			@PathVariable("mbr_sq") int mbr_sq,
			@PathVariable("sort") String sort,
			@PathVariable("pageNo") int pageNo
			) {
		
		ResumeSearchDataDTO resumeSearchDataDTO = new ResumeSearchDataDTO(mbr_sq, sort, pageNo);
		Map<String, Object> map = null;
		HttpStatus httpStatus = null;
		
		try {
			
			map = resumeManagementService.findResumeData(resumeSearchDataDTO);
			
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		httpStatus=HttpStatus.OK;
		
		return new ResponseEntity<Map<String, Object>>(map, httpStatus);
		
	}
	
	// 이력서 상세 불러오기
	@GetMapping("/resumes/resume-detail/{rsm_sq}")
	public ResponseEntity<Map<String, Object>> findResumeDetailData(@PathVariable("rsm_sq") int rsm_sq) {

		// 이력서 정보를 조회
        Map<String, Object> resumeDetail = resumeManagementService.findResumeM(rsm_sq);

        // 조회된 이력서 정보를 응답으로 반환
        return ResponseEntity.ok(resumeDetail);
	}
	
	// 이력서 등록
	@PostMapping("/resumes/insert-resume")
	public void inputResumeFullData(@RequestParam("mbrSq") Long mbrSq, @RequestBody ResumeDataDTO resumeDataDTO) {
		resumeManagementService.insertResume(mbrSq, resumeDataDTO);
	} // inputResumeFullData


	
	// 이력서 등록  테스트 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	@PostMapping("/resumes/test")
	public void test(
			@ModelAttribute ResumeDataDTO resumeDataDTO
			//@ModelAttribute List<SelfintoductionsDataDTO> selfintoductionsDataDTO
			) {
		System.out.println("test 도착");
		System.out.println(resumeDataDTO);
		
	}
	
	// 이력서 수정	
	@PatchMapping("/resumes/{rsm_sq}")
	public void modifyResumeFullData(
			@PathVariable("rsm_sq") int rsm_sq,
			@RequestBody String Data
			) {
		System.out.println("컨트롤 도착 : " + rsm_sq);
		// 여기가 쫌 문제./././././
		
	}

	// 이력서 삭제
	@DeleteMapping("/resumes/{rsm_sq}")
	public ResponseEntity<HttpStatus> deleteResumeFullData(@PathVariable("rsm_sq") int rsm_sq) {
		System.out.println("컨트롤 도착 : " + rsm_sq);
		HttpStatus httpStatus = null;
		
		try {
			
			if(resumeManagementService.deleteResumeFullData(rsm_sq)) {
				// 성공
				httpStatus = HttpStatus.OK;
			} else {
				// 실패 로직
			}
			
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return new ResponseEntity<HttpStatus>(httpStatus);
	}
	
	/////////////////////////// 리스트 페이지 기능 ///////////////////////////////////
	// 대표이력서 수정
	@PatchMapping("/resumes/representative/{rsm_sq}")
	public ResponseEntity<HttpStatus> modifyResumeRepresentative(@PathVariable("rsm_sq") int rsm_sq) {
		System.out.println("컨트롤 도착 : " + rsm_sq);
		HttpStatus httpStatus = null;
		
		// 변경로직 -> Y인걸 전부 N 시키고 , 보낸PK를 Y로
		try {
			
			if(resumeManagementService.modifyResumeyRepresentative(rsm_sq)) {
				// 성공
				httpStatus = HttpStatus.OK;
			} else {
				// 실패 로직
			}
			
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		return new ResponseEntity<HttpStatus>(httpStatus);
	}
	
	// 복제
	@PostMapping("/resumes/copy/{rsm_sq}")
	public ResponseEntity<HttpStatus> copyResumeData(@PathVariable("rsm_sq") int rsm_sq) {
		System.out.println("컨트롤 도착 : " + rsm_sq);
		HttpStatus httpStatus = null;
		
		try {
			
			if(resumeManagementService.copyResumeData(rsm_sq)) {
				// 성공
				httpStatus = HttpStatus.OK;
			} else {
				// 실패 로직
			}
			
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		return new ResponseEntity<HttpStatus>(httpStatus);
	}
	
	
	
}
