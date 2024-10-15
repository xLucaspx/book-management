package utils;

/**
 * Utility class containing static methods for validating data.
 *
 * @author Lucas da Paz
 */
public class Validator {

	/**
	 * Checks the validity of the string passed as argument. To be
	 * considered valid the string must not be <code>null</code>,
	 * {@link String#isEmpty() empty} or {@link String#isBlank() blank}.
	 *
	 * @param s The string to be validated.
	 * @return <code>true</code> if the string is valid, <code>false</code> otherwise.
	 */
	public static boolean isValidString(String s) {
		if (s == null) return false;

		return !(s.isEmpty() || s.isBlank());
	}
}
