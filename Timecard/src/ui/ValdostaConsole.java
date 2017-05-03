package ui;

import java.io.*;

import client.*;
//this is a test
public class ValdostaConsole {

	// Class variables *************************************************
		/**
		 * The default port to connect on.
		 */
		final public static int DEFAULT_PORT = 5555;
		
		public static boolean connected;
		
		public static String id;
		public static loginGUI l = new loginGUI();
		

		// Instance variables **********************************************
		/**
		 * The instance of the client that created this ConsoleChat.
		 */
		static ValdostaClient client;
		

		// Constructors ****************************************************
		/**
		 * Constructs an instance of the ClientConsole UI.
		 *
		 * @param host The host to connect to.
		 * @param port The port to connect on.
		 */
		public ValdostaConsole(String host, int port) {
		    try {
		    	client = new ValdostaClient(host, port, this);
		    }
		    catch(IOException exception) {
		    	System.out.println("ERROR - Can't setup connection, terminating client.");
		    	System.exit(1);
		    }
		}

		// Instance methods ************************************************
		/**
		 * This method waits for input from the console.  Once it is
		 * received, it sends it to the client's message handler.
		 * @param l 
		 */
		public static void accept(String message, String type) {
			try {
				//BufferedReader fromConsole = new BufferedReader(new InputStreamReader(System.in));
				
				client.handleMessageFromClientUI("#connect", id);
				//message= "#";
				//keeps looping if message does not contain digits 
				if(message.matches("^\\d+$")){
					client.handleMessageFromClientUI("#login " + message, id);
					id = message;
				} else if(type != ""){
					client.handleMessageFromClientUI("#cb", id);
				}
				else {
					client.handleMessageFromClientUI(message, id);
				}
			}
			catch (Exception ex) {
				System.out.println("ERROR - Unexpected error while reading from console.");
			}
		}

		/**
		 * This method displays a message onto the screen.
		 *
		 * @param message The string to be displayed.
		 */
		public void display(String message) {
			System.out.println("> " + message);
			
			if (message.contains("Message from server:Logged in")){
				l.createClockInPopup();
				
				
			}
		}

		// Class methods ***************************************************
		/**
		 * This method is responsible for the creation of the Client UI.
		 *
		 * @param args[0] The host to connect to.
		 */
	  	public static void main(String[] args) {
	  		String host = "";
	  		try {
	  			host = args[0];
	  		}
	  		catch(ArrayIndexOutOfBoundsException e) {
	  			host = "localhost";
	  		}
	  		ValdostaConsole chat= new ValdostaConsole(host, DEFAULT_PORT);

	  		l.createLoginPopup();
	  		
	  		
	  		
	  		//chat.accept();  //Wait for console data
	  	}
	
}
