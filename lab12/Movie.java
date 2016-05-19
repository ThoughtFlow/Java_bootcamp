package lab12;

import java.util.Set;

public class Movie{

	private final Set<String> categories;
	private final String name;
	private final Integer releaseYear;
	
	public Movie(Set<String> categories, String name, Integer releaseYear) {
		this.categories = categories;
		this.name = name;
		this.releaseYear = releaseYear;
	}

	public Set<String> getCategories() {
		return categories;
	}

	public String getName() {
		return name;
	}

	public Integer getReleaseYear() {
		return releaseYear;
	}

	@Override
	public String toString() {
		return "Movie [categories=" + categories + ", name=" + name + ", releaseYear=" + releaseYear + "]";
	}
}
