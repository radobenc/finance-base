package sk.radobenc.sandbox;

public class Recursion {

	static void countdown(final int n) {
		System.out.println(n);
		if (n > 0) {
			countdown(n - 1);
		}
		return;
	}

	public static void main(final String[] args) {
		countdown(10);
	}

}
