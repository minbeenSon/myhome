package com.study.practice.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String title;
    String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}
