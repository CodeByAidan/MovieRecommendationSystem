package com.movie.recommendation.controller;

import com.movie.recommendation.dto.MovieGenreDTO;
import com.movie.recommendation.model.MovieGenre;
import com.movie.recommendation.repo.MovieGenreRepository;
import com.movie.recommendation.service.MovieGenreService;

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
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Controller()
public class MovieGenreController {
    private static final Logger logger = LoggerFactory.getLogger(MovieGenreController.class);
    private final MovieGenreRepository movieGenreRepository;
    private final MovieGenreService movieGenreService;
    private final Executor threadPoolExecutor;

    @Autowired
    public MovieGenreController(MovieGenreService movieGenreService,
                                @Qualifier("customTaskExecutor") ThreadPoolTaskExecutor threadPoolExecutor,
                                MovieGenreRepository movieGenreRepository) {
        this.movieGenreService = movieGenreService;
        this.threadPoolExecutor = threadPoolExecutor;
        this.movieGenreRepository = movieGenreRepository;
    }

    /**
     * Asynchronously loads movie genres.
     *
     * @return A CompletableFuture with a "pending" response initially, which will be completed
     *     later.
     */
    @Async("customTaskExecutor")
    @PostMapping(path = "/loadgenre", produces = "application/json")
    public CompletableFuture<ResponseEntity<HttpStatus>> loadMovieGenres() {
        // TODO: Extraordinary slow code
        logger.info("-------> Loading movie genres...");

        // Return a CompletableFuture with a "pending" response initially, which will be completed later.
        CompletableFuture<ResponseEntity<HttpStatus>> futureResponse = new CompletableFuture<>();
        futureResponse.complete(ResponseEntity.status(HttpStatus.ACCEPTED).body(HttpStatus.ACCEPTED));

        // Use the thread pool to parallelize the saving process
        CompletableFuture<List<MovieGenre>> futureGenres = movieGenreService.getGenresAsync();
        futureGenres.thenAcceptAsync(
                genres -> {
                    saveGenresInThreadPool(genres);
                    logger.info("-------> Movie genres saved successfully.");
                    futureResponse.complete(
                            ResponseEntity.status(HttpStatus.CREATED).body(HttpStatus.CREATED));
                });

        return futureResponse;
    }

    // Helper method to save movie genres in a thread pool
    private void saveGenresInThreadPool(List<MovieGenre> genres) {
        genres.forEach(genre ->
                threadPoolExecutor.execute(() ->
                        movieGenreRepository.save(genre)
                )
        );
    }

    /**
     * Saves a movie genre.
     *
     * @param movieGenreDTO The movie genre DTO to save.
     * @return HTTP status code 201 (Created) if the movie genre was saved successfully.
     */
    @GetMapping(path="/setGenre", produces = "application/json")
    public ResponseEntity<HttpStatus> setGenre(MovieGenreDTO movieGenreDTO) {
        logger.info("-------> Setting movie genre...");
        MovieGenre movieGenre = new MovieGenre();
        movieGenre.setGenre(movieGenreDTO.getGenre());
        movieGenre.setMovieId(movieGenreDTO.getMovieId());
        movieGenreRepository.save(movieGenre);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Retrieves all movie genres.
     *
     * @return A list of all movie genres.
     */
    @GetMapping(path="/getGenres", produces = "application/json")
    @ResponseBody
    public Iterable<MovieGenre> getAllGenres() {
        logger.info("-------> Retrieving all movie genres...");
        return movieGenreRepository.findAll();
    }
}
