package jobplatform.fo.enterprise.service;

import java.util.List;

import jobplatform.fo.enterprise.domain.dto.SkillCodeSDto;

public interface SkillCodeResumeRService {
    public void insertSkillCodeResume(Long rsmSq, List<SkillCodeSDto> skillCodeSDtos);
} // SkillCodeResumeRService
