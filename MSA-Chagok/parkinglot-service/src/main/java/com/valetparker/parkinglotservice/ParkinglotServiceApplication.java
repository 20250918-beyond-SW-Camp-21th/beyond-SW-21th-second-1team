package com.valetparker.parkinglotservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ParkinglotServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParkinglotServiceApplication.class, args);
    }

}
