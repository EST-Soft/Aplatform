package jobplatform.fo.project.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jobplatform.fo.project.domain.ProjectDomain;
import jobplatform.fo.project.mapper.ProjectMapper;

@Service
public class PojectServiceImpl implements ProjectService {


    @Autowired
    private ProjectMapper projectMapper;
    
    // public List<ProjectDomain> projectList(HashMap<String, Object> params) {
    //     return projectMapper.projectList(params);
    // }

    @Override
    public List<ProjectDomain> projectList(){
        return projectMapper.projectList();
    }

    @Override
    public ProjectDomain projectRead(int prjctSq) {
        return projectMapper.projectRead(prjctSq);
    }

    @Override
    public int projectInsert(ProjectDomain projectEntity) {
        return projectInsert(projectEntity);
    }

    @Override
    public int projectUpdate(ProjectDomain projectEntity) {
        return projectUpdate(projectEntity);
    }

    @Override
    public int projectDelete(int prjctSq) {
      return projectDelete(prjctSq);
    }
    
}
