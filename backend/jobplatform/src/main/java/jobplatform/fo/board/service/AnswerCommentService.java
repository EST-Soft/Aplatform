package jobplatform.fo.board.service;

import jobplatform.fo.board.entity.AnswerCommentEntity;
import java.util.List;

public interface AnswerCommentService {
    List<AnswerCommentEntity> getCommentsByAnswerId(long answrSq);
    void addComment(AnswerCommentEntity comment);
    void updateComment(long cmntSq, String newContent);
    void deleteComment(long cmntSq);
}
