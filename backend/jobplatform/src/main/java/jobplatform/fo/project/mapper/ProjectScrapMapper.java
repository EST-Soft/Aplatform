package jobplatform.fo.project.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
}
