package br.com.alura.screenmatch.model;

import java.text.Normalizer;
import java.util.Locale;

public enum Category {
  ACTION("Action", "Ação"),
  COMEDY("Comedy", "Comédia"),
  CRIME("Crime", "Crime"),
  DRAMA("Drama", "Drama"),
  ROMANCE("Romance", "Romance"),
  THRILLER("Thriller", "Suspense");

  private final String omdbCategory;
  private final String portugueseCategory;

  Category(String omdbCategory, String portugueseCategory) {
    this.omdbCategory = omdbCategory;
    this.portugueseCategory = portugueseCategory;
  }

  public static Category of(String omdbCategory) {
    for (Category category : values()) {
      if (category.omdbCategory.equalsIgnoreCase(omdbCategory)) {
        return category;
      }
    }
    throw new IllegalArgumentException("Invalid category: " + omdbCategory);
  }

  public static Category ofPortuguese(String portugueseCategory) {
    String normalizedInput = normalize(portugueseCategory);
    for (Category category : values()) {
      if (normalize(category.portugueseCategory).equalsIgnoreCase(normalizedInput)) {
        return category;
      }
    }
    throw new IllegalArgumentException("Invalid category: " + portugueseCategory);
  }

  private static String normalize(String input) {
    return Normalizer.normalize(input, Normalizer.Form.NFD)
      .replaceAll("\\p{M}", "")
      .toLowerCase(Locale.ROOT);
  }
}
