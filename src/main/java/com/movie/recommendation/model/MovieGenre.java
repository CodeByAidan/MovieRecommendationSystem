package com.movie.recommendation.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
