package sk.radobenc.finance.impl;

import sk.radobenc.finance.Payment;
import sk.radobenc.finance.Validator;

public class ValidatorFactory {

	private static ValidatorFactory instance;

	public static final Validator<String> createCurrencyCodeValidator() {
		return new AbstractCurrencyCodeValidator() {
		};
	}

	public static final Validator<Payment> createPaymentValidator() {
		return new AbstractPaymentValidator() {
		};
	}

	public static final ValidatorFactory getInstance() {
		if (null == instance) {
			instance = new ValidatorFactory();
		}
		return instance;
	}

	private ValidatorFactory() {
	}
}
