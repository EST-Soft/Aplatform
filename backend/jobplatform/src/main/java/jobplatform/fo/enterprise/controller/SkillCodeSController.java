package jobplatform.fo.enterprise.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jobplatform.fo.enterprise.domain.dto.SkillCodeSDto;
import jobplatform.fo.enterprise.service.SkillCodeSService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/skl-cd")
@RequiredArgsConstructor
public class SkillCodeSController {

    private final SkillCodeSService skillCodeSService;

    @GetMapping("/list")
    public ResponseEntity<List<SkillCodeSDto>> getSklCdList() {
        List<SkillCodeSDto> skillCodeSDtos = skillCodeSService.getSkillCodes();
        System.out.println("skill code list : " + skillCodeSDtos);
        return ResponseEntity.ok(skillCodeSDtos);
    } // getSklCdList

} // SkillCodeSController
