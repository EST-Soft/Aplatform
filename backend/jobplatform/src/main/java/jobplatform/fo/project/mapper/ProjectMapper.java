package jobplatform.fo.project.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import jobplatform.fo.project.domain.ProjectDomain;

@Mapper
public interface ProjectMapper {
 
    // public List<ProjectDomain> projectList(@Param("params") HashMap<String, Object> option);

    public List<ProjectDomain> projectList();

    public ProjectDomain projectRead(@Param("prjctSq") int prjctSq);

    public int projectInsert(ProjectDomain projectEntity);

    public int projectUpdate(ProjectDomain projectEntity);

    public int projectDelete(@Param("prjctSq") int prjctSq);
}
