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
            sendEmail(email);
        }
                return emails;
    }

 
    private void sendEmail(String to) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("zmclsnsn@gmail.com");
            helper.setTo(to);
            helper.setSubject("프로젝트 참여 안내");
            helper.setText("안녕하세요, 해당 프로젝트에 참여하실 수 있는 기회가 있습니다!", false);

            mailSender.send(message);
            System.out.println("이메일 전송 완료: " + to);
        } catch (Exception e) {
            System.err.println("이메일 전송 실패: " + to);
            e.printStackTrace();
        }
    
    }

    @Override
    public List<Integer> selectQualifiedMembers(int prjctSq) {

        
      return projectMapper.selectQualifiedMembers(prjctSq);

    }

    
}
