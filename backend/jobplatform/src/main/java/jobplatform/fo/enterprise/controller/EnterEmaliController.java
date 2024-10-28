package jobplatform.fo.enterprise.controller;

import jobplatform.fo.enterprise.domain.dto.EnterRegisterDTO;
import jobplatform.fo.enterprise.domain.repository.EnterMemberRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Properties;
import java.util.Random;


@Log4j2
@RestController
@RequestMapping("/enter")
public class EnterEmaliController {



    @Autowired
    EnterMemberRepository enterMemberRepository;

    //서비스 레이어 없이 구현
    //회원가입용
    @PostMapping("/emlSend")
    public ResponseEntity<?> emlSend(@RequestBody Map<String,String> map) {

        log.info(map);

        String entrprsPicEml = map.get("entrprsPicEml");
        String purpose = map.get("purpose");

        log.info("entrprsPicEml : " + entrprsPicEml);
        // 이메일 주소가 null인지 확인
        if (entrprsPicEml == null ) {

            return ResponseEntity.badRequest().body(map);
        }
        //어떻게 메일 보낼지에 대한 설정

        JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
        Properties prop = new Properties();

        mailSenderImpl.setHost("smtp.gmail.com");
        mailSenderImpl.setPort(587);
        mailSenderImpl.setUsername("zmclsnsn@gmail.com");        // 본인 또는 회사 아이디로 교체
        mailSenderImpl.setPassword("lvyi aayx ivbe lefz");                // 참고 링크에 따라 제공받은 비밀번호 사용
        prop.put("mail.smtp.auth", true);                                // 이메일 서버에 인증 요구
        prop.put("mail.smtp.starttls.enable", true);                    // 암호화된 연결을 활성화. starttls는 이메일 전송중에 보안 계층을 추가하여 데이터의 기밀성을 보호

        mailSenderImpl.setJavaMailProperties(prop);


        // MemberEntity member = memberRepository.findByMbrEmlAdrs(mbrEmlAdrs);
        boolean member=enterMemberRepository.existsByEntrprsPicEml(entrprsPicEml);
        // 가입 때는 true값이 나오면 BadRequest ,  비밀번호 변경 , 아이디 조회를 위해서 false일 때 BadRequest
        System.out.println("멤버"+member);
        if (purpose == null) { // 회원가입 로직
            if (member) {
                map.put("error", "이미 존재하는 이메일입니다.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
            }
        }

        Random random = new Random();
        String key = "";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(entrprsPicEml);

        for (int i = 0; i < 3; i++) {
            int index = random.nextInt(26) + 65;
            key += (char) index;
        }
        for (int i = 0; i < 6; i++) {
            int numIndex = random.nextInt(10);
            key += numIndex;
        }

        message.setSubject("인증코드 메일입니다.");
        message.setText("인증번호는 " + key + " 입니다.");

        try {
            mailSenderImpl.send(message);
        } catch (Exception e) {
            map.put("error", "메일 전송에 실패했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
        }

        map.put("key", key);
        return ResponseEntity.ok(map);
    }
}
