package sk.radobenc.finance.impl;

public class SimpleValue<V> extends AbstractValue<V> {

	private static final long serialVersionUID = 3281730732217935779L;

	public SimpleValue(final V value, final long time) {
		super(value, time);
	}

	public SimpleValue(final V value) {
		super(value);
	}
}
