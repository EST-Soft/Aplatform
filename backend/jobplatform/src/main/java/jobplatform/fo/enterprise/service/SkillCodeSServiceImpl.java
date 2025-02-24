package jobplatform.fo.enterprise.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import jobplatform.fo.enterprise.domain.dto.SkillCodeSDto;
import jobplatform.fo.enterprise.domain.entity.SkillCodeSEntity;
import jobplatform.fo.enterprise.domain.repository.SkillCodeSRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SkillCodeSServiceImpl implements SkillCodeSService {

    private final SkillCodeSRepository skillCodeSRepository;

    @Override
    public List<SkillCodeSDto> getSkillCodes() {
        List<SkillCodeSEntity> sCodeSEntities = skillCodeSRepository.findAll();
        List<SkillCodeSDto> skillCodeSDtos = new ArrayList<SkillCodeSDto>();
        System.out.println("sadsadasdas : " + sCodeSEntities);

        skillCodeSDtos = sCodeSEntities.stream().map(SkillCodeSDto::toList).collect(Collectors.toList());

        System.out.println("sdfasdf : " + skillCodeSDtos);
        return skillCodeSDtos;
    } // getSkillCodes

} // SkillCodeSServiceImpl
