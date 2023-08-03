package com.movie.recommendation.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ThreadPoolConfig {

    private static final Logger logger = LoggerFactory.getLogger(ThreadPoolConfig.class);

    @Bean(name = "customTaskExecutor")
    public ThreadPoolTaskExecutor customTaskExecutor() {
        logger.info("-------> Creating custom task executor...");

        int availableCores = Runtime.getRuntime().availableProcessors();

        int corePoolSize = ThreadPoolCalculator.calculateCorePoolSize(availableCores);
        int maxPoolSize = ThreadPoolCalculator.calculateMaxPoolSize(availableCores);
        int queueCapacity = ThreadPoolCalculator.calculateQueueCapacity(availableCores, 100); // Adjust 100 based on your workload

        logger.info("-------> Calculated corePoolSize: {}", corePoolSize);
        logger.info("-------> Calculated maxPoolSize: {}", maxPoolSize);
        logger.info("-------> Calculated queueCapacity: {}", queueCapacity);

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("custom-task-executor-");
        executor.initialize();

        logger.info("-------> Custom task executor created.");
        return executor;
    }
}
