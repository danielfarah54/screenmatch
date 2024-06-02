package br.com.alura.screenmatch.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Entity
@Table(name = "EPISODE")
public class Episode {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "EPIS_SQ_ID")
  private Long id;

  @Column(name = "EPIS_NR_SEASON")
  private Integer season;

  @Column(name = "EPIS_TX_TITLE")
  private String title;

  @Column(name = "EPIS_NR_EPISODE")
  private Integer episodeNumber;

  @Column(name = "EPIS_NR_SCORE")
  private Double score;

  @Column(name = "EPIS_DT_RELEASE")
  private LocalDate releaseDate;

  @ManyToOne
  @JoinColumn(name = "SERI_SQ_ID")
  private Series series;

  public Episode() {}

  public Episode(Integer seasonNumber, EpisodeData episodeData) {
    this.season = seasonNumber;
    this.title = episodeData.title();
    this.episodeNumber = episodeData.episode();

    try {
      this.score = Double.valueOf(episodeData.score());
    } catch (NumberFormatException e) {
      this.score = 0.0;
    }

    try {
      this.releaseDate = LocalDate.parse(episodeData.releaseDate());
    } catch (DateTimeParseException e) {
      this.releaseDate = null;
    }
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getSeason() {
    return season;
  }

  public void setSeason(Integer season) {
    this.season = season;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Integer getEpisodeNumber() {
    return episodeNumber;
  }

  public void setEpisodeNumber(Integer episodeNumber) {
    this.episodeNumber = episodeNumber;
  }

  public Double getScore() {
    return score;
  }

  public void setScore(Double score) {
    this.score = score;
  }

  public LocalDate getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(LocalDate releaseDate) {
    this.releaseDate = releaseDate;
  }

  public Series getSeries() {
    return series;
  }

  public void setSeries(Series series) {
    this.series = series;
  }

  @Override
  public String toString() {
    return "season=" + season
      + ", title='" + title + '\''
      + ", episodeNumber=" + episodeNumber
      + ", score=" + score
      + ", releaseDate=" + releaseDate;
  }
}
