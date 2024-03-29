package com.example.Controller;

import com.example.Model.*;
import com.example.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/project/task")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService)
    {
        this.taskService = taskService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createTask(@RequestBody TaskRequest taskRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(taskRequest));
    }
    @GetMapping("/list")
    public ResponseEntity<Iterable<Task>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }
    @GetMapping("/{t_code}")
    public ResponseEntity<Task> getTaskByTaskCode(@PathVariable String t_code) {
        Task task = taskService.getTaskByTaskCode(t_code);

        if (task != null)
        {
            return ResponseEntity.ok(task);
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{project_id}/task")
    public ResponseEntity <List<Map<String,Object>>> getTaskByProject(@PathVariable Integer project_id) {
        return new ResponseEntity<>(taskService.getTaskByProject(project_id),HttpStatus.OK);
    }
    @GetMapping("/{t_status}/assignedTaskStatus")
    public ResponseEntity<List<Map<String,Object>>> getAssignedTaskbyStatus(@PathVariable String t_status) {
        return new ResponseEntity<>(taskService.getAssignedTaskbyStatus(t_status),HttpStatus.OK);
    }
    @GetMapping("/{t_status}/myTaskStatus")
    public ResponseEntity<List<Map<String,Object>>> getMyTasksByStatus(@PathVariable String t_status) {
        return new ResponseEntity<>(taskService.getMyTasksByStatus(t_status),HttpStatus.OK);
    }

    @GetMapping("/filterTasks/{assigned_to}")
    public ResponseEntity<List<Map<String,Object>>> getAssignedTasksByName(@PathVariable String assigned_to) {
        return new ResponseEntity<>(taskService.getAssignedTasksByName(assigned_to),HttpStatus.OK);
    }

    @GetMapping("/AssignedTasks")
    public ResponseEntity<List<Map<String,Object>>> getTaskbyAssignee() {
        return new ResponseEntity<>(taskService.getTaskbyAssignee(),HttpStatus.OK);
    }
    @GetMapping("/category")
    public ResponseEntity<List<Category>> getAllTaskCategory() {
        return new ResponseEntity<>(taskService.getAllTaskCategory(),HttpStatus.OK);
    }
    @GetMapping("/priority")
    public ResponseEntity<List<Priority>> getAllTaskPriority() {
        return new ResponseEntity<>(taskService.getAllTaskPriority(),HttpStatus.OK);
    }
    @GetMapping("/MyTasks")
    public ResponseEntity <List<Map<String,Object>>> getTaskbyAssigned() {
        return new ResponseEntity<>(taskService.getTaskbyAssigned(),HttpStatus.OK);
    }
    @GetMapping("/searchTask")
    public ResponseEntity<List<Map<String,Object>>> searchTaskByName(@RequestParam("task") String taskName) {
        return new ResponseEntity<>(taskService.searchTask(taskName),HttpStatus.OK);
    }
    @GetMapping("/{taskId}/taskHistory")
    public ResponseEntity <List<Map<String,Object>>> getTaskHistory(@PathVariable Integer taskId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.getTaskHistory(taskId));
    }
    @PostMapping("/{taskId}/editTask")
    public ResponseEntity<String> editTask(@PathVariable Integer taskId, @RequestBody Map<String,Integer> editRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.editTask(taskId,editRequest.get("newTaskHolderId")));
    }
    @PostMapping("/{taskId}/editTaskStatus")
    public  ResponseEntity<String> editTaskStatus(@PathVariable Integer taskId,@RequestBody Map<String,String> changeRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.editTaskStatus(taskId,changeRequest.get("newTaskStatus")));
    }


}
