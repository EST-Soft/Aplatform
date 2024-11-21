package jobplatform.fo.enterprise.service;


import java.util.List;

import org.springframework.stereotype.Service;

import jobplatform.fo.enterprise.domain.dto.JobRecommendationDTO;


@Service
public interface JobRecommendationService  {
  public List<JobRecommendationDTO> recommendJobs(long rsmSq, List<String> selectedSkills);
	




	
}
