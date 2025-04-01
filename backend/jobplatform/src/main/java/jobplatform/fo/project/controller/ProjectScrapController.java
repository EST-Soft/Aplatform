package jobplatform.fo.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import jobplatform.fo.project.domain.ProjectDomain;
import jobplatform.fo.project.domain.ProjectScrapDomain;
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
    @GetMapping("/projectDetail/user/{mbrSq}/{prjctSq}")
    public boolean isScrapped(@PathVariable("mbrSq") Long mbrSq, @PathVariable("prjctSq") Long prjctSq) {
        return scrapService.checkScrap(mbrSq, prjctSq);
    }

    // 스크랩 생성
    @PostMapping("/create/user/{mbrSq}/{prjctSq}")
    public void createScrap(@PathVariable Long mbrSq, @PathVariable Long prjctSq) {
        scrapService.createScrap(mbrSq, prjctSq);
    }

    // 스크랩 제거
    @DeleteMapping("/remove/user/{mbrSq}/{prjctSq}")
    public void removeScrap(@PathVariable Long mbrSq, @PathVariable Long prjctSq) {
        scrapService.removeScrap(mbrSq, prjctSq);
    }

    // 기업 회원일 때
    // 스크랩 여부 확인
    @GetMapping("/projectDetail/enter/{entrprsSq}/{prjctSq}")
    public boolean isEnterScrapped(@PathVariable("entrprsSq") Long entrprsSq, @PathVariable("prjctSq") Long prjctSq) {
        return scrapService.checkEnterScrap(entrprsSq, prjctSq);
    }

    // 스크랩 생성
    @PostMapping("/create/enter/{entrprsSq}/{prjctSq}")
    public void createEnterScrap(@PathVariable Long entrprsSq, @PathVariable Long prjctSq) {
        scrapService.createEnterScrap(entrprsSq, prjctSq);
    }

    // 스크랩 제거
    @DeleteMapping("/remove/enter/{entrprsSq}/{prjctSq}")
    public void removeEnterScrap(@PathVariable Long entrprsSq, @PathVariable Long prjctSq) {
        scrapService.removeEnterScrap(entrprsSq, prjctSq);
    }

    // 스크랩 프로젝트 불러오기 (개인)
    @GetMapping("/user/scrap-list/{mbrSq}")
    public List<ProjectDomain> getProjectScrapByMbrSq(@PathVariable Long mbrSq) {
        return scrapService.getProjectByPrjctSq(mbrSq);
    }
    
}
