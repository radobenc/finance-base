package sk.radobenc.finance;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FinanceUtils {

	public static abstract class LazyValue<V> {

		private V v;

		public abstract V create();

		public final V get() {
			if (null == v) {
				this.v = create();
			}
			return v;
		}

	}

	public static abstract class SimpleCache<K, V> {

		private final Map<K, V> map = new HashMap<K, V>();

		public abstract V createEntry(K key);

		public V get(final K key) {
			V v = map.get(key);
			if (null == v) {
				v = createEntry(key);
				map.put(key, v);
			}
			return v;
		}
	}

	public static String leftPad(final Number number, final int length) {
		return leftPad(Objects.toString(number), length, '0');
	}

	public static String leftPad(final String string, final int length) {
		return leftPad(string, length, ' ');
	}

	public static String leftPad(final String string, final int length, final char pad) {
		if (null == string || 0 == string.length()) {
			throw new IllegalArgumentException(String.format("string = '%s'", string));
		}
		if (length < string.length()) {
			throw new IllegalArgumentException(String.format("length = %d", length));
		}
		final StringBuilder sb = new StringBuilder();
		for (int i = string.length(); i < length; i++) {
			sb.append(pad);
		}
		sb.append(string);
		return sb.toString();
	}

	public static <T extends Number & Comparable<T>> BigDecimal median(final Collection<T> numbers) {
		if (null == numbers || numbers.isEmpty()) {
			throw new IllegalArgumentException("Cannot compute median on null or empty collection of numbers");
		}
		final List<T> numberList = new ArrayList<T>(numbers);
		Collections.sort(numberList);
		final int middle = numberList.size() / 2;

		if (numberList.size() % 2 == 0) {
			return new BigDecimal(numberList.get(middle).toString())
					.add(new BigDecimal(numberList.get(middle - 1).toString())).divide(new BigDecimal(2));
		}
		return new BigDecimal(numberList.get(middle).toString());
	}

	public static BigDecimal monthlyIncome(final BigDecimal dailyRate, final int year, final int month,
			final boolean incudeWeekends) {
		final int days = incudeWeekends ? FinDate.daysInMonth(year, month) : FinDate.workingDaysInMonth(year, month);
		return dailyRate.multiply(new BigDecimal(days).round(MathContext.UNLIMITED));
	}

	public static final String version() {
		final Calendar c = Calendar.getInstance();
		return new StringBuilder().append(c.get(Calendar.YEAR)).append(leftPad(c.get(Calendar.MONTH) + 1, 2))
				.append(leftPad(c.get(Calendar.DATE), 2)).append(leftPad(c.get(Calendar.HOUR_OF_DAY), 2))
				.append(leftPad(c.get(Calendar.MINUTE), 2)).toString();
	}

	private FinanceUtils() {

	}

}
