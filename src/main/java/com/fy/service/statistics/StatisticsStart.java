package com.fy.service.statistics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableDiscoveryClient
@SpringBootApplication
public class StatisticsStart {

    public static void main(String[] args) {
        log.debug("--->start StatisticsStart ");
        SpringApplication.run(StatisticsStart.class, args);
        log.debug("--->start StatisticsStart success");
    }
}
