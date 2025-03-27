package jobplatform.fo.project.service;

import java.util.List;
import java.util.Optional;

import jobplatform.fo.project.entity.ProjectApplyEntity;

public interface ProjectApplyService {
    Optional<ProjectApplyEntity> getProjectApply(Long prjctAplSq);
    List<ProjectApplyEntity> getAllProjectApplies();
    ProjectApplyEntity saveProjectApply(ProjectApplyEntity projectApply);
    void deleteProjectApply(Long prjctAplSq);
    Optional<ProjectApplyEntity> getUserApplyByMbrSqAndPrjctSq(Long mbrSq, Long prjctSq);
}
