package jobplatform.fo.project.service;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import jobplatform.fo.enterprise.domain.entity.JobPostingEntity;
import jobplatform.fo.enterprise.domain.entity.ProjectListEntity;
import jobplatform.fo.project.domain.ProjectDomain;

public interface ProjectService {
    
    
    // public List<ProjectDomain> projectList(@Param("params") HashMap<String, Object> option);


    public List<ProjectDomain> projectList();

    public ProjectDomain projectRead(@Param("prjctSq") int prjctSq);

    public int projectInsert(ProjectDomain projectEntity);

    public int projectUpdate(ProjectDomain projectEntity);

    public int projectDelete(@Param("prjctSq") int prjctSq);

    
    public List<String> selectEmailsForEssentialSkills(@Param("prjctSq") int prjctSq);

    public List<String> selectQualifiedMembers(@Param("prjctSq") int prjctSq);

    public int interviewInsert(int prjctSq, String prjctInterv);
    
        // 특정 기업의 공고 리스트 조회

    public List<ProjectListEntity> myProjectList(String sortBy, Long entrprsSq);

        // 공고 검색
        public List<ProjectListEntity> searchProject(String searchTerm, String searchField);

        // 마감일 연장
    public void extendDeadline(Long jbpSq, int daysToAdd);

}
