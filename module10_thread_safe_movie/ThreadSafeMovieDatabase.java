package module10_thread_safe_movie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ThreadSafeMovieDatabase {

	private final Map<String, List<Movie>> database = new HashMap<>();

	private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

	public void add(Set<String> categories, String name, Integer yearReleased) {

		readWriteLock.writeLock().lock();

		try {
			Movie movieToAdd = new Movie(categories, name, yearReleased);

			for (String nextCategory : categories) {
				List<Movie> movies = database.get(nextCategory);
				if (movies == null) {
					movies = new LinkedList<Movie>();
					database.put(nextCategory, movies);
				}
				if (movies.contains(movieToAdd) == false)
				{
				   movies.add(movieToAdd);
				}
			}
		} finally {
			readWriteLock.writeLock().unlock();
		}
	}

	public void add(String category, String name, Integer yearReleased) {

		Set<String> categories = new HashSet<>();
		categories.add(category);
		add(categories, name, yearReleased);
	}

	public Movie find(String name) {

		readWriteLock.readLock().lock();

		try {
			Movie foundMovie = null;
			Iterator<List<Movie>> iterator = database.values().iterator();
			while (iterator.hasNext() && foundMovie == null) {
				List<Movie> nextList = iterator.next();

				for (Movie nextMovie : nextList) {
					if (nextMovie.getName().equals(name)) {
						foundMovie = nextMovie;
						break;
					}
				}
			}

			return foundMovie;
		} finally {
			readWriteLock.readLock().unlock();
		}
	}

	public List<String> getByCategory(String category) {

		readWriteLock.readLock().lock();

		try {
			List<Movie> movies = database.containsKey(category) ? database.get(category) : Collections.emptyList();
			List<String> movieTitles = new ArrayList<>();

			for (Movie next : movies) {
				movieTitles.add(next.getName());
			}

			return movieTitles;
		} finally {
			readWriteLock.readLock().unlock();
		}
	}

	public boolean delete(String name) {

		readWriteLock.writeLock().lock();

		try {
			Movie foundMovie = null;
			Iterator<List<Movie>> iterator = database.values().iterator();
			while (iterator.hasNext() && foundMovie == null) {
				List<Movie> nextList = iterator.next();

				Iterator<Movie> movieIterator = nextList.iterator();
				while (movieIterator.hasNext()) {
					Movie nextMovie = movieIterator.next();
					if (nextMovie.getName().equals(name)) {
						foundMovie = nextMovie;
						movieIterator.remove();
					}
				}
			}

			return foundMovie != null;
		} finally {
			readWriteLock.writeLock().unlock();
		}
	}

	public static void main(String... args) {

		ThreadSafeMovieDatabase movies = new ThreadSafeMovieDatabase();
		movies.add("COMEDY", "The Simpsons", 2015);
		movies.add("COMEDY", "The Simpsons", 2015);
		movies.add("DRAMA", "Goodfellas", 1990);
		movies.add("HORROR", "Jurrasic Parc", 1993);
		Set<String> romanticComedy = new HashSet<>(Arrays.asList("COMEDY", "ROMANTIC"));
		movies.add(romanticComedy, "Sleepless in Seattle", 1994);

		System.out.println(movies.find("The Simpsons").getReleaseYear());
		for (String next : movies.getByCategory("COMEDY")) {
			System.out.println(next);
		}
		movies.delete("Sleepless in Seattle");

		for (String next : movies.getByCategory("HORROR")) {
			System.out.println(next);
		}
	}
}
