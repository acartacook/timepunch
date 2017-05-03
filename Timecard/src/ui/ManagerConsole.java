package ui;


import client.ValdostaClient;
import data.DBTrial;
import data.Employee;

import java.io.*;
import java.util.Scanner;

import client.*;

public class ManagerConsole {
	
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
				public static String id;
				public static boolean connected;



				// Constructors ****************************************************
				/**
				 * Constructs an instance of the ClientConsole UI.
				 *
				 * @param host The host to connect to.
				 * @param port The port to connect on.
				 */
				public ManagerConsole(String host, int port) {
				    try {
				    	client = new ValdostaClient(host, port, this);
				    }
				    catch(IOException exception) {
				    	System.out.println("ERROR - Can't setup connection, terminating client.");
				    	System.exit(1);
				    }
				}
		
		
				public void display(String message) {
					System.out.println("> " + message);
				}
				
				public void accept() {
					try {
						BufferedReader fromConsole = new BufferedReader(new InputStreamReader(System.in));
						String message;
						client.handleMessageFromClientUI("#connect", "");
						System.out.println("hi");
					}
					catch (Exception ex) {
						System.out.println("ERROR - Unexpected error while reading from console.");
					}
				}		
			


	public static void main(String[] args){
  		String host = "";
  		try {
  			host = args[0];
  		}
  		catch(ArrayIndexOutOfBoundsException e) {
  			host = "localhost";
  		}
  		ManagerConsole chat= new ManagerConsole(host, DEFAULT_PORT);
  		
  		chat.accept();  //Wait for console data
		
	}
}
