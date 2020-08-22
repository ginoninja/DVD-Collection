package dvd;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * The DVDCollection class which stores and allows utilization of the DVD's
 * 
 * @author Alexander Lee
 * @version 1.0
 */
public class DVDCollection {
	private ArrayList<DVD> collection;

	public DVDCollection() throws FileNotFoundException, ClassNotFoundException, IOException {
		collection = new ArrayList<>();
		try {
			fileRead();
		} catch (IOException e) {
			File file = new File("DVDCollection.txt");
		}
	}

	/**
	 * Reads the file if it has already been created before and places contents of
	 * file into array
	 * 
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public void fileRead() throws ClassNotFoundException, IOException {
		ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream("DVDCollection.txt"));
		collection = (ArrayList<DVD>) objectIn.readObject();
		objectIn.close();
	}

	/**
	 * Add a DVD to the collection
	 *
	 * @param String title
	 * @param String artistName
	 * @param int yearPurchased
	 * @param String category
	 * @return the DVD added
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public DVD addDVD(String title, String artistName, int yearPurchased, String category)
			throws FileNotFoundException, IOException {
		DVD dvd = new DVD(title, artistName, yearPurchased, category);
		collection.add(dvd);
		updateFile();
		return dvd;
	}

	/**
	 * Iterates through the collection so each DVD can be retrieved 1 by 1
	 * 
	 * @return void
	 */
	public ArrayList<DVD> displayDVDs() {
		ArrayList<DVD> iCollection = new ArrayList();
		for (int i = 0; i < collection.size(); i++) {
			iCollection.add(collection.get(i));
		}
		return iCollection;
	}

	/**
	 * Find a DVD if stored in the collection
	 * 
	 * @param dvd
	 * @return the DVD if in collection
	 */
	public DVD selectDVD(DVD dvd) {
		if (collection.contains(dvd)) {
			return dvd;
		}
		return null;
	}

	/**
	 * Finds all DVDs by the same artist/star
	 * 
	 * @param String artistName
	 * @return DVDs by the artist/star or null if there are none
	 */
	public ArrayList<DVD> selectDVDByArtist(String artistName) {
		ArrayList<DVD> dvdsByArtist = new ArrayList<>();
		Iterator<DVD> it = collection.iterator();
		while (it.hasNext()) {
			DVD dvd = it.next();
			if (dvd.getArtistName().equals(artistName)) {
				dvdsByArtist.add(dvd);
			}
		}
		return dvdsByArtist;
	}

	/**
	 * Removes a DVD from the collection
	 * 
	 * @param String title
	 * @return the DVD deleted, null if the DVD is not in the collection
	 */
	public DVD deleteDVD(DVD dvd) throws IOException {
		collection.remove(dvd);
		updateFile();
		return dvd;
	}

	// updates the file when a DVD has been deleted from the collection
	private void updateFile() throws FileNotFoundException, IOException {
		ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream("DVDCollection.txt"));
		objectOut.writeObject(collection);
		objectOut.close();
	}

	/**
	 * Getter for DVDCollection
	 * 
	 * @return ArrayList collection
	 */
	public ArrayList<DVD> getCollection() {
		return collection;
	}

	/**
	 * Setter for DVDCollection
	 * 
	 * @param ArrayListcollection
	 */
	public void setCollection(ArrayList<DVD> collection) {
		this.collection = collection;
	}

	// Convenience method for printing out collection
	public void printDVDs() {
		for (int i = 0; i < collection.size(); i++) {
			System.out.print(collection.get(i));
		}
	}

	// Convenience method for deleting file
	public void deleteFile() {
		File file = new File("DVDCollection.txt");
		file.delete();
	}

	// get DVD from title for CLI to make deleting a DVD easier for user
	public DVD getDVD(String title) {
		for (int i = 0; i < collection.size(); i++) {
			if (collection.get(i).getTitle().equals(title)) {
				return collection.get(i);
			}
		}
		return null;
	}

	public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, IOException {
		CLI cli = new CLI();
		System.out.println("Welcome to your DVD collection! "
				+ "Please enter a number that corresponds to one of the following instructions!");
		while (cli.select());
	}
}
