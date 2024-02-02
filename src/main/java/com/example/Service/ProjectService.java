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
    private final UserRepository userRepository;

    @Autowired
    public ProjectService(ProjectRepo projectRepo, OrganizationRepo organizationRepo, OrgProjectRepo orgProjectRepo, UserRepository userRepository, ProjectUserRepo projectUserRepo) {
        this.projectRepo = projectRepo;
        this.projectUserRepo = projectUserRepo;
        this.userRepository = userRepository;
    }

    public List<Map<String,Object>> getUserByProject(Integer projectId, Integer currentUserId) {

        return projectUserRepo.getProjectUsers(projectId,currentUserId);
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
    public Iterable<Project> getAllProjects() {
        return projectRepo.findAllProjects();
    }
    public String assignProject(Integer projectId, List<Integer> userIds) {
        Project project = projectRepo.findById(projectId).orElse(null);
        if (project != null) {
            for (Integer userId : userIds) {
                String role = userRepository.getUserRole(userId);
                if (UserRoles.GM.toString().equals(role) || UserRoles.ADMIN.toString().equals(role)) {
                    return "Cannot project assign to GM or Admin!";
                } else {
                    User user = userRepository.findById(userId).orElse(null);

                    ProjectUser projectUser = new ProjectUser();
                    projectUser.setProject(project);
                    projectUser.setUser(user);
                    projectUser.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
                    projectUserRepo.save(projectUser);
                }
            }
            return "Project Assignment to Users Successful!";
        }
        else{
            return "No Project Found !";
        }
    }
    public List<Project> getProjectbyStatus(String projStatus) {
        return projectRepo.findByProjectStatus(projStatus);
    }
    public void removeMember(Integer user_id,Integer project_id) {
        projectUserRepo.removeMemberByUserId(user_id,project_id);
    }
}

