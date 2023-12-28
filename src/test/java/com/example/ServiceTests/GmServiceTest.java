package com.example.ServiceTests;

import com.example.Model.*;
import com.example.Repository.*;
import com.example.Service.GmService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GmServiceTest {

    @Mock
    private ProjectRepo projectRepo;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ProjectUserRepo projectUserRepo;
    @Mock
    private OrgProjectRepo orgProjectRepo;
    @Mock
    private OrganizationRepo organizationRepo;

    @InjectMocks
    private GmService gmService;

    @Test
    void testCreateProject() {

        Project project = new Project();
        project.setProject_code("P01");
        project.setProject_name("ACA");
        project.setProject_description("First Project");
        project.setProject_status("Ongoing");
        project.setDue_date(Date.valueOf("2023-12-15"));

        Organization organization = new Organization();
        organization.setId(1);

        when(organizationRepo.findById(1)).thenReturn(java.util.Optional.of(organization));

        String result = gmService.createProject(project, 1);

        assertEquals("Project Created Successfully", result);
        verify(projectRepo, times(1)).save(any(Project.class));
        verify(orgProjectRepo, times(1)).save(any(OrgProject.class));
    }

    @Test
    void testAssignProject() {

        Project project = new Project();
        project.setProject_code("P01");
        project.setProject_name("ACA");
        project.setProject_description("First  Project");
        project.setProject_status("Ongoing");
        project.setDue_date(Date.valueOf("2023-12-15"));

        User user1 = new User();
        user1.setId(1);
        User user2 = new User();
        user2.setId(2);

        when(projectRepo.findById(1)).thenReturn(java.util.Optional.of(project));
        when(userRepository.findById(1)).thenReturn(java.util.Optional.of(user1));
        when(userRepository.findById(2)).thenReturn(java.util.Optional.of(user2));


        List<Integer> userIds = Arrays.asList(1, 2);
        String result = gmService.assignProject(1, userIds);

        assertEquals("Project Assignment to Users Successful!", result);
        verify(projectUserRepo, times(2)).save(any(ProjectUser.class));
    }

    @Test
    void testGetAllProjects() {

        Project project1 = new Project();
        project1.setId(1);
        Project project2 = new Project();
        project2.setId(2);
        when(projectRepo.findAll()).thenReturn(Arrays.asList(project1, project2));

        Iterable<Project> projects = gmService.getAllProjects();

        assertNotNull(projects);
        assertEquals(2, ((List<Project>) projects).size());
    }

    @Test
    void testGetProjectByStatus() {

        String status = "Ongoing";
        Project project1 = new Project();
        project1.setId(1);
        project1.setProject_status(status);
        Project project2 = new Project();
        project2.setId(2);
        project2.setProject_status(status);
        when(projectRepo.findByProjectStatus(status)).thenReturn(Arrays.asList(project1, project2));

        List<Project> projects = gmService.getProjectbyStatus(status);

        assertNotNull(projects);
        assertEquals(2, projects.size());
        for (Project project : projects) {
            assertEquals(status, project.getProject_status());
        }
    }

    @Test
    void testGetProjectByOrganization() {

        Integer orgId = 1;
        when(orgProjectRepo.findProjectByOrganization(orgId)).thenReturn(Arrays.asList(
                Map.of("ACA", "Project1", "projectStatus", "Pending"),
                Map.of("AOT", "Project2", "projectStatus", "Ongoing")
        ));


        List<Map<String, Object>> projects = gmService.getProjectByOrganization(orgId);

        assertNotNull(projects);
        assertEquals(2, projects.size());
    }
}
