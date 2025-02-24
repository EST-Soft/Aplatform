package jobplatform.fo.enterprise.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import jobplatform.fo.enterprise.domain.entity.ResumeEntity;
import java.util.Optional;

@Repository
public interface ResumeRepository extends JpaRepository<ResumeEntity, Long> {
    Optional<ResumeEntity> findByRsmSq(Long rsmSq);
}
