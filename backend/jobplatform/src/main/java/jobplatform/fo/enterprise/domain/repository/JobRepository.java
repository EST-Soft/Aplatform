package jobplatform.fo.enterprise.domain.repository;

import java.util.List;

import org.springframework.boot.autoconfigure.batch.BatchProperties.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;


import jobplatform.fo.enterprise.domain.entity.JobEntity;
import jobplatform.fo.enterprise.domain.entity.JobPostingEntity;

import java.time.LocalDateTime;
public interface JobRepository extends JpaRepository<JobPostingEntity, Long> {

    // 최근 본 공고를 기록하는 쿼리 메서드
    @Modifying
    @Transactional
    @Query("INSERT INTO JobViewEntity (mbrSq, jbpSq, viewDate) VALUES (:mbrSq, :jbpSq, :viewDate)")
    void insertJobView(
            @Param("mbrSq") Long mbrSq,
            @Param("jbpSq") Long jbpSq,
            @Param("viewDate") LocalDateTime viewDate
    );
}
