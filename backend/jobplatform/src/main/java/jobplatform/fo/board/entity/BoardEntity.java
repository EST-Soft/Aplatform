package jobplatform.fo.board.entity;

import jakarta.persistence.*;
import jobplatform.fo.enterprise.domain.entity.EnterMemberEntity;
import jobplatform.fo.user.domain.entity.MemberEntity;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "P3_TBL_BOARD") // 테이블 이름을 명시
public class BoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가
    @Column(name = "brd_sq")
    private int brdSq;           // 게시글 순번

    @Column(name = "mbr_sq", nullable = true)
    private Integer mbrSq;       // 회원 순번 (null 가능)

    @Column(name = "entrprs_sq", nullable = true)
    private Long entrprsSq;      // 기업 순번 (null 가능)

    @Column(name = "brd_gnr_cd", nullable = false, length = 50)
    private String brdGnrCd;     // 게시글 분류 코드

    @Column(name = "brd_ttl", nullable = false, length = 255)
    private String brdTtl;       // 게시글 제목

    @Column(name = "brd_cntnt", nullable = false, columnDefinition = "TEXT")
    private String brdCntnt;     // 게시글 내용

    @Column(name = "brd_hits", nullable = false)
    private int brdHits;         // 게시글 조회수

    @Column(name = "insrt_dtm", nullable = false)
    private LocalDateTime insrtDtm=LocalDateTime.now();  // 생성 일시

    @Column(name = "updt_dtm", nullable = false)
    private LocalDateTime updtDtm ;   // 수정 일시-수정시 업데이트

    @Column(name = "dlt_dtm", nullable = true)
    private LocalDateTime dltDtm;    // 삭제 일시 - 삭제시 업데이트 시간

    @Column(name = "dlt_yn", nullable = false, length = 1 )
    private String dltYn ="N";        // 삭제 여부 ('N' 또는 'Y')

    private String createdBy; // 새로운 필드 추가

    // 댓글 목록 추가
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BoardCommentEntity> comments;
    // 작성자 정보 필드 추가


}