package models.dto;

import exceptions.ValidationException;

import static utils.Validator.isValidString;

public record AuthorDto(String name, String nationality) {
	public AuthorDto {
		if (!isValidString(name))
			throw new ValidationException("O nome deve ser corretamente preenchido!");

		if (!isValidString(nationality))
			throw new ValidationException("A nacionalidade deve ser corretamente preenchida!");
	}
}
