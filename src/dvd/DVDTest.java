package dvd;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DVDTest {

	DVDCollection dvdCol;

	/**
	 * test if a DVD can be added to the DVD collection
	 * 
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 */
	@Test
	@Order(1)
	public void testAdd() throws FileNotFoundException, IOException, ClassNotFoundException {
		dvdCol = new DVDCollection();
		DVD dvd = dvdCol.addDVD("Toy Story", "Pixar", 2020, "Kids");
		DVD dvd1 = dvdCol.addDVD("Glory", "Edward Zwick", 2017, "Historical");
		assertEquals(true, dvdCol.getCollection().contains(dvd));
		assertNotSame(false, dvdCol.getCollection().contains(dvd1));
	}

	/**
	 * test if the contents of the collection can be read once saved
	 * 
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	@Test
	@Order(2)
	public void testRead() throws FileNotFoundException, ClassNotFoundException, IOException {
		dvdCol = new DVDCollection();
		DVD dvd = new DVD("Toy Story", "Pixar", 2020, "Kids");
		assertEquals(true, dvdCol.getCollection().contains(dvd));
		dvdCol.printDVDs();
		System.out.println();
	}

	/**
	 * Adds a new DVD to the collection once the file has been written to multiple
	 * times
	 * 
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	@Test
	@Order(3)
	public void testAddAfterClose() throws ClassNotFoundException, IOException {
		dvdCol = new DVDCollection();
		DVD dvd = dvdCol.addDVD("Fight Club", "David Fincher", 2018, "Thriller/Action");
		assertEquals(true, dvdCol.getCollection().contains(dvd));
	}

	/**
	 * Reads the file once it has been written to multiple times
	 * 
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	@Test
	@Order(4)
	public void testReadAfterClose() throws ClassNotFoundException, IOException {
		dvdCol = new DVDCollection();
		DVD dvd = new DVD("Fight Club", "David Fincher", 2018, "Thriller/Action");
		assertEquals(true, dvdCol.getCollection().contains(dvd));
		dvdCol.printDVDs();
		System.out.println();
	}

	/**
	 * Removes a DVD from the collection
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@Test
	@Order(5)
	public void testDelete() throws IOException, ClassNotFoundException {
		dvdCol = new DVDCollection();
		DVD dvd = new DVD("Glory", "Edward Zwick", 2017, "Historical");
		dvdCol.deleteDVD(dvd);
		assertEquals(false, dvdCol.getCollection().contains(dvd));
		dvdCol.printDVDs();
		System.out.println();

	}

	/**
	 * Checks that the correct DVD is returned from the collection when searched by
	 * name
	 * 
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	@Test
	@Order(6)
	public void testSelect() throws FileNotFoundException, ClassNotFoundException, IOException {
		dvdCol = new DVDCollection();
		DVD dvd = new DVD("Fight Club", "David Fincher", 2018, "Thriller/Action");
		DVD dvd1 = new DVD("Glory", "Edward Zwick", 2017, "Historical");
		DVD dvd2 = new DVD("Finding Nemo", "Pixar", 2008, "Kids");
		assertEquals(dvd, dvdCol.selectDVD(dvd));
		assertNotSame(dvd1, dvdCol.selectDVD(dvd));
		assertEquals(null, dvdCol.selectDVD(dvd2));
	}

	/**
	 * Checks that the sub-collection of DVDs by the same artist returned is correct 
	 * 
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	@Test
	@Order(7)
	public void testSelectByArtist() throws FileNotFoundException, ClassNotFoundException, IOException {
		dvdCol = new DVDCollection();
		DVD dvd = dvdCol.addDVD("Coco", "Pixar", 2017, "Kids");
		DVD dvd1 = new DVD("Toy Story", "Pixar", 2020, "Kids");
		assertEquals(Arrays.asList(dvd1, dvd), dvdCol.selectDVDByArtist("Pixar"));
		// delete file so test can be run again
		dvdCol.deleteFile();
	}
}
