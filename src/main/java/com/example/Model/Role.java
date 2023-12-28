package com.example.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name="Role")

public class Role {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @Enumerated(EnumType.STRING)
    private UserRoles roles;



}
