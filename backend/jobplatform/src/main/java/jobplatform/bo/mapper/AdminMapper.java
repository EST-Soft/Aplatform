package jobplatform.bo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import jobplatform.bo.domain.vo.AdminVo;

@Mapper
public interface AdminMapper {
	
	//Admin 계정 테이블의 모든 정보를 조회하는 mapper 로직
	 @Select("SELECT * FROM p3_tbl_admin_m WHERE adm_id = #{admId}")
	AdminVo findByAdminId(String admId);

	
}
