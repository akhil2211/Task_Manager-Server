package com.example.Repository;

import com.example.Model.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Map;


public interface TaskRepo extends CrudRepository<Task, Integer> {

    @Query(value = "select * from task  where t_title = ?1 or t_code = ?2",nativeQuery = true)
    List<Task> existByTitleOrCode(String tTitle, String tCode);
    @Query(value = "select* from task where t_code=?", nativeQuery = true)
    Task findByTaskCode(String t_code);
    @Query(value = "select t.*,concat(u1.firstname,(' '),u1.lastname) as Assigned_To,concat(u2.firstname,(' '),u2.lastname) as Assigned_By,p.project_name" +
                   " from task as t inner join assignment as a on t.id=a.task_id inner join user as u1 on u1.id=a.assigned_to inner join user as u2 on u2.id=a.assignee_id " +
                   "inner join project as p on p.id=t.project_id where project_id=?", nativeQuery = true)
    List<Map<String,Object>> findByProject(Integer projectId);

    @Query(value = "select t.*,c.c_name,p.type,proj.project_name,proj.client,u.firstname,u.lastname from taskmanagerdb.assignment as a inner JOIN taskmanagerdb.task as t on a.task_id=t.id inner join taskmanagerdb.user as u on a.assigned_to = u.id \n" +
            "          inner join taskmanagerdb.task_category as tc on tc.task_id=t.id\n" +
            "          inner join taskmanagerdb.category as c on c.id=tc.category_id\n" +
            "          inner join taskmanagerdb.task_priority as tp on tp.task_id=t.id\n" +
            "          inner join taskmanagerdb.priority as p on p.id=tp.priority_id\n" +
            "          inner join taskmanagerdb.project as proj on proj.id=t.project_id\n" +
            "          WHERE assigned_to=?1 and t.t_status=?2 order by t.created_at DESC", nativeQuery = true)
    List<Map<String,Object>> findMyTasksByStatus(Integer assigned_to,String tStatus);
    @Query(value = "select t.*,c.c_name,p.type,proj.project_name,proj.client,u.firstname,u.lastname from taskmanagerdb.assignment as a inner JOIN taskmanagerdb.task as t on a.task_id=t.id inner join taskmanagerdb.user as u on a.assigned_to = u.id \n" +
            "          inner join taskmanagerdb.task_category as tc on tc.task_id=t.id\n" +
            "          inner join taskmanagerdb.category as c on c.id=tc.category_id\n" +
            "          inner join taskmanagerdb.task_priority as tp on tp.task_id=t.id\n" +
            "          inner join taskmanagerdb.priority as p on p.id=tp.priority_id\n" +
            "          inner join taskmanagerdb.project as proj on proj.id=t.project_id\n" +
            "          WHERE assignee_id=?1 and t.t_status=?2 order by t.created_at DESC", nativeQuery = true)
    List<Map<String,Object>> findAssignedByTaskStatus(Integer assignee_id,String tStatus);
    @Query(value = "select t.*,a.assigned_to,u.username,r.roles,c.c_name,u.firstname,u.lastname,p.type,proj.project_name,proj.client from taskmanagerdb.assignment as a inner JOIN taskmanagerdb.task as t on a.task_id=t.id inner join taskmanagerdb.user as u on a.assigned_to = u.id inner join taskmanagerdb.role as r on u.role_id=r.id\n" +
            "inner join taskmanagerdb.task_category as tc on tc.task_id=t.id\n" +
            "inner join taskmanagerdb.category as c on c.id=tc.category_id\n" +
            "inner join taskmanagerdb.task_priority as tp on tp.task_id=t.id\n" +
            "inner join taskmanagerdb.priority as p on p.id=tp.priority_id\n" +
            "inner join taskmanagerdb.project as proj on proj.id=t.project_id"+
            " WHERE assignee_id=? order by t.created_at DESC ;", nativeQuery = true)
    List<Map<String,Object>> findByAssignee(Integer tAssignee);
    @Query(value="select t.*,c.c_name,p.type,proj.project_name,proj.client,a.assignee_id,concat(u.firstname,' ',u.lastname) as full_name from taskmanagerdb.assignment as a inner JOIN taskmanagerdb.task as t on a.task_id=t.id \n" +
            "            inner join taskmanagerdb.task_category as tc on tc.task_id=t.id\n" +
            "            inner join taskmanagerdb.category as c on c.id=tc.category_id\n" +
            "            inner join taskmanagerdb.task_priority as tp on tp.task_id=t.id\n" +
            "            inner join taskmanagerdb.priority as p on p.id=tp.priority_id\n" +
            "            inner join taskmanagerdb.project as proj on proj.id=t.project_id\n" +
            "            inner join taskmanagerdb.user as u on a.assignee_id=u.id\n" +
            "            WHERE assigned_to=? order by t.created_at DESC",nativeQuery = true)
    List<Map<String,Object>> findByAssigned(Integer tAssigned);

    @Query(value=" select t.*,a.assigned_to,u.username,r.roles,c.c_name,u.firstname,u.lastname,p.type,proj.project_name " +
            "from taskmanagerdb.assignment as a inner JOIN taskmanagerdb.task as t on a.task_id=t.id inner join taskmanagerdb.user as u on a.assigned_to = u.id inner join taskmanagerdb.role as r on u.role_id=r.id\n" +
            "inner join taskmanagerdb.task_category as tc on tc.task_id=t.id\n" +
            "inner join taskmanagerdb.category as c on c.id=tc.category_id\n" +
            "inner join taskmanagerdb.task_priority as tp on tp.task_id=t.id\n" +
            "inner join taskmanagerdb.priority as p on p.id=tp.priority_id\n" +
            "inner join taskmanagerdb.project as proj on proj.id=t.project_id\n" +
            "WHERE assignee_id=?1 and t.t_title LIKE CONCAT('%',?2,'%');",nativeQuery = true)
    List<Map<String,Object>> searchTaskByName(Integer tAssignee,String taskName);

    @Query(value = "select t.*,c.c_name,p.type,proj.project_name,proj.client,u.firstname,u.lastname from taskmanagerdb.assignment as a inner JOIN taskmanagerdb.task as t on a.task_id=t.id inner join taskmanagerdb.user as u on a.assigned_to = u.id \n" +
            "                inner join taskmanagerdb.task_category as tc on tc.task_id=t.id\n" +
            "                inner join taskmanagerdb.category as c on c.id=tc.category_id\n" +
            "                inner join taskmanagerdb.task_priority as tp on tp.task_id=t.id\n" +
            "                inner join taskmanagerdb.priority as p on p.id=tp.priority_id\n" +
            "                inner join taskmanagerdb.project as proj on proj.id=t.project_id\n" +
            "                WHERE assignee_id=?1 and concat(u.firstname,' ',u.lastname) =?2", nativeQuery = true)
    List<Map<String, Object>> findAssignedTasksByName(Integer currentUserId, String assignedTo);
}
