package jobplatform.fo.enterprise.domain.vo;

import java.sql.Date;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobViewVO {
    private Long rjv_sq; // 최근 공고 저장 된 pk
    private Long mbr_sq; // 멤버 테이블 회원 순번 pk
    private Long jbp_sq; // 구직 공고 pk
    private LocalDateTime view_date; // 날짜
    private String mbr_id; // 멤버 id
	private String enterpriseName;
    private String jbpTtl;  // 공고 제목
}
