package jobplatform.fo.enterprise.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResumeSelfintroductionDataVO {
	//자기소개서 순번
	private int si_sq;
	// 제목
	private String si_ttl;
	// 내용
	private String si_cntnt;
}
