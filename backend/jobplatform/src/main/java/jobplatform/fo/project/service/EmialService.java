package jobplatform.fo.project.service;

import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import jobplatform.fo.project.domain.UserEmailDomain;
import jobplatform.fo.project.mapper.ResumeFilterMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmialService {
    
      JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
       Properties prop = new Properties();

    private final ResumeFilterMapper resumeFilterMapper;

    public void sendProjectEmails(List<Long> skillCodes, List<Long> careerConditions) {
        List<UserEmailDomain> users = resumeFilterMapper.findEligibleUsers(skillCodes, careerConditions);

        for (UserEmailDomain user : users) {
            sendEmail(user.getEmail());
        }
    }


    private void sendEmail(String toEmail) {

        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("zmclsnsn@gmail.com");
        mailSender.setPassword("lvyi aayx ivbe lefz");
        prop.put("mail.smtp.auth", true);                               
        prop.put("mail.smtp.starttls.enable", true);  

        mailSender.setJavaMailProperties(prop);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("프로젝트 등록 알림");
        message.setText("프로젝트에 적합한 기술과 경력을 보유하셨습니다. 지원해보세요!");

        
        mailSender.send(message);
    }
}
