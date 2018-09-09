package sk.radobenc.finance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class PaymentTest {

	@Test
	public void testAddAttribute() {
		final Payment p1 = Payments.create(100, CurrencyCode.EUR);
		p1.addAttribute(Attribute.BILLING_DATE, new FinDate(2018, 9, 1));
		assertEquals(2, p1.getAttributes().size());
		p1.addAttribute(Attribute.BILLING_DATE, new FinDate(2018, 9, 1));
		assertEquals(2, p1.getAttributes().size());
		try {
			p1.addAttribute(null, null);
			throw new IllegalStateException(TestUtils.EXCEPTION_EXPECTED_BUT_NOT_THROWN);
		} catch (final Exception e) {
			TestUtils.checkException(IllegalArgumentException.class, e);
		}
		try {
			p1.addAttribute(null, "VALUE");
			throw new IllegalStateException(TestUtils.EXCEPTION_EXPECTED_BUT_NOT_THROWN);
		} catch (final Exception e) {
			TestUtils.checkException(IllegalArgumentException.class, e);
		}
		try {
			p1.addAttribute(Attribute.DESCRIPTION, null);
			throw new IllegalStateException(TestUtils.EXCEPTION_EXPECTED_BUT_NOT_THROWN);
		} catch (final Exception e) {
			TestUtils.checkException(IllegalArgumentException.class, e);
		}
		p1.addAttribute(Attribute.DESCRIPTION, "First description");
		assertEquals("First description", p1.getAttribute(Attribute.DESCRIPTION));
		p1.addAttribute(Attribute.DESCRIPTION, "Second description");
		assertEquals("Second description", p1.getAttribute(Attribute.DESCRIPTION));
		assertEquals(3, p1.getAttributes().size());
	}

	@Test
	public void testEquals() {
		final Payment p1 = Payments.create(100, CurrencyCode.EUR);
		final Payment p2 = Payments.create(100, CurrencyCode.EUR);
		assertEquals(p1, p2);
		assertFalse(p1.equals(null));
		assertFalse(p1.equals(new Object()));
	}

	@Test
	public void testGetAttributeDefaultValue() {
		final Payment p1 = Payments.create(100, CurrencyCode.EUR);
		p1.addAttribute(Attribute.BILLING_DATE, new FinDate(2018, 9, 1));
		p1.addAttribute(Attribute.DESCRIPTION, "TEST");
		assertEquals(FinDate.MIN, p1.getAttribute(Attribute.PROCESSING_DATE, FinDate.MIN));
		assertNull(p1.getAttribute(Attribute.PROCESSING_DATE, null));
	}

	@Test
	public void testGetAttributes() {
		try {
			final Payment p1 = Payments.create(100, CurrencyCode.EUR);
			p1.addAttribute(Attribute.BILLING_DATE, new FinDate(2018, 9, 1));
			p1.getAttributes().put(Attribute.DESCRIPTION, "TEST");
			throw new IllegalStateException(TestUtils.EXCEPTION_EXPECTED_BUT_NOT_THROWN);
		} catch (final Exception e) {
			TestUtils.checkException(UnsupportedOperationException.class, e);
		}
	}

	@Test
	public void testHasAttribute() {
		final Payment p1 = Payments.create(100, CurrencyCode.EUR);
		p1.addAttribute(Attribute.BILLING_DATE, new FinDate(2018, 9, 1));
		assertTrue(p1.hasAttribute(Attribute.BILLING_DATE));
		assertFalse(p1.hasAttribute(Attribute.SOURCE_ACCOUNT));
	}

	@Test
	public void testHashCode() {
		final Payment p1 = Payments.create(100, CurrencyCode.EUR);
		final Payment p2 = Payments.create(100, CurrencyCode.EUR);
		final Payment p3 = Payments.create(101, CurrencyCode.EUR);
		assertEquals(p1.hashCode(), p2.hashCode());
		assertFalse(p1.hashCode() == p3.hashCode());
		try {
			p1.hasAttribute(null);
			throw new IllegalStateException(TestUtils.EXCEPTION_EXPECTED_BUT_NOT_THROWN);
		} catch (final Exception e) {
			TestUtils.checkException(IllegalArgumentException.class, e);
		}
	}

	@Test
	public void testPutAttributes() {
		final Payment p1 = Payments.create(100, CurrencyCode.EUR);
		p1.addAttribute(Attribute.BILLING_DATE, new FinDate(2018, 9, 1));
		p1.addAttribute(Attribute.PROCESSING_DATE, new FinDate(2018, 9, 1));
		final Map<Attribute<?>, Object> other = new HashMap<Attribute<?>, Object>();
		other.put(Attribute.BILLING_DATE, new FinDate(2018, 9, 1));
		other.put(Attribute.CURRENCY_CODE, CurrencyCode.USD);
		other.put(Attribute.DESCRIPTION, "other attributes");
		p1.putAttributes(other);
		assertEquals(4, p1.getAttributes().size());
		assertEquals(CurrencyCode.USD, p1.getAttribute(Attribute.CURRENCY_CODE));
		assertEquals("other attributes", p1.getAttribute(Attribute.DESCRIPTION));
		try {
			p1.putAttributes(null);
			throw new IllegalStateException(TestUtils.EXCEPTION_EXPECTED_BUT_NOT_THROWN);
		} catch (final Exception e) {
			TestUtils.checkException(IllegalArgumentException.class, e);
		}
	}

	@Test
	public void testRemoveAttributes() {
		final Payment p1 = Payments.create(100, CurrencyCode.EUR);
		p1.addAttribute(Attribute.BILLING_DATE, new FinDate(2018, 9, 1));
		p1.addAttribute(Attribute.PROCESSING_DATE, new FinDate(2018, 9, 1));
		assertEquals(3, p1.getAttributes().size());
		try {
			p1.removeAttribute(null);
			throw new IllegalStateException(TestUtils.EXCEPTION_EXPECTED_BUT_NOT_THROWN);
		} catch (final Exception e) {
			TestUtils.checkException(IllegalArgumentException.class, e);
		}
		p1.removeAttribute(Attribute.DESCRIPTION);
		assertEquals(3, p1.getAttributes().size());
		p1.removeAttribute(Attribute.PROCESSING_DATE);
		assertEquals(2, p1.getAttributes().size());
	}
}
