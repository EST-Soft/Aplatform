package jobplatform.bo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import jobplatform.bo.domain.vo.AdminVo;
import jobplatform.bo.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



@Slf4j
@RestController
@RequestMapping("/admin") // localhost:3000 하위의  /admin 으로 오는 경로를 모두 받아 처리하는 용도의 컨트롤러 
@RequiredArgsConstructor

public class AdminController {

	private final AdminService service;
	
	
	// 데이터베이스 상 admin 의 계정으로 들어오는 요청을 처리하는 controller 
	@PostMapping("/login")
	public ResponseEntity<?> adminLogin(@RequestBody AdminVo adminVo, HttpSession session) {
	  	    
	    AdminVo inputLogin = service.login(adminVo.getAdmId(), adminVo.getAdmPswrd());
	       
	    // login 으로 오는 요청에 data 가 null일 경우 
	    if (inputLogin == null) {

	    	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");       
	    }
	    
	    session.setAttribute("loginAdmin", adminVo);
	    
	    log.warn("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO ={} : " + inputLogin);
	    
	    return ResponseEntity.ok(inputLogin);
	    
	}
	
	
	
}
