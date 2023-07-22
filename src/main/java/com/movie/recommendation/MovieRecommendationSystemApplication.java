package com.movie.recommendation;

import com.movie.recommendation.service.DataLoaderService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(
  {
    "com.movie.recommendation.service",
    "com.movie.recommendation.repository",
    "com.movie.recommendation.controller",
    "com.movie.recommendation.model",
  }
)
public class MovieRecommendationSystemApplication {

  public static void main(String[] args) {
    SpringApplication.run(MovieRecommendationSystemApplication.class, args);
  }

  @Bean
  public CommandLineRunner loadData(DataLoaderService dataLoaderService) {
    return args -> {
      dataLoaderService.loadMoviesData();
      // TODO: uncomment the following lines after you implement the corresponding methods:
      //  dataLoaderService.loadUserData();
      //  dataLoaderService.loadRatingsData();
      //  dataLoaderService.loadTagsData();
      //  dataLoaderService.loadGenomeScoresData();
      //  dataLoaderService.loadGenomeTagsData();
    };
  }
}
