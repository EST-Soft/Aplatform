package jobplatform.bo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jobplatform.bo.domain.vo.AdminVo;
import jobplatform.bo.mapper.AdminMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminMapper adminMapper;
    
    
    @Override
    public AdminVo login(String admId, String admPswrd) {
    	
    	// 로그인 시, 분기문 내 매개변수의 항목에 하나라도 충족이 되지 않을 시, null 을 리턴하여 로그인 막음            
        if (admId == null || admId.isEmpty() || admPswrd == null || admPswrd.isEmpty()) {

            return null;
        }
        
        AdminVo adminVo = adminMapper.findByAdminId(admId);
        
        // adminVo에 담긴 값이 null 이라면 로그인을 막는 분기문
        if (adminVo == null) {
        	
            return null;
        }
               
        // DB에서 조회된 비밀번호와 입력한 비밀번호 비교 (평문 비교)
        if(!admPswrd.equals(adminVo.getAdmPswrd() )) {
        	
        	return null;
        }else {
        	// admPswrd 가 DB에서 가지고 온 admPswrd와 일치한다면 adminVo를 컨트롤러로 리턴
        	return adminVo;
        }
        
	    
        
    }
}
