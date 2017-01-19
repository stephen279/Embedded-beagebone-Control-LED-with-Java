import java.net.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;




public class Client extends JFrame  implements ActionListener {     // First create a GUI for client 

		
	private static final long serialVersionUID = 1L;
	JPanel pnl = new JPanel() ;							// creates new Panel
//	JButton getMessage = new JButton( "Get Message" ) ; // getMessage button
	JButton login = new JButton( "Login");
	static JTextArea ta = new JTextArea( 10 ,50 );   // new TextArea 
	private JTextField tf;								// textField tf
	String sendMessageValueIP = "192.168.1.2";    		 // IP address
	private JLabel label; 							 // to hold the  messages		  
    public static String username;							//used for entering username from textField   
 	public boolean connected;						// connected will be True or False
 	private Client client;					 	// the Client object			

 	
 	

    
    public Client(){						// Client constructor   when run from main() it opens GUI
    	 	
    	super("Stephen Client");							// set up GUI
		setSize( 600,300 );
		setDefaultCloseOperation( EXIT_ON_CLOSE );
		add(pnl);
		
		tf = new JTextField(50);          				    // set up size off textField
		tf.setText("Unknown User");
		tf.setEditable(true);
		label = new JLabel("Please Log-In Enter username below", SwingConstants.CENTER);
	
		
	//	this.pnl.add( getMessage ) ;
		this.pnl.add(login);
		this.pnl.add(label);
		this.pnl.add(tf , BorderLayout.NORTH);
		this.pnl.add(new JScrollPane(ta),BorderLayout.SOUTH);   // where want to put it
		this.pnl.setSize(30,30);
		
		
	//	ta.setText("Message will show up here ") ;
	//	getMessage.addActionListener(this);	
		login.addActionListener(this);					// add an ActionListener to the login button
		setVisible( true );
	
		
    }
    

	public void actionPerformed(ActionEvent e) {				// what action to take when a event occurs
		Object o = e.getSource();
//if (e.getSourse == 
		// ok it is coming from the TextField Event when Enter is Pressed when connected = true
		if(connected) {
			System.out.println("inside connected event .....");
			// just have to send the message
			client.sendMessage(new ChatMessage(ChatMessage.MESSAGE, tf.getText()));	 // Send Object message to ChatMessage Class		
			tf.setText("");															// Reset TextField		
			return;
		}
			
		if(o == login) {							// LogIn Button
			 username = tf.getText();					// username is now what was entered in tf textField
			 System.out.println("usernname is "+username);
			 client = new Client(sendMessageValueIP);			// pass in IpAddress to ConnectToSever
			 label.setText("Enter your message below");
			 connected = true;									// set connected to true so we can now use data entered in textField 
				
			// Action listener for when the user enter a message
			 tf.addActionListener(this);
			 client.sendUser(new ChatUser(ChatUser.USER, tf.getText()));	 // Send Object message to ChatMessage Class through sendUser()	

		}	
		
		
	}
		


	private static int portNumber = 5060;				// set port number to 5060  just a random port
    private Socket socket = null;
    private ObjectOutputStream os = null;             	// declare OutputStream equal to os
    //private ObjectOutputStream os = null;    // sets output stream
    public ObjectInputStream is = null;				// set up all new Input Streams ............
    public ObjectInputStream iss = null;			 
    public ObjectInputStream isss = null;
    
        // the constructor expects the IP address of the server - the port is fixed
    public Client(String serverIP) {     //  sets up client constructor passing in an ipaddress
            if (!connectToServer(serverIP)) {
                    System.out.println("XX. Failed to open socket connection to: " + serverIP);            
            }
    }

