package jobplatform.fo.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jobplatform.fo.user.domain.entity.EducationEntity;
import jobplatform.fo.user.domain.repository.EducationRepository;
import jobplatform.fo.user.domain.vo.EducationDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EducationServiceImpl implements EducationService {

    private final EducationRepository educationRepository;

    @Override
    public void insertEducation(Long mbrSq, Long rsmSq, List<EducationDto> educationDtos) {
        for (EducationDto educationDto : educationDtos) {
            EducationEntity educationEntity = new EducationEntity(mbrSq, rsmSq, educationDto);
            educationRepository.save(educationEntity);
        }
    } // insertEducation

} // EducationServiceImpl
