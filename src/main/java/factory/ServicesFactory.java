package factory;

import services.AuthorServices;
import services.BookServices;
import services.GenreServices;
import services.PublisherServices;

import java.sql.Connection;

public class ServicesFactory {
	private final Connection connection;

	public ServicesFactory() {
		this.connection = new ConnectionFactory().getConection();
	}

	public BookServices createBookServices() {
		return new BookServices(connection);
	}

	public AuthorServices createAuthorServices() {
		return new AuthorServices(connection);
	}

	public PublisherServices createPublisherServices() {
		return new PublisherServices(connection);
	}

	public GenreServices createGenreServices() {
		return new GenreServices(connection);
	}
}
