package jobplatform.fo.enterprise.service;


import java.util.List;

import jobplatform.fo.enterprise.domain.dto.EnterMemberListDTO;

public interface EnterpriseMemberService {

	List<EnterMemberListDTO> getEnterpriseMemberList(Long entrprsSq); 

}