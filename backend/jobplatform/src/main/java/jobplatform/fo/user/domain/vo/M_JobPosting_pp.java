package jobplatform.fo.user.domain.vo;

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;

@Data
public class M_JobPosting_pp {
    private Long jbp_sq;
    private Long entrprs_sq;
    private String jbp_ttl;
    private String jbp_cntnt;
    private String crr_drtn;
    private String edctn;
    private Timestamp regstr_dln_dtm;
    private Long pstn_prpsl_sq;
    private Timestamp prpsl_dtm;
    private String pstn_prpsl_cndtn;
    private String entrprs_name;
    private List<String> areas;
    private List<String> jobs;
    private List<String> skills;
    private int hits;
    private String work_form;
    private String slry;
    private String forSeacrch;
    private String code_id;
    private String code_name;
    private String skl_name;
    private String job_name;
    private String work_area;
    private String work_hour;
    private Timestamp regstr_strt_dtm;
    private String pic_name;
    private String pic_mp;
    private String pic_eml;
    private String jbp_end_yn;
    private String jbp_cndtn;

    

    @Override
    public String toString(){
        return jbp_ttl+jbp_cntnt+crr_drtn+edctn+entrprs_name+areas+jobs+skills;
    }

}
