package com.movie.recommendation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@Data
@Entity
@NoArgsConstructor
public class Movie {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)  Long id;
    private String title;
    private String genre;
    private long movieId;

    public Movie(String title, String genre, long movieId) {
        setTitle(title);
        setGenre(genre);
        setMovieId(movieId);
    }
}
