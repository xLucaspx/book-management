package models;

/**
 * <p>Represents the Publisher entity.</p>
 * <p>Changes in an instance of this class will <strong>not</strong>
 * be automatically persisted in the database; for persistence to
 * happen, a {@link services.Services Services} class is needed.
 * Usually, persistence of this class is performed by an instance of
 * {@link services.PublisherServices PublisherServices} called through the
 * {@link controller.PublisherController PublisherController} class.</p>
 *
 * @author Lucas da Paz
 */
public class Publisher implements Comparable<Publisher> {
	private int id;
	private String name;
	private int booksOwned;

	/**
	 * Constructor using all fields; used when retrieving an occurrence
	 * of {@link Publisher} from the database.
	 *
	 * @param id         The publisher iD.
	 * @param name       The publisher name.
	 * @param booksOwned Number of books owned that were published by this publisher.
	 */
	public Publisher(int id, String name, int booksOwned) {
		this.id = id;
		this.name = name;
		this.booksOwned = booksOwned;
	}

	/**
	 * Constructor for creating or updating a {@link Publisher}. If this publisher is
	 * being created, only the {@code name} is necessary; if it is being updated, then
	 * the {@code id} is required to identify the occurrence to be updated. In both cases,
	 * the {@code booksOwned} attribute of this instance will be {@code 0}, as this attribute
	 * depends on information provided by the database.
	 *
	 * @param id   The publisher ID if it exists, or {@code -1} if it's being created.
	 * @param name The publisher name.
	 */
	public Publisher(int id, String name) {
		this(id, name, 0);
	}

	/**
	 * @return The ID of this {@link Publisher}.
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return The name of this {@link Publisher}.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return Number of books owned that were published by this {@link Publisher}.
	 */
	public int getBooksOwned() {
		return booksOwned;
	}

	/**
	 * @return A hash code value for this {@link Publisher} using the {@code id} and
	 * {@code name} attributes.
	 */
	@Override
	public int hashCode() {
		return id * name.hashCode();
	}

	/**
	 * Indicates whether some other object is "equal to" this {@link Publisher} instance.
	 * Will return {@code false} if the object passed as an argument is {@code null} or is
	 * not an instance of {@code Publisher}.
	 *
	 * @param o The object to check for equality.
	 * @return {@code true} if this publisher is the same as the one passed as an argument, i.e.,
	 * if its {@code id} and {@code name} attributes are equal; {@code false} otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null) return false;
		if (!(o instanceof Publisher p)) return false;

		return id == p.getId() && name.equals(p.getName());
	}

	/**
	 * @return The description of this {@link Publisher}, as it should be displayed for the user.
	 */
	@Override
	public String toString() {
		return name;
	}

	/**
	 * Compares this object with another instance of {@link Publisher} for order. Uses the
	 * {@link String#compareToIgnoreCase} method to compare the name of this publisher to
	 * the name of the one passed as an argument.
	 *
	 * @param p The publisher to be compared.
	 * @return A negative integer, zero, or a positive integer as this object is less than, equal to
	 * or greater than the specified publisher.
	 */
	@Override
	public int compareTo(Publisher p) {
		return name.compareToIgnoreCase(p.getName());
	}

	/**
	 * @return This {@link Publisher} information formatted as JSON.
	 */
	public String formatAsJson() {
		return String.format("Publisher: { id: %d, name: \"%s\", booksOwned: %d }", id, name, booksOwned);
	}
}
