package jobplatform.fo.project.domain;

import java.util.Date;

import lombok.Data;

@Data
public class ProjectDomain {
    private int prjctSq;
    private String entrprsName;
    private String prjctTtl;
    private String prjctTpy;
    private String prjctWork;
    private String prjctUseSkl;
    private String prjctEsntlSkl;
    private String prjctEsntlCrr;
    private Date prjctStrt;
    private Date prjctEnd;
    private String prjctPrd;
    private String prjctLctn;
    private String ntfctnSendYn;

}