package com.example.Controller;

import com.example.Model.Project;
import com.example.Service.GmService;
import com.example.Service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/gm")

public class GmController {
    private final ProjectService projectService;

    private final GmService gmService;
    @Autowired
    public GmController(ProjectService projectService, GmService gmService) {
        this.projectService = projectService;
        this.gmService = gmService;
    }

    @GetMapping("/projectList")
    public ResponseEntity<Iterable<Project>> getAllProjects() {
        Iterable<Project> projects = gmService.getAllProjects();
        return ResponseEntity.ok(projects);
    }
        @PostMapping("/{projectId}/assign")
    public ResponseEntity<String> assignProjectToUser(@RequestBody Map<String, List<Integer>> assignRequest, @PathVariable Integer projectId){
        return new ResponseEntity<>(gmService.assignProject(projectId,assignRequest.get("userIds")), HttpStatus.OK);
    }
    @GetMapping("/organization/{organization_id}")
    public ResponseEntity<List<Map<String, Object>>> findProjectByOrganization(@PathVariable Integer organization_id) {
        return new ResponseEntity<>(gmService.getProjectByOrganization(organization_id), HttpStatus.OK);
    }
    @PostMapping("/{orgId}/create")
    public ResponseEntity<String> createProject(@RequestBody Project project, @PathVariable Integer orgId)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(gmService.createProject(project,orgId));
    }
    @GetMapping("/{projStatus}/ProjectStatus")
    public ResponseEntity<List<Project>> getProjectbyStatus(@PathVariable String projStatus) {
        return new ResponseEntity<>(gmService.getProjectbyStatus(projStatus),HttpStatus.OK);
    }
    @DeleteMapping("/removeMember/{user_id}/{project_id}")
    public String removeMember(@PathVariable Integer user_id,@PathVariable Integer project_id) {
        gmService.removeMember(user_id,project_id);
        return "Project Member Removed";

    }

}
