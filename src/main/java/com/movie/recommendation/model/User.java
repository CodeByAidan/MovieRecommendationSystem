package com.movie.recommendation.model;

import java.util.*;
import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String username;

  private String password;

  @ManyToMany(fetch = FetchType.LAZY)
  private Set<Movie> favoriteMovies;

  public User() {}

  public User(String username, String password, Set<Movie> favoriteMovies) {
    this.username = username;
    this.password = password;
    this.favoriteMovies = favoriteMovies;
  }

  public Long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public Set<Movie> getFavoriteMovies() {
    return favoriteMovies;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setFavoriteMovies(Set<Movie> favoriteMovies) {
    this.favoriteMovies = favoriteMovies;
  }

  @Override
  public String toString() {
    return (
      "User{" +
      "id=" +
      id +
      ", username=" +
      username +
      ", password=" +
      password +
      ", favoriteMovies=" +
      favoriteMovies +
      '}'
    );
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof User)) return false;
    User user = (User) o;
    return (
      Objects.equals(this.id, user.id) &&
      Objects.equals(this.username, user.username) &&
      Objects.equals(this.password, user.password) &&
      Objects.equals(this.favoriteMovies, user.favoriteMovies)
    );
  }

  @Override
  public int hashCode() {
    return Objects.hash(
      this.id,
      this.username,
      this.password,
      this.favoriteMovies
    );
  }
}
