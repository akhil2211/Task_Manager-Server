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

    @GetMapping("/organization/{organization_id}")
    public ResponseEntity<List<Map<String, Object>>> findProjectByOrganization(@PathVariable Integer organization_id) {
        return new ResponseEntity<>(gmService.getProjectByOrganization(organization_id), HttpStatus.OK);
    }
    @PostMapping("/{orgId}/create")
    public ResponseEntity<String> createProject(@RequestBody Project project, @PathVariable Integer orgId)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(gmService.createProject(project,orgId));
    }
    @GetMapping("{t_status}/tasks/{project_id}")
     public ResponseEntity<List<Map<String,Object>>> findProjectTasksByStatus(@PathVariable String t_status,@PathVariable Integer project_id) {
        return new ResponseEntity<>(gmService.getProjectTasksByStatus(t_status,project_id),HttpStatus.OK);
    }


}
