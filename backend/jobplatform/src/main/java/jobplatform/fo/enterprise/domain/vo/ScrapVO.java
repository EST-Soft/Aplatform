package jobplatform.fo.enterprise.domain.vo;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScrapVO {
	// 스크랩 순번
	private Long scrap_sq;
	// 회원 순번
	private Long mbr_sq;
	// 구인공고 순번
	private Long jbp_sq;
	// 스크랩 일시
	private Timestamp scrap_dtm;
	
}
