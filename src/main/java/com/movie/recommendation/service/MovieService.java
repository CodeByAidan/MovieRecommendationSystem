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

class MovieServiceException extends RuntimeException {
    public MovieServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}

@Service
public class MovieService {
    private static final Logger logger = LoggerFactory.getLogger(MovieService.class);
    private final MovieRepository movieRepository;
    private final MovieTitleRepository movieTitleRepository;
    private final MovieGenreRepository movieGenreRepository;

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

    private List<Movie> loadMovies() {
        List<Movie> movies = new ArrayList<>();
        ClassPathResource resource = new ClassPathResource("data/ml-25m/movies.csv");

        try (Reader reader = new InputStreamReader(resource.getInputStream())) {
            batchProccessor(movies, reader);
            long start = System.currentTimeMillis();

            for (int i = 0; i < movies.size(); i += 1000) {
                List<Movie> subList = movies.subList(i, Math.min(i + 1000, movies.size()));
                movieRepository.saveAll(subList);

                logger.info("Movies left: {}", (movies.size() - i));

                long millis = System.currentTimeMillis() - start;
                logger.info("Time elapsed: {}", formatMillis(millis));
                logger.info("Average time per 1000 movies: {}", formatMillis(millis / ((i + 1000) / 1000)));

                long millisLeft = (millis / ((i + 1000) / 1000)) * ((movies.size() - i) / 1000);
                logger.info("Estimated time left: {}", formatMillis(millisLeft));

                int percent = (int) ((i + 1000) / (double) movies.size() * 100);
                String bar = StringUtils.repeat("=", percent / 2) + StringUtils.repeat(" ", 50 - (percent / 2));
                logger.info("\r[{}] {}%", bar, percent);
            }

            logger.info("Movies saved.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.info("Movies loaded.");
        return movies;
    }

    private void batchProccessor(List<Movie> movies, Reader reader) throws IOException {
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

    private void saveToMovieTitleTable(List<Movie> movies) throws IOException {
        ClassPathResource resource = new ClassPathResource("data/ml-25m/movies.csv");

        try (Reader reader = new InputStreamReader(resource.getInputStream())) {
            batchProccessor(movies, reader);
            long start = System.currentTimeMillis();

            for (int i = 0; i < movies.size(); i += 1000) {
                List<Movie> subList = movies.subList(i, Math.min(i + 1000, movies.size()));
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

                logger.info("Movies left: {}", (movies.size() - i));
                long millis = System.currentTimeMillis() - start;
                logger.info("Time elapsed: {}", formatMillis(millis));
                logger.info("Average time per 1000 movies: {}", formatMillis(millis / ((i + 2000) / 1000)));

                long millisLeft = (millis / ((i + 2000) / 1000)) * ((movies.size() - i) / 1000);
                logger.info("Estimated time left: {}", formatMillis(millisLeft));

                int percent = (int) ((i + 2000) / (double) movies.size() * 100);
                String bar = StringUtils.repeat("=", percent / 2) + StringUtils.repeat(" ", 50 - (percent / 2));
                logger.info("\r[{}] {}%", bar, percent);
            }
            logger.info("Saved to the movie_title table.");
        } catch (IOException e) {
            throw new MovieServiceException("Error processing movie titles", e);
        }
    }

    private String formatMillis(long millis) {
        return String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
    }
}