package com.example.multiplex.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Getter
public class MemberEventDto {
    private String eventName;
    private String ss;
}
