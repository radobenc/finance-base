package sk.radobenc.finance;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import sk.radobenc.finance.impl.ValidatorFactory;

public class ValidatorTest {

	private final Validator<String> currencyCodeValidator = ValidatorFactory.createCurrencyCodeValidator();
	private final Validator<Payment> paymentValidator = ValidatorFactory.createPaymentValidator();

	@Test
	public void testIsValid() {
		assertFalse(currencyCodeValidator.isValid(null));
		assertFalse(currencyCodeValidator.isValid(""));
		assertTrue(currencyCodeValidator.isValid("EUR"));
		Payment p = null;
		assertFalse(paymentValidator.isValid(p));
		p = Payments.create(100, "USD");
		assertTrue(paymentValidator.isValid(p));
		p.addAttribute(Attribute.BILLING_DATE, Values.createFinDate(2018, 9, 1));
		assertTrue(paymentValidator.isValid(p));
	}

}
