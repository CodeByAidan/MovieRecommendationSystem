package com.movie.recommendation.controller;

import com.movie.recommendation.model.Rating;
import com.movie.recommendation.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rating")
public class RatingController {

  private final RatingService ratingService;

  @Autowired
  public RatingController(RatingService ratingService) {
    this.ratingService = ratingService;
  }

  @PostMapping("/add")
  public Rating addRating(@RequestBody Rating rating) {
    return ratingService.save(rating);
  }

  @GetMapping("/{id}")
  public Rating getRating(@PathVariable Integer id) {
    return ratingService.findById(id);
  }
  // TODO: Define more endpoints
}
