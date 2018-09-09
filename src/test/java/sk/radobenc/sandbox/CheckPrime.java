package sk.radobenc.sandbox;

public class CheckPrime {

	static boolean isPrime(final long n) {
		for (long i = 2; i < n; i++) {
			if (n % i == 0) {
				return false;
			}
		}
		return true;

	}

	public static void main(final String[] args) {
		System.out.println(isPrime(37));
		System.out.println(isPrime(100));
	}

}
