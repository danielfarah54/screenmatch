package br.com.alura.screenmatch.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;

import java.util.HashMap;
import java.util.Map;

public class DotenvEnvironmentPostProcessor implements EnvironmentPostProcessor {

  @Override
  public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
    Dotenv dotenv = Dotenv.load();
    Map<String, Object> dotenvProperties = new HashMap<>();

    dotenv.entries().forEach(entry -> dotenvProperties.put(entry.getKey(), entry.getValue()));

    PropertySource<?> propertySource = new MapPropertySource("dotenvProperties", dotenvProperties);
    environment.getPropertySources().addFirst(propertySource);
  }
}
