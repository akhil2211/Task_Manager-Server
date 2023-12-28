package com.example.Repository;

import com.example.Model.ProjectUser;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Map;

public interface ProjectUserRepo extends CrudRepository<ProjectUser,Integer> {
    @Query(value ="select u.*,r.roles from taskmanagerdb.project_user as pu \n" +
            " inner join taskmanagerdb.user as u on pu.user_id=u.id\n" +
            " inner join taskmanagerdb.role as r on r.id = u.role_id \n" +
            " where pu.project_id=?",nativeQuery = true )
    List<Map<String,Object>> getProjectUsers(Integer projectId);
    @Transactional
    @Modifying
    @Query(value="delete from taskmanagerdb.project_user where user_id=?1 and project_id=?2",nativeQuery = true)
    void removeMemberByUserId(Integer userId,Integer projectId);
}
