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
	private int rsmSq;
	// 회원 순번
	private int mbrSq;
	// 대표여부
	private String rsmRprsntvYn;
	// 제목
	private String rsmTtl;
	// 수정일
	private Timestamp updtDtm;
	// 등록일
	private Timestamp insrtDtm;
}