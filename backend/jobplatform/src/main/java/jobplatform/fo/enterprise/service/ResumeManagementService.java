package jobplatform.fo.enterprise.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jobplatform.fo.enterprise.common.Pagination;
import jobplatform.fo.enterprise.domain.dto.ResumeSearchDataDTO;
import jobplatform.fo.enterprise.domain.mapper.ResumeMapper;
import jobplatform.fo.enterprise.domain.vo.ResumeListVO;

@Service
public class ResumeManagementService {

	private final ResumeMapper resumeMapper;

	public ResumeManagementService(ResumeMapper resumeMapper) {
		this.resumeMapper = resumeMapper;
	}
	
	//이력서 리스트 데이터 얻기
	public Map<String, Object> findResumeData(ResumeSearchDataDTO resumeSearchDataDTO) throws SQLException, IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		// 총 데이터 갯수
		int totalCount= resumeMapper.loadResumeListCount(resumeSearchDataDTO);
		// 페이지네이션 정보 생성 (기본 1페이지 = 데이터10 / 페이지그룹= 페이지5)
		Pagination pagination = new Pagination(totalCount, resumeSearchDataDTO.getPageNo());
		//페이지네이션 정보 map 추가
		map.put("paginationData", pagination);
		// 검색/정렬 정보 map 추가
		map.put("searchData", resumeSearchDataDTO);
		//이력서 리스트 정보 얻기
		List<ResumeListVO> resumeListVO = resumeMapper.selectResumeData(map);
		// 이력서 리스트 정보 map 추가
		map.put("resumeDatas", resumeListVO);
		
		return map;
	}
	
	// 이력서 대표여부 변경
	public Boolean modifyResumeyRepresentative(int rsm_sq) throws SQLException, IOException {
		Boolean result = false;
		
		if(resumeMapper.updateResumeyRepresentative(rsm_sq) > 0) {
			result = true;
		} else {
			//실패 예외
		}

		

		return result;
	}
	
	// 이력서 삭제
	@Transactional
	public Boolean deleteResumeFullData(int rsm_sq) throws SQLException, IOException {
		Boolean result = false;
		System.out.println(rsm_sq);
		//이력서 메인 + 자식 테이블 전부 업데이트
		if(resumeMapper.deleteResumeFullData(rsm_sq) == 1) {
			if(resumeMapper.delectRsmAtchmnt(rsm_sq) >= 0) {
				if(resumeMapper.delectRsmSi(rsm_sq) >= 0) {
					if(resumeMapper.delectRsmEdctn(rsm_sq) >= 0) {
						if(resumeMapper.delectRsmCrtfct(rsm_sq) >= 0) {
							if(resumeMapper.delectRsmCr(rsm_sq) >= 0) {
								result = true;
							};
						};
					};
				};
			};
		} 
		
		return result;
	}
	
	// 이력서 카피 로직수행
	@Transactional
	public Boolean copyResumeData(int rsm_sq) throws SQLException, IOException {
		Boolean result = false;
		
		//이력서 메인 + 자식 테이블 전부 insert
		if(resumeMapper.insertCopyResume(rsm_sq) == 1) {
			// 이력서 아랫 테이블 전부 만들어야함.
		};
		
		return result;
	}
	
}