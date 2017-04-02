package client;

import ui.ValdostaConsole;
import data.Employee;
import ocsf.client.AbstractClient;

import java.io.IOException;

public class ValdostaClient extends AbstractClient {
	
	// Instance variables **********************************************
		/**
		 * Reference to the client UI
		 */
		ValdostaConsole clientUI;

		// Constructors ****************************************************
		/**
		 * Constructs an instance of the client.
		 *
		 * @param host The server to connect to.
		 * @param port The port number to connect on.
		 * @param clientUI The interface type variable.
		 */
		public ValdostaClient(String host, int port, ValdostaConsole clientUI) throws IOException {
			super(host, port);
			this.clientUI = clientUI;
			clientUI.display("Type '#connect' to connect to server");
			//openConnection();
		}

		// Instance methods ************************************************

		/**
		 * This method handles all data that comes in from the server.
		 *
		 * @param msg The message from the server.
		 */
		public void handleMessageFromServer(Object msg) {
			// See if server sent a BlobManager
			
				clientUI.display("Message from server:" + msg.toString() + "\n");
		}

		/**
		 * This method handles all data coming from the UI
		 *
		 * @param message The message from the UI.
		 * @throws InterruptedException
		 */
		public String handleMessageFromClientUI(String message) throws InterruptedException {
			// See if client wants to connect
			if(message.equals("#connect")) {
			      try {
			        this.openConnection();
					clientUI.display("Connection open.");
			      }
			      catch(IOException e) {
			    	  clientUI.display("ERROR - Cannot establish connection with server" );
			    	  return "";
			      }
			}
			// Close the connect, but don't quit.
			else if(message.equals("#close")) {
				try {
			        //closeConnection();
			        sendToServer(message);
					clientUI.display("Connection closed.");
				}
				catch(IOException e) {
			        clientUI.display("ERROR - Cannot close connection.");
				}
			}
			// See if client is connected to server.
			else if(message.equals("#isconnected")) {
				clientUI.display("Connection to server=" + this.isConnected());
			}
			else{
				try {
					sendToServer(message);
					return "Message sent to server";
				}
				catch(IOException e) {
					return "ERROR - Could not send BlobManager to server";
				}
			}
			return "";
		}
	

}
