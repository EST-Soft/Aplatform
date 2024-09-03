package jobplatform.fo.enterprise.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jobplatform.fo.enterprise.domain.dto.JobPostingDTO;
import jobplatform.fo.enterprise.domain.entity.ApplyEntity;
import jobplatform.fo.enterprise.domain.entity.EnterMemberEntity;
import jobplatform.fo.enterprise.domain.entity.JobPostingEntity;
import jobplatform.fo.enterprise.domain.entity.ResumeEntity;
import jobplatform.fo.enterprise.domain.repository.ApplyRepository;
import jobplatform.fo.enterprise.domain.repository.AreaRepository;
import jobplatform.fo.enterprise.domain.repository.EnterMemberRepository;
import jobplatform.fo.enterprise.domain.repository.JobPostingRepository;
import jobplatform.fo.enterprise.domain.repository.JobRepository;
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
    

    
    // 공고 리스트 조회
    @Override
    public List<JobPostingEntity> jobPostingList(String sortBy) {
        return jobPostingRepository.findAllJobPostings(sortBy);
    }

    // 공고 등록
    @Override
    public int insertJobPosting(JobPostingEntity jpe) {
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
        	int jbpSq = saveJbpSq.getJbpSq();
        	
        	return jbpSq;
        
    }







    // 공고 상세
    @Override
    @Transactional
    public JobPostingDTO jobPostingDetail(int jbpSq) {
        JobPostingEntity jpe = jobPostingRepository.findById(jbpSq)
                .orElseThrow(() -> new RuntimeException("Job posting not found with id " + jbpSq));

        EnterMemberEntity enterMember = jpe.getEnterpriseMember();

        // 기업 이름 설정
        jpe.getEnterpriseMember().setEntrprsName(enterMember.getEntrprsName());

        return JobPostingDTO.from(jpe);
    }
	

	// 조회수 증가
	@Override
	@Transactional
	public int increaseHits(int jbpSq) {
		return jobPostingRepository.increaseHits(jbpSq);
		
	}
	
	// 공고 수정
	@Override
	public void updateJobPosting(JobPostingEntity jpe) {
		// 예외처리 안해주면 Optional 쓰라고 나옴 
		JobPostingEntity updateJobPosting = jobPostingRepository.findById(jpe.getJbpSq())
				.orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다 "+jpe.getJbpSq()));
		
		
        //현재 날짜
        LocalDate today = LocalDate.now();
         
        
		updateJobPosting.setJbpTtl(jpe.getJbpTtl());
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
	public void deleteJobPosting(int jbpSq) {
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

	    ApplyEntity saveApply = applyRepository.save(ae);

	    return saveApply.getApySq();
	}



}
