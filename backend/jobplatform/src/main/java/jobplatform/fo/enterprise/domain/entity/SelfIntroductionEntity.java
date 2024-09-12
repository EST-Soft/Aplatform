package jobplatform.fo.enterprise.domain.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jobplatform.fo.enterprise.domain.dto.SelfIntroductionsDataDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "p2_tbl_resume_selfintroduction_s")
public class SelfIntroductionEntity {

    // 자기소개서_순번
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "si_sq", nullable = false)
    private Long siSq;

    // 이력서_순번
    @Column(name = "rsm_sq", nullable = false)
    private Long rsmSq;

    // 자기소개서_제목
    @Column(name = "si_ttl", nullable = false, length = 30)
    private String siTtl;

    // 자기소개서_내용
    @Column(name = "si_cntnt", nullable = false, length = 1000)
    private String siCntnt;

    // 등록_회원_순번
    @Column(name = "insrt_mbr_sq", nullable = false)
    private Long insrtMbrSq;

    // 등록_일시
    @Column(name = "insrt_dtm", nullable = false)
    private LocalDateTime insrtDtm = LocalDateTime.now();

    // 수정_회원_순번
    @Column(name = "updt_mbr_sq", nullable = true)
    private Long updtMbrSq;

    // 수정_일시
    @Column(name = "updt_dtm", nullable = true)
    private LocalDateTime updtDtm;

    // 삭제_여부
    @Column(name = "dlt_yn", nullable = false, length = 1)
    private String dltYn = "N";

    // 사용_여부
    @Column(name = "use_yn", nullable = false, length = 1)
    private String useYn = "Y";

    public SelfIntroductionEntity(Long mbrSq, Long rsmSq, SelfIntroductionsDataDTO selfIntroductionsDataDTO) {
        this.rsmSq = rsmSq;
        this.siTtl = selfIntroductionsDataDTO.getSiTtl();
        this.siCntnt = selfIntroductionsDataDTO.getSiCntnt();
        this.insrtMbrSq = mbrSq;
        this.updtMbrSq = mbrSq;
        this.updtDtm = LocalDateTime.now();
    } // SelfIntroductionEntity

} // SelfIntroductionEntity
