package jobplatform.bo.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;

import jobplatform.bo.dto.adm_JobPostingDTO;

@MapperScan("jobplatform.bo.mapper")
@Mapper
public interface adm_JobPostingMapper {
	
    // 공고 관리 다 가져오기
    List<adm_JobPostingDTO> selectAllJobPostings();


}
