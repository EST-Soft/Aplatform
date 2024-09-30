package jobplatform.fo.board.service;

import java.util.List;

import jobplatform.fo.board.entity.BoardAnswerEntity;
import jobplatform.fo.sample.util.Header;

public interface BoardAnswerService {

    // 답글 등록
    int insertOne(BoardAnswerEntity answer);

    // 답글 선택 완료 체크
    int checkAnswer(int brdSq);

    // 답글 수
    int countAnswer(int brdSq);

    // 답글 리스트
    Header<List<BoardAnswerEntity>> List(int page, int size, int brdSq);

    // 답글 상세보기 
    BoardAnswerEntity answer(int answrSq);

    // 답글 추천여부 확인
    int checkRecommendation(int answrSq, int mbrSq);

    // 답글 추천 업데이트(추천수, 추천 테이블)
    int updateRecommendation(int answrSq, int mbrSq, int value);

    // 답글 채택
    int selectRecommendation(int answrSq);

    // 답글 삭제
    int deleteAnswer(int answrSq);

    // 조회수 업데이트
    int updateHits(int answrSq);

    // 게시글 수정
    int editAnswer(BoardAnswerEntity boardAnswerEntity);

}
