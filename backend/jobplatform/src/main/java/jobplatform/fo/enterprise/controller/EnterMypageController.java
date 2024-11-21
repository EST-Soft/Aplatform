package jobplatform.fo.enterprise.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jobplatform.fo.enterprise.domain.dto.EnterInfoDTO;
import jobplatform.fo.enterprise.service.EnterMemberService;
import jobplatform.fo.enterprise.service.EnterMypageService;

@RestController
@RequestMapping("/enter")
public class EnterMypageController {

	@Autowired
	EnterMypageService enterMypageServie;


	// 회원정보 수정 -> 회원정보 가져오기
	@GetMapping("/getEnterInfo")
	public ResponseEntity<?> getEnterInfo(@RequestParam Map<String, String> param)throws Exception{
		//		System.out.println(param);

		int entrprsSq = Integer.parseInt(param.get("entrprsSq")); // 기업회원 순번

		Map<String, String> enterInfo = enterMypageServie.getEnterpriseInfo(entrprsSq); // 기업회원 개인정보
		System.out.println(enterInfo);
		
		

		return ResponseEntity.ok(enterInfo);
	}

	// 기업회원 아이디 중복체크
	@PostMapping("/idRepetitionCheck")
	public ResponseEntity<?> idRepetitionCheck(@RequestBody Map<String, String> param)throws Exception{

		String entrprsId = param.get("entrprsId");

		int idCheck = enterMypageServie.idRepetitionCheck(entrprsId);
		//		System.out.println(entrprsId + ":" +idCheck);

		if(idCheck > 0) {
			return ResponseEntity.ok("사용불가능");
		}

		return ResponseEntity.ok("사용가능");
	}

	// 기업정보 수정
	@PostMapping("/updateEnterInfo")
	public ResponseEntity<?> updateEnterInfo(@RequestBody EnterInfoDTO enterInfoDTO )throws Exception{

		try {

			int result = enterMypageServie.updateEnterInfo(enterInfoDTO);
			System.out.println(result);

			if(result > 0) {
				return ResponseEntity.ok("수정완료");
			}else {
				return ResponseEntity.ok("수정실패");
			}

		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("수정중 오류 발생");
		}
	}

	// 기업회원 비밀번호 수정
	@PostMapping("/enterChangPassword")
	public ResponseEntity<?> enterChangPassword(@RequestBody Map<String, String> param )throws Exception{

		System.out.println(param);
		
		int entrprsSp = Integer.parseInt(param.get("entrprsSp"));
		String entrprsPswrd = param.get("entrprsPswrd");
		String entrprsPswrdChng = param.get("entrprsPswrdChng");
		String entrprsPswrdChngCheck = param.get("entrprsPswrdChngCheck");

		Boolean result = enterMypageServie.passwordMatches(entrprsPswrd, entrprsSp);

		if(result) {
			int PswrdUpdate = enterMypageServie.passwordUpdate(entrprsPswrdChng, entrprsSp);
			if(PswrdUpdate > 0) {
				return ResponseEntity.ok("수정완료");
			}else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("수정중 오류 발생");
			}

		}else {
			return ResponseEntity.ok("비밀번호 불일치");
		}
	}

	// 기업회원 탈퇴
	@PostMapping("/enterSignOut")
	public ResponseEntity<?> enterSignOut(@RequestBody Map<String, String> param )throws Exception{

		System.out.println("탈퇴자 정보 : " + param); // 탈퇴자 정보

		int entrprsSp = Integer.parseInt(param.get("entrprsSq"));
		String entrprsPswrd = param.get("entrprsPswrd");
		
		Boolean result = enterMypageServie.passwordMatches(entrprsPswrd, entrprsSp);
		
		if(result) { //비밀번호 일치할 경우
			int result2 = enterMypageServie.enterSignOut(entrprsSp);

			if(result2 > 0) {
				return ResponseEntity.ok("탈퇴완료");
			}else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("수정중 오류 발생");
			}
		}else {
			return ResponseEntity.ok("비밀번호 불일치");
		}

		

	}



}
