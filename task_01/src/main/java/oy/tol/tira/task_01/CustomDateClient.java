package oy.tol.tira.task_01;

public class CustomDateClient {

	public static void main(String [] args) {
		testValidDatesConstruction();
		testInvalidDatesConstruction();
		testSetMonthInvalidDate();
		testsuccessfulAdvance();
		testSuccessfulAdvance2();
		testFailingAdvance();
		testChangingLeapYearToNonLeapYear();
	}
	
	private static void testValidDatesConstruction() {
		System.out.println("\n===> testValidDatesConstruction()");
		int remainingCount = validValues.length;
		for (DateValues value : validValues) {
			try {
				var testDate = new CustomDate(value.d, value.m, value.y);
			} catch (IllegalStateException | IllegalArgumentException e) {
				remainingCount--;
				System.out.format("NOT GOOD: Catched %s for VALID date %s%n", e.getClass(), value);
			}
		}
		if (remainingCount == validValues.length) {
			System.out.format("GOOD JOB: Didn't complain about valid dates%n");
		}
	}

	private static void testInvalidDatesConstruction() {
		System.out.println("\n===> testInvalidDatesConstruction()");
		int remainingCount = invalidValues.length;
		for (DateValues value : invalidValues) {
			try {
				var testDate = new CustomDate(value.d, value.m, value.y);
			} catch (IllegalStateException | IllegalArgumentException e) {
				remainingCount--;
				System.out.format("GOOD JOB: Catched %s for date %s%n", e.getClass(), value);
			}
		}
		if (remainingCount > 0) {
			System.out.format("NOT GOOD: Didn't catch %d test cases%n", remainingCount);
		}
	}

	// TODO: Find a way to get a good job in using setXxx
	private static void testSetMonthInvalidDate() {
		System.out.println("\n===> testPreConditionTest()");
		CustomDate date = null;
		try {
			date = new CustomDate(1,1,2025);
			date.advance(364);
			System.out.format("Date is now: %s%n", date);
			System.out.println("Trying to set the month of that date to 2...");
			date.setMonth(2);
		} catch (IllegalArgumentException e) {
			System.out.println("GOOD JOB: precondition catches the error");
		} catch (IllegalStateException e) {
			System.out.println("GOOD JOB: Class invariant catches the error");
		} finally {
			System.out.format("Date is now: %s%n", date);
		}
		if (date.getDay() > CustomDate.daysInMonth(date.getMonth(), date.getYear())) {
			System.out.println("NOT GOOD: Date is still wrong now!");
		}
	}

	private static void testsuccessfulAdvance() {
		System.out.println("\n===> testsuccessfulAdvance()");
		CustomDate date = new CustomDate(20, 2, 2025);
		System.out.format("Date is now %s, advancing it eight (8) days...%n", date);
		date.advance(8);
		System.out.format("Date is now %s%n", date);
		assert(date.toString().equals("28.2.2025"));
		System.out.format("GOOD JOB: we passed the assert so date %s was as expected%n", date);
	}

	private static void testSuccessfulAdvance2() {
		try {
			System.out.println("\n===> testSuccessfulAdvance2()");
			CustomDate date = new CustomDate(20, 2, 2025);
			System.out.format("Date is now %s, advancing it nine (9) days...%n", date);
			date.advance(9);
			System.out.format("Date is now %s%n", date);
			assert(date.toString().equals("1.3.2025")) : date;		
			System.out.format("GOOD JOB: we passed the assert so date %s was as expected%n", date);	
		} catch (AssertionError e) {
			System.out.println("NOT GOOD: advancing a date failed: " + e.getMessage());
		}
	}

	private static void testFailingAdvance() {
		CustomDate date = null;
		try {
			System.out.println("\n===> testFailingAdvance()");
			date = new CustomDate(20, 12, 2025);
			System.out.format("Date is now %s, advancing it two weeks (14 days)...%n", date);
			date.advance(14);
			assert(date.toString().equals("3.1.2026")) : date;			
		} catch (IllegalArgumentException e) {
			System.out.println("GOOD JOB: precondition catches the error");
		} catch (IllegalStateException e) {
			System.out.println("GOOD JOB: Class invariant catches the error");
		} catch (AssertionError e) {
			System.out.println("NOT GOOD: advancing a date failed: " + e.getMessage());
		} finally {
			System.out.format("Date is now: %s%n", date);
		}
	}

	private static void  testChangingLeapYearToNonLeapYear() {
		CustomDate date = null;
		try {
			System.out.println("\n===> testChangingLeapYearToNonLeapYear()");
			date = new CustomDate(29, 2, 2000);
			System.out.format("Date is now %s, setting year to non-leap year...%n", date);
			date.setYear(2025);
		} catch (IllegalArgumentException e) {
			System.out.println("GOOD JOB: precondition catches the error");
		} catch (IllegalStateException e) {
			System.out.println("GOOD JOB: Class invariant catches the error");
		} finally {
			System.out.format("Date is now: %s%n", date);
		}
	}

	// Utility class for testing only
	private static class DateValues {
		DateValues(int dd,  int mm, int yy) {
			this.d = dd;
			this.m = mm;
			this.y = yy;
		}

		int d;
		int m;
		int y;

		@Override
		public String toString() {
			return String.format("%d.%d.%d", d, m, y);
		}
	}

	private static DateValues [] validValues = {
		new DateValues(1, 1, 2025),
		new DateValues(31, 12, 2025),
		new DateValues(29, 2, 2000),
		new DateValues(31, 1, 2025),
		new DateValues(28, 2, 2025),
		new DateValues(31, 3, 2025),
		new DateValues(30, 4, 2025),
		new DateValues(31, 5, 2025),
		new DateValues(30, 6, 2025),
		new DateValues(31, 7, 2025),
		new DateValues(31, 8, 2025),
		new DateValues(30, 9, 2025),
		new DateValues(31, 10, 2025),
		new DateValues(30, 11, 2025),
		new DateValues(31, 12, 2025),
	};

	private static DateValues [] invalidValues = {
		new DateValues(32, 1, 1900),
		new DateValues(31, 11, 2025),
		new DateValues(3, 13, 2025),
		new DateValues(-1, 1, 2025),
		new DateValues(1, -1, 2025),
		new DateValues(29, 2, 1800),
		new DateValues(29, 2, 1900),
	};

}
