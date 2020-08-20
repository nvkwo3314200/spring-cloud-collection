package com.peak.collection.retry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class EmailRetryApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmailRetryApplication.class, args);
    }
}
