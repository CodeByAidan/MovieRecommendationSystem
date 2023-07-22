package com.movie.recommendation.service;

import com.movie.recommendation.model.Movie;
import com.movie.recommendation.repository.MovieRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

  private final MovieRepository movieRepository;

  @Autowired
  public MovieService(MovieRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  public List<Movie> getAllMovies() {
    return (List<Movie>) movieRepository.findAll();
  }

  public Movie save(Movie movie) {
    return movieRepository.save(movie);
  }
  //    public Movie findById(Long id) {
  //        return movieRepository.findById(id).orElse(null);
  //    }

  // TODO: More service methods
}
