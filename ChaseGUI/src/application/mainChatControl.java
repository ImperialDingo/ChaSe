/* File: mainChatControl.java
 * Authors: Joshua C Oglesby, Jean Michael Almonte
 * Instructor: Dr. Stacy Lukins
 * CS 321 - Object-Oriented Programming
 * ChaSe - A Chat Service
 * ChaSe seeks to aid in the instant communication between peers working within a closed network.
 */

package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class mainChatControl {
	
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert textInput != null : "fx:id=\"textInput\" was not injected: check your FXML file 'MainChat-ChaSe.fxml'.";
        assert sendButton != null : "fx:id=\"sendButton\" was not injected: check your FXML file 'MainChat-ChaSe.fxml'.";
        assert mainChatText != null : "fx:id=\"mainChatText\" was not injected: check your FXML file 'MainChat-ChaSe.fxml'.";      
    }
    
    @FXML // fx:id="textInput"
    private TextField textInput; 
    
    @FXML // fx:id="sendButton"
    private Button sendButton; 
    
    @FXML // fx:id="mainChatText"
    private TextArea mainChatText;
    
    @FXML
    void textInputHandler() {
    	textInput.setOnKeyPressed((event) -> {
    		if (event.getCode() == KeyCode.ENTER)
    			sendMessage(usrname);
    	});
    }
    
    @FXML
     void sendButtonHandler(ActionEvent anEvent) {
    	sendButton.setOnAction((event) -> {
    		sendMessage(usrname);
    	});
    }
    
    private void sendMessage(String name)
    {
    	msg = textInput.getText();
		
		if (msg.isEmpty() || msg.length() > 500) {
			//Don't send anything if it's empty or greater than 500 characters
			//TODO Don't send anything if it's all whitespace
		}
		else {
			mainChatText.appendText(name + ": " + msg + '\n');
    		textInput.clear();
		}
    }
    
	//Need mainStage reference to close out of system safely.
	private Stage mainStage;
	private String msg;
	private String usrname;

	public void setUsername(String username){
		this.usrname = username;
	}
	
	public void setStage(Stage stage) {
		this.mainStage = stage;
	}
}
