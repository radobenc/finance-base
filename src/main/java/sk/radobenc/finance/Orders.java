package sk.radobenc.finance;

import sk.radobenc.finance.impl.AbstractOrder;

public class Orders {

	public static Order create(final String name, final Number amount, final FinDate startDate, final FinDate endDate,
			final Order.Frequency frequency) {
		return create(name, amount, Terms.create(startDate, endDate), frequency);
	}

	public static Order create(final String name, final Number amount, final Term term,
			final Order.Frequency frequency) {
		return new AbstractOrder(name, amount, term, frequency) {

			private static final long serialVersionUID = 7007720081266858279L;

		};
	}
}
