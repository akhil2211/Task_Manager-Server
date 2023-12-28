package com.example.ServiceTests;

import com.example.Model.Project;
import com.example.Repository.ProjectRepo;
import com.example.Repository.ProjectUserRepo;
import com.example.Service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class ProjectServiceTest {

    @Mock
    private ProjectRepo projectRepo;

    @Mock
    private ProjectUserRepo projectUserRepo;

    @InjectMocks
    private ProjectService projectService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    public void testGetUserByProject() {
//
//        List<String> users = new ArrayList<>();
//        users.add("Akhil");
//        users.add("Adiithyan");
//        when(projectUserRepo.getProjectUsers(anyInt())).thenReturn(users);
//
//        List<String> response = projectService.getUserByProject(1);
//
//        assertEquals(users, response);
//        verify(projectUserRepo, times(1)).getProjectUsers(1);
//    }

    @Test
    public void testGetProjectById() {

        Project project = new Project();
        when(projectRepo.getProjectById(anyInt())).thenReturn(project);

        Project resp = projectService.getProjectById(1);

        assertEquals(project, resp);
        verify(projectRepo, times(1)).getProjectById(1);
    }

    @Test
    public void testEditProjectStatus() {

        Project project = new Project();
        when(projectRepo.findById(anyInt())).thenReturn(java.util.Optional.of(project));

        String initialStatus = "InitialStatus";
        project.setProject_status(initialStatus);
        String newStatus = "NewStatus";
        String expectedResponse = "Project Status Changed";

        String actualResponse = projectService.editProjectStatus(1, newStatus);

        assertEquals(expectedResponse, actualResponse);

        assertEquals(newStatus, project.getProject_status());

        verify(projectRepo, times(1)).save(project);
    }
}
