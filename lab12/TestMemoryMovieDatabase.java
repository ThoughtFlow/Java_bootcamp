package lab12;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TestMemoryMovieDatabase {
	
	private static void findAndPrint(MovieDatabase database, String nameOfMovie) {
		
		// Try to find the movie
		Movie foundMovie = database.find(nameOfMovie);
		
		if (foundMovie != null) {
			System.out.println("Movie was found: " + foundMovie);
		}
		else {
			System.out.println("Movie was not found: " + nameOfMovie);
		}
	}

	public static void main(String... args) {

		MovieDatabase movies = new MemoryMovieDatabase();

		// Adding single category movies
		movies.add("The Simpsons", "COMEDY", 2007);
		movies.add("Goodfellas", "DRAMA", 1990);
		movies.add("Silence of the Lambs", "HORROR", 1991);

		// Now try to find newly added movie
		findAndPrint(movies,  "GoodFellas");

		// Try to find a non-existent movie
		findAndPrint(movies, "non-existent movie");

		// Adding multiple category movies
		Set<String> romanticComedy = new HashSet<>(Arrays.asList("COMEDY", "ROMANTIC"));
		movies.add("When Harry Met Sally", romanticComedy, 1989);	

		// Getting by categories & deleting
		for (String next : movies.getByCategory("COMEDY"))
		{
			System.out.println(next);
		}
		movies.delete("When Harry Met Sally");

		// Try to find movie after being deleted
		findAndPrint(movies, "When Harry Met Sally");
	}
}