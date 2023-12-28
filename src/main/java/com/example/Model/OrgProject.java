package com.example.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="Organisation_Project")
public class OrgProject {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="org_id")
    private Organization org;

    @ManyToOne
    @JoinColumn(name="proj_id")
    private Project proj;


}
