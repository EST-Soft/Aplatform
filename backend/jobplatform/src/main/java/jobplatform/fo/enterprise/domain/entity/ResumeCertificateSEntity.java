package jobplatform.fo.enterprise.domain.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jobplatform.fo.enterprise.domain.dto.ResumeCertificateSDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "p2_tbl_resume_certificate_s")
public class ResumeCertificateSEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "crtfct_sq")
    private Long crtfctSq;

    // 이력서_순번
    @Column(name = "rsm_sq", nullable = false)
    private Long rsmSq;

    // 자격증_이름
    @Column(name = "crtfct_name", nullable = false, length = 30)
    private String crtfctName;

    // 자격증_발행기관
    @Column(name = "crtfct_isr", nullable = true, length = 30)
    private String crtfctIsr;

    // 자격증_취득_일
    @Column(name = "crtfct_ad", nullable = true)
    private String crtfctAd;

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

    public ResumeCertificateSEntity(Long mbrSq, Long rsmSq, ResumeCertificateSDto resumeCertificateSDto) {

        this.rsmSq = rsmSq;
        this.crtfctName = resumeCertificateSDto.getCrtfctName();
        this.crtfctIsr = resumeCertificateSDto.getCrtfctIsr();
        this.crtfctAd = resumeCertificateSDto.getCrtfctAd();
        this.insrtMbrSq = mbrSq;
        this.updtMbrSq = mbrSq;
        this.updtDtm = LocalDateTime.now();
    } // ResumeCertificateSEntity

} // ResumeCertificateSEntity
