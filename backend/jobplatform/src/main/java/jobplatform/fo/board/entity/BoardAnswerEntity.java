package jobplatform.fo.board.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;



@Data
@Entity
@Table(name = "P3_TBL_BOARD_ANSWER")
public class BoardAnswerEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answr_sq")
    private long answrSq;

    @Column(name = "atchmnt_sq", nullable=true)
    private long atchmntSq;

    @Column(name = "brd_sq", nullable = false)
    private long brdSq;

    @Column(name = "mbr_sq", nullable = true)
    private long mbrSq;

    @Column(name = "entrprs_sq", nullable = true)
    private long entrprsSq;

    @Column(name = "answr_ttl", nullable = false, length = 100)
    private String answrTtl;

    @Column(name = "answr_cntnt", nullable = false, columnDefinition = "TEXT")
    private String answrCntnt;

    @Column(name = "answr_hits", nullable=false)
    private int answrHits;

    @Column(name = "answr_rcmndtns", nullable = false)
    private int answrRcmndtns;

    @Column(name = "answr_slctn_yn", nullable = false)
    private char answrSlctnYn = 'N';

    @Column(name = "insrt_dtm", nullable = false)
    private LocalDateTime insrtDtm = LocalDateTime.now();

    @Column(name = "updt_dtm", nullable = true)
    private LocalDateTime updtDtm;

    @Column(name = "dlt_dtm", nullable = true)
    private LocalDateTime dltDtm;

    @Column(name = "dlt_yn", nullable = false)
    private char dltYn = 'N';


}
