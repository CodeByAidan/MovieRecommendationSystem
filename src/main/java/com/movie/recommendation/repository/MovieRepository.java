package com.movie.recommendation.repository;

import com.movie.recommendation.model.Movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Long> {
    // Add custom query methods here if needed:
    // https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods
}
