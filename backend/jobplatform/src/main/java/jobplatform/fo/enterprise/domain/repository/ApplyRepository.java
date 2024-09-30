package jobplatform.fo.enterprise.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jobplatform.fo.enterprise.domain.entity.ApplyEntity;

public interface ApplyRepository extends JpaRepository<ApplyEntity, Long>{
    boolean existsByResumeRsmSqAndJobPostingJbpSq(Long rsmSq, Long jbpSq);

}
