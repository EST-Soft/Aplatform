package jobplatform.fo.project.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;
import jobplatform.fo.project.domain.ProjectDomain;
import jobplatform.fo.project.mapper.ProjectMapper;

@Service
public class PojectServiceImpl implements ProjectService {


    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private JavaMailSender mailSender;
    
    
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


    
}
