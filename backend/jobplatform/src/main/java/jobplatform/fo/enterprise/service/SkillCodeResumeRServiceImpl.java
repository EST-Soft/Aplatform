package jobplatform.fo.enterprise.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Override
    public void insertSkillCodeResume(Long rsmSq, List<SkillCodeSDto> skillCodeSDtos) {

        for (SkillCodeSDto skillCodeSDto : skillCodeSDtos) {
            
            int exist = skillCodeMapper.existSkill(rsmSq, skillCodeSDto.getSklCodeSq());
            if(exist < 1){
           int result = skillCodeMapper.skillinsert(rsmSq, skillCodeSDto.getSklCodeSq());
           if(result < 0){
               System.out.println("등록 실패");
            }
            System.out.println("등록 성공");
        }
        System.out.println("중복됨");
            
        }
        

    } // insertSkillCodeResume

} // SkillCodeResumeRServiceImpl
