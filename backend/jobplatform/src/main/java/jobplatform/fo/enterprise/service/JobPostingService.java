package jobplatform.fo.enterprise.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jobplatform.fo.enterprise.domain.dto.JobPostingDTO;
import jobplatform.fo.enterprise.domain.entity.ApplyEntity;
import jobplatform.fo.enterprise.domain.entity.JobPostingEntity;

@Service
public interface JobPostingService {

	public List<JobPostingEntity> jobPostingList(String sortBy);

	// public int insertJobPosting(JobPostingEntity jpe);

	public JobPostingDTO jobPostingDetail(Long jbpSq);

	public Long increaseHits(Long jbpSq);

	public void updateJobPosting(JobPostingEntity jpe);

	public void deleteJobPosting(Long jbpSq);


	public List<JobPostingEntity> searchJobPostings(String searchTerm, String searchField);

	public Long insertApply(ApplyEntity ae);

	public Long insertJobPosting(JobPostingEntity jpe);

    public List<JobPostingEntity> myJobPostingList(String sortBy, Long entrprsSq);

    public void extendDeadline(Long jbpSq, int daysToAdd);





	


	
}