package br.com.alura.screenmatch;

import br.com.alura.screenmatch.main.Main;
import br.com.alura.screenmatch.repository.SeriesRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {
  private final SeriesRepository seriesRepository;

  public ScreenmatchApplication(SeriesRepository seriesRepository) {
    this.seriesRepository = seriesRepository;
  }

  public static void main(String[] args) {
    SpringApplication.run(ScreenmatchApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    Main main = new Main(seriesRepository);
    main.showMenu();
  }
}
