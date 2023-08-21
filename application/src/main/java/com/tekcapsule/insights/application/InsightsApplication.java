package com.tekcapsule.insights.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.tekcapsule.insights","com.tekcapsule.core"})
public class InsightsApplication {
    public static void main(String[] args) {
        SpringApplication.run(InsightsApplication.class, args);
    }
}
