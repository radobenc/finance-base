package sk.radobenc.finance;

import static org.junit.Assert.assertEquals;

import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class TestUtils {

	static Logger logger = LoggerFactory.getLogger(TestUtils.class);
	public static final String EXCEPTION_EXPECTED_BUT_NOT_THROWN = "Exception expected but not thrown";
	public static final double EPS = 10E-7;

	static void checkException(final Class<? extends Throwable> expected, final Exception e) {
		assertEquals(expected, e.getClass());
		logger.debug(e.getMessage(), e);
	}

	private TestUtils() {
	}

	static void printPayments(final Collection<Payment> payments) {
		final PrintStream ps = new PrintStream(System.out);
		for (final Payment p : payments) {
			final FinDate billingDate = (FinDate) p.getAttribute(Attribute.BILLING_DATE, FinDate.MIN);
			ps.println(String.format("%s %f %s", billingDate, new BigDecimal(p.getAmount().toString()),
					p.getAttribute(Attribute.CURRENCY_CODE)));
		}
	
	}

}
