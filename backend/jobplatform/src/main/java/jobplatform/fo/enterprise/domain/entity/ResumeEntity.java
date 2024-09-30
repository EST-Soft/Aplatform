package jobplatform.fo.enterprise.domain.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jobplatform.fo.enterprise.domain.dto.ResumeDataDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "p2_tbl_resume_m")
public class ResumeEntity {

    // 이력서_순번
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rsm_sq", nullable = false)
    private Long rsmSq;

    // 회원_순번
    @Column(name = "mbr_sq", nullable = false)
    private Long mbrSq;

    // 이력서_대표_여부
    @Column(name = "rsm_rprsntv_yn", nullable = false, length = 1)
    private String rsmRprsntvYn = "N";

    // 이력서_이미지_원본_파일명
    @Column(name = "rsm_img_orgnl_fn", nullable = true, length = 50)
    private String rsmImgOrgnlFn;

    // 이력서_이미지_파일_url
    @Column(name = "rsm_img_file_url", nullable = true, length = 100)
    private String rsmImgFileUrl;

    // 이력서_최종_학력_코드
    @Column(name = "rsm_fnl_edctn_code", nullable = false, length = 10)
    private String rsmFnlEdctnCode;

    // 이력서_학점
    @Column(name = "rsm_grd", nullable = true)
    private int rsmGrd;

    // 이력서_희망연봉
    @Column(name = "rsm_es", nullable = true)
    private int rsmEs;

    // 이력서_제목
    @Column(name = "rsm_ttl", nullable = false, length = 30)
    private String rsmTtl;

    // 이력서_이름
    @Column(name = "rsm_name", nullable = false, length = 20)
    private String rsmName;

    // 이력서_성별_코드
    @Column(name = "rsm_gndr_code", nullable = false, length = 10)
    private String rsmGndrCode;

    // 이력서_생년월일
    @Column(name = "rsm_bd", nullable = false)
    private String rsmBd;

    // 이력서_휴대폰
    @Column(name = "rsm_mp", nullable = false, length = 20)
    private String rsmMp;

    // 이력서_주소
    @Column(name = "rsm_adrs", nullable = false, length = 50)
    private String rsmAdrs;

    // 이력서_이메일
    @Column(name = "rsm_eml", nullable = false, length = 50)
    private String rsmEml;

    // 등록_회원_순번
    @Column(name = "insrt_mbr_sq", nullable = false)
    private Long insrtMbrSq;

    // 동록_일시
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

    public enum YesNo {
        Y, N
    } // YesNo

    public ResumeEntity(Long mbrSq, ResumeDataDTO resumeDataDTO) {
        String fileUrl = null;
        String originalFileName = null;

        if (resumeDataDTO.getRsmImgOrgnlFn() != null && resumeDataDTO.getRsmImgFileUrl() != null) {
            fileUrl = resumeDataDTO.getRsmImgOrgnlFn();
            originalFileName = resumeDataDTO.getRsmImgFileUrl();
        }

        this.mbrSq = mbrSq;
        this.rsmImgOrgnlFn = fileUrl;
        this.rsmImgFileUrl = originalFileName;
        this.rsmFnlEdctnCode = resumeDataDTO.getRsmFnlEdctnCode();
        this.rsmGrd = resumeDataDTO.getRsmGrd();
        this.rsmEs = resumeDataDTO.getRsmEs();
        this.rsmTtl = resumeDataDTO.getRsmTtl();
        this.rsmName = resumeDataDTO.getRsmName();
        this.rsmGndrCode = resumeDataDTO.getRsmGndrCode();
        this.rsmBd = formatDateOfBirth(resumeDataDTO.getRsmBd());
        this.rsmMp = formatPhoneNumber(resumeDataDTO.getRsmMp());
        this.rsmAdrs = resumeDataDTO.getRsmAdrs();
        this.rsmEml = resumeDataDTO.getRsmEml();
        this.insrtMbrSq = mbrSq;
        this.updtMbrSq = mbrSq;
        this.updtDtm = LocalDateTime.now();
    } // ResumeEntity

    private String formatDateOfBirth(String rawBd) {
        return String.format("%s-%s-%s", rawBd.substring(0, 4), rawBd.substring(4, 6), rawBd.substring(6, 8));
    }

    private String formatPhoneNumber(String rawMp) {
        return String.format("%s-%s-%s", rawMp.substring(0, 3), rawMp.substring(3, 7), rawMp.substring(7, 11));
    }

} // ResumeEntity
