package models.filters;

/**
 * Represents possible filters to be used when searching
 * for {@link models.Book books}.
 *
 * @author Lucas da Paz
 */
public enum BookFilter {
	TITLE(1, "Título"), AUTHOR(2, "Autor"), ISBN(3, "ISBN");

	private final int code;
	private final String name;

	BookFilter(int code, String name) {
		this.code = code;
		this.name = name;
	}

	/**
	 * @return The code associated with the filter.
	 */
	public int getCode() {
		return code;
	}

	/**
	 * Returns the name of this enum constant as it should appear
	 * for the user.
	 *
	 * @return This enum constant as a string.
	 */
	@Override
	public String toString() {
		return name;
	}
}
