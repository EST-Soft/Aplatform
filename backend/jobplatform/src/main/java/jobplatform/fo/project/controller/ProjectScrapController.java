package jobplatform.fo.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import jobplatform.fo.project.service.ProjectScrapService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/scrap")
public class ProjectScrapController {


    @Autowired
    private ProjectScrapService scrapService;

    // 스크랩 여부 확인
    @GetMapping("/projectDetail/{mbrSq}/{prjctSq}")
    public boolean isScrapped(@PathVariable("mbrSq") Long mbrSq, @PathVariable("prjctSq") Long prjctSq) {
        return scrapService.checkScrap(mbrSq, prjctSq);
    }

    // 스크랩 생성
    @PostMapping("/create/{mbrSq}/{prjctSq}")
    public void createScrap(@PathVariable Long mbrSq, @PathVariable Long prjctSq) {
        scrapService.createScrap(mbrSq, prjctSq);
    }

    // 스크랩 제거
    @DeleteMapping("/remove/{mbrSq}/{prjctSq}")
    public void removeScrap(@PathVariable Long mbrSq, @PathVariable Long prjctSq) {
        scrapService.removeScrap(mbrSq, prjctSq);
    }
    
}
