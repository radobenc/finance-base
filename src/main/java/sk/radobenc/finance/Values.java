package sk.radobenc.finance;

import sk.radobenc.finance.impl.SimpleValue;

public class Values {

	public static <V> Value<V> create(final V value) {
		if (value instanceof String || value instanceof FinDate) {
			return new SimpleValue<V>(value);
		}
		throw new UnsupportedOperationException("value=" + value);

	}

	public static Value<FinDate> createFinDate(final int year, final int month, final int day) {
		return new SimpleValue<FinDate>(new FinDate(year, month, day));
	}
}
