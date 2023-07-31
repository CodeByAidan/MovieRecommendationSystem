package com.movie.recommendation;

import com.movie.recommendation.model.MovieTitle;
import com.movie.recommendation.service.MovieTitleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.ArrayList;
import java.util.List;

@EnableAsync
@SpringBootApplication
public class MovieRecommendationSystemApplication {

  private static final Logger logger = LoggerFactory.getLogger(MovieRecommendationSystemApplication.class);

  public static void main(String[] args) {
     logger.info("-------> Starting application...");
    SpringApplication.run(MovieRecommendationSystemApplication.class, args);
  }

}
