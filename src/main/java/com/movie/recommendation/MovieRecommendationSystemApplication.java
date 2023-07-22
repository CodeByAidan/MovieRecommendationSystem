package com.movie.recommendation;

import com.movie.recommendation.service.DataLoaderService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(
  {
    "com.movie.recommendation.service",
    "com.movie.recommendation.repository",
    "com.movie.recommendation.controller",
    "com.movie.recommendation.model",
  }
)
@SpringBootApplication
public class MovieRecommendationSystemApplication {

  @Bean
  public CommandLineRunner loadData(DataLoaderService dataLoaderService) {
    return args -> {
      dataLoaderService.loadMoviesData();
    };
  }

  public static void main(String[] args) {
    ApplicationContext applicationContext = SpringApplication.run(
      MovieRecommendationSystemApplication.class,
      args
    );

    for (String name : applicationContext.getBeanDefinitionNames()) {
      System.out.println(name);
    }
  }
}
