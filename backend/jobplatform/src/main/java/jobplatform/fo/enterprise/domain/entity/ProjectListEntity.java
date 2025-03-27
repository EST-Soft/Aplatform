package jobplatform.fo.enterprise.domain.entity;

import java.time.LocalDate;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "P5_TBL_PROJECT")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectListEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prjct_sq", nullable = false)
    private long prjctSq;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "entrprs_sq", referencedColumnName = "entrprs_sq", nullable = false)
    private EnterMemberEntity enterpriseMember;

    @Column(name = "prjct_ttl", nullable = false,length = 50)
    private String prjctTtl;
    @Column(name = "prjct_tpy", nullable = false,length = 100)
    private String prjctTpy;
    @Column(name = "prjct_work", nullable = false,length = 100)
    private String prjctWork;
    @Column(name = "prjct_use_skl", nullable = false,length = 100)
    private String prjctUseSkl;
    @Column(name = "prjct_esntl_skl", nullable = true,length = 100)
    private String prjctEsntlSkl;
    @Column(name = "prjct_esntl_crr", nullable = false,length = 20)
    private String prjctEsntlCrr;

   @Column(name = "prjct_strt_date", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate prjctStrtDate;
    @Column(name = "prjct_end_date", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate prjctEndDate;

    @Column(name = "prjct_lctn", nullable = false,length = 50)
    private String prjctLctn;
    @Column(name = "prjct_prd", nullable = false,length = 500)
    private String prcjtPrd;
    @Column(name = "prjct_cntnt", nullable = true,length = 1000)
    private String prjctCntnt;
    
}
