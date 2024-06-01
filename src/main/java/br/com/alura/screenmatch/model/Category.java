package br.com.alura.screenmatch.model;

public enum Category {
  ACTION("Action"),
  COMEDY("Comedy"),
  CRIME("Crime"),
  DRAMA("Drama"),
  ROMANCE("Romance"),
  THRILLER("Thriller");

  private final String omdbCategory;

  Category(String omdbCategory) {
    this.omdbCategory = omdbCategory;
  }

  public static Category of(String omdbCategory) {
    for (Category category : values()) {
      if (category.omdbCategory.equalsIgnoreCase(omdbCategory)) {
        return category;
      }
    }
    throw new IllegalArgumentException("Invalid category: " + omdbCategory);
  }
}
