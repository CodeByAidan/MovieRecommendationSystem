package com.movie.recommendation.model;

import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "movies")
public class Movie {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;

  @ManyToMany(fetch = FetchType.LAZY)
  private Set<Genre> genres;

  public Movie() {}

  public Movie(String title, Set<Genre> genres) {
    this.title = title;
    this.genres = genres;
  }

  public Long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public Set<Genre> getGenres() {
    return genres;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setGenres(Set<Genre> genres) {
    this.genres = genres;
  }

  @Override
  public String toString() {
    return "Movie{" + "id=" + id + ", title='" + title + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Movie)) return false;
    return id != null && id.equals(((Movie) o).getId());
  }

  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : 0;
  }
}
