package jobplatform.fo.project.service;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import jobplatform.fo.project.domain.ProjectDomain;

public interface ProjectService {
    
    
    // public List<ProjectDomain> projectList(@Param("params") HashMap<String, Object> option);


    public List<ProjectDomain> projectList();

    public ProjectDomain projectRead(@Param("prjctSq") int prjctSq);

    public int projectInsert(ProjectDomain projectEntity);

    public int projectUpdate(ProjectDomain projectEntity);

    public int projectDelete(@Param("prjctSq") int prjctSq);

    
    public List<String> selectEmailsForEssentialSkills(@Param("prjctSq") int prjctSq);

    public List<Integer> selectQualifiedMembers(@Param("prjctSq") int prjctSq);

    
}
