package jobplatform.fo.project.domain;

import java.util.Date;

import lombok.Data;

@Data
public class ProjectDomain {
    private int prjctSq;
        // 기업 순번
    private int entrprsSq;
    private String entrprsName;
    private String prjctWork;
    private String prjctEsntlCrr;
    private Date prjctStrt;
    private Date prjctEnd;
      // 프로젝트 기간
    private String prjctPrd;
       // 프로젝트 wldur
    private String prjctLctn;
    private String ntfctnSendYn;
    // 프로젝트 제목
    private String prjctTtl;
    // 근무형태
    private String prjctTpy;
    // 사용기술
    private String prjctUseSkl;
    // 필수 스킬
    private String prjctEsntlSkl;
    // 채용 시작일자
    private Date prjctStrtDate;
    // 채용 마감일자
    private Date prjctEndDate;
    // 알람 발송 여부
    private boolean check;



}