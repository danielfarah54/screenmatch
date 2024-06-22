package br.com.alura.screenmatch.main;

import br.com.alura.screenmatch.model.*;
import br.com.alura.screenmatch.repository.SeriesRepository;
import br.com.alura.screenmatch.service.ConvertData;
import br.com.alura.screenmatch.service.FetchApi;

import java.util.*;

public class Main {
  private final Scanner scanner = new Scanner(System.in);
  private final FetchApi fetchApi = new FetchApi();
  private final ConvertData convertData = new ConvertData();
  private final String BASE_URL = "https://www.omdbapi.com/?t=";
  private final String API_KEY = "&apikey=96a9c788";
  private final SeriesRepository seriesRepository;

  public Main(SeriesRepository seriesRepository) {
    this.seriesRepository = seriesRepository;
  }

  public void showMenu() {
    var option = "-1";
    while (!option.equals("0")) {
      var menu = """
                1 - Search for series
                2 - Search for episodes
                3 - Show searched series
                4 - Search series by title
                5 - Search series by actor
                6 - Top 5 series
                7 - Search series by genre
                8 - Search series by number of seasons and score
                
                0 - Exit
                """;

      System.out.println(menu);
      option = scanner.nextLine();
      switch (option) {
        case "1":
          searchWebSeries();
          break;
        case "2":
          searchEpisodesBySeries();
          break;
        case "3":
          showSearchedSeries();
          break;
        case "4":
          searchSeriesByTitle();
          break;
        case "5":
          searchSeriesByActor();
          break;
        case "6":
          showTop5Series();
          break;
        case "7":
          searchSeriesByGenre();
          break;
        case "8":
          searchSeriesByNumberOfSeasonsAndScore();
          break;
        case "0":
          System.out.println("Exiting...");
          break;
        default:
          System.out.println("Invalid option");
          break;
      }
    }
  }

  private void searchWebSeries() {
    SeriesData data = getSeriesData();
    Series series = new Series(data);
    seriesRepository.save(series);
    System.out.println(data);
  }

  private SeriesData getSeriesData() {
    System.out.println("Enter series name");
    var name = scanner.nextLine();

    var url = BASE_URL + name.replace(" ", "+") + API_KEY;
    var json = fetchApi.getData(url);
    return convertData.getData(json, SeriesData.class);
  }

  private void searchEpisodesBySeries(){
    showSearchedSeries();
    System.out.println("Enter series name");
    var name = scanner.nextLine();

    Optional<Series> series = seriesRepository.findByTitleContainingIgnoreCase(name);

    if (series.isPresent()) {
      var foundSeries = series.get();
      List<SeasonData> seasonList = new ArrayList<>();

      for (int i = 1; i <= foundSeries.getTotalSeasons(); i++) {
        var url = BASE_URL + foundSeries.getTitle().replace(" ", "+") + "&season=" + i + API_KEY;
        var json = fetchApi.getData(url);
        SeasonData seasonData = convertData.getData(json, SeasonData.class);
        seasonList.add(seasonData);
      }
      seasonList.forEach(System.out::println);

      List<Episode> episodes = seasonList
        .stream()
        .flatMap(season -> season.episodes().stream()
          .map(episode -> new Episode(season.seasonNumber(), episode)))
        .toList();

      foundSeries.setEpisodes(episodes);
      seriesRepository.save(foundSeries);
    } else {
      System.out.println("Series not found");
    }
  }

  private void showSearchedSeries() {
    List<Series> seriesList = seriesRepository.findAll();

    seriesList
      .stream()
      .sorted(Comparator.comparing(Series::getGenre))
      .forEach(System.out::println);
  }

  private void searchSeriesByTitle() {
    System.out.println("Enter series name");
    var name = scanner.nextLine();
    Optional<Series> series = seriesRepository.findByTitleContainingIgnoreCase(name);
    if (series.isPresent()) {
      System.out.println(series.get());
    } else {
      System.out.println("Series not found");
    }
  }

  private void searchSeriesByActor() {
    System.out.println("Enter actor name");
    var name = scanner.nextLine();
    System.out.println("Enter score (from 0.1 to 10.0)");
    var score = scanner.nextDouble();
    scanner.nextLine();
    List<Series> foundSeries = seriesRepository.findByActorsContainingIgnoreCaseAndScoreGreaterThanEqual(name, score);

    System.out.println("Series that " + name + " played in: ");
    foundSeries
      .stream()
      .sorted(Comparator.comparing(Series::getScore).reversed())
      .forEach(series -> System.out.println(series.getTitle() + " - " + series.getScore()));
  }

  private void showTop5Series() {
    List<Series> topSeries = seriesRepository.findTop5ByOrderByScoreDesc();
    topSeries
      .forEach(series -> System.out.println(series.getTitle() + " - " + series.getScore()));
  }

  private void searchSeriesByGenre() {
    System.out.println("Enter genre");
    var genre = scanner.nextLine();

    List<Series> foundSeries = seriesRepository.findByGenre(Category.ofPortuguese(genre.trim()));
    foundSeries
      .forEach(series -> System.out.println(series.getTitle() + " - " + series.getScore()));
  }

  private void searchSeriesByNumberOfSeasonsAndScore() {
    System.out.println("Enter maximum number of seasons");
    var seasons = scanner.nextInt();
    scanner.nextLine();

    System.out.println("Enter minimum score (from 0.1 to 10.0)");
    var score = scanner.nextDouble();
    scanner.nextLine();

    List<Series> foundSeries = seriesRepository.findByTotalSeasonsLessThanEqualAndScoreGreaterThanEqual(seasons, score);
    foundSeries
      .forEach(series -> System.out.println(series.getTitle() + " - " + series.getScore()));
  }
}
