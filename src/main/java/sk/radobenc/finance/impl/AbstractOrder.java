package sk.radobenc.finance.impl;

import sk.radobenc.finance.FinDate;
import sk.radobenc.finance.Order;
import sk.radobenc.finance.Term;

public abstract class AbstractOrder extends AbstractPayment implements Order {

	private static final long serialVersionUID = 2787314497184872473L;

	private final Frequency frequency;
	private final String name;
	private boolean skippingWeekends = true;
	private final AbstractTerm term;

	protected AbstractOrder(final String name, final Number amount, final Term term, final Frequency frequency) {
		super(amount);
		if (null == name || name.isEmpty()) {
			throw new IllegalArgumentException("name = " + name);
		}
		this.name = name;
		if (null == frequency) {
			throw new IllegalArgumentException("frequency = " + frequency);
		}
		this.frequency = frequency;
		if (null == term) {
			throw new IllegalArgumentException("term = " + term);
		}
		this.term = (AbstractTerm) term;
	}

	@Override
	public boolean dateIsAfter(final FinDate date) {
		return term.dateIsAfter(date);
	}

	@Override
	public boolean dateIsBefore(final FinDate date) {
		return term.dateIsBefore(date);
	}

	@Override
	public boolean dateIsIn(final FinDate date) {
		return term.dateIsIn(date);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final AbstractOrder other = (AbstractOrder) obj;
		if (frequency != other.frequency)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (term == null) {
			if (other.term != null)
				return false;
		} else if (!term.equals(other.term))
			return false;
		return true;
	}

	@Override
	public FinDate getEndDate() {
		return term.getEndDate();
	}

	@Override
	public Frequency getFrequency() {
		return frequency;
	}

	@Override
	public final String getName() {
		return name;
	}

	@Override
	public FinDate getStartDate() {
		return term.getStartDate();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((frequency == null) ? 0 : frequency.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((term == null) ? 0 : term.hashCode());
		return result;
	}

	@Override
	public boolean isSkippingWeekends() {
		return skippingWeekends;
	}

	@Override
	public void setSkippingWeekends(final boolean value) {
		skippingWeekends = value;
	}

	@Override
	public String toString() {
		return new StringBuilder(this.getClass().getSimpleName()).append("[name=").append(name).append(", amount=")
				.append(getAmount()).append(", term=").append(term).append(", frequency=").append(frequency)
				.append(", attributes=").append(getAttributes()).append("]").toString();
	}
}
