package com.movie.recommendation.repository;

import com.movie.recommendation.model.Tag;
import org.springframework.data.repository.CrudRepository;

public interface TagRepository extends CrudRepository<Tag, Long> {
  // Add custom query methods here if needed
}
