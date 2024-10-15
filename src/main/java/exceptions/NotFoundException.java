package exceptions;

/**
 * Exception to be thrown when the search for a specific
 * data occurrence returns an empty result.
 *
 * @author Lucas da Paz
 */
public class NotFoundException extends RuntimeException {

	/**
	 * Constructs a new {@code NotFoundException} with the specified message.
	 * Calls super on {@link RuntimeException#RuntimeException(String) RuntimeException(String message)}.
	 *
	 * @param message The detail message; can be retrieved by the {@link #getMessage()} method.
	 */
	public NotFoundException(String message) {
		super(message);
	}
}
