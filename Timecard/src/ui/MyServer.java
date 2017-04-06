package ui;

import java.io.*;
//import java.sql.*;
import java.sql.Date;

import data.DBTrial;
import data.Employee;
import java.sql.Timestamp;
import ocsf.server.*;


public class MyServer extends AbstractServer {
	
	/*private final String url = "jdbc:mysql://localhost:3306/timepunch";
	private final String user = "root";
	private final String password = "root";
	
	private  Connection con = null;
	private  Statement stmt = null;
	private  PreparedStatement pstmt= null;*/
	
	// Class variables *************************************************
		/**
	   	  The default port to listen on.
		 */
		final public static int DEFAULT_PORT = 5555;

		// Constructors ****************************************************

		/**
		  Constructs an instance of the echo server.

		  @param port The port number to connect on.
		 */
		public MyServer(int port) {
			super(port);
		}

		// Instance methods ************************************************

		/**
	      This method handles any messages received from the client.

	      @param msg The message received from the client.
		  @param client The connection from which the message originated.
		 */
		@SuppressWarnings("null")
		public void handleMessageFromClient (Object msg, ConnectionToClient client) {
			//ResultSet rs;
			
			// Check to see if msg is a String
			if(msg instanceof String) {
				System.out.println(msg);
				//clientmsg is the message picked up by the client change to fit what is needed by ui
				String clientMsg = null;
				String command = (String)msg;
				ObjectOutputStream oos = null;
				FileOutputStream fout = null;
				
				if(command.equals("#close")) {
					try {
						client.close();
					}
					catch (IOException e) {
						e.printStackTrace();
					}
				}

				if(command.startsWith("#login")) {
					String data = command.substring(7);
					int eId = Integer.parseInt(data);
					DBTrial d = new DBTrial();
					if(d.findId(eId, "employee") != -1){
						clientMsg = "Logged in";
					}
				}
//This command should insert data into the database. The timestamp database.				
				else if(command.startsWith("#IN"))
				{
					
					String data = command.substring(4);
					String [] strNum = data.split(",");
					int eId = Integer.parseInt(strNum[0]);
					Timestamp timestamp = new Timestamp(Long.parseLong(strNum[1]));
					String type = "REG";
					if(strNum.length == 3){
						type = strNum[2];
					}


					try{
						DBTrial d = new DBTrial();
						clientMsg = d.insertIn(eId, timestamp,type);
					} catch (Exception ex) {
					    ex.printStackTrace();
					} finally {
					    if(oos != null){
					        try {
								oos.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					    } 
					}
						
				}
					/*try {
						con = DriverManager.getConnection(url,user,password);
						String sql = "INSERT INTO timestamp (EMPLOYEE_ID, IN_TIMESTAMP) Values (?,?)" ;
						pstmt = con.prepareStatement(sql);
						
						pstmt.setInt(1, eId);
						pstmt.setString(2,timestamp);
						pstmt.executeUpdate();	
						clientMsg = "Clocked in";
					}
					catch (SQLException e ) {
						clientMsg = "Error";
						e.printStackTrace();
					}*/
				//This command should insert data into the database. The timestamp database.								
				else if(command.startsWith("#OUT"))
				{
					String data = command.substring(5);
					System.out.println(data);
					String [] strNum = data.split(",");
					int eId = Integer.parseInt(strNum[0]);
					Timestamp out = new Timestamp(Long.parseLong(strNum[1]));

					try{
						DBTrial d = new DBTrial();
						clientMsg = d.setOut(eId, out);
					} catch (Exception ex) {
					    ex.printStackTrace();
					}
				}
				else if(command.startsWith("#PRINTTIMESTAMPS"))
				{
					//Timestamp t = new Timestamp();
					//t.printAllTimestamps();
				}
	  	  		try {
					client.sendToClient(clientMsg);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	  		}
			
			// Else, message is not recognized!
			else {
				System.out.println("Unrecognized message received: " + msg + " from " + client);
			}
	  	}

		/**
		 *  Hook method to inform client of successful connection
		 *  @throws IOException 
		 */
		protected void clientConnected(ConnectionToClient client) {
			String msg = "Successful connection for client: " + client.getInetAddress();
			System.out.println(msg);
			try {
				client.sendToClient(msg);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			//this.sendToAllClients(msg);
		}

		/**
		 *  Hook method to illustrate server's knowledge of client disconnecting,
		 *  turns out, client is already null at this point.
		 */
		@Override
		synchronized protected void clientDisconnected(ConnectionToClient client) {
			String msg = "Client: " + client.getInetAddress() + " has disconnected";
			System.out.println(msg);
		}

		/**
		 *  Hook method to illustrate server's knowledge of client quitting or
		 *  client abnormally terminating, e.g Ctrl+c from command prompt.
		 *  turns out, client is already null at this point.
		 */
		@Override
	 	synchronized protected void clientException(ConnectionToClient client, Throwable exception) {
			String msg = "Client Exception";
			System.out.println(exception);
		}

		/**
		 * Hook method to inform server console of successful start.
		 */
		protected void serverStarted() {
			System.out.println("Server listening for connections on port " + getPort());
		}

		/**
		 * Hook method to inform server console that server is no longer listening
		 * for connections.
		 */
		protected void serverStopped() {
			System.out.println("Server has stopped listening for connections.");
		}

		// Class methods ***************************************************

		/**
		 * This method is responsible for the creation of the server.
	     *
	     * @param args[0] The port number to listen on.  Defaults to 5555 if no argument is entered.
	     */
		public static void main(String[] args) {
	  		int port = 0; // Port to listen on

	  		try {
	  			port = Integer.parseInt(args[0]); // Get port from command line
	  		}
	  		catch(Throwable t) {
	  			port = DEFAULT_PORT; // Set port to 5555
	  		}

	  		MyServer sv = new MyServer(port);

	  		try {
	  			sv.listen(); // Start listening for connections
	  		}
	  		catch (Exception ex) {
	  			System.out.println(ex);
	  		}
	  	}

}
