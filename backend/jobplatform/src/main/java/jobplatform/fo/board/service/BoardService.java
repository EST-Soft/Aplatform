package jobplatform.fo.board.service;

import jobplatform.fo.board.entity.BoardEntity;
import jobplatform.fo.sample.util.Header;
import jobplatform.fo.sample.util.Search;

import java.util.List;

public interface BoardService {
    int register(BoardEntity boardEntity);
    Header<List<BoardEntity>> List(int page, int size, Search search,String brdTypCode);
    BoardEntity board(int brdSq);
    int modify(BoardEntity boardEntity);

    int destroy(int brdSq);
}
