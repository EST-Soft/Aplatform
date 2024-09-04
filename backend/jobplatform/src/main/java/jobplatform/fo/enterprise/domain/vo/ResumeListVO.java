package jobplatform.fo.enterprise.domain.vo;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResumeListVO {
	// 이력서 순번
	private int rsm_sq;
	// 회원 순번
	private int mbr_sq;
	// 대표여부
	private String rsm_rprsntv_yn;
	// 제목
	private String rsm_ttl;
	// 수정일
	private Timestamp updt_dtm;
	
	
}
