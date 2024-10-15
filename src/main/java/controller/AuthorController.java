package controller;

import models.Author;
import models.dto.AuthorDto;
import services.AuthorServices;

import java.util.Set;

/**
 * Controller class for manipulating {@link Author} objects.
 *
 * @author Lucas da Paz
 */
public class AuthorController {
	private final AuthorServices authorServices;

	/**
	 * Constructs an instance of {@link AuthorController}
	 *
	 * @param services An instance of {@link AuthorServices}.
	 */
	public AuthorController(AuthorServices services) {
		this.authorServices = services;
	}

	/**
	 * Finds an {@link Author} whose ID corresponds to the one
	 * passed as an argument.
	 *
	 * @param id The ID of the desired author.
	 * @return The instance of author with the corresponding ID.
	 * @throws exceptions.NotFoundException If no author is found.
	 */
	public Author getById(int id) {
		return authorServices.getById(id);
	}

	/**
	 * @return A {@link Set} the contains all instances of {@link Author}
	 * found in the database.
	 */
	public Set<Author> getAll() {
		return authorServices.getAll();
	}

	/**
	 * Creates a new instance of {@link Author} and persists it
	 * in the database.
	 *
	 * @param authorData Data to create the author with.
	 * @return The created instance of the object, with the generated keys.
	 */
	public Author create(AuthorDto authorData) {
		return authorServices.create(new Author(-1, authorData));
	}

	/**
	 * Updates the {@link Author} with the corresponding ID with the
	 * data passed as an argument.
	 *
	 * @param id         The ID of the author to update.
	 * @param authorData The data to update the author with.
	 * @return The updated author instance.
	 */
	public Author update(int id, AuthorDto authorData) {
		return authorServices.update(new Author(id, authorData));
	}

	/**
	 * Deletes an instance of {@link Author} from the database.
	 *
	 * @param id The ID of the author to be deleted.
	 */
	public void delete(int id) {
		authorServices.delete(id);
	}
}
