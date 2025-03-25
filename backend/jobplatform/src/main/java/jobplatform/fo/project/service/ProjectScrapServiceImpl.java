package jobplatform.fo.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jobplatform.fo.project.mapper.ProjectScrapMapper;

@Service
public class ProjectScrapServiceImpl implements ProjectScrapService {

    @Autowired
    ProjectScrapMapper scrapMapper;

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
}
