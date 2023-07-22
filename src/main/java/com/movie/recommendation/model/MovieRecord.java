package com.movie.recommendation.model;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.*;

@Entity
@Table(name = "movie_records")
public class MovieRecord {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private LocalDateTime timestamp;

  @ManyToOne(fetch = FetchType.LAZY)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  private Movie movie;

  public MovieRecord() {}

  public MovieRecord(LocalDateTime timestamp, User user, Movie movie) {
    this.timestamp = timestamp;
    this.user = user;
    this.movie = movie;
  }

  public MovieRecord(Long id, LocalDateTime timestamp, User user, Movie movie) {
    this.id = id;
    this.timestamp = timestamp;
    this.user = user;
    this.movie = movie;
  }

  public Long getId() {
    return id;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
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

  public void setTimestamp(LocalDateTime timestamp) {
    this.timestamp = timestamp;
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
      "MovieRecord{" +
      "id=" +
      id +
      ", timestamp='" +
      timestamp +
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
    if (!(o instanceof MovieRecord)) return false;
    MovieRecord that = (MovieRecord) o;
    return (
      id.equals(that.id) &&
      timestamp.equals(that.timestamp) &&
      user.equals(that.user) &&
      movie.equals(that.movie)
    );
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, timestamp, user, movie);
  }
}
