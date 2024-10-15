package controller;

import exceptions.ValidationException;
import models.Publisher;
import services.PublisherServices;

import java.util.Set;

import static utils.Validator.isValidString;

/**
 * Controller class for manipulating {@link Publisher} objects.
 *
 * @author Lucas da Paz
 */
public class PublisherController {
	private final PublisherServices publisherServices;

	/**
	 * Constructs an instance of {@link PublisherController}
	 *
	 * @param services An instance of {@link PublisherServices}.
	 */
	public PublisherController(PublisherServices services) {
		this.publisherServices = services;
	}

	/**
	 * Finds an {@link Publisher} whose ID corresponds to the one
	 * passed as an argument.
	 *
	 * @param id The ID of the desired publisher.
	 * @return The instance of publisher with the corresponding ID.
	 * @throws exceptions.NotFoundException If no publisher is found.
	 */
	public Publisher getById(int id) {
		return publisherServices.getById(id);
	}

	/**
	 * @return A {@link Set} the contains all instances of {@link Publisher}
	 * found in the database.
	 */
	public Set<Publisher> getAll() {
		return publisherServices.getAll();
	}

	/**
	 * Creates a new instance of {@link Publisher} and persists it
	 * in the database.
	 *
	 * @param name The name of the publisher to be created.
	 * @return The created instance of the object, with the generated keys.
	 * @throws ValidationException If the name is not a valid string as specified
	 *                             by {@link utils.Validator#isValidString isValidString}.
	 */
	public Publisher create(String name) {
		if (!isValidString(name)) throw new ValidationException("O nome da editora deve ser preenchido corretamente!");

		return publisherServices.create(new Publisher(-1, name));
	}

	/**
	 * Updates the {@link Publisher} with the corresponding ID with the
	 * data passed as an argument.
	 *
	 * @param id      The ID of the publisher to update.
	 * @param newName The name of the publisher to be created.
	 * @return The updated publisher instance.
	 * @throws ValidationException If the name is not a valid string as specified
	 *                             by {@link utils.Validator#isValidString isValidString}.
	 */
	public Publisher update(int id, String newName) {
		if (!isValidString(newName)) throw new ValidationException("O nome da editora deve ser preenchido corretamente!");

		return publisherServices.update(new Publisher(id, newName));
	}

	/**
	 * Deletes an instance of {@link Publisher} from the database.
	 *
	 * @param id The ID of the publisher to be deleted.
	 */
	public void delete(int id) {
		publisherServices.delete(id);
	}
}
