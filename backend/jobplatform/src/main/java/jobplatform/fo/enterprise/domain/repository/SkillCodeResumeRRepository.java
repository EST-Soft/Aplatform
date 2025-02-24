package jobplatform.fo.enterprise.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jobplatform.fo.enterprise.domain.entity.SkillCodeResumeREntity;
import jobplatform.fo.enterprise.domain.entity.primaryKey.SkillCodeResumeRPK;

public interface SkillCodeResumeRRepository extends JpaRepository<SkillCodeResumeREntity, SkillCodeResumeRPK> {

}
