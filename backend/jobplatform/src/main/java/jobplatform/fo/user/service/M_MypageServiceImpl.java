package jobplatform.fo.user.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;
import jobplatform.fo.enterprise.common.Pagination;
import jobplatform.fo.enterprise.domain.dto.JobPostingDTO;
import jobplatform.fo.enterprise.domain.dto.JobViewDTO;
import jobplatform.fo.enterprise.domain.dto.ResumeSearchDataDTO;
import jobplatform.fo.enterprise.domain.entity.JobPostingEntity;
import jobplatform.fo.enterprise.domain.entity.JobViewEntity;
import jobplatform.fo.enterprise.domain.mapper.CommonCodeMapper;

import jobplatform.fo.enterprise.domain.vo.CommonCodeVO;
import jobplatform.fo.enterprise.domain.vo.JobViewVO;
import jobplatform.fo.enterprise.domain.vo.ResumeListVO;
import jobplatform.fo.enterprise.domain.vo.ScrapVO;
import jobplatform.fo.user.domain.mapper.M_MypageMapper;
import jobplatform.fo.user.domain.vo.M_JobPosting_pp;
import jobplatform.fo.user.domain.vo.MemberVO;


@Service
public class M_MypageServiceImpl implements M_MypageService{

	@Autowired
    private M_MypageMapper mypageMapper;
    @Autowired
    private CommonCodeMapper commonCodeMapper;
    
	
    //마이페이지 매인화면에 필요한 전체 데이터 가져오기
	@Override
	public Map<String, Object> getMyPageMainData(Long mbr_sq, int month) {
		
		Map<String, Object> response = new HashMap<>();

        // 회원 정보
        Map<String, Object> mbrInfo = mypageMapper.getMbrInfo(mbr_sq);
        response.put("mbrInfo", mbrInfo);

        // 대표 이력서 정보
        Map<String, Object> rsmInfo = mypageMapper.getRprsntvRsmInfo(mbr_sq);
        response.put("rsmInfo", rsmInfo);

        if(rsmInfo != null && rsmInfo.containsKey("rsm_sq")){
            Long rsm_sq = (Long)rsmInfo.get("rsm_sq");

            // 각 상태별 지원 개수
            Map<String, Object> EachCndtnApplyCount = mypageMapper.getEachCndtnApplyCount(rsm_sq);
            response.put("EachCndtnApplyCount", EachCndtnApplyCount);
    
            // 등록한 이력서, 스크랩한 공고, 포지션 제안 받은 개수
            Map<String, Object> myState = mypageMapper.getMyState(mbr_sq, rsm_sq);
            response.put("myState", myState);
    
            // 월별 캘린더 데이터
            List<Map<String, Object>> calendarData = mypageMapper.getToMakeCalendarData(mbr_sq,rsm_sq, month);
            response.put("calendarData", calendarData);
            
        }else{

            response.put("rsmInfo", new HashMap<>());
            response.put("EachCndtnApplyCount", new HashMap<>());
            response.put("myState", new HashMap<>());
            response.put("calendarData", new ArrayList<>());
        }
       
		return response;
	}

    //마이페이지 메인 화면 캘린더 데이터(월 이동시)
    @Override
    public List<Map<String, Object>> getToMakeCalendarData(Long mbr_sq, Long rsm_sq, int month) {
        return mypageMapper.getToMakeCalendarData(mbr_sq,rsm_sq, month);
    }

    //마이페이지 정보수정의 회원 정보가져오기
    @Override
    public Map<String, Object> findUserINfo(Long mbr_sq) {
        // 회원 정보
        return mypageMapper.getMbrInfo(mbr_sq);
    } 


//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

    //포지션 제안 받기 페이지 데이터
    @Override
    public Map<String, Object> getPosionProposalData(Long mbr_sq) {

        Map<String, Object> response = new HashMap<>();

        response.put("ppAcceptYN", mypageMapper.getPstnPrpslAcceptYN(mbr_sq));
        response.put("rsmInfo", mypageMapper.getRprsntvRsmInfo(mbr_sq));
        response.put("selectedAreas", mypageMapper.getSelectedAreas(mbr_sq));
        response.put("selectedJobs", mypageMapper.getSelectedJobs(mbr_sq));

        response.put("areaList", mypageMapper.getAreaCodes());
        response.put("jobList", mypageMapper.getJobCodes());


        return response;
    }

    //포지션 제안 받기 수락 여부 변경
    @Override
    public int updatePstnPrpslAcceptYN(Long mbr_sq, String pstn_prpsl_accept_yn) {
        return mypageMapper.updatePstnPrpslAcceptYN(mbr_sq, pstn_prpsl_accept_yn);
    }

    //포지션 제안 받을 때, 지역, 직업 선택
    @Override
    @SuppressWarnings("unchecked")
    public int insertSelectAreasAndJobs(Long mbr_sq, Map<String, Object> areaAndJobLists) {
        int result = 0;
        
        ObjectMapper om = new ObjectMapper();
        
        List<Integer> areaList = new ArrayList<>();
        List<Integer> jobList = new ArrayList<>();
        
        
        Map<String, Object> checkedAreaMap = (Map<String, Object>) areaAndJobLists.get("checkedArea");
        Map<String, Object> checkedJobMap = (Map<String, Object>) areaAndJobLists.get("checkedJob");
        
        List<Map<String, Object>> areas = om.convertValue(checkedAreaMap.get("_rawValue"), new TypeReference<List<Map<String, Object>>>() {});
        List<Map<String, Object>> jobs = om.convertValue(checkedJobMap.get("_rawValue"), new TypeReference<List<Map<String, Object>>>() {});
        
        for(Map<String, Object> area : areas) {
        	areaList.add((int)area.get("area_sq"));
        }
        
        for(Map<String, Object> job : jobs) {
        	jobList.add((int)job.get("job_sq"));
        }
        
        mypageMapper.resetSelectedAreas(mbr_sq);
        mypageMapper.resetSelectedJobs(mbr_sq);
        if(areaList.size() != 0) result += mypageMapper.insertSelectAreas(mbr_sq, areaList);
        if(jobList.size() != 0) result += mypageMapper.insertSelectJobs(mbr_sq, jobList);

        return result;
    }


    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

