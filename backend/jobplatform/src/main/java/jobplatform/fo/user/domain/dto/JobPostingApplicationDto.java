package jobplatform.fo.user.domain.dto;

import org.apache.ibatis.type.Alias;

import jobplatform.fo.enterprise.domain.vo.ResumeListVO;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Alias("JobPostingApplicationMapper")
public class JobPostingApplicationDto {
    private String mbrName;
    private String mbrBd;
    private String mbrMp;
    private ResumeListVO selectedResume;
}
