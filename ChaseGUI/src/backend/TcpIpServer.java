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
				System.out.println(username);
				new Thread(()->receiveMessage(username)).start();
			} catch (IOException e) {

				e.printStackTrace();
			}
			
		}
	}

	private void receiveMessage(String username)
	{
		
		System.out.println("Receive Message");
		String dstUsername = new String();
		String message = new String();
		try {
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clients.get(username).getInputStream()));

			while(listening)
			{
					message = inFromClient.readLine();
					dstUsername = message.substring(0,message.lastIndexOf(":~"));
					message = message.substring(message.lastIndexOf(":~")+ 3);

					try {
						sendMessage(username, dstUsername, message);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void sendMessage(String srcUsername, String dstUsername, String message)throws Exception
	{
		if(clients.get(dstUsername) == null)
		{
			noUserFound(srcUsername);
		}
		else
		{
			message = srcUsername + ":~:" + message;
			DataOutputStream outToClient = new DataOutputStream(clients.get(dstUsername).getOutputStream());
	       	outToClient.writeBytes(message);
		}
	}
	private void noUserFound(String username) throws IOException
	{
		DataOutputStream outToClient;

		outToClient = new DataOutputStream(clients.get(username).getOutputStream());
       	outToClient.writeBytes("User is not online.");
	}
	
  public static void main(String argv[]) throws Exception
  {
   TcpIpServer chaseServer = new TcpIpServer();
   chaseServer.startServer();
  }

   
   private ServerSocket acceptorSocket;
   private boolean listening;
   private HashMap<String, Socket> clients;
   
   
}
