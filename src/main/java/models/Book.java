package models;

import models.dto.BookDto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>Represents the Book entity.</p>
 * <p>Changes in an instance of this class will <strong>not</strong>
 * be automatically persisted in the database; for persistence to
 * happen, a {@link services.Services Services} class is needed.
 * Usually, persistence of this class is performed by an instance of
 * {@link services.BookServices BookServices} called through the
 * {@link controller.BookController BookController} class.</p>
 *
 * @author Lucas da Paz
 */
public class Book implements Comparable<Book> {
	private int id;
	private int pages;
	private float price;
	private boolean read;
	private String title;
	private String isbn10;
	private String isbn13;
	private String review;
	private Format format;
	private Author author;
	private Publisher publisher;
	private LocalDate purchaseDate;
	private Set<Genre> genres;

	/**
	 * Constructor using required fields; used when retrieving an occurrence of
	 * {@link Book} from the database.
	 *
	 * @param id        ID of the book.
	 * @param title     Title of the book.
	 * @param isbn13    ISBN-13 of the book.
	 * @param pages     Number of pages in the book.
	 * @param read      The reading status of the book.
	 * @param format    The {@link Format} of the book.
	 * @param author    The {@link Author} of the book.
	 * @param publisher The {@link Publisher} of the book.
	 * @param price     Price of the book.
	 */
	public Book(int id, String title, String isbn13, int pages, boolean read, Format format, Author author,
							Publisher publisher, float price) {
		this.id = id;
		this.title = title;
		this.isbn13 = isbn13;
		this.pages = pages;
		this.read = read;
		this.format = format;
		this.author = author;
		this.publisher = publisher;
		this.price = price;
		this.genres = new HashSet<>();
	}

	/**
	 * Constructor for creating or updating an {@link Book}. If this book is
	 * being created, only the {@code data} and the set of {@code genres} is
	 * necessary; if it is being updated, then the {@code id} is required to
	 * identify the occurrence to be updated.
	 *
	 * @param id     The book ID if it exists, or {@code -1} if it's being created.
	 * @param data   {@link BookDto#BookDto BookDto} object containing the book data.
	 * @param genres {@link Set} of {@link Genre genres} to which the book belongs.
	 */
	public Book(int id, BookDto data, Set<Genre> genres) {
		this(id,
			data.title(),
			data.isbn13(),
			data.pages(),
			data.read(),
			data.format(),
			data.author(),
			data.publisher(),
			data.price()
		);
		isbn10 = data.isbn10();
		purchaseDate = data.purchaseDate();
		this.genres = genres;
	}

	/**
	 * @return The ID of this {@link Book}.
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return The title of this {@link Book}.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return The ISBN-10 of this {@link Book}.
	 */
	public String getIsbn10() {
		return isbn10;
	}

	/**
	 * Sets the ISBN-10 of this {@link Book}.
	 *
	 * @param isbn10 The ISBN-10 of this book.
	 */
	public void setIsbn10(String isbn10) {
		this.isbn10 = isbn10;
	}

	/**
	 * @return The ISBN-13 of this {@link Book}.
	 */
	public String getIsbn13() {
		return isbn13;
	}

	/**
	 * @return The number of pages in this {@link Book}.
	 */
	public int getPages() {
		return pages;
	}

	/**
	 * @return The reading status of this {@link Book}.
	 */
	public boolean isRead() {
		return read;
	}

	/**
	 * @return The {@link Author} of this {@link Book}.
	 */
	public Author getAuthor() {
		return author;
	}

	/**
	 * @return The {@link Publisher} of this {@link Book}.
	 */
	public Publisher getPublisher() {
		return publisher;
	}

	/**
	 * @return The {@link Format} of this {@link Book}.
	 */
	public Format getFormat() {
		return format;
	}

	/**
	 * Adds a new genre to the {@link Set} of {@link Genre genres} to which
	 * this {@link Book} belongs.
	 *
	 * @param genre The genre to be added.
	 */
	public void addGenre(Genre genre) {
		genres.add(genre);
	}

