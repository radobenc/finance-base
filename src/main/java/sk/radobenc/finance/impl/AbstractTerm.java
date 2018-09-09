package sk.radobenc.finance.impl;

import sk.radobenc.finance.FinDate;
import sk.radobenc.finance.Term;

public class AbstractTerm implements Term {

	private static final long serialVersionUID = 1610642690676365437L;

	private final FinDate endDate;
	private final FinDate startDate;

	public AbstractTerm(final FinDate startDate, final FinDate endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
	}

	private void checkDate(final FinDate date) {
		if (null == date) {
			throw new IllegalArgumentException("date = " + date);
		}
	}

	@Override
	public boolean dateIsAfter(final FinDate date) {
		checkDate(date);
		return date.after(endDate);
	}

	@Override
	public boolean dateIsBefore(final FinDate date) {
		checkDate(date);
		return date.before(startDate);
	}

	@Override
	public boolean dateIsIn(final FinDate date) {
		checkDate(date);
		return !date.after(endDate) && !date.before(startDate);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final AbstractTerm other = (AbstractTerm) obj;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		return true;
	}

	@Override
	public FinDate getEndDate() {
		return endDate;
	}

	@Override
	public FinDate getStartDate() {
		return startDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return new StringBuilder().append(startDate).append('-').append(endDate).toString();
	}

}
