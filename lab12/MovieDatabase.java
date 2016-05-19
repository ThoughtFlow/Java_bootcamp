package lab12;

import java.util.List;
import java.util.Set;

/**
 * Interface to define a movie database.
 * 
 * @author Nick Maiorano
 */
public interface MovieDatabase {

	/**
	 * Adds a movie to the database with the given categories and year released.
	 * 
	 * @param name The movie name
	 * @param categories The type of movie
	 * @param yearReleased The year released.
	 */
	void add(String name, Set<String> categories, Integer yearReleased);

	/**
	 * Overloaded method to add a movie with only one category.
	 *  
	 * @param name The movie name
	 * @param category The type of movie
	 * @param yearReleased The year released.
	 */
	void add(String name, String category, Integer yearReleased);

	/**
	 * Find the movie by its name.
	 * 
	 * @param name The name of the movie to find.
	 * @return The found movie.
	 */
	Movie find(String name);

	/**
	 * Returns the list of movies with that category.
	 * 
	 * @param category The category to find.
	 * @return The list of movies corresponding to the category.
	 */
	List<String> getByCategory(String category);

	/**
	 * Deletes the movie with the given name.
	 * 
	 * @param name The name of the movie to delete.
	 * @return True if the movie was found - false otherwise.
	 */
	boolean delete(String name);
}