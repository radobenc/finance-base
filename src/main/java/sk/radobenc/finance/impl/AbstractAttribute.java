package sk.radobenc.finance.impl;

import sk.radobenc.finance.Attribute;
import sk.radobenc.finance.Attributes;

public abstract class AbstractAttribute<V> implements Attribute<V> {

	private static final long serialVersionUID = 245128739597965021L;

	private final boolean mandatory;
	private final Attribute.Matcher<V> matcher;
	private final String name;

	protected AbstractAttribute(final String name) {
		this(name, Attributes.createStringAttributeMatcher(name));
	}

	protected AbstractAttribute(final String name, final Attribute.Matcher<?> matcher) {
		this(name, matcher, false);
	}

	@SuppressWarnings("unchecked")
	protected AbstractAttribute(final String name, final Attribute.Matcher<?> matcher, final boolean mandatory) {
		this.name = name;
		this.matcher = (Attribute.Matcher<V>) matcher;
		this.mandatory = mandatory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final AbstractAttribute<V> other = (AbstractAttribute<V>) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public final Attribute.Matcher<V> getMatcher() {
		return matcher;
	}

	@Override
	public final String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	public final boolean isMandatory() {
		return mandatory;

	}

	@Override
	public String toString() {
		return name.toString();
	}

}
