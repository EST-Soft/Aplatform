package jobplatform.fo.enterprise.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobRecommendationDTO {
    private Long jbpSq;           // 채용 공고 ID
    private String jbpTtl;         // 채용 공고 제목
    private int matchSkills;      // 일치하는 기술 수

} // CareerDto
