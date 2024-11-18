package jobplatform.fo.enterprise.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import jobplatform.fo.enterprise.domain.dto.JobPostingDTO;
import jobplatform.fo.enterprise.domain.entity.ApplyEntity;
import jobplatform.fo.enterprise.domain.entity.EnterMemberEntity;
import jobplatform.fo.enterprise.domain.entity.JobPostingEntity;
import jobplatform.fo.enterprise.domain.entity.JobViewEntity;
import jobplatform.fo.enterprise.domain.entity.ResumeEntity;
import jobplatform.fo.enterprise.domain.repository.ApplyRepository;
import jobplatform.fo.enterprise.domain.repository.AreaRepository;
import jobplatform.fo.enterprise.domain.repository.EnterMemberRepository;
import jobplatform.fo.enterprise.domain.repository.JobPostingRepository;
import jobplatform.fo.enterprise.domain.repository.JobRepository;
import jobplatform.fo.enterprise.domain.repository.JobViewRepository;
import jobplatform.fo.enterprise.domain.repository.ResumeRepository;


@Service
public class JobPostingServiceImpl implements JobPostingService {
    
    @Autowired
    private JobPostingRepository jobPostingRepository;

    @Autowired
    private EnterMemberRepository enterMemberRepository;
    
    @Autowired
    private ResumeRepository resumeRepository;
    
    @Autowired
    private ApplyRepository applyRepository; 
    
    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private AreaRepository areaRepository;
    private final JobViewService jobViewService;
        @Autowired
    private JobViewRepository jobViewRepository;

     @Autowired
    public JobPostingServiceImpl(@Lazy JobViewService jobViewService) {
        this.jobViewService = jobViewService;
    }
    

    
    // 공고 리스트 조회
    @Override
    public List<JobPostingEntity> jobPostingList(String sortBy) {
        return jobPostingRepository.findAllJobPostings(sortBy);
    }

    // 공고 등록
    @Override
    public Long insertJobPosting(JobPostingEntity jpe) {
        EnterMemberEntity enterpriseMember = enterMemberRepository.findById(jpe.getEnterpriseMember().getEntrprsSq())
            .orElseThrow(() -> new RuntimeException("EnterpriseMember not found with id " + jpe.getEnterpriseMember().getEntrprsSq()));
        
        jpe.setEnterpriseMember(enterpriseMember);
        
        //현재 날짜
        LocalDate today = LocalDate.now();
        
        // 등록 시작일이 오늘일 경우 진행중 아니면 예정으로
        if (jpe.getRegstrStrtDtm() != null && jpe.getRegstrStrtDtm().isEqual(today)) {
            jpe.setJbpCndtn("702");
        } else {
            jpe.setJbpCndtn("701");
        }    
               JobPostingEntity saveJbpSq = jobPostingRepository.save(jpe);
        	Long jbpSq = saveJbpSq.getJbpSq();
        	
        	return jbpSq;
        
    }



 // 최근 본 공고 기록 추가 또는 갱신
 @Override
 @Transactional
 public void addJobViewEntity(Long mbrSq, Long jbpSq, String mbrId) {
     // 현재 시간을 viewDate로 설정
     JobViewEntity jobViewEntity = new JobViewEntity(mbrSq, jbpSq, LocalDateTime.now(), mbrId);

     // JobViewEntity를 DB에 저장
     jobViewRepository.save(jobViewEntity);  // 이 메소드가 INSERT 쿼리를 자동으로 처리
     System.out.println("DB에 최근 본 공고 저장됨: 사용자 순번 = " + mbrSq + ", 공고 순번 = " + jbpSq + ", 사용자 ID = " + mbrId);
 }

    // 공고 상세
    @Override
    @Transactional
    public JobPostingDTO jobPostingDetail(Long jbpSq, HttpSession session) {
        // 세션에서 회원 ID 가져오기
        Long mbrSq = (Long) session.getAttribute("mbrSq");

        // 비로그인 사용자의 경우, 최근 본 공고 기록하지 않음
        if (mbrSq != null) {
            // 로그인한 사용자라면 최근 본 공고 기록 추가
            LocalDateTime viewDate = LocalDateTime.now(); // 현재 시간 설정
            jobViewRepository.insertJobView(mbrSq, jbpSq, viewDate);  // JobView 기록
        }

        // 공고 조회
        JobPostingEntity jpe = jobPostingRepository.findById(jbpSq)
                .orElseThrow(() -> new RuntimeException("Job posting not found with id " + jbpSq));

        // JobPostingDTO 반환
        return JobPostingDTO.from(jpe);
    }

	

	// 조회수 증가
	@Override
	@Transactional
	public Long increaseHits(Long jbpSq) {
		int updatedRows = jobPostingRepository.increaseHits(jbpSq);
        // int to Long conversion
        return (long) updatedRows;
	}
	
