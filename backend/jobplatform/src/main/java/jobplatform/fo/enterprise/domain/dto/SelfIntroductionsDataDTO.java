package jobplatform.fo.enterprise.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelfIntroductionsDataDTO {
	// 자기소개서 제목
	private String siTtl; // siTtl;
	// 자기소개서 내용
	private String siCntnt; // siCntnt;
} // SelfIntroductionsDataDTO
