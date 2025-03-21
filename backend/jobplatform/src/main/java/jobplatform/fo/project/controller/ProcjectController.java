package jobplatform.fo.project.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Producer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jobplatform.fo.project.domain.ProjectDomain;
import jobplatform.fo.project.service.ProjectService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/project")
public class ProcjectController {
 
    
    
    @Autowired
    private ProjectService projectService;

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
    

    @GetMapping("/{prjctSq}")
    public ProjectDomain getProjectDetail(@PathVariable("prjctSq") int prjctSq) {
        return projectService.projectRead(prjctSq);
    }

    @PostMapping("")
    public ResponseEntity<?> postMethodName(@RequestBody ProjectDomain projectEntity) {
        int result = projectService.projectInsert(projectEntity);
        
        if(result < 1){
            return new ResponseEntity<>("FAIL",HttpStatus.BAD_REQUEST);
        }

        return  new ResponseEntity<>("SUCCESS",HttpStatus.OK);
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
    public ResponseEntity<?> deleteMethodName(@RequestParam("prjctSq") int prjctSq) {
        int result = projectService.projectDelete(prjctSq);
        
        if(result < 1){
            return new ResponseEntity<>("FAIL",HttpStatus.BAD_REQUEST);
        }

        return  new ResponseEntity<>("SUCCESS",HttpStatus.OK);
    }
    
}
