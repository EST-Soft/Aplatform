package jobplatform.fo.board.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "P5_TBL_ANSWER_COMMENT")
public class AnswerCommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cmnt_sq")
    private long cmntSq; // 댓글 ID

    @Column(name = "answr_sq", nullable = false)
    private long answrSq; // 답변 ID

    @Column(name = "mbr_sq", nullable = false)
    private Long mbrSq; // 회원 ID

    @Column(name = "entrprs_sq", nullable = true)
    private Long entrprsSq; // 기업 ID

    @Column(name = "cmnt_cntnt", nullable = false, length = 10000)
    private String cmntCntnt; // 댓글 내용

    @Column(name = "insrt_dtm", nullable = false)
    private LocalDateTime insrtDtm = LocalDateTime.now(); // 댓글 작성일

    @Column(name = "updt_dtm", nullable = true)
    private LocalDateTime updtDtm; // 댓글 수정일

    @Column(name = "dlt_dtm", nullable = true)
    private LocalDateTime dltDtm; // 댓글 삭제일

    @Column(name = "dlt_yn", nullable = false)
    private char dltYn = 'N'; // 삭제 여부 ('Y' 또는 'N')

    private String createdBy;
}
