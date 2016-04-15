package controller;

import javafx.geometry.Insets;



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


public class MainPanelController extends SubController{

	@Override
	public void showPane() {
		//Create a new Scene with the mainWinPane and show to screen
		
		//Pane mainWinPane = new Pane();
		mainPane = createMainPane();
		mainScene = new Scene(mainPane);
		//mainWinPane = createMainPane();
		myController.getStage().setScene(mainScene);
		myController.getStage().show();
		
		new Thread(()->receiveMessages()).start();
	}
	
	
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
		MenuItem darkGreen = new MenuItem("Dark Green");
		MenuItem lightPink = new MenuItem("Light Pink");
		MenuItem black = new MenuItem("Black");		
		
		//Set Event Handlers for menu items
		connect.setOnAction(event->connectHandler(event));
		logout.setOnAction(event->logoutHandler(event));
		aboutItem.setOnAction(event->aboutHandler(event));
		
		aliceBlue.setOnAction(event->colorHandler(Color.ALICEBLUE));
		blueViolet.setOnAction(event->colorHandler(Color.BLUEVIOLET));
		chocolate.setOnAction(event->colorHandler(Color.CHOCOLATE));
		crimson.setOnAction(event->colorHandler(Color.CRIMSON));
		darkGreen.setOnAction(event->colorHandler(Color.DARKGREEN));
		lightPink.setOnAction(event->colorHandler(Color.LIGHTPINK));
		black.setOnAction(event->colorHandler(Color.BLACK));		
		
		//Assign menu items to respective menus
		fileMenu.getItems().addAll(aboutItem, logout);
		backgroundColor.getItems().addAll(aliceBlue, blueViolet, chocolate, crimson, darkGreen, lightPink, black);
		prefMenu.getItems().addAll(connect, backgroundColor);		

		//Assign menus to menubar
		topMenu.getMenus().addAll(fileMenu, prefMenu);
		
		return topMenu;
		
	}
	
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
        about.setText("Created by Joshua C Oglesby &amp; Jean Michael Almonte. All Rights Reserved. 2016.");
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
    	
    	if(myController.getSelectedUsername() != null)
    	{
    		mainChatText.appendText("Me: " + message + '\n');
    		myController.sendMessage(message);
    	}
    	else
    	{
    		mainChatText.appendText("You have not selected a user to chat with. \n");
    	}
    	
    	textInput.clear();
    }
    
    private void receiveMessages()
    {
    	String message = new String();
    	while(true)
    	{
    		message = myController.receiveMessages();
    		mainChatText.appendText(message + '\n');
    	}
    	
    }
	
/*	
	@FXML
    private void textInputHandler() {
        textInput.setOnKeyPressed((event) -> {
            if (event.getCode() == KeyCode.ENTER)
                sendMessage();
        });
    }
    

    
    @FXML
    private void aboutButtonHandler(ActionEvent event) {
            //Initialize the about screen
            try {
            	//aboutStage = new Stage();
            	Stage SecondaryStage = new Stage();
            	SecondaryStage.setTitle("About ChaSe 2016");
                
                FXMLLoader loader = new FXMLLoader(getClass().getResource("about.fxml"));
                Pane aboutPane = (Pane)loader.load();
                
                Scene scene = new Scene(aboutPane);
                SecondaryStage.getIcons().add(icon);
                SecondaryStage.setScene(scene);
                SecondaryStage.centerOnScreen();
                SecondaryStage.setResizable(false);
                SecondaryStage.sizeToScene();
                SecondaryStage.show();
                
            }
            catch (Exception e) {
                e.printStackTrace();
            }
  
    }
    
    @FXML
    void PreferencesHandler(ActionEvent event) {

            try {
            	Stage SecondaryStage = new Stage();
            	SecondaryStage.setTitle("ChaSe - Preferences");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Preferences.fxml"));
                Pane prefPane = (Pane)loader.load();
                Scene scene = new Scene(prefPane);
                SecondaryStage.getIcons().add(icon);
                SecondaryStage.setResizable(false);
                SecondaryStage.setScene(scene);
                SecondaryStage.sizeToScene();
                SecondaryStage.centerOnScreen();
                SecondaryStage.show();
            }
            catch (Exception e) {
                e.printStackTrace();
            }

    }
    @FXML
    void connectToUserHandler() {
    	connectButton.setOnMouseClicked((event) -> {
    		String usr = usernameField.getText();
			System.out.println(myController.getUsername());  		
    		//Check if empty username or we can check other conditions for name here
    		if (usr.isEmpty() || usr.contains(" ")) {
    			//userTextField.setText("Please enter a valid username!");
    			usernameField.clear();
    			ChaSeConsole.appendText("Please enter a valid username with no spaces\n");
    		}
    		else {
    			//chaseClient.setDestinationUsername(usr);
    			//System.out.println(chaseClient.getDestinationUsername());
    			ChaSeConsole.appendText("Connecting to " + usr + "\n");
    			try {
					//chaseClient.startClient();
					//chaseClient.serverHandshake();
					//new Thread( ()-> receiveMessages()).start();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("Exception occured while attempting to start TcpIpClient");
				}
    		}
    	});
    }
    
    private void receiveMessages()
    {
    
    }
    /*
    @FXML
    void setBackGroundColorHandler(ActionEvent event) {
    		String color = "#" + Integer.toHexString(colorPicker.getValue().hashCode()); 
    		setTextAreaColor(color);
    }*/
    
 /*   // TODO once user selects save, begin connecting to user. Print out server detail to screen
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
    	mainChatText.appendText(myController.getUsername() + ":" + msg);
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
    
   */ 

    private TextArea mainChatText;
    private Scene mainScene;
    private Pane mainPane;
    private Stage secondaryStage;
    private Image icon = new Image(getClass().getResourceAsStream("t.png"));
    private String msg;
    
     
    private MenuItem closeButton;
    
    private TextArea ChaSeConsole;
    
	@SuppressWarnings("unused")
	private Stage mainStage;


    private TextField textInput; 
    

    private Button sendButton; 



    private Button okAboutButton;

    
    private TextField usernameField;
    
    
    private Button connectButton;
    

    private MenuItem connectMenu;   

}
