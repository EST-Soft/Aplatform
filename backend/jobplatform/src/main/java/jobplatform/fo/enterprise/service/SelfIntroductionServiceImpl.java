package jobplatform.fo.enterprise.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jobplatform.fo.enterprise.domain.dto.SelfIntroductionsDataDTO;
import jobplatform.fo.enterprise.domain.entity.SelfIntroductionEntity;
import jobplatform.fo.enterprise.domain.repository.SelfIntroductionRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SelfIntroductionServiceImpl implements SelfIntroductionService {
    private final SelfIntroductionRepository selfIntroductionRepository;

    @Override
    public void insertSelfIntroduction(Long mbrSq, Long rsmSq,
            List<SelfIntroductionsDataDTO> selfIntroductionsDataDTOs) {

        for (SelfIntroductionsDataDTO selfIntroductionsDataDTO : selfIntroductionsDataDTOs) {
            SelfIntroductionEntity selfIntroductionEntity = new SelfIntroductionEntity(mbrSq, rsmSq,
                    selfIntroductionsDataDTO);
            selfIntroductionRepository.save(selfIntroductionEntity);
        }
    } // insertSelfIntroduction

} // SelfIntroductionServiceImpl
