package jobplatform.bo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import jobplatform.bo.domain.vo.AdminFAQDto;

@Mapper
public interface FaqMapper {
	
	// FAQ 게시판 database에서 게시글을 가져오는 mapper 로직
	@Select("SELECT * FROM p3_tbl_faq")
	List<AdminFAQDto> getAllFaqs();

}
