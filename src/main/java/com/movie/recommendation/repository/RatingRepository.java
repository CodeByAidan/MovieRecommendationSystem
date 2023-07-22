package com.movie.recommendation.repository;

import com.movie.recommendation.model.Rating;
import org.springframework.data.repository.CrudRepository;

public interface RatingRepository extends CrudRepository<Rating, Long> {
    // Add custom query methods here if needed
}