    public boolean connectToServer(String serverIP) {						// Send in IpAddress 
            try { 	
         
            		this.username = username;							// Username which was typed into textField
            	// open a new socket to the server 
                    this.socket = new Socket(sendMessageValueIP,portNumber);    // new socket with ip address and Port number    // sets up socket   
               
                    this.os = new ObjectOutputStream(this.socket.getOutputStream());   	// set up streams    // set up sream this.os = new ObjectOutputStream (this.scoket.getOutputStream()
                    this.is = new ObjectInputStream(this.socket.getInputStream());
                           
                    System.out.println("00. -> Connected to Server:" + this.socket.getInetAddress() 
                                    + " on port: " + this.socket.getPort());
                    System.out.println("    -> from local address: " + this.socket.getLocalAddress() 
                                    + " and port: " + this.socket.getLocalPort());
                    String msgUsername = username;
                    showMessage(msgUsername +":");       // prints out Username: on textArea          
                    
                    String msg = "a new Connection accepted with IP address:" + socket.getInetAddress() + "and Port number:" + socket.getPort()+"\n Please Type Your message in textField above and press Enter to send to Server";
            		showMessage(msg);      				 // print message on textArea 
            		
            		
                 
            } 
        catch (Exception e) {
                System.out.println("XX. Failed to Connect to the Server at port: " + portNumber);
                System.out.println("    Exception: " + e.toString());        
                return false;
        }
                return true;
    }

	private void display(String msg) {
	
		ta.setText(msg);   									  //  use settext on txtArea
		System.out.println("inside display() ...."+msg);
					
	}

	private void sendUser(ChatUser user) {             // passing in a chatUser Object  passing the chatUser Cass object  to server // os.writeObject(user);s

				try {
		System.out.println("02. -> Sending an chat user object to server..." + user);
		new Client.ListenFromServer().start();							// starts listen fro servr method which id threaded runs independently why u need th use start();
    	os.writeObject(user);           								 // send username object to the server
    	os.flush();

    	
				}  
				catch (Exception e) {
        System.out.println("XX. ah god Exception Occurred on Sending:" +  e.toString());
				}
 
		
	}


   
	// method to send a generic object.
    public void sendMessage(ChatMessage msg) {		  // passing in a ChatMessage Object   send meesgge object message from ChatMessage class // use this.os.writeObject(msn);
    
                try {
                 System.out.println("02. -> Sending an sendMessage object to server..." + msg);         
                 os.writeObject(msg);           							 // send object to the server
                 os.flush();												// make sure o data left in pipes
             	 ta.setText("Message Sent (now  open up a new client and login to see my message i sent you!) ");		   // println in TxtArea mode set the text of txtArea
           //     new Client.ListenFromServer().start();    //  start listening to  server  for a message ONLY used when not using the FleOutputStreams files message.bin and messageUser.bin
             	 
                }  
            catch (Exception e) {
                    System.out.println("XX. ah god Exception Occurred on Sending:" +  e.toString());
                }
             
    }
    
  

    
    public static void showMessage( final String m) throws IOException {     // shows message on textArea
		
		SwingUtilities.invokeLater(
				new Runnable(){
					public void run(){
						System.out.println("inside showMessage in cliient ");
						//Client.ta.setText(m);						
					    	ta.append( m );   	 // appends  messages one after another not like settext
						
					}
				}

				);
    }
		
    
    
    private Object receive() 				//  recieves message from server when called 
    {
                Object o = null;				// need to set up object o and set it to zero;
                try {
                        System.out.println("03. -- About to receive an object...");
                    o = is.readObject();     						// readObject() using o = is.teadObjetct()[  gets obbject fro server
                  //  String msgmic = (String) is.readObject(); 
                    System.out.println("04. <- Object received...");
                } 
            catch (Exception e) {
                    System.out.println("XX. Exception Occurred on Receiving:" + e.toString());
                }
                return o;
    }
    
    class ListenFromServer extends Thread {        // listen to server to send a message always used when sending message from server back to same client that sent message

  		public void run() {
  		       				 // while loop always listening
  				try {
  					System.out.println("hi im listening from client listenfromserver()");
  					String msgmic = (String) is.readObject();     // recieves a string from server 
  					System.out.println("recieved message from server... : "+msgmic);
  					ta.append("\nmessage RECIEVED from Server is :"+msgmic);
  					
  		 
  					  		
  				}
  				catch(IOException e) {
  				//	display("Message Sent and Server has close the connection: " + e);
  					
  				
  				}
  				// can't happen with a String object but need the catch anyhow
  				catch(ClassNotFoundException e2) {
  				}
  			}
  		}
	
  		
 
    
    
    
    public static void main(String[] args) {

		new Client();			// opens up new Client GUI without server connection
	
    }}
