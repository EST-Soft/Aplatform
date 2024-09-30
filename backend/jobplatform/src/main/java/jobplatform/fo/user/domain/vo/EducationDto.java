package jobplatform.fo.user.domain.vo;

import lombok.Data;

@Data
public class EducationDto {

    // 학교_이름
    private String schlName;
    // 학교_상태_코드
    private String schlCndtnCode;
    // 학교_학과
    private String schlDprtmnt;
    // 학교_입학_일
    private String schlAdmsnDate;
    // 학교_졸업_일
    private String schlGrdtnDate;

} // EducationDto