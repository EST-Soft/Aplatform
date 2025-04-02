package jobplatform.fo.user.domain.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import jobplatform.fo.user.domain.dto.JobPostingApplicationDto;

@Mapper
public interface JobPostingApplicationMapper {

    JobPostingApplicationDto getJobPostingApplicationDtoByMbrSq(@Param("mbrSq")Long mbrSq);
}
