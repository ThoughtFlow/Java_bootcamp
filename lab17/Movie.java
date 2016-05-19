package lab17;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Movie{

	private final long id;
	private final Set<String> categories;
	private String name;
	private Date releaseDate;
	


	public Movie(long id, Set<String> categories, String name, Date releaseDate) {
		this.id = id;
		this.categories = categories;
		this.name = name;
		this.releaseDate = releaseDate;
	}

	public Movie(long id, String singleCategory, String name, Date releaseDate) {
		this.id = id;
		categories = new HashSet<String>();
		categories.add(singleCategory);
		this.name = name;
		this.releaseDate = releaseDate;	
	}
	
	public long getId() {
		return id;
	}
	
	public Set<String> getCategories() {
		return categories;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	@Override
	public String toString() {
		return "Movie [id=" + id + ", categories=" + categories + ", name=" + name + ", releaseDate=" + releaseDate + "]";
	}
}
