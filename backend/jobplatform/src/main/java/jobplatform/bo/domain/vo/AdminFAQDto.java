package jobplatform.bo.domain.vo;

import java.time.LocalDateTime;

import lombok.Data;

// FAQ 게시판 전용 DTO ( 240930 기준, dummy 계정 1개 데이터베이스에 있음. ID : adm01 / Pw :adm01 )
@Data
public class AdminFAQDto {

	private Long faqSq; // 자주 묻는 질문 순번 / faq_sq
	private Long atchmntSq; // 첨부파일 순번 / atchmnt_sq
	private String faqName; // 자주 묻는 질문 게시판 제목 / faq_name
	private String faqCntnt; // 자주 묻는 질문 게시판 내용 / faq_cntnt
	private Long insrtAdmSq; // 등록 관리자 순번 / insrt_adm_sq
	private LocalDateTime insrtDtm; // 등록 일시 / insrt_dtm
	private Long updtAdmSq; // 수정 일시 / updt_dtm
	private String dltYn; // 삭제 여부 / dlt_yn
	private String useYn; // 사용 여부 / dlt_yn
}
