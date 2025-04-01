package jobplatform.fo.project.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jobplatform.fo.project.domain.ProjectDomain;
import jobplatform.fo.project.domain.ProjectScrapDomain;
import jobplatform.fo.project.mapper.ProjectMapper;
import jobplatform.fo.project.mapper.ProjectScrapMapper;

@Service
public class ProjectScrapServiceImpl implements ProjectScrapService {

    @Autowired
    ProjectScrapMapper scrapMapper;

    @Autowired
    ProjectMapper projectMapper;

    @Override
    public boolean checkScrap(Long mbrSq, Long prjctSq) {
        return scrapMapper.isScrapped(mbrSq, prjctSq);
    }

    // 스크랩 생성
    @Override
    @Transactional
    public void createScrap(Long mbrSq, Long prjctSq) {
        if (!checkScrap(mbrSq, prjctSq)) { // 스크랩이 없으면 생성
            scrapMapper.createScrap(mbrSq, prjctSq);
        }
    }

    // 스크랩 제거
    @Override
    @Transactional
    public void removeScrap(Long mbrSq, Long prjctSq) {
        if (checkScrap(mbrSq, prjctSq)) { // 스크랩이 있으면 제거
            scrapMapper.removeScrap(mbrSq, prjctSq);
        }
    }


    @Override
    public boolean checkEnterScrap(Long entrprsSq, Long prjctSq) {
        return scrapMapper.isEnterScrapped(entrprsSq, prjctSq);
    }

    // 스크랩 생성
    @Override
    @Transactional
    public void createEnterScrap(Long entrprsSq, Long prjctSq) {
        if (!checkEnterScrap(entrprsSq, prjctSq)) { // 스크랩이 없으면 생성
            scrapMapper.createEnterScrap(entrprsSq, prjctSq);
        }
    }

    // 스크랩 제거
    @Override
    @Transactional
    public void removeEnterScrap(Long entrprsSq, Long prjctSq) {
        if (checkEnterScrap(entrprsSq, prjctSq)) { // 스크랩이 있으면 제거
            scrapMapper.removeEnterScrap(entrprsSq, prjctSq);
        }
    }


    // 스크랩 프로젝트 불러오기 (개인)
    @Override
    public List<ProjectDomain> getProjectByPrjctSq(Long mbrSq) {

        List<Long> prjctSqList = scrapMapper.findByMbrSq(mbrSq);

        List<ProjectDomain> projectDomains = prjctSqList.stream()
            .map(prjctSq -> projectMapper.projectRead(prjctSq.intValue()))
            .filter(item -> item != null)
            .collect(Collectors.toList());

            return projectDomains;
    }
}


/*@Override
public List<ProjectDomain> getProjectByPrjctSq(Long mbrSq) {
    // 1. mbrSq를 기준으로 prjctSq 리스트 조회
    List<Long> prjctSqList = scrapMapper.findByMbrSq(mbrSq);

    // 2. Stream을 사용하여 prjctSqList를 처리
    List<ProjectDomain> projectDomains = prjctSqList.stream()
            .map(prjctSq -> scrapMapper.projectRead(prjctSq.intValue())) // long → int 변환 후 projectRead 호출
            .filter(Objects::nonNull) // null인 프로젝트는 제외
            .collect(Collectors.toList()); // 결과를 리스트로 수집

    return projectDomains;
}
 */