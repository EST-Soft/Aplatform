package jobplatform.fo.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jobplatform.fo.user.domain.entity.EducationEntity;
import jobplatform.fo.user.domain.repository.EducationRepository;
import jobplatform.fo.user.domain.vo.EducationDto;

@Service
public class EducationServiceImpl implements EducationService {

    @Autowired
    private EducationRepository educationRepository;

    @Override
    public void insertEducation(Long mbrSq, Long rsmSq, EducationDto educationDto) {
        EducationEntity educationEntity = new EducationEntity(mbrSq, rsmSq, educationDto);
        educationRepository.save(educationEntity);
    } // insertEducation

} // EducationServiceImpl
