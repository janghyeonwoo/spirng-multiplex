package com.example.multiplex.dto;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.YearMonth;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@RedisHash("member")
public class MemberDto implements Serializable {
    @Id
    private String id;
    private String name;
    private int age;
    private String rgdt;
}
