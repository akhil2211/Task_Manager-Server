package com.example.ServiceTests;

import com.example.Model.Comment;
import com.example.Model.Task;
import com.example.Model.User;
import com.example.Repository.CommentRepo;
import com.example.Repository.TaskRepo;
import com.example.Repository.UserRepository;
import com.example.Service.CommentService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class CommentServiceTest {

    @Mock
    private CommentRepo commentRepo;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskRepo taskRepo;

    @InjectMocks
    private CommentService commentService;

    @Test
    void testGetAllComments() {
        List<Comment> mockCommentList = new ArrayList<>();
        when(commentRepo.findAll()).thenReturn(mockCommentList);

        List<Comment> result = commentService.getallComments();
        assertEquals(mockCommentList, result);
    }

    @Test
    void testAddComment() {
        User user = new User();
        user.setId(1);
        Task task = new Task();
        task.setId(1);
        Comment newComment = new Comment();
        newComment.setComm_body("ThinkPalm comment");

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(taskRepo.findById(1)).thenReturn(Optional.of(task));
        when(commentRepo.save(any(Comment.class))).thenAnswer(i -> i.getArguments()[0]);

        Comment result = commentService.addComment(1, 1, newComment);
        assertEquals(newComment.getComm_body(), result.getComm_body());
    }

    @Test
    void testFindTaskComments() {
        List<Comment> commentList = new ArrayList<>();
        when(commentRepo.findTaskComment(1)).thenReturn(commentList);

        List<Comment> result = commentService.findTaskComments(1);
        assertEquals(commentList, result);
    }

    @Test
    void testDeleteComment() {
        commentService.deleteComment(1);
        verify(commentRepo,times(1)).deleteById(1);
    }
}