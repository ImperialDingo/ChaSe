/* File: mainChatControl.java
 * Authors: Joshua C Oglesby, Jean Michael Almonte
 * Instructor: Dr. Stacy Lukins
 * CS 321 - Object-Oriented Programming
 * ChaSe - A Chat Service
 * ChaSe seeks to aid in the instant communication between peers working within a closed network.
 */

package application;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class mainChatControl extends ChaSeGUI{
    
    @FXML // This method is called by the FXMLLoader when initialization is complete
    @Override
    void initialize() {
    	assert backgroundColor != null : "fx:id=\"backgroundColor\" was not injected: check your FXML file 'MainChat-ChaSe.fxml'.";
        assert textInput != null : "fx:id=\"textInput\" was not injected: check your FXML file 'MainChat-ChaSe.fxml'.";
        assert closeButton != null : "fx:id=\"closeButton\" was not injected: check your FXML file 'MainChat-ChaSe.fxml'.";
        assert mainChatText != null : "fx:id=\"mainChatText\" was not injected: check your FXML file 'MainChat-ChaSe.fxml'.";
        assert aboutButton != null : "fx:id=\"aboutButton\" was not injected: check your FXML file 'MainChat-ChaSe.fxml'.";
        assert sendButton != null : "fx:id=\"sendButton\" was not injected: check your FXML file 'MainChat-ChaSe.fxml'."; 

        backgroundColor = new MenuItem();
        aboutButton = new MenuItem();
        //sendButton = new Button();
                
        backgroundColor.setOnAction(event -> setBackGroundColorHandler(event));
        aboutButton.setOnAction(event->aboutButtonHandler(event));
        //sendButton.setOnAction(event-> sendButtonHandler(event));
        
    	mainChatText.setWrapText(true);
    }
    
    @FXML
    private void textInputHandler() {
        textInput.setOnKeyPressed((event) -> {
            if (event.getCode() == KeyCode.ENTER)
                sendMessage();
        });
    }
    
    @FXML
    private void sendButtonHandler() {
    	sendButton.setOnMouseClicked((event) -> {
            sendMessage();
        });
    }
    
    @FXML
    private void aboutButtonHandler(ActionEvent event) {
            //Initialize the about screen
            try {
            	//aboutStage = new Stage();
                aboutStage.setTitle("About ChaSe 2016");
                
                FXMLLoader loader = new FXMLLoader(getClass().getResource("about.fxml"));
                Pane aboutPane = (Pane)loader.load();
                
                Scene scene = new Scene(aboutPane);
                aboutStage.getIcons().add(icon);
                aboutStage.setScene(scene);
                aboutStage.centerOnScreen();
                aboutStage.setResizable(false);
                aboutStage.sizeToScene();
                aboutStage.show();
                
            }
            catch (Exception e) {
                e.printStackTrace();
            }
  
    }
    
    @FXML
    void PreferencesHandler() {
        connectMenu.setOnAction((event) -> {
            try {
                prefStage.setTitle("ChaSe - Preferences");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Preferences.fxml"));
                Pane prefPane = (Pane)loader.load();
                Scene scene = new Scene(prefPane);
                prefStage.getIcons().add(icon);
                prefStage.setResizable(false);
                prefStage.setScene(scene);
                prefStage.sizeToScene();
                prefStage.centerOnScreen();
                prefStage.show();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    @FXML
    void connectToUserHandler() {
    	connectButton.setOnMouseClicked((event) -> {
    		String usr = usernameField.getText();
			  		
    		//Check if empty username or we can check other conditions for name here
    		if (usr.isEmpty() || usr.contains(" ")) {
    			//userTextField.setText("Please enter a valid username!");
    			usernameField.clear();
    			ChaSeConsole.appendText("Please enter a valid username with no spaces\n");
    		}
    		else {
    			chaseClient.setDestinationUsername(usr);
    			System.out.println(chaseClient.getDestinationUsername());
    			ChaSeConsole.appendText("Connecting to " + usr + "\n");
    			try {
					chaseClient.startClient();
					chaseClient.serverHandshake();
					new Thread( ()-> receiveMessages()).start();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("Exception occured while attempting to start TcpIpClient");
				}
    		}
    	});
    }
    
    private void receiveMessages()
    {
    	
    	
    	while(true){
    		String message = chaseClient.receiveMessages();
    		System.out.println("WORD: " + message);
	    	if(!message.equals(null))
	    	{
	    		mainChatText.appendText(message);   
	    	}
    	}
    }
    @FXML
    void setBackGroundColorHandler(ActionEvent event) {

    		String color = "#" + Integer.toHexString(colorPicker.getValue().hashCode()); 
    		setTextAreaColor(color);

    }
    
    // TODO once user selects save, begin connecting to user. Print out server detail to screen
    @FXML
    void saveButtonHandler() {
    	saveButton.setOnMouseClicked((event) -> {
    		Node source = (Node) event.getSource();
    		Stage stage = (Stage) source.getScene().getWindow();
    		stage.close();
    	});
    }
    
    @FXML
    void okAboutButtonHandler() {
        okAboutButton.setOnMouseClicked((event) -> {
            Stage stage = (Stage) okAboutButton.getScene().getWindow();
            stage.close();
        });
    }
    
    @FXML
    void closeHandler() {
        closeButton.setOnAction((event) -> {
        	Platform.exit();
        });
    }
    
    private void sendMessage()
    {
        msg = textInput.getText();
        
        if (msg.isEmpty() || msg.length() > 1000) {
            //Don't send anything if it's empty or greater than 1000 characters
        }
        else {
            mainChatText.appendText(loggedUser.getUsername() + ": " + msg + '\n');
            System.out.println("Started: " + chaseClient.isStarted());
            //if(chaseClient.isStarted())
            //{
            	try {
					chaseClient.sendMessage(chaseClient.getDestinationUsername() + ":~:" + msg + '\n');
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("Exception in sending message");
				}
            //}
            textInput.clear();
        }
    }
    
    private void setTextAreaColor(String colorCode) {
    	try {
    		Region region = (Region) mainChatText.lookup(".content");
    		if (colorCode.equals("#ff"))
    		{
    			throw new Exception("Sorry, ChaSe does not support color #FF");
    		}
    		region.setStyle("-fx-background-color: " + colorCode + ";");
    	}
    	catch (Exception e) {
    		mainChatText.appendText("<ChaSe>: " + e.getMessage());
    	}
    }

    public void setStage(Stage stage) {
        this.mainStage = stage;
    }
    
    /******************************* Private variable Section ***********************/
    @FXML // fx:id="mainChatText"
    private TextArea mainChatText = new TextArea();
    private Stage aboutStage = new Stage();
    private Stage prefStage = new Stage();
    private Image icon = new Image(getClass().getResourceAsStream("t.png"));
    private String msg;
    
    @FXML 
    private MenuItem closeButton;
    @FXML
    private TextArea ChaSeConsole;
    
	@SuppressWarnings("unused")
	private Stage mainStage;

    @FXML // fx:id="textInput"
    private TextField textInput; 
    
    @FXML // fx:id="sendButton"
    private Button sendButton; 
    
    @FXML // fx:id="saveButton
    private Button saveButton;
    
    @FXML // fx:id="backgroundColor
    private MenuItem backgroundColor;
    
    @FXML // fx:id="helpButton"
    private MenuItem aboutButton;
    
    @FXML // fx:id="okAboutButton"
    private Button okAboutButton;
    
    @FXML // fx:id="colorPicker"
    private ColorPicker colorPicker;
    
    @FXML
    private TextField usernameField;
    
    @FXML
    private Button connectButton;
    
    @FXML // fx:id="connectMenu
    private MenuItem connectMenu;   
}
