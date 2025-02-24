package jobplatform.fo.user.service;

import java.util.List;

import jobplatform.fo.user.domain.vo.EducationDto;

public interface EducationService {

    public void insertEducation(Long mbrSq, Long rsmSq, List<EducationDto> educationDtos);

} // EducationService
