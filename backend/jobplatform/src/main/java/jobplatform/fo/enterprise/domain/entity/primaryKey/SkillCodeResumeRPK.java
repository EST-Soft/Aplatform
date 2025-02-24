package jobplatform.fo.enterprise.domain.entity.primaryKey;

import java.io.Serializable;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SkillCodeResumeRPK implements Serializable {

    @Column(name = "rsm_sq")
    private Long rsmSq;

    @Column(name = "skl_code_sq")
    private Long sklCodeSq;
} // SkillCodeResumeRPK
