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
public class WeatherDTO {
    private Long id;
    private Long measuredDate;
    private int temperature;
    private int visibility;
    private int windSpeed;
    private Long cityAddressId;
    private String isDeleted;
}
