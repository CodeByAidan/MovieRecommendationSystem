package com.movie.recommendation.controller;

import com.movie.recommendation.model.Movie;
import com.movie.recommendation.repo.MovieRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ResourceBundle;

@Controller()
public class MovieController {

    @Autowired private MovieRepository movieRepository;

    @GetMapping(path = "/all", produces = "application/json")
    public @ResponseBody Iterable<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @PostMapping(path = "/setmovie", produces = "application/json")
    public ResponseEntity<HttpStatus> saveMovie(Movie movie) {
        movieRepository.save(movie);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
