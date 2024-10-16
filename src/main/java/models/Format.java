package models;

/**
 * Enum class that represents available book formats.
 *
 * @author Lucas da Paz
 */
public enum Format {
	HARDCOVER(1, "Capa dura"), PAPERBACK(2, "Brochura"), EBOOK(3, "Ebook"), BOX(4, "Box");

	private final int code;
	private final String name;

	Format(int code, String name) {
		this.code = code;
		this.name = name;
	}

	/**
	 * @return The code of this {@link Format}, in sync with the database specification.
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @return The description of this {@link Format}, as it should be displayed for the user.
	 */
	@Override
	public String toString() {
		return name;
	}

	/**
	 * @return This {@link Format} constant formatted as JSON.
	 */
	public String formatAsJson() {
		return String.format("Format: { code: %d, name: \"%s\" }", code, name);
	}
}
