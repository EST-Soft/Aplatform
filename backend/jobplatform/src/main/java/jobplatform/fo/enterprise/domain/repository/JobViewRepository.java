package jobplatform.fo.enterprise.domain.repository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import jobplatform.fo.enterprise.domain.dto.JobViewDTO;
import jobplatform.fo.enterprise.domain.entity.JobViewEntity;

public interface JobViewRepository extends JpaRepository<JobViewEntity, Long> {

 // 회원이 본 직업 공고 조회
 
 List<JobViewEntity> findByMbrSq(Long mbrSq);

 // 특정 직업 공고를 본 회원 조회
 List<JobViewEntity> findByJbpSq(Long jbpSq);
 // 회원 순번(mbrSq)으로 조회, 조회 일자(viewDate) 기준 내림차순 정렬
 List<JobViewEntity> findByMbrSqOrderByViewDateDesc(Long mbrSq);
 // 회원과 직업 공고의 조합이 있을 때, 해당 기록이 있는지 확인
 Optional<JobViewEntity> findByMbrSqAndJbpSq(Long mbrSq, Long jbpSq);
  // JobViewDTO를 매개변수로 하는 쿼리 메서드 정의
      // 최근 본 공고를 기록하는 쿼리 메서드
      @Modifying
      @Transactional
      @Query("INSERT INTO JobViewEntity (mbrSq, jbpSq, viewDate) VALUES (:mbrSq, :jbpSq, :viewDate)")
      void insertJobView(
              @Param("mbrSq") Long mbrSq,
              @Param("jbpSq") Long jbpSq,
              @Param("viewDate") LocalDateTime viewDate
      );

      boolean existsByMbrSqAndJbpSq(Long mbrSq, Long jbpSq);
  
      // 기업명과 공고명으로 검색
      @Query("SELECT new jobplatform.fo.enterprise.domain.dto.JobViewDTO(" +
      "jv.rjvSq, jv.mbrSq, jp.jbpSq, jv.viewDate, em.entrprsName, jv.mbrId, jp.jbpTtl) " +
      "FROM JobViewEntity jv " +
      "JOIN jv.jobPosting jp " +
      "JOIN jp.enterpriseMember em " +
      "WHERE (UPPER(em.entrprsName) LIKE UPPER(CONCAT('%', :enterpriseName, '%')) " +
      "OR UPPER(jp.jbpTtl) LIKE UPPER(CONCAT('%', :jobTitle, '%'))) " +
      "AND jv.mbrSq = :mbrSq")
List<JobViewDTO> findJobViews(@Param("enterpriseName") String enterpriseName,
                             @Param("jobTitle") String jobTitle,
                             @Param("mbrSq") Long mbrSq);
// 최근 본 공고를 mbrSq 기준으로 페이지네이션 처리
@Query("SELECT j FROM JobViewEntity j WHERE j.mbrSq = :mbrSq ORDER BY j.viewDate DESC")
Page<JobViewEntity> findRecentJobViewsByMbrSq(@Param("mbrSq") Long mbrSq, Pageable pageable);


}
