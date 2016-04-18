package controller;

import javafx.geometry.Insets;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.layout.*;

/**
 * MainPanelController object
 * @authors Josh Oglesby & Jean Michael Almonte
 * <P> This class handles all events within the main chat window.
 */

public class MainPanelController extends SubController{

	
	/**
	 * Override function showPane to instantiate the mainPanel GUI
	 */
	@Override
	public void showPane() {
		//Create a new Scene with the mainWinPane and show to screen
		
		//Pane mainWinPane = new Pane();
		mainPane = createMainPane();
		mainScene = new Scene(mainPane);
		//mainWinPane = createMainPane();
		myController.getStage().setScene(mainScene);
		myController.getStage().show();
		
		mainChatText.appendText(myController.readLogFile());
		myController.startLog();
		new Thread(()->receiveMessages()).start();
	}
	
	/**
	 * Creates the mainPanel
	 * @return Pane
	 */
	@SuppressWarnings("static-access")
	private Pane createMainPane()
	{
		//Create Pane
		Pane mainWinPane = new Pane();
		
		//Set Vertical Box Container
		VBox chatBox = new VBox();
		chatBox.setPrefHeight(400.0);
		chatBox.setPrefWidth(640.0);
		
		MenuBar topMenu = createMenuBar();
		chatBox.getChildren().add(topMenu);
		chatBox.setVgrow(topMenu, Priority.NEVER);
		
		AnchorPane chatPane = createChatPane();
		chatBox.getChildren().add(chatPane);
		chatBox.setVgrow(chatPane, Priority.ALWAYS);
		
		mainWinPane.getChildren().add(chatBox);
		return mainWinPane;
	}
	
	/**
	 * Creates the menu Bar
	 * @return MenuBar contianing all options
	 */
	private MenuBar createMenuBar()
	{
		//Create Menubar, menus and items
		MenuBar topMenu = new MenuBar();
		Menu fileMenu = new Menu("File");
		Menu prefMenu = new Menu("Preferences");
		MenuItem aboutItem = new MenuItem("About");
		MenuItem logout = new MenuItem("Log Out");		
		MenuItem connect = new MenuItem("Connect");
		
		Menu backgroundColor = new Menu("Background Color");
		MenuItem aliceBlue = new MenuItem("Alice Blue");
		MenuItem blueViolet = new MenuItem("Blue/Violet");
		MenuItem chocolate = new MenuItem("Chocolate");
		MenuItem crimson = new MenuItem("Crimson");
		MenuItem darkGreen = new MenuItem("Green");
		MenuItem lightPink = new MenuItem("Light Pink");
		MenuItem black = new MenuItem("Cornsilk");		
		
		//Set Event Handlers for menu items
		connect.setOnAction(event->connectHandler(event));
		logout.setOnAction(event->logoutHandler(event));
		aboutItem.setOnAction(event->aboutHandler(event));
		
		aliceBlue.setOnAction(event->colorHandler(Color.ALICEBLUE));
		blueViolet.setOnAction(event->colorHandler(Color.BLUEVIOLET));
		chocolate.setOnAction(event->colorHandler(Color.CHOCOLATE));
		crimson.setOnAction(event->colorHandler(Color.CRIMSON));
		darkGreen.setOnAction(event->colorHandler(Color.LIGHTGREEN));
		lightPink.setOnAction(event->colorHandler(Color.LIGHTPINK));
		black.setOnAction(event->colorHandler(Color.CORNSILK));		
		
		//Assign menu items to respective menus
		fileMenu.getItems().addAll(aboutItem, logout);
		backgroundColor.getItems().addAll(aliceBlue, blueViolet, chocolate, crimson, darkGreen, lightPink, black);
		prefMenu.getItems().addAll(connect, backgroundColor);		

		//Assign menus to menubar
		topMenu.getMenus().addAll(fileMenu, prefMenu);
		
		return topMenu;
		
	}
	
	/**
	 * Creates the main chat window
	 * @return
	 */
	@SuppressWarnings("static-access")
	private AnchorPane createChatPane()
	{
		AnchorPane chatPane = new AnchorPane();
		chatPane.setMaxHeight(400);
		chatPane.setMaxWidth(640);
		chatPane.setPrefHeight(400);
		chatPane.setPrefWidth(640);

		mainChatText = new TextArea();
		mainChatText.setWrapText(true);
		mainChatText.setEditable(false);
		mainChatText.setPrefHeight(264.0);
		mainChatText.setPrefWidth(608.0);
		mainChatText.setLayoutX(14.0);
		mainChatText.setLayoutY(18.0);
		
		textInput = new TextField();
		textInput.setPrefHeight(47.0);
		textInput.setPrefWidth(546.0);
		textInput.setLayoutX(14.0);
		textInput.setLayoutY(298.0);
		
		sendButton = new Button("SEND");
		sendButton.setPrefHeight(47.0);
		sendButton.setPrefWidth(61.0);
		sendButton.setLayoutX(560.0);
		sendButton.setLayoutY(298.0);
		sendButton.setOnAction(event->sendButtonHandler(event));
		
		chatPane.getChildren().addAll(mainChatText, textInput, sendButton);
		chatPane.setBottomAnchor(mainChatText, 93.0);
		chatPane.setLeftAnchor(mainChatText, 14.0);
		chatPane.setRightAnchor(mainChatText, 18.0);
		chatPane.setTopAnchor(mainChatText, 18.0);
		chatPane.setBottomAnchor(textInput, 30.0);
		chatPane.setLeftAnchor(textInput, 14.0);
		chatPane.setRightAnchor(textInput, 80.0);
		chatPane.setBottomAnchor(sendButton, 30.0);
		chatPane.setRightAnchor(sendButton, 19.0);		
		return chatPane;
	}
	
