package sk.radobenc.finance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class FinDateTest {

	private static final FinDate TEST_DATE = new FinDate(2018, 8, 30);
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testAddDay() {
		final FinDate d1 = new FinDate(2018, 8, 31);
		assertEquals(d1, TEST_DATE.addDays(1));
		assertEquals(new FinDate(2018, 9, 1), d1.addDays(1));
		assertEquals(new FinDate(2018, 3, 1), new FinDate(2018, 2, 28).addDays(1));
		assertEquals(new FinDate(2018, 3, 2), new FinDate(2018, 2, 28).addDays(1).addDays(1));
		assertEquals(new FinDate(2004, 3, 1), new FinDate(2004, 2, 28).addDays(1).addDays(1));
		assertEquals(new FinDate(2018, 1, 1), new FinDate(2017, 12, 31).addDays(1));
	}

	@Test
	public void testAddMonth() {
		final FinDate d1 = new FinDate(2018, 9, 30);
		assertEquals(d1, TEST_DATE.addMonths(1));
		final FinDate d2 = new FinDate(2004, 1, 31);
		assertEquals(new FinDate(2004, 2, 29), d2.addMonths(1));
	}

	@Test
	public void testAddYear() {
		final FinDate d1 = new FinDate(2004, 2, 29);
		assertEquals(new FinDate(2005, 2, 28), d1.addYear());
	}

	@Test
	public void testAfter() {
		assertTrue(TEST_DATE.after(new FinDate(1969, 11, 5)));
		assertFalse(TEST_DATE.after(new FinDate(2018, 9, 1)));
		try {
			TEST_DATE.after(null);
			throw new IllegalStateException(TestUtils.EXCEPTION_EXPECTED_BUT_NOT_THROWN);
		} catch (final Exception e) {
			TestUtils.checkException(NullPointerException.class, e);
		}
	}
	@Test
	public void testAgeInDays() {
		assertEquals(365, FinDate.ageInDays(2017, 9, 5, 2018, 9, 5));
	}
	
	@Test
	public void testAgeInYears() {
		try {
			FinDate.ageInYears(null, TEST_DATE);
			throw new IllegalStateException(TestUtils.EXCEPTION_EXPECTED_BUT_NOT_THROWN);
		} catch (final Exception e) {
			TestUtils.checkException(IllegalArgumentException.class, e);
		}
		try {
			FinDate.ageInYears(TEST_DATE, null);
			throw new IllegalStateException(TestUtils.EXCEPTION_EXPECTED_BUT_NOT_THROWN);
		} catch (final Exception e) {
			TestUtils.checkException(IllegalArgumentException.class, e);
		}
		try {
			FinDate.ageInYears(new FinDate(2018, 9, 1), new FinDate(2018, 8, 30));
			throw new IllegalStateException(TestUtils.EXCEPTION_EXPECTED_BUT_NOT_THROWN);
		} catch (final Exception e) {
			TestUtils.checkException(IllegalArgumentException.class, e);
		}
		assertEquals(12, FinDate.ageInYears(new FinDate(2006, 8, 30), new FinDate(2018, 9, 2)));
		assertEquals(12, FinDate.ageInYears(new FinDate(2006, 8, 30), new FinDate(2018, 8, 30)));
		assertEquals(12, FinDate.ageInYears(new FinDate(2006, 8, 30), new FinDate(2018, 12, 31)));
		assertEquals(12, FinDate.ageInYears(new FinDate(2006, 8, 30), new FinDate(2019, 1, 1)));
		assertEquals(11, FinDate.ageInYears(new FinDate(2006, 8, 30), new FinDate(2018, 8, 29)));
		assertEquals(11, FinDate.ageInYears(new FinDate(2006, 8, 30), new FinDate(2018, 7, 29)));
		assertEquals(11, FinDate.ageInYears(new FinDate(2006, 8, 30), new FinDate(2018, 7, 30)));
		assertEquals(11, FinDate.ageInYears(new FinDate(2006, 8, 30), new FinDate(2018, 7, 31)));
	}

	@Test
	public void testBefore() {
		assertFalse(TEST_DATE.before(new FinDate(1969, 11, 5)));
		assertTrue(TEST_DATE.before(new FinDate(2018, 9, 1)));
		try {
			TEST_DATE.before(null);
			throw new IllegalStateException(TestUtils.EXCEPTION_EXPECTED_BUT_NOT_THROWN);
		} catch (final Exception e) {
			TestUtils.checkException(NullPointerException.class, e);
		}
	}

	@Test
	public void testCheckDay() {
		try {
			FinDate.checkDay(2018, 2, 29);
			throw new IllegalStateException(TestUtils.EXCEPTION_EXPECTED_BUT_NOT_THROWN);
		} catch (final Exception e) {
			TestUtils.checkException(IllegalArgumentException.class, e);
		}
		FinDate.checkDay(2018, 2, 28);
	}

	@Test
	public void testCheckMonth() {
		try {
			FinDate.checkMonth(0);
			throw new IllegalStateException(TestUtils.EXCEPTION_EXPECTED_BUT_NOT_THROWN);
		} catch (final Exception e) {
			TestUtils.checkException(IllegalArgumentException.class, e);
		}
		try {
			FinDate.checkMonth(13);
			throw new IllegalStateException(TestUtils.EXCEPTION_EXPECTED_BUT_NOT_THROWN);
		} catch (final Exception e) {
			TestUtils.checkException(IllegalArgumentException.class, e);
		}
		FinDate.checkMonth(6);
	}

	@Test
	public void testCheckYear() {
		try {
			FinDate.checkYear(-1);
			throw new IllegalStateException(TestUtils.EXCEPTION_EXPECTED_BUT_NOT_THROWN);
		} catch (final Exception e) {
			TestUtils.checkException(IllegalArgumentException.class, e);
		}
		try {
			FinDate.checkYear(13000);
			throw new IllegalStateException(TestUtils.EXCEPTION_EXPECTED_BUT_NOT_THROWN);
		} catch (final Exception e) {
			TestUtils.checkException(IllegalArgumentException.class, e);
		}
		FinDate.checkYear(1969);
	}

	@Test
	public void testCompareTo() {
		final FinDate d1 = new FinDate(1969, 5, 11);
		final FinDate d2 = new FinDate(2018, 8, 25);
		assertEquals(-1, d1.compareTo(d2));
		assertEquals(1, d2.compareTo(d1));
		assertEquals(0, d1.compareTo(d1));
		try {
			d1.compareTo(null);
			throw new IllegalStateException(TestUtils.EXCEPTION_EXPECTED_BUT_NOT_THROWN);
		} catch (final Exception e) {
			TestUtils.checkException(NullPointerException.class, e);
		}
	}

	@Test
	public void testCreate() {
		final Calendar calendar = Calendar.getInstance();
		calendar.set(2018, Calendar.AUGUST, 30, 0, 0, 0);
		final FinDate fd1 = FinDate.create(calendar);
		assertEquals(2018, fd1.getYear());
		assertEquals(8, fd1.getMonth());
		assertEquals(30, fd1.getDay());
		try {
			FinDate.create((Calendar) null);
			throw new IllegalStateException(TestUtils.EXCEPTION_EXPECTED_BUT_NOT_THROWN);
		} catch (final Exception e) {
			TestUtils.checkException(IllegalArgumentException.class, e);
		}
		try {
			FinDate.create((Date) null);
			throw new IllegalStateException(TestUtils.EXCEPTION_EXPECTED_BUT_NOT_THROWN);
		} catch (final Exception e) {
			TestUtils.checkException(IllegalArgumentException.class, e);
		}
		final Date date = new Date(calendar.getTimeInMillis());
		final FinDate fd2 = FinDate.create(date);
		assertEquals(fd1, fd2);
	}

	@Test
	public void testEquals() {
		final FinDate d1 = new FinDate(2018, 8, 25);
		final FinDate d2 = new FinDate(2018, 8, 25);
		assertEquals(d1, d2);
		assertTrue(d1 != d2);
		try {
			d1.compareTo(null);
			throw new IllegalStateException(TestUtils.EXCEPTION_EXPECTED_BUT_NOT_THROWN);
		} catch (final Exception e) {
			TestUtils.checkException(NullPointerException.class, e);
		}
		assertFalse(d1.equals(null));
		assertTrue(d1.equals(d1));
		assertFalse(d1.equals(new FinDate(2018, 9, 1)));
		assertFalse(d1.equals(new Object()));
		assertFalse(d1.equals(new FinDate(2019, 8, 25)));
		assertFalse(d1.equals(new FinDate(2018, 9, 25)));
	}

	@Test
	public void testFinDate() {
		FinDate fd = new FinDate(1969, 11, 5);
		assertNotNull(fd);
		try {
			fd = new FinDate(-1, -1, -1);
			throw new IllegalStateException(TestUtils.EXCEPTION_EXPECTED_BUT_NOT_THROWN);
		} catch (final Exception e) {
			TestUtils.checkException(IllegalArgumentException.class, e);
		}
	}

	@Test
	public void testHashCode() {
		final FinDate d1 = new FinDate(2018, 9, 1);
		final FinDate d2 = new FinDate(2018, 9, 1);
		assertEquals(d1.hashCode(), d2.hashCode());
	}

	@Test
	public void testIsLastDayOfMonth() {
		assertTrue(new FinDate(2018, 8, 31).isLastDayOfMonth());
		assertFalse(new FinDate(2018, 9, 1).isLastDayOfMonth());
	}

	@Test
	public void testIsLeapYear() {
		assertFalse(FinDate.isLeapYear(1969));
		assertTrue(FinDate.isLeapYear(2000));
		assertFalse(FinDate.isLeapYear(1900));
		assertTrue(FinDate.isLeapYear(0));
		assertFalse(FinDate.isLeapYear(-1));
		assertTrue(FinDate.isLeapYear(2004));
	}

	@Test
	public void testIsWeekend() {
		assertFalse(TEST_DATE.isWeekend());
		assertTrue(new FinDate(2018, 9, 1).isWeekend());
		assertTrue(new FinDate(2018, 9, 2).isWeekend());
		assertFalse(new FinDate(2018, 9, 3).isWeekend());
	}

	@Test
	public void testNextWorkingDate() {
		assertEquals(new FinDate(2018, 9, 4), new FinDate(2018, 9, 3).nextWorkingDate());
		assertEquals(new FinDate(2018, 9, 3), new FinDate(2018, 9, 1).nextWorkingDate());
		assertEquals(new FinDate(2018, 9, 3), new FinDate(2018, 9, 2).nextWorkingDate());
	}

	@Test
	public void testPreviousWorkingDate() {
		assertEquals(new FinDate(2018, 8, 30), new FinDate(2018, 8, 31).previousWorkingDate());
		assertEquals(new FinDate(2018, 8, 31), new FinDate(2018, 9, 1).previousWorkingDate());
		assertEquals(new FinDate(2018, 8, 31), new FinDate(2018, 9, 2).previousWorkingDate());
	}

	@Test
	public void testSubtractDay() {
		final FinDate d1 = new FinDate(2018, 8, 29);
		assertEquals(d1, TEST_DATE.addDays(-1));
		assertEquals(new FinDate(2018, 8, 28), d1.addDays(-1));
		assertEquals(new FinDate(2018, 2, 28), new FinDate(2018, 3, 1).addDays(-1));
		assertEquals(new FinDate(2018, 2, 28), new FinDate(2018, 3, 2).addDays(-2));
		assertEquals(new FinDate(2004, 2, 28), new FinDate(2004, 3, 1).addDays(-2));
		assertEquals(new FinDate(2017, 12, 31), new FinDate(2018, 1, 1).addDays(-1));
	}

	@Test
	public void testSubtractMonth() {
		final FinDate d1 = new FinDate(2018, 7, 30);
		assertEquals(d1, TEST_DATE.addMonths(-1));
		final FinDate d2 = new FinDate(2004, 3, 31);
		assertEquals(new FinDate(2004, 2, 29), d2.addMonths(-1));
		assertEquals(new FinDate(2018, 12, 15), new FinDate(2019, 1, 15).addMonths(-1));
	}

	@Test
	public void testSubtractYear() {
		final FinDate d1 = new FinDate(2004, 2, 29);
		assertEquals(new FinDate(2003, 2, 28), d1.subtractYear());
	}

	@Test
	public void testToCalendar() {
		final FinDate fd = new FinDate(2018, 8, 28);
		final Calendar c = fd.toCalendar();
		assertTrue(c instanceof Calendar);
		assertEquals(2018, c.get(Calendar.YEAR));
		assertEquals(Calendar.AUGUST, c.get(Calendar.MONTH));
		assertEquals(28, c.get(Calendar.DAY_OF_MONTH));
	}

	@Test
	public void testToDate() {
		final FinDate fd = new FinDate(2018, 8, 28);
		final Date d = fd.toDate();
		assertTrue(d instanceof Date);
		final Calendar c = Calendar.getInstance();
		c.setTime(d);
		assertEquals(2018, c.get(Calendar.YEAR));
		assertEquals(Calendar.AUGUST, c.get(Calendar.MONTH));
		assertEquals(28, c.get(Calendar.DAY_OF_MONTH));
	}

	@Test
	public void testToday() {
		final Calendar c = Calendar.getInstance();
		final FinDate fd = FinDate.today();
		assertEquals(fd.getDay(), c.get(Calendar.DAY_OF_MONTH));
		assertEquals(fd.getMonth(), c.get(Calendar.MONTH) + 1);
		assertEquals(fd.getYear(), c.get(Calendar.YEAR));
	}

	@Test
	public void testToString() {
		final FinDate fd = new FinDate(2018, 8, 28);
		assertNotNull(fd.toString());
	}

	@Test
	public void testWorkingDaysInMonth() {
		assertEquals(23, FinDate.workingDaysInMonth(2018, 1));
		assertEquals(20, FinDate.workingDaysInMonth(2018, 2));
		assertEquals(22, FinDate.workingDaysInMonth(2018, 3));
		assertEquals(21, FinDate.workingDaysInMonth(2018, 4));
		assertEquals(23, FinDate.workingDaysInMonth(2018, 5));
		assertEquals(21, FinDate.workingDaysInMonth(2018, 6));
		assertEquals(22, FinDate.workingDaysInMonth(2018, 7));
		assertEquals(23, FinDate.workingDaysInMonth(2018, 8));
		assertEquals(20, FinDate.workingDaysInMonth(2018, 9));
		assertEquals(23, FinDate.workingDaysInMonth(2018, 10));
		assertEquals(22, FinDate.workingDaysInMonth(2018, 11));
		assertEquals(21, FinDate.workingDaysInMonth(2018, 12));
	}
	
	@Test
	public void testJulianDate() {
		assertEquals(2458367, FinDate.julianDay(2018, 9, 5));
		assertEquals(2458336, FinDate.julianDay(2018, 8, 5));
		assertEquals(1721060, FinDate.MIN.getJulian());
		assertEquals(new FinDate(2018, 9, 5), FinDate.toFinDate(2458367));
	}
}
