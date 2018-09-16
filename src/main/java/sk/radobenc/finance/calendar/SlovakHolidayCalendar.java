package sk.radobenc.finance.calendar;

import sk.radobenc.finance.FinDate;
import sk.radobenc.finance.HolidayCalendar;

public class SlovakHolidayCalendar extends HolidayCalendar {

	private static final long serialVersionUID = 4829094420979724634L;

	/**
	 * Calculates the Slovak Constitution Day for a specified year.
	 *
	 * @param year The year for which to calculate Slovak Constitution Day.
	 * @return a date representing Slovak Constitution Day.
	 */
	public static final FinDate slovakConstitutionDay(final int year) {
		FinDate.checkYear(year);
		return new FinDate(year, 9, 1);
	}

	/**
	 * Calculates the Slovak National Upspring Day for a specified year.
	 *
	 * @param year The year for which to calculate Slovak National Upspring Day.
	 * @return a date representing Slovak National Upspring Day.
	 */
	public static final FinDate slovakNationalUpspringDay(final int year) {
		FinDate.checkYear(year);
		return new FinDate(year, 8, 29);
	}

	@Override
	public boolean isHoliday(final FinDate date) {
		return date.equals(HolidayCalendar.newYearsDay(date.getYear()))
				|| date.equals(HolidayCalendar.orthodoxChristmasDay(date.getYear()))
				|| date.equals(HolidayCalendar.goodFriday(date.getYear()))
				|| date.equals(HolidayCalendar.easterSunday(date.getYear()))
				|| date.equals(HolidayCalendar.easterMonday(date.getYear()))
				|| date.equals(HolidayCalendar.labourDay(date.getYear()))
				|| date.equals(HolidayCalendar.victoryAgainstFascismDay(date.getYear()))
				|| date.equals(HolidayCalendar.saintCyrilAndMethodiusDay(date.getYear()))
				|| date.equals(SlovakHolidayCalendar.slovakNationalUpspringDay(date.getYear()))
				|| date.equals(SlovakHolidayCalendar.slovakConstitutionDay(date.getYear()))
				|| date.equals(HolidayCalendar.ourLadyOfSorrowsDay(date.getYear()))
				|| date.equals(HolidayCalendar.studentsDay(date.getYear()))
				|| date.equals(HolidayCalendar.christmasEve(date.getYear()))
				|| date.equals(HolidayCalendar.christmasDay(date.getYear()))
				|| date.equals(HolidayCalendar.boxingDay(date.getYear()));
	}

}
