package ui;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

import data.DBTrial;
import data.Employee;

public class ManagerConsole {

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
