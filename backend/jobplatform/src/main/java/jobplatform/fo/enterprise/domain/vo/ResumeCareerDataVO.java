package jobplatform.fo.enterprise.domain.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResumeCareerDataVO {
	// 경력 순번
	private int crr_sq;
	// 기업 이름
	private String entrprs_name;
	// 기업 직급
	private String entrprs_pstn;
	// 입사일
	private Date entrprs_jac_date;
	// 퇴사일
	private Date entrprs_rsgntn_date;

	
}
