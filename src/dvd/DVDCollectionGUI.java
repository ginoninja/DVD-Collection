package dvd;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

/**
 * The GUI for the program 
 * 
 * @author Alexander Lee
 * @version 1.0
 */
public class DVDCollectionGUI extends Application {

	private DVDCollection dvdCol;
	private ObservableList observableList;
	private String[] tabNames = { "DVDs", "Add DVD", "Search for DVDs by artist/star" };
	private TabPane tabPane = new TabPane();
	private int yearPurchased;
	private TableView table = new TableView();

	@Override
	public void init() throws Exception {
		this.dvdCol = new DVDCollection();
		observableList = FXCollections.observableList(dvdCol.getCollection());
		super.init();
	}

	private ObservableList getObservableList() {
		return observableList;
	}

	private BorderPane createListPane() {
		BorderPane borderPane = new BorderPane();
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		TableColumn<DVD, String> titleCol = new TableColumn<>("Title");
		TableColumn<DVD, String> artistCol = new TableColumn<>("Artist/star name");
		TableColumn<DVD, String> yearCol = new TableColumn<>("YearPurchased");
		TableColumn<DVD, String> categoryCol = new TableColumn<>("Category");
		table.getColumns().addAll(titleCol, artistCol, yearCol, categoryCol);
		table.setItems(getObservableList());
		titleCol.setCellValueFactory(new PropertyValueFactory<DVD, String>("title"));
		artistCol.setCellValueFactory(new PropertyValueFactory<DVD, String>("artistName"));
		yearCol.setCellValueFactory(new PropertyValueFactory<DVD, String>("yearPurchased"));
		categoryCol.setCellValueFactory(new PropertyValueFactory<DVD, String>("category"));
		borderPane.setCenter(table);
		Button deleteButton = new Button("Delete");
		deleteButton.setOnAction(e -> {
			DVD dvd = (DVD) table.getSelectionModel().getSelectedItem();
			if (dvdCol.getCollection().contains(dvd)) {
				Alert alert = new Alert(AlertType.CONFIRMATION, "Delete " + dvd.getTitle() + " from collection?",
						ButtonType.YES, ButtonType.NO);
				alert.showAndWait();
				if (alert.getResult() == ButtonType.YES) {
					try {
						dvdCol.deleteDVD(dvd);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					table.refresh();
				}
			}
		});
		borderPane.setBottom(deleteButton);
		return borderPane;
	}

	private BorderPane createEntryPane() {
		BorderPane entryPane = new BorderPane();
		TilePane fieldPane = new TilePane();
		TextField entryTitle = new TextField();
		TextField entryArtistName = new TextField();
		TextField entryYearPurchased = new TextField();
		TextField entryCategory = new TextField();
		fieldPane.setPrefColumns(2);
		fieldPane.getChildren().addAll(new Label("Title:"), entryTitle, new Label("Artist/star name:"), entryArtistName,
				new Label("Year Purchased:"), entryYearPurchased, new Label("Category"), entryCategory);
		entryPane.setTop(fieldPane);
		Button button = new Button("Add DVD");
		entryPane.setCenter(button);
		button.setOnAction(e -> {
			try {
				String title = entryTitle.getText().trim();
				String artistName = entryArtistName.getText().trim();
				isIntYear(entryYearPurchased, entryYearPurchased.getText());
				String category = entryCategory.getText().trim();
				if (title.equals("") || title.length() > 70) {
					Alert dialog = new Alert(AlertType.ERROR);
					dialog.setTitle("Invalid title");
					dialog.setHeaderText("Please enter a valid title");
					dialog.showAndWait();
				} else if (artistName.equals("") || artistName.length() > 70) {
					Alert dialog = new Alert(AlertType.ERROR);
					dialog.setTitle("Invalid artist/star name");
					dialog.setHeaderText("Please enter a valid artist/star name");
					dialog.showAndWait();
				} else if (!isIntYear(entryYearPurchased, entryYearPurchased.getText())) {
					Alert dialog = new Alert(AlertType.ERROR);
					dialog.setTitle("Invalid year purchased");
					dialog.setHeaderText("Please enter a valid year purchased");
					dialog.showAndWait();
				} else if (category.equals("") || category.length() > 70) {
					Alert dialog = new Alert(AlertType.ERROR);
					dialog.setTitle("Invalid category");
					dialog.setHeaderText("Please enter a valid category");
					dialog.showAndWait();
				} else {
					DVD dvd = dvdCol.addDVD(title, artistName, yearPurchased, category);
					refreshTable();
					// clear text fields
					for (Node node : fieldPane.getChildren()) {
						if (node instanceof TextField) {
							((TextField) node).setText("");
						}
					}
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		return entryPane;

	}

	private boolean isIntYear(TextField input, String message) {
		try {
			int number = Integer.parseInt(input.getText());
			int year = Calendar.getInstance().get(Calendar.YEAR);
			if (number >= 1995 && number <= year) {
				yearPurchased = Integer.parseInt(input.getText());
				return true;
			}
			return false;
		} catch (NumberFormatException e) {
		}
		return false;
	}

	// work around for table.refresh() issue
	void refreshTable() {
		final List<DVD> items = table.getItems();
		if (items == null || items.size() == 0)
			return;
		final DVD item = (DVD) table.getItems().get(0);
		items.remove(0);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				items.add(0, item);
			}
		});
	}

	private BorderPane createSearchPane() {
		BorderPane searchPane = new BorderPane();
		BorderPane top = new BorderPane();
		searchPane.setTop(top);
		TableView localTable = new TableView();
		TilePane fieldPane = new TilePane();
		TextField searchField = new TextField();
		fieldPane.setPrefColumns(2);
		fieldPane.getChildren().addAll(new Label("Enter artist/star name"), searchField);
		top.setLeft(fieldPane);
		Button searchButton = new Button("Search");
		top.setCenter(searchButton);
		localTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		TableColumn<DVD, String> titleCol = new TableColumn<>("Title");
		TableColumn<DVD, String> artistCol = new TableColumn<>("Artist/star name");
		TableColumn<DVD, String> yearCol = new TableColumn<>("YearPurchased");
		TableColumn<DVD, String> categoryCol = new TableColumn<>("Category");
		localTable.getColumns().addAll(titleCol, artistCol, yearCol, categoryCol);
		searchPane.setCenter(localTable);
		searchButton.setOnAction(e -> {
			String search = searchField.getText().trim();
			if (!dvdCol.selectDVDByArtist(search).isEmpty()) {
				localTable.setItems(FXCollections.observableList(dvdCol.selectDVDByArtist(search)));
				titleCol.setCellValueFactory(new PropertyValueFactory<DVD, String>("title"));
				artistCol.setCellValueFactory(new PropertyValueFactory<DVD, String>("artistName"));
				yearCol.setCellValueFactory(new PropertyValueFactory<DVD, String>("yearPurchased"));
				categoryCol.setCellValueFactory(new PropertyValueFactory<DVD, String>("category"));
			} else {
				Alert dialog = new Alert(AlertType.ERROR);
				dialog.setTitle("No DVDs");
				dialog.setHeaderText("No DVDs by artist/star in collection");
				dialog.showAndWait();
			}
		});

		return searchPane;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane root = new BorderPane();
		for (String s : tabNames) {
			Tab t = new Tab(s);
			t.setClosable(false);
			tabPane.getTabs().add(t);
			switch (s) {
			case "DVDs":
				t.setContent(createListPane());
				break;
			case "Add DVD":
				t.setContent(createEntryPane());
				break;
			case "Search for DVDs by artist/star":
				t.setContent(createSearchPane());
				break;
			default:
				throw new Exception("Unknown tab " + s);
			}
		}
		root.setCenter(tabPane);
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("DVD Collection");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
