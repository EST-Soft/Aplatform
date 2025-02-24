package jobplatform.fo.board.mapper;

import jobplatform.fo.board.entity.BoardCommentEntity;
import jobplatform.fo.enterprise.domain.entity.EnterMemberEntity;
import jobplatform.fo.user.domain.entity.MemberEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardCommentMapper {
    /*댓글등록*/
    int insertOne(BoardCommentEntity comment);
    /*댓글 리스트*/
    List<BoardCommentEntity> findAll();
    /*댓글 수정*/
    int updateOne(BoardCommentEntity comment);
    /*댓글 삭제*/
    int deleteOne(long cmntSq);
}
