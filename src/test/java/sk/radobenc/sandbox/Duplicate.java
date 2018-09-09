package sk.radobenc.sandbox;

import java.util.HashSet;
import java.util.Set;

public class Duplicate {

	static boolean hasDuplicates(final int[] array) {
		final Set<Integer> s = new HashSet<Integer>();
		for (final int element : array) {
			if (!s.add(element)) {
				return true;
			}
		}
		return false;
	}

	public static void main(final String[] args) {
		System.out.println(hasDuplicates(new int[] { 0, 11, 11, 2 }));
		System.out.println(hasDuplicates(new int[] { 0, 11, 12, 2 }));
	}

}
