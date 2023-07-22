package com.movie.recommendation.repository;

import com.movie.recommendation.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
  // Add custom query methods here if needed
}
