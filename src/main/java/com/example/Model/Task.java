package com.example.Model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "task")

public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @NonNull
    private String t_code;

    @Column
    @NonNull
    private String t_title;

    @Column
    private String t_description;

    @Column
    @NonNull
    private Date duedate;

    @Column
    @NonNull
    private Timestamp createdAt;

    @Column
    @NonNull
    private Timestamp modifiedAt;

    @Column
    @NonNull
    private String t_status;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

}
