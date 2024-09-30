package jobplatform.bo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import jobplatform.bo.dto.adm_JobPostingDTO;
import jobplatform.bo.mapper.adm_JobPostingMapper;

@Service
public class adm_JobPostingService {
    
    @Autowired
    private adm_JobPostingMapper jobpostingmapper;

    public List<adm_JobPostingDTO> getAllJobPostingList(){
        return jobpostingmapper.selectAllJobPostings();
        
    }

    

}
