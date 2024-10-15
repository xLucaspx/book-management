package controller;

import exceptions.ValidationException;
import models.Author;
import models.Book;
import models.Genre;
import models.Publisher;
import models.dto.BookDto;
import models.filters.BookFilter;
import services.BookServices;

import java.util.Set;

/**
 * Controller class for manipulating {@link Book} objects.
 *
 * @author Lucas da Paz
 */
public class BookController {
	private final BookServices bookServices;

	/**
	 * Constructs an instance of {@link BookController}
	 *
	 * @param services An instance of {@link BookServices}.
	 */
	public BookController(BookServices services) {
		this.bookServices = services;
	}

	/**
	 * Finds an {@link Book} whose ID corresponds to the one
	 * passed as an argument.
	 *
	 * @param id The ID of the desired book.
	 * @return The instance of book with the corresponding ID.
	 * @throws exceptions.NotFoundException If no book is found.
	 */
	public Book getById(int id) {
		return bookServices.getById(id);
	}

	/**
	 * Searches for all instances of {@link Book} whose {@link Author} is
	 * exactly the same as the one passed as an argument.
	 *
	 * @param author The reference author.
	 * @return A {@link Set} that contains all instances of book with the
	 * corresponding author.
	 */
	public Set<Book> getByAuthor(Author author) {
		return bookServices.getByAuthor(author);
	}

	/**
	 * Searches for all instances of {@link Book} whose {@link Publisher} is
	 * exactly the same as the one passed as an argument.
	 *
	 * @param publisher The reference publisher.
	 * @return A {@link Set} that contains all instances of book with the
	 * corresponding publisher.
	 */
	public Set<Book> getByPublisher(Publisher publisher) {
		return bookServices.getByPublisher(publisher);
	}

	/**
	 * Searches for all instances of {@link Book} whose {@link Genre} is
	 * exactly the same as the one passed as an argument.
	 *
	 * @param genre The reference genre.
	 * @return A {@link Set} that contains all instances of book with the
	 * corresponding genre.
	 */
	public Set<Book> getByGenre(Genre genre) {
		return bookServices.getByGenre(genre);
	}

	/**
	 * @return A {@link Set} the contains all instances of {@link Book}
	 * found in the database.
	 */
	public Set<Book> getAll() {
		return bookServices.getAll();
	}

	/**
	 * Selects the appropriate method based on a {@link BookFilter} constant
	 * and searches for all instances of {@link Book} whose corresponding
	 * attribute matches the input string passed as an argument.
	 *
	 * @param input  The input string used to filter.
	 * @param filter Enum constant used to select the appropriate filter method.
	 * @return A {@link Set} that contains all instances of book that matched the filter.
	 */
	public Set<Book> filter(String input, BookFilter filter) {
		return switch (filter) {
			case TITLE -> bookServices.filterByTitle(input);
			case AUTHOR -> bookServices.filterByAuthor(input);
			case ISBN -> bookServices.filterByIsbn(input.replaceAll("-", ""));
		};
	}

	/**
	 * Creates a new instance of {@link Book} and persists it
	 * in the database. Links it to each instance of {@link Genre}
	 * in the set passed as an argument.
	 *
	 * @param bookData Data to create the book with.
	 * @param genres   {@link Set} of genres to be linked to the book.
	 * @return The created instance of the object, with the generated keys.
	 */
	public Book create(BookDto bookData, Set<Genre> genres) {
		if (genres.isEmpty() || genres.size() > 3) {
			throw new ValidationException("Um livro deve possuir entre 1 e 3 categorias!");
		}

		return bookServices.create(new Book(-1, bookData, genres));
	}

	/**
	 * Updates the {@link Book} with the corresponding ID with the data passed
	 * as an argument. It also updates the links between the book and {@link Genre}
	 * occurrences by removing links with genres that do not appear in the set of
	 * genres passed as an argument and creating new links between those that do
	 * appear, if the links do not already exist.
	 *
	 * @param id        The ID of the book to update.
	 * @param bookData  The data to update the book with.
	 * @param newGenres {@link Set} of genres to link to the book. Existing links
	 *                  with genres that are not contained in this set will be removed,
	 *                  i.e., only genres contained in this set will be linked to the
	 *                  book after the execution of this method.
	 * @return The updated book instance.
	 */
	public Book update(int id, BookDto bookData, Set<Genre> newGenres) {
		return bookServices.update(new Book(id, bookData, newGenres));
	}

	/**
	 * Changes the read status of the {@link Book} passed as an argument, i.e., if
	 * {@code read} was {@code true}, it will be set to {@code false} and vice-versa.
	 * Persists the new status in the database.
	 *
	 * @param b The book being updated.
	 */
	public void updateReadStatus(Book b) {
		b.toggleRead();
		bookServices.updateReadStatus(b);
	}

	/**
	 * Deletes an instance of {@link Book} from the database.
	 *
	 * @param id The ID of the book to be deleted.
	 */
	public void delete(int id) {
		bookServices.delete(id);
	}
}
