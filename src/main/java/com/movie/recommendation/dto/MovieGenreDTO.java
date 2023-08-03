package com.movie.recommendation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieGenreDTO {
    private Long id;
    private String genre;
    private long movieId;
}