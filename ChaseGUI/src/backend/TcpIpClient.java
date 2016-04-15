package backend;

import java.io.*;
import java.net.*;


public class TcpIpClient {

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

	public void serverHandshake()
	{
		try {
			toServer.writeBytes(username+ '\n');
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendMessage(String message) throws IOException
	{
		toServer.writeBytes(dstUsername + ":~:" + message + '\n');
	}

	public String receiveMessages()
	{
		System.out.println(loggedIn);
		while(loggedIn)
		{
			try {
				String tmp = fromServer.readLine();
				System.out.println(tmp);
				return tmp;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		return null;
	}
	
	public void setCurrentUsername(String username)
	{
		this.username = username;
	}

	public void setDestinationUsername(String dstUsername)
	{
		this.dstUsername = dstUsername;
		hasDestination = true;
	}
	
	public String getDestinationUsername()
	{
		return dstUsername;
	}
	public boolean isStarted(){return this.hasDestination;}
	
	public static void main(String [] argv)
	{
		
		TcpIpClient chaseClient = new TcpIpClient();
		try{
			chaseClient.startClient();
		}catch(Exception e)
		{
			e.printStackTrace();		
		}
	}

	private Socket clientSocket;
	private BufferedReader fromServer;
	private DataOutputStream toServer;
	private boolean loggedIn;
	private boolean hasDestination;
	private String username;
	private String dstUsername;
}
