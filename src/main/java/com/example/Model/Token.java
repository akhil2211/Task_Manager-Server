package com.example.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

@Entity
@Table(name="Token")

public class Token {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String token;

    @Column
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    @Column
    private boolean expired;

    @Column
    private boolean revoked;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
