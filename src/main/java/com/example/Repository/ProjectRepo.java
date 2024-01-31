package com.example.Repository;

import com.example.Model.Project;
import com.example.Model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Map;
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

     @Query(value="select t.*,u.firstname,u.lastname,p.project_name from task as t inner join assignment as a on t.id=a.task_id inner join user as u on u.id=a.assigned_to inner join project as p on p.id=t.project_id where t.t_status=?1 and project_id=?2  ",nativeQuery = true)
     List<Map<String, Object>> findProjectTasksByStatus( String tStatus,Integer projectId);

    @Query(value = "select * from project  where project_name = ?1 or project_code = ?2",nativeQuery = true)
    List<Project> existByNameOrCode(String projectName, String projectCode);
    @Query(value = "select * from project order by due_date ",nativeQuery = true)
    Iterable<Project> findAllProjects();
}
