package dvd;

import java.io.Serializable;

/**
 * The DVD class which represents an individual DVD
 * 
 * @author Alexander Lee
 * @version 1.0
 */
public class DVD implements Serializable {
	private String title;
	private String artistName;
	private int yearPurchased;
	private String category;

	public DVD(String title, String artistName, int yearPurchased, String category) {
		this.title = title;
		this.artistName = artistName;
		this.yearPurchased = yearPurchased;
		this.category = category;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArtistName() {
		return artistName;
	}

	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}

	public int getYearPurchased() {
		return yearPurchased;
	}

	public void setYearPurchased(int yearPurchased) {
		this.yearPurchased = yearPurchased;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "DVD Title: " + title + ", Artist name: " + artistName + ", Year purchased: " + yearPurchased
				+ ", Category: " + category + "\n";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DVD other = (DVD) obj;
		if (artistName == null) {
			if (other.artistName != null)
				return false;
		} else if (!artistName.equals(other.artistName))
			return false;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (yearPurchased != other.yearPurchased)
			return false;
		return true;
	}

}
