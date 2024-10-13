package models.filters;

public enum BookFilter {
	TITLE(1, "Título"), AUTHOR(2, "Autor");

	private final int code;
	private final String name;

	BookFilter(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	@Override
	public String toString() {
		return name;
	}
}
