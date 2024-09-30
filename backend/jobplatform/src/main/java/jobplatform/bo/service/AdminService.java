package jobplatform.bo.service;

import jobplatform.bo.domain.vo.AdminVo;

public interface AdminService {

	/** Admin 계정으로 로그인 하는 service 
	 * @param admId
	 * @param admPswrd
	 * @return
	 */
	AdminVo login(String admId, String admPswrd);
	
}
