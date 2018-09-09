package sk.radobenc.finance;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FinDatePerformance {

	private static final int ITERATIONS = 100;

	public static void main(String[] args) {
		testDaysInMonthSpeed();
		testSubtractDaySpeed();
		testWorkingDaysInMonthSpeed();
	}

	private static int counter = 0;
	static Logger logger = LoggerFactory.getLogger(FinDatePerformance.class);
	private static long startTimeInMillis;
	private static long timeInMillis;

	public static void testDaysInMonthSpeed() {
		int data = 0;
		// Test FinDate implementation
		long startTimeInMillis = System.currentTimeMillis();
		FinDate min = FinDate.MIN.addMonths(1);
		for (int i = 0; i < ITERATIONS; i++) {
			FinDate d = FinDate.today();
			while (d.after(min)) {
				d = d.addMonths(-1);
				counter++;
				data += FinDate.daysInMonth(d.getYear(), d.getMonth()) < 31 ? 1 : 0;
			}
		}
		long timeInMillis = System.currentTimeMillis() - startTimeInMillis;
		logger.info(new StringBuilder().append("Elapsed time of FinDate.daysInMonth = ").append(timeInMillis)
				.append(" [ms], count = ").append(counter).append(", data = ").append(data).toString());
		final Calendar end = Calendar.getInstance();
		end.set(0, Calendar.FEBRUARY, 1);
		startTimeInMillis = System.currentTimeMillis();
		counter = 0;
		data = 0;
		for (int i = 0; i < ITERATIONS; i++) {
			final Calendar c = Calendar.getInstance();
			while (c.after(end)) {
				c.add(Calendar.MONTH, -1);
				counter++;
				data += c.getActualMaximum(Calendar.DAY_OF_MONTH) < 31 ? 1 : 0;
			}
		}
		timeInMillis = System.currentTimeMillis() - startTimeInMillis;
		logger.info(new StringBuilder().append("Elapsed time of Calendar based daysInMonth = ").append(timeInMillis)
				.append(" [ms], count = ").append(counter).append(", data = ").append(data).toString());

	}

	public static void testSubtractDaySpeed() {
		startTimeInMillis = System.currentTimeMillis();
		for (int i = 0; i < ITERATIONS; i++) {
			FinDate d = FinDate.today();
			while (d.after(FinDate.MIN)) {
				d = d.addDays(-1);
				counter++;
			}
		}
		timeInMillis = System.currentTimeMillis() - startTimeInMillis;
		logger.info(new StringBuilder().append("Elapsed time of FinDate.subtractDay() = ").append(timeInMillis)
				.append(" [ms], count = ").append(counter).toString());
		counter = 0;

		final Calendar end = Calendar.getInstance();
		startTimeInMillis = System.currentTimeMillis();
		end.set(0, Calendar.JANUARY, 1);
		for (int i = 0; i < ITERATIONS; i++) {
			final Calendar c = Calendar.getInstance();
			while (c.after(end)) {
				c.add(Calendar.DAY_OF_MONTH, -1);
				counter++;
			}
		}
		timeInMillis = System.currentTimeMillis() - startTimeInMillis;
		logger.info(new StringBuilder().append("Elapsed time of Calendar.add() = ").append(timeInMillis)
				.append(" [ms], count = ").append(counter).toString());
	}

	public static void testWorkingDaysInMonthSpeed() {
		int data = 0;
		// Test FinDate implementation
		long startTimeInMillis = System.currentTimeMillis();
		for (int i = 0; i < ITERATIONS; i++) {
			FinDate d = FinDate.today();
			while (d.after(FinDate.MIN.addMonths(1))) {
				d = d.addMonths(-1);
				counter++;
				data += FinDate.workingDaysInMonth(d.getYear(), d.getMonth());
			}
		}
		long timeInMillis = System.currentTimeMillis() - startTimeInMillis;
		logger.info(new StringBuilder().append("Elapsed time of FinDate.workingDaysInMonth = ").append(timeInMillis)
				.append(" [ms], count = ").append(counter).append(", data = ").append(data).toString());
		final Calendar end = Calendar.getInstance();
		end.set(0, Calendar.FEBRUARY, 1);
		Calendar c2 = Calendar.getInstance();
		startTimeInMillis = System.currentTimeMillis();
		counter = 0;
		data = 0;
		for (int i = 0; i < ITERATIONS; i++) {
			final Calendar c = Calendar.getInstance();
			while (c.after(end)) {
				c.add(Calendar.MONTH, -1);
				counter++;
				for (int j = 1; j <= c.getActualMaximum(Calendar.DATE); j++) {
					c2.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1);
					data += (c2.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
							|| c2.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) ? 0 : 1;
				}
			}
		}
		timeInMillis = System.currentTimeMillis() - startTimeInMillis;
		logger.info(
				new StringBuilder().append("Elapsed time of Calendar based workingDaysInMonth = ").append(timeInMillis)
						.append(" [ms], count = ").append(counter).append(", data = ").append(data).toString());

	}

}
