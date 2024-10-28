package jobplatform.fo.enterprise.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jobplatform.fo.enterprise.domain.dto.EnterInfoDTO;
import jobplatform.fo.enterprise.domain.mapper.EnterpriseMypageMapper;

@Service
public class EnterMypageServiceImpl implements EnterMypageService{
   
   @Autowired
   EnterpriseMypageMapper enterpriseMypageMapper;
   
   @Autowired
   PasswordEncoder passwordEncoder;
   
   @Override
   public Map<String, String>  getEnterpriseInfo(int entrprsSq) {
      // TODO Auto-generated method stub
      return enterpriseMypageMapper.getEnterpriseInfo(entrprsSq);
   }

   // 기업회원 아이디 중복체크
   @Override
   public Integer idRepetitionCheck(String entrprsId) {
      // TODO Auto-generated method stub
      return enterpriseMypageMapper.idRepetitionCheck(entrprsId);
   }

   // 기업회원 정보 수정
   @Override
   public Integer updateEnterInfo(EnterInfoDTO enterInfoDTO) {
      // TODO Auto-generated method stub
      return enterpriseMypageMapper.updateEnterInfo(enterInfoDTO);
   }
   
   // 비밀번호 조회
    public Boolean passwordMatches(String entrprsPsWrd, int entrprsSp) {
       String pswrd = enterpriseMypageMapper.getEnterSq(entrprsSp); // DB에서 가져온 비밀번호
       System.out.println(pswrd);
       System.out.println(entrprsPsWrd);
       
       boolean result = passwordEncoder.matches(entrprsPsWrd, pswrd);
       
      return result;
       
    }

    // 비밀번호 수정
   @Override
   public int passwordUpdate(String entrprsPswrdChng, int entrprsSp) {
      // TODO Auto-generated method stub
      Map<String, Object> data = new HashMap<>();
      
      String encodePswrd = passwordEncoder.encode(entrprsPswrdChng);
      
      data.put("entrprsSp", entrprsSp);
      data.put("encodePswrd", encodePswrd);
      
      int result = enterpriseMypageMapper.passwordUpdate(data);
      
      return result;
   }

   // 회워탈퇴
   @Override
   public int enterSignOut(int entrprsSq) {
      // TODO Auto-generated method stub
      return enterpriseMypageMapper.enterSignOut(entrprsSq);
   }

}
