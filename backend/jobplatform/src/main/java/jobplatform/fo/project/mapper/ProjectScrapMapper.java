package jobplatform.fo.project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import jobplatform.fo.project.domain.ProjectScrapDomain;

@Mapper
public interface ProjectScrapMapper {

    // 프로젝트 상세보기
    @Select("SELECT EXISTS (SELECT 1 FROM p2_tbl_scrap_s WHERE mbr_sq = #{mbrSq} AND prjct_sq = #{prjctSq})")
    boolean isScrapped(@Param("mbrSq") Long mbrSq, @Param("prjctSq") Long prjctSq);

    // 스크랩 생성
    @Insert("INSERT INTO p2_tbl_scrap_s (mbr_sq, prjct_sq) VALUES (#{mbrSq}, #{prjctSq})")
    void createScrap(@Param("mbrSq") Long mbrSq, @Param("prjctSq") Long prjctSq);

    // 스크랩 제거
    @Delete("DELETE FROM p2_tbl_scrap_s WHERE mbr_sq = #{mbrSq} AND prjct_sq = #{prjctSq}")
    void removeScrap(@Param("mbrSq") Long mbrSq, @Param("prjctSq") Long prjctSq);

    // 기업인 경우
    // 프로젝트 상세보기
    @Select("SELECT EXISTS (SELECT 1 FROM p2_tbl_scrap_s WHERE entrprs_sq = #{entrprsSq} AND prjct_sq = #{prjctSq})")
    boolean isEnterScrapped(@Param("entrprsSq") Long entrprsSq, @Param("prjctSq") Long prjctSq);

    // 스크랩 생성
    @Insert("INSERT INTO p2_tbl_scrap_s (entrprs_sq, prjct_sq) VALUES (#{entrprsSq}, #{prjctSq})")
    void createEnterScrap(@Param("entrprsSq") Long entrprsSq, @Param("prjctSq") Long prjctSq);

    // 스크랩 제거
    @Delete("DELETE FROM p2_tbl_scrap_s WHERE entrprs_sq = #{entrprsSq} AND prjct_sq = #{prjctSq}")
    void removeEnterScrap(@Param("entrprsSq") Long entrprsSq, @Param("prjctSq") Long prjctSq);

    // 스크랩한 프로젝트 불러오기 (개인)
    @Select("SELECT prjct_sq FROM p2_tbl_scrap_s WHERE mbr_sq = #{mbrSq} AND prjct_sq IS NOT NULL")
    List<Long> findByMbrSq(@Param("mbrSq") Long mbrSq);
    
}
