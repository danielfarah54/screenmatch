package br.com.alura.screenmatch.model;

import br.com.alura.screenmatch.service.FetchChatGPT;

import java.util.OptionalDouble;

public class Series {
  private final String title;
  private final Integer totalSeasons;
  private final Double score;
  private final Category genre;
  private final String actors;
  private final String poster;
  private final String plot;

  public Series (SeriesData seriesData) {
    this.title = seriesData.title();
    this.totalSeasons = seriesData.totalSeasons();
    this.score = OptionalDouble.of(Double.parseDouble(seriesData.score())).orElse(0.0);
    this.genre = Category.of(seriesData.genre().split(",")[0].trim());
    this.actors = seriesData.actors();
    this.poster = seriesData.poster();
    this.plot = FetchChatGPT.getTranslation(seriesData.plot()).trim();
  }

  public String getTitle() {
    return title;
  }

  public Integer getTotalSeasons() {
    return totalSeasons;
  }

  public Double getScore() {
    return score;
  }

  public Category getGenre() {
    return genre;
  }

  public String getActors() {
    return actors;
  }

  public String getPoster() {
    return poster;
  }

  public String getPlot() {
    return plot;
  }

  @Override
  public String toString() {
    return
      "genre=" + genre
      + ", title='" + title + '\''
      + ", totalSeasons=" + totalSeasons
      + ", score=" + score
      + ", actors='" + actors + '\''
      + ", poster='" + poster + '\''
      + ", plot='" + plot + '\'';
  }
}
