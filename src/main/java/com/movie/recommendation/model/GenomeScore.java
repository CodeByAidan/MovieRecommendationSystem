package com.movie.recommendation.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "genome_scores")
public class GenomeScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    private GenomeTag genomeTag;

    private Double score;

    public GenomeScore() {
    }

    public GenomeScore(Movie movie, GenomeTag genomeTag, Double score) {
        this.movie = movie;
        this.genomeTag = genomeTag;
        this.score = score;
    }

    public GenomeScore(Long id, Movie movie, GenomeTag genomeTag, Double score) {
        this.id = id;
        this.movie = movie;
        this.genomeTag = genomeTag;
        this.score = score;
    }

    public Long getId() {
        return id;
    }

    public Movie getMovie() {
        return movie;
    }

    public GenomeTag getGenomeTag() {
        return genomeTag;
    }

    public Double getScore() {
        return score;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public void setGenomeTag(GenomeTag genomeTag) {
        this.genomeTag = genomeTag;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "GenomeScore{" +
                "id=" + id +
                ", movie=" + movie +
                ", genomeTag=" + genomeTag +
                ", score=" + score +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GenomeScore)) return false;
        GenomeScore that = (GenomeScore) o;
        return id.equals(that.id) &&
                movie.equals(that.movie) &&
                genomeTag.equals(that.genomeTag) &&
                score.equals(that.score);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, movie, genomeTag, score);
    }
}
