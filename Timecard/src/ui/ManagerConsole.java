package ui;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

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
				String name = str[1];
				Double pay = Double.parseDouble(str[2]);
				Double vacationHours = Double.parseDouble(str[3]);
				int deptID = Integer.parseInt(str[4]);
				//add 1, John Doe, 20.0,40.0,1
				if(addEmployee(name,eId,pay,vacationHours,deptID)){
					System.out.println(name + " was added");
				}
				else{
					System.out.println(name + " was not added");
				}
			}
		}
		s.close();
		
	}

	public static boolean addEmployee(String name, int id, double pay, double vacationHours, int deptID){
		if(findEmployee(id)){
			System.out.println("Employee id already exists");
			return false;
		}
		Employee toAdd = new Employee(name,id,pay,deptID,vacationHours);
		
		ObjectOutputStream oos = null;
		FileOutputStream fout = null;
		try{
		    fout = new FileOutputStream("src/employee.ser", true);
		    oos = new ObjectOutputStream(fout);
		    oos.writeObject(toAdd);
		    return true;
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
		
		return false;
	}
	
	private static boolean findEmployee(int eId) {
		// TODO Auto-generated method stub
		ObjectInputStream objectinputstream = null;
		try {
			FileInputStream streamIn = new FileInputStream("src/employee.ser");
		    objectinputstream = new ObjectInputStream(streamIn);
		    Object o = new Object();
		    while((o = objectinputstream.readObject()) != null){
		    	Employee e = new Employee();
		    	e = (Employee) o;
		    	if(e.getId() == eId){
		    		return true;
		    	}
		    }
		} catch (Exception e) {
			
		} finally {
		    if(objectinputstream != null){
		        try {
					objectinputstream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    } 
		}
		return false;
	}
}
