package jobplatform.fo.user.domain.vo;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class M_ProjectPosting_pp {
    private Long prjctSq;           // 프로젝트 ID (Primary Key)
    private Long entrprsSq;         // 기업 ID
    private String entrprsName;     // 기업 이름
    private String prjctTtl;        // 프로젝트 제목
    private String prjctTpy;        // 프로젝트 유형
    private String prjctWork;       // 프로젝트 업무
    private String prjctUseSkl;     // 사용 기술
    private String prjctEsntlSkl;   // 필수 기술 (nullable)
    private String prjctEsntlCrr;   // 필수 경력
    private Date prjctStrtDate;     // 프로젝트 시작일
    private Date prjctEndDate;      // 프로젝트 종료일
    private String prjctLctn;       // 프로젝트 위치
    private Character ntfctnSendYn; // 알림 발송 여부 (nullable)
    private String prjctPrd;        // 프로젝트 기간
    private String prjctCntnt;      // 프로젝트 내용 (nullable)
}
