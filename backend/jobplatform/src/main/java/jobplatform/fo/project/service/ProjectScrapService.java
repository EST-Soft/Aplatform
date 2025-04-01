package jobplatform.fo.project.service;

import java.util.List;

import jobplatform.fo.project.domain.ProjectDomain;

public interface ProjectScrapService {

    public boolean checkScrap(Long mbrSq, Long prjctSq);

    public void createScrap(Long mbrSq, Long prjctSq);

    public void removeScrap(Long mbrSq, Long prjctSq);

    public boolean checkEnterScrap(Long entrprsSq, Long prjctSq);

    public void createEnterScrap(Long entrprsSq, Long prjctSq);

    public void removeEnterScrap(Long entrprsSq, Long prjctSq);

    public List<ProjectDomain> getProjectByPrjctSq(Long mbrSq);
}
