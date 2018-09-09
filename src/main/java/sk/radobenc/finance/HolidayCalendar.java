package sk.radobenc.finance;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import sk.radobenc.finance.calendar.SlovakHolidayCalendar;

public class HolidayCalendar implements Serializable {

	private static class DayOfYear {
		private final int day;
		private final int month;

		DayOfYear(final int day, final int month) {
			this.day = day;
			this.month = month;
		}

		@Override
		public boolean equals(final Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final DayOfYear other = (DayOfYear) obj;
			if (day != other.day)
				return false;
			if (month != other.month)
				return false;
			return true;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + day;
			result = prime * result + month;
			return result;
		}

		@Override
		public String toString() {

			return new StringBuilder().append(this.getClass().getSimpleName()).append("[day=").append(day)
					.append(", month=").append(month).append("]").toString();
		}
	}

	/**
	 *
	 */
	private static final long serialVersionUID = 9178002200250637526L;

	private static Set<DayOfYear> dates = new HashSet<DayOfYear>();

	private static HolidayCalendar instance;

	/**
	 * Calculates Abraham Lincoln's birthday (12th February) for a specified year.
	 *
	 * @param year The year for which to calculate Abraham's Lincoln birthday.
	 * @return the date representing Abraham Lincoln's birthday.
	 */
	public static final FinDate abrahamLincolnsBirthday(final int year) {
		FinDate.checkYear(year);
		return new FinDate(year, 2, 12);
	}

	/**
	 * Calculates the All Saints Day (1st November) for a specified year.
	 *
	 * @param year The year for which to calculate All Saints Day.
	 * @return the date representing All Saints Day.
	 */
	public static final FinDate allSaintsDay(final int year) {
		FinDate.checkYear(year);
		return new FinDate(year, 11, 1);
	}

	/**
	 * Calculates the Boxing Day (26th December) for a specified year.
	 *
	 * @param year The year for which to calculate Boxing Day.
	 * @return the date representing Boxing Day.
	 */
	public static final FinDate boxingDay(final int year) {
		FinDate.checkYear(year);
		return new FinDate(year, 12, 26);
	}

	/**
	 * Calculates the Christmas Day (25th December) for a specified year.
	 *
	 * @param year The year for which to calculate Christmas Day.
	 * @return the date representing Christmas Day.
	 */
	public static final FinDate christmasDay(final int year) {
		FinDate.checkYear(year);
		return new FinDate(year, 12, 25);
	}

	/**
	 * Calculates the Christmas Eve (24th DEcember) for a specified year.
	 *
	 * @param year The year for which to calculate Christmas Eve.
	 * @return the date representing Christmas Eve.
	 */
	public static final FinDate christmasEve(final int year) {
		FinDate.checkYear(year);
		return new FinDate(year, 12, 24);
	}

	/**
	 * Calculates Cinco de Mayo (5th May) for a specified year.
	 *
	 * @param year The year for which to calculate Cinco de Mayo.
	 * @return a date representing Cinco de Mayo.
	 */
	public static final FinDate cincoDeMayo(final int year) {
		FinDate.checkYear(year);
		return new FinDate(year, 5, 5);
	}

	/**
	 * Calculates the Czechoslovakia Independence Day (28th October) for a specified
	 * year.
	 *
	 * @param year The year for which to calculate Czechoslovakia Independence Day.
	 * @return a date represention Czechoslovakia Independence Day.
	 */
	public static final FinDate czechoslovakiaIndependenceDay(final int year) {
		FinDate.checkYear(year);
		return new FinDate(year, 10, 28);
	}

