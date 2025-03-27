package jobplatform.fo.user.domain.vo;

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class M_JobPosting_pp {
    private Long jbpSq;
    private Long entrprsSq;
    private String jbpTtl;
    private String jbpCntnt;
    private String crrDrtn;
    private String edctn;
    private Timestamp regstrDlnDtm;
    private Long pstnPrpslSq;
    private Timestamp prpslDtm;
    private String pstnPrpslCndtn;
    private String entrprsName;
    private List<String> areas;
    private List<String> jobs;
    private List<String> skills;
    private int hits;
    private String workForm;
    private String slry;
    private String forSeacrch;
    private String codeId;
    private String codeName;
    private String sklName;
    private String jobName;
    private String workArea;
    private String workHour;
    private Timestamp regstrStrtDtm;
    private String picName;
    private String picMp;
    private String picEml;
    private String jbpEndYn;
    private String jbpCndtn;
    private boolean isScrapped;
}
