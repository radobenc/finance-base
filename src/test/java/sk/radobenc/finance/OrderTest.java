package sk.radobenc.finance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import sk.radobenc.finance.Order.Frequency;

public class OrderTest {

	@Test
	public void testAbstractOrderImpl() {
		try {
			Orders.create(null, null, null, null);
			throw new IllegalStateException(TestUtils.EXCEPTION_EXPECTED_BUT_NOT_THROWN);
		} catch (final Exception e) {
			TestUtils.checkException(IllegalArgumentException.class, e);
		}
		try {
			Orders.create("", null, null, null);
			throw new IllegalStateException(TestUtils.EXCEPTION_EXPECTED_BUT_NOT_THROWN);
		} catch (final Exception e) {
			TestUtils.checkException(IllegalArgumentException.class, e);
		}
		try {
			Orders.create("name", null, null, null);
			throw new IllegalStateException(TestUtils.EXCEPTION_EXPECTED_BUT_NOT_THROWN);
		} catch (final Exception e) {
			TestUtils.checkException(IllegalArgumentException.class, e);
		}
		try {
			Orders.create("name", 0, null, null);
			throw new IllegalStateException(TestUtils.EXCEPTION_EXPECTED_BUT_NOT_THROWN);
		} catch (final Exception e) {
			TestUtils.checkException(IllegalArgumentException.class, e);
		}
		try {
			Orders.create("name", 0, null, Frequency.ANNUALLY);
			throw new IllegalStateException(TestUtils.EXCEPTION_EXPECTED_BUT_NOT_THROWN);
		} catch (final Exception e) {
			TestUtils.checkException(IllegalArgumentException.class, e);
		}
	}

	@Test
	public void testEquals() {
		final Order o1 = Orders.create("Test", 100, new FinDate(2018, 5, 31), new FinDate(2018, 9, 30),
				Frequency.MONTHLY);
		assertFalse(o1.equals(null));
		assertFalse(o1.equals(new Object()));
		final Order o2 = Orders.create("Test", 100, new FinDate(2018, 5, 31), new FinDate(2018, 9, 30),
				Frequency.MONTHLY);
		assertEquals(o1, o2);
	}

	@Test
	public void testHashCode() {
		final Order o1 = Orders.create("Test", 100, new FinDate(2018, 5, 31), new FinDate(2018, 9, 30),
				Frequency.MONTHLY);
		final Order o2 = Orders.create("Test", 100, new FinDate(2018, 5, 31), new FinDate(2018, 9, 30),
				Frequency.MONTHLY);
		final Order o3 = Orders.create("Test 3", 100, new FinDate(2018, 5, 31), new FinDate(2018, 9, 30),
				Frequency.MONTHLY);
		assertEquals(o1.hashCode(), o2.hashCode());
		assertFalse(o1.hashCode() == o3.hashCode());
	}

	@Test
	public void testToString() {
		assertNotNull(
				Orders.create("A", 0, Terms.create(new FinDate(2018, 1, 1), new FinDate(2019, 1, 1)), Frequency.DAILY)
						.toString());
	}
}
