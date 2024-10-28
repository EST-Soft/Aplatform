package jobplatform.fo.enterprise.domain.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplyDataDTO {
	// 이력서 순번
			private Long rsm_sq;
			// 공고 순번
			private Long jbp_sq;
			// 지원 일시
			private LocalDateTime apy_dtm;
			//지원 상태
			private String apy_cndtn;
			//기업 열람 일시
			private LocalDateTime entrprs_read_dtm;
			//면접 일시
			private LocalDateTime intv_dtm;

}
