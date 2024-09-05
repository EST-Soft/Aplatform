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
	// 이력서 순번
	//private Long rsmSq;
	// 회원_순번
	//private Long mbrSq;
	// 이력서_이미지_원본_파일명
	private String rsmImgOrgnlFn;
	// 이력서_이미지_파일_url
	private String rsmImgFileUrl;
	// 이력서_최종_학력_코드
	private String rsmFnlEdctnCode;
	// 이력서_학점
	private int rsmGrd;
	// 이력서_희망연봉
	private int rsmEs;
	// 이력서_제목
	private String rsmTtl;
	// 이력서_이름
	private String rsmName;
	// 이력서_성별_코드
	private String rsmGndrCode;
	// 이력서 생년월일
	private String rsmBd;
	// 이력서_휴대폰
	private String rsmMp;
	// 이력서_주소
	private String rsmAdrs;
	// 이력서_이메일
	private String rsmEml;	
	// 자기소개서 리스트
	//private List<SelfintoductionsDataDTO> selfintoductionData;
	
	
	
	

	
} // ResumeDataDTO