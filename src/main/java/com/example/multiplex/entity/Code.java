package com.example.multiplex.entity;

import com.example.multiplex.convert.AdStatusConvert;
import com.example.multiplex.type.AdStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Code {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = AdStatusConvert.class)
    AdStatus adStatus;
}
