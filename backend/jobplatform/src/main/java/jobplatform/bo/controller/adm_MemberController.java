package jobplatform.bo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jobplatform.bo.dto.adm_MemberDTO;
import jobplatform.bo.service.adm_MemberService;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/admin") // 기본 경로 설정
public class adm_MemberController {
    
    private final adm_MemberService memberservice;

    public adm_MemberController(adm_MemberService memberservice){
        this.memberservice = memberservice;
    }

    @GetMapping("/board/MemberAccount")
    public List<adm_MemberDTO> getAllMember() {
        
        return memberservice.getAllMemberList();
    }
    
}
