package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;
import java.io.*;
import java.util.*;
import java.util.Date;

public class DBTrial {
	
	private  final String url = "jdbc:mysql://localhost:3306/timepunch";
	private final String user = "root";
	private final String password = "root";
	
	private  Connection con = null;
	private  Statement stmt = null;
	private  PreparedStatement pstmt= null;
	private static ResultSet rs;
	private Scanner input = new Scanner(System.in);
//	private ArrayList<String> movieGenre = new ArrayList<String>();
	
	public int findId(int id, String table)
	{
		try
		{
			con = DriverManager.getConnection(url,user,password);
			stmt = con.createStatement();
			
			String sql = "SELECT ID FROM"+ table +" WHERE ID = '"+ id +"'";
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next())
			{
				return rs.getInt("ID");
			}
		}
		catch(SQLException se)
		{
			 se.printStackTrace(); 
		}
		return -1;
	}
	
	public Employee getEmployee(int id)
	{
		try
		{
			con = DriverManager.getConnection(url,user,password);
			stmt = con.createStatement();
			
			String sql = "SELECT * FROM employee WHERE ID = '"+ id +"'";
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next())
			{
				Employee e = new Employee();
				e.setId(rs.getInt("ID"));
				e.setName(rs.getString("FNAME") + " " + rs.getString("LNAME"));
				e.setDeptID(rs.getInt("DEPT_ID"));
				e.setPay(rs.getDouble("PAY"));
				e.setVacationHours(rs.getDouble("VACATION_HOURS"));
				return e;
			}
		}
		catch(SQLException se)
		{
			 se.printStackTrace(); 
		}
		return null;
	}
	
	public Department getDepartment(int id){
		try
		{
			con = DriverManager.getConnection(url,user,password);
			stmt = con.createStatement();
			
			String sql = "SELECT ID FROM employee WHERE ID = '"+ id +"'";
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next())
			{
				Department d = new Department();
				 d.setId(rs.getInt("ID"));
				 d.setManagerID(rs.getInt("MANAGER_ID"));
				 d.setName(rs.getString("NAME"));
				 return d;
			}
		}
		catch(SQLException se)
		{
			 se.printStackTrace(); 
		}
		return null;
	}

	/*
	 * Parameters: Employee id and out timestamp
	 * Does:finds timestamp where out is null and in is set and updates.
	 * 	if not found inserts a new timestamp with out
	 * Returns: Updated: if timestamp is found, inserted: if not found and error: if sql has issues
	 */
	public String setOut(int eid, Date t){
		try
		{
			con = DriverManager.getConnection(url,user,password);
			stmt = con.createStatement();
			
			String sql = "UPDATE Timestamp SET OUT_TIMESTAMP="+ t +"WHERE ID = "
				+"(SELECT ID FROM timestamp WHERE in_timestamp < '"+ t
				+"' AND OUT_TIMESTAMP = '0000-00-00 00:00:00' AND EMPLOYEE_ID = '" +eid+ "')";
			
			rs = stmt.executeQuery(sql);
			
			if (rs.next()){
				return "Updated";
			} else {
				sql = "INSERT INTO Timestamp (`EMPLOYEE_ID`,`OUT_TIMESTAMP`) VALUES ('"+eid+"', '"+ t + "');";
				rs = stmt.executeQuery(sql);
				return "Inserted";
			}
			
		}
		catch(SQLException se)
		{
			 return " Sql Error";
		}
	}

	/*
	 * Parameters: Employee id and in timestamp
	 * Returns: true: if inserted, false: if error
	 */
	public boolean insertIn(int eid, Date in){
		try
		{
			con = DriverManager.getConnection(url,user,password);
			stmt = con.createStatement();
			
			String sql = "INSERT INTO Timestamp (`EMPLOYEE_ID`,`IN_TIMESTAMP`) VALUES ('"+eid+"', '"+ in + "');";
			
			rs = stmt.executeQuery(sql);
			
			return true;
		}
		catch(SQLException se)
		{
			 return false;
		}
	}
	/*
	 * Parameter(s) dept id: -1 if not loking by department
	 * 
	 * Returns: an Arraylist of all employees(by dept if wanted)
	 */
	public ArrayList<Employee> getEmployees(int deptId){
		ArrayList<Employee> e = new ArrayList<Employee>();
		try
		{
			con = DriverManager.getConnection(url,user,password);
			stmt = con.createStatement();
			String sql  = "SELECT * FROM employee";
			if(deptId != -1){
				sql = sql + "WHERE `DEPT_ID` = '" +deptId+ "'";
			}
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next())
			{
				Employee at = new Employee();
				at.setId(rs.getInt("ID"));
				at.setName(rs.getString("FNAME") + " " + rs.getString("LNAME"));
				at.setDeptID(rs.getInt("DEPT_ID"));
				at.setPay(rs.getDouble("PAY"));
				at.setVacationHours(rs.getDouble("VACATION_HOURS"));
				
				e.add(at);
			}
		}
		catch(SQLException se)
		{
			 return null;
		}
		return e;
	}
	public static void main(String[] args)
	{
		DBTrial db = new DBTrial();
		System.out.println(db.findId(1, "employee"));
	}
}