package sk.radobenc.finance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import sk.radobenc.finance.Attribute.Matcher;

public class Calculator {

	public static class Filter {

		private final Map<Attribute<?>, Object> attributes = new HashMap<Attribute<?>, Object>();

		public Filter add(final Attribute<?> attribute, final Object value) {
			attributes.put(attribute, value);
			return this;
		}

		public void clear() {
			attributes.clear();
		}

		public boolean equals(final Object o) {
			return attributes.equals(o);
		}

		public int hashCode() {
			return attributes.hashCode();
		}

		public boolean isEmpty() {
			return attributes.isEmpty();
		}

		/**
		 * Returns true if all attributes of payment match this filter.
		 *
		 * @param payment the payment to check
		 * @return true is all attributes match
		 */
		@SuppressWarnings("unchecked")
		public boolean matchAll(final Payment payment) {
			boolean result = true;
			for (final Map.Entry<Attribute<?>, Object> e : attributes.entrySet()) {
				if (!result) {
					return false; // Fast exit
				}
				final Attribute.Matcher<Object> matcher = (Matcher<Object>) e.getKey().getMatcher();
				final Object paymentValue = payment.getAttribute(e.getKey());
				result &= matcher.matches(paymentValue, e.getValue());
			}
			return result;
		}

		/**
		 * Returns true if at least one attribute of payments matches this filter.
		 *
		 * @param payment the payment to check
		 * @return true if there is a match
		 */
		@SuppressWarnings("unchecked")
		public boolean matchAny(final Payment payment) {
			for (final Map.Entry<Attribute<?>, Object> e : attributes.entrySet()) {
				final Attribute.Matcher<Object> matcher = (Matcher<Object>) e.getKey().getMatcher();
				final Object paymentValue = payment.getAttribute(e.getKey());
				if (matcher.matches(paymentValue, e.getValue())) {
					return true;
				}
			}
			return false;
		};

		public int size() {
			return attributes.size();
		}

		@Override
		public String toString() {
			return new StringBuilder().append(Filter.class.getSimpleName()).append("[").append(attributes).append("]")
					.toString();
		}

	}

	public static class Result {

		private final Map<Map<Attribute<?>, Object>, BigDecimal> map = new HashMap<Map<Attribute<?>, Object>, BigDecimal>();

		public void clear() {
			map.clear();
		}

		public boolean containsKey(final Object key) {
			return map.containsKey(key);
		}

		public boolean equals(final Object o) {
			return map.equals(o);
		}

		public BigDecimal get(final Object key) {
			return map.get(key);
		}

		public int hashCode() {
			return map.hashCode();
		}

		@SuppressWarnings("unchecked")
		public Result list(final Filter filter) {
			final Result result = new Result();
			for (final Map.Entry<Map<Attribute<?>, Object>, BigDecimal> e : map.entrySet()) {
				boolean match = true;
				for (final Map.Entry<Attribute<?>, Object> f : filter.attributes.entrySet()) {
					final Attribute.Matcher<Object> matcher = (Matcher<Object>) f.getKey().getMatcher();
					final Object resultValue = e.getKey().get(f.getKey());
					match &= matcher.matches(resultValue, f.getValue());
				}
				if (match) {
					result.put(e.getKey(), e.getValue());
				}
			}
			return result;
		}

		public BigDecimal put(final Map<Attribute<?>, Object> key, final BigDecimal value) {
			return map.put(key, value);
		}

		public int size() {
			return map.size();
		}

		public Collection<Payment> toPayments() {
			final Collection<Payment> result = new ArrayList<Payment>();
			for (final Map.Entry<Map<Attribute<?>, Object>, BigDecimal> e : map.entrySet()) {
				final Payment payment = Payments.create(e.getValue(), (String) e.getKey().get(Attribute.CURRENCY_CODE));
				payment.putAttributes(e.getKey());
				result.add(payment);
			}
			return result;
		}

		@Override
		public String toString() {
			return new StringBuilder().append(this.getClass().getSimpleName()).append("[").append(map).append("]")
					.toString();
		}
	}

	/**
	 * Aggregate the payments of unique combinations of attributes, with optional
	 * filter.
	 *
	 * @param payments the payments to aggregate
	 * @param filter   the filter
	 * @return the aggregated result
	 */
	public static Result aggregate(final Collection<Payment> payments, final Filter filter) {
		checkPayments(payments);
		final Result result = new Result();
		for (final Payment p : payments) {
			if (null == filter || filter.matchAll(p)) {
				final Map<Attribute<?>, Object> attributes = p.getAttributes();
				BigDecimal old = BigDecimal.ZERO;
				if (result.containsKey(attributes)) {
					old = result.get(attributes);
				}
				result.put(attributes, old.add(new BigDecimal(p.getAmount().toString())));
			}
		}
		return result;
	}

	private static void checkFilter(final Filter filter) {
		if (null == filter) {
			throw new IllegalArgumentException("Filter can not be null");
		}
		final Set<Attribute<?>> missing = new HashSet<Attribute<?>>(Attributes.allMandatoryAttributes());
		missing.removeAll(filter.attributes.keySet());
		if (missing.size() > 0) {
			throw new IllegalArgumentException(String.format("Missing mandatory attributes %s", missing));
		}
	}

	private static void checkPayments(final Collection<Payment> payments) {
		if (null == payments) {
			throw new IllegalArgumentException("Payments can not be null");
		}
	}

	/**
	 * Calculates sum of payments using the filter. The filter must contain all
	 * mandatory attributes.
	 *
	 * @param payments the payments to sum up
	 * @param filter   the filter
	 * @return the sum of amounts
	 */
	public static BigDecimal sum(final Collection<Payment> payments, final Filter filter) {
		checkPayments(payments);
		checkFilter(filter);
		BigDecimal result = BigDecimal.ZERO;
		for (final Payment p : payments) {
			if (filter.matchAll(p)) {
				result = result.add(new BigDecimal(p.getAmount().toString()));
			}
		}
		return result;
	}

	private Calculator() {

	}
}
