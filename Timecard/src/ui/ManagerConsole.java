package ui;


import client.ValdostaClient;
import data.DBTrial;
import data.Department;
import data.Employee;
import data.timeCalculations;

import java.io.*;
import java.util.ArrayList;
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
				 * 
				 */
				
				public ManagerConsole() {
					
				}
				public ManagerConsole(String host, int port) {
				    try {
				    	client = new ValdostaClient(host, port, this);
				    }
				    catch(IOException exception) {
				    	System.out.println("ERROR - Can't setup connection, terminating client.");
				    	System.exit(1);
				    }
				}
		
		
				public void reportEmp()
				{
					 DBTrial test = new DBTrial();
						ArrayList<Employee> e = test.getEmployees(-1);	
//						timeCalculations time = new timeCalculations();	
						for(int i=0;i<e.size();i++){
							  System.out.println(e.get(i).toString());
						}
//							  System.out.println(emp.toString());					
				}
			
		

	public static void main(String[] args){
		ManagerConsole m = new ManagerConsole();
  		m.reportEmp();
	}
}
