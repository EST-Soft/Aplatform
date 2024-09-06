package jobplatform.fo.user.domain.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jobplatform.fo.user.domain.vo.EducationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "p2_tbl_resume_education_s")
public class EducationEntity {

    // 학력_순번
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "edctn_sq", nullable = false)
    private Long edctnSq;

    // 이력서_순번
    @Column(name = "rsm_sq", nullable = true)
    private Long rsmSq;

    // 학교_이름
    @Column(name = "schl_name", nullable = false, length = 25)
    private String schlName;

    // 학교_상태_코드
    @Column(name = "schl_cndtn_code", nullable = false, length = 10)
    private String schlCndtnCode;

    // 학교_학과
    @Column(name = "schl_dprtmnt", nullable = true, length = 10)
    private String schlDprtmnt;

    // 학교_입학_일
    @Column(name = "schl_admsn_date", nullable = true)
    private String schlAdmsnDate;

    // 학교_졸업_일
    @Column(name = "schl_grdtn_date", nullable = true)
    private String schlGrdtnDate;

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

    public EducationEntity(Long mbrSq, Long rsmSq, EducationDto educationDto) {
        LocalDateTime now = LocalDateTime.now();
        String insrtNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        this.rsmSq = rsmSq;
        this.schlName = educationDto.getSchlName();
        this.schlCndtnCode = educationDto.getSchlCndtnCode();
        this.schlDprtmnt = educationDto.getSchlDprtmnt();
        this.schlAdmsnDate = educationDto.getSchlAdmsnDate();
        this.schlGrdtnDate = educationDto.getSchlGrdtnDate();
        this.insrtMbrSq = mbrSq;
        this.updtDtm = LocalDateTime.now();
        this.updtMbrSq = mbrSq;

    } // Education

} // EducationEntity
