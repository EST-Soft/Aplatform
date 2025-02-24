package jobplatform.fo.enterprise.domain.mapper;

import java.util.Map;

import jobplatform.fo.enterprise.domain.entity.EnterMemberEntity;
import org.apache.ibatis.annotations.Mapper;

import jobplatform.fo.enterprise.domain.dto.EnterInfoDTO;

@Mapper
public interface EnterpriseMypageMapper {
   
   Map<String, String>  getEnterpriseInfo(int entrprsSq); // 기업 정보
   
   Integer idRepetitionCheck(String entrprsId); // 기업회원 아이디 중복체크
   
   Integer updateEnterInfo(EnterInfoDTO enterInfoDTO); //기업회원 정보 수정
   
   String getEnterSq (int entrprsSq); // 기업회원 비밀번호

   int passwordUpdate(Map<String, Object> data); // 기업회원 비밀번호 수정

   int enterSignOut(int entrprsSq); // 기업회원 탈퇴
   EnterMemberEntity findByEntrprsSq(int entrprsSq);

}
