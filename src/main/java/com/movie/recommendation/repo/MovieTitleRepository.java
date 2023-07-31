package com.movie.recommendation.repo;

import com.movie.recommendation.model.MovieTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieTitleRepository extends JpaRepository<MovieTitle, Integer> {

}
