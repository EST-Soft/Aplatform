package jobplatform.fo.board.service;

import jobplatform.fo.board.entity.BoardCommentEntity;

public interface BoardCommentService {
    //댓글등록
    int findOne(BoardCommentEntity comment);
    //댓글 수정
    int update(BoardCommentEntity comment);
    //댓글 삭제
    int destroy(long cmntSq);
}
