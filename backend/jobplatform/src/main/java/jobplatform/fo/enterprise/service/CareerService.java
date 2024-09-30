package jobplatform.fo.enterprise.service;

import java.util.List;

import jobplatform.fo.enterprise.domain.dto.CareerDto;

public interface CareerService {
    public void insertCareerData(Long mbrSq, Long rsmSq, List<CareerDto> careerDtos);
} // CareerService
