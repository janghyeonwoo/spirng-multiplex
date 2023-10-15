package com.example.multiplex.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "code1")
@Entity
public class Code1 {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long cod1Idx;

    @Column(name = "code1_val")
    private String code1Val;

    @Column(name = "code1_name")
    private String code1Name;
}
