package jobplatform.fo.enterprise.domain.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResumeCertificateDataVO {
	//자격증 순번
	private int crtfct_sq;
	//자격증 이름
	private String crtfct_name;
	//자격증 발행기관
	private String crtfct_isr;
	//자격증 발행 일자
	private Date crtfct_ad;
	
	
}
