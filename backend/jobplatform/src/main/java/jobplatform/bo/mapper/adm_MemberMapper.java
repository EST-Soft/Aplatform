package jobplatform.bo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import jobplatform.bo.dto.adm_MemberDTO;

@Mapper
public interface adm_MemberMapper {
    
    // 회원 관리 다 가져오기
    List<adm_MemberDTO> selectAllMember();
}
