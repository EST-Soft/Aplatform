package jobplatform.fo.enterprise.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResumeSearchDataDTO {
	// 이력서 번호
	private int mbr_sq;
	//정렬
	private String sort;
	// 현재 페이지 번호
	private int pageNo;
}
