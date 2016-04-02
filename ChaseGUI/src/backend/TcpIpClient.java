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
			try{	
				new Thread( ()->startMessages()).start();
			}catch (IOException e)
			{
				e.printStackTrace();
			}
			try{	
				new Thread( ()->receiveMessages()).start();
			}catch (IOException e)
			{
				e.printStackTrace();
			}


			
	}

	private void startMessages() throws IOException
	{
		toServer.writeBytes("username" + '\n');
		while(loggedIn)
		{
			toServer.writeBytes(fromUser.readLine());
		}
	}

	private void receiveMessages() throws IOException
	{
		while(loggedIn)
		{
			System.out.println(fromServer.readLine());
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
}
