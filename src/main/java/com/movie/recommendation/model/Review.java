package com.movie.recommendation.model;

import java.util.Objects;
import javax.persistence.*;

@Entity
@Table(name = "reviews")
public class Review {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String text;

  @ManyToOne(fetch = FetchType.LAZY)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  private Movie movie;

  public Review() {}

  public Review(String text, User user, Movie movie) {
    this.text = text;
    this.user = user;
    this.movie = movie;
  }

  public Long getId() {
    return id;
  }

  public String getText() {
    return text;
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

  public void setText(String text) {
    this.text = text;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public void setMovie(Movie movie) {
    this.movie = movie;
  }

  @Override
  public String toString() {
    return (
      "Review{" +
      "id=" +
      id +
      ", text='" +
      text +
      '\'' +
      ", user=" +
      user +
      ", movie=" +
      movie +
      '}'
    );
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Review)) return false;
    Review that = (Review) o;
    return (
      Objects.equals(getId(), that.getId()) &&
      Objects.equals(getText(), that.getText()) &&
      Objects.equals(getUser(), that.getUser()) &&
      Objects.equals(getMovie(), that.getMovie())
    );
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getText(), getUser(), getMovie());
  }
}
