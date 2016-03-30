package backend;

import java.io.*;
import java.net.*;
import java.util.HashMap;


public class TcpIpServer {

	
	public void startServer() throws Exception
	{
		acceptorSocket = new ServerSocket(12345);
		listening = true;
		new Thread()
		{
			public void run()
			{
				listenForConnections();
			}			
		};
	}
	
	private void listenForConnections()
	{
		while(listening)
		{
			try {
				Socket newClient = acceptorSocket.accept();
				BufferedReader inFromClient =
			               new BufferedReader(new InputStreamReader(newClient.getInputStream()));
				String username = inFromClient.readLine();
				clients.put(username, newClient);	

				new Thread(){public void run(){
					try {
						receiveMessage(username);
					} catch (Exception e) {
					// TODO Auto-generated catch block
						e.printStackTrace();
					}}};
			} catch (IOException e) {

				e.printStackTrace();
			}
			
		}
	}
	
	private void routeMessage()
	{
		//sendMessage();
	}
	
	private void receiveMessage(String username)throws Exception
	{
		BufferedReader inFromClient =
	               new BufferedReader(new InputStreamReader(clients.get(username).getInputStream()));
		while(inFromClient.ready())
		{
			//parse
			//route
		}	
		
	}
	
	private void sendMessage(String dstUsername, String message)
	{
		
	}
   public static void main(String argv[]) throws Exception
      {
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
      }

   
   private ServerSocket acceptorSocket;
   private boolean listening;
   private HashMap<String, Socket> clients;
   
   
}