	// 공고 수정
	@Override
	public void updateJobPosting(JobPostingEntity jpe) {
		// 예외처리 안해주면 Optional 쓰라고 나옴 
		JobPostingEntity updateJobPosting = jobPostingRepository.findById(jpe.getJbpSq())
				.orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다 "+jpe.getJbpSq()));
		
		
        //현재 날짜
        LocalDate today = LocalDate.now();
        
		// System.out.println("서비스에서 받는 jpe ; " + jpe);
		
        
		updateJobPosting.setJbpTtl(jpe.getJbpTtl());
		updateJobPosting.setJbpCntnt(jpe.getJbpCntnt());
		updateJobPosting.setJbpCndtn(jpe.getJbpCndtn());
		updateJobPosting.setJobName(jpe.getJobName());
		updateJobPosting.setCrrDrtn(jpe.getCrrDrtn());
		updateJobPosting.setEdctn(jpe.getEdctn());
		updateJobPosting.setWorkArea(jpe.getWorkArea());
		updateJobPosting.setWorkForm(jpe.getWorkForm());
		updateJobPosting.setWorkHour(jpe.getWorkHour());
		updateJobPosting.setSlry(jpe.getSlry());
		updateJobPosting.setSklName(jpe.getSklName());
		updateJobPosting.setUpdtDtm(LocalDateTime.now());
	    updateJobPosting.setRegstrStrtDtm(jpe.getRegstrStrtDtm()); // 등록 시작일 설정
	    
	    // 등록 시작일이 오늘일 경우 702 아니면 701로
	    if (jpe.getRegstrStrtDtm() != null && jpe.getRegstrStrtDtm().isEqual(today)) {
	        updateJobPosting.setJbpCndtn("702");
	    } else {
	        updateJobPosting.setJbpCndtn("701");
	    }
		updateJobPosting.setRegstrDlnDtm(jpe.getRegstrDlnDtm());
		updateJobPosting.setPicEml(jpe.getPicEml());
		updateJobPosting.setPicMp(jpe.getPicMp());
		updateJobPosting.setPicName(jpe.getPicName());
		
		jobPostingRepository.save(updateJobPosting);
		
		
	}

	// 공고 삭제 메소드
	@Override
	public void deleteJobPosting(Long jbpSq) {
		jobPostingRepository.deleteById(jbpSq);
		
	}
	
	// 공고 검색 메소드
	@Override
	public List<JobPostingEntity> searchJobPostings(String searchTerm, String searchField) {
	    //제목 또는 내용
		if ("jbpTtl_jbpCntnt".equals(searchField)) {
	        return jobPostingRepository.findByJbpTtlContainingOrJbpCntntContaining(searchTerm, searchTerm);
	    // 제목
		} else if ("jbpTtl".equals(searchField)) {
	        return jobPostingRepository.findByJbpTtlContaining(searchTerm);
	    // 내용
		} else if ("jbpCntnt".equals(searchField)) {
	        return jobPostingRepository.findByJbpCntntContaining(searchTerm);
	    } else {
	        return jobPostingRepository.findAll();
	    }
}

	@Override
	public Long insertApply(ApplyEntity ae) {
	    ResumeEntity resume = resumeRepository.findById(ae.getResume().getRsmSq())
	            .orElseThrow(() -> new IllegalArgumentException("이력서를 찾을 수 없습니다. rsmSq: " + ae.getResume().getRsmSq()));

	    // 지원테이블에 이력서 번호 설정
	    ae.setResume(resume);

		if (applyRepository.existsByResumeRsmSqAndJobPostingJbpSq(ae.getResume().getRsmSq(), ae.getJobPosting().getJbpSq())) {
			throw new IllegalArgumentException("이미 지원한 이력서입니다.");
		}

	    ApplyEntity saveApply = applyRepository.save(ae);

	    return saveApply.getApySq();
	}

    @Override
    public List<JobPostingDTO> getJobPostings() {
        // 데이터베이스에서 모든 채용 공고를 조회
        List<JobPostingEntity> jobPostings = jobPostingRepository.findAll();

        // 엔티티 리스트를 DTO 리스트로 변환
        return jobPostings.stream()
                          .map(JobPostingDTO::from)
                          .collect(Collectors.toList());
    }



    @Override
    public void addJobView(Long mbrSq, Long jbpSq, String mbrId) {
        // 1. 중복 체크: 이미 동일한 공고와 회원 순번이 존재하는지 확인
        Optional<JobViewEntity> existingJobView = jobViewRepository.findByMbrSqAndJbpSq(mbrSq, jbpSq);
        
        if (existingJobView.isPresent()) {
            // 중복이 존재하면 새로 저장하지 않음
            System.out.println("이미 본 공고가 있습니다. 저장하지 않습니다.");
            return;  // 중복이 존재하면 메서드를 종료
        }
    
        // 2. JobViewEntity 객체 생성
        JobViewEntity jobViewEntity = new JobViewEntity();
        jobViewEntity.setMbrSq(mbrSq);  // 회원 순번
        jobViewEntity.setJbpSq(jbpSq);  // 공고 순번
        jobViewEntity.setMbrId(mbrId);  // 회원 ID
        
        jobViewEntity.setViewDate(LocalDateTime.now());  // 최근 본 시간 (현재 시간)
        
        // 3. JobViewEntity를 DB에 저장
        jobViewRepository.save(jobViewEntity);
        
        // 4. 저장된 데이터를 로그로 출력 (디버깅 용도)
        System.out.println("최근 본 공고 저장: " + jobViewEntity);
    }
    
    



}
