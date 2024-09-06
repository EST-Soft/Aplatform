package jobplatform.fo.enterprise.service;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import jobplatform.fo.enterprise.common.Pagination;
import jobplatform.fo.enterprise.domain.dto.ResumeDataDTO;
import jobplatform.fo.enterprise.domain.dto.ResumeSearchDataDTO;
import jobplatform.fo.enterprise.domain.entity.AttachmentEntity;
import jobplatform.fo.enterprise.domain.entity.ResumeEntity;
import jobplatform.fo.enterprise.domain.mapper.ResumeMapper;
import jobplatform.fo.enterprise.domain.repository.AttachmentRepository;
import jobplatform.fo.enterprise.domain.repository.ResumeRepository;
import jobplatform.fo.enterprise.domain.vo.ResumeAttachmentDataVO;
import jobplatform.fo.enterprise.domain.vo.ResumeCareerDataVO;
import jobplatform.fo.enterprise.domain.vo.ResumeCertificateDataVO;
import jobplatform.fo.enterprise.domain.vo.ResumeEducationDataVO;
import jobplatform.fo.enterprise.domain.vo.ResumeListVO;
import jobplatform.fo.enterprise.domain.vo.ResumeSelfintroductionDataVO;
import jobplatform.fo.enterprise.domain.vo.ResumeSkillCodeDataVO;
import jobplatform.fo.user.domain.repository.MemberRepository;

@Service
public class ResumeManagementService {

	private final ResumeMapper resumeMapper;
	private final MemberRepository memberRepository;
	private final ResumeRepository resumeRepository;
	private final AttachmentRepository attachmentRepository;

	public ResumeManagementService(ResumeMapper resumeMapper, MemberRepository memberRepository,
			ResumeRepository resumeRepository, AttachmentRepository attachmentRepository) {
		this.memberRepository = memberRepository;
		this.resumeRepository = resumeRepository;
		this.resumeMapper = resumeMapper;
		this.attachmentRepository = attachmentRepository;
	}

	// 이력서 리스트 데이터 얻기
	public Map<String, Object> findResumeData(ResumeSearchDataDTO resumeSearchDataDTO)
			throws SQLException, IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		// 총 데이터 갯수
		int totalCount = resumeMapper.loadResumeListCount(resumeSearchDataDTO);
		// 페이지네이션 정보 생성 (기본 1페이지 = 데이터10 / 페이지그룹= 페이지5)
		Pagination pagination = new Pagination(totalCount, resumeSearchDataDTO.getPageNo());
		// 페이지네이션 정보 map 추가
		map.put("paginationData", pagination);
		// 검색/정렬 정보 map 추가
		map.put("searchData", resumeSearchDataDTO);
		// 이력서 리스트 정보 얻기
		List<ResumeListVO> resumeListVO = resumeMapper.selectResumeData(map);
		// 이력서 리스트 정보 map 추가
		map.put("resumeDatas", resumeListVO);

