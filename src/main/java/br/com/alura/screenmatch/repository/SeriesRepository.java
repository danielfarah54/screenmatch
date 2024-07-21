package br.com.alura.screenmatch.repository;

import br.com.alura.screenmatch.model.Category;
import br.com.alura.screenmatch.model.Episode;
import br.com.alura.screenmatch.model.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SeriesRepository extends JpaRepository<Series, Long> {
  Optional<Series> findByTitleContainingIgnoreCase(String title);

  List<Series> findByActorsContainingIgnoreCaseAndScoreGreaterThanEqual(String name, Double score);

  List<Series> findTop5ByOrderByScoreDesc();

  List<Series> findByGenre(Category genre);

  List<Series> findByTotalSeasonsLessThanEqualAndScoreGreaterThanEqual(int totalSeasons, Double score);

  @Query("SELECT s FROM Series s WHERE s.totalSeasons <= :totalSeasons AND s.score >= :score")
  List<Series> findByTotalSeasonsAndScore(int totalSeasons, Double score);

  @Query("SELECT e FROM Series s JOIN s.episodes e WHERE e.title ILIKE %:excerpt% ORDER BY e.season ASC, e.episodeNumber ASC")
  List<Episode> findByExcerpt(String excerpt);
}
