
import java.net.*;

import java.util.ArrayList;

import javax.swing.SwingUtilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


			/* Class is threaded so will run on its own thread */

public class   ThreadedConnectionHandler extends Thread			// Extends thread for multithreading
{
    private static Socket clientSocket = null;                         // Client socket object
    private ObjectInputStream is = null;                        // Input stream is    // sets up input streams private ObjectInputStram is = null;
    private ObjectInputStream ison = null;                        // Input stream is
    private ObjectOutputStream os = null;                       // Output stream os
    private ObjectOutputStream oss = null; 						// Output stream oss .......
    private ObjectOutputStream osss = null; 
    public String usernames;
    public String un;
  


   
    // The constructor for the connection handler
    public ThreadedConnectionHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        //Set up a service object to get the current date and time
     //   theDateService = new DateTimeService();
      
        
    }

   
	// called from inside server.java from the object created use of  Multithreading
    public void  run(){            							     // going run this ans object of class was set u from server and .start()used.
         try {
        /*	try {										//(SLEEP) test with a sleep (4 seconds)  e.g open two Clients side by side press log in on 1st client then 2nd client straight away, you will see after 4 secounds the two clients where working on there own. 
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  */
        	System.out.println("inside run() in connection handler");
            this.is = new ObjectInputStream(clientSocket.getInputStream());		   // sets up new input and Output streams // set up new input stream this.is = new InputStream(clientSever.getInputtream		
            this.os = new ObjectOutputStream(clientSocket.getOutputStream());
      
            
            try {
     			getMessage();					//First getMessage() is called to get message and username that is saved in file message.bin and messageUser.bin which will be displayed in the txtarea of client (off coarse only if there is some data inside these files) 
     		} catch (IOException e1) {
     		
     			e1.printStackTrace();
     		}
            
            
            
        	FileOutputStream fs = new FileOutputStream("message.bin");				//  set up fileInputStream files called message.bin fileInputStream fs = new FileinputStream(messge.bin) NB * creates new file called message.bin . Note* will need to refresh home folder to see these files appear in directory
        	FileOutputStream fss = new FileOutputStream("messageUser.bin");			//  NB * creates new file called messageuser.bin	
        	
        	FileInputStream fis = new FileInputStream("message.bin");		
        	this.os = new ObjectOutputStream(clientSocket.getOutputStream()); 
        	this.oss = new ObjectOutputStream(fs);					// set up new Output stream for to send msg to message.bin file
        	this.osss = new ObjectOutputStream(fss);				// set up new Output stream for to send username to messageUser.bin file
        	this.ison = new ObjectInputStream(fis);
        	this.readCommand1();								// calls readcommand1 to get username object from client and send it to chatUser.java							
            this.readCommand();										// calls readcommand1 to get message object from client and send it to chatMessage.java
      
            
         } 
         catch (IOException e) 
         {
                System.out.println("XX. There was a problem with the Input/Output Communication:");
            e.printStackTrace();
         }
         
    }
    
    
    // gets text from message.bin and messageUser.bin which then is sent to client 
    public  void getMessage() throws IOException{        		  //set up a getMessage publiv void getMessage(){		 

    	System.out.println("inside getMessage in client");
  
   
   	
   		try {
   			FileInputStream fi = new FileInputStream("message.bin");                 //  File input stream from message.bin
   			FileInputStream fii = new FileInputStream("messageUser.bin");			  // File  input stream from messageUser.bin
   			System.out.println("fi is"+ fi);	
   			System.out.println("fii is"+ fii);
   			ObjectInputStream is = new ObjectInputStream(fi);				// input stream seet up new input strea but this time dont use clientSocket use fs fileinput stream
   			ObjectInputStream iss = new ObjectInputStream(fii);				// output stream
   			System.out.println("is is"+ is);
   			System.out.println("iss is"+ iss);
   			try {
   				String message12 = (String)is.readObject();							// message12 is the message read from message.bin  now read the file message = is.readObject(
   				String fromUsername1 = (String)iss.readObject();					// fromUsername1 is the username read from messageUser.bin
   			
   				
   				System.out.println("messaga 12 from getMessage inside ThreadedConnection is ...."+ message12 +"and messageUser1 is" + fromUsername1);
   				String returnMsg  = ("Message is...."+ message12 +"...From Username.." + fromUsername1);    // string that will be sent to client
  
   				this.send(returnMsg);										// call send method with parameter returnMsg 
   				
   				is.close();															// close Streams
   				iss.close();
   				
   			} catch (ClassNotFoundException e) {
   				// TODO Auto-generated catch block
   				e.printStackTrace();
   			}
   			
   			

   		} catch (FileNotFoundException e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		} catch (IOException e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		}


   	
	}
    	

    
    
    // Receive and process incoming string commands from client socket 
	public boolean readCommand() throws IOException {         					// recieves Object message from Client
        Object s = null; 
    

        try {
        	System.out.println("recieved an  object from client");
        	s = (ChatMessage) is.readObject();                           		// read in the ChatMessage Object  message sent from Client (Chatmessage must have implement Serializable)
        	System.out.println(" object s is now "+s);
        
       
        } 
        catch (Exception e){    // catch a general exception
                this.closeSocket();
            return false;
        }

        System.out.println("01. <- Received a new message object from the client (" + s + ").");  
        String message = ((ChatMessage) s).getMessage();  			  // send s Object(sent from client) into ChatMessage and return it to message variable Chatmessage must have implement Serializable
        System.out.println("message is "+message);
  //    writeMsg(message);     		          //   send message to client only used when not using my fileOutputStreams message.bin   		
        writeMsg1(message);					// when using my message.bin writeMsg1 sends message to message.bin file to be stored
    	MyLeds ledOn = new MyLeds();     // call an instance of MyLeds class then call method to turn on my leds by passing in "yes".
		ledOn.ledFast("yes");

    
		
		return true;
	
	}


	public boolean readCommand1() throws IOException {					// recieves Object Username from Client
        Object un = null; 										

    	
        try {
        	System.out.println("recieved and object from client");
        	un = (ChatUser) is.readObject();							// read in the ChatUser Object  sent from Client						  
        	System.out.println(" object un is now "+un);
        	
       
        } 
        catch (Exception e){    // catch a general exception
                this.closeSocket();
            return false;
        }
    	
     
        System.out.println("01. <- Received a new username object from the client (" + un + ").");   
        String usern = ((ChatUser) un).getUser(); 				 // send un Object(sent from client username) into ChatUser and return it to usern variable
        System.out.println("message is "+usern);			 
    //  writeMsg(usern);             			 //   send message to client only used when not using my fileOutputStreams messageUser.bin   			
        writeMsg2(usern);						// when using my messageUser.bin writeMsg2 sends user to messageUser.bin file to be stored
       
		return true; 
   
	}

	
	   // Send a generic String back to the client 
    private boolean send(String o) {
        try {
            System.out.println("02. -> Sending (" + o +") to the client from Threadedconnection inside send().");
            this.os.writeObject(o);					// sends String back to server (the client is always listening when ever ListenFromServer() is called)
        	MyLeds ledOff = new MyLeds();     // call an instance of MyLeds class then call method to turn off my leds by passing in "no".
    		ledOff.ledFast("no");
			System.out.println("BeagleBone Leds turned off");
			
        } 
        catch (Exception e) {
            System.out.println("XX." + e.getStackTrace());
        }
        return true;
    }
    

	/*public  boolean writeMsg(String msg) {  		// only used when i dont use my message.bin and messageUser.bin file and used when sending message from server back to same client that sent message 
	
		System.out.println("inside WriteMsg inside ThreadedConnection"+msg);
		// write the message to the stream
		try {
			System.out.println("Write message to client"+msg);
			os.writeObject(msg);            					     // send message to client    (not to a file)
		}
		// if an error occurs, do not abort just inform the user
		catch(IOException e) {
			
		}
		return true;
	}*/

    
    // write the message to the stream oss message.bin which is the message.
		public boolean writeMsg1(String msg) {
		
		
		
			try {
				System.out.println("Write message to message.bin from writeMsg1()"+msg);
				oss.writeObject(msg);              // send message to message.bin using oss outputStream
				oss.flush();
			
				
			}
			// if an error occurs, do not abort just inform the user
			catch(IOException e) {
				display("Error sending message to " );
				display(e.toString());
			}
			return true;
		}
		
		// write the message to the outputstream osss (which is the messageUser.bin (username)) 
		public boolean writeMsg2(String usr) {
			
			
				// write the message to the outputstream osss 
				try {
					System.out.println("Write message to messagesUser.bin from writeMsg2()"+usr);
					osss.writeObject(usr);              // send message to messageUser.bin using outputStream osss
			
				}
				// if an error occurs, do not abort just inform the user
				catch(IOException e) {
					display("Error sending message to " );
					display(e.toString());
				}
				return true;
			}
		
		

    public void display(String msg) {
	
			System.out.println("inside display() in threaded class msg = "+ msg);
			Client.ta.setText(msg + "\n");		// append to the ClientGUI JTextArea (or whatever)
			
			
	}
    
 
/*
    // Use our custom DateTimeService Class to get the date and time
    private void getDate() {        // use the date service to get the date
        String currentDateTimeText = theDateService.getDateAndTime();
        this.writeMsg(currentDateTimeText);
        System.out.println("inside getDate()");
    }

*/
    
    // Close the client socket 
    public void closeSocket() { //gracefully close the socket connection
        try {
            this.os.close();
            this.is.close();
            this.clientSocket.close();
        	System.out.println("inside closeSocket");
        	
        } 
        catch (Exception e) {
            System.out.println("XX. " + e.getStackTrace());
        }
    }
    
       

   
  
  public static void showMessage(final String m) throws IOException {     // shows message on textArea
		
		SwingUtilities.invokeLater(
				new Runnable(){
					public void run(){
		
						Client.ta.append(m);
					}
				}

				);
    }
  
    
    
}




		