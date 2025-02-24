package jobplatform.fo.enterprise.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jobplatform.fo.enterprise.domain.dto.CareerDto;
import jobplatform.fo.enterprise.domain.entity.CareerEntity;
import jobplatform.fo.enterprise.domain.repository.CareerRepositoey;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CareerServiceImpl implements CareerService {

    private final CareerRepositoey careerRepositoey;

    @Override
    public void insertCareerData(Long mbrSq, Long rsmSq, List<CareerDto> careerDtos) {
        for (CareerDto careerDto : careerDtos) {
            CareerEntity careerEntity = new CareerEntity(mbrSq, rsmSq, careerDto);
            careerRepositoey.save(careerEntity);
        }
    }

} // CareerServiceImpl
