package jobplatform.fo.enterprise.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "p5_tbl_job_view_r")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobViewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rjv_sq", nullable = false)
    private Long rjvSq;  // 공고 조회 순번 (Primary Key)

    @Column(name = "mbr_sq", nullable = false)
    private Long mbrSq;  // 회원 순번

    @Column(name = "jbp_sq", nullable = false)
    private Long jbpSq;  // 공고 순번

    @Column(name = "view_date", nullable = false)
    private LocalDateTime viewDate;  // 조회 일자

    @Column(name = "mbr_id", nullable = true)
    private String mbrId;  // 회원 ID (Optional)

    // JobPosting과의 관계 정의 (다대일 관계)
    @ManyToOne(fetch = FetchType.EAGER)  // EAGER 로딩으로 변경
    @JoinColumn(name = "jbp_sq", referencedColumnName = "jbp_sq", insertable = false, updatable = false)
    private JobPostingEntity jobPosting;
    

    // 추가: mbrSq, jbpSq, viewDate, mbrId를 받는 생성자 추가
    public JobViewEntity(Long mbrSq, Long jbpSq, LocalDateTime viewDate, String mbrId) {
        this.mbrSq = mbrSq;
        this.jbpSq = jbpSq;
        this.viewDate = viewDate;
        this.mbrId = mbrId;
    }

    // 기업명 가져오기: JobPostingEntity와 연결된 EnterpriseMemberEntity에서 기업명 가져오기
// JobViewEntity 내에서 기업명을 가져오는 메소드
public String getEnterpriseName() {
    if (this.jobPosting != null && this.jobPosting.getEnterpriseMember() != null) {
        return this.jobPosting.getEnterpriseMember().getEntrprsName();  // JobPostingEntity -> EnterpriseMemberEntity -> 기업명
    }
    return null;  // 기업명이 없을 경우 null 반환
}


    
}
