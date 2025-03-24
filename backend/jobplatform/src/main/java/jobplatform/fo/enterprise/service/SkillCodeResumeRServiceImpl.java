package jobplatform.fo.enterprise.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jobplatform.fo.enterprise.domain.dto.SkillCodeSDto;
import jobplatform.fo.enterprise.domain.entity.SkillCodeResumeREntity;
import jobplatform.fo.enterprise.domain.repository.SkillCodeResumeRRepository;
import jobplatform.fo.skill.mapper.SkillCodeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SkillCodeResumeRServiceImpl implements SkillCodeResumeRService {

    // private final SkillCodeResumeRRepository skillCodeResumeRRepository;

    @Autowired
    private SkillCodeMapper skillCodeMapper;

    @Override
    public void insertSkillCodeResume(Long rsmSq, List<SkillCodeSDto> skillCodeSDtos) {

        System.out.println("왜 아나옴?" + skillCodeSDtos);
        for (SkillCodeSDto skillCodeSDto : skillCodeSDtos) {
            
            System.out.println("왜 아나옴2222?" + skillCodeSDto.getSklCodeSq());
            skillCodeMapper.skillinsert(rsmSq, skillCodeSDto.getSklCodeSq());
            
        }
        

    } // insertSkillCodeResume

} // SkillCodeResumeRServiceImpl
