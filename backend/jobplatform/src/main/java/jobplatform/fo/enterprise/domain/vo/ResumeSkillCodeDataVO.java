package jobplatform.fo.enterprise.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResumeSkillCodeDataVO {
    //기술 코드 순번
    private int skl_code_sq;
    //기술 대분류 이름
    private String skl_lc_name;
    //기술 중분류 이름
    private String skl_mc_name;
    //기술 소분류 이름
    private String skl_sc_name;
    //사용 여부
    private String use_yn;
}
