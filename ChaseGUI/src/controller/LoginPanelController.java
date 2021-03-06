package controller;


import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * LoginPanelController object
 * @authors Josh Oglesby & Jean Michael Almonte
 * <P> This object handles all things to do with the login GUI.
 *
 */

public class LoginPanelController extends SubController {

	/**
	 * Override function showPane to instantiate the login GUI
	 */
	@Override
	public void showPane() {
		
		
	  	GridPane grid = new GridPane();
	  	grid.setAlignment(Pos.CENTER);
	  	grid.setHgap(10);
	  	grid.setVgap(10);
	  	grid.setPadding(new Insets(25, 25, 25, 25));

	  	Scene scene = new Scene(grid, 400, 375);

	  	Text scenetitle = new Text("Welcome");
	  	scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
	  	grid.add(scenetitle, 0, 0, 2, 1);

	  	Label userName = new Label("User Name:");
	  	grid.add(userName, 0, 1);

	  	userTextField = new TextField();
	  	grid.add(userTextField, 1, 1);

	  	Label pw = new Label("Password:");
	  	grid.add(pw, 0, 2);

	  	PasswordField pwBox = new PasswordField();
	  	grid.add(pwBox, 1, 2);
	  	
	  	Button loginButton = new Button("Login");
	  	loginButton.setOnAction(event->logInButtonHandler(event));
	  	
	  	HBox hbBtn = new HBox(10);
	  	hbBtn.setAlignment(Pos.TOP_RIGHT);
	  	hbBtn.getChildren().add(loginButton);
	  	grid.add(hbBtn, 1, 4);	 

		Image icon = new Image(getClass().getResourceAsStream("t.png"));
		myController.getStage().getIcons().add(icon);
        myController.getStage().setScene(scene);
    	myController.getStage().show();
    	myController.getStage().setTitle("ChaSe - A Chat Service");

		
	}
	
	
	//Event Handlers
	
	/**
	 * Event handler for login button
	 * @param anEvent login button event
	 */
	void logInButtonHandler(ActionEvent event) 
	{		
			//Get text from user 
			String username = userTextField.getText();
    		//Check if empty username or we can check other conditions for name here
    		if (username.isEmpty() || username.contains(" ")) {
    			//userTextField.setText("Please enter a valid username!");
    			userTextField.clear();    			
    		}
    		else 
    		{
    			myController.setUsername(username);
    			myController.logToServer();
    			myController.getStage().hide();
    			myController.setPane("main");    			
	        }
    }	

	//Variable declarations
    private TextField userTextField;



}
