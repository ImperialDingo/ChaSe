package backend;

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
		myLog.createLog(username);
	}
	
	/**
	 * Getter for username
	 * @return
	 */
	public String getUsername(){return this.username;}
	
	
	
	private String username;
	private Log myLog;
}
