package models.dto;

import exceptions.ValidationException;

import static utils.Validator.isValidString;

/**
 * Data transfer object regarding {@link models.Author Author} information.
 *
 * @author Lucas da Paz
 */
public record AuthorDto(String name, String nationality) {

	/**
	 * @param name        The authors name.
	 * @param nationality The authors nationality.
	 * @throws ValidationException If any of the arguments is not a valid string, as specified
	 *                             in the {@link utils.Validator#isValidString isValidString} method.
	 */
	public AuthorDto {
		if (!isValidString(name))
			throw new ValidationException("O nome deve ser corretamente preenchido!");

		if (!isValidString(nationality))
			throw new ValidationException("A nacionalidade deve ser corretamente preenchida!");
	}
}
