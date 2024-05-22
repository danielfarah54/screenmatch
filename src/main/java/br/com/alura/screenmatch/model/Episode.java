package br.com.alura.screenmatch.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episode {
  private Integer season;
  private String title;
  private Integer episodeNumber;
  private Double score;
  private LocalDate releaseDate;

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

  @Override
  public String toString() {
    return "season=" + season + ", title='" + title + '\'' + ", episodeNumber=" + episodeNumber + ", score=" + score + ", releaseDate=" + releaseDate;
  }
}
