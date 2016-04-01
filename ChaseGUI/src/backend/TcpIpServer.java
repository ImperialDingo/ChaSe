package backend;

import java.io.*;
import java.net.*;
import java.util.HashMap;


public class TcpIpServer {

	
	public void startServer() throws Exception
	{
		System.out.println("Starting Server");
		acceptorSocket = new ServerSocket(6789);
		listening = true;
		/*
		Thread ServerThread = new Thread()
		{
			public void run()
			{
				listenForConnections();
			}			
		};
		ServerThread.start();
		*/
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
			
			//String message = new String();
			
			while(inFromClient.ready())
			{
				//Get dstUsername
				//Get message
				//message = inFromClient.readLine();
				//Send Message to dstUsername;
					try {
						sendMessage(username, username);
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
	
	private void sendMessage(String dstUsername, String message)throws Exception
	{
		DataOutputStream outToClient = new DataOutputStream(clients.get(dstUsername).getOutputStream());
        message  = message.toUpperCase() + '\n';
        outToClient.writeBytes(message);
	}
	
   public static void main(String argv[]) throws Exception
      {
	   
	   /*
         String clientSentence;
         String capitalizedSentence;
         ServerSocket welcomeSocket = new ServerSocket(6789);

         while(true)
         {
            Socket connectionSocket = welcomeSocket.accept();
            BufferedReader inFromClient =
               new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
            clientSentence = inFromClient.readLine();
            System.out.println("Received: " + clientSentence);
            capitalizedSentence = clientSentence.toUpperCase() + '\n';
            outToClient.writeBytes(capitalizedSentence);
         }
         */
	   
	   TcpIpServer chaseServer = new TcpIpServer();
	   chaseServer.startServer();
      }

   
   private ServerSocket acceptorSocket;
   private boolean listening;
   private HashMap<String, Socket> clients;
   
   
}
