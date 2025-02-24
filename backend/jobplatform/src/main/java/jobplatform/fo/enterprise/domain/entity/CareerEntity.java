package jobplatform.fo.enterprise.domain.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jobplatform.fo.enterprise.domain.dto.CareerDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "p2_tbl_resume_career_s")
public class CareerEntity {

    // 경력 순번
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "crr_sq", nullable = false)
    private Long crrSq;

    // 이력서 순번
    @Column(name = "rsm_sq", nullable = false)
    private Long rsmSq;

    // 기업 이름
    @Column(name = "entrprs_name", nullable = false, length = 20)
    private String entrprsName;

    // 기업 직급
    @Column(name = "entrprs_pstn", nullable = true, length = 20)
    private String entrprsPstn;

    // 기업 입사 일
    @Column(name = "entrprs_jac_date", nullable = true)
    private String entrprsJacDate;

    // 기업 퇴사 일
    @Column(name = "entrprs_rsgntn_date", nullable = true)
    private String entrprsRsgntnDate;

    // 등록 회원 순번
    @Column(name = "insrt_mbr_sq", nullable = false)
    private Long insrtMbrSq;

    // 등록 일시
    @Column(name = "insrt_dtm", nullable = false)
    private LocalDateTime insrtDtm = LocalDateTime.now();

    // 수정 회원 순번
    @Column(name = "updt_mbr_sq", nullable = true)
    private Long updtMbrSq;

    // 수정 일시
    @Column(name = "updt_dtm", nullable = true)
    private LocalDateTime updtDtm;

    // 삭제 여부
    @Column(name = "dlt_yn", nullable = false, length = 1)
    private String dltYn = "N";

    // 사용 여부
    @Column(name = "use_yn", nullable = false, length = 1)
    private String useYn = "Y";

    public CareerEntity(Long mbrSq, Long rsmSq, CareerDto careerDto) {

        this.rsmSq = rsmSq;
        this.entrprsName = careerDto.getEntrprsName();
        this.entrprsPstn = careerDto.getEntrprsPstn();
        this.entrprsJacDate = careerDto.getEntrprsJacDate();
        this.entrprsRsgntnDate = careerDto.getEntrprsRsgntnDate();
        this.insrtMbrSq = mbrSq;
        this.updtMbrSq = mbrSq;
        this.updtDtm = LocalDateTime.now();

    } // CareerEntity
} // CareerEntity
