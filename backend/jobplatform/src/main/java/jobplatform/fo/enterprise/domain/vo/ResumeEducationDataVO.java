package jobplatform.fo.enterprise.domain.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResumeEducationDataVO {
	//학력 순번
	private int edctn_sq;
	// 학교명
	private String schl_name;
	// 학교 상태 코드
	private String schl_cndtn_code;
	//학교 상태 코드에 따른 한글명
	private String code_name;
	// 학교 학과명
	private String schl_dprtmnt;
	// 학교 입학일
	private Date schl_admsn_date;
	// 학교 졸업일
	private Date schl_grdtn_date;
}
