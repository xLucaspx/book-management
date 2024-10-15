package models.dto;

import exceptions.ValidationException;
import models.Author;
import models.Format;
import models.Publisher;

import java.time.LocalDate;

import static utils.Isbn.convertToIsbn13;
import static utils.Isbn.isValidIsbn13;
import static utils.Validator.isValidString;

/**
 * Data transfer object regarding {@link models.Book Book} information.
 *
 * @author Lucas da Paz
 */
public record BookDto(String title, String isbn10, String isbn13, int pages, boolean read, Format format, Author author,
											Publisher publisher, LocalDate purchaseDate, float price) {
	/**
	 * @param title        The book title; must be a valid string.
	 * @param isbn10       The books ISBN-10. If this is valid and no ISBN-13 is provided, then an ISBN-13
	 *                     will be generated with the {@link utils.Isbn#convertToIsbn13 convertToIsbn13} method;
	 *                     if an ISBN-13 is provided, then the generated ISBN-13 must be equal to it.
	 * @param isbn13       The books ISBN-13; must be a valid ISBN and, if an ISBN-10 is provided, the
	 *                     generated ISBN-13 must be equal to the informed one.
	 * @param pages        The number of pages in the book; cannot be less than 0 and if it is exactly 0 it
	 *                     means that it is unknown or it was not provided.
	 * @param read         Boolean value that indicates whether the book has been read.
	 * @param format       The books {@link Format}; cannot be {@code null}.
	 * @param author       The books {@link Author}; cannot be {@code null}.
	 * @param publisher    The books {@link Publisher}; cannot be {@code null}.
	 * @param purchaseDate The books purchase date.
	 * @param price        The books price; cannot be less than 0.
	 * @throws ValidationException If any of the criteria defined above were not met. Strings are validated
	 *                             using the {@link utils.Validator#isValidString isValidString} method and both
	 *                             ISBN strings are validated using the {@link utils.Isbn#isValidIsbn10 isValidIsbn10}
	 *                             and {@link utils.Isbn#isValidIsbn10 isValidIsbn13} methods accordingly.
	 */
	public BookDto {
		if (!isValidString(title)) throw new ValidationException("O título do livro deve ser corretamente preenchido!");

		if (isValidString(isbn10)) {
			String generatedIsbn13 = convertToIsbn13(isbn10); // convertToIsbn13 calls on isValidIsbn10 internally

			if (!isValidString(isbn13)) isbn13 = generatedIsbn13;

			if (!isbn13.equals(generatedIsbn13))
				throw new ValidationException("O ISBN-10 e ISBN-13 informados não pertencem ao mesmo livro!");
		}

		if (!isValidIsbn13(isbn13)) throw new ValidationException("O ISBN-13 inserido não é válido!");

		if (pages < 0) throw new ValidationException("O número de páginas não pode ser menor que 0!");

		if (format == null) throw new ValidationException("O formato não pode ser nulo!");

		if (author == null) throw new ValidationException("O autor não pode ser nulo!");

		if (publisher == null) throw new ValidationException("A editora não pode ser nula!");

		if (price < 0) throw new ValidationException("O preço não pode ser menor que 0!");
	}
}
