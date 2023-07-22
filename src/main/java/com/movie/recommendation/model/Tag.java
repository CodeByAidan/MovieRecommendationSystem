package com.movie.recommendation.model;

import javax.persistence.*;

@Entity
@Table(name = "tags")
public class Tag {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  private Movie movie;

  public Tag() {}

  public Tag(String name) {
    this.name = name;
  }

  public Tag(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public Tag(Long id, String name, User user, Movie movie) {
    this.id = id;
    this.name = name;
    this.user = user;
    this.movie = movie;
  }

  public Tag(String name, User user, Movie movie) {
    this.name = name;
    this.user = user;
    this.movie = movie;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
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

  public void setName(String name) {
    this.name = name;
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
      "Tag{" +
      "id=" +
      id +
      ", name='" +
      name +
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
    if (!(o instanceof Tag)) return false;
    return id != null && id.equals(((Tag) o).getId());
  }

  @Override
  public int hashCode() {
    return 31;
  }
}
