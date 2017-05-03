package data;

import java.util.ArrayList;

/*
  Desired output
  Employee Added
  Department Added

*/
public class DBTest{
	static DBTrial test = new DBTrial();
	public static void main(String[] args){

	/*
	  String addEmployee = test.addEmployee("April", "May", 21.00, 40.00, 104);
	  System.out.println("Employee " + addEmployee);
	
	  String addDepartment = test.addDepartment("Office Test", "Adel", "Indirect", 1000);
	  System.out.println("Department " + addDepartment);
	
	  System.out.println(test.getEmployee(1000).toString());
	
	  System.out.println(test.getDepartment(101).toString());
	*/
	//Use this for emp


		getEmployees();
//	  ArrayList<Department> d = test.getDepartments();
	  
//	  for(Department dept: d){
//		  System.out.println(dept.toString());
//	  }
	  

	  //System.out.println(test.setOut(1001, new Timestamp(System.currentTimeMillis())));
	  
//	 test.getReportEmp();
	}
	
	public static void getEmployees(){
		ArrayList<Employee> e = test.getEmployees(-1);
			
		for(int i=0;i<e.size();i++){
		  System.out.println(e.get(i).toString());
		}
		System.out.println("\n");
		  ArrayList<Employee> e2 = test.getEmployees(100);
		
		for(int i=0;i<e2.size();i++){
			    System.out.println(e2.get(i).toString());
		}

	}
}
