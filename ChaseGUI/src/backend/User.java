package backend;

import java.io.IOException;

/**
 * User object
 * @authors Josh Oglesby & Jean Michael Almonte
 * <P> This keeps up with the name of the current user and its logfile
 *
 */
public class User {

	/**
	 * Constructor
	 */
	public User()
	{
		myLog = new Log();
	}
	
	/**
	 * Setter for Username as well as making sure
	 * a log file is created for that user
	 * @param username current username of the loggedUser
	 */
	public void setUsername(String username)
	{
		this.username = username;
	}
	
	/**
	 * Getter for username
	 * @return username of the current user
	 */
	public String getUsername(){return this.username;}
	
	/**
	 * Sends command to close the logfile
	 */
	public void closeLog()
	{
		try {
			myLog.closeLogOutput();
		} catch (IOException e) {
			System.out.println("Exception occurred while closing log file.");
		}
	}
	
	/**
	 * Sends command to start the logfile
	 */
	public void startLog()
	{
		myLog.createLog(username);
	}
	
	/**
	 * Writes provided message to logfile
	 * @param message string containing a message
	 */
	public void writeToLogFile(String message)
	{
		myLog.writeToLog(message);
	}
	
	/**
	 * Reads from the logfile
	 * @return string containing the contents of the logfile
	 */
	public String readLogFile()
	{
		String log = new String();
		log = myLog.retreiveLog(username);
		return log;
	}
	
	// Private variables
	private String username;
	private Log myLog;
}
