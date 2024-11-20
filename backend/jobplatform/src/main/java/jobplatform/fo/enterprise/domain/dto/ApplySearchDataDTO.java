package jobplatform.fo.enterprise.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplySearchDataDTO {
	// 공고번호
	private long jbp_sq;
	// 구분
	private String division;
	//상태
	private String condition;
	//정렬
	private String sort;
	// 현재 페이지 번호
	private int pageNo;
}
