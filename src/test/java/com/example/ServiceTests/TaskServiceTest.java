package com.example.ServiceTests;

import com.example.CustomContextHolder.AppContextHolder;
import com.example.Model.*;
import com.example.Repository.*;
import com.example.Service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class TaskServiceTest {

    @Mock
    private TaskRepo taskRepo;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskHistoryRepo taskHistoryRepo;

    @Mock
    private ProjectRepo projectRepo;

    @Mock
    private CategoryRepo categoryRepo;

    @Mock
    private TaskCategoryRepo taskCategoryRepo;

    @Mock
    private PriorityRepo priorityRepo;

    @Mock
    private AssignmentRepo assignmentRepo;

    @Mock
    private TaskPriorityRepo taskPriorityRepo;

    @InjectMocks
    private TaskService taskService;

    private User user;
    private Task task;
    private TaskRequest taskRequest;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1);
        user.setUsername("testUser");

        task = new Task();
        task.setId(1);
        task.setT_code("T001");
        task.setT_title("Test Task");
        task.setT_description("Test Description");

        taskRequest = new TaskRequest();
        taskRequest.setT_code("T002");
        taskRequest.setT_title("New Task");
        taskRequest.setT_description("New Description");
        taskRequest.setDuedate(null);
        taskRequest.setT_status("New");
        taskRequest.setProject_id(1);
        taskRequest.setAssignedto(2);
        taskRequest.setC_id(1);
        taskRequest.setPriority_id(1);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testMakeTask() {

        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setT_code("T01");
        taskRequest.setT_title("Task");
        taskRequest.setT_description("Task Desc");
        taskRequest.setDuedate(new Date(2023,12,8));
        taskRequest.setT_status("Ongoing");
        taskRequest.setProject_id(1);
        taskRequest.setC_id(1);
        taskRequest.setPriority_id(1);
        taskRequest.setAssignedto(4);

        when(projectRepo.findById(taskRequest.getProject_id())).thenReturn(Optional.of(new Project()));
        when(userRepository.findById(taskRequest.getAssignedto())).thenReturn(Optional.of(new User()));
        when(userRepository.findById(1)).thenReturn(Optional.of(new User()));

        taskService.makeTask(taskRequest, 1);

        verify(taskRepo, times(1)).save(any(Task.class));
        verify(assignmentRepo, times(1)).save(any(Assignment.class));
        verify(taskCategoryRepo, times(1)).save(any(TaskCategory.class));
        verify(taskPriorityRepo, times(1)).save(any(TaskPriority.class));
    }


    @Test
    void testGetAllTasks() {
        List<Task> mockTaskList = new ArrayList<>();
        when(taskRepo.findAll()).thenReturn(mockTaskList);

        Iterable<Task> result = taskService.getAllTasks();
        assertEquals(mockTaskList, result);
    }

    @Test
    void testEditTask() {

        when(userRepository.getUserRole(1)).thenReturn("GM");
        when(userRepository.getUserRole(2)).thenReturn("Developer");
        when(taskRepo.findById(1)).thenReturn(Optional.of(task));
        when(userRepository.findById(2)).thenReturn(Optional.of(new User()));
        when(assignmentRepo.findAssignmentByTask(1)).thenReturn(Optional.of(new Assignment()));

        String result = taskService.editTask(1, 2);
        assertEquals("Task Holder Edited", result);
    }

    @Test
    void testGetTaskHistory() {
        List<String> mockHistory = new ArrayList<>();
        when(taskHistoryRepo.getHistory(1)).thenReturn(mockHistory);

        List<String> result = taskService.getTaskHistory(1);
        assertEquals(mockHistory, result);
    }

    @Test
    void testGetTaskByTaskCode() {
        when(taskRepo.findByTaskCode("T001")).thenReturn(task);

        Task result = taskService.getTaskByTaskCode("T001");
        assertEquals(task, result);
    }

    @Test
    void testGetTaskByProject() {
        List<Task> mockTaskList = new ArrayList<>();
        when(taskRepo.findByProject(1)).thenReturn(mockTaskList);

        List<Task> result = taskService.getTaskByProject(1);
        assertEquals(mockTaskList, result);
    }

    @Test
    void testGetTaskbyStatus() {
        List<Task> mockTaskList = new ArrayList<>();
        when(taskRepo.findByTaskStatus("New")).thenReturn(mockTaskList);

        List<Task> result = taskService.getTaskbyStatus("New");
        assertEquals(mockTaskList, result);
    }

    @Test
    void testGetTaskbyAssignee() {
        User user1=new User();
        MockedStatic<AppContextHolder> securityContext=mockStatic(AppContextHolder.class);
        when(AppContextHolder.getUserId()).thenReturn(user1.getId());
        when(taskRepo.findByAssignee(1)).thenReturn(new ArrayList<>());
        List<Map<String,Object>> result = taskService.getTaskbyAssignee();
        assertEquals(new ArrayList<>(), result);
    }


    @Test
    void testGetTaskbyAssigned() {
        User user = new User();
        try (MockedStatic mocked = mockStatic(AppContextHolder.class)) {
            when(AppContextHolder.getUserId()).thenReturn(user.getId());
            when(taskRepo.findByAssigned(1)).thenReturn(new ArrayList<>());
            List<Task> result = taskService.getTaskbyAssigned();
            assertEquals(new ArrayList<>(), result);
        }
    }

    @Test
    void testEditTaskStatus() {
        when(taskRepo.findById(1)).thenReturn(Optional.of(task));
        String result = taskService.editTaskStatus(1, "Completed");
        assertEquals("Task Status Changed", result);
    }
}