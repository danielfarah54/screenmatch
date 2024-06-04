package br.com.alura.screenmatch.main;

import br.com.alura.screenmatch.model.Episode;
import br.com.alura.screenmatch.model.SeasonData;
import br.com.alura.screenmatch.model.Series;
import br.com.alura.screenmatch.model.SeriesData;
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

  private List<Series> seriesList = new ArrayList<>();

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

    Optional<Series> series = seriesList
      .stream()
      .filter(serie -> serie
        .getTitle().toLowerCase().contains(name.toLowerCase()))
      .findFirst();

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
    seriesList = seriesRepository.findAll();

    seriesList
      .stream()
      .sorted(Comparator.comparing(Series::getGenre))
      .forEach(System.out::println);
  }
}
