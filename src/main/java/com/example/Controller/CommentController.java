package com.example.Controller;

import com.example.Model.Comment;
import com.example.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comments")

public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    @PostMapping("/add/{userid}/{taskid}")
    public Comment addComment(@PathVariable Integer userid, @PathVariable Integer taskid, @RequestBody Comment comment) {
        return commentService.addComment(userid,taskid,comment);
    }
    @GetMapping("/all/{taskid}")
    public List<Comment> findTaskComments(@PathVariable Integer taskid) {
        return commentService.findTaskComments(taskid);
    }
    @DeleteMapping("/delete/{id}")
    public String deleteComment(@PathVariable Integer id) {
        commentService.deleteComment(id);
        return "Comment Deleted";

    }

}