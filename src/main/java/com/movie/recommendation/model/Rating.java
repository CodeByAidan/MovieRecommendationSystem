package com.movie.recommendation.model;

import javax.persistence.*;
import java.util.Objects;


@Entity
@Table(name = "ratings")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer score;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;

    public Rating() {
    }

    public Rating(Integer score, User user, Movie movie) {
        this.score = score;
        this.user = user;
        this.movie = movie;
    }

    public Long getId() {
        return id;
    }

    public Integer getScore() {
        return score;
    }

    public User getUser() {
        return user;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "id=" + id +
                ", score=" + score +
                ", user=" + user +
                ", movie=" + movie +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rating)) return false;
        Rating rating = (Rating) o;
        return getId().equals(rating.getId()) &&
                getScore().equals(rating.getScore()) &&
                getUser().equals(rating.getUser()) &&
                getMovie().equals(rating.getMovie());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getScore(), getUser(), getMovie());
    }
}
