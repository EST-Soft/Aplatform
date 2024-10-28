package jobplatform.fo.enterprise.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jobplatform.fo.enterprise.domain.dto.SkillCodeSDto;
import jobplatform.fo.enterprise.domain.entity.primaryKey.SkillCodeResumeRPK;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "p2_tbl_skill_code_resume_r")
@IdClass(SkillCodeResumeRPK.class)
public class SkillCodeResumeREntity {

    @Id
    @Column(name = "rsm_sq", nullable = false)
    private Long rsmSq;

    @Id
    @Column(name = "skl_code_sq", nullable = false)
    private Long sklCodeSq;

    public SkillCodeResumeREntity(Long rsmSq, SkillCodeSDto skillCodeSDto) {
        this.rsmSq = rsmSq;
        this.sklCodeSq = skillCodeSDto.getSklCodeSq();
    } // SkillCodeResumeREntity

} // SkillCodeResumeREntity
