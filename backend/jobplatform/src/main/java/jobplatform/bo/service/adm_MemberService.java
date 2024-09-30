package jobplatform.bo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jobplatform.bo.dto.adm_MemberDTO;
import jobplatform.bo.mapper.adm_MemberMapper;

@Service
public class adm_MemberService {
    
    @Autowired
    private adm_MemberMapper membermapper;

    public List<adm_MemberDTO> getAllMemberList(){
        return membermapper.selectAllMember();
    }
}
