package jobplatform.fo.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import jobplatform.fo.user.domain.dto.JobPostingApplicationDto;
import jobplatform.fo.user.service.JobPostingApplicationService;


@RestController
public class JobPostingApplicationController {
    private final JobPostingApplicationService jobPostingApplicationService;

    @Autowired
    public JobPostingApplicationController(JobPostingApplicationService jobPostingApplicationService) {
        this.jobPostingApplicationService = jobPostingApplicationService;
    }

    @GetMapping("/apply/{mbrSq}")
    ResponseEntity<JobPostingApplicationDto> getJobPostingApplicationDtoByMbrSq(@PathVariable("mbrSq") Long mbrSq) {
        JobPostingApplicationDto dto = jobPostingApplicationService.getJobPostingApplicationDtoByMbrSq(mbrSq);
        return ResponseEntity.ok(dto);
    }
}
