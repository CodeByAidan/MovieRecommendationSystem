package com.movie.recommendation.controller;

import com.movie.recommendation.model.Movie;
import com.movie.recommendation.repo.MovieGenreRepository;
import com.movie.recommendation.repo.MovieRepository;
import com.movie.recommendation.repo.MovieTitleRepository;
import com.movie.recommendation.service.MovieService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.sql.DataSource;

@Controller
public class MovieDefaultsController {
    private static final Logger logger = LoggerFactory.getLogger(MovieDefaultsController.class);
    private final MovieController movieController;
    private final MovieRepository movieRepo;
    private final MovieTitleRepository movieTitleRepo;
    private final MovieGenreRepository movieGenreRepo;
    private final DataSource dataSource;
    private final MovieService movieService;

    @Autowired
    public MovieDefaultsController(MovieController movieController,
                                   MovieRepository movieRepo,
                                   MovieTitleRepository movieTitleRepo,
                                   MovieGenreRepository movieGenreRepo,
                                   DataSource dataSource,
                                   MovieService movieService) {
        this.movieController = movieController;
        this.movieRepo = movieRepo;
        this.movieTitleRepo = movieTitleRepo;
        this.movieGenreRepo = movieGenreRepo;
        this.dataSource = dataSource;
        this.movieService = movieService;
    }

    @Async("customTaskExecutor")
    @PostMapping("/loadDefaultMovies")
    public CompletableFuture<ResponseEntity<HttpStatus>> loadDefaultMovies() {
        logger.info("-------> Loading default movies...");
        logger.info("-------> Checking if default movies are already loaded...");

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        Long sqlMovieCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM movie", Long.class);
        Long sqlMovieTitleCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM movie_title", Long.class);
        Long sqlMovieGenreCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM movie_genre", Long.class);

        if (sqlMovieTitleCount != null && sqlMovieTitleCount == 62423L &&
                sqlMovieGenreCount != null && sqlMovieGenreCount == 62423L &&
                sqlMovieCount != null && sqlMovieCount == 62423L) {
            logger.info("-------> Your movie_titles and movie_genres tables are already loaded...");
            return CompletableFuture.completedFuture(new ResponseEntity<>(HttpStatus.OK));
        } else {
            logger.info("-------> Default movies are not loaded, or the data is corrupted. " +
                    "Removing all data from the movie, movie_titles, and movie_genres tables, and loading default movies...");
            movieRepo.deleteAll();
            movieTitleRepo.deleteAll();
            movieGenreRepo.deleteAll();
            movieController.loadMovies();
            logger.info("-------> Default movies loaded successfully...");
            List<Movie> movies = movieRepo.findAll();

            // TODO: how the hell do i get this under 60 seconds?
            logger.info("-------> Retrieved all movies from movie repository. " +
                    "Now adding movies to movie, movie_title, and movie_genre tables in the database...");
            logger.info("-------> Please wait, this can take up to 60 minutes to complete...");
            movieService.saveToMovieTitleTableAsync(movies);
            return CompletableFuture.completedFuture(new ResponseEntity<>(HttpStatus.CREATED));
        }
    }
}
