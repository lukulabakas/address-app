package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Person;
import util.DateUtil;

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
		
		//clear person details
		showPersonDetails(null);
		
		//listen for selection changes and show the person details when changed
		//
		personTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showPersonDetails(newValue));
	}
	
	public void setMainApp(Main main) {
		this.main = main;
		
		//add observable list data to the table
		personTable.setItems(main.getPersonData());
	}
	
	//shows the details about the person
	//if the person is null > all text is cleared
	private void showPersonDetails(Person person) {
		if(person != null) {
			//fill the labels with info about the chosen person
			firstNameLabel.setText(person.getFirstName());
			lastNameLabel.setText(person.getLastName());
			streetLabel.setText(person.getStreet());
			postalCodeLabel.setText(Integer.toString(person.getPostalCode()));
			cityLabel.setText(person.getCity());
			birthdayLabel.setText(DateUtil.format(person.getBirthday()));
		}else {
			//clear all text if the person is null
			firstNameLabel.setText("");
			lastNameLabel.setText("");
			streetLabel.setText("");
			postalCodeLabel.setText("");
			cityLabel.setText("");
			birthdayLabel.setText("");
		}
	}
	
	//delete button funcionality
	@FXML
	private void handleDeletePerson() {
		int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
		if(selectedIndex >= 0) {
			personTable.getItems().remove(selectedIndex);
		} else {
			//nothing is selected > index is -1
			//without this handling deleting without selection would cause an ArrayIndexOutOfBoundsException
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(main.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Person Selected");
			alert.setContentText("Please select a Person in the table");
			alert.showAndWait();
		}

	}
	
	//called when new button is clicked
	@FXML
	private void handleNewPerson() {
		Person tempPerson = new Person();
		boolean okClicked =  main.showPersonEditDialog(tempPerson);
		if(okClicked) {
			main.getPersonData().add(tempPerson);
		}
	}
	
	//called when edit button is clicked; opens dialog to edit the details of a person
	@FXML
	private void handleEditPerson() {
		Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
		if(selectedPerson != null) {
			boolean okClicked = main.showPersonEditDialog(selectedPerson);
			if(okClicked) {
				showPersonDetails(selectedPerson);
			}
		}else {
			//nothing is selected 
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(main.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Person selected");
			alert.setContentText("Please select a Person in the table");
			
			alert.showAndWait();
		}
	}
}












