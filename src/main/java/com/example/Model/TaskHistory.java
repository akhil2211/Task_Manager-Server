package com.example.Model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name="task_history")

public class TaskHistory {
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @ManyToOne
    @JoinColumn(name="task_id")
    private Task task;

    @ManyToOne
    @JoinColumn(name="previoususer_id")
    private User previoususer;

    @Column
    @NonNull
    private Timestamp unassigned_at;

}
