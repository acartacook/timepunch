package client;

import ui.ManagerConsole;
import ui.ValdostaConsole;
import data.Employee;
import ocsf.client.AbstractClient;

import java.io.IOException;
import java.sql.Timestamp;
//This is my test
public class ValdostaClient extends AbstractClient {
	
	// Instance variables **********************************************
		/**
		 * Reference to the client UI
		 */
		ValdostaConsole clientUI;
		ManagerConsole clientUI2;
		

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
			clientUI.display("Welcome Login now with id");
			//openConnection();
		}
		
		public ValdostaClient(String host, int port, ManagerConsole clientUI2) throws IOException {
			super(host, port);
			this.clientUI2 = clientUI2;
			clientUI2.display("Welcome login now with id");
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
		public String handleMessageFromClientUI(String message, String id) throws InterruptedException {
			// See if client wants to connect
			if(message.equals("#connect")) {
				if(!isConnected()){
			      try {
			        this.openConnection();
					clientUI.display("Connection open.");
			      }
			      catch(IOException e) {
			    	  clientUI.display("ERROR - Cannot establish connection with server" );
			    	  return "";
			      }
				}
				return "";
			}
			else if(message.equals("#IN")){
				if(this.isConnected()){
					try {
						sendToServer("#IN " + id +"," + System.currentTimeMillis());
					} catch (IOException e) {
						return "error";
					}
				} 
				else {
					//print to textfile and try to reconnect
				}
			}
			else if(message.equals("#cb")){
				if(this.isConnected()){
					try {
						sendToServer("#IN " + id +"," + System.currentTimeMillis() + ",CB");
					} catch (IOException e) {
						return "error";
					}
				} 
				else {
					//print to textfile and try to reconnect
				}
			}
			else if(message.equals("#OUT")){
				if(this.isConnected()){
					try {
						sendToServer("#OUT " + id +"," + System.currentTimeMillis());
					} catch (IOException e) {
						return "error";
					}
				} 
				else {
					//print to textfile
					try {
				        this.openConnection();
				     }
				     catch(IOException e) {
				    	  //save to txt file here
				    	  return "No connection saved locally";
				     }
					try {
						sendToServer("#OUT " + id +"," + System.currentTimeMillis());
					} catch (IOException e) {
						return "error";
					}
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
					return "ERROR - Could not send message to server";
				}
			}
			return "";
		}
		
		
}


