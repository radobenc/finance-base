package sk.radobenc.finance.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import sk.radobenc.finance.Attribute;
import sk.radobenc.finance.Order;
import sk.radobenc.finance.Orders;
import sk.radobenc.finance.Payment;
import sk.radobenc.finance.Term;

public abstract class AbstractPayment implements Payment {

	private static final long serialVersionUID = 7893880087035402269L;

	private final Number amount;
	private final Map<Attribute<?>, Object> attributes;

	protected AbstractPayment(final Number amount) {
		if (null == amount) {
			throw new IllegalArgumentException("amount = " + amount);
		}
		this.amount = amount;
		attributes = new HashMap<Attribute<?>, Object>();
	}

	@SuppressWarnings("unchecked")
	public <V> V addAttribute(final Attribute<V> attribute, final V value) {
		if (null == attribute) {
			throw new IllegalArgumentException("attribute = " + attribute);
		}
		if (null == value) {
			throw new IllegalArgumentException(String.format("Illegal value %s for attribute %s", value, attribute));
		}
		return (V) attributes.put(attribute, value);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final AbstractPayment other = (AbstractPayment) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (attributes == null) {
			if (other.attributes != null)
				return false;
		} else if (!attributes.equals(other.attributes))
			return false;
		return true;
	}

	public final Number getAmount() {
		return amount;
	}

	public final Object getAttribute(final Attribute<?> attribute) {
		return attributes.get(attribute);
	}

	public final Object getAttribute(final Attribute<?> attribute, final Object defaultValue) {
		return attributes.getOrDefault(attribute, defaultValue);
	}

	public final Map<Attribute<?>, Object> getAttributes() {
		return Collections.unmodifiableMap(attributes);
	}

	public <V> boolean hasAttribute(final Attribute<V> attribute) {
		if (null == attribute) {
			throw new IllegalArgumentException("attribute = " + attribute);
		}
		return attributes.containsKey(attribute);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((attributes == null) ? 0 : attributes.hashCode());
		return result;
	}

	@Override
	public void putAttributes(final Map<Attribute<?>, Object> moreAttributes) {
		if (null == moreAttributes) {
			throw new IllegalArgumentException("attributes = " + moreAttributes);
		}
		for (final Map.Entry<Attribute<?>, Object> e : moreAttributes.entrySet()) {
			attributes.put(e.getKey(), e.getValue());
		}
	}

	@SuppressWarnings("unchecked")
	public <V> V removeAttribute(final Attribute<V> attribute) {
		if (null == attribute) {
			throw new IllegalArgumentException("attribute = " + attribute);
		}
		return (V) attributes.remove(attribute);
	}

	public Order toOrder(final String name, final Term term, final Order.Frequency frequency) {
		return Orders.create(name, amount, term, frequency);
	}

	@Override
	public String toString() {
		return new StringBuilder().append(this.getClass().getSimpleName()).append("[amount=").append(amount.toString())
				.append(", attributes=").append(attributes).append("]").toString();
	}
}
