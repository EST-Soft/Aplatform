package jobplatform.fo.enterprise.domain.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jobplatform.fo.enterprise.domain.dto.JobPostingDTO;
import jobplatform.fo.enterprise.domain.dto.JobViewDTO;
import jobplatform.fo.enterprise.domain.entity.ApplyEntity;
import jobplatform.fo.enterprise.domain.entity.EnterMemberEntity;
import jobplatform.fo.enterprise.domain.entity.JobPostingEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Repository
public interface JobPostingRepository extends JpaRepository<JobPostingEntity, Long>{
	

	// 전체 공고 리스트 조회 및 정렬
    @Query("SELECT j FROM JobPostingEntity j ORDER BY "
    + "CASE WHEN :sortBy = 'hits' THEN j.hits END DESC, "
    + "CASE WHEN :sortBy = 'regstrStrtDtm' THEN j.regstrStrtDtm END DESC")
    List<JobPostingEntity> findAllJobPostings(@Param("sortBy") String sortBy);

    // 특정 회사의 공고 리스트 조회 및 정렬 
    @Query("SELECT j FROM JobPostingEntity j WHERE j.enterpriseMember.entrprsSq = :entrprsSq ORDER BY "
    + "CASE WHEN :sortBy = 'hits' THEN j.hits END DESC, "
    + "CASE WHEN :sortBy = 'regstrStrtDtm' THEN j.regstrStrtDtm END DESC")
    List<JobPostingEntity> findByEntrprsSq(@Param("entrprsSq") Long entrprsSq, @Param("sortBy") String sortBy);



	
    // 조회수 증가
	@Modifying
    @Query("UPDATE JobPostingEntity j SET j.hits = j.hits + 1 WHERE j.jbpSq = :jbpSq")
    int increaseHits(@Param("jbpSq") Long jbpSq);
    // @Modifying을 사용하여 데이터 변경 쿼리 작성
    @Modifying
    @Transactional  // 트랜잭션을 위한 어노테이션
    @Query("INSERT INTO JobViewEntity (mbrSq, jbpSq, viewDate, mbrId) " +
           "VALUES (:mbrSq, :jbpSq, CURRENT_TIMESTAMP, :mbrId)")
    void addJobViewEntity(Long mbrSq, Long jbpSq, String mbrId);
    
    // 검색
    List<JobPostingEntity> findByJbpTtlContainingOrJbpCntntContaining(String jbpTtl, String jbpCntnt);
	List<JobPostingEntity> findByJbpTtlContaining(String jpbTtl);
	List<JobPostingEntity> findByJbpCntntContaining(String jbpCntnt);
        
    // 조회수(hits) 기준으로 상위 3개 공고 조회
    List<JobPostingEntity> findTop3ByOrderByHitsDesc();


    //마감일 연장 
    @Modifying
    @Query("UPDATE JobPostingEntity j SET j.regstrDlnDtm = :newDeadline WHERE j.jbpSq = :jbpSq")
    int extendDeadline(@Param("jbpSq") Long jbpSq, @Param("newDeadline") LocalDate newDeadline);
    
    
    


}