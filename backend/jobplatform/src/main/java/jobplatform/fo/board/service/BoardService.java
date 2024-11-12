package jobplatform.fo.board.service;

import jobplatform.fo.board.entity.BoardEntity;
import jobplatform.fo.sample.util.Header;
import jobplatform.fo.sample.util.Search;
import jobplatform.fo.sample.util.Sort;
import jobplatform.fo.sample.util.Selection;

import java.util.List;

public interface BoardService {
    int register(BoardEntity boardEntity);
    Header<List<BoardEntity>> List(int page, int size, Search search, String brdTypCode, Sort sort, Selection selection);
    BoardEntity board(int brdSq);
    int modify(BoardEntity boardEntity);
    int destroy(int brdSq);
   
    // 자체채택
    int selectSelfRecommendation(int brdSq);
}
