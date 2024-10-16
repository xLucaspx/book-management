package models;

import models.dto.AuthorDto;

/**
 * <p>Represents the Author entity.</p>
 * <p>Changes in an instance of this class will <strong>not</strong>
 * be automatically persisted in the database; for persistence to
 * happen, a {@link services.Services Services} class is needed.
 * Usually, persistence of this class is performed by an instance of
 * {@link services.AuthorServices AuthorServices} called through the
 * {@link controller.AuthorController AuthorController} class.</p>
 *
 * @author Lucas da Paz
 */
public class Author implements Comparable<Author> {
	private int id;
	private String name;
	private String nationality;
	private int booksOwned;

	/**
	 * Constructor using all fields; used when retrieving an occurrence
	 * of {@link Author} from the database.
	 *
	 * @param id          The author ID.
	 * @param name        The author name.
	 * @param nationality The author nationality
	 * @param booksOwned  Number of books owned that were written by this author.
	 */
	public Author(int id, String name, String nationality, int booksOwned) {
		this.id = id;
		this.name = name;
		this.nationality = nationality;
		this.booksOwned = booksOwned;
	}

	/**
	 * Constructor for creating or updating an {@link Author}. If this author is
	 * being created, only the {@code data} is necessary; if it is being updated, then
	 * the {@code id} is required to identify the occurrence to be updated. In both cases,
	 * the {@code booksOwned} attribute of this instance will be {@code 0}, as this attribute
	 * depends on information provided by the database.
	 *
	 * @param id   The author ID if it exists, or {@code -1} if it's being created.
	 * @param data {@link AuthorDto#AuthorDto AuthorDto} object containing the author data.
	 */
	public Author(int id, AuthorDto data) {
		this(id, data.name(), data.nationality(), 0);
	}

	/**
	 * @return The ID of this {@link Author}.
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return The name of this {@link Author}.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return The nationality of this {@link Author}.
	 */
	public String getNationality() {
		return nationality;
	}

	/**
	 * @return Number of books owned that were written by this {@link Author}.
	 */
	public int getBooksOwned() {
		return booksOwned;
	}

	/**
	 * @return A hash code value for this {@link Author} using the {@code id},
	 * {@code name} and {@code nationality} attributes.
	 */
	@Override
	public int hashCode() {
		return id * name.hashCode() * nationality.hashCode();
	}

	/**
	 * Indicates whether some other object is "equal to" this {@link Author} instance.
	 * Will return {@code false} if the object passed as an argument is {@code null} or is
	 * not an instance of {@code Author}.
	 *
	 * @param o The object to check for equality.
	 * @return {@code true} if this author is the same as the one passed as an argument, i.e.,
	 * if its {@code id}, {@code name} and {@code nationality} attributes are equal;
	 * {@code false} otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null) return false;
		if (!(o instanceof Author a)) return false;

		return id == a.getId() && name.equals(a.getName()) && nationality.equals(a.getNationality());
	}

	/**
	 * @return The description of this {@link Author}, as it should be displayed for the user.
	 */
	@Override
	public String toString() {
		return name;
	}

	/**
	 * Compares this object with another instance of {@link Author} for order. Uses the
	 * {@link String#compareToIgnoreCase} method to compare the name of this author to
	 * the name of the one passed as an argument.
	 *
	 * @param a The author to be compared.
	 * @return A negative integer, zero, or a positive integer as this object is less than, equal to
	 * or greater than the specified author.
	 */
	@Override
	public int compareTo(Author a) {
		return name.compareToIgnoreCase(a.getName());
	}

	/**
	 * @return This {@link Author} information formatted as JSON.
	 */
	public String formatAsJson() {
		return String.format("Author { id: %d, name: \"%s\", nationality: \"%s\", booksOwned: %d }",
			id,
			name,
			nationality,
			booksOwned
		);
	}
}
