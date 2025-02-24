package jobplatform.fo.board.entity;

import jakarta.persistence.*;
import jobplatform.fo.enterprise.domain.entity.EnterMemberEntity;
import jobplatform.fo.user.domain.entity.MemberEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "p3_tbl_board_comment")
@Data
public class BoardCommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cmnt_sq")
    private Long cmntSq;  // 댓글 순번

    @Column(name = "mbr_sq", nullable = true)
    private Integer mbrSq;  // 회원 순번 (nullable)

    @Column(name = "entrprs_sq", nullable = true)
    private Long entrprsSq;  // 기업 순번 (nullable)

    @Column(name = "brd_sq", nullable = false)
    private Integer brdSq;  // 게시글 순번

    @Column(name = "cmnt_cntnt", nullable = false, length = 10000)
    private String cmntCntnt;  // 댓글 내용

    @Column(name = "insrt_dtm")
    private LocalDateTime insrtDtm=LocalDateTime.now();;  // 등록 일시

    @Column(name = "updt_dtm")
    private LocalDateTime updtDtm;  // 수정 일시

    @Column(name = "dlt_dtm")
    private LocalDateTime dltDtm;  // 삭제 일시

    @Column(name = "dlt_yn", nullable = false, columnDefinition = "char(1) default 'N'")
    private String dltYn = "N";  // 삭제 여부 ('Y' 또는 'N')

    // BoardEntity와의 연관관계 추가
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brd_sq", insertable = false, updatable = false)
    private BoardEntity board;

    // 연관 관계 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mbr_sq", insertable = false, updatable = false)
    private MemberEntity member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entrprs_sq", insertable = false, updatable = false)
    private EnterMemberEntity entMember;

}
