package jobplatform.fo.project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import jobplatform.fo.project.domain.UserEmailDomain;

@Mapper
public interface ResumeFilterMapper {
    List<UserEmailDomain> findEligibleUsers(@Param("skillCodes") List<Long> skillCodes, 
    @Param("careerConditions") List<Long> careerConditions);
}
