/* File: Main.java
 * Authors: Joshua C Oglesby, Jean Michael Almonte
 * Instructor: Dr. Stacy Lukins
 * CS 321 - Object-Oriented Programming
 * ChaSe - A Chat Service
 * ChaSe seeks to aid in the instant communication between peers working within a closed network.
 */

package application;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.fxml.*;

public class Main extends Application{

	@Override 
	public void start(Stage primaryStage) throws Exception{
		try {
			//Set the title of the stage
			primaryStage.setTitle("ChaSe - A Chat Service");
			
			//Create a new FXMLLoader to get .fxml files
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Login-ChaSe.fxml"));
			//FXMLLoader loader = new FXMLLoader(getClass().getResource("Login-ChaSe.fxml"));
			
			//Create a pane for the stage
			Pane loginPane = (Pane)loader.load();
			//Parent loginPane = (Parent)loader.load();
			
			//Create a reference to the loader and primaryStage for use in controller classes
			ChaSeGUI guiControl = (ChaSeGUI) loader.getController();
			guiControl.setStage(primaryStage, loader);
			
			// Create a new Scene for the log in screen and display out to screen
			Scene loginScreen = new Scene(loginPane);
			primaryStage.setScene(loginScreen);
			primaryStage.centerOnScreen();
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	    
	public static void main(String[] args) {
		// Sets up JavaFx in Application. Then Application calls the Start() method which Main overrides.
		launch(args);  
	}
}
