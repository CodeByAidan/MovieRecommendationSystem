package com.movie.recommendation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieDTO {
    private Long id;
    private String title;
    private String genre;
    private long movieId;

    public List<String> getGenres() {
        List<String> genres = new ArrayList<>();
        String[] genresArray = this.genre.split("\\|");
        Collections.addAll(genres, genresArray);
        return genres;
    }
}
