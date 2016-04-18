package backend;

import java.io.*;
import java.net.*;
import java.util.HashMap;

/**
 * TcpIpServer object
 * @authors Josh Oglesby & Jean Michael Almonte
 * <P> Handles all the routing of user messages from the TcpIpClients *
 */
 

public class TcpIpServer {

	/**
	 * Constructor
	 * Instantiates the HashMap
	 */
	public TcpIpServer()
	{
		clients = new HashMap<String, Socket>();
	}	


	/**
	 * Starts the server and kicks off threads.
	 * @throws Exception
	 */
	public void startServer() throws Exception
	{
		System.out.println("Starting Server");
		acceptorSocket = new ServerSocket(6789);
		listening = true;

		new Thread(()->listenForConnections()).start();
		new Thread(()->listenForClose()).start();
	}
	
	/**
	 * Listens for new TcpIpClient connections and adds them to the HashMap.
	 */
	private void listenForConnections()
	{
		while(listening)
		{
			try {
				System.out.println("Listening for connections");
				Socket newClient = acceptorSocket.accept();
				BufferedReader inFromClient =
			               new BufferedReader(new InputStreamReader(newClient.getInputStream()));
				String username = inFromClient.readLine();
				clients.put(username, newClient);	
				System.out.println(username + " has enterd the chat.");
				new Thread(()->receiveMessage(username)).start();
			} catch (IOException e) {
				System.out.println("Server has shut down");
				
			}
			
		}
	}

	/**
	 * Handler for receiving a message from a TcpIpClient and routing it.
	 * @param username username of the TcpIpClient it is receiving from.
	 */
	private void receiveMessage(String username)
	{
		String dstUsername = new String();
		String message = new String();

		try {
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clients.get(username).getInputStream()));

			while(listening)
			{
					message = inFromClient.readLine();
					dstUsername = message.substring(0,message.lastIndexOf(":~:"));
					message = message.substring(message.lastIndexOf(":~:")+ 3);
					try {
						sendMessage(username, dstUsername, message);
					} catch (Exception e) {
						break;
					}			
			}
		} catch (IOException e) {
			System.out.println(username + " has logged out.");
			clients.remove(username);
		}
	}
	
	/**
	 * Sends a message to a TcpIpClient
	 * @param srcUsername username of the sender
	 * @param dstUsername username of the receiver
	 * @param message string containing the message from the source to destination
	 * @throws Exception
	 */
	private void sendMessage(String srcUsername, String dstUsername, String message)throws Exception
	{
		if(clients.get(dstUsername) == null)
		{
			noUserFound(srcUsername, dstUsername);
		}
		else
		{
			message = srcUsername + ":" + message + '\n';
			DataOutputStream outToClient = new DataOutputStream(clients.get(dstUsername).getOutputStream());
		       	outToClient.writeBytes(message);
		}
	}
	
	/**
	 * Handles if the desired user is not logged on
	 * @param username string containing the username of the sender
	 * @param dstUsername username of the receiver
	 * @throws IOException
	 */
	private void noUserFound(String username, String dstUsername) throws IOException
	{
		DataOutputStream outToClient = new DataOutputStream(clients.get(username).getOutputStream());
       		outToClient.writeBytes(dstUsername + " is not online." + '\n');
	}

	/**
	 * Listener for the "close" statement from the terminal to close down the server
	 */
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

	/**
	 * Should notify the clients if it shuts down; did not get a chance to implement.
	 */
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

	/**
	 * main to launch the server
	 * @param argv
	 * @throws Exception
	 */
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
