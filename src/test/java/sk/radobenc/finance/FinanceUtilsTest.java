package sk.radobenc.finance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FinanceUtilsTest {

	private static class DataObject {
		private final List<String> data;

		public DataObject() {
			data = new ArrayList<String>();
			data.add("Orange");
			data.add("Appled");
		}
	}

	Logger logger = LoggerFactory.getLogger(FinanceUtilsTest.class);

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testLazyValue() {
		final FinanceUtils.LazyValue<DataObject> lv = new FinanceUtils.LazyValue<DataObject>() {

			@Override
			public DataObject create() {
				return new DataObject();
			}

		};
		assertTrue(lv.get().data.contains("Orange"));
	}

	@Test
	public void testLeftPad() {
		try {
			FinanceUtils.leftPad(null, 0, ' ');
			throw new IllegalStateException(TestUtils.EXCEPTION_EXPECTED_BUT_NOT_THROWN);
		} catch (final Exception e) {
			TestUtils.checkException(IllegalArgumentException.class, e);
		}
		try {
			FinanceUtils.leftPad("", 0, ' ');
			throw new IllegalStateException(TestUtils.EXCEPTION_EXPECTED_BUT_NOT_THROWN);
		} catch (final Exception e) {
			TestUtils.checkException(IllegalArgumentException.class, e);
		}
		try {
			assertEquals("34567890", FinanceUtils.leftPad("34567890", 7, '0'));
			throw new IllegalStateException(TestUtils.EXCEPTION_EXPECTED_BUT_NOT_THROWN);
		} catch (final Exception e) {
			TestUtils.checkException(IllegalArgumentException.class, e);
		}
		assertEquals("34567890", FinanceUtils.leftPad("34567890", 8, '0'));
		assertEquals("0034567890", FinanceUtils.leftPad("34567890", 10, '0'));
		assertEquals("034567890 ", FinanceUtils.leftPad("34567890 ", 10, '0'));
		assertEquals("0034567890", FinanceUtils.leftPad(34567890, 10));
		assertEquals("00345.6789", FinanceUtils.leftPad(345.6789, 10));
		assertEquals("         X", FinanceUtils.leftPad("X", 10));
	}

	@Test
	public void testMedian() {
		try {
			FinanceUtils.median(null);
			throw new IllegalStateException(TestUtils.EXCEPTION_EXPECTED_BUT_NOT_THROWN);
		} catch (final Exception e) {
			TestUtils.checkException(IllegalArgumentException.class, e);
		}
		assertEquals(3.0, FinanceUtils.median(Arrays.asList(3.0, 2.0, 1.0, 9.0, 13.0)).doubleValue(), 10E-6);
		assertEquals(3.5, FinanceUtils.median(Arrays.asList(3.0, 2.0, 1.0, 9.0, 4.0, 13.0)).doubleValue(), 10E-6);
	}

	@Test
	public void testMonthlyIncome() {
		assertEquals(972.2964,
				FinanceUtils.monthlyIncome(new BigDecimal(62.7288 / 2), 2018, 8, true).doubleValue(), TestUtils.EPS);
		assertEquals(940.932,
				FinanceUtils.monthlyIncome(new BigDecimal(62.7288 / 2), 2018, 9, true).doubleValue(), TestUtils.EPS);
		assertEquals((double) 7360,
				FinanceUtils.monthlyIncome(new BigDecimal(40 * 8), 2018, 8, false).doubleValue(), TestUtils.EPS);
	}

	@Test
	public void testVersion() {
		assertEquals(12, FinanceUtils.version().length());
	}
}
