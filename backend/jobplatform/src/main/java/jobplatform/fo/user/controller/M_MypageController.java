package jobplatform.fo.user.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import jobplatform.fo.enterprise.domain.dto.JobPostingDTO;
import jobplatform.fo.enterprise.domain.dto.JobViewDTO;
import jobplatform.fo.enterprise.domain.dto.ResumeSearchDataDTO;
import jobplatform.fo.enterprise.domain.entity.ApplyEntity;
import jobplatform.fo.enterprise.domain.entity.JobPostingEntity;
import jobplatform.fo.enterprise.domain.entity.JobViewEntity;
import jobplatform.fo.enterprise.domain.vo.JobViewVO;
import jobplatform.fo.enterprise.service.JobPostingService;
import jobplatform.fo.enterprise.service.JobViewService;
import jobplatform.fo.user.domain.entity.MemberEntity;
import jobplatform.fo.user.domain.vo.M_JobPosting_pp;
import jobplatform.fo.user.service.M_MypageService;




@RestController
@RequestMapping("/user/mypage")
public class M_MypageController {
	@Autowired
    private JobPostingService jobPostingService;

	@Autowired
	private M_MypageService myPageService;
	 @Autowired
    private JobViewService jobViewService;  // JobViewService를 주입
	//마이페이지 메인 화면 데이터
	//jwt 구현 전 까지 클라에서 mbr_sq 같이 넘겨주는 걸로!
	@GetMapping("/")
	public ResponseEntity<Map<String, Object>> getMainData(@RequestParam("mbr_sq") Long mbr_sq, @RequestParam("month") int month) {
		// System.out.println("회원정보"+myPageService.getMyPageMainData(mbr_sq, month));
		return ResponseEntity.ok(myPageService.getMyPageMainData(mbr_sq, month));
	}

	//마이페이지 메인 화면 달력 데이터
	//달력 월별 데이터 제공 *mbr_sq, rsm_sq는 어떻게 처리할지 미정
	@GetMapping("/calendar")
	public ResponseEntity<List<Map<String, Object>>> getCalendarData(@RequestParam("month") int month) {
		System.out.println("캘린더데이터"+myPageService.getToMakeCalendarData((long)1,(long)1, month));
		return ResponseEntity.ok(myPageService.getToMakeCalendarData((long)1,(long)1, month));
	}

	//마이페이지 포지션 제안 받기 화면 데이터(대표이력서, 제안 수락 여부, 선택했던 지역&직업 + 지역&직업 데이터)
	//http://localhost/user/mypage/ppAcception?mbr_sq=1
	@GetMapping("/ppAcception")
	public ResponseEntity<Map<String, Object>> getPosionProposalData(@RequestParam("mbr_sq") Long mbr_sq) {
		return ResponseEntity.ok(myPageService.getPosionProposalData(mbr_sq));
	}

	//포지션 제안받기 상태 변경
	@PatchMapping("/ppAcception/{ppyn}")
	public int putMethodName(@PathVariable String ppyn, @RequestBody Map<String, Object> data) {
		Object mbrSqObj = data.get("mbr_sq");
		Long mbrSq = null;

		if (mbrSqObj instanceof String) {
			try {
				mbrSq = Long.parseLong((String) mbrSqObj);
			} catch (NumberFormatException e) {
				// 잘못된 형식의 mbr_sq가 전달된 경우 처리
				throw new IllegalArgumentException("Invalid format for mbr_sq: " + mbrSqObj, e);
			}
		} else if (mbrSqObj instanceof Integer) {
			// mbr_sq가 Integer 타입인 경우, Long으로 변환
			mbrSq = ((Integer) mbrSqObj).longValue();
		} else {
			throw new IllegalArgumentException("Invalid data type for mbr_sq");
		}
		
		return myPageService.updatePstnPrpslAcceptYN(mbrSq, ppyn);
		// Long mbr_sq = (Long)data.get("mbr_sq");
		// return myPageService.updatePstnPrpslAcceptYN(mbr_sq, ppyn);
	}

	//포지션 제안 받기 설정 후 원하는 지역, 직종 선택 -> 기존 데이터 삭제 후 재입력
	@PostMapping("/ppAcception")
	public int postMethodName(@RequestParam("mbr_sq") Long mbr_sq, @RequestBody Map<String, Object> areaAndJobLists) {
		return myPageService.insertSelectAreasAndJobs(mbr_sq, areaAndJobLists);
	}

	//제안 받은 포지션 공고 리스트
	@GetMapping("/ppJobPost")
	public ResponseEntity<Map<String, Object>> getMethodName(@RequestParam("mbr_sq") Long mbr_sq, @RequestParam("page_num") int page_num) {
		return ResponseEntity.ok(myPageService.getPPJopPostingData(mbr_sq, page_num));
	}
	
