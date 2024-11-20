package jobplatform.fo.user.domain.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "P5_TBL_SCRAP")
public class ScrapEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scrap_sq")
    private Long scrapSq; // 스크랩 ID

    @Column(name = "mbr_sq", nullable = false)
    private Long mbrSq; // 회원 ID

    @Column(name = "entrprs_sq", nullable = true)
    private Long entrprsSq; // 기업 ID (선택 사항)

    @Column(name = "scrap_target_sq", nullable = false)
    private Long scrapTargetSq; // 스크랩 대상 ID (게시글, 답글, 댓글 등)

    @Column(name = "scrap_target_type", nullable = false, length = 10)
    private String scrapTargetType; // 스크랩 대상 유형 (POST, ANSWER, COMMENT)

    @Column(name = "insrt_dtm", nullable = false)
    private LocalDateTime insrtDtm = LocalDateTime.now(); // 스크랩 생성일

    @Column(name = "dlt_dtm", nullable = true)
    private LocalDateTime dltDtm; // 삭제일

    @Column(name = "dlt_yn", nullable = false)
    private char dltYn = 'N'; // 삭제 여부 ('Y' 또는 'N')
}
