package jobplatform.fo.project.service;

public interface ProjectScrapService {

    public boolean checkScrap(Long mbrSq, Long prjctSq);

    public void createScrap(Long mbrSq, Long prjctSq);

    public void removeScrap(Long mbrSq, Long prjctSq);

}
