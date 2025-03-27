package jobplatform.fo.enterprise.domain.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectDTO {
    private Long prjctSq;
    private Long mbrSq;
    private Long entrprsSq;

    private String prjctTtl;
    private String prjctTpy;

    private String prjctWork;

    private String prjctUseSkl;

    private String prjctEsntlSkl;

    private String prjctEsntlCrr;

    private LocalDateTime prjctStrt;

    private LocalDateTime prjctEnd;

    private String prjctLctn;

    private String ntfctnSendYn;

    private String prjctPrd;
    private EnterMemberEntity enterpriseMember;
    private String enterpriseName;

    private boolean isScrapped;
}