    //제안 받은 포지션 공고 리스트 출력
    //검색에 사용하기 위해 vo에 추가 변수(forSearch)
    @Override
    public Map<String, Object> getPPJopPostingData(Long mbr_sq, int page_num){
        Long rsm_sq = mypageMapper.getRRsm_sq(mbr_sq);
        
        int limit = 5; //한페이지에 보여줄 글 수
        int offset = limit * (page_num - 1);
        
        List<M_JobPosting_pp> result = mypageMapper.getPPJopPostingData(rsm_sq, limit, offset);
        
        for(M_JobPosting_pp temp : result){
            Long jbp_sq = temp.getJbp_sq();
            List<Long> areas = mypageMapper.getAreasOfJobPost(jbp_sq);
            List<Long> jobs = mypageMapper.getJobsOfJobPost(jbp_sq);
            List<Long> skills = mypageMapper.getSkillsOfJobPost(jbp_sq);
            if(areas.size() != 0) temp.setAreas(mypageMapper.areaCodeToName(areas));
            if(jobs.size() != 0) temp.setJobs(mypageMapper.jobCodeToName(jobs));
            if(skills.size() != 0) temp.setSkills(mypageMapper.skillCodeToName(skills));
//            temp.setForSeacrch(temp.toString());
        }
        
        Map<String, Object> response = new HashMap<>();
        
        response.put("jobPosts", result);
        response.put("totalPostsCount", Math.ceil(mypageMapper.getPPPostsCount(rsm_sq)/(double)limit));

        return response;
    };
    
    public int refuseProposedPostion(Long pstn_prpsl_sq) {
    	return mypageMapper.refuseProposedPostion(pstn_prpsl_sq);
    }

    @Override
    public int pwUpdate(Long mbr_sq, String newPassword) {
        // 비밀번호 업데이트 쿼리 호출
        return mypageMapper.pwUpdate(mbr_sq, newPassword);
    }

    // 이력서 리스트 데이터 얻기
	public Map<String, Object> findScrapData(ResumeSearchDataDTO resumeSearchDataDTO) throws SQLException, IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		// 총 데이터 갯수
		int totalCount = mypageMapper.loadScrapListCount(resumeSearchDataDTO);
		// 페이지네이션 정보 생성 (기본 1페이지 = 데이터10 / 페이지그룹= 페이지5)
		Pagination pagination = new Pagination(totalCount, resumeSearchDataDTO.getPageNo());
		// 페이지네이션 정보 map 추가
		map.put("paginationData", pagination);
		// 검색/정렬 정보 map 추가
		map.put("searchData", resumeSearchDataDTO);
		// 스크랩 리스트 정보 얻기
		List<ScrapVO> scrapVO = mypageMapper.selectScrapData(map);
		// 스크랩 리스트 정보 map 추가
		map.put("scrapDatas", scrapVO);

        // jbp_sq 리스트를 Long으로 변환하여 생성
        List<Long> jbpSqList = new ArrayList<>();
        for (ScrapVO scrap : scrapVO) {
            jbpSqList.add(scrap.getJbp_sq());
        }
        // 공고 정보 가져오기
        List<M_JobPosting_pp> JobPostingDTO = mypageMapper.selectJobPosting(jbpSqList);
        //공고 정보 map에 추가
        map.put("jobPostingData", JobPostingDTO);

		return map;
	}

    public Map<String, Object> findCommonCode(){
        Map<String, Object> map = new HashMap<String, Object>();
        List<CommonCodeVO> commonCodeVO = commonCodeMapper.findCommonCode();
        map.put("commonCodeList", commonCodeVO);
        return map;
    }

    //스크랩 등록
    @Override
    public void scrapInsert(Long mbr_sq, Long jbp_sq) {
        mypageMapper.scrapInsert(mbr_sq, jbp_sq);
    }

    //스크랩 취소
    @Override
    public void scrapDelete(Long mbr_sq, Long jbp_sq) {
        mypageMapper.scrapDelete(mbr_sq, jbp_sq);
    }
//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@



@Override
public void saveJobView(Long mbrSq, Long jbpSq, HttpSession session) {
    // 사용자 순번에 해당하는 최근 본 공고 목록을 세션에서 가져옵니다
    List<Long> recentJobViews = (List<Long>) session.getAttribute("recentJobViews_" + mbrSq);

    if (recentJobViews == null) {
        recentJobViews = new ArrayList<>();
    }

    // 이미 세션에 해당 공고가 없으면 추가
    if (!recentJobViews.contains(jbpSq)) {
        recentJobViews.add(0, jbpSq);  // 최신 공고를 맨 앞에 추가
    }

    // 세션에 최근 본 공고 목록 저장
    session.setAttribute("recentJobViews_" + mbrSq, recentJobViews);
}

@Override
public List<Long> getRecentJobViews(Long mbrSq, HttpSession session) {
    // 사용자 순번에 해당하는 최근 본 공고 목록을 세션에서 가져옴
    List<Long> recentViews = (List<Long>) session.getAttribute("recentJobViews_" + mbrSq);
    if (recentViews == null) {
        recentViews = new ArrayList<>();
    }
    return recentViews;
}


    




    

}
