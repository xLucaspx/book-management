package factory;

import controller.AuthorController;
import controller.BookController;
import controller.GenreController;
import controller.PublisherController;

/**
 * Class responsible for instantiating controller classes.
 *
 * @author Lucas da Paz
 */
public class ControllerFactory {
	private final ServicesFactory servicesFactory;

	/**
	 * Constructs an instance of {@link ControllerFactory}.
	 */
	public ControllerFactory() {
		this.servicesFactory = new ServicesFactory();
	}

	/**
	 * @return A new instance of {@link BookController}.
	 */
	public BookController getBookController() {
		return new BookController(servicesFactory.createBookServices());
	}

	/**
	 * @return A new instance of {@link AuthorController}.
	 */
	public AuthorController getAuthorController() {
		return new AuthorController(servicesFactory.createAuthorServices());
	}

	/**
	 * @return A new instance of {@link PublisherController}.
	 */
	public PublisherController getPublisherController() {
		return new PublisherController(servicesFactory.createPublisherServices());
	}

	/**
	 * @return A new instance of {@link GenreController}.
	 */
	public GenreController getGenreController() {
		return new GenreController(servicesFactory.createGenreServices());
	}
}
