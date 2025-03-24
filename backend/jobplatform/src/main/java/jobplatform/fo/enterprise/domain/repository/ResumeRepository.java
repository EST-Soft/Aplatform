package jobplatform.fo.enterprise.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jobplatform.fo.enterprise.domain.entity.ResumeEntity;

@Repository
public interface ResumeRepository extends JpaRepository<ResumeEntity, Long> {
    Optional<ResumeEntity> findByRsmSq(Long rsmSq);



    // 3/24

    List<ResumeEntity> findByMbrSq(Long mbrSq);

}
