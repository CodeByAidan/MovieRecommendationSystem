package com.movie.recommendation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class MovieRecommendationSystemApplication {
    private static final Logger logger = LoggerFactory.getLogger(MovieRecommendationSystemApplication.class);

    public static void main(String[] args) {
        logger.info("-------> Starting application...");
        SpringApplication.run(MovieRecommendationSystemApplication.class, args);
        logger.info("-------> Application started {}", System.currentTimeMillis());
    }
}
