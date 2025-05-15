package controller;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;

//controller for the root layout. provides basics like menu bar and space for other components

public class RootLayoutController {
	
	//reference to the main application
	private Main main;
	
	//called by the main app to give a reference back to itself
	public void setMainApp(Main main) {
		this.main = main;
	}
	
	//creates an empty address book
	@FXML
	private void handleNew() {
		main.getPersonData().clear();
		main.setPersonFilePath(null);
	}
	
	//opens a FileChooser to let the User select an address book to load
	@FXML
	private void handleOpen() {
		FileChooser fileChooser = new FileChooser();
		
		//set extension filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);
		
		//show open file dialog
		File file = fileChooser.showOpenDialog(main.getPrimaryStage());
		
		if(file != null) {
			main.loadPersonDataFromFile(file);
		}
	}
	
	//saves the file to the person file that is currently open; if there is no open file, the "Save as" dialog is shown
	@FXML
	private void handleSave() {
		File personFile = main.getPersonFilePath();
		if(personFile != null) {
			main.savePersonDataToFile(personFile);
		}else {
			handleSaveAs();
		}
	}
	
	//opens a filechooser to let the user select a file to save to
	@FXML
	private void handleSaveAs() {
		FileChooser fileChooser = new FileChooser();
		
		//set extension filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);
		
		//show save file dialog
		File file = fileChooser.showSaveDialog(main.getPrimaryStage());
		
		if(file != null) {
			//make sure it has the correct extension
			if(!file.getPath().endsWith(".xml")) {
				file = new File(file.getPath() + ".xml");
			}
			main.savePersonDataToFile(file);
		}
	}
	
	//opens an about dialog
	@FXML
	private void handleAbout() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("AddressApp");
		alert.setHeaderText("About");
		alert.setContentText("Who needs address apps anyways");
		
		alert.showAndWait();
	}
	
	//closes the application
	@FXML
	private void handleExit() {
		System.exit(0);
	}
}