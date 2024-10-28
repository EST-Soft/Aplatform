package jobplatform.fo.enterprise.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelfintoductionsDataDTO { 
	//자기소개서 제목
	private String title;     //si_tl;
	//자기소개서 내용
	private String content;   //si_cntnt;
}
