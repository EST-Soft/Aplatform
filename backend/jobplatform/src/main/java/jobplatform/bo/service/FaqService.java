package jobplatform.bo.service;

import java.util.List;

import jobplatform.bo.domain.vo.AdminFAQDto;

public interface FaqService {

	/** FAQ 게시판 테이블 내 데이터를 조회하는 메서드
	 * @return
	 */
	List<AdminFAQDto> getFaqList();

}
