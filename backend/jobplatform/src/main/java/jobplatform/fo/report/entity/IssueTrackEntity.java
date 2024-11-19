package jobplatform.fo.report.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "P5_TBL_ISSUE_TRACK")
public class IssueTrackEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "issue_sq")
    private Long issueSq; // 신고 ID

    @Column(name = "brd_sq", nullable = false)
    private Long brdSq; // 게시판 시퀀스 ID

    @Column(name = "brd_typ_code", nullable = false, length = 20)
    private String brdTypCode; // 게시판 타입 코드

    @Column(name = "issue_typ", nullable = false, length = 20)
    private String issueTyp; // 신고 유형 (POST, ANSWER, COMMENT)

    @Column(name = "mbr_sq", nullable = false)
    private Long mbrSq; // 신고자 회원 ID

    @Column(name = "entrprs_sq", nullable = true)
    private Long entrprsSq; // 신고자 기업 회원 ID (NULL 가능)

    @Column(name = "issue_rsn", nullable = false, length = 1000)
    private String issueRsn; // 신고 사유

    @Column(name = "issue_stat", nullable = false, length = 20)
    private String issueStat = "PENDING"; // 신고 상태 (PENDING, RESOLVED, REJECTED)

    @Column(name = "insrt_dtm", nullable = false)
    private LocalDateTime insrtDtm = LocalDateTime.now(); // 신고 생성일

    @Column(name = "updt_dtm", nullable = true)
    private LocalDateTime updtDtm; // 신고 상태 변경일

    @Column(name = "dlt_dtm", nullable = true)
    private LocalDateTime dltDtm; // 삭제일

    @Column(name = "dlt_yn", nullable = false)
    private char dltYn = 'N'; // 삭제 여부 ('Y' 또는 'N')
}
