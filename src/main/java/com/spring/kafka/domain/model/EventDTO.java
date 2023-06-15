package com.spring.kafka.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class EventDTO {
    private Long eventId;
    private Long callDate;
    private Long streetAddressId;
    private String isDeleted;
}
