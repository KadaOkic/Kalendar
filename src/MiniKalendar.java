import java.time.YearMonth;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MiniKalendar {

	public static void main(String[] args) {

		boolean isGood = true;
		int month = 0;

		while (isGood) {
			isGood = false;
			// Enter a number of a month that we want to see
			month = takeInput("Enter a number of a month from 1 to 12: ");
			if (month <= 1 || month >= 12) {
				isGood = true;
			}
		}
		// Enter a year
		int year = takeInput("Enter a year : ");

		int nuOfDays = noDaysInMonth(month, year);
		int firstDay = firstDayOfTheMonth(month, year);
		printMont(firstDay - 1, nuOfDays);

	}

	public static int takeInput(String message) {
		Scanner input = new Scanner(System.in);
		System.out.println(message);
		boolean isGood = false;
		int userInput = 0;
		while (!isGood) {
			try {
				isGood = true;
				userInput = input.nextInt();
			} catch (InputMismatchException ex) {
				System.out.println("Try again. ("
						+ "Invalid input: Enter an integer: ");
				// final statements
			} finally {
				input.nextLine();
			}
		}
		return userInput;
	}

	public static void printMont(int firstDay, int daysInMonth) {
		System.out.println("Mon\t Tue\t Wed\t Thu\t Fri\t San\t Sun ");

		if (firstDay % 7 != 0) {
			for (int i = 0; i < firstDay; i++)
				System.out.print("\t");
		}
		for (int i = 1; i <= daysInMonth; i++) {
			System.out.print(i + "\t");
			if ((firstDay + i) % 7 == 0)
				System.out.println();

		}

	}

	public static int firstDayOfTheMonth(int month, int year) {
		YearMonth yearMonth = YearMonth.of(year, month);
		return yearMonth.atDay(1).getDayOfWeek().getValue();
	}

	// Method that returns number of days in the month
	public static int noDaysInMonth(int month, int year) {
		int days = 0;
		// Defining number of days
		if (((year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0)))
				&& (month == 2))
			days = 29;
		else
			switch (month) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				days = 31;
				break;
			case 2:
				days = 28;
				break;
			case 4:
			case 6:
			case 9:
			case 11:
				days = 30;
				break;
			}
		return days;

	}

}
