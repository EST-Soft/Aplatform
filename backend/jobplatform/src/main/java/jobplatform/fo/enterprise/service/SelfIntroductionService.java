package jobplatform.fo.enterprise.service;

import java.util.List;

import jobplatform.fo.enterprise.domain.dto.SelfIntroductionsDataDTO;

public interface SelfIntroductionService {
    public void insertSelfIntroduction(Long mbrSq, Long rsmSq,
            List<SelfIntroductionsDataDTO> selfIntroductionsDataDTOs);
} // SelfIntroductionService
