import java.io.Serializable;         // use off serialization
/*
  This class defines the different type of Usernames that will be exchanged between the
  Clients and the Server. 
 */
public class ChatUser implements Serializable {          // use off serialization   // use off serialization for passing an object e.g chatUser@8484839       

	protected static final long serialVersionUID = 1112122200L;


	static final int getUser = 0, USER = 1;
	private int type;
	public  String user ;
	
	// constructor
	@SuppressWarnings("static-access")
	ChatUser(int type, String user) {
		System.out.println("inside ChatUser Constructor , object passed in is "+ user);
		this.type = type;
		this.user = user;
	}
	
	// getters
	int getType() {
		return type;
	}
	 String getUser() {
		
		System.out.println("inside getUser"+user);
		return user;
	}
}

