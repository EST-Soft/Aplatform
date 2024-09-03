package jobplatform.fo.enterprise.domain.vo;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplyListVO {
		// 지원자 순번
		private int apy_sq;
		// 지원 일시
		private Timestamp apy_dtm;
		//지원 상태 코드 번호
		private String apy_cndtn;
		//지원 상태 코드 한글화
		private String apy_cndtn_name;
		// 이력서 순번 (상세열람 용)
		private int rsm_sq;
		// 이력서 이미지 주소
		private String rsm_img_file_url;
		// 최종학력 코드 번호
		private int rsm_fnl_edctn_cd;
		// 이력서 최종학력 코드 한글화
		private String rsm_fnl_edctn_name;
		// 이력서 학점
		private int rsm_grd;
		// 이력서 희망연봉
		private int rsm_es;
		// 지원자 이름
		private String rsm_name;
		// 구분 (가상컬럼)  pstn_prpsl_sq 컬럼이 null = apply(지원), null != proposal(제안)
		private String rnqns_name;
		
		


}
