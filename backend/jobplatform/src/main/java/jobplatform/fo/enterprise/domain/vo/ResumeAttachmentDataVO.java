package jobplatform.fo.enterprise.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResumeAttachmentDataVO {
	//첨부파일 순번
	private int atchmnt_sq;
	// 원본파일명
	private String atchmnt_orgnl_fn;
	// 파일 주소
	private String atchmnt_url;
}
