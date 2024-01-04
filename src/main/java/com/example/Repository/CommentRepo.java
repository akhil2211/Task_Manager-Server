package com.example.Repository;

import com.example.Model.Comment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepo extends CrudRepository<Comment,Integer> {
    @Query(value = "select c.*,u.firstname from taskmanagerdb.comment as c inner join user as u on u.id=c.user_id where task_id=?", nativeQuery = true)
    List<Comment> findTaskComment(Integer taskid);

}
