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
@Table(name = "code2")
@Entity
public class Code2 {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long cod2Idx;


    @JoinColumn(name = "code1_val", referencedColumnName = "code1_val")
    @ManyToOne(fetch = FetchType.LAZY)
    private Code1 code1;

    @Column(name = "code2_val")
    private String code2Val;

    @Column(name = "code2_name")
    private String code2Name;
}
