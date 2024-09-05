package jobplatform.fo.enterprise.controller;

import java.awt.geom.Area;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties.Job;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jobplatform.fo.enterprise.domain.dto.JobPostingDTO;
import jobplatform.fo.enterprise.domain.entity.ApplyEntity;
import jobplatform.fo.enterprise.domain.entity.AreaEntity;
import jobplatform.fo.enterprise.domain.entity.JobEntity;
import jobplatform.fo.enterprise.domain.entity.JobPostingEntity;
import jobplatform.fo.enterprise.domain.entity.ResumeEntity;
import jobplatform.fo.enterprise.domain.repository.AreaRepository;
import jobplatform.fo.enterprise.domain.repository.EnterMemberRepository;
import jobplatform.fo.enterprise.domain.repository.JobRepository;
import jobplatform.fo.enterprise.domain.repository.ResumeRepository;
import jobplatform.fo.enterprise.service.JobPostingService;
import lombok.extern.log4j.Log4j2;


@Log4j2
@RestController
public class JobPostingController {
	
	@Autowired  
	private JobPostingService jobPostingService;
	
	@Autowired
	private EnterMemberRepository enterMemberRepository;
	
	@Autowired
	private ResumeRepository resumeRepository;
	
	@Autowired
	private AreaRepository areaRepository;
	
	@Autowired
	private JobRepository jobRepository;
	
	@GetMapping("/board/list/jobPosting")
	public ResponseEntity<List<JobPostingEntity>> jobPostingList(@RequestParam(value = "sortBy", defaultValue = "regstrStrtDtm") String sortBy) {
	    List<JobPostingEntity> jobPostings = jobPostingService.jobPostingList(sortBy);
	    return ResponseEntity.ok(jobPostings);
	}

	
	// 공고 등록 메소드
	@PostMapping("/board/jobPostingInsert")
	public ResponseEntity<Map<String, Object>> insertJobPosting(@RequestBody JobPostingEntity jpe) {
	    int insertedJbpSq = jobPostingService.insertJobPosting(jpe);

		System.out.println("jpe :: " + jpe);
	    
	    // 상세 페이지로 이동
	    String detailUrl = "/board/detail/jobPosting/" + insertedJbpSq;
	    
	  
	    Map<String, Object> response = new HashMap<>();
	    response.put("detailUrl", detailUrl);
	    response.put("jbpSq", insertedJbpSq);
	    
	    return ResponseEntity.ok(response);
	}

	  // 지역 목록
	  @GetMapping("/areas")
	  public ResponseEntity<List<AreaEntity>> getAllAreas() {
	    List<AreaEntity> areas = areaRepository.findAll();
	    System.out.println("지역");
	    return ResponseEntity.ok(areas);
	  }

	  //직업 목록
	  @GetMapping("/jobs")
	  public ResponseEntity<List<JobEntity>> getAllJobs() {
	    List<JobEntity> jobs = jobRepository.findAll();
	    System.out.println("직업" );
	    return ResponseEntity.ok(jobs);
	  }

	
	// 공고 상세 조회 메소드
	@GetMapping("/board/detail/jobPosting/{jbpSq}")
	public JobPostingDTO JobPostingDetail(@PathVariable int jbpSq) {
		JobPostingDTO jpe = jobPostingService.jobPostingDetail(jbpSq);
		
		// 조회수 증가
		jobPostingService.increaseHits(jbpSq);
		
		return jpe;
	}
	
	// 공고 수정 메소드
	@PostMapping("/board/jobPostingUpdate/{jbpSq}")
	public void updateJobPosting(@PathVariable int jbpSq , @RequestBody JobPostingEntity jpe) {
		jobPostingService.updateJobPosting(jpe);
	}
	
	
	// 공고 삭제 메소드
	@DeleteMapping("/board/jobPostingDelete/{jbpSq}")
	public void deleteJobPosting(@PathVariable int jbpSq) {
		jobPostingService.deleteJobPosting(jbpSq);
	}
	
	// 공고 검색 메소드
    @GetMapping("/board/search")
    public List<JobPostingEntity> searchJobPostings(
            @RequestParam(value = "searchTerm", required = false) String searchTerm,
            @RequestParam(value = "searchField", defaultValue = "jbpTl_jbpCntnt") String searchField) {
        	System.out.println("검색 잘 되니"+searchTerm);
        	System.out.println("검색 잘 되니"+searchField);
        return jobPostingService.searchJobPostings(searchTerm, searchField);
    }
    
    // 입사지원 메소드
    @PostMapping("/apply/insert")
    public ResponseEntity<String> insertApply(@RequestBody ApplyEntity ae) {
        
        Optional<ResumeEntity> optionalResume = resumeRepository.findByRsmSq(ae.getResume().getRsmSq());
        if (optionalResume.isPresent()) {
            ae.setResume(optionalResume.get());
            Long apySq = jobPostingService.insertApply(ae);

            if (apySq != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body("입사지원 성공 : " + apySq);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("입사지원 실패");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("이력서 찾을 수 없음");
        }
    }

	
}
