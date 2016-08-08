import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MiniKalendar {

	public static void main(String[] args) {
		// we make array list and as we turn on the program we put everything
		// that is recorded into the arraylist
		ArrayList<Notes> savedNotes = new ArrayList<>();
		try {
			savedNotes = readNotes();
		} catch (FileNotFoundException ex) {
			System.out.println("Your file might be missing");
		}
		// make array with method choose month
		int[] monthYear = chooseMonth();
		boolean loop = true;
		// infinite loop until user ends it with an exit
		while (loop) {
			// first element of an array
			int month = monthYear[0];
			// second element
			int year = monthYear[1];
			boolean inRange = false;
			int userChoice = 0;
			while (!inRange) {
				// offer a choice
				System.out.println("You have next choice : ");
				userChoice = takeInput("1: Show another month in calendar\n2: Make a note for specific month which is shown\n3: Look at the existing notes\n4: Exit\nEnter a number :");

				if (userChoice >= 1 && userChoice <= 4) {
					inRange = true;
				}
			}
			// at the end of every iteration we save notes
			monthYear = ifChoice(userChoice, savedNotes, monthYear);
			try {
				saveNotes(savedNotes);
			} catch (FileNotFoundException ex) {
				System.out.println("Your file might be missing");
			}
		}
	}

	// method in which we take from the user month and a year
	public static int[] chooseMonth() {
		boolean isGood = false;
		int month = 0;
		while (!isGood) {

			// Enter a number of a month that we want to see
			month = takeInput("Enter a number of a month from 1 to 12: ");
			// making sure that is in this range
			if (month >= 1 && month <= 12) {
				isGood = true;
			}
		}
		// Enter a year
		int year = takeInput("Enter a year : ");
		// print a month and return this two variables into an array
		printMonth(month, year);
		int[] monthYear = { month, year };
		// we are returning array because we need month and year and as this we
		// send it both
		return monthYear;
	}

	// taking input from the user and controling with try i catch
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

	// method to get first day in wanted month
	public static int firstDayOfTheMonth(int month, int year) {
		YearMonth yearMonth = YearMonth.of(year, month);
		// returning value of the first day for the year and month which user
		// enters
		return yearMonth.atDay(1).getDayOfWeek().getValue();
	}

	// Method that returns number of days in the month
	public static int noDaysInMonth(int month, int year) {
		int days = 0;
		// Defining number of days in a month first for leap year (month) then
		// for the rest month
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

	// Method that goes through array notes
	public static void saveNotes(ArrayList<Notes> list)
			throws FileNotFoundException {
		File file = new File("notes.txt");
		// records array of notes
		PrintWriter writer = new PrintWriter(file);
		for (Notes note : list) {
			// first records date(month and year)
			writer.print(note.getDate() + " ");
			// then text records
			writer.print(note.getText().trim());
			writer.println();
		}
		// closing writer
		writer.close();

	}

	public static ArrayList<Notes> readNotes() throws FileNotFoundException {
		File file = new File("notes.txt");
		Scanner reader = new Scanner(file);
		ArrayList<Notes> list = new ArrayList<>();
		while (reader.hasNextLine()) {
			String noteFromFile = reader.nextLine();
			Notes note = new Notes(noteFromFile.substring(0,
					noteFromFile.indexOf(" ")),
					noteFromFile.substring(noteFromFile.indexOf(" ")));
			list.add(note);
		}
		return list;

	}

	// method which goes through file line by line
	public static void makeNoteForCurrentMonth(int month, int year,
			ArrayList<Notes> list) {
		Scanner input = new Scanner(System.in);
		int noOfDays = noDaysInMonth(month, year);
		boolean isGood = false;
		int day = 0;
		while (!isGood) {
			day = takeInput("Enter the day for your note:");
			if (day >= 1 && day <= noOfDays) {
				isGood = true;
			} else {
				System.out
						.println("You entered the day that is not in range of 1 to "
								+ noOfDays);
			}
		}
		System.out.println("Enter your note text");
		String noteText = day + " - " + input.nextLine();
		String currentMonth = Integer.toString(month) + "."
				+ Integer.toString(year);
		// list of notes objects first is a date and the rest is text
		Notes note = new Notes(currentMonth, noteText);
		list.add(note);
	}

	// prints new notes for the current month and add to a list an object notes
	public static void printNotesForCurrentMonth(int month, int year,
			ArrayList<Notes> list) {
		int countNotes = 0;
		if (list.size() != 0) {
			// for current month we get date
			String currentMonth = Integer.toString(month) + "."
					+ Integer.toString(year);
			// for each loop to go through the list
			for (Notes note : list) {
				// if is object get date equal to the current month
				if (note.getDate().equals(currentMonth)) {
					countNotes++;
					// print note
					System.out.println(note);
				}
			}
		}
		if (countNotes == 0) {
			System.out.println("No notes for current month");
		}
	}

	// creating a method to print a month
	public static void printMonth(int month, int year) {
		// calling methods
		int daysInMonth = noDaysInMonth(month, year);
		int firstDay = firstDayOfTheMonth(month, year);
		String monthName = getMonthName(month);
		// printing top of the calander or the header
		System.out.println("\t\t" + monthName + " " + year);
		System.out
				.println("____________________________________________________\n");
		System.out.println("Sun\tMon\tTue\tWed\tThu\tFri\tSat\t\n");
		// we print the blank spaces because not every month starts with monday
		if (firstDay % 7 != 0) {
			for (int i = 0; i < firstDay; i++)
				System.out.print("\t");
		}
		// print the days in the month
		for (int i = 1; i <= daysInMonth; i++) {
			System.out.print(i + "\t");// space between the numbers
			// print 7 numbers then go to the next line
			if ((firstDay + i) % 7 == 0)
				System.out.println("\n");

		}
		System.out.println();
	}

	// method to get name of the month(1-12)we enter a number and get a
	// string(name)
	public static String getMonthName(int month) {
		String monthName = "";
		switch (month) {
		case 1:
			monthName = " January ";
			break;
		case 2:
			monthName = " February ";
			break;
		case 3:
			monthName = " March ";
			break;
		case 4:
			monthName = " April ";
			break;
		case 5:
			monthName = " May ";
			break;
		case 6:
			monthName = " June ";
			break;
		case 7:
			monthName = " July ";
			break;
		case 8:
			monthName = " August ";
			break;
		case 9:
			monthName = " September ";
			break;
		case 10:
			monthName = " October ";
			break;
		case 11:
			monthName = " November ";
			break;
		case 12:
			monthName = " December ";
			break;
		}
		return monthName;
	}

	// method which uses entry from the user-his choice to get something done
	public static int[] ifChoice(int number, ArrayList<Notes> list,
			int[] inputArray) {
		int[] array = inputArray;
		switch (number) {
		case 1:
			return array = chooseMonth();

		case 2:
			makeNoteForCurrentMonth(array[0], array[1], list);
			printMonth(array[0], array[1]);
			return array;
		case 3:
			printMonth(array[0], array[1]);
			System.out.println();
			printNotesForCurrentMonth(array[0], array[1], list);
			return array;
		case 4:
			System.exit(1);
		default:
			return array;
		}
	}
}