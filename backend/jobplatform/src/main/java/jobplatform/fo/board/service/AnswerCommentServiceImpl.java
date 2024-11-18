package jobplatform.fo.board.service;

import jobplatform.fo.board.entity.AnswerCommentEntity;
import jobplatform.fo.board.mapper.AnswerCommentMapper;
import jobplatform.fo.board.service.AnswerCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerCommentServiceImpl implements AnswerCommentService {

    @Autowired
    private AnswerCommentMapper answerCommentMapper;

    @Override
    public List<AnswerCommentEntity> getCommentsByAnswerId(long answrSq) {
        return answerCommentMapper.selectCommentsByAnswerId(answrSq);
    }

    @Override
    public void addComment(AnswerCommentEntity comment) {
        answerCommentMapper.insertComment(comment);
    }

    @Override
    public void updateComment(long cmntSq, String newContent) {
        AnswerCommentEntity comment = new AnswerCommentEntity();
        comment.setCmntSq(cmntSq);
        comment.setCmntCntnt(newContent);
        answerCommentMapper.updateComment(comment);
    }

    @Override
    public void deleteComment(long cmntSq) {
        answerCommentMapper.deleteComment(cmntSq);
    }
}
