package lab17;

import java.util.Date;
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
	 * @param dateReleased The year released.
	 * @return The newly added row represented as an object.
	 * @throws StorageException In case of error interacting with database.
	 */
	Movie add(String name, Set<String> categories, Date dateReleased) throws StorageException;

	/**
	 * Overloaded method to add a movie with only one category.
	 *  
	 * @param name The movie name
	 * @param category The type of movie
	 * @param yearReleased The year released.
	 * @return The newly added row represented as an object.
	 * @throws StorageException In case of error interacting with database.
	 */
	public Movie add(String name, String category, Date dateReleased) throws StorageException;

	/**
	 * Find the movie by its name.
	 * 
	 * @param name The name of the movie to find.
	 * @return The found movie or null if not found.
	 * @throws StorageException In case of error interacting with database.
	 */
	Movie find(String name) throws StorageException;

	/**
	 * Returns the list of movies with that category.
	 * 
	 * @param category The category to find.
	 * @return The set of movies corresponding to the category or an empty set if none found.
	 * @throws StorageException In case of error interacting with database.
	 */
     Set<String> getByCategory(String category) throws StorageException;

     /**
      * Updates the movie name or date released as per the given moie object.
      * 
      * @param movie The updates to make (only considers movie name or date released).
	  * @throws StorageException In case of error interacting with database.
      */
     void update(Movie movie) throws StorageException;
     
	/**
	 * Deletes the movie with the given name.
	 * 
	 * @param name The name of the movie to delete.
	 * @throws StorageException In case of error with storage device.
	 */
	void delete(String name) throws StorageException;
	
	/**
	 * Clears entire movie database
	 * @throws StorageException In case of error interacting with database.
	 */
	void clear() throws StorageException;
}