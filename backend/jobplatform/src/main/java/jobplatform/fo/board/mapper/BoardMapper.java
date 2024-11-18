package jobplatform.fo.board.mapper;

import jobplatform.fo.board.entity.BoardEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface BoardMapper {
    int insertOne(BoardEntity boardEntity);
    List<BoardEntity> findAll();

    List<BoardEntity> findAll(HashMap<String, Object> paramMap);

    int getBoardTotalCount(HashMap<String, Object> paramMap);

    BoardEntity findOne(int brdSq);

    int updateOne(BoardEntity boardEntity);

    int deleteOne(int brdSq);

    void hitUpdate(int brdSq);
    
    // 3일지난 게시글 수저 쿼리
    int updateBoardCondition();
    
    // 자체 채택
    int selectSelfRecommendation(int brdSq);
}
