package jobplatform.fo.enterprise.domain.mapper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import jobplatform.fo.enterprise.domain.vo.CommonCodeVO;

@Mapper
public interface CommonCodeMapper {

	//지원상태 정보 얻기
	List<CommonCodeVO> selectCommonCodeApplyCategoryData() throws SQLException, IOException;
	//학력, 경력 정보 얻기
    List<CommonCodeVO> findCommonCode();

}
