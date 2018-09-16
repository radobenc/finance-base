package sk.radobenc.finance.impl;

import sk.radobenc.finance.Order;
import sk.radobenc.finance.Orders;
import sk.radobenc.finance.Payment;
import sk.radobenc.finance.Term;

public abstract class AbstractPayment extends AbstractAttributeOwner implements Payment {

	private static final long serialVersionUID = 7893880087035402269L;

	private final Number amount;

	protected AbstractPayment(final Number amount) {
		if (null == amount) {
			throw new IllegalArgumentException("amount = " + amount);
		}
		this.amount = amount;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final AbstractPayment other = (AbstractPayment) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		return true;
	}

	public final Number getAmount() {
		return amount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		return result;
	}

	public Order toOrder(final String name, final Term term, final Order.Frequency frequency) {
		return Orders.create(name, amount, term, frequency);
	}

	@Override
	public String toString() {
		return new StringBuilder().append(this.getClass().getSimpleName()).append("[amount=").append(amount.toString())
				.append(", attributes=").append(getAttributes()).append("]").toString();
	}
}
