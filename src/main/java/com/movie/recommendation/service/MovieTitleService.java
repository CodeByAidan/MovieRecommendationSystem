package com.movie.recommendation.service;

import com.movie.recommendation.model.MovieTitle;
import com.movie.recommendation.repo.MovieTitleRepository;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
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

@Service
public class MovieTitleService {

    private static final Logger logger = LoggerFactory.getLogger(MovieTitleService.class);

    private final MovieTitleRepository movieTitleRepository;

    @Autowired
    public MovieTitleService(MovieTitleRepository movieTitleRepository) {
        this.movieTitleRepository = movieTitleRepository;
    }

    @Async("customTaskExecutor")
    public CompletableFuture<List<MovieTitle>> getTitlesAsync() {
        List<MovieTitle> titles = getTitles();
        return CompletableFuture.completedFuture(titles);
    }

    public List<MovieTitle> getTitles() {
        List<MovieTitle> titles = new ArrayList<>();
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
                    String title = csvRecord.get("title");

                    MovieTitle movieTitle = new MovieTitle();
                    movieTitle.setMovieId(Integer.parseInt(movieId));
                    movieTitle.setTitle(title);

                    titles.add(movieTitle);

                    logger.info("-------> Movie title: {}", title);
                    logger.info("-------> Movie id: {}", movieId);

                    movieTitleRepository.save(movieTitle);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("-------> Movie titles loaded.");
        return titles;
    }
}
