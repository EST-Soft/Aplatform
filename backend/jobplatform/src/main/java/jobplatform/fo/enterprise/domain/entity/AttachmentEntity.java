package jobplatform.fo.enterprise.domain.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jobplatform.fo.enterprise.domain.dto.AttachmentDto;
import jobplatform.fo.enterprise.domain.dto.ResumeDataDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "p2_tbl_resume_attachment_s")
public class AttachmentEntity {

    // 첨부파일_순번
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "atchmnt_sq", nullable = false)
    private Long atchmntSq;

    // 이력서_순번
    @Column(name = "rsm_sq", nullable = true)
    private Long rsmSq;

    // 첨부파일_원본_파일명
    @Column(name = "atchmnt_orgnl_fn", nullable = false, length = 50)
    private String atchmntOrgnlFn;

    // 첨부파일_url
    @Column(name = "atchmnt_url", nullable = false, length = 100)
    private String atchmntUrl;

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

    public AttachmentEntity(Long mbrSq, Long rsmSq, ResumeDataDTO resumeDataDTO) {
        this.rsmSq = rsmSq;
        this.atchmntOrgnlFn = resumeDataDTO.getRsmImgOrgnlFn();
        this.atchmntUrl = resumeDataDTO.getRsmImgFileUrl();
        this.insrtMbrSq = mbrSq;
    } // AttachmentEntity

    public AttachmentEntity(Long mbrSq, Long rsmSq, AttachmentDto attachmentDto) {
        this.rsmSq = rsmSq;
        this.atchmntOrgnlFn = attachmentDto.getAtchmntOrgnlFn();
        this.atchmntUrl = attachmentDto.getAtchmntUrl();
        this.insrtMbrSq = mbrSq;
    } // AttachmentEntity

} // Attachment
