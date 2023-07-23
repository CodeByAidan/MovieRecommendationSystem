package com.movie.recommendation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.movie.recommendation.model.Rating;
import com.movie.recommendation.repository.RatingRepository;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;

    @Autowired
    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public Rating save(Rating rating) {
        return ratingRepository.save(rating);
    }

    public Rating findById(Integer id) {
        return ratingRepository.findById(Long.valueOf(id)).orElse(null);
    }

    // TODO: More service methods
}
