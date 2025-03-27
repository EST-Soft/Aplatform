package jobplatform.fo.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jobplatform.fo.project.entity.ProjectApplyEntity;
import jobplatform.fo.project.repository.ProjectApplyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectApplyServiceImpl implements ProjectApplyService {

    private final ProjectApplyRepository projectApplyRepository;

    @Autowired
    public ProjectApplyServiceImpl(ProjectApplyRepository projectApplyRepository) {
        this.projectApplyRepository = projectApplyRepository;
    }

    @Override
    public Optional<ProjectApplyEntity> getProjectApply(Long prjctAplSq) {
        return projectApplyRepository.findById(prjctAplSq);
    }

    @Override
    public List<ProjectApplyEntity> getAllProjectApplies() {
        return projectApplyRepository.findAll();
    }

    @Override
    public ProjectApplyEntity saveProjectApply(ProjectApplyEntity projectApply) {
        return projectApplyRepository.save(projectApply);
    }

    @Override
    public void deleteProjectApply(Long prjctAplSq) {
        projectApplyRepository.deleteById(prjctAplSq);
    }
    
    @Override
    // 일반 회원이 특정 프로젝트에 이미 지원했는지 확인
    public Optional<ProjectApplyEntity> getUserApplyByMbrSqAndPrjctSq(Long mbrSq, Long prjctSq) {
        return projectApplyRepository.findByMbrSqAndPrjctSq(mbrSq, prjctSq);
    }
}
