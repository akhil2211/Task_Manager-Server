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
@Table(name="project")

public class Project {
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @NonNull
    private String project_code;

    @Column
    @NonNull
    private String project_name;

    @Column
    @NonNull
    private String project_description;

    @Column
    @NonNull
    private Timestamp created_at;

    @Column
    private Date due_date;

    @Column
    private Timestamp modified_at;

    @Column
    @NonNull
    private String project_status;
}
