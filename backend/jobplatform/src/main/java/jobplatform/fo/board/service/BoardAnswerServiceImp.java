package jobplatform.fo.board.service;

import java.util.*;

import org.springframework.stereotype.Service;

import jobplatform.fo.board.entity.BoardAnswerEntity;
import jobplatform.fo.board.mapper.BoardAnswerMapper;
import jobplatform.fo.sample.util.Pagination;
import jobplatform.fo.sample.util.Header;


@Service
public class BoardAnswerServiceImp implements BoardAnswerService {

    private BoardAnswerMapper boardAnswerMapper;

     public BoardAnswerServiceImp(BoardAnswerMapper boardAnswerMapper) {
        this.boardAnswerMapper = boardAnswerMapper;
    }


    @Override
    public int insertOne(BoardAnswerEntity answer){
        System.out.println("서비스에서 answer 값" + answer);
        int result = boardAnswerMapper.insertOne(answer);
        System.out.println("서비스에서 result 값" + result);
        return result;
    }


    @Override
    public int checkAnswer(int brdSq){
        System.out.println("서비스에서 brdSq 값" + brdSq);
        int result = boardAnswerMapper.checkAnswer(brdSq);
        System.out.println("서비스에서 result 값 : " + result);
        return result;
    }


    @Override
    public int countAnswer(int brdSq) {
        int result = boardAnswerMapper.countAnswer(brdSq);
        return result;
    }


    @Override
    public Header<List<BoardAnswerEntity>> List(int page, int size, int brdSq) {
        HashMap<String, Object> paramMap = new HashMap<>();

        System.out.println("서비스에서 brdSq : " + brdSq);
            System.out.println("서비스에서 page : " + page);
            System.out.println("서비스에서 size : " + size);

        // 페이지 계산
        if(page <= 1){
            paramMap.put("page", 0);
        }else {
            paramMap.put("page", (page-1)*size);
        }
        paramMap.put("size", size);
        paramMap.put("brdSq", brdSq);

        // 데이터 가져오기
        List<BoardAnswerEntity> answerList = boardAnswerMapper.findAll(paramMap);

        int totalCount = boardAnswerMapper.countAnswer(brdSq);
        Pagination pagination = new Pagination(totalCount, page, size, 5);

        System.out.println("서비스에서는?" + answerList);
        System.out.println("parmaMap : " + paramMap);
        System.out.println("totalCount" + totalCount);

        return Header.OK(answerList, pagination);

    }
    

}
