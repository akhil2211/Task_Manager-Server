package com.example.Service;
import com.example.CustomContextHolder.AppContextHolder;
import com.example.Model.*;
import com.example.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class TaskService {
    private final TaskRepo taskRepo;
    private final TaskHistoryRepo taskHistoryRepo;

    private final CategoryRepo categoryRepo;

    private final PriorityRepo priorityRepo;

    private final AssignmentRepo assignmentRepo;
    private final TaskCategoryRepo taskCategoryRepo;
    private final ProjectRepo projectRepo;

    private final UserRepository userRepository;

    private final TaskPriorityRepo taskPriorityRepo;

    @Autowired

    public TaskService(TaskRepo taskRepo, ProjectRepo projectRepo, CategoryRepo categoryRepo, TaskCategoryRepo taskCategoryRepo, PriorityRepo priorityRepo, TaskPriorityRepo taskPriorityRepo, AssignmentRepo assignmentRepo, UserRepository userRepository, TaskHistoryRepo taskHistoryRepo, CategoryRepo categoryRepo1, PriorityRepo priorityRepo1, AssignmentRepo assignmentRepo1, TaskCategoryRepo taskCategoryRepo1, ProjectRepo projectRepo1, UserRepository userRepository1, TaskPriorityRepo taskPriorityRepo1) {
        this.taskRepo = taskRepo;
        this.taskHistoryRepo = taskHistoryRepo;
        this.categoryRepo = categoryRepo;
        this.priorityRepo = priorityRepo;
        this.assignmentRepo = assignmentRepo1;
        this.taskCategoryRepo = taskCategoryRepo;
        this.projectRepo = projectRepo;
        this.userRepository = userRepository;
        this.taskPriorityRepo = taskPriorityRepo;
    }
    public String editTask(Integer newTaskId, Integer taskHolderId) {
        Integer currentUserId = AppContextHolder.getUserId();
        String currentUserRole=userRepository.getUserRole(currentUserId);
        String userRole=userRepository.getUserRole(taskHolderId);
        if(UserRoles.GM.toString().equals(currentUserRole)){

            if(UserRoles.GM.toString().equals(userRole)|| UserRoles.ADMIN.toString().equals(userRole)){
                return "GM Cannot assign task to GM or Admin !";
            }else {
                return changeTaskHolder(newTaskId,taskHolderId);
            }
        }else{
            if(UserRoles.PM.toString().equals(userRole) || UserRoles.GM.toString().equals(userRole) || UserRoles.ADMIN.toString().equals(userRole)){
                return "PM Cannot assign task to GM or Admin !";
            }else{
                return changeTaskHolder(newTaskId,taskHolderId);
            }
        }

    }
    private String changeTaskHolder(Integer newTaskId, Integer taskHolderId) {
        Task task= taskRepo.findById(newTaskId).orElse(null);
        User user=userRepository.findById(taskHolderId).orElse(null);
        TaskHistory taskHistory=new TaskHistory();
        taskHistory.setTask(task);
        Assignment assignment=assignmentRepo.findAssignmentByTask(newTaskId).orElse(null);
        taskHistory.setPrevioususer(assignment.getAssigned_to());
        taskHistory.setUnassigned_at(Timestamp.valueOf(LocalDateTime.now()));
        taskHistoryRepo.save(taskHistory);
        assignment.setAssigned_to(user);
        assignmentRepo.save(assignment);
        return "Task Holder Edited";
    }

    public String createTask(TaskRequest task) {
        Integer currentUserId = AppContextHolder.getUserId();

        String currentUserRole=userRepository.getUserRole(currentUserId);
        String userRole=userRepository.getUserRole(task.getAssignedto());
        if(UserRoles.GM.toString().equals(currentUserRole)){

            if(UserRoles.GM.toString().equals(userRole)|| UserRoles.ADMIN.toString().equals(userRole)){
                return "GM Cannot assign task to GM or Admin !";
            }else {
                makeTask(task,currentUserId);
            }
        }else{
            if(UserRoles.PM.toString().equals(userRole) || UserRoles.GM.toString().equals(userRole) || UserRoles.ADMIN.toString().equals(userRole)){
                return "PM Cannot assign task to GM or Admin !";
            }else{
                makeTask(task,currentUserId);
            }
        }
        return "Task Created Successfully";
    }

    public void makeTask(TaskRequest task, Integer currentUserId) {
        Task taskdata = new Task();
        taskdata.setT_code(task.getT_code());
        taskdata.setT_title(task.getT_title());
        taskdata.setT_description(task.getT_description());
        taskdata.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        taskdata.setDuedate((Date) task.getDuedate());
        taskdata.setT_status(task.getT_status());
        Project project = projectRepo.findById(task.getProject_id()).orElse(null);
        taskdata.setProject(project);
        taskRepo.save(taskdata);

        Assignment assignment = new Assignment();
        User assignto = userRepository.findById(task.getAssignedto()).orElse(null);
        assignment.setAssigned_to(assignto);
        User assignee = userRepository.findById(currentUserId).orElse(null);

        assignment.setAssignee_id(assignee);
        assignment.setTask(taskdata);
        assignmentRepo.save(assignment);


        TaskCategory taskCategory = new TaskCategory();
        Category cat = categoryRepo.findById(task.getC_id()).orElse(null);
        taskCategory.setTask(taskdata);
        taskCategory.setCategory(cat);
        taskCategoryRepo.save(taskCategory);

        TaskPriority taskPriority = new TaskPriority();
        Priority prior = priorityRepo.findById(task.getPriority_id()).orElse(null);
        taskPriority.setTask(taskdata);
        taskPriority.setPriority(prior);
        taskPriorityRepo.save(taskPriority);
    }

    public Iterable<Task> getAllTasks() {
        return taskRepo.findAll();
    }

      public List<String> getTaskHistory(Integer taskId) {

      return taskHistoryRepo.getHistory(taskId);

    }

    public Task getTaskByTaskCode(String t_code) {
        return taskRepo.findByTaskCode(t_code);
    }

    public List<Task> getTaskByProject(Integer project_id) {
        return taskRepo.findByProject(project_id);
    }

    public List<Task> getTaskbyStatus(String tStatus) {
        return taskRepo.findByTaskStatus(tStatus);
    }

    public List<Map<String,Object>> getTaskbyAssignee() {
        Integer currentUserId = AppContextHolder.getUserId();
        return taskRepo.findByAssignee(currentUserId);
    }
    public List<Task> getTaskbyAssigned() {
        Integer currentUserId = AppContextHolder.getUserId();
        return taskRepo.findByAssigned(currentUserId);
    }

    public String editTaskStatus(Integer taskId, String newTaskStatus) {
        Task task=taskRepo.findById(taskId).orElse(null);
        task.setT_status(newTaskStatus);
        task.setModifiedAt(Timestamp.valueOf(LocalDateTime.now()));
        taskRepo.save(task);
        return "Task Status Changed";
    }

    public List<Map<String,Object>> searchTask(String taskName) {
        Integer currentUserId = AppContextHolder.getUserId();
        return taskRepo.searchTaskByName(currentUserId,taskName);
        }

    public List<Category> getAllTaskCategory() {
        return (List<Category>) categoryRepo.findAll();
    }

    public List<Priority> getAllTaskPriority() {
        return (List<Priority>) priorityRepo.findAll();
    }
}

