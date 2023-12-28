package com.example.Service;

import com.example.Model.*;
import com.example.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ProjectService {
    private final ProjectRepo projectRepo;
    private final ProjectUserRepo projectUserRepo;

    @Autowired
    public ProjectService(ProjectRepo projectRepo, OrganizationRepo organizationRepo, OrgProjectRepo orgProjectRepo, UserRepository userRepository, ProjectUserRepo projectUserRepo) {
        this.projectRepo = projectRepo;
        this.projectUserRepo = projectUserRepo;
    }

    public List<Map<String,Object>> getUserByProject(Integer projectId) {
        return projectUserRepo.getProjectUsers(projectId);
    }

    public Project getProjectById(Integer projectId) {
        return projectRepo.getProjectById(projectId);
    }

    public String editProjectStatus(Integer projectId, String newProjectStatus) {
        Project project= projectRepo.findById(projectId).orElse(null);
          assert project != null;
          project.setProject_status(newProjectStatus);
        project.setModified_at(Timestamp.valueOf(LocalDateTime.now()));
        projectRepo.save(project);
        return "Project Status Changed";
    }
    public List<Project> searchProject(String projectName) {
        return projectRepo.searchProjectByName(projectName);
    }

}