		return map;
	}

	// 이력서 대표여부 변경
	public Boolean modifyResumeyRepresentative(int rsm_sq) throws SQLException, IOException {
		Boolean result = false;

		if (resumeMapper.updateResumeyRepresentative(rsm_sq) > 0) {
			result = true;
		} else {
			// 실패 예외
		}

		return result;
	}

	// 이력서 상세정보 얻기
	public Map<String, Object> findResumeM(int rsm_sq) {
		Map<String, Object> map = new HashMap<>();

		// 이력서 상세 정보 조회
		Map<String, Object> resumeInfo = resumeMapper.selectResumeM(rsm_sq);

		// 학력 정보 가져오기
		List<ResumeEducationDataVO> rsmedctnInfo = resumeMapper.selectResumeEducation(rsm_sq);
		// 경력 정보 가져오기
		List<ResumeCareerDataVO> rsmcrInfo = resumeMapper.selectResumeCareer(rsm_sq);
		// 기술 정보 가져오기
		List<ResumeSkillCodeDataVO> rsmsklcdInfo = resumeMapper.selectResumeSkillCode(rsm_sq);
		// 자격증 정보 가져오기
		List<ResumeCertificateDataVO> rsmcrtfctInfo = resumeMapper.selectResumeCertificate(rsm_sq);
		// 자기소개서 정보 가져오기
		List<ResumeSelfintroductionDataVO> rsmsiInfo = resumeMapper.selectResumeSelfIntroduction(rsm_sq);
		// 첨부파일 정보 가져오기
		List<ResumeAttachmentDataVO> rsmatchmntInfo = resumeMapper.selectResumeAttachment(rsm_sq);

		// 결과를 map에 추가
		map.put("rsmInfo", resumeInfo);
		map.put("rsmedctnInfo", rsmedctnInfo);
		map.put("rsmcrInfo", rsmcrInfo);
		map.put("rsmsklcdInfo", rsmsklcdInfo);
		map.put("rsmcrtfctInfo", rsmcrtfctInfo);
		map.put("rsmsiInfo", rsmsiInfo);
		map.put("rsmatchmntInfo", rsmatchmntInfo);

		return map;
	}

	// 이력서 삭제
	@Transactional
	public Boolean deleteResumeFullData(int rsm_sq) throws SQLException, IOException {
		Boolean result = false;
		System.out.println(rsm_sq);
		// 이력서 메인 + 자식 테이블 전부 업데이트
		if (resumeMapper.deleteResumeFullData(rsm_sq) == 1) {
			if (resumeMapper.delectRsmAtchmnt(rsm_sq) >= 0) {
				if (resumeMapper.delectRsmSi(rsm_sq) >= 0) {
					if (resumeMapper.delectRsmEdctn(rsm_sq) >= 0) {
						if (resumeMapper.delectRsmCrtfct(rsm_sq) >= 0) {
							if (resumeMapper.delectRsmCr(rsm_sq) >= 0) {
								result = true;
							}
							;
						}
						;
					}
					;
				}
				;
			}
			;
		}

		return result;
	}

	// 이력서 카피 로직수행
	@Transactional
	public Boolean copyResumeData(int rsm_sq) throws SQLException, IOException {
		Boolean result = false;

		// 이력서 메인 + 자식 테이블 전부 insert
		if (resumeMapper.insertCopyResume(rsm_sq) == 1) {
			// 이력서 아랫 테이블 전부 만들어야함.
		}
		;

		return result;
	}

	@Transactional
	public Long insertResume(Long mbrSq, ResumeDataDTO resumeDataDTO) {

		String fileUrl = null;
		String originalFileName = null;

		if (resumeDataDTO.getRsmImgFileUrl() != null && resumeDataDTO.getRsmImgOrgnlFn() != null) {
			fileUrl = resumeDataDTO.getRsmImgFileUrl();
			originalFileName = resumeDataDTO.getRsmImgOrgnlFn();
		}

		ResumeEntity resumeEntity = new ResumeEntity();
		// resumeEntity.setMbrSq(resumeDataDTO.getMbrSq());
		resumeEntity.setMbrSq(mbrSq);
		resumeEntity.setRsmImgOrgnlFn(originalFileName);
		resumeEntity.setRsmImgFileUrl(fileUrl);
		resumeEntity.setRsmFnlEdctnCode(resumeDataDTO.getRsmFnlEdctnCode());
		resumeEntity.setRsmGrd(resumeDataDTO.getRsmGrd());
		resumeEntity.setRsmEs(resumeDataDTO.getRsmEs());
		resumeEntity.setRsmTtl(resumeDataDTO.getRsmTtl());
		resumeEntity.setRsmName(resumeDataDTO.getRsmName());
		resumeEntity.setRsmGndrCode(resumeDataDTO.getRsmGndrCode());
		resumeEntity.setRsmBd(resumeDataDTO.getRsmBd());
		resumeEntity.setRsmMp(resumeDataDTO.getRsmMp());
		resumeEntity.setRsmAdrs(resumeDataDTO.getRsmAdrs());
		resumeEntity.setRsmEml(resumeDataDTO.getRsmEml());
		resumeEntity.setInsrtMbrSq(mbrSq);
		resumeEntity.setUpdtDtm(LocalDateTime.now());
		resumeEntity.setUpdtMbrSq(mbrSq);
		resumeEntity.setInsrtMbrSq(mbrSq);

		Long rsmSq = resumeRepository.save(resumeEntity).getRsmSq();

		if (fileUrl != null && originalFileName != null) {
			AttachmentEntity attachmentEntity = new AttachmentEntity();
			attachmentEntity.setRsmSq(rsmSq);
			attachmentEntity.setAtchmntOrgnlFn(originalFileName);
			attachmentEntity.setAtchmntUrl(fileUrl);
			attachmentEntity.setInsrtMbrSq(mbrSq);
			attachmentRepository.save(attachmentEntity);
		}

		return rsmSq;
	} // insertResume

	// 이력서 가져오기
	public ResumeEntity getRsmByRsmSq(Long rsmSq) {
		Optional<ResumeEntity> resumeEntity = null;

		resumeEntity = resumeRepository.findByRsmSq(rsmSq);
		return resumeEntity.orElse(null);
	} // getRsmByRsmSq

	/*
	 * // 이력서 이미지 등록
	 * 
	 * @Transactional
	 * public ResumeEntity insertImage(MultipartFile file, ResumeEntity
	 * resumeEntity){
	 * String fileUrl = resumeEntity.getRsmImgFileUrl();
	 * String originalFileName = resumeEntity.getRsmImgOrgnlFn();
	 * 
	 * if (file != null && !file.isEmpty()) {
	 * // 파일을 S3에 업로드하고 URL을 반환받음
	 * fileUrl = fileServiceImpl.uploadImage(file);
	 * originalFileName = file.getOriginalFilename();
	 * }
	 * 
	 * resumeEntity.setRsmImgFileUrl(fileUrl);
	 * resumeEntity.setRsmImgOrgnlFn(originalFileName);
	 * 
	 * return resumeEntity;
	 * } // insertImage
	 */
}