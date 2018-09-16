package sk.radobenc.finance.impl;

import java.util.SortedMap;
import java.util.TreeMap;

import sk.radobenc.finance.Value;

public abstract class AbstractValue<V> implements Value<V> {

	/*
	 * Immutable first value used if the Value has no history.
	 */
	private static class First<V> {
		private final long time;
		private final V value;

		private First(final V value, final long time) {
			this.value = value;
			this.time = time;
		}

		@Override
		public boolean equals(final Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final First<?> other = (First<?>) obj;
			if (time != other.time)
				return false;
			if (value == null) {
				if (other.value != null)
					return false;
			} else if (!value.equals(other.value))
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (int) (time ^ (time >>> 32));
			result = prime * result + ((value == null) ? 0 : value.hashCode());
			return result;
		}

		@Override
		public String toString() {
			return new StringBuilder().append("[time=").append(this.time).append(",value=").append(this.value)
					.append("]").toString();
		}
	}

	private static final long serialVersionUID = 4909626160420705942L;

	private First<V> first;
	private SortedMap<Long, V> timeValues;

	protected AbstractValue(final V value) {
		this(value, Long.MIN_VALUE);
	}

	protected AbstractValue(final V value, final long time) {
		if (null == first) {
			this.first = new First<V>(value, time);
		} else {
			set(time, value);
		}
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final AbstractValue<?> other = (AbstractValue<?>) obj;
		if (first == null) {
			if (other.first != null)
				return false;
		} else if (!first.equals(other.first))
			return false;
		if (timeValues == null) {
			if (other.timeValues != null)
				return false;
		} else if (!timeValues.equals(other.timeValues))
			return false;
		return true;
	}

	@Override
	public V get(final long time) {
		if (null == timeValues) {
			return time >= first.time ? first.value : null;
		}
		if (timeValues.containsKey(time)) {
			return timeValues.get(time);
		}
		final SortedMap<Long, V> headMap = timeValues.headMap(time);
		if (headMap.isEmpty()) {
			return null;
		}
		final Long last = headMap.lastKey();
		return timeValues.get(last);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((first == null) ? 0 : first.hashCode());
		result = prime * result + ((timeValues == null) ? 0 : timeValues.hashCode());
		return result;
	}

	@Override
	public void set(final long time, final V value) {
		if (null == this.timeValues) {
			this.timeValues = new TreeMap<Long, V>();
		}
		timeValues.put(time, value);
	}

	@Override
	public String toString() {
		if (null == timeValues) {
			return first.toString();
		}
		return timeValues.toString();
	}
}
