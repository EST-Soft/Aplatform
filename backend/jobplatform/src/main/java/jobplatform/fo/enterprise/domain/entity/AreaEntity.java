package jobplatform.fo.enterprise.domain.entity;

import jakarta.persistence.*;
import jobplatform.fo.enterprise.domain.entity.JobPostingEntity.YesNo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "p2_tbl_area_code_s")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AreaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "area_sq", nullable = false)
    private int areaSq;

    @Column(name = "area_name", nullable = false, length = 50)
    private String areaName;

    @Column(name = "prnt_area_sq")
    private Integer prntAreaSq;

    @Column(name = "insrt_mbr_sq", nullable = false)
    private int insrtMbrSq = 0;

    @Column(name = "insrt_dtm", nullable = false)
    private LocalDateTime insrtDtm = LocalDateTime.now();

    @Column(name = "updt_mbr_sq")
    private Integer updtMbrSq;

    @Column(name = "updt_dtm" , nullable = true)
    private LocalDateTime updtDtm;

    public enum YesNo {
        Y, N
    }

    @Column(name = "dlt_yn", nullable = false)
    @Enumerated(EnumType.STRING)
    private YesNo dltYn = YesNo.N;

    @Column(name = "use_yn", nullable = false)
    @Enumerated(EnumType.STRING)
    private YesNo useYn = YesNo.Y;
}
