package factory;

import services.AuthorServices;
import services.BookServices;
import services.GenreServices;
import services.PublisherServices;

import java.sql.Connection;

/**
 * Class responsible for instantiating service classes.
 *
 * @author Lucas da Paz
 */
public class ServicesFactory {
	private final Connection connection;

	/**
	 * Constructs an instance of {@link ServicesFactory}.
	 */
	public ServicesFactory() {
		this.connection = new ConnectionFactory().getConnection();
	}

	/**
	 * @return A new instance of {@link BookServices}.
	 */
	public BookServices createBookServices() {
		return new BookServices(connection);
	}

	/**
	 * @return A new instance of {@link AuthorServices}.
	 */
	public AuthorServices createAuthorServices() {
		return new AuthorServices(connection);
	}

	/**
	 * @return A new instance of {@link PublisherServices}.
	 */
	public PublisherServices createPublisherServices() {
		return new PublisherServices(connection);
	}

	/**
	 * @return A new instance of {@link GenreServices}.
	 */
	public GenreServices createGenreServices() {
		return new GenreServices(connection);
	}
}
