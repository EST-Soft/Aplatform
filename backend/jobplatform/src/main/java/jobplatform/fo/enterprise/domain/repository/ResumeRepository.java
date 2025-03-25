package jobplatform.fo.enterprise.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jobplatform.fo.enterprise.domain.entity.ResumeEntity;

@Repository
public interface ResumeRepository extends JpaRepository<ResumeEntity, Long> {
    Optional<ResumeEntity> findByRsmSq(Long rsmSq);

    // 3/24

    List<ResumeEntity> findByMbrSqOrderByRsmRprsntvYnDesc(Long mbrSq);

    @Modifying
    @Transactional
    @Query("UPDATE ResumeEntity r SET r.rsmRprsntvYn = 'n' WHERE r.mbrSq = :mbrSq")
    void resetRepresentativeResume(@Param("mbrSq") Long mbrSq);

    @Modifying
    @Transactional
    @Query("UPDATE ResumeEntity r SET r.rsmRprsntvYn = 'y' WHERE r.rsmSq = :rsmSq")
    void setRepresentativeResume(@Param("rsmSq") Long rsmSq);

}
