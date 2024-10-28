package jobplatform.fo.enterprise.domain.entity;

import java.time.LocalDateTime;

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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "p2_tbl_apply_h")
public class ApplyEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "apy_sq")
	private Long apySq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rsm_sq", referencedColumnName = "rsm_sq")
    private ResumeEntity resume;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jbp_sq", referencedColumnName = "jbp_sq")
    private JobPostingEntity jobPosting;

    @Column(name = "apy_dtm", nullable = false)
    private LocalDateTime apyDtm;

    @Column(name = "apy_cndtn")
    private String apyCndtn = "unrd"; // 기본값 설정

    @Column(name = "entrprs_read_dtm")
    private LocalDateTime entrprsReadDtm;

    @Column(name = "intv_dtm")
    private LocalDateTime intvDtm;
}
