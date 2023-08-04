package com.movie.recommendation.controller;

import com.movie.recommendation.dto.MovieTitleDTO;
import com.movie.recommendation.model.MovieTitle;
import com.movie.recommendation.repo.MovieTitleRepository;
import com.movie.recommendation.service.MovieTitleService;

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

@Controller
public class MovieTitleController {
    private static final Logger logger = LoggerFactory.getLogger(MovieTitleController.class);
    private final MovieTitleRepository movieTitleRepository;
    private final MovieTitleService movieTitleService;
    private final Executor threadPoolExecutor;

    @Autowired
    public MovieTitleController(MovieTitleService movieTitleService,
                                @Qualifier("customTaskExecutor") ThreadPoolTaskExecutor threadPoolExecutor,
                                MovieTitleRepository movieTitleRepository) {
        this.movieTitleService = movieTitleService;
        this.threadPoolExecutor = threadPoolExecutor;
        this.movieTitleRepository = movieTitleRepository;
    }

    /**
     * Asynchronously loads movie titles.
     *
     * @return A CompletableFuture with a "pending" response initially, which will be completed
     *     later.
     */
    @Async("customTaskExecutor")
    @PostMapping(path = "/loadtitle", produces = "application/json")
    public CompletableFuture<ResponseEntity<HttpStatus>> loadMovieTitles() {
        // TODO: Extraordinary slow code
        logger.info("-------> Loading movie titles...");

        // Return a CompletableFuture with a "pending" response initially, which will be completed later.
        CompletableFuture<ResponseEntity<HttpStatus>> futureResponse = new CompletableFuture<>();
        futureResponse.complete(ResponseEntity.status(HttpStatus.ACCEPTED).body(HttpStatus.ACCEPTED));

        // Use the thread pool to parallelize the saving process
        CompletableFuture<List<MovieTitle>> futureTitles = movieTitleService.getTitlesAsync();
        futureTitles.thenAcceptAsync(
                titles -> {
                    saveTitlesInThreadPool(titles);
                    logger.info("-------> Movie titles loaded successfully.");
                    futureResponse.complete(ResponseEntity.status(HttpStatus.CREATED).body(HttpStatus.CREATED));
                });

        return futureResponse;
    }

    // Helper method to save titles using the thread pool
    private void saveTitlesInThreadPool(List<MovieTitle> titles) {
        titles.forEach(title ->
                threadPoolExecutor.execute(() ->
                        movieTitleRepository.save(title)
                )
        );
    }

    /**
     * Saves a movie title.
     *
     * @param movieTitleDTO The movie title DTO to save.
     * @return HTTP status code 201 (Created) if the movie title was saved successfully.
     */
    @GetMapping(path="/setTitle", produces = "application/json")
    public ResponseEntity<HttpStatus> setTitle(MovieTitleDTO movieTitleDTO) {
        logger.info("-------> Setting movie genre...");
        MovieTitle movieTitle = new MovieTitle();
        movieTitle.setTitle(movieTitleDTO.getTitle());
        movieTitle.setMovieId(movieTitleDTO.getMovieId());
        movieTitleRepository.save(movieTitle);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Retrieves all movie titles.
     *
     * @return A list of all movie titles.
     */
    @GetMapping(path = "/getTitles", produces = "application/json")
    @ResponseBody
    public Iterable<MovieTitle> getAllTitles() {
        logger.info("-------> Retrieving all movie titles...");
        return movieTitleRepository.findAll();
    }
}
