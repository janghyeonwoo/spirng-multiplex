package com.example.multiplex.entity;

import com.example.multiplex.type.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@NoArgsConstructor
@Getter
@Entity
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memIdx;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_type")
    RoleType roleType;


}
