package jobplatform.fo.user.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import jobplatform.fo.enterprise.domain.dto.ResumeSearchDataDTO;
import jobplatform.fo.user.domain.vo.M_JobPosting_pp;
import jobplatform.fo.user.domain.vo.MemberVO;

public interface M_MypageService {

	//마이페이지 매인화면에 필요한 전체 데이터 가져오기
	public Map<String, Object> getMyPageMainData(Long mbr_sq, int month);

	//마이페이지 메인 화면 캘린더 데이터(월 이동시)
	public List<Map<String, Object>> getToMakeCalendarData(Long mbr_sq, Long rsm_sq, int month);

	//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

	//포지션 제안 받기 페이지 데이터
	public Map<String, Object> getPosionProposalData(Long mbr_sq);

	//포지션 제안 받기 수락 여부 변경
	public int updatePstnPrpslAcceptYN(Long mbr_sq, String pstn_prpsl_accept_yn);

	//포지션 제안 받을 때, 지역, 직업 선택
	public int insertSelectAreasAndJobs(Long mbr_sq, Map<String, Object> areaAndJobLists);

	//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

	//제안 받은 포지션 공고
	public Map<String, Object> getPPJopPostingData(Long mbr_sq, int page_num);

	//@@예정@@포지션 제안 수락했을 때 -> 같은 공고로 지원한 내역 있는지 확인 + 지원 프로세스 진행 + 포지션 제안 상태 변경 
	//@@예정@@포지션 제안 거절했을 때 -> 포지션 제안 상태 변경
	public int refuseProposedPostion(Long pstn_prpsl_sq);

	//회원 정보 받아오기
	public Map<String, Object> findUserINfo(Long mbr_sq);

	//비밀번호 수정
	public int pwUpdate(Long mbr_sq, String mbr_pswrd);

	//스크랩 목록
	public Map<String, Object> findScrapData(ResumeSearchDataDTO resumeSearchDataDTO) throws SQLException, IOException;
	// 학력, 경력 공통코드 모음
	public Map<String, Object> findCommonCode();

	//스크랩 추가
	public void scrapInsert(Long mbr_sq, Long jbp_sq);

	//스크랩 삭제
	public void scrapDelete(Long mbr_sq, Long jbp_sq);





}
