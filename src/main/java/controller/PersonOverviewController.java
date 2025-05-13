package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import mainPackage.Main;
import model.Person;

public class PersonOverviewController {

	@FXML
	private TableView<Person> personTable;
	@FXML
	private TableColumn<Person, String> firstNameColumn;
	@FXML
	private TableColumn<Person, String> lastNameColumn;
	
	@FXML
	private Label firstNameLabel;
	@FXML
	private Label lastNameLabel;
	@FXML
	private Label streetLabel;
	@FXML
	private Label postalCodeLabel;
	@FXML
	private Label cityLabel;
	@FXML
	private Label birthdayLabel;
	
	//reference to the main application
	private Main main;
	
	//constructor (is called before the intiliaze() method
	public PersonOverviewController() {
		
	}
	
	//initialize the controller class. is automatically called after the fxml file has been loaded
	@FXML
	private void initialize() {
		//initialize the person table with two columns
		firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
		lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
	}
	
	public void setMainApp(Main main) {
		this.main = main;
		
		//add observable list data to the table
		personTable.setItems(main.getPersonData());
	}
}
