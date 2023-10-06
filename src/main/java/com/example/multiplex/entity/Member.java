package com.example.multiplex.entity;

import com.example.multiplex.converter.YearMonthConverter;
import com.example.multiplex.dto.MemberDto;
import lombok.*;


import javax.persistence.*;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Member {
    @Id
    private String id;
    private String name;
    private int age;

    @Convert(converter = YearMonthConverter.class)
    @Column(name = "rgdt")
    private YearMonth rgdt;

    public MemberDto getMemberDto() {
        return MemberDto.builder()
                .id(this.id)
                .name(this.name)
                .age(this.age)
                .rgdt(this.rgdt.format(DateTimeFormatter.ofPattern("yyyy-MM")))
                .build();
    }
}
