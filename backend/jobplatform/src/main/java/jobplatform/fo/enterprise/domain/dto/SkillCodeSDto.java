package jobplatform.fo.enterprise.domain.dto;

import java.util.List;

import jobplatform.fo.enterprise.domain.entity.SkillCodeSEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SkillCodeSDto {

    // 기술 코드 순번
    private Long sklCodeSq;
    // 기술 대분류 이름
    private String sklLcName;
    // 기술 중분류 이름
    private String sklMcName;
    // 기술 소분류 이름
    private String sklScName;

    public static SkillCodeSDto toList(SkillCodeSEntity skillCodeSEntitie) {
        SkillCodeSDto dto = new SkillCodeSDto();
        dto.sklCodeSq = skillCodeSEntitie.getSklCodeSq();
        dto.sklLcName = skillCodeSEntitie.getSklLcName();
        dto.sklMcName = skillCodeSEntitie.getSklMcName();
        dto.sklScName = skillCodeSEntitie.getSklScName();
        return dto;
    } // toList

} // SkillCodeSDto
