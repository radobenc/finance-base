package sk.radobenc.finance.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import sk.radobenc.finance.Attribute;
import sk.radobenc.finance.AttributeOwner;
import sk.radobenc.finance.Value;

public abstract class AbstractAttributeOwner implements AttributeOwner {

	private static final long serialVersionUID = 79443631410928093L;

	private final Map<Attribute<?>, Value<?>> attributeValues;

	protected AbstractAttributeOwner() {
		attributeValues = new HashMap<Attribute<?>, Value<?>>();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <V> Value<V> addAttribute(final Attribute<V> attribute, final Value<V> value) {
		if (null == attribute) {
			throw new IllegalArgumentException("attribute=" + attribute);
		}
		if (null == value) {
			throw new IllegalArgumentException("value" + value);
		}
		return (Value<V>) attributeValues.put(attribute, value);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final AbstractAttributeOwner other = (AbstractAttributeOwner) obj;
		if (attributeValues == null) {
			if (other.attributeValues != null)
				return false;
		} else if (!attributeValues.equals(other.attributeValues))
			return false;
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <V> V getAttribute(final Attribute<V> attribute, final long time) {
		final Value<?> v = attributeValues.get(attribute);
		if (null == v) {
			return null;
		}
		return (V) v.get(time);
	}

	@Override
	public <V> V getAttribute(final Attribute<V> attribute, final long time, final V defaultValue) {
		final V v = getAttribute(attribute, time);
		return (null == v) ? defaultValue : v;
	}

	@Override
	public Map<Attribute<?>, Value<?>> getAttributes() {
		return Collections.unmodifiableMap(attributeValues);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map<Attribute, Object> getAttributes(final long time) {
		final Map<Attribute, Object> result = new HashMap<Attribute, Object>();
		for (final Entry<Attribute<?>, Value<?>> e : attributeValues.entrySet()) {
			result.put(e.getKey(), e.getValue().get(time));
		}
		return result;
	}

	@Override
	public <V> boolean hasAttribute(final Attribute<V> attribute) {
		if (null == attribute) {
			throw new IllegalArgumentException("attribute=" + attribute);
		}
		return attributeValues.containsKey(attribute);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attributeValues == null) ? 0 : attributeValues.hashCode());
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <V> Value<V> removeAttribute(final Attribute<V> attribute) {
		if (null == attribute) {
			throw new IllegalArgumentException("attribute=" + attribute);
		}
		return (Value<V>) attributeValues.remove(attribute);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <V> Value<V> setAttribute(final Attribute<V> attribute, final V value, final long time) {
		final Value<V> v = (Value<V>) attributeValues.get(attribute);
		v.set(time, value);
		return v;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <V> Value<V> setAttribute(final Attribute<V> attribute, final Value<V> value) {
		return (Value<V>) attributeValues.put(attribute, value);

	}

	public void setAttributes(final Map<Attribute<?>, Value<?>> attributeValues) {
		if (null == attributeValues) {
			throw new IllegalArgumentException("attributeValues=" + attributeValues);
		}
		this.attributeValues.putAll(attributeValues);
	}

}
