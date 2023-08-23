package com.tekcapsule.insight.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.tekcapsule.insight","com.tekcapsule.core"})
public class InsightApplication {
    public static void main(String[] args) {
        SpringApplication.run(InsightApplication.class, args);
    }
}
