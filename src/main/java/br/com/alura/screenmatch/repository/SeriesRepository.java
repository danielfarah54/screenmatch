package br.com.alura.screenmatch.repository;

import br.com.alura.screenmatch.model.Series;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeriesRepository extends JpaRepository<Series, Long> {
  Optional<Series> findByTitleContainingIgnoreCase(String title);

  List<Series> findByActorsContainingIgnoreCaseAndScoreGreaterThanEqual(String name, Double score);

  List<Series> findTop5ByOrderByScoreDesc();
}
