package controller;
import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Person;
import model.PersonListWrapper;

public class Main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    
    //data as an obervable list of Persons
    private ObservableList<Person> personData = FXCollections.observableArrayList();
    
    //constructor
    public Main() {
    	//sample data
    /*
		personData.add(new Person("Hans", "Muster"));
		personData.add(new Person("Ruth", "Mueller"));
		personData.add(new Person("Heinz", "Kurz"));
		personData.add(new Person("Cornelia", "Meier"));
		personData.add(new Person("Werner", "Meyer"));
		personData.add(new Person("Lydia", "Kunz"));
		personData.add(new Person("Anna", "Best"));
		personData.add(new Person("Stefan", "Meier"));
		personData.add(new Person("Martin", "Mueller"));
	*/
    }
    
    //return the data as an observable list of Persons
    public ObservableList<Person> getPersonData(){
    	return personData;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Address-App");
        
        //set the application icon
        this.primaryStage.getIcons().add(new Image("file:src/main/resources/images/86957_address_book_icon.png"));

        initRootLayout();

        showPersonOverview();
    }
    

     //Initializes the root layout.
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            
            //give the controller access to the main app
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);
            
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //try to load last opened person file
        File file = getPersonFilePath();
        if(file != null) {
        	loadPersonDataFromFile(file);
        }
    }

 
     //Shows the person overview inside the root layout.
    public void showPersonOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/view/PersonOverview.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();
            
            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);
            
            //give the controller acccess to the main app
            PersonOverviewController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //return the main stage
	public Stage getPrimaryStage() {
		return primaryStage;
	}

    public static void main(String[] args) {
        launch(args);
    }
    
    //opens a dialog to edit the details of a person; if the user clicks okay, changes are made and true is returned
    public boolean showPersonEditDialog(Person person) {
    	try {
    		//load the fxml file and crate a new stage for dialog
    		FXMLLoader loader = new FXMLLoader();
    		loader.setLocation(Main.class.getResource("/view/PersonEditDialog.fxml"));
    		AnchorPane page = (AnchorPane) loader.load();
    		
    		//create the dialog stage
    		Stage dialogStage = new Stage();
    		dialogStage.setTitle("Edit Person");
    		dialogStage.initModality(Modality.WINDOW_MODAL);
    		dialogStage.initOwner(primaryStage);
    		Scene scene = new Scene(page);
    		dialogStage.setScene(scene);
    		
    		//set the person into the controller
    		PersonEditDialogController controller = loader.getController();
    		controller.setDialogStage(dialogStage);
    		controller.setPerson(person);
    		
    		//show the dialog and wait until the user closes it
    		dialogStage.showAndWait(); 		
    		
    		return controller.isOkClicked();
    	}catch (IOException e) {
    		e.printStackTrace();
    		return false;
    	}
    }
    
    //returns the person file preference as a file
    public File getPersonFilePath() {
    	Preferences prefs = Preferences.userNodeForPackage(Main.class);
    	String filePath = prefs.get("filePath", null);
    	if(filePath != null) {
    		return new File(filePath);
    	}else {
    		return null;
    	}
    }
    
    //set the file path of the currently loaded file
    public void setPersonFilePath(File file) {
    	Preferences prefs = Preferences.userNodeForPackage(Main.class);
    	if(file != null) {
    		prefs.put("filePath", file.getPath());
    		
    		//update stage title
    		primaryStage.setTitle("AddressApp - " + file.getName());
    	}else {
    		prefs.remove("filePath");
    		
    		//Update stage title
    		primaryStage.setTitle("AddressApp");
    	}
    }
    
    //loads person data from a specified file; current person data will be replaced
    public void loadPersonDataFromFile(File file) {
    	try {
    		JAXBContext context = JAXBContext
    				.newInstance(PersonListWrapper.class);
    		Unmarshaller um = context.createUnmarshaller();
    		
    		//reading xml from file and unmarshalling
    		PersonListWrapper wrapper = (PersonListWrapper) um.unmarshal(file);
    		
    		personData.clear();
    		personData.addAll(wrapper.getPersons());
    		
    		//save the file path to the registry
    		setPersonFilePath(file);
    	}catch (Exception e) {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Error");
    		alert.setHeaderText("Could not load data");
    		alert.setContentText("Could not load data from file:\n" + file.getPath());
    		
    		alert.showAndWait();
    	}
    }
    
    //Saves the current person data to a specified file
    public void savePersonDataToFile(File file) {
    	try {
    		JAXBContext context = JAXBContext
    				.newInstance(PersonListWrapper.class);
    		Marshaller m = context.createMarshaller();
    		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
    		
    		//wrapping our person data
    		PersonListWrapper wrapper = new PersonListWrapper();
    		wrapper.setPersons(personData);
    		
    		//marshalling and saving XML to the file
    		m.marshal(wrapper, file);
    		
    		//save the file path to the registry
    		setPersonFilePath(file);    		
    	}catch (Exception e) {
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Error");
    		alert.setHeaderText("Could not save data");
    		alert.setContentText("Could not save data to file:\n" + file.getParent());
    		e.printStackTrace();
    		
    		alert.showAndWait();
    	}
    }
}
















