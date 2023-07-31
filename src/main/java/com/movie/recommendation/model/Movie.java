package com.movie.recommendation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@Data
@Entity
@NoArgsConstructor
public class Movie {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter
    private Integer id;
    private String title;
    private String genre;

    public Movie(String  title, String genre) {
        setTitle(title);
        setGenre(genre);
    }
}
