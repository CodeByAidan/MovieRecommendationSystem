package com.movie.recommendation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieTitle {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)  Long id;
    private String title;

    public MovieTitle(String title) {
        setTitle(title);
    }
}
