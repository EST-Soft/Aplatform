package jobplatform.bo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jobplatform.bo.dto.adm_JobPostingDTO;
import jobplatform.bo.service.adm_JobPostingService;


@RestController
@RequestMapping("/admin") // 기본 경로 설정
public class adm_JobPostingController {

    private final adm_JobPostingService jobpostingservice;

    public adm_JobPostingController(adm_JobPostingService jobpostingservice){
        this.jobpostingservice = jobpostingservice;
    }


    @GetMapping("/board/JobPosting")
    public List<adm_JobPostingDTO> getAllJobPostings(){
        return jobpostingservice.getAllJobPostingList();
    }
    
    
}
