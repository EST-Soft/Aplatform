package jobplatform.bo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jobplatform.bo.domain.vo.AdminFAQDto;
import jobplatform.bo.mapper.FaqMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FaqServiceImpl implements FaqService{

	private final FaqMapper faqMapper;
	
	@Override
	public List<AdminFAQDto> getFaqList() {
		
		
		// faq테이블에서 가져올 비즈니스 로직 작성, 240930 기준 미완성
		
		
		return faqMapper.getAllFaqs();
	}

}
