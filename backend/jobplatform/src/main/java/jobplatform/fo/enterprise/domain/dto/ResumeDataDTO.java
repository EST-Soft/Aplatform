package jobplatform.fo.enterprise.domain.dto;

import java.sql.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResumeDataDTO {
	// 이력서 제목
	private String rsm_ttl;
	// 이력서 이름
	private String rsm_name;
	// 이력서 주소
	private String rsm_adrs;
	// 이력서 전화번호
	private String rsm_mp;
	// 이력서 이메일
	private String rsm_eml;
	// 이력서 생년월일
	private String rsm_bd;
	// 이력서 희망연봉
	private String rsm_es;
	// 이력서 학점
	private String rsm_grd;
	// 이력서 학점 코드
	private int rsm_fnl_edctn_cd;
	
	// 이력서 성별 코드
	private int rsm_gndr_code;
	
	// 자기소개서 리스트
	private List<SelfintoductionsDataDTO> selfintoductionData;
	
	
	
	

	
}
