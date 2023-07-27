package com.movie.recommendation.repository;

import com.movie.recommendation.model.GenomeScore;
import org.springframework.data.repository.CrudRepository;

public interface GenomeScoreRepository
  extends CrudRepository<GenomeScore, Long> {
  // Add custom query methods here if needed
}
