package com.example.RepoTests;

import com.example.Model.Task;
import com.example.Model.User;
import com.example.Repository.TaskRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class TaskRepoTest {
    @Autowired
    private TaskRepo taskRepo;

    @Test
    public void getByCodeTest(){
        Task task= taskRepo.findById(6).get();
        Assertions.assertThat(task.getId()).isEqualTo(6);
    }

    @Test
    public void getByProject(){
            List<Task> tasks=taskRepo.findByProject(1);
            Assertions.assertThat(tasks.size()).isGreaterThan(0);
        }

    @Test
    public void getByProjectStatus(){
        List<Task> tasks=taskRepo.findByTaskStatus("Active");
        Assertions.assertThat(tasks.size()).isGreaterThan(0);
    }
    @Test
    public void getByAssigneeTest(){
      List<Map<String,Object>> task=taskRepo.findByAssignee(2);
      Assertions.assertThat(task.size()).isGreaterThan(0);}

      @Test
        public void getByAssignedTest(){
            List<Task> task=taskRepo.findByAssigned(4);
            Assertions.assertThat(task.size()).isGreaterThan(0);

    }


    }

