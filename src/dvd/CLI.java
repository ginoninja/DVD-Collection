package dvd;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The Command Line Interface for the program 
 * 
 * @author Alexander Lee
 * @version 1.0
 */
public class CLI {

	private DVDCollection dvdCol;

	public boolean select() throws FileNotFoundException, ClassNotFoundException, IOException {
		dvdCol = new DVDCollection();
		Scanner input = new Scanner(System.in);
		int option = -1;
		while (option < 0) {
			try {
				System.out.println("1. Add a new DVD\n2. Display all DVDs\n3. Select and display"
						+ " DVDs by artist or star\n4. Delete DVD\n5. Exit");
				option = input.nextInt();
				if (option == 1) {
					addCLIView();
				} else if (option == 2) {
					displayCLIView();
				} else if (option == 3) {
					displayDVDByArtistCLIView();
				} else if (option == 4) {
					displayDeleteDVDCLIView();
				} else if (option == 5) {
					input.close();
					return false;
				}
				if (option > 5 || option <= 0) {
					System.err.println("Invalid value, please enter valid int:");
				}

			} catch (InputMismatchException ex) {
				System.err.println("Invalid value, please enter valid int");
				input.next();
			}
		}
		return true;
	}

	public void addCLIView() throws FileNotFoundException, IOException {
		Scanner input = new Scanner(System.in);
		String title = "";
		while (!title.matches(".*\\w.*")) {
			try {
				System.out.println("Please enter a Title for the DVD:");
				title = input.nextLine();
				title = title.replaceAll("^\\s+|\\s+$", "");
				if (title.length() > 100) {
					String cutTitle = title.substring(0, 100);
					title = cutTitle;

				}
				if (!title.matches(".*\\w.*")) {
					System.err.println("Please enter a valid title");
				}
			} catch (InputMismatchException ex) {
			}
		}
		String artistName = "";
		while (!artistName.matches(".*\\w.*")) {
			try {
				System.out.println("Please enter an artist or star name:");
				artistName = input.nextLine();
				artistName = artistName.replaceAll("^\\s+|\\s+$", "");
				if (artistName.length() > 100) {
					String cutArtistName = artistName.substring(0, 100);
					artistName = cutArtistName;

				}
				if (!artistName.matches(".*\\w.*")) {
					System.err.println("Please enter a valid artist or star name");
				}
			} catch (InputMismatchException ex) {
			}
		}
		int yearPurchased = -1;
		int year = Calendar.getInstance().get(Calendar.YEAR);
		while (yearPurchased < 0 || yearPurchased < 1995 || yearPurchased > year) {
			try {
				System.out.println("Please enter a year purchased:");
				yearPurchased = input.nextInt();
				if (yearPurchased < 1995 || yearPurchased > year) {
					System.err.println("Please enter a valid year");
				}
				input.nextLine();

			} catch (InputMismatchException ex) {
				System.err.println("Please enter a valid year");
				input.next();
			}
		}
		String category = "";
		while (!category.matches(".*\\w.*")) {
			try {
				System.out.println("Please enter a category:");
				category = input.nextLine();
				category = category.replaceAll("^\\s+|\\s+$", "");
				if (category.length() > 100) {
					String cutCategory = category.substring(0, 100);
					cutCategory = cutCategory.replaceAll("^\\s+|\\s+$", "");

				}
				if (!category.matches(".*\\w.*")) {
					System.err.println("Please enter a valid category");
				}
			} catch (InputMismatchException ex) {
			}
		}
		dvdCol.addDVD(title, artistName, yearPurchased, category);
	}

	public void displayCLIView() {
		ArrayList<DVD> displayDVDs = dvdCol.displayDVDs();
		String formattedString = displayDVDs.toString().replace(",", "") // remove the commas
				.replace("[", "") // remove the right bracket
				.replace("]", "") // remove the left bracket
				.trim();
		System.out.print(" ");
		if (!displayDVDs.isEmpty()) {
			System.out.println(formattedString);
		} else {
			System.out.println("DVD collection is empty!");
		}
	}

	public void displayDVDByArtistCLIView() {
		Scanner input = new Scanner(System.in);
		String artistName = "";
		while (!artistName.matches(".*\\w.*")) {
			try {
				System.out.println("Please enter an artist or star name:");
				artistName = input.nextLine();
				artistName = artistName.replaceAll("^\\s+|\\s+$", "");
				if (artistName.length() > 100) {
					String cutArtistName = artistName.substring(0, 100);
					artistName = cutArtistName;

				}
				if (!artistName.matches(".*\\w.*")) {
					System.err.println("Please enter a valid artist or star name");
				}
			} catch (InputMismatchException ex) {
			}
		}
		ArrayList<DVD> displayDVDsByArtist = dvdCol.selectDVDByArtist(artistName);
		String formattedString = displayDVDsByArtist.toString().replace(",", "") // remove the commas
				.replace("[", "") // remove the right bracket
				.replace("]", "") // remove the left bracket
				.trim();
		System.out.print(" ");
		if (!displayDVDsByArtist.isEmpty()) {
			System.out.println(formattedString);
		} else {
			System.out.println("No DVDs by artist/star present in collection!");
		}
	}

	public void displayDeleteDVDCLIView() throws IOException {
		Scanner input = new Scanner(System.in);
		String title = "";
		while (!title.matches(".*\\w.*")) {
			try {
				System.out.println("Please enter a Title for the DVD to be deleted:");
				title = input.nextLine();
				title = title.replaceAll("^\\s+|\\s+$", "");
				if (title.length() > 100) {
					String cutTitle = title.substring(0, 100);
					title = cutTitle;

				}
				if (!title.matches(".*\\w.*")) {
					System.err.println("Please enter a valid title");
				}
			} catch (InputMismatchException ex) {
			}
		}

		DVD dvd = dvdCol.getDVD(title);
		if (dvdCol.deleteDVD(dvd) == null) {
			System.out.println("No such DVD in collection!");
		}
		dvdCol.deleteDVD(dvd);

	}
}
