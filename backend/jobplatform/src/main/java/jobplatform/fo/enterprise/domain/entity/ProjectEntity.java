package jobplatform.fo.enterprise.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "project")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prjct_sq")
    private Long prjctSq;

    @Column(name = "entrprs_sq", nullable = false)
    private Long entrprsSq;

    @Column(name = "prjct_ttl", nullable = false, length = 50)
    private String prjctTtl;

    @Column(name = "prjct_tpy", nullable = false, length = 100)
    private String prjctTpy;

    @Column(name = "prjct_work", nullable = false, length = 100)
    private String prjctWork;

    @Column(name = "prjct_use_skl", nullable = false, length = 100)
    private String prjctUseSkl;

    @Column(name = "prjct_esntl_skl", nullable = true, length = 100)
    private String prjctEsntlSkl;

    @Column(name = "prjct_esntl_crr", nullable = false, length = 20)
    private String prjctEsntlCrr;

    @Column(name = "prjct_strt", nullable = false)
    private LocalDateTime prjctStrt;

    @Column(name = "prjct_end", nullable = true)
    private LocalDateTime prjctEnd;

    @Column(name = "prjct_lctn", nullable = false, length = 50)
    private String prjctLctn;

    @Column(name = "ntfctn_send_yn", nullable = false, length = 1)
    private String ntfctnSendYn;

    @Column(name = "prjct_prd", nullable = false, length = 30)
    private String prjctPrd;
}
