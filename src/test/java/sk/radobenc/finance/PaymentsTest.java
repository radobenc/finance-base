package sk.radobenc.finance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sk.radobenc.finance.Order.Frequency;

public class PaymentsTest {

	Logger logger = LoggerFactory.getLogger(PaymentsTest.class);

	@Test
	public void testCreateOrder() {
		final Order o = Orders.create("Order 1", 2345, Terms.create(new FinDate(2007, 7, 10), new FinDate(2018, 8, 10)),
				Frequency.MONTHLY);
		List<Payment> result = null;
		try {
			result = Payments.list(o);
			throw new IllegalStateException(TestUtils.EXCEPTION_EXPECTED_BUT_NOT_THROWN);
		} catch (final Exception e) {
			TestUtils.checkException(IllegalArgumentException.class, e);
		}
		o.addAttribute(Attribute.CURRENCY_CODE, CurrencyCode.EUR);
		result = Payments.list(o);
		assertNotNull(result);
		assertEquals(134, result.size());
//		TestUtils.printPayments(result);
	}

	@Test
	public void testCreatePayment() {
		Payment p = Payments.create(100, CurrencyCode.EUR);
		logger.info("Created test payment p: " + p);
		assertEquals(100, p.getAmount());
		try {
			p = Payments.create(null, null);
			throw new IllegalStateException(TestUtils.EXCEPTION_EXPECTED_BUT_NOT_THROWN);
		} catch (final Exception e) {
			TestUtils.checkException(IllegalArgumentException.class, e);
		}
		try {
			p = Payments.create(200, null);
			throw new IllegalStateException(TestUtils.EXCEPTION_EXPECTED_BUT_NOT_THROWN);
		} catch (final Exception e) {
			TestUtils.checkException(IllegalArgumentException.class, e);
		}
		try {
			p = Payments.create(300, "");
			throw new IllegalStateException(TestUtils.EXCEPTION_EXPECTED_BUT_NOT_THROWN);
		} catch (final Exception e) {
			TestUtils.checkException(IllegalArgumentException.class, e);
		}
	}
}
