package jobplatform.fo.enterprise.domain.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jobplatform.fo.enterprise.domain.entity.AreaEntity;
import jobplatform.fo.enterprise.domain.entity.JobEntity;
import jobplatform.fo.enterprise.domain.entity.JobPostingEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobPostingDTO {

    private int jbpSq;
    private String jbpTtl;
    private String jbpCntnt;
    private int hits;
    private String crrDrtn;
    private String sklName;
    private List<String> jobName;
    private String edctn;
    private List<String> workArea;
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
    private Long entrprsSq;
    private String enterpriseName;

    @Builder.Default
    private List<Integer> areaIds = new ArrayList<>(); // 빈 리스트로 초기화
    @Builder.Default
    private List<Integer> jobIds = new ArrayList<>(); // 빈 리스트로 초기화

    // from 메소드 추가
    public static JobPostingDTO from(JobPostingEntity entity) {
//        List<String> jobName = entity.getJobName().stream()
//                .map(JobEntity::getJobScName)
//                .collect(Collectors.toList());
//
//        List<String> areaName = entity.getWorkArea().stream()
//                .map(AreaEntity::getAreaName)
//                .collect(Collectors.toList());
//
//        List<Integer> jobIds = entity.getJobName().stream()
//                .map(JobEntity::getJobSq)
//                .collect(Collectors.toList());
//
//        List<Integer> areaIds = entity.getWorkArea().stream()
//                .map(AreaEntity::getAreaSq)
//                .collect(Collectors.toList());

        return JobPostingDTO.builder()
                .jbpSq(entity.getJbpSq())
                .jbpTtl(entity.getJbpTtl())
                .jbpCntnt(entity.getJbpCntnt())
                .hits(entity.getHits())
                .crrDrtn(entity.getCrrDrtn())
                .sklName(entity.getSklName())
                .jobName(entity.getJobName())
                .workArea(entity.getWorkArea())
                .edctn(entity.getEdctn())
                .workForm(entity.getWorkForm())
                .slry(entity.getSlry())
                .workHour(entity.getWorkHour())
                .regstrStrtDtm(entity.getRegstrStrtDtm())
                .regstrDlnDtm(entity.getRegstrDlnDtm())
                .picName(entity.getPicName())
                .picMp(entity.getPicMp())
                .picEml(entity.getPicEml())
                .jbpEndYn(entity.getJbpEndYn())
                .jbpCndtn(entity.getJbpCndtn())
                .insrtMbrSq(entity.getInsrtMbrSq())
                .insrtDtm(entity.getInsrtDtm())
                .updtMbrSq(entity.getUpdtMbrSq())
                .updtDtm(entity.getUpdtDtm())
                .dltYn(entity.getDltYn().name())
                .useYn(entity.getUseYn().name())
                .entrprsSq(entity.getEnterpriseMember().getEntrprsSq())
                .enterpriseName(entity.getEnterpriseMember().getEntrprsName())
                .build();
    }
}
