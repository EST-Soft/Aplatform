package jobplatform.fo.board.service;

import java.util.List;

import jobplatform.fo.board.entity.BoardAnswerEntity;
import jobplatform.fo.sample.util.Header;

public interface BoardAnswerService {

    // 답글 등록
    int insertOne(BoardAnswerEntity answer);

    // 답변완료 체크
    int checkAnswer(int brdSq);

    // 답변 수
    int countAnswer(int brdSq);

    // 답변 리스트
    Header<List<BoardAnswerEntity>> List(int page, int size, int brdSq);

}
