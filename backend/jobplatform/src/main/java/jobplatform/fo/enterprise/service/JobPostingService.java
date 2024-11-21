package jobplatform.fo.enterprise.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import jobplatform.fo.enterprise.domain.dto.JobPostingDTO;
import jobplatform.fo.enterprise.domain.entity.ApplyEntity;
import jobplatform.fo.enterprise.domain.entity.JobPostingEntity;

@Service
public interface JobPostingService {

	public List<JobPostingEntity> jobPostingList(String sortBy);

	// public int insertJobPosting(JobPostingEntity jpe);

	public JobPostingDTO jobPostingDetail(Long jbpSq,HttpSession session);

	public Long increaseHits(Long jbpSq);

	public void updateJobPosting(JobPostingEntity jpe);

	public void deleteJobPosting(Long jbpSq);


	public List<JobPostingEntity> searchJobPostings(String searchTerm, String searchField);

	public Long insertApply(ApplyEntity ae);

	public Long insertJobPosting(JobPostingEntity jpe);

<<<<<<< HEAD
	public List<JobPostingDTO> getJobPostings();
	public void addJobView(Long mbrSq, Long jbpSq, String mbrId);
	public void addJobViewEntity(Long mbrSq, Long jbpSq, String mbrId);
	public List<JobPostingDTO> getTopJobPostings();
=======
    public List<JobPostingEntity> myJobPostingList(String sortBy, Long entrprsSq);

    public void extendDeadline(Long jbpSq, int daysToAdd);



>>>>>>> 58ab0f1bab39a51ff54013fbc41654e6b3f41c97


	


	
}