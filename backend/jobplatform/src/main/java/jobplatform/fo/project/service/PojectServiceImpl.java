package jobplatform.fo.project.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import jobplatform.fo.enterprise.domain.entity.JobPostingEntity;
import jobplatform.fo.enterprise.domain.entity.ProjectListEntity;
import jobplatform.fo.enterprise.domain.repository.ProjectRepository;
import jobplatform.fo.project.domain.ProjectDomain;
import jobplatform.fo.project.mapper.ProjectMapper;

@Service
public class PojectServiceImpl implements ProjectService {


    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ProjectRepository projectRepository;
    
    
    // public List<ProjectDomain> projectList(HashMap<String, Object> params) {
    //     return projectMapper.projectList(params);
    // }

    @Override
    public List<ProjectDomain> projectList(){
        return projectMapper.projectList();
    }

    @Override
    public ProjectDomain projectRead(int prjctSq) {
        return projectMapper.projectRead(prjctSq);
    }

    @Override
    public int projectInsert(ProjectDomain projectEntity) {
        return projectMapper.projectInsert(projectEntity);
    }

    @Override
    public int projectUpdate(ProjectDomain projectEntity) {
        return projectMapper.projectUpdate(projectEntity);
    }

    @Override
    public int projectDelete(int prjctSq) {
      return projectMapper.projectDelete(prjctSq);
    }


    
    @Override
    public List<String> selectEmailsForEssentialSkills(int prjctSq) {
   // 1. 필수 기술을 가진 회원 이메일 조회
        System.out.println("🚀 selectEmailsForEssentialSkills 실행됨!" + prjctSq);
        List<String> emails = projectMapper.selectEmailsForEssentialSkills(prjctSq);
        System.out.println("🚀 selectEmailsForEssentialSkills 실행됨!" + emails);
        // 2. 이메일 발송
        for (String email : emails) {
        
        }
                return emails;
    }

 
    private void sendEmail(String to, String projectLink) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("zmclsnsn@gmail.com");
            helper.setTo(to);
            helper.setSubject("프로젝트 참여 안내");
        // HTML 형식의 이메일 본문
        String emailContent = "<p>안녕하세요,</p>"
                + "<p>해당 프로젝트에 참여하실 수 있는 기회가 있습니다!</p>"
                + "<p>아래 링크를 클릭하여 자세한 내용을 확인해 주세요:</p>"
                + "<p><a href='" + projectLink + "' style='color:blue; text-decoration:underline;'>프로젝트 바로가기</a></p>"
                + "<p>감사합니다.</p>";

        helper.setText(emailContent, true);
            mailSender.send(message);
            System.out.println("이메일 전송 완료: " + to);
        } catch (Exception e) {
            System.err.println("이메일 전송 실패: " + to);
            e.printStackTrace();
        }
    
    }

    @Override
    public List<String> selectQualifiedMembers(int prjctSq) {

    // 1. 필수 기술을 가진 회원 이메일 조회
    System.out.println("🚀 selectEmailsForEssentialSkills 실행됨!" + prjctSq);
    List<String> emails = projectMapper.selectQualifiedMembers(prjctSq);
    System.out.println("🚀 selectEmailsForEssentialSkills 실행됨!" + emails);
    String prLink = String.format("http://localhost:8080/projectDetail/%d", prjctSq);
    // 2. 이메일 발송
    for (String email : emails) {
        sendEmail(email, prLink);
    }
            return emails;

    }

    @Override
    public int interviewInsert(int prjctSq, String prjctInterv) {
       return projectMapper.interviewInsert(prjctSq, prjctInterv);
    }

    @Override
    public List<ProjectListEntity> myProjectList(String sortBy, Long entrprsSq) {
     
        if (entrprsSq != null) {
            // 특정 회사의 공고만 조회
            return projectRepository.findPrjctByEntrprsSq(entrprsSq, sortBy);
        } else {
            // 모든 공고 조회
            return projectRepository.findPrjctAllJobPostings(sortBy);
        }
    

    }

    @Override
    public List<ProjectListEntity> searchProject(String searchTerm, String searchField) {
        System.out.println("searchTerm: " + searchTerm + ", searchField: " + searchField);
    
        // 빈 검색어 처리
        if (searchTerm == null || searchTerm.isEmpty()) {
            List<ProjectListEntity> allPostings = projectRepository.findAll();
            System.out.println("findAll() 호출 결과: " + allPostings);
            return allPostings;
        }
    
        // searchField 값 확인
        System.out.println("검색 조건에 해당하는 필드: " + searchField);
    
        // 검색 조건 처리
        switch (searchField) {
            case "jbpTtl_jbpCntnt":
                List<ProjectListEntity> result1 = projectRepository.findByPrjctTtlContainingOrPrjctCntntContaining(searchTerm, searchTerm);
                System.out.println("findByJbpTtlContainingOrJbpCntntContaining 결과: " + result1);
                return result1;
            case "jbpTtl":
                List<ProjectListEntity> result2 = projectRepository.findByprjctTtlContaining(searchTerm);
                System.out.println("findByJbpTtlContaining 결과: " + result2);
                return result2;
            case "jbpCntnt":
                List<ProjectListEntity> result3 = projectRepository.findByprjctCntntContaining(searchTerm);
                System.out.println("findByJbpCntntContaining 결과: " + result3);
                return result3;
            default:
                System.out.println("기본값으로 처리됨: " + searchField);  // default가 호출된 경우 로그 추가
                return projectRepository.findAll();
        }
    }

    @Transactional
    @Override
    public void extendDeadline(Long prjctSq, int daysToAdd) {
        ProjectListEntity ProjectListEntity = projectRepository.findById(prjctSq)
                .orElseThrow(() -> new RuntimeException("Job posting not found"));

        LocalDate currentDeadline = ProjectListEntity.getPrjctEndDate();
        LocalDate newDeadline = currentDeadline.plusDays(daysToAdd);

        int updatedCount = projectRepository.extendDeadline(prjctSq, newDeadline);
        if (updatedCount == 0) {
            throw new RuntimeException("Failed to extend deadline for job posting");
        }
    }


    
}