	/**
	 * Calculates Easter Sunday Written by Gregory N. Mirsky, adjusted by Rado Benc
	 * Source: 2nd Edition by Peter Duffett-Smith. It was originally from Butcher's
	 * Ecclesiastical Calendar, published in 1876. This algorithm has also been
	 * published in the 1922 book General Astronomy by Spencer Jones; in The Journal
	 * of the British Astronomical Association (Vol.88, page 91, December 1977); and
	 * in Astronomical Algorithms (1991) by Jean Meeus. This algorithm holds for any
	 * year in the Gregorian Calendar, which (of course) means years including and
	 * after 1583. a=year%19 b=year/100 c=year%100 d=b/4 e=b%4 f=(b+8)/25
	 * g=(b-f+1)/3 h=(19*a+b-d-g+15)%30 i=c/4 k=c%4 l=(32+2*e+2*i-h-k)%7
	 * m=(a+11*h+22*l)/451 Easter Month =(h+l-7*m+114)/31 [3=March, 4=April]
	 * p=(h+l-7*m+114)%31 Easter Date=p+1 (date in Easter Month) Note: Integer
	 * truncation is already factored into the calculations. Using higher percision
	 * variables will cause inaccurate calculations.
	 *
	 * @param year The year for which the Easter sunday should be calculated.
	 * @return Easter Sunday for the specified year
	 */
	public static final FinDate easterSunday(int year) {
		FinDate.checkYear(year);
		if (year < 1900) {
			// if year is in java format put it into standard
			// format for the calculation
			year += 1900;
		}
		final int nA = year % 19;
		final int nB = year / 100;
		final int nC = year % 100;
		final int nD = nB / 4;
		final int nE = nB % 4;
		final int nF = (nB + 8) / 25;
		final int nG = (nB - nF + 1) / 3;
		final int nH = (19 * nA + nB - nD - nG + 15) % 30;
		final int nI = nC / 4;
		final int nK = nC % 4;
		final int nL = (32 + 2 * nE + 2 * nI - nH - nK) % 7;
		final int nM = (nA + 11 * nH + 22 * nL) / 451;
		// [3=March, 4=April]
		int easterMonth = (nH + nL - 7 * nM + 114) / 31;
		--easterMonth;
		final int nP = (nH + nL - 7 * nM + 114) % 31;
		// Date in Easter Month.
		final int easterDay = nP + 1;
		return new FinDate(year, easterMonth, easterDay);
	}

	public static HolidayCalendar getInstance() {
		if (null == instance) {
			instance = new HolidayCalendar();
		}
		return instance;
	}

	public static HolidayCalendar getInstance(final Locale locale) {
		if (locale.getLanguage().equals("sk") && locale.getCountry().equals("SK")) {
			return new SlovakHolidayCalendar();
		}
		return instance;
	}

	public static FinDate goodFriday(final int year) {
		return easterSunday(year).addDays(-2);
	}

	/**
	 * Calculates the Groundhog Day (8th February) for a specified year.
	 *
	 * @param year The year for which to calculate Groundhog Day.
	 * @return a date representing Groundhog Day.
	 */
	public static final FinDate groundHogDay(final int year) {
		FinDate.checkYear(year);
		return new FinDate(year, 2, 8);
	}

	/**
	 * Calculates the Haloween's (31st October) date.
	 *
	 * @param year The year for which to calculate Haloween.
	 * @return a date representing Haloween.
	 */
	public static final FinDate halloween(final int year) {
		FinDate.checkYear(year);
		return new FinDate(year, 10, 31);
	}

	/**
	 * Calculates the Independence Day (4th July) for a specified year.
	 *
	 * @param year The year for which to calculate Independence Day.
	 * @return a date representing Independence Day.
	 */
	public static final FinDate independenceDay(final int year) {
		FinDate.checkYear(year);
		return new FinDate(year, 7, 4);
	}

	/**
	 * Calculates the Jan Hus Day (6th July) for a specified year.
	 *
	 * @param year The year for which to calculate Jan Hus Day.
	 * @return a date representing Jan Hus Day.
	 */
	public static final FinDate janHusDay(final int year) {
		FinDate.checkYear(year);
		return new FinDate(year, 7, 6);
	}

	/**
	 * Calculates the Labour Day (1st May) for a specified year.
	 *
	 * @param year The year for which to calculate Labour Day.
	 * @return a date representing Labour Day.
	 */
	public static final FinDate labourDay(final int year) {
		FinDate.checkYear(year);
		return new FinDate(year, 5, 1);
	}

	/**
	 * Calculates the New Years Day (1st January) for a specified year.
	 *
	 * @param year The year for which to calculate New Years Day.
	 * @return a date representing New Years Day.
	 */
	public static final FinDate newYearsDay(final int year) {
		FinDate.checkYear(year);
		return new FinDate(year, 1, 1);
	}

	/**
	 * Calculates the Orthodox Christmas Day (6th January) for a specified year.
	 * Also called Three Magi.
	 *
	 * @param year The year for which to calculate Orthodox Christmas Day.
	 * @return a date representing the Orthodox Christmas Day.
	 */
	public static final FinDate orthodoxChristmasDay(final int year) {
		FinDate.checkYear(year);
		return new FinDate(year, 1, 6);
	}

	/**
	 * Calculate the Our Lady Of Sorrows Day (15th September) for a specified year.
	 *
	 * @param year The year for which to calculate Our Lady Of Sorrows Day.
	 * @return a date representing Our Lady Of Sorrows Day.
	 */
	public static final FinDate ourLadyOfSorrowsDay(final int year) {
		FinDate.checkYear(year);
		return new FinDate(year, 9, 15);
	}

