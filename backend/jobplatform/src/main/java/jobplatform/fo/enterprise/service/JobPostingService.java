package jobplatform.fo.enterprise.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import jobplatform.fo.enterprise.domain.dto.JobPostingDTO;
import jobplatform.fo.enterprise.domain.entity.ApplyEntity;
import jobplatform.fo.enterprise.domain.entity.JobPostingEntity;

@Service
public interface JobPostingService {

    // 공고 리스트 조회
    public List<JobPostingEntity> jobPostingList(String sortBy);

    // 공고 상세 조회
    public JobPostingDTO jobPostingDetail(Long jbpSq, HttpSession session);

    // 조회수 증가
    public Long increaseHits(Long jbpSq);

    // 공고 수정
    public void updateJobPosting(JobPostingEntity jpe);

    // 공고 삭제
    public void deleteJobPosting(Long jbpSq);

    // 공고 검색
    public List<JobPostingEntity> searchJobPostings(String searchTerm, String searchField);

    // 지원 정보 추가
    public Long insertApply(ApplyEntity ae);

    // 공고 등록
    public Long insertJobPosting(JobPostingEntity jpe);

    // 최근 본 공고 리스트 조회
    public List<JobPostingDTO> getJobPostings();

    // 최근 본 공고 추가
    public void addJobView(Long mbrSq, Long jbpSq, String mbrId);

    // 최근 본 공고 엔터티 추가
    public void addJobViewEntity(Long mbrSq, Long jbpSq, String mbrId);

    // 조회수 높은 상위 공고 리스트
    public List<JobPostingDTO> getTopJobPostings();

    // 특정 기업의 공고 리스트 조회
    public List<JobPostingEntity> myJobPostingList(String sortBy, Long entrprsSq);

    // 마감일 연장
    public void extendDeadline(Long jbpSq, int daysToAdd);
}
