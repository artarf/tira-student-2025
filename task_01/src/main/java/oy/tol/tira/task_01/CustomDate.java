package oy.tol.tira.task_01;

/**
 * Custom date handles simple gregorian dates.
 * 
 * Valid values of day depends on the month, first day is 1.
 * Valid values of month are 1..12.
 * All integer values are valid for the year.
 * 
 * @see https://en.wikipedia.org/wiki/Gregorian_calendar
 */
public class CustomDate {
	
	private int day;
	private int month;
	private int year;

	/**
	 * Creates a custom date object from parameters.
	 * 
	 * @param d The day of the month.
	 * @param m The month of the year.
	 * @param y The year.
	 * 
	 * @throws IllegalStateException if the date is not valid.
	 */
	public CustomDate(final int d, final int m, final int y) throws IllegalStateException {
		this.day = d;
		this.month = m;
		this.year = y;
		invariant();
	}

	/**
	 * Creates a custom date object from parameter date.
	 * 
	 * @param another Another date to copy values from.
	 * 
	 * @throws IllegalStateException if the date is not valid.
	 */
	public CustomDate(final CustomDate another) throws IllegalStateException {
		this.day = another.day;
		this.month = another.month;
		this.year = another.year;
		invariant();
	}

	/**
	 * For querying the day of month.
	 * 
	 * @return The day of month.
	 */
	public int getDay() {
		return day;
	}

	/**
	 * Sets the day of month.
	 * 
	 * @param day The new day of date.
	 * 
	 * @throws IllegalArgumentException if day is not a valid day for this date.
	 * @throws throws IllegalStateException if the resulting date is not valid date.
	 */
	public void setDay(final int dd) throws IllegalArgumentException, IllegalStateException {
		if (dd < 1 || dd > CustomDate.daysInMonth(month, year)) {
			throw new IllegalArgumentException(String.format("No such day %d in month %d of year %d", day, month, year));
		}
		this.day = dd;
		invariant();
	}

	/**
	 * Gets the month of the year.
	 * 
	 * @return The month of the year, January is month 1.
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * Sets the month of the date.
	 * 
	 * @param month The new month for the date.
	 * 
	 * @throws IllegalArgumentException if month value is not valid.
	 * @throws IllegalStateException if the date is invalid after setting the month.
	 */
	public void setMonth(final int mm) throws IllegalArgumentException, IllegalStateException  {
		if (mm < 1 || mm > 12 || day > CustomDate.daysInMonth(mm, year)) {
			throw new IllegalArgumentException("No such month");
		}
		this.month = mm;
		invariant();
	}

	/**
	 * Gets the year of the date.
	 * 
	 * @return The year of the date.
	 */
	public int getYear() {
		return year;
	}

	/**
	 * Sets the year of the date.
	 * 
	 * @param year The new year for the date.
	 * 
	 * @throws IllegalStateException if the date is invalid after setting the year.
	 */

	public void setYear(final int yy) throws IllegalArgumentException, IllegalStateException {
		if (day > CustomDate.daysInMonth(month, yy)) {
			throw new IllegalArgumentException("No such date in year");
		}
		this.year = yy;
		invariant();
	}

	@Override
	public String toString() {
		return String.format("%d.%d.%d", day, month, year);
	}

	public void advance(final int days) {
		if (days <= 0) {
			throw new IllegalArgumentException("Days to advance must be > 0");
		}
		int toAdd =	days;
		if (day + days > CustomDate.daysInMonth(month, year)) {
			do {
				final int remainingDays = CustomDate.daysInMonth(month, year) - day;
				if (day + toAdd > CustomDate.daysInMonth(month, year)) {
					toAdd -= remainingDays + 1;
					day = 1;
					month += 1;
					if (month == 13) {
						month = 1;
						year += 1;
					}
				} else {
					day += toAdd;
					toAdd = 0;
				}
			} while (toAdd > 0);
		} else {
			day += days;
		}
		invariant();
	}

	public static boolean isLeapYear(final int year) {
		return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
	}

	public static int daysInMonth(final int month, final int year) {
		switch (month) {
		case 1, 3, 5, 7, 8, 10, 12:
			return 31;
		case 4, 6, 9, 11:
			return 30;
		case 2:
			if (isLeapYear(year)) {
				return 29;
			} else {
				return 28;
			}
		default:
			return -1;
		}
	}

	private void invariant() {
		var correct_days = daysInMonth(month, year);
		if (day > correct_days || day < 1) {
			throw new IllegalStateException("[CustomDate] Invalid number of days");
		}

		if (correct_days == -1) {
			throw new IllegalStateException("[CustomDate] Invalid month");
		}
	}
	
}
