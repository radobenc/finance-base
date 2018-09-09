package sk.radobenc.finance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TermTest {

	private final FinDate start = new FinDate(2007, 6, 1);
	private final FinDate end = new FinDate(2018, 7, 31);
	private final Term term = Terms.create(start, end);

	@Test
	public void testDateIsAfter() {
		assertFalse(term.dateIsAfter(new FinDate(1969, 11, 5)));
		assertFalse(term.dateIsAfter(new FinDate(2010, 11, 5)));
		assertTrue(term.dateIsAfter(new FinDate(2018, 9, 1)));
		try {
			term.dateIsAfter(null);
			throw new IllegalStateException(TestUtils.EXCEPTION_EXPECTED_BUT_NOT_THROWN);
		} catch (final Exception e) {
			TestUtils.checkException(IllegalArgumentException.class, e);
		}
	}

	@Test
	public void testDateIsBefore() {
		assertTrue(term.dateIsBefore(new FinDate(1969, 11, 5)));
		assertFalse(term.dateIsBefore(new FinDate(2010, 11, 5)));
		assertFalse(term.dateIsBefore(new FinDate(2018, 9, 1)));
		try {
			term.dateIsBefore(null);
			throw new IllegalStateException(TestUtils.EXCEPTION_EXPECTED_BUT_NOT_THROWN);
		} catch (final Exception e) {
			TestUtils.checkException(IllegalArgumentException.class, e);
		}
	}

	@Test
	public void testDateIsIn() {
		assertFalse(term.dateIsIn(new FinDate(1969, 11, 5)));
		assertTrue(term.dateIsIn(new FinDate(2010, 11, 5)));
		assertFalse(term.dateIsIn(new FinDate(2018, 9, 1)));
		try {
			term.dateIsIn(null);
			throw new IllegalStateException(TestUtils.EXCEPTION_EXPECTED_BUT_NOT_THROWN);
		} catch (final Exception e) {
			TestUtils.checkException(IllegalArgumentException.class, e);
		}
	}

	@Test
	public void testEquals() {
		assertFalse(term.equals(null));
		assertFalse(term.equals(new Object()));
		final Term otherTerm = Terms.create(start, end);
		assertEquals(term, otherTerm);
	}

	@Test
	public void testHashCode() {
		final Term t1 = Terms.create(start, end);
		final Term t2 = Terms.create(start, end);
		assertEquals(t1.hashCode(), t2.hashCode());
		assertEquals(t1.hashCode(), t1.hashCode());
		final Term t3 = Terms.create(start, end.addDays(1));
		assertTrue(t1.hashCode() != t3.hashCode());
	}
}
