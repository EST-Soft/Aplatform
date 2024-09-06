package jobplatform.fo.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jobplatform.fo.user.domain.vo.EducationDto;
import jobplatform.fo.user.service.EducationService;

@RestController
@RequestMapping("/education")
public class EducationController {

    @Autowired
    private EducationService educationService;

    @PostMapping("/insert-education")
    public void insertEducation(@RequestParam("mbrSq") Long mbrSq,
            @RequestParam("rsmSq") Long rsmSq, @RequestBody List<EducationDto> educationDtoList) {
        System.out.println(educationDtoList + "   sdfdsfsdfs");
        for (EducationDto education : educationDtoList) {
            educationService.insertEducation(mbrSq, rsmSq, education);
        }
    } // insertEducation
} // EducationController
