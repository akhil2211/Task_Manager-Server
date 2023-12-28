package com.example.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name="Task_Priority")
public class TaskPriority {
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name="task_id")
    private Task task;

    @ManyToOne
    @JoinColumn(name="priority_id")
    private Priority priority;

}