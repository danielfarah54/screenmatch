package br.com.alura.screenmatch.model;

import br.com.alura.screenmatch.service.FetchChatGPT;
import jakarta.persistence.*;

import java.util.OptionalDouble;

@Entity
@Table(name = "SERIES")
public class Series {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "SERI_SQ_ID")
  private Long id;

  @Column(name = "SERI_TX_TITLE", unique = true)
  private String title;

  @Column(name = "SERI_NR_TOTAL_SEASONS")
  private Integer totalSeasons;

  @Column(name = "SERI_NR_SCORE")
  private Double score;

  @Enumerated(EnumType.STRING)
  @Column(name = "SERI_TX_GENRE")
  private Category genre;

  @Column(name = "SERI_TX_ACTORS")
  private String actors;

  @Column(name = "SERI_TX_POSTER")
  private String poster;

  @Column(name = "SERI_TX_PLOT")
  private String plot;

  public Series() {}

  public Series (SeriesData seriesData) {
    this.title = seriesData.title();
    this.totalSeasons = seriesData.totalSeasons();
    this.score = OptionalDouble.of(Double.parseDouble(seriesData.score())).orElse(0.0);
    this.genre = Category.of(seriesData.genre().split(",")[0].trim());
    this.actors = seriesData.actors();
    this.poster = seriesData.poster();
    this.plot = FetchChatGPT.getTranslation(seriesData.plot()).trim();
  }


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Integer getTotalSeasons() {
    return totalSeasons;
  }

  public void setTotalSeasons(Integer totalSeasons) {
    this.totalSeasons = totalSeasons;
  }

  public Double getScore() {
    return score;
  }

  public void setScore(Double score) {
    this.score = score;
  }

  public Category getGenre() {
    return genre;
  }

  public void setGenre(Category genre) {
    this.genre = genre;
  }

  public String getActors() {
    return actors;
  }

  public void setActors(String actors) {
    this.actors = actors;
  }

  public String getPoster() {
    return poster;
  }

  public void setPoster(String poster) {
    this.poster = poster;
  }

  public String getPlot() {
    return plot;
  }

  public void setPlot(String plot) {
    this.plot = plot;
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
