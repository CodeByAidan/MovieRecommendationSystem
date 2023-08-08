package com.movie.recommendation.controller;

import com.movie.recommendation.dto.MovieDTO;
import com.movie.recommendation.model.Movie;
import com.movie.recommendation.repo.MovieRepository;
import com.movie.recommendation.service.MovieService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Controller
public class MovieController {
    private static final Logger logger = LoggerFactory.getLogger(MovieController.class);
    private final MovieRepository movieRepository;
    private final MovieService movieService;
    private final Executor threadPoolExecutor;

    @Autowired
    public MovieController(MovieService movieService,
                           @Qualifier("customTaskExecutor") ThreadPoolTaskExecutor threadPoolExecutor,
                           MovieRepository movieRepository) {
        this.movieService = movieService;
        this.threadPoolExecutor = threadPoolExecutor;
        this.movieRepository = movieRepository;
    }

    @Async("customTaskExecutor")
    @PostMapping(path = "/loadmovie", produces = "application/json")
    public CompletableFuture<ResponseEntity<HttpStatus>> loadMovies() {
        // TODO: Extraordinary slow code
        logger.info("Loading movies...");

        CompletableFuture<ResponseEntity<HttpStatus>> futureResponse = new CompletableFuture<>();
        futureResponse.complete(ResponseEntity.status(HttpStatus.ACCEPTED).body(HttpStatus.ACCEPTED));

        CompletableFuture<List<Movie>> futureMovies = movieService.getMoviesAsync();
        futureMovies.thenAcceptAsync(
                movies -> {
                    saveMoviesInThreadPool(movies);
                    logger.info("Movies loaded successfully!");
                    futureResponse.complete(
                            ResponseEntity.status(HttpStatus.CREATED).body(HttpStatus.CREATED));
                });

        return futureResponse;
    }

    private void saveMoviesInThreadPool(List<Movie> movies) {
        movies.forEach(movie ->
                threadPoolExecutor.execute(() ->
                        movieRepository.save(movie)
                )
        );
    }

    @PostMapping(path = "/setMovie", produces = "application/json")
    @ResponseBody
    public ResponseEntity<HttpStatus> setMovie(@RequestBody MovieDTO movieDTO) {
        logger.info("Saving movie: {}", movieDTO.getTitle());
        Movie movie = new Movie(movieDTO.getTitle(), movieDTO.getGenre(), movieDTO.getMovieId());
        movieRepository.save(movie);

        return ResponseEntity.status(HttpStatus.CREATED).body(HttpStatus.CREATED);
    }

    @GetMapping(path = "/getMovies", produces = "application/json")
    @ResponseBody
    public Iterable<Movie> getAllMovies() {
        logger.info("Retrieving all movies...");
        return movieRepository.findAll();
    }

    @PostMapping(path = "/getMoviesByGenre", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Iterable<Movie>> getMoviesByGenre(@RequestBody MovieDTO movieDTO) {
         movieDTO.setGenre("Action|Adventure|Animation|Children's|Comedy|Crime|Documentary|Drama|Fantasy|Film-Noir|Horror|Musical|Mystery|Romance|Sci-Fi|Thriller|War|Western|(no genres listed)");
         List<String> genres = movieDTO.getGenres();
         logger.info("Genres: {}", genres);
         logger.info("Retrieving movies by genre...");
         Iterable<Movie> movies = movieRepository.findAll();

//         List<Movie> moviesByGenre = new ArrayList<>();
        Set<Movie> uniqueMoviesByGenre = new HashSet<>();

        for (Movie movie : movies) {
             for (String genre : genres) {
                 if (movie.getGenre().contains(genre)) {
                     uniqueMoviesByGenre.add(movie);
                     break;
                 }
             }
         }

        return ResponseEntity.status(HttpStatus.OK).body(uniqueMoviesByGenre);
    }
}
