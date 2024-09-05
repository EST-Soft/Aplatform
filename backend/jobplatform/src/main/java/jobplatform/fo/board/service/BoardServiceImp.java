package jobplatform.fo.board.service;

import jobplatform.fo.board.entity.BoardEntity;
import jobplatform.fo.board.mapper.BoardMapper;
import jobplatform.fo.sample.util.Header;
import jobplatform.fo.sample.util.Pagination;
import jobplatform.fo.sample.util.Search;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
@AllArgsConstructor
public class BoardServiceImp implements BoardService{
    private BoardMapper boardMapper;

    @Override
    public int register(BoardEntity boardEntity) {
        return boardMapper.insertOne(boardEntity);
    }

    @Override
    public Header<List<BoardEntity>> List(int page, int size, Search search,String brdTypCode) {
        HashMap<String, Object> paramMap = new HashMap<>();

        // 페이지 계산
        if (page <= 1) {
            paramMap.put("page", 0);
        } else {
            paramMap.put("page", (page - 1) * size);
        }
        paramMap.put("size", size);

        // 검색 키워드가 존재할 경우만 파라미터 추가
        if (search.getSk() != null && !search.getSk().isEmpty()) {
            paramMap.put("sk", search.getSk());
            paramMap.put("sv", search.getSv());
        }
        System.out.println(brdTypCode);
        if (brdTypCode != null && !brdTypCode.isEmpty()) {
            System.out.println("출력되고있어"+brdTypCode);

            paramMap.put("brdTypCode", brdTypCode);
        }
        System.out.println(paramMap);
        // 데이터 가져오기
        List<BoardEntity> boardList = boardMapper.findAll(paramMap);

        int totalCount = boardMapper.getBoardTotalCount(paramMap);
        Pagination pagination = new Pagination(totalCount, page, size, 10);

        return Header.OK(boardList, pagination);
    }

    @Override
    public BoardEntity board(int brdSq) {

        boardMapper.hitUpdate(brdSq);
        return boardMapper.findOne(brdSq);
    }

    @Override
    public int modify(BoardEntity boardEntity) {
        boardMapper.updateOne(boardEntity);
        return 0;
    }

    @Override
    public int destroy(int brdSq) {
        boardMapper.deleteOne(brdSq);
        return 0;
    }

}
