package lab17;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class TestPersistentMovieDatabase {
	
	private static Date toDate(int day, int month, int year) throws ParseException {
		DateFormat df = new SimpleDateFormat("m d y");
		String stringedDate = Integer.toString(month) + " " + Integer.toString(day) + " " + Integer.toString(year);
		return df.parse(stringedDate);
	}
	
	private static void findAndPrint(MovieDatabase database, String nameOfMovie) throws StorageException {
		
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

		try {
			MovieDatabase movies = new PersistentMovieDatabase(true);

			// Adding single category movies
			movies.add("The Simpsons", "COMEDY", toDate(7, 21, 2007));
			movies.add("Goodfellas", "DRAMA", toDate(12, 9, 1990));
			movies.add("Silence of the Lambs", "HORROR", toDate(13, 2, 1991));
			
			// Now try to find newly added movie
			findAndPrint(movies,  "GoodFellas");
			
			// Try to find a non-existent movie
			findAndPrint(movies, "non-existent movie");
			
			// Adding multiple category movies
			Set<String> romanticComedy = new HashSet<>(Arrays.asList("COMEDY", "ROMANTIC"));
			movies.add("When Harry Met Sally", romanticComedy, toDate(21, 7,1989));	
			
			// Update release date
			Movie movie = movies.find("Silence of the Lambs");
			movie.setReleaseDate(toDate(14, 3, 1992));
			movies.update(movie);
			findAndPrint(movies, "Silence of the Lambs");
			
			// Getting by categories & deleting
			for (String next : movies.getByCategory("COMEDY"))
			{
				System.out.println(next);
			}
			movies.delete("When Harry Met Sally");
			
			// Try to find movie after being deleted
			findAndPrint(movies, "When Harry Met Sally");
		}
		catch (StorageException | ParseException exception) {
			System.err.println("An error occurred: " + exception.getMessage());
			exception.printStackTrace();
		}
	}
}
