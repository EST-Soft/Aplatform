package jobplatform.fo.enterprise.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jobplatform.fo.enterprise.domain.dto.SkillCodeSDto;
import jobplatform.fo.enterprise.domain.entity.SkillCodeResumeREntity;
import jobplatform.fo.enterprise.domain.repository.SkillCodeResumeRRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SkillCodeResumeRServiceImpl implements SkillCodeResumeRService {

    private final SkillCodeResumeRRepository skillCodeResumeRRepository;

    @Override
    public void insertSkillCodeResume(Long rsmSq, List<SkillCodeSDto> skillCodeSDtos) {
        for (SkillCodeSDto skillCodeSDto : skillCodeSDtos) {
            SkillCodeResumeREntity skillCodeResumeREntity = new SkillCodeResumeREntity(rsmSq, skillCodeSDto);
            skillCodeResumeRRepository.save(skillCodeResumeREntity);
        }

    } // insertSkillCodeResume

} // SkillCodeResumeRServiceImpl
