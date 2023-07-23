package com.movie.recommendation.repository;

import com.movie.recommendation.model.GenomeTag;
import org.springframework.data.repository.CrudRepository;

public interface GenomeTagRepository extends CrudRepository<GenomeTag, Long> {
    // Add custom query methods here if needed
}
