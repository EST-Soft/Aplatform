package jobplatform.fo.enterprise.domain.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplyConditionDataDTO {
	//지원 순번
	private int apy_sq;
	//지원 상태
	private String apy_cndtn;
	//면접 일시
	private Timestamp intv_dtm;
}
