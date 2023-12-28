package com.example.Service;

import com.example.Model.Comment;
import com.example.Model.Task;
import com.example.Model.User;
import com.example.Repository.CommentRepo;
import com.example.Repository.TaskRepo;
import com.example.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
;import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service

public class CommentService {
    private final CommentRepo commentRepo;
    private final UserRepository userRepository;
    private final TaskRepo taskRepo;

    @Autowired
    public CommentService(CommentRepo commentRepo, UserRepository userRepository, TaskRepo taskRepo) {
        this.commentRepo = commentRepo;
        this.userRepository = userRepository;
        this.taskRepo = taskRepo;
    }
    public List<Comment> getallComments(){
        return (List<Comment>) commentRepo.findAll();
    }
    public Comment addComment(Integer userid, Integer taskid, Comment comment) {
        User currentuser= userRepository.findById(userid).orElse(null);
        Task currenttask= taskRepo.findById(taskid).orElse(null);
        if(currentuser!= null && currenttask!= null){
            Comment currentcomment= new Comment();
            currentcomment.setComm_body(comment.getComm_body());
            currentcomment.setComm_created_at(Timestamp.valueOf(LocalDateTime.now()));
            currentcomment.setUser(currentuser);
            currentcomment.setTask(currenttask);
            return commentRepo.save(currentcomment);
        }
        else {
            return null;
        }

    }
    public List<Comment> findTaskComments(Integer taskid) {
        return commentRepo.findTaskComment(taskid);
    }
   public void deleteComment(Integer id) {
        commentRepo.deleteById(id);
    }
}

