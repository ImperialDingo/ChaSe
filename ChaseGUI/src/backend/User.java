package backend;

import java.io.IOException;

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
	 * @param username
	 */
	public void setUsername(String username)
	{
		this.username = username;
	}
	
	/**
	 * Getter for username
	 * @return
	 */
	public String getUsername(){return this.username;}
	
	public void closeLog()
	{
		try {
			myLog.closeLogOutput();
		} catch (IOException e) {
			System.out.println("Exception occurred while closing log file.");
		}
	}
	
	public void startLog()
	{
		myLog.createLog(username);
	}
	public void writeToLogFile(String message)
	{
		myLog.writeToLog(message);
	}
	
	public String readLogFile()
	{
		String log = new String();
		log = myLog.retreiveLog(username);
		return log;
	}
	
	private String username;
	private Log myLog;
}
