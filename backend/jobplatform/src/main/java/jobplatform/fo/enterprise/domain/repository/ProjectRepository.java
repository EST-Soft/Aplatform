package jobplatform.fo.enterprise.domain.repository;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jobplatform.fo.enterprise.domain.entity.ProjectListEntity;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectListEntity, Long>{

    // 모든 데이터를 가져오게 되어있는데 마감일이 지난 것은 가져오지 않게 수정했음
	// 전체 공고 리스트 조회 및 정렬
    @Query("SELECT j FROM ProjectListEntity j "
    + "WHERE j.prjctEndDate >= CURRENT_DATE "  // 오늘 날짜보다 크거나 같은 것만 필터링
    + "ORDER BY "
    + "CASE WHEN :sortBy = 'prjctEndDate' THEN j.prjctEndDate END DESC")
    List<ProjectListEntity> findPrjctAllJobPostings(@Param("sortBy") String sortBy);

    // 특정 회사의 공고 리스트 조회 및 정렬 
    @Query("SELECT j FROM ProjectListEntity j WHERE j.enterpriseMember.entrprsSq = :entrprsSq ORDER BY "
    + "CASE WHEN :sortBy = 'prjctEndDate' THEN j.prjctEndDate END DESC")
    List<ProjectListEntity> findPrjctByEntrprsSq(@Param("entrprsSq") Long entrprsSq, @Param("sortBy") String sortBy);

     // 검색
     List<ProjectListEntity> findByPrjctTtlContainingOrPrjctCntntContaining(String prjctTtl, String prjctCntnt);
     List<ProjectListEntity> findByprjctTtlContaining(String prjctTtl);
     List<ProjectListEntity> findByprjctCntntContaining(String prjctCntnt);

      //마감일 연장 
    @Modifying
    @Query("UPDATE ProjectListEntity j SET j.prjctEndDate = :newDeadline WHERE j.prjctSq = :prjctSq")
    int extendDeadline(@Param("prjctSq") Long prjctSq, @Param("newDeadline") LocalDate newDeadline);
    
    
    

}
