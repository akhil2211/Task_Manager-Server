package com.example.Model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name="Comment")

public class Comment {
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @NonNull
    private String comm_body;

    @Column
    @NonNull
    private Timestamp comm_created_at;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @Column
    private Timestamp modified_at;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}

