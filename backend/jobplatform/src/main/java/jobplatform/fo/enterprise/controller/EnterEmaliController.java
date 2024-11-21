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

    //�꽌?��꾩뒪 �젅�씠�뼱 �뾾�씠 ?��?�쁽
    //�쉶�썝媛��엯�슜
    @PostMapping("/emlSend")
    public ResponseEntity<?> emlSend(@RequestBody Map<String,String> map) {

        log.info(map);

        String entrprsPicEml = map.get("entrprsPicEml");
        String purpose = map.get("purpose");

        log.info("entrprsPicEml : " + entrprsPicEml);
        // �씠硫붿?�� 二쇱?��媛� null�씤吏� �솗�씤
        if (entrprsPicEml == null ) {

            return ResponseEntity.badRequest().body(map);
        }
        //�뼱�뼸寃� 硫붿?�� 蹂�??궪吏���? �����? �꽕�젙

        JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
        Properties prop = new Properties();

        mailSenderImpl.setHost("smtp.gmail.com");
        mailSenderImpl.setPort(587);
        mailSenderImpl.setUsername("zmclsnsn@gmail.com");        // 蹂몄?�� �삉�뒗 �쉶�궗 �븘�씠�뵒濡� ?���?�?
        mailSenderImpl.setPassword("lvyi aayx ivbe lefz");                // 李멸??? 留곹겕��? �뵲�씪 �젣??�듬컺��� ?��꾨��踰?��?�� �궗�슜
        prop.put("mail.smtp.auth", true);                                // �씠硫붿?�� �꽌踰꾩�? �씤利� �슂?���?
        prop.put("mail.smtp.starttls.enable", true);                    // �븫�샇�솕�맂 �뿰寃곗?�� �솢�꽦�솕. starttls�뒗 �씠硫붿?�� �쟾�넚以묒�? 蹂댁�? ??�꾩링�?�� ?��붽����?�뿬 �뜲�씠�꽣�쓽 湲곕���꽦�쓣 蹂댄?��

        mailSenderImpl.setJavaMailProperties(prop);


        // MemberEntity member = memberRepository.findByMbrEmlAdrs(mbrEmlAdrs);
        boolean member=enterMemberRepository.existsByEntrprsPicEml(entrprsPicEml);
        // 媛��엯 �븣�뒗 true媛�?�씠 �굹�삤硫� BadRequest ,  ?��꾨��踰?��?�� 蹂�寃� , �븘�씠�뵒 議고?��?���? �쐞�빐�꽌 false�씪 �븣 BadRequest
        System.out.println("硫ㅻ�?"+member);
        if (purpose == null) { // �쉶�썝媛��엯 濡쒖�?
            if (member) {
                map.put("error", "�씠誘� 議댁?���븯�뒗 �씠硫붿?���엯�땲�떎.");
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

        message.setSubject("�씤利앹?���뱶 硫붿?���엯�땲�떎.");
        message.setText("�씤利앸쾲�?���뒗 " + key + " �엯�땲�떎.");

        try {
            mailSenderImpl.send(message);
        } catch (Exception e) {
            map.put("error", "硫붿?�� �쟾�넚�뿉 �떎�뙣�뻽�뒿�땲�떎.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
        }

        map.put("key", key);
        return ResponseEntity.ok(map);
    }
}
