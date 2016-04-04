package backend;

import java.io.*;
import java.net.*;


public class TcpIpClient {

	public void startClient() throws Exception
	{

			clientSocket = new Socket("localhost", 6789);
			fromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			fromUser = new BufferedReader(new InputStreamReader(System.in));
			toServer = new DataOutputStream(clientSocket.getOutputStream());
			loggedIn = true;
			System.out.println("ChaSe Started!");	
			System.out.println("Enter your username.");
			username = fromUser.readLine();
			System.out.println("Who are you sending a message to?");
			dstUsername = fromUser.readLine();
			
			new Thread( ()->startMessages()).start();
			new Thread( ()->receiveMessages()).start();			
	}

	private void startMessages()
	{
		try {
			toServer.writeBytes(username+ '\n');
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(loggedIn)
		{
			try {
				sendMessage(fromUser.readLine());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void sendMessage(String message) throws IOException
	{
		System.out.println("You Entered: " + message);
		toServer.writeBytes(dstUsername + ":~:" + message + '\n');
	}

	private void receiveMessages()
	{
		while(loggedIn)
		{
			try {
				System.out.println(fromServer.readLine());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

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
	private BufferedReader fromUser;
	private DataOutputStream toServer;
	boolean loggedIn;
	private String username;
	private String dstUsername;
}
