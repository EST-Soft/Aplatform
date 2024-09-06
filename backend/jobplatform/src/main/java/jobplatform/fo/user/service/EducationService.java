package jobplatform.fo.user.service;

import jobplatform.fo.user.domain.vo.EducationDto;

public interface EducationService {

    void insertEducation(Long mbrSq, Long rsmSq, EducationDto educationDto);

} // EducationService
