package com.movie.recommendation.controller;

import com.movie.recommendation.model.MovieGenre;
import com.movie.recommendation.repo.MovieGenreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller()
public class MovieGenreController {

        @Autowired
        MovieGenreRepository movieGenreRepository;

        private static final Logger logger = LoggerFactory.getLogger(MovieGenreController.class);

        @GetMapping(path="/setgenre", produces = "application/json")
        public ResponseEntity<HttpStatus> setGenre(MovieGenre movieGenre) {
            logger.info("-------> Setting movie genre...");
            movieGenreRepository.save(movieGenre);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        @GetMapping(path="/getGenre", produces = "application/json")
        public @ResponseBody Iterable<MovieGenre> getAllGenres() {
            logger.info("-------> Retrieving all movie genres...");
            return movieGenreRepository.findAll();
        }

}
