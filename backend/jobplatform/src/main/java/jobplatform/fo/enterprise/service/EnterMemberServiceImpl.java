package jobplatform.fo.enterprise.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jobplatform.fo.enterprise.domain.dto.EnterLoginDTO;
import jobplatform.fo.enterprise.domain.dto.EnterRegisterDTO;
import jobplatform.fo.enterprise.domain.entity.EnterMemberEntity;
import jobplatform.fo.enterprise.domain.repository.EnterMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class EnterMemberServiceImpl implements EnterMemberService{


    @Autowired
    EnterMemberRepository enterMemberRepository;
    
    
    
    @Autowired
    PasswordEncoder passwordEncoder;
    

    @Autowired
    ModelMapper modelMapper;



    @Override
    public void insert(EnterRegisterDTO enterMemberDTO) {

        if(enterMemberDTO != null ){
           EnterMemberEntity enterMemberEntity = modelMapper.map(enterMemberDTO,EnterMemberEntity.class);
           
           String encodePswrd =   passwordEncoder.encode(enterMemberDTO.getEntrprsPswrd());
           
            enterMemberEntity.setEntrprsPswrd(encodePswrd);
             
            enterMemberRepository.save(enterMemberEntity);
        }
    }

    @Override
    public boolean selectId(String entrprsId) {

        //아이디 조회
       boolean yn =  enterMemberRepository.existsByEntrprsId(entrprsId);

        return yn;
    }

    @Override
    public String findByIdEml(EnterRegisterDTO enterRegisterDTO) {
        // DTO에서 이메일을 추출
        String EntrprsPicEml = enterRegisterDTO.getEntrprsPicEml();

        // 이메일을 사용하여 회원 정보 조회
        EnterMemberEntity memberEntity = enterMemberRepository.findByEntrprsPicEml(EntrprsPicEml);

        // 회원이 존재하면 아이디 반환, 그렇지 않으면 빈 문자열 반환
        if (memberEntity != null) {
            return memberEntity.getEntrprsId();
        } else {
            return ""; // 회원이 존재하지 않으면 빈 문자열 반환
        }
    }

    @Override
    public String findByPswrd(EnterRegisterDTO enterRegisterDTO) {

        String entrprsSq=enterMemberRepository.findByEntrprsPw(enterRegisterDTO);

            return entrprsSq;
    }

    @Override
    public int pswrdReset(EnterRegisterDTO enterRegisterDTO) {
        String encodePswrd =   passwordEncoder.encode(enterRegisterDTO.getEntrprsPswrd());
        enterRegisterDTO.setEntrprsPswrd(encodePswrd);
        int result= enterMemberRepository.pswrdReset(enterRegisterDTO);

        return result;
    }

    @Override
    public Long login(EnterLoginDTO enterLoginDTO) {
    	
    	String id = enterLoginDTO.getEntrprsId();

    	Long sq = null;
    	//전에 저장한 암호화된 비밀번호
    	String pswrd = 	enterMemberRepository.findByEntrprsId(id);
    	//전에 저장한 암호화된 비밀번호와 확인 (컨트롤러에서 받아온 password, 전에 저장한 암호화된 비빌번호 )
    	if(passwordEncoder.matches(enterLoginDTO.getEntrprsPswrd(), pswrd)) {
    		
    	 sq	= enterMemberRepository.findByEntrprsIdSq(id);
    	};
    	//무슨 값을 리턴해주는게 좋을까? 
    	
    	return sq;
    }
     
    
}
