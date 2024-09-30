package jobplatform.bo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jobplatform.bo.domain.vo.AdminFAQDto;
import jobplatform.bo.service.FaqService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("admin/faq") // 임시로 localhost:3000/admin/faq 로 오는 요청을 처리하는 컨트롤러로 만듦
@RequiredArgsConstructor
@Slf4j
public class FaqController {

	private final FaqService faqService;
	// admin/faq 경로로 오는 요청( 240930 기준 faq 게시판의 database 조회하는 )에 대한 조회 메서드
	@GetMapping("/faq")
	public ResponseEntity<List<AdminFAQDto>> getFaqList(){
		
		List<AdminFAQDto> faqList = faqService.getFaqList();
		
		log.info(faqList.toString());
		return ResponseEntity.ok(faqList);

	}
	

}
