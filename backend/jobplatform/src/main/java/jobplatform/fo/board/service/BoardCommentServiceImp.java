package jobplatform.fo.board.service;

import jobplatform.fo.board.entity.BoardCommentEntity;
import jobplatform.fo.board.mapper.BoardCommentMapper;
import org.springframework.stereotype.Service;


@Service
public class BoardCommentServiceImp implements BoardCommentService {
    private BoardCommentMapper boardCommentMapper;

    public BoardCommentServiceImp(BoardCommentMapper boardCommentMapper) {
        this.boardCommentMapper = boardCommentMapper;
    }

    @Override
    public int findOne(BoardCommentEntity comment) {
        // 삽입하기 전에 객체의 정보를 콘솔에 출력
        System.out.println("삽입 전 댓글 데이터: " + comment);

        // 데이터베이스에 삽입
        int result = boardCommentMapper.insertOne(comment);

        // 삽입 후에도 동일한 객체를 출력 (이때 cmntSq 값이 자동으로 생성되었을 수 있습니다)
        System.out.println("삽입 후 댓글 데이터: " + comment);

        // 삽입 결과를 반환 (보통 성공적으로 삽입된 행의 개수를 반환)
        return (comment.getCmntSq()).intValue();
    }

    @Override
    public int update(BoardCommentEntity comment) {
        return boardCommentMapper.updateOne(comment);
    }

    @Override
    public int destroy(long cmntSq) {
        return boardCommentMapper.deleteOne(cmntSq);
    }
}
