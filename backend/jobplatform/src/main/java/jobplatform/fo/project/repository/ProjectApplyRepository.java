package jobplatform.fo.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import jobplatform.fo.project.entity.ProjectApplyEntity;

public interface ProjectApplyRepository extends JpaRepository<ProjectApplyEntity,Long> {
    // 일반 회원 이미 지원했는지 체크
    Optional<ProjectApplyEntity> findByMbrSqAndPrjctSq(Long mbrSq, Long prjctSq);

}
