package com.movie.recommendation.repo;

import com.movie.recommendation.model.MovieGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieGenreRepository extends JpaRepository<MovieGenre, Integer> {

}
