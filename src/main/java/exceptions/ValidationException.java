package exceptions;

/**
 * Exception to be thrown when any type of validation fails.
 *
 * @author Lucas da Paz
 */
public class ValidationException extends RuntimeException {

	/**
	 * Constructs a new {@code ValidationException} with the specified message.
	 * Calls super on {@link RuntimeException#RuntimeException(String) RuntimeException(String message)}.
	 *
	 * @param message The detail message; can be retrieved by the {@link #getMessage()} method.
	 */
	public ValidationException(String message) {
		super(message);
	}
}
