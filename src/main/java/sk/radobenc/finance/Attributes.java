package sk.radobenc.finance;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sk.radobenc.finance.impl.AbstractAttribute;

public class Attributes {

	static abstract class AbstractMatcher<V> implements Attribute.Matcher<V> {

		private final String attributeName;
		private long count;
		private long hitCount;

		protected AbstractMatcher(final String attributeName) {
			this.attributeName = attributeName;
		}

		public final long getCount() {
			return count;
		}

		public final long getHitCount() {
			return hitCount;
		}

		protected abstract boolean match(V v1, V v2);

		public final boolean matches(final V v1, final V v2) {
			count++;
			if (null == v1 || null == v2) {
				return false;
			}
			final boolean result = match(v1, v2);
			if (result) {
				hitCount++;
			}
			return result;
		}

		@Override
		public String toString() {
			return new StringBuilder().append(this.getClass().getSimpleName()).append("[").append(attributeName)
					.append("]").toString();
		}
	}

	static Logger logger = LoggerFactory.getLogger(Attributes.class);

	static Set<Attribute<?>> allMandatoryAttributes() {
		final Set<Attribute<?>> result = new HashSet<Attribute<?>>();
		for (final Field f : Attribute.class.getFields()) {
			Attribute<?> a = null;
			try {
				a = (Attribute<?>) f.get(Attribute.class);
			} catch (final IllegalArgumentException e) {
				logger.error(e.getMessage(), e);
			} catch (final IllegalAccessException e) {
				logger.error(e.getMessage(), e);
			}
			if (null != a && a.isMandatory()) {
				result.add(a);
			}
		}
		return result;
	}

	public static Attribute<String> create(final String name) {
		return create(name, createStringAttributeMatcher(name), false, false);
	}

	public static <V> Attribute<V> create(final String name, final Attribute.Matcher<V> matcher) {
		return create(name, matcher, false, false);
	}

	public static <V> Attribute<V> create(final String name, final Attribute.Matcher<V> matcher,
			final boolean mandatory, final boolean hierarchical) {
		return new AbstractAttribute<V>(name, matcher, mandatory, hierarchical) {

			private static final long serialVersionUID = -4702361245242880357L;
		};
	}

	public static Attribute<String> create(final String name, final boolean mandatory) {
		return create(name, createStringAttributeMatcher(name), mandatory, false);
	}

	public static Attribute.Matcher<FinDate> createFinDateAttributeMatcher(final String attributeName) {
		return new AbstractMatcher<FinDate>(attributeName) {

			@Override
			protected boolean match(final FinDate v1, final FinDate v2) {
				return v2.equals(v1) || v2.after(v1);
			}

		};
	}

	public static Attribute.Matcher<String> createStringAttributeMatcher(final String attributeName) {
		return new AbstractMatcher<String>(attributeName) {

			@Override
			protected boolean match(final String v1, final String v2) {
				return v1.equals(v2);
			}

		};
	}

	private Attributes() {

	}
}
