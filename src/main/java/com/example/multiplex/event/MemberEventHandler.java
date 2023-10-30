package com.example.multiplex.event;

import com.example.multiplex.dto.MemberEventDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MemberEventHandler {

    @EventListener
    public void process(MemberEventDto memberEventDto){
        log.info("memberEvent !!!! {}", memberEventDto.getEventName());
    }
}
