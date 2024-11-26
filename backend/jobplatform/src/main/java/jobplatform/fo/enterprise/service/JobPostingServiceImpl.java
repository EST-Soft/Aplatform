package jobplatform.fo.enterprise.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Autowired
    private JobViewRepository jobViewRepository;

    private final JobViewService jobViewService;

    @Autowired
    public JobPostingServiceImpl(@Lazy JobViewService jobViewService) {
        this.jobViewService = jobViewService;
    }

    // 공고 리스트 조회
    @Override
    public List<JobPostingEntity> jobPostingList(String sortBy) {
        return jobPostingRepository.findAllJobPostings(sortBy);
    }

    // 특정 공고 리스트 조회
    @Override
    public List<JobPostingEntity> myJobPostingList(String sortBy, Long entrprsSq) {
        if (entrprsSq != null) {
            // 특정 회사의 공고만 조회
            return jobPostingRepository.findByEntrprsSq(entrprsSq, sortBy);
        } else {
            // 모든 공고 조회
            return jobPostingRepository.findAllJobPostings(sortBy);
        }
    }

    // 공고 등록
    @Override
    public Long insertJobPosting(JobPostingEntity jpe) {
        EnterMemberEntity enterpriseMember = enterMemberRepository.findById(jpe.getEnterpriseMember().getEntrprsSq())
                .orElseThrow(() -> new RuntimeException(
                        "EnterpriseMember not found with id " + jpe.getEnterpriseMember().getEntrprsSq()));

        jpe.setEnterpriseMember(enterpriseMember);

        // 현재 날짜
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

    // 마감일 연장
    @Transactional
    @Override
    public void extendDeadline(Long jbpSq, int daysToAdd) {
        JobPostingEntity jobPostingEntity = jobPostingRepository.findById(jbpSq)
                .orElseThrow(() -> new RuntimeException("Job posting not found"));

        LocalDate currentDeadline = jobPostingEntity.getRegstrDlnDtm();
        LocalDate newDeadline = currentDeadline.plusDays(daysToAdd);

        int updatedCount = jobPostingRepository.extendDeadline(jbpSq, newDeadline);
        if (updatedCount == 0) {
            throw new RuntimeException("Failed to extend deadline for job posting");
        }
    }


    // 최근 본 공고 기록 추가
    @Override
    @Transactional
    public void addJobViewEntity(Long mbrSq, Long jbpSq, String mbrId) {
        JobViewEntity jobViewEntity = new JobViewEntity(mbrSq, jbpSq, LocalDateTime.now(), mbrId);
        jobViewRepository.save(jobViewEntity);
        System.out.println("DB에 최근 본 공고 저장됨: 사용자 순번 = " + mbrSq + ", 공고 순번 = " + jbpSq + ", 사용자 ID = " + mbrId);
    }

    // 공고 상세

    @Override
    @Transactional
    public JobPostingDTO jobPostingDetail(Long jbpSq, HttpSession session) {
        Long mbrSq = (Long) session.getAttribute("mbrSq");

        if (mbrSq != null) {
            LocalDateTime viewDate = LocalDateTime.now();
            jobViewRepository.insertJobView(mbrSq, jbpSq, viewDate);
        }

        JobPostingEntity jpe = jobPostingRepository.findById(jbpSq)
                .orElseThrow(() -> new RuntimeException("Job posting not found with id " + jbpSq));



        // JobPostingDTO 반환

        EnterMemberEntity enterMember = jpe.getEnterpriseMember();

        // 기업 이름 설정 
        jpe.getEnterpriseMember().setEntrprsName(enterMember.getEntrprsName());



        return JobPostingDTO.from(jpe);
    }

    // 조회수 증가
    @Override
    @Transactional
    public Long increaseHits(Long jbpSq) {
        int updatedRows = jobPostingRepository.increaseHits(jbpSq);
        return (long) updatedRows;
    }

    // 공고 수정
    @Override
    public void updateJobPosting(JobPostingEntity jpe) {
        JobPostingEntity updateJobPosting = jobPostingRepository.findById(jpe.getJbpSq())
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다. " + jpe.getJbpSq()));

        LocalDate today = LocalDate.now();

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
        updateJobPosting.setRegstrStrtDtm(jpe.getRegstrStrtDtm());

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

    // 공고 삭제
    @Override
    public void deleteJobPosting(Long jbpSq) {
        jobPostingRepository.deleteById(jbpSq);
    }

    // 공고 검색
    @Override
    public List<JobPostingEntity> searchJobPostings(String searchTerm, String searchField) {
        System.out.println("searchTerm: " + searchTerm + ", searchField: " + searchField);
    
        // 빈 검색어 처리
        if (searchTerm == null || searchTerm.isEmpty()) {
            List<JobPostingEntity> allPostings = jobPostingRepository.findAll();
            System.out.println("findAll() 호출 결과: " + allPostings);
            return allPostings;
        }
    
        // searchField 값 확인
        System.out.println("검색 조건에 해당하는 필드: " + searchField);
    
        // 검색 조건 처리
        switch (searchField) {
            case "jbpTtl_jbpCntnt":
                List<JobPostingEntity> result1 = jobPostingRepository.findByJbpTtlContainingOrJbpCntntContaining(searchTerm, searchTerm);
                System.out.println("findByJbpTtlContainingOrJbpCntntContaining 결과: " + result1);
                return result1;
            case "jbpTtl":
                List<JobPostingEntity> result2 = jobPostingRepository.findByJbpTtlContaining(searchTerm);
                System.out.println("findByJbpTtlContaining 결과: " + result2);
                return result2;
            case "jbpCntnt":
                List<JobPostingEntity> result3 = jobPostingRepository.findByJbpCntntContaining(searchTerm);
                System.out.println("findByJbpCntntContaining 결과: " + result3);
                return result3;
            default:
                System.out.println("기본값으로 처리됨: " + searchField);  // default가 호출된 경우 로그 추가
                return jobPostingRepository.findAll();
        }
    }
    
    

    // 지원 등록
    @Override
    public Long insertApply(ApplyEntity ae) {
        ResumeEntity resume = resumeRepository.findById(ae.getResume().getRsmSq())
                .orElseThrow(() -> new IllegalArgumentException(
                        "이력서를 찾을 수 없습니다. rsmSq: " + ae.getResume().getRsmSq()));

        ae.setResume(resume);

        if (applyRepository.existsByResumeRsmSqAndJobPostingJbpSq(ae.getResume().getRsmSq(), ae.getJobPosting().getJbpSq())) {
            throw new IllegalArgumentException("이미 지원한 이력서입니다.");
        }

        ApplyEntity saveApply = applyRepository.save(ae);
        return saveApply.getApySq();
    }

    @Override
    public List<JobPostingDTO> getJobPostings() {
        List<JobPostingEntity> jobPostings = jobPostingRepository.findAll();
        return jobPostings.stream()
                .map(JobPostingDTO::from)
                .collect(Collectors.toList());
    }

    @Override
    public void addJobView(Long mbrSq, Long jbpSq, String mbrId) {
        Optional<JobViewEntity> existingJobView = jobViewRepository.findByMbrSqAndJbpSq(mbrSq, jbpSq);
        if (existingJobView.isPresent()) {
            System.out.println("이미 본 공고가 있습니다. 저장하지 않습니다.");
            return;
        }

        JobViewEntity jobViewEntity = new JobViewEntity();
        jobViewEntity.setMbrSq(mbrSq);
        jobViewEntity.setJbpSq(jbpSq);
        jobViewEntity.setMbrId(mbrId);
        jobViewEntity.setViewDate(LocalDateTime.now());

        jobViewRepository.save(jobViewEntity);
        System.out.println("최근 본 공고 저장: " + jobViewEntity);
    }

    // 조회수 높은 상위 3개 채용 공고
    public List<JobPostingDTO> getTopJobPostings() {
        List<JobPostingEntity> jobPostings = jobPostingRepository.findTop3ByOrderByHitsDesc();
        return jobPostings.stream()
                .map(JobPostingDTO::from)
                .collect(Collectors.toList());
    }
}
