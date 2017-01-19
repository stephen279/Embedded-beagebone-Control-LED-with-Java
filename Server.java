import java.net.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
//import java.util.ArrayList;              // not used only used if i create a array of objects






		public class Server
{
	
	
    private Socket clientSocket = null;                         // Client socket object
    private ObjectInputStream is = null;                        // Input stream
    private ObjectOutputStream os = null;                       // Output stream
    private ObjectOutputStream oss = null;
    private ObjectOutputStream iss = null;
	private ServerSocket server;  
	public Client cg;										// Client GUI 
	private static int portNumber = 5060;					// set up port number
	//private ArrayList<ThreadedConnectionHandler> al;
	ChatMessage cm;											// ChatMessage now is cm
	

	
	
		public void StartServer(){        // starts server
			
			System.out.println("inside StartServer()");
				 
			
			
			
			
			
				
    boolean listening = true;   		 //    set listening to true
    ServerSocket serverSocket = null;       // Set up the Server Socket
    String usernames;
  
    try 
    {
        serverSocket = new ServerSocket(portNumber);
        System.out.println("The New Server has started listening on port: " + portNumber  );
    }
    	catch (IOException e) 
    {
        System.out.println("Cannot listen on port: " + portNumber + ", Exception: " + e);
        System.exit(1);
    }
    
  
    // Server is now listening for connections or would not get to this point
    	while (listening) // almost infinite loop - loop once for each client request
    {
    	
    	
        Socket clientSocket = null;
        try{
        	
        	
        	
            System.out.println("**. Listening for a connection...");
            clientSocket = serverSocket.accept();       // accepts socket sent from new Client
     
            
            System.out.println("00. <- Accepted socket connection from a client: ");
            System.out.println("    <- with address: " + clientSocket.getInetAddress().toString());
            System.out.println("    <- and port number: " + clientSocket.getPort());
           
        } 
        catch (IOException e){
            System.out.println("XX. Accept failed: " + portNumber + e);
            listening = false;   // end the loop - stop listening for further client requests
        }        
        System.out.println("02. -- Finished communicating with client:" 
                + clientSocket.getInetAddress().toString());
      
        
        
        
        
        
        
        
        
        
        
        
        
        ThreadedConnectionHandler con = new ThreadedConnectionHandler(clientSocket);      // Creates an object which calls ThreadConnectionHandler
        con.start(); // calls run() inside ThreadedConnectionHandler use of multithreading
        
        			
        
     	
   
    } // Server is no longer listening for client connections - time to shut down.
    	try 
    {
        System.out.println("04. -- Closing down the server socket gracefully.");
        serverSocket.close();
    } 
    	catch (IOException e) 
    {
        System.err.println("XX. Could not close server socket. " + e.getMessage());
    }
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
			
}
		
	    public static void main(String args[]) {
	    	
	    	Server myServer;
	    	myServer = new Server();
	        myServer.StartServer();      // Starts Server
	        
    	
	    	
	}

	
		
}		
		
	    
	    
	    
	    
	    
 