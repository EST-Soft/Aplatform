package jobplatform.fo.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jobplatform.fo.user.domain.dto.JobPostingApplicationDto;
import jobplatform.fo.user.domain.mapper.JobPostingApplicationMapper;

@Service
public class JobPostingApplicationServiceImpl implements JobPostingApplicationService {

    private final JobPostingApplicationMapper jobPostingApplicationMapper;

    @Autowired
    public JobPostingApplicationServiceImpl(JobPostingApplicationMapper jobPostingApplicationMapper) {
        this.jobPostingApplicationMapper = jobPostingApplicationMapper;
    }

    public JobPostingApplicationDto getJobPostingApplicationDtoByMbrSq(Long mbrSq) {
        JobPostingApplicationDto dto = jobPostingApplicationMapper.getJobPostingApplicationDtoByMbrSq(mbrSq);
        System.out.println(dto);
        return dto;
    }
}