	/**
	 * Handles the connection selection for the desired user
	 * @param event
	 */
	private void connectHandler(ActionEvent event)
	{

		//Create a GridPane container
		secondaryStage = new Stage();
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(5);
		grid.setHgap(5);
		Button submit = new Button("Submit");
		GridPane.setConstraints(submit, 1, 0);
		grid.getChildren().add(submit);
		final TextField name = new TextField();
		name.setPrefColumnCount(17);
		GridPane.setConstraints(name, 0, 0);
		grid.getChildren().add(name);		
		
		submit.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override
			    public void handle(ActionEvent e) {
			        if (!name.getText().isEmpty() )
			        {
			        	myController.setSelectedUsername(name.getText());
			        	secondaryStage.close();
			        } 
			        else 
			        {
			        	name.setPromptText("Enter username.");
			        }
			     }
			 });
		
	  	Scene scene = new Scene(grid, 285, 50);
	  	secondaryStage.setScene(scene);
	  	secondaryStage.setTitle("Select user to chat with.");
		secondaryStage.show();	
	}

	/**
	 * Handles logout button select event
	 * @param event
	 */
	private void logoutHandler(ActionEvent event)
	{
		myController.closeLog();
		myController.logOutOfServer();
		myController.setPane("login");
	}
	
	/**
	 * Handles about button select event
	 * @param event
	 */
	private void aboutHandler(ActionEvent event)
	{
		secondaryStage = new Stage();
    	secondaryStage.setTitle("About ChaSe 2016");
        
        Pane aboutPane = new Pane();
        aboutPane.setPrefWidth(300);
        aboutPane.setPrefHeight(50);
        TextArea about = new TextArea();
        about.setText("Created by Joshua C Oglesby & Jean Michael Almonte. All Rights Reserved. 2016.");
        aboutPane.getChildren().add(about);
        about.setPrefWidth(300);
        about.setPrefHeight(50);
        about.setEditable(false);
        about.setWrapText(true);

        Scene scene = new Scene(aboutPane);
        secondaryStage.getIcons().add(icon);
        secondaryStage.setScene(scene);
        secondaryStage.centerOnScreen();
        secondaryStage.setResizable(false);
        secondaryStage.sizeToScene();
        secondaryStage.show();
	}
	
	/**
	 * Handles selected color event
	 * @param selectedColor
	 */
	private void colorHandler(Color selectedColor)
	{
		String color = "#" + Integer.toHexString(selectedColor.hashCode()); 
		setTextAreaColor(color);
	}
	
	/**
	 * Updates the text background color
	 * @param colorCode desired color from menu
	 */
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
	
	/**
	 * Handles send event
	 * @param event
	 */
    private void sendButtonHandler(ActionEvent event) {sendMessage();}

    
    
    /**
     * Sends message to mainChatText and TcpIpClient
     */
    private void sendMessage()
    {
    	String message = textInput.getText();
    	if(message.equals("")){return;}
    	
    	if(myController.getSelectedUsername() != null)
    	{
    		mainChatText.appendText("Me: " + message + '\n');
    		myController.sendMessage(message);
    		myController.writeToLogFile("Me: " + message + '\n');
    	}
    	else
    	{
    		mainChatText.appendText("You have not selected a user to chat with. \n");
    	}
    	
    	textInput.clear();
    }
    
    /**
     * Upon reveiving a message it will populate to the mainChatText area.
     */
    private void receiveMessages()
    {
    	String message = new String();
    	while(true)
    	{
    		try {
				message = myController.receiveMessages();
				mainChatText.appendText(message + '\n');
				myController.writeToLogFile(message + '\n');
			} catch (Exception e) {
				break;
			}    		
    	}    	
    }
	

    // Private variables
    private TextArea mainChatText;
    private Scene mainScene;
    private Pane mainPane;
    private Stage secondaryStage;
    private TextField textInput;
    private Button sendButton; 
    private Image icon = new Image(getClass().getResourceAsStream("t.png"));

}
