package com.example.Repository;

import com.example.Model.Project;
import com.example.Model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProjectRepo extends JpaRepository<Project,Integer> {

    @Query(value = "select* from project where org_id=?", nativeQuery = true)
    List<Project> findProjectByOrganization(Integer orgId);
    @Query(value="select* from project where id=?",nativeQuery = true)
    Project getProjectById(Integer projectId);

    @Query(value = "select* from project where project_status=? order by created_at DESC", nativeQuery = true)
    List<Project> findByProjectStatus(String projStatus);
     @Query(value="select * from project where project_name  LIKE CONCAT('%',?,'%');",nativeQuery = true )
     List<Project> searchProjectByName(String projectName);

}
