package jobplatform.fo.enterprise.service;

import java.util.Map;

import jobplatform.fo.enterprise.domain.dto.EnterInfoDTO;

public interface EnterMypageService {

	Map<String, String> getEnterpriseInfo(int entrprsSq); // 기업 정보
	
	Integer idRepetitionCheck(String entrprsId); // 기업회원 아이디 중복체크
	
	Integer updateEnterInfo(EnterInfoDTO enterInfoDTO); //기업회원 정보 수정
	
	public Boolean passwordMatches(String entrprsPsWrd, int entrprsSp); // 비밀번호 매칭

	int passwordUpdate(String entrprsPswrdChng, int entrprsSp); // 비밀번호 변경

	int enterSignOut(int entrprsSq); // 기업회원 탈퇴
}