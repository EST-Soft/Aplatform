package jobplatform.fo.user.domain.vo;

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;

@Data
public class M_JobPosting_pp {
    private int jbp_sq;
    private int entrprs_sq;
    private String jbp_ttl;
    private String jbp_cntnt;
    private String crr;
    private String edctn;
    private Timestamp regstr_dln_dtm;
    private int pstn_prpsl_sq;
    private Timestamp prpsl_dtm;
    private int pstn_prpsl_cndtn;
    private String entrprs_name;
    private List<String> areas;
    private List<String> jobs;
    private List<String> skills;
    private int hits;
    private String work_form;
    private String slry;
    private String forSeacrch;
    

    @Override
    public String toString(){
        return jbp_ttl+jbp_cntnt+crr+edctn+entrprs_name+areas+jobs+skills;
    }

}
