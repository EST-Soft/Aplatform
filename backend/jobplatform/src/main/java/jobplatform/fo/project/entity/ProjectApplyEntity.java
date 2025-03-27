package jobplatform.fo.project.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name ="p5_tbl_project_apply")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectApplyEntity {

    @Id
    @Column(name = "prjct_apl_sq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prjctAplSq;
    
    @Column(name = "prjct_sq")
    private Long prjctSq;

    @Column(name = "mbr_sq")
    private Long mbrSq;

    @Column(name = "entrprs_sq")
    private Long entrprsSq;

    @Column(name = "rsm_sq")
    private Long rsmSq;

    @Column(name = "prjct_apl_dtm", nullable = false)
    private LocalDateTime prjctAplDtm = LocalDateTime.now(); // 기본값 현재 시간

    @Column(name = "prjct_pass_yn")
    private String prjctPassYn;
}
