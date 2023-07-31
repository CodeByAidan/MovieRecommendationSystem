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
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.FileNotFoundException;
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

    @Async
    public CompletableFuture<List<MovieTitle>> getTitlesAsync() throws IOException {
        List<MovieTitle> titles = getTitles();
        return CompletableFuture.completedFuture(titles);
    }

    @Transactional
    @Cacheable("cacheTitles")
    public List<MovieTitle> getTitles() throws IOException {
        List<MovieTitle> titles = new ArrayList<>();
        ClassPathResource resource = new ClassPathResource("data/ml-25m/movies.csv");
        Reader reader = new InputStreamReader(resource.getInputStream());

        CSVFormat csvFormat = CSVFormat.Builder.create()
                        .setHeader()
                        .setSkipHeaderRecord(true)
                        .setQuoteMode(QuoteMode.ALL_NON_NULL)
                        .build();

        try (CSVParser parser = new CSVParser(reader, csvFormat)) {
            for (CSVRecord record : parser) {
                String movieId = record.get("movieId");
                String title = record.get("title");
                String genres = record.get("genres");

                MovieTitle movieTitle = new MovieTitle();
                movieTitle.setMovieId(Integer.parseInt(movieId));
                movieTitle.setTitle(title);
                movieTitle.setGenre(genres);

                titles.add(movieTitle);

                logger.info("-------> Movie title: " + movieTitle.getTitle());
                logger.info("-------> Movie genre: " + movieTitle.getGenre());
                logger.info("-------> Movie id: " + movieTitle.getMovieId());

                movieTitleRepository.save(movieTitle);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return titles;
    }
}