package jobplatform.fo.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jobplatform.fo.enterprise.domain.entity.JobPostingEntity;
import jobplatform.fo.enterprise.domain.entity.ProjectListEntity;
import jobplatform.fo.enterprise.domain.repository.ProjectRepository;
import jobplatform.fo.project.domain.ProjectDomain;
import jobplatform.fo.project.service.ProjectService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/project")
public class ProcjectController {

    private final ProjectRepository projectRepository;
 
    
    
    @Autowired
    private ProjectService projectService;


    ProcjectController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }



    // @GetMapping("")
    // public ResponseEntity<List<ProjectDomain>> getProjectList(@RequestBody HashMap<String, Object> params) {
    //     List<ProjectDomain> projectList = projectService.projectList(params);


    //     return ResponseEntity.ok(projectList);
    // }

    @GetMapping("")
    public List<ProjectDomain> getMethodName() {
        List<ProjectDomain> projectList = projectService.projectList();
        return projectList;
    }

    @GetMapping("/list")
    public ResponseEntity<List<ProjectListEntity>> myJobPostingList(
    @RequestParam(value = "sortBy", defaultValue = "prjctEndDate") String sortBy,
    @RequestParam(value = "entrprsSq", required = false) Long entrprsSq // 추가된 필터 파라미터
	) {
		List<ProjectListEntity> projectPostings = projectService.myProjectList(sortBy, entrprsSq);
        System.out.println(projectPostings + "나옴?");
        System.out.println("길이 같아? " + projectPostings.size());
		return ResponseEntity.ok(projectPostings);
	}
	
    

    @GetMapping("/{prjctSq}")
    public ProjectDomain getProjectDetail(@PathVariable("prjctSq") int prjctSq) {
        return projectService.projectRead(prjctSq);
    }

    @PostMapping("")
    public ResponseEntity<?> postMethodName(@RequestBody ProjectDomain projectEntity) {
        System.out.println("🚀 postMethodName 실행됨!"); 
  

        int result = projectService.projectInsert(projectEntity);

          if (result < 1) {
            return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
        }
        for (String time : projectEntity.getPrjctInterv()) {
            projectService.interviewInsert(projectEntity.getPrjctSq(), time);
        }

        // 2. check 값이 true면 이메일 전송
        if (projectEntity.isCheck()) {
            // projectService.selectEmailsForEssentialSkills(projectEntity.getPrjctSq());
            System.out.println("🚀 메일 실행됨!"); 
            //회원번호 가져오기
            List<String> re = projectService.selectQualifiedMembers(projectEntity.getPrjctSq());
            System.out.println("🚀 re" +  re); 
        }

        return ResponseEntity.ok("SUCCESS");
    }



    @PutMapping("")
    public ResponseEntity<?> putMethodName(@RequestBody ProjectDomain projectEntity) {
        int result = projectService.projectUpdate(projectEntity);
        
        if(result < 1){
            return new ResponseEntity<>("FAIL",HttpStatus.BAD_REQUEST);
        }

        return  new ResponseEntity<>("SUCCESS",HttpStatus.OK);
    }
    
    @DeleteMapping("/{prjctSq}")
    public ResponseEntity<?> deleteMethodName(@PathVariable("prjctSq") int prjctSq) {
        int result = projectService.projectDelete(prjctSq);
        
        if(result < 1){
            return new ResponseEntity<>("FAIL",HttpStatus.BAD_REQUEST);
        }

        return  new ResponseEntity<>("SUCCESS",HttpStatus.OK);
    }


    @PutMapping("/deadline/{prjctSq}")
	public ResponseEntity<String> extendDeadline(@PathVariable Long prjctSq) {
		int daysToAdd = 30; // 마감일 연장 기간을 30일로 설정
	
		try {
			projectService.extendDeadline(prjctSq, daysToAdd);
			return ResponseEntity.ok("마감일이 30일 연장되었습니다.");
		} catch (RuntimeException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 공고를 찾을 수 없습니다.");
		}
	}

    // 공고 검색 메소드
    @GetMapping("/search")
    public List<ProjectListEntity> searchJobPostings(
	@RequestParam(value = "searchTerm", required = false) String searchTerm,
	@RequestParam(value = "searchField", defaultValue = "prjctTtl_prjctCntnt") String searchField) {
		System.out.println("검색 잘 되니"+searchTerm);
        System.out.println("검색 잘 되니"+searchField);
        return projectService.searchProject(searchTerm, searchField);
    }
    
    
}
