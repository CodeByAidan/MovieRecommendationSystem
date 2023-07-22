package com.movie.recommendation.model;

import java.util.Objects;
import javax.persistence.*;

@Entity
@Table(name = "genome_tags")
public class GenomeTag {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String tag;

  public GenomeTag() {}

  public GenomeTag(String tag) {
    this.tag = tag;
  }

  public GenomeTag(Long id, String tag) {
    this.id = id;
    this.tag = tag;
  }

  public Long getId() {
    return id;
  }

  public String getTag() {
    return tag;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  @Override
  public String toString() {
    return "GenomeTag{" + "id=" + id + ", tag='" + tag + '\'' + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof GenomeTag)) return false;
    GenomeTag that = (GenomeTag) o;
    return id.equals(that.id) && tag.equals(that.tag);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, tag);
  }
}
