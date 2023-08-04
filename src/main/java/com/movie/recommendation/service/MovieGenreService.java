package com.movie.recommendation.service;

import com.movie.recommendation.model.MovieGenre;
import com.movie.recommendation.repo.MovieGenreRepository;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.transaction.Transactional;

@Service
public class MovieGenreService {
    private static final Logger logger = LoggerFactory.getLogger(MovieGenreService.class);
    private final MovieGenreRepository movieGenreRepository;

    @Autowired
    public MovieGenreService(MovieGenreRepository movieGenreRepository) {
        this.movieGenreRepository = movieGenreRepository;
    }

    @Async
    @Transactional
    @Cacheable("movieGenres")
    public CompletableFuture<List<MovieGenre>> getGenresAsync() {
        List<MovieGenre> genres = getGenres();
        return CompletableFuture.completedFuture(genres);
    }

    public List<MovieGenre> getGenres() {
        List<MovieGenre> movieGenres = new ArrayList<>();
        ClassPathResource resource = new ClassPathResource("data/ml-25m/movies.csv");
        try (Reader reader = new InputStreamReader(resource.getInputStream())) {
            CSVFormat csvFormat = CSVFormat.Builder.create()
                    .setHeader()
                    .setSkipHeaderRecord(true)
                    .setQuoteMode(QuoteMode.ALL_NON_NULL)
                    .build();

            try (CSVParser parser = new CSVParser(reader, csvFormat)) {
                for (CSVRecord csvRecord : parser) {
                    String movieId = csvRecord.get("movieId");
                    String genresString = csvRecord.get("genres");

                    MovieGenre movieGenre = new MovieGenre();
                    movieGenre.setMovieId(Integer.parseInt(movieId));
                    movieGenre.setGenre(genresString);
                    movieGenres.add(movieGenre);
                    logger.info("-------> Movie Genre: {}", genresString);
                    logger.info("-------> Movie ID: {}", movieId);
                    movieGenreRepository.save(movieGenre);
                }
            }
        } catch (IOException e) {
            logger.error("Error parsing movie genres", e);
        }
        logger.info("-------> Movie genres loaded.");
        return movieGenres;
    }
}
