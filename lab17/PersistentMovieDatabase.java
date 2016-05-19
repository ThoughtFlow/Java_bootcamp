package lab17;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class PersistentMovieDatabase implements MovieDatabase {

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection("jdbc:mysql://localhost:3306/movie?useSSL=false", "nick", "password");
	}
	
	public PersistentMovieDatabase(boolean isDatabaseCleared) throws StorageException {
		if (isDatabaseCleared) {
			clear();
		}
	}
	
	@Override
	public Movie add(String name, Set<String> categories, Date dateReleased) throws StorageException {

		long movieId = addMovie(name, dateReleased);
		
		for (String nextCategory : categories) {
			long nextCategoryId = getOrAddCategoryId(nextCategory);
			addMovieCategory(movieId, nextCategoryId);
		}

		return new Movie(movieId, categories, name, dateReleased);
	}

	@Override
	public Movie add(String name, String category, Date dateReleased) throws StorageException {

		Set<String> categories = new HashSet<>();
		categories.add(category);
		
		return add(name, categories, dateReleased);
	}

	@Override
	public Movie find(String name) throws StorageException{
		return getMovie(name);
	}
	
	@Override
	public Set<String> getByCategory(String category) throws StorageException {
		return getMovieByCategory(category);
	}

	@Override
	public void update(Movie movie) throws StorageException {
		updateMovie(movie);
	}
	
	@Override
	public void delete(String name) throws StorageException {
		deleteMovie(name);
	}
	
	@Override
	public void clear() throws StorageException {
		deleteAll();
	}

	private long addMovie(String name, Date yearReleased) throws StorageException {

		long newId;
		
		try (Connection connection = getConnection();
			 PreparedStatement statement = connection.prepareStatement("INSERT INTO Movie (name, release_date) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);) {

			int index = 1;
			statement.setString(index++, name);
			statement.setDate(index++, new java.sql.Date(yearReleased.getTime()));
			
			statement.executeUpdate();
	        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                newId = generatedKeys.getLong(1);
	            }
	            else {
	    			throw new StorageException("Error - could not get movie id");
	            }
	        }
		}
		catch (SQLException exception) {
			throw new StorageException("Error during add: " + exception.getMessage());
		}
		
		return newId;
	}
	
	private void addMovieCategory(long movie_id, long category_id) throws StorageException {

		try (Connection connection = getConnection();
			 PreparedStatement statement = connection.prepareStatement("INSERT INTO Movie_category (movie_id, category_id) VALUES (?, ?)");) {

			int index = 1;
			statement.setLong(index++, movie_id);
			statement.setLong(index++, category_id);
			
			statement.executeUpdate();
		}
		catch (SQLException exception) {
			throw new StorageException("Error during add: " + exception.getMessage());
		}
	}
	
	private Movie getMovie(String name) throws StorageException {

		Movie foundMovie = null;
		Set<String> categories = new HashSet<>();
		Date releaseDate = null;
		long id = 0;

		try (Connection connection = getConnection();
			 PreparedStatement statement = connection.prepareStatement("SELECT a.id, a.release_date FROM Movie a, Category b, Movie_category c WHERE a.name=? AND c.movie_id = a.id AND c.category_id = b.id");	) {

			statement.setString(1, name);
			ResultSet resultSet = statement.executeQuery();

			
			if (resultSet.next()) {
				if(releaseDate == null) {
					int index = 1;
					id = resultSet.getLong(index++);
					releaseDate = new Date(resultSet.getDate(index++).getTime());
				}

				categories.add(resultSet.getString(1));
			}
			
			if (categories.size() > 0) {
				foundMovie = new Movie(id, categories, name, releaseDate);
			}
		}
		catch (SQLException exception) {
			throw new StorageException("Error during get: " + exception.getMessage());
		}

		return foundMovie;
	}
	
	private Set<String> getMovieByCategory(String category) throws StorageException {

		Set<String> categories = new HashSet<>();

		try (Connection connection = getConnection();
			 PreparedStatement statement = connection.prepareStatement("SELECT a.name FROM Movie a, Category b, Movie_category c WHERE b.name=? AND c.movie_id = a.id AND c.category_id = b.id");	) {

			statement.setString(1, category);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				categories.add(resultSet.getString(1));
			}
		}
		catch (SQLException exception) {
			throw new StorageException("Error during get: " + exception.getMessage());
		}
		
		return categories;

	}
	
	private long getCategoryId(String categoryName) throws StorageException {

		long categoryId = 0;

		try (Connection connection = getConnection();
			PreparedStatement statement = connection.prepareStatement("SELECT id FROM Category WHERE name=?");	) {

			statement.setString(1, categoryName);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				categoryId = resultSet.getLong(1);
			}
		}
		catch (SQLException exception) {
			throw new StorageException("Error during get: " + exception.getMessage());
		}

		return categoryId;
	}
	
	private long addCategoryId(String categoryName) throws StorageException {

		long categoryId;

		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement("INSERT INTO category (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);) {

			statement.setString(1, categoryName);

			statement.executeUpdate();

	        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                categoryId = generatedKeys.getLong(1);
	            }
	            else {
	    			throw new StorageException("Error - could not get catgoryId");
	            }
	        }
		}
		catch (SQLException exception) {
			throw new StorageException("Error during add category " + exception.getMessage());
		}

		return categoryId;
	}

	private long getOrAddCategoryId(String category) throws StorageException {
		long categoryId = getCategoryId(category);
		
		if (categoryId == 0) {
			categoryId = addCategoryId(category);
		}
		
		return categoryId;
	}
	
	private void updateMovie(Movie movie) throws StorageException {
		try (Connection connection = getConnection();
				PreparedStatement updateMovie = connection.prepareStatement("UPDATE Movie set name=?, release_date=? WHERE id=?")) {

			int index = 1;
			updateMovie.setString(index++, movie.getName());
			updateMovie.setDate(index++, new java.sql.Date(movie.getReleaseDate().getTime()));
			updateMovie.setLong(index++, movie.getId());
			
			updateMovie.executeUpdate();
		}
		catch (SQLException exception) {
			throw new StorageException("Error during update: " + exception.getMessage());
		}
	}
	
	private void deleteMovie(String name) throws StorageException {
		try (Connection connection = getConnection();
				PreparedStatement deleteMovieCategoryStatement = connection.prepareStatement("DELETE b FROM Movie_category b JOIN Movie a ON a.id = b.movie_id WHERE a.name = ?");
			    PreparedStatement deleteMovieStatement = connection.prepareStatement("DELETE FROM Movie WHERE name = ?")) {

			deleteMovieCategoryStatement.setString(1,  name);
			deleteMovieCategoryStatement.executeUpdate();
			
			deleteMovieStatement.setString(1,  name);
			deleteMovieStatement.executeUpdate();
		}
		catch (SQLException exception) {
			throw new StorageException("Error during delete: " + exception.getMessage());
		}
	}
	
	private void deleteAll() throws StorageException {

		try (Connection connection = getConnection();
				PreparedStatement deleteMovieCategoryStatement = connection.prepareStatement("DELETE FROM movie_category");
				PreparedStatement deleteMovieStatement = connection.prepareStatement("DELETE FROM movie");
				PreparedStatement deleteCategoryStatement = connection.prepareStatement("DELETE FROM category")) {

			deleteMovieCategoryStatement.executeUpdate();
			deleteMovieStatement.executeUpdate();
			deleteCategoryStatement.executeUpdate();
		}
		catch (SQLException exception) {
			throw new StorageException("Error during truncate: " + exception.getMessage());
		}
	}
}