	/**
	 * Calculates Quebec Civics Holiday (3rd January) for a specified year.
	 *
	 * @param year The year for which to calculate Quebec civic holiday.
	 * @return a date representing Quebec civic holiday.
	 */
	public static final FinDate quebecCivicHoliday(final int year) {
		FinDate.checkYear(year);
		return new FinDate(year, 1, 3);
	}

	/**
	 * Calculates the Robert E. Lee Day (18th January) for a specified year.
	 *
	 * @param year The year for which to calculate Robert E. Lee Day.
	 * @return a date representing Robert E. Lee Day.
	 */
	public static final FinDate robertELeeDay(final int year) {
		FinDate.checkYear(year);
		return new FinDate(year, 1, 18);
	}

	/**
	 * Calculates the Saint Cyril and Methodius' Day (5th July) for a specified
	 * year.
	 *
	 * @param year The year for which to calculate Saint Cyril and Methodius' Day
	 * @return a date representing Saint Cyril and Methodius' Day.
	 */
	public static final FinDate saintCyrilAndMethodiusDay(final int year) {
		FinDate.checkYear(year);
		return new FinDate(year, 7, 5);
	}

	/**
	 * Calculates the Saint Patrick's Day (17th March) for a specifed year.
	 *
	 * @param year The year for which to calculate Saint Patrick's Day.
	 * @return a date representing Saint Patrick's Day.
	 */
	public static final FinDate saintPatricksDay(final int year) {
		FinDate.checkYear(year);
		return new FinDate(year, 3, 17);
	}

	/**
	 * Calculates the Students Day (17th November) for a specified year. Also called
	 * The day of the struggle against the totalitarianism in Czech Republic and
	 * Slovakia.
	 *
	 * @param year The year for which to calculate Students Day.
	 * @return a date representing Students Day.
	 */
	public static final FinDate studentsDay(final int year) {
		FinDate.checkYear(year);
		return new FinDate(year, 11, 17);
	}

	/**
	 * Calculates the Susan B. Anthony's Day (15th February) for a specified year.
	 *
	 * @param year The year for which to calculate Susan B. Anthony's day.
	 * @return a date representing Susan B. Anthony's day.
	 */
	public static final FinDate susanBAnthonyDay(final int year) {
		FinDate.checkYear(year);
		return new FinDate(year, 2, 15);
	}

	/**
	 * Calculates U.S Election Day, which is 1st Tuesday in November for a specified
	 * year.
	 *
	 * @param year The year for which to calculate Election Day.
	 * @return a date representing U.S. Election Day.
	 * @see Date
	 */
	public static final FinDate usElectionDay(final int year) {
		FinDate.checkYear(year);
		final int d = FinDate.dayOfWeek(year, 11, 1) - 1;
		return new FinDate(year, 11, d == 0 ? 7 : d);
	}

	/**
	 * Calculates the Valentine's Day (14th February) for a specified year.
	 *
	 * @param year The year for which to calculate Valentine's Day.
	 * @return a date representing Valentine's Day.
	 */
	public static final FinDate valentinesDay(final int year) {
		FinDate.checkYear(year);
		return new FinDate(year, 2, 14);
	}

	/**
	 * Calculates the Veterans Day (11th November) for a specified year.
	 *
	 * @param year The year for which to calculate Veterans Day.
	 * @return a date representing Veterans Day.
	 */
	public static final FinDate veteransDay(final int year) {
		FinDate.checkYear(year);
		return new FinDate(year, 11, 11);
	}

	/**
	 * Calculates the Victory against Fascism Day (8th May) for a specified year.
	 *
	 * @param year The year for which to calculate Victory against Fascism Day.
	 * @return a date representing Victory against Fascism Day.
	 */
	public static final FinDate victoryAgainstFascismDay(final int year) {
		FinDate.checkYear(year);
		return new FinDate(year, 5, 8);
	}

	{
		dates.add(new DayOfYear(1, 1));
		dates.add(new DayOfYear(1, 5));
		dates.add(new DayOfYear(25, 12));
		dates.add(new DayOfYear(26, 12));
	}

	public boolean isHoliday(final FinDate finDate) {
		return dates.contains(new DayOfYear(finDate.getDay(), finDate.getMonth()))
				|| finDate.equals(goodFriday(finDate.getYear()))
				|| finDate.equals(easterSunday(finDate.getYear()).addDays(1));
	}

	public static FinDate easterMonday(int year) {
		return easterSunday(year).addDays(1);
	}
}
