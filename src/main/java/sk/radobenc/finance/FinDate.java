package sk.radobenc.finance;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class FinDate implements Serializable, Comparable<FinDate> {

	private static final long serialVersionUID = -8159792542876708207L;

	private static final int[] daysInMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	public static final FinDate MIN = new FinDate(0, 1, 1); // 1.1.0

	public static final int MIN_JULIAN = MIN.getJulian();

	public static int ageInDays(final int year1, final int month1, final int day1, final int year2, final int month2,
			final int day2) {
		return julianDay(year2, month2, day2) - julianDay(year1, month1, day1);
	}

	public static int ageInYears(final FinDate start, final FinDate end) {
		if (null == start) {
			throw new IllegalArgumentException("start = " + start);
		}
		if (null == end) {
			throw new IllegalArgumentException("end = " + end);
		}
		if (start.after(end)) {
			throw new IllegalArgumentException(
					String.format("Start date %s must not be greater than end date %s", start, end));
		}
		return ageInYears(start.year, start.month, start.day, end.year, end.month, end.day);
	}

	static int ageInYears(final int year1, final int month1, final int day1, final int year2, final int month2,
			final int day2) {
		int result = year2 - year1;
		if (month2 < month1) {
			result--;
		}
		if (month2 == month1) {
			if (day2 < day1) {
				result--;
			}
		}
		return result;
	}

	static void checkDay(final int year, final int month, final int day) {
		if ((day < 1) || (day > daysInMonth(year, month))) {
			throw new IllegalArgumentException("day = " + day);
		}
	}

	static void checkMonth(final int month) {
		if ((month < 1) || (month > 12)) {
			throw new IllegalArgumentException("month = " + month);
		}
	}

	public static void checkYear(final int year) {
		if ((year < 0) || (year > 9999)) {
			throw new IllegalArgumentException("year = " + year);
		}
	}

	public static FinDate create(final Calendar calendar) {
		if (null == calendar) {
			throw new IllegalArgumentException("calendar = " + calendar);
		}
		return new FinDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DATE));
	}

	public static FinDate create(final Date date) {
		if (null == date) {
			throw new IllegalArgumentException("date = " + date);
		}
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return create(calendar);
	}

	public static int dayOfWeek(final int year, final int month, final int day) {
		return julianDay(year, month, day) % 7 + 1;
	}

	public static int daysInMonth(final int year, final int month) {
		if (month == 2) {
			return isLeapYear(year) ? 29 : 28;
		} else
			return daysInMonth[month - 1];
	}

	public static boolean isLeapYear(final int year) {
		return ((year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0));
	}

	static int julianDay(final int year, final int month, final int day) {
		return (1461 * (year + 4800 + (month - 14) / 12)) / 4 + (367 * (month - 2 - 12 * ((month - 14) / 12))) / 12
				- (3 * ((year + 4900 + (month - 14) / 12) / 100)) / 4 + day - 32075;
	}

	public static FinDate today() {
		final Calendar c = Calendar.getInstance();
		return new FinDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DATE));
	}

	public static FinDate toFinDate(final int julianDay) {
		final int f = julianDay + 1401 + (((4 * julianDay + 274277) / 146097) * 3) / 4 + (-38);
		final int h = 5 * ((4 * f + 3) % 1461 / 4) + 2;
		final int day = (h % 153) / 5 + 1;
		final int month = ((h / 153 + 2) % 12) + 1;
		final int year = ((4 * f + 3) / 1461) - 4716 + (12 + 2 - month) / 12;
		return new FinDate(year, month, day);
	}

	public static int workingDaysInMonth(final int year, final int month) {
		int result = 0;
		final byte[] mask = { 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 1,
				1, 1, 1, 0, 0, 1, 1 };
		final int dw = dayOfWeek(year, month, 1);
		for (int i = 0; i < daysInMonth(year, month); i++) {
			result += mask[i + dw - 1];
		}
		return result;
	}

	private final int day;
	private final int julian;
	private final int month;
	private final int year;

	public FinDate(final int year, final int month, final int day) {
		checkYear(year);
		checkMonth(month);
		checkDay(year, month, day);
		this.year = year;
		this.month = month;
		this.day = day;
		julian = julianDay(year, month, day);
	}

	public FinDate addDays(final int number) {
		return toFinDate(julian + number);
	}

	public FinDate addMonth() {
		int y = year;
		int m = month;
		int d = day;
		if (12 == m) {
			y++;
			m = 1;
		} else {
			m++;
		}
		final int nextMonthMax = daysInMonth(y, m);
		if (nextMonthMax < d) {
			d = nextMonthMax;
		}
		return new FinDate(y, m, d);
	}

	public FinDate addMonths(final int number) {
		FinDate temp = this;
		if (number > 0) {
			for (final int i = 0; i < number;) {
				temp = temp.addMonth();
				return temp;
			}
		} else if (number < 0) {
			for (final int i = 0; i < -number;) {
				temp = temp.subtractMonth();
				return temp;
			}
		}
		return this;
	}

	public FinDate addYear() {
		int d = day;
		final int daysInMonthNext = daysInMonth(year + 1, month);
		if (daysInMonthNext < day) {
			d = daysInMonthNext;
		}
		return new FinDate(year + 1, month, d);
	}

	public boolean after(final FinDate date) {
		return julian > date.julian;
	}

	public boolean before(final FinDate date) {
		return julian < date.julian;
	}

	public int compareTo(final FinDate o) {
		return Integer.compare(julian, o.julian);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final FinDate other = (FinDate) obj;
		if (day != other.day)
			return false;
		if (month != other.month)
			return false;
		if (year != other.year)
			return false;
		return true;
	}

	public final int getDay() {
		return day;
	}

	public final int getJulian() {
		return julian;
	}

	public final int getMonth() {
		return month;
	}

	private int getWeekDay() {
		return julian % 7 + 1;
	}

	public final int getYear() {
		return year;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + day;
		result = prime * result + month;
		result = prime * result + year;
		return result;
	}

	public boolean isLastDayOfMonth() {
		return day == daysInMonth(year, month);
	}

	public boolean isWeekend() {
		return getWeekDay() > 5;
	}

	public FinDate nextWorkingDate() {
		final int increment[] = { 1, 1, 1, 1, 3, 2, 1 };
		return this.addDays(increment[getWeekDay() - 1]);
	}

	public FinDate previousWorkingDate() {
		final int increment[] = { -3, -1, -1, -1, -1, -1, -2 };
		return this.addDays(increment[getWeekDay() - 1]);
	}

	public FinDate subtractMonth() {
		int y = year;
		int m = month;
		int d = day;
		if (1 == m) {
			y--;
			m = 12;
		} else {
			m--;
		}
		final int prevMonthDays = daysInMonth(y, m);
		if (d > prevMonthDays) {
			d = prevMonthDays;
		}
		return new FinDate(y, m, d);

	}

	public FinDate subtractYear() {
		final int prevYearDays = daysInMonth(year - 1, month);
		int d = day;
		if (d > prevYearDays) {
			d = prevYearDays;
		}
		return new FinDate(year - 1, month, d);
	}

	public Calendar toCalendar() {
		final Calendar result = Calendar.getInstance();
		result.clear();
		result.set(year, month - 1, day);
		return result;
	}

	public Date toDate() {
		return new Date(toCalendar().getTimeInMillis());
	}

	public long toLong() {
		return toCalendar().getTimeInMillis();
	}

	@Override
	public String toString() {
		return String.format("%04d-%02d-%02d", year, month, day);
	}
}
