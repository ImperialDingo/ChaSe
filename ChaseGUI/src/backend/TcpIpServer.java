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

				e.printStackTrace();
			}
			
		}
	}

	private void receiveMessage(String username)
	{
		String dstUsername = new String();
		String message = new String();

		try {
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clients.get(username).getInputStream()));

			while(listening)
			{
					message = inFromClient.readLine();
					dstUsername = message.substring(0,message.lastIndexOf(":~"));
					message = message.substring(message.lastIndexOf(":~")+ 3);
					System.out.println(dstUsername);
					System.out.println(message);
					try {
						sendMessage(username, dstUsername, message);
					} catch (Exception e) {
						e.printStackTrace();
					}			
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void sendMessage(String srcUsername, String dstUsername, String message)throws Exception
	{
		if(clients.get(dstUsername) == null)
		{
			System.out.println("user not found");
			noUserFound(srcUsername);
		}
		else
		{
			System.out.println("User found");
			message = srcUsername + ":~:" + message + '\n';
			DataOutputStream outToClient = new DataOutputStream(clients.get(dstUsername).getOutputStream());
		       	outToClient.writeBytes(message);
		}
	}
	
	private void noUserFound(String username) throws IOException
	{
		DataOutputStream outToClient = new DataOutputStream(clients.get(username).getOutputStream());
       		outToClient.writeBytes("User is not online." + '\n');
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
