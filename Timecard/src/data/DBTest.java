package data;

import data.DBTrial;

/*
  Desired output
  Employee Added
  Department Added

*/
public static void main(String[] args){
  DBTrial test = new DBTrial();

  String addEmployee = test.addEmployee("April", "May", 21.00, 40.00, 104);
  System.out.println("Employee " + addEmployee);

  String addDepartment = test.addDepartment("Office Test", "Adel", "Indirect", "1000");
  System.out.println("Department " + addDepartment);

  System.out.println(test.getEmployee(1000).toString());

  System.out.println(test.getDepartment(100).toString());

  ArrayList<Employee> e = test.getEmployees();

  for(Employee emp in e){
    System.out.println(emp.toString());
  }

  ArrayList<Employee> e2 = test.getEmployees(1000);

  for(Employee emp in e2){
    System.out.println(emp.toString());
  }

}
