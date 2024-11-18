package jobplatform.fo.enterprise.domain.dto;

import jobplatform.fo.enterprise.domain.entity.JobPostingEntity;
import jobplatform.fo.enterprise.domain.entity.JobViewEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobViewDTO {

    private Long rjvSq;
    private Long mbrSq;
    private Long jbpSq;
    private LocalDateTime viewDate;
    private String enterpriseName;
    private String mbrId;
    private String jbpTtl;  // 공고 제목
    private int page;
    private int size;

    // Constructor with 7 parameters (added)
    public JobViewDTO(Long rjvSq, Long mbrSq, Long jbpSq, LocalDateTime viewDate, String enterpriseName, String mbrId, String jbpTtl) {
        this.rjvSq = rjvSq;
        this.mbrSq = mbrSq;
        this.jbpSq = jbpSq;
        this.viewDate = viewDate;
        this.enterpriseName = enterpriseName;
        this.mbrId = mbrId;
        this.jbpTtl = jbpTtl;
    }

    // JobViewDTO -> JobViewEntity 변환 메소드
    public JobViewEntity toEntity() {
        JobViewEntity jobViewEntity = new JobViewEntity();
        jobViewEntity.setRjvSq(this.rjvSq);
        jobViewEntity.setMbrSq(this.mbrSq);
        jobViewEntity.setJbpSq(this.jbpSq);
        jobViewEntity.setViewDate(this.viewDate);
        jobViewEntity.setMbrId(this.mbrId);
        return jobViewEntity;
    }

    // JobViewEntity -> JobViewDTO 변환 메소드
    public static JobViewDTO from(JobViewEntity jobViewEntity) {
        String enterpriseName = null;
        String jobTitle = null;

        // JobPosting과 관련된 데이터를 안전하게 가져오기
        if (jobViewEntity.getJobPosting() != null) {
            JobPostingEntity jobPosting = jobViewEntity.getJobPosting();
            if (jobPosting.getEnterpriseMember() != null) {
                enterpriseName = jobPosting.getEnterpriseMember().getEntrprsName();
            }
            jobTitle = jobPosting.getJbpTtl();  // jobPosting이 null이 아니면 직무명을 가져옵니다.
        }

        return new JobViewDTO(
                jobViewEntity.getRjvSq(),
                jobViewEntity.getMbrSq(),
                jobViewEntity.getJbpSq(),
                jobViewEntity.getViewDate(),
                enterpriseName,
                jobViewEntity.getMbrId(),
                jobTitle
        );
    }
}

