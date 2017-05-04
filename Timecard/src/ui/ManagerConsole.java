package ui;


import client.ValdostaClient;
import data.DBTrial;
import data.Department;
import data.Employee;
import data.Timestamps;
import data.timeCalculations;

import java.io.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
						ArrayList<Employee> e = DBTrial.getEmployees(-1);	
						System.out.println("Total Hours | Total Hours Worked | Total Regular Hours | Overtime Hours | Callback Hours Worked | Callback Hours NOT Worked |"
								+ "Callback Hours Considered OT | Vacation Hours | Holiday Hours | Jury Duty Hours | Bereavement |\n");
						for(int i=0;i<e.size();i++){
							  System.out.println(e.get(i).toString() + timeCalculations.getTotal(e.get(i).getId()));
						}
//							  System.out.println(emp.toString());					
				}
			
		

	public static void main(String[] args){
		ManagerConsole m = new ManagerConsole();
		Scanner in = new Scanner(System.in);
		String prompt = "\nEnter 1 for Employee report\n";
		prompt += "Enter 2 and dept id for Department Full report\n";
		prompt += "Enter 3 for Department brief report\n";
		prompt += "Enter 4 to update timestamps\n";
		System.out.print(prompt);
		while(in.hasNext()){
			int toCheck = in.nextInt();
			if(toCheck == 1){
		  		m.reportEmp();
				System.out.print(prompt);
			} else if(toCheck == 2){
					ArrayList<Department> d = DBTrial.getDepartments();
					if(d.size() !=0){
						for(Department dept: d){
							System.out.println("\n"+dept.toString() + "\n");
							
							ArrayList <Employee> e = DBTrial.getEmployees(dept.getId());
							for(Employee emp:e){
								System.out.println(emp.toString());
							}
						}
					}
				System.out.print(prompt);
			} else if(toCheck == 3){
				ArrayList<Department> d = DBTrial.getDepartments();
				for(Department dept: d){
					System.out.println(dept.toString());
				}
				System.out.print(prompt);
			} else if(toCheck == 4){
				m.reportEmp();
				System.out.println("Enter employee Id to check");
				int id = in.nextInt();
				ArrayList<Timestamps> t = DBTrial.getTimestamps(id);
				
				for(Timestamps temp: t){
					System.out.println(temp.toString());
				}
				System.out.println("Enter Timestamp Id to update");
				id = in.nextInt();
				Timestamps time = DBTrial.getTimeStamp(id);
				System.out.println("to update type: 1 in :2 out:3");
				int checking = in.nextInt();
				String ret = "";
				if(checking == 1){
					ret = DBTrial.updateTimestamp(id, time.getIn(), time.getOut(), in.next());
				} else if(checking == 2){
					System.out.println("Enter time to update as month day year hour minute");
					int month = in.nextInt();
					int day = in.nextInt();
					int year = in.nextInt();
					int hour = in.nextInt();
					int minute = in.nextInt();
					@SuppressWarnings("deprecation")
				    Calendar cal = new GregorianCalendar();
				    cal.set(Calendar.YEAR, year);
				    cal.set(Calendar.MONTH, month - 1);
				    cal.set(Calendar.DATE, day);
				    cal.set(Calendar.HOUR_OF_DAY, hour);
				    cal.set(Calendar.MINUTE, minute);
				    cal.set(Calendar.SECOND, 0);
				    cal.set(Calendar.MILLISECOND, 0);
					Timestamp toUpdate = new Timestamp(cal.getTimeInMillis());
					ret = DBTrial.updateTimestamp(id, toUpdate, time.getOut(), time.getType());
				}else if(checking == 3){
					System.out.println("Enter time to update as month day year hour minute");
					int month = in.nextInt();
					int day = in.nextInt();
					int year = in.nextInt();
					int hour = in.nextInt();
					int minute = in.nextInt();
					@SuppressWarnings("deprecation")
				    Calendar cal = new GregorianCalendar();
				    cal.set(Calendar.YEAR, year);
				    cal.set(Calendar.MONTH, month - 1);
				    cal.set(Calendar.DATE, day);
				    cal.set(Calendar.HOUR_OF_DAY, hour);
				    cal.set(Calendar.MINUTE, minute);
				    cal.set(Calendar.SECOND, 0);
				    cal.set(Calendar.MILLISECOND, 0);
					Timestamp toUpdate = new Timestamp(cal.getTimeInMillis());
					ret = DBTrial.updateTimestamp(time.getID(), time.getIn(), toUpdate, time.getType());
				}
				System.out.println(ret);
				System.out.println(prompt);
			} else if(toCheck == -1){
				
				break;
			}
		}
		in.close();
	}
}
