package jobplatform.fo.user.domain.service;

import java.util.List;

public interface ScrapService {

    void addScrap(Long mbrSq, Long entrprsSq, Long scrapTargetSq, String scrapTargetType);

    void deleteScrap(Long scrapTargetSq);

    List<Object> getScrapListByMember(Long mbrSq);

    boolean checkScrapExists(Long mbrSq, Long scrapTargetSq, String scrapTargetType);
}
