package jobplatform.fo.enterprise.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import jobplatform.fo.enterprise.domain.dto.EnterLoginDTO;
import jobplatform.fo.enterprise.domain.dto.EnterRegisterDTO;
import jobplatform.fo.enterprise.service.EnterMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;


@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/enter")
public class EnterMemberController {

    @Autowired
    EnterMemberService enterMemberService;


    // ResponseEntity로 상태 코드만 전달,

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody EnterRegisterDTO enterMemberDTO){
        log.info("register 들어옴");
        if(enterMemberService.selectId(enterMemberDTO.getEntrprsId())){

            log.info(  "중복 id 있음");

            return ResponseEntity.badRequest().build();
        }

        System.out.println("asdasd" + enterMemberDTO);

        try{
            enterMemberService.insert(enterMemberDTO);
            System.out.println("이거 실행되나");

        }catch (Exception e){
            log.info("register sql 오류 "+e);
        }

        return ResponseEntity.ok().build();
    };

    @GetMapping("/check")
    public ResponseEntity checkId(@RequestParam String entrprsId){
        log.info("check요청");
        if(enterMemberService.selectId(entrprsId)){
            log.info(entrprsId + " id 있음");
            return    ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build(); }



    @PostMapping("/login")
    public ResponseEntity login(@RequestBody EnterLoginDTO enterLoginDTO
    ) {
        System.out.println("로그인요청"+enterLoginDTO);
        log.info("login요청 들어옴");
        log.info(enterLoginDTO);
        Long pk = null;

        ResponseCookie cookie = ResponseCookie
                .from("entrprsId", enterLoginDTO.getEntrprsId())
                .domain("localhost")
                .path("/")
                .httpOnly(true)
                .secure(false)
                .maxAge(Duration.ofDays(30))
                .sameSite("Strict")
                .build();



        if(enterLoginDTO.getEntrprsId() != null && enterLoginDTO.getEntrprsPswrd() != null){

            try {
                
                pk =	enterMemberService.login(enterLoginDTO);
                
                if(pk == null ){

                    return ResponseEntity.badRequest().body("아이디 정보가 없습니다");

                }

            }catch(Exception e) {
                return ResponseEntity.badRequest().body("jpa login 오류" + e);
            }

        }


        Map map = new HashMap();

        map.put("pk",pk);
        map.put("entrprsId",enterLoginDTO.getEntrprsId());

        log.info("cookie"+ cookie);

        //body를 쓰면 build()를 안써도됨.
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(map);
    }
    @PostMapping("/findId")
    public ResponseEntity<String> findByIdEmail(@RequestBody EnterRegisterDTO enterRegisterDTO) {
        String entrprsId = null;
        try {
            entrprsId = enterMemberService.findByIdEml(enterRegisterDTO);
            if (entrprsId == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 이메일로 등록된 기업 ID를 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            System.out.println("err" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ID 조회 중 오류가 발생했습니다.");
        }
        return ResponseEntity.ok(entrprsId);
    }

    @PostMapping("/findPswrd")
    public ResponseEntity<String> findByPswrd(@RequestBody EnterRegisterDTO enterRegisterDTO) {
        try {
            System.out.println("엔터데이터" + enterRegisterDTO);
            String entrprsSq = enterMemberService.findByPswrd(enterRegisterDTO);
            if (entrprsSq == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 정보로 등록된 비밀번호를 찾을 수 없습니다.");
            }
            System.out.println("비밀번호는" + entrprsSq);
            return ResponseEntity.ok(entrprsSq);
        } catch (Exception e) {
            System.out.println("err" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("비밀번호 조회 중 오류가 발생했습니다.");
        }
    }

    @PatchMapping("/PswrdReset")
    public ResponseEntity<Integer> pswrdReset(@RequestBody EnterRegisterDTO enterRegisterDTO) {
        try {
            System.out.println(enterRegisterDTO);
            int result = enterMemberService.pswrdReset(enterRegisterDTO);
            if (result == 0) {
                return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(result);
            }
            System.out.println("비번수정" + result);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            System.out.println("err" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(0);
        }
    }
}