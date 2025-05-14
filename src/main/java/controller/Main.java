package controller;
import java.io.IOException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Person;

public class Main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    
    //data as an obervable list of Persons
    private ObservableList<Person> personData = FXCollections.observableArrayList();
    
    //constructor
    public Main() {
    	//sample data
		personData.add(new Person("Hans", "Muster"));
		personData.add(new Person("Ruth", "Mueller"));
		personData.add(new Person("Heinz", "Kurz"));
		personData.add(new Person("Cornelia", "Meier"));
		personData.add(new Person("Werner", "Meyer"));
		personData.add(new Person("Lydia", "Kunz"));
		personData.add(new Person("Anna", "Best"));
		personData.add(new Person("Stefan", "Meier"));
		personData.add(new Person("Martin", "Mueller"));
    }
    
    //return the data as an observable list of Persons
    public ObservableList<Person> getPersonData(){
    	return personData;
    }

    @Override
    public void start(Stage primaryStage) {
    	System.out.println("test");
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
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
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
}
















