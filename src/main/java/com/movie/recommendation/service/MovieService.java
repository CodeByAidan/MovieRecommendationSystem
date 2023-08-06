package com.movie.recommendation.service;

import com.movie.recommendation.model.Movie;
import com.movie.recommendation.model.MovieGenre;
import com.movie.recommendation.model.MovieTitle;
import com.movie.recommendation.repo.MovieGenreRepository;
import com.movie.recommendation.repo.MovieRepository;
import com.movie.recommendation.repo.MovieTitleRepository;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Value;

@Service
public class MovieService {
    private static final Logger logger = LoggerFactory.getLogger(MovieService.class);
    private final MovieRepository movieRepository;
    private final MovieTitleRepository movieTitleRepository;
    private final MovieGenreRepository movieGenreRepository;

    @Value("${movie.service.logging.enabled:true}")
    private boolean loggingEnabled;

    @Autowired
    public MovieService(MovieRepository movieRepository,
                        MovieTitleRepository movieTitleRepository,
                        MovieGenreRepository movieGenreRepository) {
        this.movieRepository = movieRepository;
        this.movieTitleRepository = movieTitleRepository;
        this.movieGenreRepository = movieGenreRepository;
    }

    @Async("customTaskExecutor")
    public CompletableFuture<List<Movie>> getMoviesAsync() {
        return CompletableFuture.completedFuture(loadMovies());
    }

    @Async("customTaskExecutor")
    public void saveToMovieTitleTableAsync(List<Movie> movies) {
        CompletableFuture.runAsync(() -> {
            try {
                saveToMovieTitleTable(movies);
            } catch (IOException e) {
                throw new MovieServiceException("Error saving to movie title table", e);
            }
        });
    }

    private void logProgressInfo(int i, long start, int totalMovies) {
        if (loggingEnabled) {
            long elapsedMillis = System.currentTimeMillis() - start;
            int moviesLeft = totalMovies - i;
            String timeElapsed = formatMillis(elapsedMillis);
            String avgTimePer1000 = formatMillis(elapsedMillis / (i / 1000 + 1));
            long millisLeft = (elapsedMillis / (i / 1000 + 1)) * (moviesLeft / 1000);
            String estimatedTimeLeft = formatMillis(millisLeft);
            int percent = (int) (i / (double) totalMovies * 100);
            String bar = StringUtils.repeat("=", percent / 2) +
                    StringUtils.repeat(" ", 50 - (percent / 2));

            logger.info("movies left: {}", moviesLeft);
            logger.info("time elapsed: {}", timeElapsed);
            logger.info("average time per 1000 movies: {}", avgTimePer1000);
            logger.info("estimated time left: {}", estimatedTimeLeft);
            logger.info("\r[{}] {}%", bar, percent);
        }
    }

    private List<Movie> loadMovies() {
        List<Movie> movies = new ArrayList<>();
        ClassPathResource resource = new ClassPathResource("data/ml-25m/movies.csv");

        try (Reader reader = new InputStreamReader(resource.getInputStream())) {
            batchProcessor(movies, reader);
            long start = System.currentTimeMillis();
            int batchSize = 1000;

            for (int i = 0; i < movies.size(); i += batchSize) {
                List<Movie> subList = movies.subList(i, Math.min(i + batchSize, movies.size()));
                movieRepository.saveAll(subList);
                logProgressInfo(i, start, movies.size());
            }

            logger.info("Movies saved.");
        } catch (IOException e) {
            throw new MovieServiceException("Error processing movies", e);
        }

        logger.info("Movies loaded.");
        return movies;
    }


    private void saveToMovieTitleTable(List<Movie> movies) throws IOException {
        ClassPathResource resource = new ClassPathResource("data/ml-25m/movies.csv");

        try (Reader reader = new InputStreamReader(resource.getInputStream())) {
            batchProcessor(movies, reader);
            long start = System.currentTimeMillis();
            // TODO: test 2000 batch size
            int batchSize = 2000;

            for (int i = 0; i < movies.size(); i += batchSize) {
                List<Movie> subList = movies.subList(i, Math.min(i + batchSize, movies.size()));
                List<MovieTitle> movieTitles = new ArrayList<>();
                List<MovieGenre> movieGenres = new ArrayList<>();

                for (Movie movie : subList) {
                    MovieTitle movieTitle = new MovieTitle(movie.getTitle(), movie.getMovieId());
                    movieTitles.add(movieTitle);

                    MovieGenre movieGenre = new MovieGenre(movie.getGenre(), movie.getMovieId());
                    movieGenres.add(movieGenre);
                }

                movieTitleRepository.saveAll(movieTitles);
                movieGenreRepository.saveAll(movieGenres);
                logProgressInfo(i, start, movies.size());
            }

        } catch (IOException e) {
            throw new MovieServiceException("Error processing movie titles", e);
        }
    }

    private void batchProcessor(List<Movie> movies, Reader reader) throws IOException {
        CSVFormat csvFormat = CSVFormat.Builder.create()
                .setHeader()
                .setSkipHeaderRecord(true)
                .setQuoteMode(QuoteMode.ALL_NON_NULL)
                .build();

        try (CSVParser parser = new CSVParser(reader, csvFormat)) {
            for (CSVRecord csvRecord : parser) {
                String movieId = csvRecord.get("movieId");
                String title = csvRecord.get("title");
                String genres = csvRecord.get("genres");

                Movie movie = new Movie();
                movie.setMovieId(Long.parseLong(movieId));
                movie.setTitle(title);
                movie.setGenre(genres);
                movies.add(movie);
            }
        }
        logger.info("Movies count: {}", movies.size());
    }

    private String formatMillis(long millis) {
        return String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
    }
}

