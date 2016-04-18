package controller;

import java.io.IOException;
import java.util.HashMap;

import backend.TcpIpClient;
import backend.User;
import javafx.stage.Stage;

/**
 * PrimaryController object
 * @authors Josh Oglesby & Jean Michael Almonte
 * <P> This class handles all interactions between the GUIS and the User/TcpIpClient.
 */

public class PrimaryController {
	
	/**
	 * Constructor
	 */
	public PrimaryController()
	{
		chaseClient = new TcpIpClient();
		loggedUser = new User();
		panels = new HashMap<String, SubController>();
		myStage = new Stage();
	}
	
	
	/**
	 * Adds a new pane to HashMap and sets itself as the parent to the {@link SubController}
	 * @param name string signifying HashMap key for desired {@link SubController}
	 * @param newPane new {@link SubController} to be added to HashMap
	 */
	public void addPane(String name, SubController newController)
	{
		newController.setParent(this);
		panels.put(name, newController);		
	}
	
	/**	
	 * Stage getter method.
	 * @return myStage reference to Stage used for implementation
	 */
	public Stage getStage(){return this.myStage;}
	
	/**
	 * Calls {@link SubController} showPane method.
	 * @param name HashMap key for desired {@link SubController} 
	 */
	public void setPane(String name)
	{
		panels.get(name).showPane();		
	}
	
	/**
	 * Setter for user who is logged in
	 * @param username
	 */
	public void setUsername(String username)
	{
		this.loggedUser.setUsername(username);
		this.chaseClient.setCurrentUsername(username);
	}
	
	/**
	 * Getter for user who is logged in
	 * @return
	 */
	public String getUsername()
	{
		return this.loggedUser.getUsername();
	}
	
	/**
	 * Setter for the user to chat with
	 * @param username
	 */
	public void setSelectedUsername(String username)
	{
		selectedUser = new User();
		this.selectedUser.setUsername(username);
		this.chaseClient.setDestinationUsername(username);
	}
	
	/**
	 * Getter for the user to chat with
	 * @return
	 */
	public String getSelectedUsername()
	{
		if(selectedUser == null){return null;}
		return this.selectedUser.getUsername();
	}
	
	/**
	 * Sends commad to close log file
	 */
	public void closeLog()
	{
		loggedUser.closeLog();
	}
	
	/**
	 * Starts the connection to the server
	 */
	public void logToServer()
	{
		try {
			chaseClient.startClient();
		} catch (Exception e) {
			System.out.println("Exception occurred while trying to connect to server.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Logs out of server
	 */
	public void logOutOfServer()
	{
		chaseClient.logout();
	}
	/**
	 * Passes message to chaseClient
	 * @param message message to be send to another user
	 */
	public void sendMessage(String message)
	{
		try {
			chaseClient.sendMessage(message);
		} catch (IOException e) {
			System.out.println("Exception occurred while attempting to send a message.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Receives message from chaseClient
	 * @return string containing a message from another user
	 * @throws IOException
	 */
	public String receiveMessages() throws IOException
	{
		return chaseClient.receiveMessages();
	}
	
	/**
	 * Sends commad to loggedUser to start the logfile
	 */
	public void startLog()
	{
		loggedUser.startLog();
	}
	/**
	 * Sends command to LoggedUser to write a message to file
	 * @param message message to be written to logfile
	 */
	public void writeToLogFile(String message)
	{
		loggedUser.writeToLogFile(message);
	}
	
	/**
	 * Reads from log upon startup
	 * @return string containing all that is logged in the logfile
	 */
	public String readLogFile()
	{
		String log = loggedUser.readLogFile();
		return log;
	}
	
	//private variables	
	private TcpIpClient chaseClient;
	private User loggedUser;
	private User selectedUser = null;
	private HashMap<String, SubController> panels;
	private Stage myStage;	

}
