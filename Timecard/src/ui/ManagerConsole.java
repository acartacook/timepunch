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
						client.handleMessageFromClientUI("#connect", id);
						message= "#";
						//keeps looping if message does not contain digits
						while(!message.matches("^\\d+$")){
							System.out.println("Need Login");
							message = fromConsole.readLine();
						}
						client.handleMessageFromClientUI("#login " + message, id);
						id = message;
						while (true) {
							message = fromConsole.readLine();
							client.handleMessageFromClientUI(message,id);
						}
					}
					catch (Exception ex) {
						System.out.println("ERROR - Unexpected error while reading from console.");
					}
				}		
			


	public static void main(String[] args) {
				

		Scanner s = new Scanner(System.in);
		String input = "";
		while(input != "close"){
			input = s.nextLine();
			
			if(input.startsWith("add")){
				String data = input.substring(4);
				String [] str = data.split(",");
				int eId = Integer.parseInt(str[0]);
				String fname = str[1];
				String lname = str[2];
				Double pay = Double.parseDouble(str[3]);
				Double vacationHours = Double.parseDouble(str[4]);
				int deptID = Integer.parseInt(str[5]);
				//add 1, John Doe, 20.0,40.0,1
				DBTrial d = new DBTrial();
				if(d.addEmployee(fname,lname,pay,vacationHours,deptID) != ""){
					System.out.println(fname + " was added");
				}
				else{
					System.out.println(fname + " was not added");
				}
			}
		}
		s.close();
		
	}
}
