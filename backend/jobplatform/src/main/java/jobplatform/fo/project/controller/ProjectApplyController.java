package jobplatform.fo.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jobplatform.fo.project.entity.ProjectApplyEntity;
import jobplatform.fo.project.repository.ProjectApplyRepository;
import jobplatform.fo.project.service.ProjectApplyService;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/project-applies")
public class ProjectApplyController {

    @Autowired
    private ProjectApplyService projectApplyService;

    @Autowired
    private ProjectApplyRepository projectApplyRepository;


    @GetMapping("/{prjctAplSq}")
    public Optional<ProjectApplyEntity> getProjectApply(@PathVariable Long prjctAplSq) {
        return projectApplyService.getProjectApply(prjctAplSq);
    }

    @GetMapping
    public List<ProjectApplyEntity> getAllProjectApplies() {
        return projectApplyService.getAllProjectApplies();
    }

    // 일반 회원 프로젝트 지원
    @PostMapping("")
    public ProjectApplyEntity saveProjectApply(@RequestBody ProjectApplyEntity projectApply) {
        return projectApplyService.saveProjectApply(projectApply);
    }

    @DeleteMapping("/{prjctAplSq}")
    public void deleteProjectApply(@PathVariable Long prjctAplSq) {
        projectApplyService.deleteProjectApply(prjctAplSq);
    }

    // 일반 회원 지원했는지 체크
    @GetMapping("/userApplyCheck/{mbrSq}/{prjctSq}")
    public Optional<ProjectApplyEntity> getUserApplyCheck(@PathVariable Long mbrSq, @PathVariable Long prjctSq) {
        return projectApplyService.getUserApplyByMbrSqAndPrjctSq(mbrSq,prjctSq);
    }
    
}
