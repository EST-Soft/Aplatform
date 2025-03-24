package jobplatform.fo.skill.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SkillCodeMapper {
    
    public int skillinsert(@Param("rsmSq") Long rsmSq, @Param("sklCodeSq") Long sklCodeSq);
}
