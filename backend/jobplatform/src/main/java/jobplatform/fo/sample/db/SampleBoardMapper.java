package jobplatform.fo.sample.db;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import jobplatform.fo.enterprise.domain.dto.ApplyConditionDataDTO;
import jobplatform.fo.enterprise.domain.dto.ApplySearchDataDTO;
import jobplatform.fo.enterprise.domain.vo.ApplyDetailDataVO;
import jobplatform.fo.enterprise.domain.vo.ApplyListVO;

@Mapper
public interface SampleBoardMapper {
	
    /*
    mapper xml파일의 resultType 해당하는 클래스에 결과를 담으며,
    N개가 되므로 MutableList로 Return 타입을 설정합니다.
     */
	List<SampleBoardEntity> getBoardList(HashMap<String, Object> paramMap);
	
	int getBoardTotalCount(HashMap<String, Object> paramMap);
	
	SampleBoardEntity getBoardOne(Long idx);
	
	int insertBoard(SampleBoardEntity entity);
	
	int updateBoard(SampleBoardEntity entity);

	int hitsBoard(Long idx);
	
	int deleteBoard(Long idx);

}
