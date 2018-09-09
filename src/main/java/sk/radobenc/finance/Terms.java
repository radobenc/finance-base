package sk.radobenc.finance;

import sk.radobenc.finance.impl.AbstractTerm;

public class Terms {

	public static Term create(final FinDate startDate, final FinDate endDate) {
		return new AbstractTerm(startDate, endDate) {

			private static final long serialVersionUID = -3461147194519648765L;

		};
	}
}
