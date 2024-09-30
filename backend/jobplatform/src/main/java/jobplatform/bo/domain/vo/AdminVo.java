package jobplatform.bo.domain.vo;

import java.time.LocalDateTime;

import lombok.Data;

//p3_tbl_admin_m 과 연결되는 운영자 계정 관련 VO (240930 기준 dummydata 로 ID : adm01 / PW : adm01 1개 있음)
@Data
public class AdminVo {

	private Long admSq; // 관리자 순번 / adm_sq
	private Long faq_sq; // 자주묻는 질문 게시판 순번 / faq_sq
	private String gndrTypCode; // 관리자 성별 / gndr_typ_code
	private String admId; // 관리자 아이디 / adm_id
	private String admName;	 // 관리자 이름 / adm_name
	private String admPswrd; // 관리자 비밀번호 / adm01 (240930 , 암호화 로직 X)
	private String admMp; // 관리자 휴대폰 / adm_mp
	private String admBd; // 관리자 생년월일 / adm_bd
	private String admEmlAdrs; // 관리자 이메일 / adm_eml_adrs
	private LocalDateTime insrtDtm; // 등록 일시 / insrt_dtm
	private Long insrtAdmSq; // 등록 관리자 순번 / insrt_adm_sq
	private LocalDateTime updtDtm; // 수정 일시 / updt_dtm
	private Long updtDtmSq; // 수정 관리자 순번 /' updt_adm_sq
	private String dltYn; // 삭제 여부 / dlt_yn
	private String useYn; // 사용 여부 / use_yn
	private int role; // 권한 / role (운영자 권한 1 2 3 당 각각 권한 다름 )
	
	
}
