package jobplatform.fo.user.service;

import jobplatform.fo.user.domain.dto.JobPostingApplicationDto;

public interface JobPostingApplicationService {
    JobPostingApplicationDto getJobPostingApplicationDtoByMbrSq(Long mbrSq);
}
