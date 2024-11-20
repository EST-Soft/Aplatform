package jobplatform.fo.user.domain.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ScrapMapper {

    void insertScrap(@Param("mbrSq") Long mbrSq,
                     @Param("entrprsSq") Long entrprsSq,
                     @Param("scrapTargetSq") Long scrapTargetSq,
                     @Param("scrapTargetType") String scrapTargetType);

    void deleteScrap(@Param("scrapTargetSq") Long scrapSq);

    List<Object> selectScrapsByMember(@Param("mbrSq") Long mbrSq);

    boolean checkScrapExists(@Param("mbrSq") Long mbrSq,
                             @Param("scrapTargetSq") Long scrapTargetSq,
                             @Param("scrapTargetType") String scrapTargetType);
}
