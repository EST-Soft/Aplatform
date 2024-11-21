package jobplatform.fo.user.domain.service;

import jobplatform.fo.user.domain.mapper.ScrapMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScrapServiceImpl implements ScrapService {

    private final ScrapMapper scrapMapper;

    @Override
    public void addScrap(Long mbrSq, Long entrprsSq, Long scrapTargetSq, String scrapTargetType) {
        scrapMapper.insertScrap(mbrSq, entrprsSq, scrapTargetSq, scrapTargetType);
    }

    @Override
    public void deleteScrap(Long scrapTargetSq) {
        scrapMapper.deleteScrap(scrapTargetSq);
    }

    @Override
    public List<Object> getScrapListByMember(Long mbrSq) {
        return scrapMapper.selectScrapsByMember(mbrSq);
    }

    @Override
    public boolean checkScrapExists(Long mbrSq, Long scrapTargetSq, String scrapTargetType) {
        return scrapMapper.checkScrapExists(mbrSq, scrapTargetSq, scrapTargetType);
    }
}
