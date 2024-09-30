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

import jobplatform.fo.enterprise.domain.dto.AttachmentDto;
import jobplatform.fo.enterprise.domain.dto.CareerDto;
import jobplatform.fo.enterprise.domain.dto.ResumeCertificateSDto;
import jobplatform.fo.enterprise.domain.dto.ResumeDataDTO;
import jobplatform.fo.enterprise.domain.dto.ResumeDataResponseDto;
import jobplatform.fo.enterprise.domain.dto.ResumeSearchDataDTO;
import jobplatform.fo.enterprise.domain.dto.SelfIntroductionsDataDTO;
import jobplatform.fo.enterprise.domain.dto.SkillCodeSDto;
import jobplatform.fo.enterprise.domain.entity.ResumeEntity;
import jobplatform.fo.enterprise.service.AttachmentService;
import jobplatform.fo.enterprise.service.CareerService;
import jobplatform.fo.enterprise.service.ResumeCertificateSService;
import jobplatform.fo.enterprise.service.ResumeManagementService;
import jobplatform.fo.enterprise.service.SelfIntroductionService;
import jobplatform.fo.enterprise.service.SkillCodeResumeRService;
import jobplatform.fo.user.domain.entity.MemberEntity;
import jobplatform.fo.user.domain.vo.EducationDto;
import jobplatform.fo.user.service.EducationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ResumeManagementController {

	private final ResumeManagementService resumeManagementService;
	private final AttachmentService attachmentService;
	private final EducationService educationService;
	private final SkillCodeResumeRService skillCodeResumeRService;
	private final ResumeCertificateSService resumeCertificateSService;
	private final SelfIntroductionService selfIntroductionService;
	private final CareerService careerService;

	/*
	 * public ResumeManagementController(ResumeManagementService
	 * resumeManagementService) {
	 * this.resumeManagementService = resumeManagementService;
	 * } // inputResumeFullData
	 */

	// 이력서 목록 불러오기 (정렬 일반화)
	@GetMapping("/resumes/resume-list/{mbr_sq}/{sort}/{pageNo}")
	public ResponseEntity<Map<String, Object>> findResumeListData(
			@PathVariable("mbr_sq") int mbr_sq,
			@PathVariable("sort") String sort,
			@PathVariable("pageNo") int pageNo) {

		ResumeSearchDataDTO resumeSearchDataDTO = new ResumeSearchDataDTO(mbr_sq, sort, pageNo);
		Map<String, Object> map = null;
		HttpStatus httpStatus = null;

		try {

			map = resumeManagementService.findResumeData(resumeSearchDataDTO);

		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		httpStatus = HttpStatus.OK;

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
	/*
	 * @PostMapping("/resumes/insert-resume/test")
	 * public Long inputResumeFullData(@RequestParam("mbrSq") Long mbrSq,
	 * 
	 * @RequestBody ResumeDataDTO resumeDataDTO) {
	 * Long rsmSq = resumeManagementService.insertResume(mbrSq, resumeDataDTO);
	 * System.out.println(rsmSq + ": 이력서번호");
	 * return rsmSq;
	 * } // inputResumeFullData
	 */

	// 이력서 등록
	@PostMapping("/resumes/insert-resume")
	public void insertResumeFullData(@RequestParam("mbrSq") Long mbrSq, @RequestBody ResumeDataResponseDto requestDto) {
		// 이력서 인적사항 및 이미지 처리
		ResumeDataDTO resumeDataDTO = requestDto.getResumeDataDTO();
		Long rsmSq = resumeManagementService.insertResume(mbrSq, resumeDataDTO);

		// 이미지가 있을 시 첨부파일
		if (resumeDataDTO.getRsmImgOrgnlFn() != null && resumeDataDTO.getRsmImgFileUrl() != null) {
			attachmentService.insertImageAttachment(mbrSq, rsmSq, resumeDataDTO);
		}

		List<EducationDto> educationDtos = requestDto.getEducationDtoList();
		educationService.insertEducation(mbrSq, rsmSq, educationDtos);

		List<SkillCodeSDto> skilsDataDtos = requestDto.getSkilsDataDtoList();
		skillCodeResumeRService.insertSkillCodeResume(rsmSq, skilsDataDtos);

		List<ResumeCertificateSDto> resumeCertificateSDtos = requestDto.getCertificateDtoList();
		resumeCertificateSService.insertResumeCertificateS(mbrSq, rsmSq, resumeCertificateSDtos);

		List<SelfIntroductionsDataDTO> selfIntroductionsDataDTOs = requestDto.getSelfIntroductionDtoList();
		selfIntroductionService.insertSelfIntroduction(mbrSq, rsmSq, selfIntroductionsDataDTOs);

		List<CareerDto> careerDtos = requestDto.getCareerDtoList();
		careerService.insertCareerData(mbrSq, rsmSq, careerDtos);

		List<AttachmentDto> attachmentDtos = requestDto.getAttachmentDtoList();
		System.out.println("첨부파일 이름: " + attachmentDtos);
		attachmentService.insertResumeAttachment(mbrSq, rsmSq, attachmentDtos);

	} // insertResumeFullData

	// 이력서 수정
	@PatchMapping("/resumes/{rsm_sq}")
	public void modifyResumeFullData(
			@PathVariable("rsm_sq") int rsm_sq,
			@RequestBody String Data) {
		System.out.println("컨트롤 도착 : " + rsm_sq);
		// 여기가 쫌 문제./././././

	}

	// 이력서 삭제
	@DeleteMapping("/resumes/{rsm_sq}")
	public ResponseEntity<HttpStatus> deleteResumeFullData(@PathVariable("rsm_sq") int rsm_sq) {
		System.out.println("컨트롤 도착 : " + rsm_sq);
		HttpStatus httpStatus = null;

		try {

			if (resumeManagementService.deleteResumeFullData(rsm_sq)) {
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

			if (resumeManagementService.modifyResumeyRepresentative(rsm_sq)) {
				// 성공
				httpStatus = HttpStatus.OK;
			} else {
				// 실패 로직
			}

		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}

		return new ResponseEntity<HttpStatus>(httpStatus);
	}

	// 복제
	@PostMapping("/resumes/copy/{rsm_sq}")
	public ResponseEntity<HttpStatus> copyResumeData(@PathVariable("rsm_sq") int rsm_sq) {
		System.out.println("컨트롤 도착 : " + rsm_sq);
		HttpStatus httpStatus = null;

		try {

			if (resumeManagementService.copyResumeData(rsm_sq)) {
				// 성공
				httpStatus = HttpStatus.OK;
			} else {
				httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			}

		} catch (SQLException | IOException e) {
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		}

		return new ResponseEntity<HttpStatus>(httpStatus);
	}

}
