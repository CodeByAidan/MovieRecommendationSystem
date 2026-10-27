package com.movie.recommendation.model;

import lombok.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieGenre {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)  Long id;
    private String genre;
    private long movieId;

    public MovieGenre(String genre, long movieId) {
        setGenre(genre);
        setMovieId(movieId);
    }
}
