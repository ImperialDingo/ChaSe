/* File: ChaSeGUI.java
 * Authors: Joshua C Oglesby, Jean Michael Almonte
 * Instructor: Dr. Stacy Lukins
 * CS 321 - Object-Oriented Programming
 * ChaSe - A Chat Service
 * ChaSe seeks to aid in the instant communication between peers working within a closed network.
 */

package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import backend.TcpIpClient;
import backend.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;

public class ChaSeGUI{
	
	@FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() 
    {
    	assert userTextField != null : "fx:id=\"userTextField\" was not injected: check your FXML file 'Login - ChaSe.fxml'.";
        assert loginButton != null : "fx:id=\"loginButton\" was not injected: check your FXML file 'Login - ChaSe.fxml'.";
        
        // Instantiate Buttons and MenuItems
        loginButton = new Button();
        closeButton = new MenuItem();
        loginButton.setOnAction(event->logInButtonHandler(event));
        closeButton.setOnAction(event->closeHandler(event));
        
        //Setup backend
        chaseClient = new TcpIpClient();
        loggedUser = new User();
        
    }
	
	
	//Event Handlers
	
	/**
	 * Event handler for login button
	 * @param anEvent
	 */
	@FXML
	void logInButtonHandler(ActionEvent event) {
		
			//Get text from user 
			String usr = userTextField.getText();
    		//Check if empty username or we can check other conditions for name here
    		if (usr.isEmpty() || usr.contains(" ")) {
    			//userTextField.setText("Please enter a valid username!");
    			userTextField.clear();    			
    		}
    		else {
    			try {
    				//Close previous window
    				currentStage.hide();
    				 
    				//Create a new loader for the mainWinPane
    				loader = new FXMLLoader(getClass().getResource("MainChat-ChaSe.fxml"));
    								
    				Pane mainWinPane = (Pane)loader.load();
    				
    				//Set controller to mainChatControl
    				loader.setController(new mainChatControl());
    				
    				//Set window title
    				currentStage.setTitle("ChaSe - A Chat Service");	
    				
    				//Set username to backend
    				
    				loggedUser.setUsername(usr);
    				chaseClient.setCurrentUsername(usr);
    				
    				//Get its controller and set the stage to the currentStage. TODO -> Set username is not working
    				mainChatControl mainControl = (mainChatControl) loader.getController();
    				mainControl.setStage(currentStage);


    				
    				//Create a new Scene with the mainWinPane and show to screen
    				Scene mainScene = new Scene(mainWinPane);
    				currentStage.setScene(mainScene);
    				currentStage.centerOnScreen();
    				currentStage.show();
    	        }
    		    catch (Exception e) {
    		    	e.printStackTrace();
    		    }
    		}    
	}
	
	/**
	 * Handles close event
	 * @param event
	 */
	@FXML
	private void closeHandler(ActionEvent event) {Platform.exit();}
	
	
	//Accessor Methods
	
	/**
	 * 
	 * @param stage
	 * @param load
	 */
	public void setStage(Stage stage, FXMLLoader load) {
		this.currentStage = stage;
		this.loader = load;
	}
	
	//Variable declarations
	@FXML 
	private MenuItem closeButton;
	
    @FXML // fx:id="userTextField"
    private TextField userTextField;
    
	@FXML // fx:id="loginButton"
	private Button loginButton; 
	
	//Controller class variables
	private Stage currentStage;
	private FXMLLoader loader;
	
	protected static TcpIpClient chaseClient;
	protected static User loggedUser;
	
}