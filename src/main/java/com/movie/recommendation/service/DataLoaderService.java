package com.movie.recommendation.service;

import com.movie.recommendation.repository.MovieRepository;
//import com.movie.recommendation.repository.RatingRepository;
//import com.movie.recommendation.repository.TagRepository;
//import com.movie.recommendation.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

import static java.lang.System.exit;


@Service
public class DataLoaderService {

    private final MovieRepository movieRepository;
//    private final UserRepository userRepository;
//    private final RatingRepository ratingRepository;
//    private final TagRepository tagRepository;
    // TODO: add repositories for GenomeScore and GenomeTag here

    private final Logger logger = LoggerFactory.getLogger(DataLoaderService.class);

    public DataLoaderService(MovieRepository movieRepository) {
//                             UserRepository userRepository,
//                             RatingRepository ratingRepository,
//                             TagRepository tagRepository) {
        this.movieRepository = movieRepository;
//        this.userRepository = userRepository;
//        this.ratingRepository = ratingRepository;
//        this.tagRepository = tagRepository;
    }

    @PostConstruct
    public void loadMoviesData() throws IOException {
        try {
            logger.info("Loading movies data...");
            movieRepository.findAll().forEach(movie -> logger.info("Movie: {}", movie));

            // for now, just exit the program with success!
            exit(0);
            // TODO: loading code here...
        } catch (Exception e) {
            throw new IOException("Failed to load movies data", e);
        }
    }

    @PostConstruct
    public void loadUserData() throws IOException {
        logger.info("Loading users data...");
        // TODO: loading code here...
    }

    @PostConstruct
    public void loadRatingsData() throws IOException {
        logger.info("Loading ratings data...");
        // TODO: loading code here...
    }

    @PostConstruct
    public void loadTagsData() throws IOException {
        logger.info("Loading tags data...");
        // TODO: loading code here...
    }

    @PostConstruct
    public void loadGenomeScoresData() throws IOException {
        logger.info("Loading genome scores data...");
        // TODO: loading code here...
    }

    @PostConstruct
    public void loadGenomeTagsData() throws IOException {
        logger.info("Loading genome tags data...");
        // TODO: loading code here...
    }
}
