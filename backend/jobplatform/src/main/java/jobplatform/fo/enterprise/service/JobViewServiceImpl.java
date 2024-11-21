package jobplatform.fo.enterprise.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpSession;
import jobplatform.fo.enterprise.domain.dto.JobPostingDTO;
import jobplatform.fo.enterprise.domain.dto.JobViewDTO;
import jobplatform.fo.enterprise.domain.entity.JobPostingEntity;
import jobplatform.fo.enterprise.domain.entity.JobViewEntity;
import jobplatform.fo.enterprise.domain.repository.JobPostingRepository;
import jobplatform.fo.enterprise.domain.repository.JobViewRepository;
import jobplatform.fo.user.domain.mapper.M_MypageMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
@Service
public class JobViewServiceImpl implements JobViewService {

   
    private final JobViewRepository jobViewRepository;
    @Autowired
    private JobPostingRepository jobPostingRepository;
    @Autowired
    public JobViewServiceImpl(JobViewRepository jobViewRepository) {
        this.jobViewRepository = jobViewRepository;
    }
    
    // 1. 특정 회원이 본 직업 공고 목록 조회
   // 특정 회원이 본 직업 공고 목록 조회 (회원 순번 mbrSq로 조회)
   @Override
   public List<JobViewDTO> getJobViewByMember(Long mbrSq) {
    // 최신순으로 JobViewEntity 목록을 조회
    List<JobViewEntity> jobViewEntities = jobViewRepository.findByMbrSqOrderByViewDateDesc(mbrSq);

    // JobViewEntity 리스트를 JobViewDTO 리스트로 변환하여 반환
    return jobViewEntities.stream()
            .map(JobViewDTO::from)  // JobViewEntity -> JobViewDTO 변환
            .collect(Collectors.toList());
}

    // JobViewDTO 리스트 반환
    public List<JobViewDTO> searchJobViews(Long mbrSq, String enterpriseName, String jobTitle) {
        // Repository에서 데이터를 조회하여 DTO로 변환하여 반환
        List<JobViewDTO> jobViews = jobViewRepository.findJobViews(enterpriseName, jobTitle, mbrSq);
        return jobViews;
    }

    
   // 최근 본 공고 추가 또는 갱신
   @Override
   public void addJobView(Long mbrSq, Long jbpSq, String mbrId) {
       // 1. 공고 순번(jbpSq)을 기준으로 JobPostingEntity 조회
       JobPostingEntity jobPosting = jobPostingRepository.findById(jbpSq)
               .orElseThrow(() -> new RuntimeException("Job posting not found for jbpSq: " + jbpSq));
   
       // 이미 본 공고인지 체크
       Optional<JobViewEntity> existingJobView = jobViewRepository.findByMbrSqAndJbpSq(mbrSq, jbpSq);
   
       if (existingJobView.isPresent()) {
           // 2. 이미 본 공고가 있다면, 해당 공고의 조회 시간을 최신으로 업데이트
           JobViewEntity jobViewEntity = existingJobView.get();
           jobViewEntity.setViewDate(LocalDateTime.now()); // 최신 시간으로 갱신
           jobViewRepository.save(jobViewEntity); // 업데이트된 entity 저장
   
           System.out.println("기존 공고의 조회 시간을 갱신했습니다: mbrSq=" + mbrSq + ", jbpSq=" + jbpSq);
       } else {
           // 3. 새로운 공고를 본 경우에는 새 JobViewEntity를 생성
           JobViewEntity jobViewEntity = new JobViewEntity();
           jobViewEntity.setMbrSq(mbrSq);  // 회원 순번
           jobViewEntity.setJbpSq(jbpSq);  // 공고 순번
           jobViewEntity.setMbrId(mbrId);  // 회원 ID
           jobViewEntity.setViewDate(LocalDateTime.now());  // 최근 본 시간 (현재 시간)
           jobViewEntity.setJobPosting(jobPosting);  // 공고 정보 설정
   
           // 4. JobViewEntity 저장
           jobViewRepository.save(jobViewEntity);
   
           System.out.println("새 공고를 저장했습니다: mbrSq=" + mbrSq + ", jbpSq=" + jbpSq);
       }
   }
   
    // 최근 본 공고 목록을 페이지네이션으로 조회
    public Page<JobViewEntity> getRecentJobViews(Long mbrSq, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("viewDate")));  // 예: 최근 조회순으로 정렬
        return jobViewRepository.findRecentJobViewsByMbrSq(mbrSq, pageable);
    }
    


 // 최근 본 공고 기록 추가 또는 갱신
 @Override
 @Transactional
 public void addJobViewEntity(Long mbrSq, Long jbpSq, String mbrId) {
     // JobViewEntity 객체를 생성 (현재 시간을 저장)
     JobViewEntity jobViewEntity = new JobViewEntity(mbrSq, jbpSq, LocalDateTime.now(), mbrId);

     // JobViewRepository를 통해 DB에 저장
     jobViewRepository.save(jobViewEntity);  // 엔티티를 DB에 저장

     System.out.println("최근 본 공고가 DB에 저장되었습니다: 사용자 순번 = " + mbrSq + ", 공고 순번 = " + jbpSq + ", 사용자 ID = " + mbrId);
 }

 
 @Override
 public void saveJobView(Long mbrSq, Long jbpSq, String mbrId) {
     // 1. JobViewEntity 객체 생성
     if (!existsJobView(mbrSq, jbpSq)) {
     JobViewEntity jobViewEntity = new JobViewEntity();
     jobViewEntity.setMbrSq(mbrSq);  // 회원 순번
     jobViewEntity.setJbpSq(jbpSq);  // 공고 순번
     jobViewEntity.setMbrId(mbrId);  // 회원 ID
     jobViewEntity.setViewDate(LocalDateTime.now());  // 최근 본 시간 (현재 시간)
 
     // 2. JobViewEntity를 DB에 저장
     jobViewRepository.save(jobViewEntity);
    } else {
        System.out.println("이미 최근 본 공고에 존재하는 공고입니다.");
 }
}
     // 공고 조회 기록이 이미 존재하는지 확인
     @Override
     public boolean existsJobView(Long mbrSq, Long jbpSq) {
         // 중복 확인 전에 로그 찍어보기
         System.out.println("Checking if job view exists for member: " + mbrSq + " and job posting: " + jbpSq);
         
         boolean exists = jobViewRepository.existsByMbrSqAndJbpSq(mbrSq, jbpSq);
         
         System.out.println("Job view exists: " + exists);
         return exists;
     }

}
 
 
