package br.com.alura.screenmatch.main;

import br.com.alura.screenmatch.model.Episode;
import br.com.alura.screenmatch.model.EpisodeData;
import br.com.alura.screenmatch.model.SeasonData;
import br.com.alura.screenmatch.model.SeriesData;
import br.com.alura.screenmatch.service.ConvertData;
import br.com.alura.screenmatch.service.FetchApi;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {
  private final Scanner scanner = new Scanner(System.in);
  private final String BASE_URL = "https://www.omdbapi.com/?t=";
  private final String API_KEY = "&apikey=my-api-key";
  private final FetchApi fetchApi = new FetchApi();
  private final ConvertData convertData = new ConvertData();

  public void showMenu() {
    System.out.println("Enter series name");
    var name = scanner.nextLine();

    var url = BASE_URL + name.replace(" ", "+") + API_KEY;
    var json = fetchApi.getData(url);

    SeriesData seriesData = convertData.getData(json, SeriesData.class);
    System.out.println(seriesData);

    List<SeasonData> seasonList = new ArrayList<>();
    for (int i = 1; i <= seriesData.totalSeasons(); i++) {
      url = BASE_URL + name.replace(" ", "+") + "&season=" + i + API_KEY;
      json = fetchApi.getData(url);
      SeasonData seasonData = convertData.getData(json, SeasonData.class);
      seasonList.add(seasonData);
    }
    seasonList.forEach(season -> season.episodes().forEach(episode -> System.out.println(episode.title())));

    List<EpisodeData> episodeDataList = seasonList.stream().flatMap(season -> season.episodes().stream()).toList();
    episodeDataList.stream().filter(episode -> !episode.score().equalsIgnoreCase("n/a")).sorted(Comparator.comparing(EpisodeData::score).reversed()).limit(5).forEach(System.out::println);

    List<Episode> episodes = seasonList.stream().flatMap(season -> season.episodes().stream().map(episode -> new Episode(season.seasonNumber(), episode))).toList();
    episodes.forEach(System.out::println);
  }
}
