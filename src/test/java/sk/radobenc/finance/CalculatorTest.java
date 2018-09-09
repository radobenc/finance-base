package sk.radobenc.finance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import sk.radobenc.finance.Calculator.Result;

public class CalculatorTest {

	private static final String DESCRIPTION_PAYMENT_IN_SKK = "Payment in SKK";
	private static final FinDate BILLING_DATE_2004_07_14 = new FinDate(2004, 7, 14);

	static List<Payment> examplePayments1() {
		List<Payment> result = new ArrayList<Payment>();
		// #1
		Payment payment = Payments.create(19900, CurrencyCode.SKK);
		payment.addAttribute(Attribute.BILLING_DATE, BILLING_DATE_2004_07_14);
		payment.addAttribute(Attribute.DESCRIPTION, DESCRIPTION_PAYMENT_IN_SKK);
		result.add(payment);
		// #2
		payment = Payments.create(100, CurrencyCode.SKK);
		payment.addAttribute(Attribute.DESCRIPTION, DESCRIPTION_PAYMENT_IN_SKK);
		payment.addAttribute(Attribute.BILLING_DATE, BILLING_DATE_2004_07_14);
		result.add(payment);
		// #3
		payment = Payments.create(100, CurrencyCode.EUR);
		payment.addAttribute(Attribute.BILLING_DATE, new FinDate(2018, 5, 10));
		result.add(payment);
		// #4
		payment = Payments.create(150, CurrencyCode.EUR);
		payment.addAttribute(Attribute.BILLING_DATE, new FinDate(2018, 6, 10));
		result.add(payment);
		// #5
		payment = Payments.create(-15, CurrencyCode.EUR);
		payment.addAttribute(Attribute.BILLING_DATE, new FinDate(2018, 6, 10));
		result.add(payment);
		// #6
		payment = Payments.create(180, CurrencyCode.USD);
		payment.addAttribute(Attribute.BILLING_DATE, new FinDate(2018, 6, 10));
		result.add(payment);
		// #7
		payment = Payments.create(-18, CurrencyCode.USD);
		payment.addAttribute(Attribute.BILLING_DATE, new FinDate(2018, 6, 10));
		result.add(payment);
		// #8
		payment = Payments.create(200, CurrencyCode.EUR);
		payment.addAttribute(Attribute.BILLING_DATE, new FinDate(2018, 7, 10));
		result.add(payment);
		// #9
		payment = Payments.create(250, CurrencyCode.EUR);
		payment.addAttribute(Attribute.BILLING_DATE, new FinDate(2018, 8, 10));
		result.add(payment);
		return result;
	}

	@Before
	public void setUp() {
	}

	@Test
	public void testAggregate() {
		try {
			Calculator.aggregate(null, null);
			throw new IllegalStateException(TestUtils.EXCEPTION_EXPECTED_BUT_NOT_THROWN);
		} catch (Exception e) {
			TestUtils.checkException(IllegalArgumentException.class, e);
		}
		Result result = Calculator.aggregate(examplePayments1(), null);
		assertEquals(6, result.size());
		Calculator.Filter filter = new Calculator.Filter().add(Attribute.CURRENCY_CODE, CurrencyCode.SKK)
				.add(Attribute.DESCRIPTION, DESCRIPTION_PAYMENT_IN_SKK).add(Attribute.BILLING_DATE, BILLING_DATE_2004_07_14);
		assertEquals(new BigDecimal(20000), result.get(filter));
		filter = new Calculator.Filter().add(Attribute.CURRENCY_CODE, CurrencyCode.SKK);
		result = Calculator.aggregate(examplePayments1(), filter);
		assertNull(result.get(filter)); // get brings exact match
		result = result.list(filter);
		TestUtils.printPayments(result.toPayments());
	}

	@Test
	public void testSum() {
		try {
			Calculator.sum(null, null);
			throw new IllegalStateException(TestUtils.EXCEPTION_EXPECTED_BUT_NOT_THROWN);
		} catch (final Exception e) {
			TestUtils.checkException(IllegalArgumentException.class, e);
		}
		try {
			Calculator.sum(new ArrayList<Payment>(), null);
			throw new IllegalStateException(TestUtils.EXCEPTION_EXPECTED_BUT_NOT_THROWN);
		} catch (final Exception e) {
			TestUtils.checkException(IllegalArgumentException.class, e);
		}
		Calculator.Filter filter = new Calculator.Filter().add(Attribute.CURRENCY_CODE, CurrencyCode.EUR).add(Attribute.BILLING_DATE,
				new FinDate(2018, 7, 31));
		assertEquals(new BigDecimal(435), Calculator.sum(examplePayments1(), filter));
		filter = new Calculator.Filter().add(Attribute.CURRENCY_CODE, CurrencyCode.SKK).add(Attribute.BILLING_DATE,
				BILLING_DATE_2004_07_14).add(Attribute.DESCRIPTION, DESCRIPTION_PAYMENT_IN_SKK);
		assertEquals(new BigDecimal(20000), Calculator.sum(examplePayments1(), filter));
	}

}
