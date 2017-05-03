package data;

import java.sql.Timestamp;
import java.util.ArrayList;

import data.DBTrial;

/*
  Desired output
  Employee Added
  Department Added

*/
public class DBTest{
	public static void main(String[] args){
	  DBTrial test = new DBTrial();
	/*
	  String addEmployee = test.addEmployee("April", "May", 21.00, 40.00, 104);
	  System.out.println("Employee " + addEmployee);
	
	  String addDepartment = test.addDepartment("Office Test", "Adel", "Indirect", 1000);
	  System.out.println("Department " + addDepartment);
	
	  System.out.println(test.getEmployee(1000).toString());
	
	  System.out.println(test.getDepartment(101).toString());
	*/
	  ArrayList<Employee> e = test.getEmployees(-1);
	
	  for(Employee emp : e){
	    System.out.println(emp.toString());
	  }
	
	  ArrayList<Employee> e2 = test.getEmployees(101);
	
	  for(Employee emp2 : e2){
	    System.out.println(emp2.toString());
	  }
	/*
	  ArrayList<Department> d = test.getDepartments();
	  
	  for(Department dept: d){
		  System.out.println(dept.toString());
	  }*/
	  
	 // System.out.println(test.setOut(1001, new Timestamp(System.currentTimeMillis())));
	}
}
