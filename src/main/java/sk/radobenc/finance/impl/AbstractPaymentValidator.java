package sk.radobenc.finance.impl;

import sk.radobenc.finance.Attribute;
import sk.radobenc.finance.Payment;
import sk.radobenc.finance.Validator;

public abstract class AbstractPaymentValidator implements Validator<Payment> {

	@Override
	public boolean isValid(final Payment validatedObject) {
		if (null == validatedObject) {
			return false;
		}
		return validatedObject.hasAttribute(Attribute.CURRENCY_CODE);
	}

}
