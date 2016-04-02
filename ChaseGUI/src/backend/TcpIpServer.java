package backend;

import java.io.*;
import java.net.*;
import java.util.HashMap;


public class TcpIpServer {

	public TcpIpServer()
	{
		clients = new HashMap<String, Socket>();
	}	


	public void startServer() throws Exception
	{
		System.out.println("Starting Server");
		acceptorSocket = new ServerSocket(6789);
		listening = true;

		new Thread(()->listenForConnections()).start();
		new Thread( ()->listenForClose()).start();
	}
	
	private void listenForConnections()
	{
		System.out.println("Listening for connections");
		while(listening)
		{
			System.out.println("Try Listening");
			try {
				System.out.println("Listening for connections");
				Socket newClient = acceptorSocket.accept();
				BufferedReader inFromClient =
			               new BufferedReader(new InputStreamReader(newClient.getInputStream()));
				String username = inFromClient.readLine();
				clients.put(username, newClient);	
				System.out.println(username + " has enterd the chat.");
				new Thread(()->receiveMessage(username)).start();
;
			} catch (IOException e) {

				e.printStackTrace();
			}
			
		}
	}

	private void receiveMessage(String username)
	{
		try {
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clients.get(username).getInputStream()));
			
			while(listening)
			{
					try {
						sendMessage(username, inFromClient.readLine());
					} catch (Exception e) {
						e.printStackTrace();
					}			
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void sendMessage(String dstUsername, String message)throws Exception
	{
		System.out.println("SendMessage()");
		DataOutputStream outToClient = new DataOutputStream(clients.get(dstUsername).getOutputStream());
		System.out.println("Received: " + message);
	        message  = message.toUpperCase() + '\n';
       	        outToClient.writeBytes(message);
	}
	
	private void listenForClose()
	{
		 terminator = new BufferedReader(new InputStreamReader(System.in));
		String checkClose = new String();
		while(listening)
		{
			try{
				checkClose = terminator.readLine();
			}catch(IOException e)
			{
				e.printStackTrace();
			}	
			if(checkClose.equals("close"))
			{
				notifyAndClose();
			}
		}
	}

	private void notifyAndClose()
	{
		System.out.println("Terminating ChaSe Server.");
		listening = false;
		try{
			acceptorSocket.close();
		}catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String argv[]) throws Exception
	{
		TcpIpServer chaseServer = new TcpIpServer();
		chaseServer.startServer();
	}

   
	private ServerSocket acceptorSocket;
	private boolean listening;
	private HashMap<String, Socket> clients;

	private BufferedReader terminator;
   
   
}
