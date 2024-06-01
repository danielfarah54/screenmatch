package br.com.alura.screenmatch.main;

import br.com.alura.screenmatch.model.SeasonData;
import br.com.alura.screenmatch.model.Series;
import br.com.alura.screenmatch.model.SeriesData;
import br.com.alura.screenmatch.service.ConvertData;
import br.com.alura.screenmatch.service.FetchApi;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {
  private final Scanner scanner = new Scanner(System.in);
  private final FetchApi fetchApi = new FetchApi();
  private final ConvertData convertData = new ConvertData();
  private final String BASE_URL = "https://www.omdbapi.com/?t=";
  private final String API_KEY = "&apikey=96a9c788";
  private final List<SeriesData> seriesList = new ArrayList<>();

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
    seriesList.add(data);
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
    SeriesData seriesData = getSeriesData();
    List<SeasonData> seasonList = new ArrayList<>();

    for (int i = 1; i <= seriesData.totalSeasons(); i++) {
      var url = BASE_URL + seriesData.title().replace(" ", "+") + "&season=" + i + API_KEY;
      var json = fetchApi.getData(url);
      SeasonData seasonData = convertData.getData(json, SeasonData.class);
      seasonList.add(seasonData);
    }
    seasonList.forEach(System.out::println);
  }

  private void showSearchedSeries() {
    List<Series> series = seriesList
      .stream()
      .map(Series::new)
      .toList();

    series
      .stream()
      .sorted(Comparator.comparing(Series::getGenre))
      .forEach(System.out::println);
  }
}
