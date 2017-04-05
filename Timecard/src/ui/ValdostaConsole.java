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

		// Instance variables **********************************************
		/**
		 * The instance of the client that created this ConsoleChat.
		 */
		ValdostaClient client;

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
		 */
		public void accept() {
			try {
				BufferedReader fromConsole = new BufferedReader(new InputStreamReader(System.in));
				String message;

				while (true) {
					message = fromConsole.readLine();
					client.handleMessageFromClientUI(message);
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
	  		chat.accept();  //Wait for console data
	  	}
	
}
