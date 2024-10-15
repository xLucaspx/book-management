package controller;

import exceptions.ValidationException;
import models.Genre;
import services.GenreServices;

import java.util.Set;

import static utils.Validator.isValidString;

/**
 * Controller class for manipulating {@link Genre} objects.
 *
 * @author Lucas da Paz
 */
public class GenreController {
	private final GenreServices genreServices;

	/**
	 * Constructs an instance of {@link AuthorController}
	 *
	 * @param services An instance of {@link GenreServices}.
	 */
	public GenreController(GenreServices services) {
		this.genreServices = services;
	}

	/**
	 * Finds an {@link Genre} whose ID corresponds to the one
	 * passed as an argument.
	 *
	 * @param id The ID of the desired genre.
	 * @return The instance of genre with the corresponding ID.
	 * @throws exceptions.NotFoundException If no genre is found.
	 */
	public Genre getById(int id) {
		return genreServices.getById(id);
	}

	/**
	 * @return A {@link Set} the contains all instances of {@link Set}
	 * found in the database.
	 */
	public Set<Genre> getAll() {
		return genreServices.getAll();
	}

	/**
	 * Creates a new instance of {@link Genre} and persists it
	 * in the database.
	 *
	 * @param name The name of the genre to be created.
	 * @return The created instance of the object, with the generated keys.
	 * @throws ValidationException If the name is not a valid string as specified
	 *                             by {@link utils.Validator#isValidString isValidString}.
	 */
	public Genre create(String name) {
		if (!isValidString(name)) {
			throw new ValidationException("O nome da categoria deve ser preenchido corretamente!");
		}

		return genreServices.create(new Genre(-1, name));
	}

	/**
	 * Updates the {@link Genre} with the corresponding ID with the
	 * data passed as an argument.
	 *
	 * @param id      The ID of the genre to update.
	 * @param newName The name of the genre to be created.
	 * @return The updated genre instance.
	 * @throws ValidationException If the name is not a valid string as specified
	 *                             by {@link utils.Validator#isValidString isValidString}.
	 */
	public Genre update(int id, String newName) {
		if (!isValidString(newName)) {
			throw new ValidationException("O nome da categoria deve ser preenchido corretamente!");
		}

		return genreServices.update(new Genre(id, newName));
	}

	/**
	 * Deletes an instance of {@link Genre} from the database.
	 *
	 * @param id The ID of the genre to be deleted.
	 */
	public void delete(int id) {
		genreServices.delete(id);
	}
}
