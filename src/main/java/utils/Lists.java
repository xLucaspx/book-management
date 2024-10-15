package utils;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Utility class containing static methods related to lists.
 *
 * @author Lucas da Paz
 */
public class Lists {
	/**
	 * Converts a {@link Set} of elements into an ordered {@link List}.
	 * Uses a {@link LinkedList} as underlying data structure due to performance
	 * on iterating over elements; if random access or constant reordering is
	 * needed, perhaps an {@link java.util.ArrayList ArrayList} would be a better choice.
	 *
	 * @param <T> Type of the elements; must implement {@link Comparable}.
	 * @param set The set of elements to be converted and ordered.
	 * @return An ordered list constructed from the set.
	 */
	public static <T extends Comparable<? super T>> List<T> toSortedList(Set<T> set) {
		var list = new LinkedList<>(set);
		Collections.sort(list);
		return list;
	}
}
