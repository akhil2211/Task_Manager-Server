package com.example.Controller;

import com.example.Model.Project;
import com.example.Model.Task;
import com.example.Model.User;
import com.example.Service.ProjectService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/project")
public class ProjectController {

    private final ProjectService projectService;
    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }


    @GetMapping("/{project_id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Integer project_id) {
        Project project = projectService.getProjectById(project_id);

        if (project != null)
        {
            return ResponseEntity.ok(project);
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/searchProject")
    public ResponseEntity<List<Project>> searchProjectByName(@RequestParam("project") String projectName) {
        return new ResponseEntity<>(projectService.searchProject(projectName),HttpStatus.OK);
    }
    @GetMapping("/{projectId}/userlist")
    public ResponseEntity<List<Map<String,Object>>> findByProject(@PathVariable Integer projectId) {
        return new ResponseEntity<>(projectService.getUserByProject(projectId), HttpStatus.OK);
    }

    @PostMapping("/{projectId}/editProjectStatus")
    public  ResponseEntity<String> editProjectStatus(@PathVariable Integer projectId,@RequestBody Map<String,String> changeStatusRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.editProjectStatus(projectId,changeStatusRequest.get("newProjectStatus")));
    }

}
