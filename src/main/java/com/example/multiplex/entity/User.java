package com.example.multiplex.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Entity
@Table(name = "user2")
public class User {
    @Id
    private Long idx;
    private String name;
    private String id;
    private String password;
    private String role;
}
