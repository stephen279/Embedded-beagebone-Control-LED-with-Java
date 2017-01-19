import java.io.*;


/*
 This class defines the different type of messages that will be exchanged between the
 Clients and the Server. 
 */
public class ChatMessage implements Serializable {         // use off serialization for passing an object e.g chatMessage@8484839 

	protected static final long serialVersionUID = 1112122200L;

	// The different types of message sent by the Client
	static final int getMessage = 0, MESSAGE = 1;
	private int type;
	public String message;

	// constructor
	@SuppressWarnings("static-access")
	ChatMessage(int type, String message) {
		System.out.println("inside ChatMessage Constructor , object passed in is "+ message);
		this.type = type;
		this.message = message;
	}

	// getters
	int getType() {
		return type;
	}

	String getMessage() {

		System.out.println("inside getMessage" + message);
		return message;
	}
}
