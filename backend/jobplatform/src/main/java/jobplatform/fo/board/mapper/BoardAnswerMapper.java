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

    // 상세보기
    BoardAnswerEntity answer(int answrSq);

    // 조회수 증가
    int updateHits(int answrSq);

    // 추천수 업데이트
    void updateRcmndtns(int answrSq, int value);
    
    // 비추천수 업데이트
    void updateNotRcmndtns(int answrSq, int value);

    // 답변 추천 테이블에 등록
    int insertRecommendation(int answrSq, int mbrSq);

    // 답변 추천 테이블에서 삭제
    int deleteRecommendation(int answrSq, int mbrSq);
    
    // 답변 비추천 테이블에 등록
    int insertNotRecommendation(int answrSq, int mbrSq);

    // 답변 비추천 테이블에서 삭제
    int deleteNotRecommendation(int answrSq, int mbrSq);

    // 답변 추천여부 확인
    int checkRecommendation(int answrSq, int mbrSq);
    
    // 답변 비추천여부 확인
    int checkNotRecommendation(int answrSq, int mbrSq);

    // 답변 채택
    int selectRecommendation(int answrSq);
    
    // 게시글 채택 상태
    int updateBoardCondition(int brdSq);

    // 답변 삭제
    int deleteAnswer(int answrSq);

    // 답변 수정
    int editAnswer(BoardAnswerEntity boardAnswerEntity);


}
