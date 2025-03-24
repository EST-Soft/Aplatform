package jobplatform.fo.skill.service;

import org.apache.ibatis.annotations.Param;

public interface SkillCodeService {
    

      public int skillinsert(@Param("rsmSq") Long rsmSq, @Param("sklCodeSq") String sklCodeSq);
}
