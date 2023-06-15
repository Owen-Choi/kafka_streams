package com.spring.kafka.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class DispatchDTO {
    private Long id;
    private Long arrivalDate;
    private Long dispatchDate;
    private Long dispatchOverDate;
    private int distance;
    private String driverAgent;
    private Long eventId;
    private Long fireStationId;
    private Long weatherEssentialId;
    private String isDeleted;
}
