package com.example.Model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name="assignment")

public class Assignment {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "assigned_to")
    private User assigned_to;


    @NonNull
    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private User assignee_id;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

}

