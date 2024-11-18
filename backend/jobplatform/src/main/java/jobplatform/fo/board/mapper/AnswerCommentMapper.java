package jobplatform.fo.board.mapper;

import jobplatform.fo.board.entity.AnswerCommentEntity;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface AnswerCommentMapper {
    List<AnswerCommentEntity> selectCommentsByAnswerId(long answrSq);
    void insertComment(AnswerCommentEntity comment);
    void updateComment(AnswerCommentEntity comment);
    void deleteComment(long cmntSq);
}
