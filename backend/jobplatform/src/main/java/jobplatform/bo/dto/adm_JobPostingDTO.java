package jobplatform.bo.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class adm_JobPostingDTO {

    private int jbpSq;
    private Long entrprsSq;
    private String jbpTl;
    private String jbpCntnt;
    private int hits;
    private String cr;
    private String sklName;
    private String jobName;
    private String edctn;
    private String workArea;
    private String workForm;
    private String slry;
    private String workHour;
    private LocalDate regstrStrtDtm;
    private LocalDate regstrDlnDtm;
    private String picName;
    private String picMp;
    private String picEml;
    private String jbpEndYn;
    private String jbpCndtn;
    private int insrtMbrSq;
    private LocalDateTime insrtDtm;
    private Integer updtMbrSq;
    private LocalDateTime updtDtm;
    private String dltYn;
    private String useYn;
    private String enterpriseName;


     
   
}
