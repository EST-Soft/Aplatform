package jobplatform.fo.board.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import jobplatform.fo.board.entity.BoardAnswerEntity;

@Mapper
public interface BoardAnswerMapper {
    
    // 답글 등록
    int insertOne(BoardAnswerEntity answer);

    // 답글 채택여부
    int checkAnswer(int brdSq);

    // 답글 수
    int countAnswer(int brdSq);

    // 리스트 불러오기
    List<BoardAnswerEntity> findAll(HashMap<String, Object> paramMap);

}
