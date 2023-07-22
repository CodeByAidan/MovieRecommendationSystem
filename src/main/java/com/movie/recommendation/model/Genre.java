package com.movie.recommendation.model;

import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "genres")
public class Genre {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @ManyToMany(mappedBy = "genres")
  private Set<Movie> movies;

  public Genre() {}

  public Genre(String name) {
    this.name = name;
  }

  public Genre(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public Genre(Long id, String name, Set<Movie> movies) {
    this.id = id;
    this.name = name;
    this.movies = movies;
  }

  public Genre(String name, Set<Movie> movies) {
    this.name = name;
    this.movies = movies;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Set<Movie> getMovies() {
    return movies;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setMovies(Set<Movie> movies) {
    this.movies = movies;
  }

  @Override
  public String toString() {
    return "Genre{" + "id=" + id + ", name='" + name + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Genre)) return false;
    return id != null && id.equals(((Genre) o).getId());
  }

  @Override
  public int hashCode() {
    return 31;
  }
}