	/**
	 * @return The {@link Set} of {@link Genre genres} to which
	 * the {@link Book} belongs.
	 */
	public Set<Genre> getGenres() {
		return Collections.unmodifiableSet(genres);
	}

	/**
	 * @return The purchase date of this {@link Book}.
	 */
	public LocalDate getPurchaseDate() {
		return purchaseDate;
	}

	/**
	 * Sets the purchase date of this {@link Book}.
	 *
	 * @param purchaseDate The purchase date of this book.
	 */
	public void setPurchaseDate(LocalDate purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	/**
	 * @return The price of this {@link Book}.
	 */
	public float getPrice() {
		return price;
	}

	/**
	 * @return The user review of this {@link Book}.
	 */
	public String getReview() {
		return review;
	}

	/**
	 * Sets the user review of this {@link Book}.
	 *
	 * @param review The user review of this book.
	 */
	public void setReview(String review) {
		this.review = review;
	}

	/**
	 * Changes the read status of this {@link Book}, i.e., if {@code read} was
	 * {@code true}, it will be set to {@code false} and vice-versa.
	 */
	public void toggleRead() {
		this.read = !read;
	}

	/**
	 * @return A hash code value for this {@link Book} using the {@code id},
	 * {@code title} and {@code Isbn13} attributes, as well as the hash codes
	 * of the {@link Author}, {@link Publisher} and {@link Format} of the book.
	 */
	@Override
	public int hashCode() {
		return id * title.hashCode() * isbn13.hashCode() * author.hashCode() * publisher.hashCode() * format.hashCode();
	}

	/**
	 * Indicates whether some other object is "equal to" this {@link Book} instance.
	 * Will return {@code false} if the object passed as an argument is {@code null} or is
	 * not an instance of {@code Book}.
	 *
	 * @param o The object to check for equality.
	 * @return {@code true} if this book is the same as the one passed as an argument, i.e.,
	 * if its {@code id}, {@code title}, {@code Isbn13}, {@link Author} and {@link Format}
	 * attributes are equal; {@code false} otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null) return false;
		if (!(o instanceof Book b)) return false;

		return id == b.getId() && title.equals(b.getTitle()) && isbn13.equals(b.getIsbn13()) && author.equals(b.getAuthor()) && format.equals(
			b.getFormat());
	}

	/**
	 * @return The description of this {@link Book}, as it should be displayed for the user.
	 */
	@Override
	public String toString() {
		return title;
	}

	/**
	 * Compares this object with another instance of {@link Book} for order. Uses the
	 * {@link String#compareToIgnoreCase} method to compare the title of this book to
	 * the title of the one passed as an argument.
	 *
	 * @param b The book to be compared.
	 * @return A negative integer, zero, or a positive integer as this object is less than, equal to
	 * or greater than the specified book.
	 */
	@Override
	public int compareTo(Book b) {
		return title.compareToIgnoreCase(b.getTitle());
	}

	/**
	 * @return This {@link Book} information formatted as JSON.
	 */
	public String formatAsJson() {
		StringBuilder strGenres = new StringBuilder();
		for (Genre g : genres) strGenres.append(String.format("%3s%s,\n", "	", g.formatAsJson()));

		return """
			Book: {
				id: %d,
				title: "%s",
				ISBN-10: "%s",
				ISBN-13: "%s",
				pages: %d,
				read: %b,
				purchaseDate: "%s",
				price "R$ %.2f",
				%s,
				%s,
				%s,
				genres: {\n%s  }
			}
			""".formatted(id,
			title,
			(isbn10 != null ? isbn10 : "n/c"),
			isbn13,
			pages,
			read,
			(purchaseDate != null ? DateTimeFormatter.ofPattern("dd/MM/yyyy").format(purchaseDate) : "n/c"),
			price,
			format.formatAsJson(),
			author.formatAsJson(),
			publisher.formatAsJson(),
			strGenres.toString()
		);
	}
}
