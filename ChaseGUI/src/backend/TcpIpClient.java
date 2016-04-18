package backend;

import java.io.*;
import java.net.*;
/**
 * TcpIpClient Object
 * @authors Josh Oglesby & Jean Michael Almonte
 * <P> This class is designed to handle the interface with the TcpIpServer *
 */

public class TcpIpClient {

	/**
	 * Starts the client socket
	 * @throws Exception
	 */
	public void startClient() throws Exception
	{

			clientSocket = new Socket("68.35.170.250", 6789);
			fromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			toServer = new DataOutputStream(clientSocket.getOutputStream());
			hasDestination = false;
			if(username != null)
			{
				loggedIn = true;
				serverHandshake();
			}
			else{loggedIn = false;}

	}

	/**
	 * Passes the username to the server to allow for checking of logged in users
	 */
	public void serverHandshake()
	{
		try {
			toServer.writeBytes(username+ '\n');
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Sends a message to the server
	 * @param message string containing a message from the logged in user
	 * @throws IOException
	 */
	public void sendMessage(String message) throws IOException
	{
		toServer.writeBytes(dstUsername + ":~:" + message + '\n');
	}

	/**
	 * Reads a single message from the server
	 * @return a string containing the message
	 * @throws IOException
	 */
	public String receiveMessages() throws IOException
	{
		while(loggedIn)
		{
			String tmp = fromServer.readLine();
			return tmp;
		}
		return null;
	}
	
	/**
	 * Sets the username for the logged in user
	 * @param username
	 */
	public void setCurrentUsername(String username)
	{
		this.username = username;
	}

	/**
	 * Sets the destination username to direct the messages on the server
	 * @param dstUsername
	 */
	public void setDestinationUsername(String dstUsername)
	{
		this.dstUsername = dstUsername;
		hasDestination = true;
	}
	
	/**
	 * Getter for the destination username
	 * @return
	 */
	public String getDestinationUsername()
	{
		return dstUsername;
	}
	
	/**
	 * Check if the client has started its connection to the server
	 * @return boolean indicating if it is started
	 */
	public boolean isStarted(){return this.hasDestination;}

	/**
	 * Logs the client out of the server
	 */
	public void logout()
	{
		try {
			clientSocket.close();
			loggedIn = false;
		} catch (IOException e) {
			System.out.println("Closing Socket");
		}
	}

	// Private variables
	private Socket clientSocket;
	private BufferedReader fromServer;
	private DataOutputStream toServer;
	private boolean loggedIn;
	private boolean hasDestination;
	private String username;
	private String dstUsername;
}
