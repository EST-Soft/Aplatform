package jobplatform.fo.enterprise.domain.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplyDetailDataVO {
	// 이력서 순번
	private int rsm_sq;
	// 회원 순번
	private int mbr_sq;
	// 이력서 이미지 주소
	private String rsm_img_file_url;
	// 이력서 최종학력코드 한글명
	private String rsm_fnl_edctn_code_name;
	// 이력서 학점
	private int rsm_grd;
	// 이력서 희망연봉
	private int rsm_es;
	// 제목
	private String rsm_ttl;
	// 지원자 이름
	private String rsm_name;
	// 성별 코드를 통한 한글명
	private String rsm_gndr_code_name;
	// 생년월일
	private String rsm_bd;
	// 나이
	private int age;
	// 핸드폰 번호
	private String rsm_mp;
	// 지원자 주소
	private String rsm_adrs;
	// 지원자 이메일
	private String rsm_eml;
	
	//지원 상태 코드 번호
	private String apy_cndtn;
	//지원 상태 코드 한글화
	private String apy_cndtn_name;
	
	// 이력서 - 첨부파일
	private List<ResumeAttachmentDataVO> resumeAttachmentList;
	// 이력서 - 자기소개서
	private List<ResumeSelfintroductionDataVO> resumeSelfintroductionList;
	// 이력서 - 첨부파일
	private List<ResumeEducationDataVO> resumeEducationList;
	// 이력서 - 첨부파일
	private List<ResumeCertificateDataVO> resumeCertificateList;
	// 이력서 - 첨부파일
	private List<ResumeCareerDataVO> ResumeCareerList;

			
}
