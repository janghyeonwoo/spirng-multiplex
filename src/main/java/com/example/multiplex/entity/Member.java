package com.example.multiplex.entity;

import com.example.multiplex.dto.MemberDto;
import lombok.*;


import javax.persistence.Entity;
import javax.persistence.Id;

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

    public MemberDto getMemberDto(){
        return MemberDto.builder()
                .id(this.id)
                .name(this.name)
                .age(this.age)
                .build();
    }

}
