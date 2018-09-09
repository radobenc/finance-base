package sk.radobenc.finance;

import java.io.Serializable;

public interface Order extends Named, Payment, Serializable, Term {

	enum Frequency {
		DAILY, MONTHLY, ANNUALLY;
	}

	Frequency getFrequency();

	boolean isSkippingWeekends();

	void setSkippingWeekends(boolean value);

}
