package com.example.Repository;

import com.example.Model.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Map;


public interface TaskRepo extends CrudRepository<Task, Integer> {
    @Query(value = "select* from task where t_code=?", nativeQuery = true)
    Task findByTaskCode(String t_code);
    @Query(value = "select* from task where project_id=?", nativeQuery = true)
    List<Task> findByProject(Integer projectId);
    @Query(value = "select* from task where t_status=? order by created_at DESC", nativeQuery = true)
    List<Task> findByTaskStatus(String tStatus);
    @Query(value = "select t.*,a.assigned_to,u.username,r.roles,c.c_name,u.firstname,u.lastname,p.type,proj.project_name from taskmanagerdb.assignment as a inner JOIN taskmanagerdb.task as t on a.task_id=t.id inner join taskmanagerdb.user as u on a.assigned_to = u.id inner join taskmanagerdb.role as r on u.role_id=r.id\n" +
            "inner join taskmanagerdb.task_category as tc on tc.task_id=t.id\n" +
            "inner join taskmanagerdb.category as c on c.id=tc.category_id\n" +
            "inner join taskmanagerdb.task_priority as tp on tp.task_id=t.id\n" +
            "inner join taskmanagerdb.priority as p on p.id=tp.priority_id\n" +
            "inner join taskmanagerdb.project as proj on proj.id=t.project_id"+
            " WHERE assignee_id=? ;", nativeQuery = true)
    List<Map<String,Object>> findByAssignee(Integer tAssignee);
    @Query(value="select t.* from assignment as a INNER JOIN task as t on a.task_id=t.id WHERE assigned_to=?",nativeQuery = true)
    List<Task> findByAssigned(Integer tAssigned);

    @Query(value=" select t.*,a.assigned_to,u.username,r.roles,c.c_name,u.firstname,u.lastname,p.type,proj.project_name from taskmanagerdb.assignment as a inner JOIN taskmanagerdb.task as t on a.task_id=t.id inner join taskmanagerdb.user as u on a.assigned_to = u.id inner join taskmanagerdb.role as r on u.role_id=r.id\n" +
            "inner join taskmanagerdb.task_category as tc on tc.task_id=t.id\n" +
            "inner join taskmanagerdb.category as c on c.id=tc.category_id\n" +
            "inner join taskmanagerdb.task_priority as tp on tp.task_id=t.id\n" +
            "inner join taskmanagerdb.priority as p on p.id=tp.priority_id\n" +
            "inner join taskmanagerdb.project as proj on proj.id=t.project_id\n" +
            "WHERE assignee_id=?1 and t.t_title LIKE CONCAT('%',?2,'%');",nativeQuery = true)
    List<Map<String,Object>> searchTaskByName(Integer tAssignee,String taskName);
}
