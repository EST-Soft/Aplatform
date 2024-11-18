package jobplatform.fo.enterprise.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import jobplatform.fo.enterprise.domain.dto.JobViewDTO;
import jobplatform.fo.enterprise.domain.entity.JobViewEntity;

@Service
public interface JobViewService {

    // addJobViewEntity 메소드 정의
     // 회원이 본 직업 공고 목록 조회 (페이지네이션 제외)
     List<JobViewDTO> getJobViewByMember(Long mbrSq);

     void addJobViewEntity(Long mbrSq, Long jbpSq, String mbrId);
 
     void addJobView(Long mbrSq, Long jbpSq, String mbrId);
 
     void saveJobView(Long mbrSq, Long jbpSq, String mbrId);
     
     boolean existsJobView(Long mbrSq, Long jbpSq);

    List<JobViewDTO> searchJobViews(Long mbrSq, String enterpriseName, String jobTitle);

    Page<JobViewEntity> getRecentJobViews(Long mbrSq, int page, int size);

    //List<JobViewDTO> getJobViewByMember(Long mbrSq);


}
