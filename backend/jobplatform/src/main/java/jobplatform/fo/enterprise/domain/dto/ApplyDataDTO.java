package jobplatform.fo.enterprise.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplyDataDTO {
	// 이력서 순번
			private int rsm_sq;
			// 공고 순번
			private int jbp_sq;
			// 지원 일시
			private int apy_dtm;
			//지원 상태
			private String apy_cndtn;
			//기업 열람 일시
			private int entrprs_read_dtm;
			//면접 일시
			private int intv_dtm;

}
