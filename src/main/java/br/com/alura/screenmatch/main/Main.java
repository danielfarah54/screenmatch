package br.com.alura.screenmatch.main;

import br.com.alura.screenmatch.model.Episode;
import br.com.alura.screenmatch.model.SeasonData;
import br.com.alura.screenmatch.model.SeriesData;
import br.com.alura.screenmatch.service.ConvertData;
import br.com.alura.screenmatch.service.FetchApi;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
  private final Scanner scanner = new Scanner(System.in);
  private final String BASE_URL = "https://www.omdbapi.com/?t=";
  private final String API_KEY = "&apikey=96a9c788";
  private final FetchApi fetchApi = new FetchApi();
  private final ConvertData convertData = new ConvertData();

  public void showMenu() {
    System.out.println("Enter series name");
    var name = scanner.nextLine();

    var url = BASE_URL + name.replace(" ", "+") + API_KEY;
    var json = fetchApi.getData(url);

    SeriesData seriesData = convertData.getData(json, SeriesData.class);
//    System.out.println(seriesData);

    List<SeasonData> seasonList = new ArrayList<>();
    for (int i = 1; i <= seriesData.totalSeasons(); i++) {
      url = BASE_URL + name.replace(" ", "+") + "&season=" + i + API_KEY;
      json = fetchApi.getData(url);
      SeasonData seasonData = convertData.getData(json, SeasonData.class);
      seasonList.add(seasonData);
    }
//    seasonList.forEach(season -> season.episodes().forEach(episode -> System.out.println(episode.title())));

//    List<EpisodeData> episodeDataList = seasonList.stream().flatMap(season -> season.episodes().stream()).toList();
//    episodeDataList.stream()
//      .filter(episode -> !episode.score().equalsIgnoreCase("n/a"))
//      .peek(episodeData -> System.out.println("First step\n" + episodeData))
//      .sorted(Comparator.comparing(EpisodeData::score).reversed())
//      .peek(episodeData -> System.out.println("Second step\n" + episodeData))
//      .limit(10)
//      .peek(episodeData -> System.out.println("Third step\n" + episodeData))
//      .map(episode -> episode.title().toUpperCase())
//      .peek(episodeData -> System.out.println("Fourth step\n" + episodeData))
//      .forEach(System.out::println);

    List<Episode> episodes = seasonList.stream()
      .flatMap(season -> season.episodes().stream()
        .map(episode -> new Episode(season.seasonNumber(), episode))
      ).toList();
//    episodes.forEach(System.out::println);

//    System.out.println("Enter searchTerm");
//    var searchTerm = scanner.nextLine();
//
//      Optional<Episode> searchedEpisode = episodes.stream()
//        .filter(episode -> episode.getTitle().toLowerCase().contains(searchTerm.toLowerCase()))
//        .findFirst();
//
//      if (searchedEpisode.isPresent()) {
//        System.out.println("Searched episode: " + searchedEpisode.get());
//      } else {
//        System.out.println("Episode not found");
//      }

//    System.out.println("From what year do you want to watch the episodes?");
//    var year = scanner.nextInt();
//    scanner.nextLine();
//
//    LocalDate searchDate = LocalDate.of(year, 1, 1);
//
//    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//    episodes.stream()
//      .filter(episode -> episode.getReleaseDate() != null && episode.getReleaseDate().isAfter(searchDate))
//      .forEach(episode -> System.out.println(
//        "Season: " + episode.getSeason() +
//          "\nEpisode: " + episode.getEpisodeNumber() +
//          "\nReleaseDate: " + episode.getReleaseDate().format(dateTimeFormatter)
//        )
//      );

    Map<Integer, Double> seasonRating = episodes.stream()
      .filter(episode -> episode.getScore() > 0.0)
      .collect(Collectors.groupingBy(Episode::getSeason,
        Collectors.averagingDouble(Episode::getScore)));
//    seasonRating.forEach((season, rating) -> System.out.println("Season: " + season + " - Rating: " + rating));
    System.out.println(seasonRating);

    DoubleSummaryStatistics statistics = episodes.stream()
      .filter(episode -> episode.getScore() > 0.0)
      .collect(Collectors.summarizingDouble(Episode::getScore));

    System.out.println("Average: " + statistics.getAverage());
    System.out.println("Total: " + statistics.getCount());
    System.out.println("Max: " + statistics.getMax());
    System.out.println("Min: " + statistics.getMin());
  }
}