	//제안 받은 포지션 거절
	@PatchMapping("/refusePp")
	public int refuseProposedPostion(@RequestBody Map<String, Object> data) {
		System.out.println((Long)data.get("pstn_prpsl_sq") + "번 제안이 거절되었습니다.");
		return myPageService.refuseProposedPostion((Long)data.get("pstn_prpsl_sq"));
	};
	
	//마이페이지 개인정보 수정
	@GetMapping("/infrmtn")
	public ResponseEntity<Map<String, Object>> userInfrmtn(@RequestParam("mbr_sq") Long mbr_sq) {

		return ResponseEntity.ok(myPageService.findUserINfo(mbr_sq));
	}

	// 스크랩 목록 불러오기 (정렬 일반화)
	@GetMapping("/scrap/scrapList-list/{mbr_sq}/{sort}/{pageNo}")
	public ResponseEntity<Map<String, Object>> findResumeListData(
			@PathVariable("mbr_sq") int mbr_sq,
			@PathVariable("sort") String sort,
			@PathVariable("pageNo") int pageNo) throws SQLException, IOException {

		ResumeSearchDataDTO resumeSearchDataDTO = new ResumeSearchDataDTO(mbr_sq, sort, pageNo);
		Map<String, Object> map = null;
		HttpStatus httpStatus = null;

		map = myPageService.findScrapData(resumeSearchDataDTO);
		httpStatus = HttpStatus.OK;

		return new ResponseEntity<Map<String, Object>>(map, httpStatus);

	}

	//공고 학력, 경력 공통코드 불러오기
	@GetMapping("/scrap/commonCode")
	public ResponseEntity<Map<String, Object>> findCommonCode(){
		Map<String, Object> map = null;
		map = myPageService.findCommonCode();
		return ResponseEntity.ok(map);
	}

	//스크랩
	@PostMapping("/scrap/insert/{mbr_sq}/{jbp_sq}")
	public ResponseEntity<String> ScrapIn(@PathVariable("mbr_sq") Long mbr_sq, @PathVariable("jbp_sq") Long jbp_sq){
		try {
			myPageService.scrapInsert(mbr_sq, jbp_sq);
			return ResponseEntity.status(HttpStatus.CREATED).body("스크랩 등록 성공");
		} catch (Exception e) {
			System.err.println("스크랩 등록 오류: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("스크랩 등록 실패: " + e.getMessage());
		}
	}

	//스크랩 취소
	@PostMapping("/scrap/delete/{mbr_sq}/{jbp_sq}")
	public ResponseEntity<String> ScrapOut(@PathVariable("mbr_sq") Long mbr_sq, @PathVariable("jbp_sq") Long jbp_sq){
		try {
			myPageService.scrapDelete(mbr_sq, jbp_sq);
			return ResponseEntity.status(HttpStatus.OK).body("스크랩 취소 성공");
		} catch (Exception e) {
			System.err.println("스크랩 취소 오류: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("스크랩 취소 실패: " + e.getMessage());
		}
	}

	@GetMapping("/jobView/{mbrSq}")
	public ResponseEntity<List<JobViewDTO>> getJobViewsByMember(@PathVariable Long mbrSq) {
    List<JobViewDTO> jobViews = jobViewService.getJobViewByMember(mbrSq); // 여기에서 DTO를 반환
    return ResponseEntity.ok(jobViews); // 반환 타입이 JobViewEntity가 되어야 함
	}
 // 최근 본 공고 저장
 @PostMapping("/jobView")
public ResponseEntity<?> saveJobView(@RequestBody JobViewDTO jobViewRequest) {
    try {
        Long mbrSq = jobViewRequest.getMbrSq();
        Long jbpSq = jobViewRequest.getJbpSq();
        String mbrId = jobViewRequest.getMbrId();

        // 최근 본 공고 저장 로직 (중복 방지)
        jobViewService.addJobView(mbrSq, jbpSq, mbrId);

        // 최근 본 공고 목록을 페이지네이션으로 조회
        Page<JobViewEntity> jobViews = jobViewService.getRecentJobViews(mbrSq, jobViewRequest.getPage(), jobViewRequest.getSize());

        return ResponseEntity.ok(jobViews);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("최근 본 공고 저장 중 오류가 발생했습니다.");
    }
}




 @PostMapping("/jobView/search")
public ResponseEntity<?> searchJobView(
        @RequestParam Long mbrSq, 
        @RequestParam(required = false) String enterpriseName, 
        @RequestParam(required = false) String jobTitle) {
    try {
        // 검색 결과를 서비스에서 조회
        List<JobViewDTO> jobViews = jobViewService.searchJobViews(mbrSq, enterpriseName, jobTitle);
        return ResponseEntity.ok(jobViews); // 검색된 데이터 반환
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("검색 중 오류가 발생했습니다.");
    }
}
    // 조회수 높은 상위 3개의 채용 공고 반환
    @GetMapping("/top")
    public List<JobPostingDTO> getTopJobPostings() {
        return jobPostingService.getTopJobPostings();
    }
 }
 
 
	
		
		
	
	

