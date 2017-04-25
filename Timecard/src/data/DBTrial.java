package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.sql.*;
import java.io.*;
import java.util.*;
import java.sql.Date;

public class DBTrial {

	private  final String url = "jdbc:mysql://localhost:3306/timepunch";
	private final String user = "root";
	private final String password = "root";

	private  Connection con = null;
	private  Statement stmt = null;
	private  PreparedStatement pstmt= null;
	private static ResultSet rs;
	private Scanner input = new Scanner(System.in);

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
	/*
	 * Parameters: employee id
	 * Returns: Employee Object
	 * Use: for login
	 */
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
				e.setFirstName(rs.getString("FNAME"));
				e.setLastName(rs.getString("LNAME"));
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
	/*
	 * Parameters: department id
	 * Returns: department object
	 */
	public Department getDepartment(int id){
		try
		{
			con = DriverManager.getConnection(url,user,password);
			stmt = con.createStatement();

			String sql = "SELECT * FROM department WHERE ID = '"+ id +"'";

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
	 * Parameters: Employee id and in timestamp
	 * Returns: timestamps object where eid and timestamp = specified
	 * FOR: updateTimestamp
	 */
	public Timestamps getTimeStamp(int eId,Timestamp in){
		Timestamps t = new Timestamps();
		try {
			con = DriverManager.getConnection(url,user,password);

			stmt = con.createStatement();

			String sql = "SELECT ID FROM timestamp WHERE EMPLOYEE_ID = '"+ eId +"' AND IN_TIMESTAMP = '"+ eId +"'";

			rs = stmt.executeQuery(sql);

			rs.next();
			t.setID(rs.getInt("ID"));
			t.setEmployeeID(rs.getInt("EMPLOYEE_ID"));


			return t;

		} catch (SQLException e) {
			return t;
		}
	}
	/*
	 * Parameters: Employee id and out timestamp
	 * Returns: timestamps object where eid and timestamp = specified
	 * FOR: updateTimestamp
	 */
	public Timestamps getOutTimeStamp(int eId,Timestamp out){
		Timestamps t = new Timestamps();
		try {
			con = DriverManager.getConnection(url,user,password);

			stmt = con.createStatement();

			String sql = "SELECT ID FROM timestamp WHERE EMPLOYEE_ID = '"+ eId +"' AND OUT_TIMESTAMP = '"+ out +"'";

			rs = stmt.executeQuery(sql);

			rs.next();
			t.setID(rs.getInt("ID"));
			t.setEmployeeID(rs.getInt("EMPLOYEE_ID"));


			return t;

		} catch (SQLException e) {
			return t;
		}
	}

	/*
	 * Parameters: Employee id and out timestamp
	 * Does:finds timestamp where out is null and in is set and updates.
	 * 	if not found inserts a new timestamp with out
	 * Returns: Updated: if timestamp is found, inserted: if not found and error: if sql has issues
	 */
	public String setOut(int eid, Timestamp t){
		try
		{
			con = DriverManager.getConnection(url,user,password);
			stmt = con.createStatement();

			String sql = "SELECT ID FROM timestamp WHERE OUT_TIMESTAMP = '1970-01-01 00:00:01' AND EMPLOYEE_ID = '" +eid+ "'"
					+ "AND (datediff('"+t+"',IN_TIMESTAMP) = 0) AND IN_TIMESTAMP < '"+ t +"';";
			rs = stmt.executeQuery(sql);

			if (rs.next()){
				int id = rs.getInt("ID");
				sql = "UPDATE timestamp SET OUT_TIMESTAMP = ? WHERE ID = ?";
				pstmt = con.prepareStatement(sql);

				pstmt.setTimestamp(1, t);
				pstmt.setInt(2,id);

				pstmt.executeUpdate();

				return "Updated";
			} else {
				sql = "INSERT INTO timestamp (`EMPLOYEE_ID`,`OUT_TIMESTAMP`) VALUES (?, ?);";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1,eid);
				pstmt.setTimestamp(2, t);

				pstmt.executeUpdate();

				return "Inserted";
			}

		}
		catch(SQLException se)
		{
			System.out.println(se);
			return " Sql Error";
		}
	}
	/*
	 * Parameters: Type and/or eId
	 * Returns: ArrayList of total hours for each timestamp of specified type and/or from specific employee
	 * FOR: timeCalculations.java
	 */
	public ArrayList<Timestamp> getTimediffs(String type, int eId){
		ArrayList<Timestamp> retArray = new ArrayList<Timestamp>();
		try {
			con = DriverManager.getConnection(url,user,password);

			stmt = con.createStatement();

			String sql = "SELECT TIMEDIFF(OUT_TIMESTAMP,IN_TIMESTAMP) AS diff FROM timestamp";

			if(type != ""){
				sql += " WHERE HOUR_TYPE = '" + type +"'";
			}
			if(eId != -1){
				if(type == ""){
					sql += "WHERE EMPLOYEE_ID = '" + eId + "'";
				} else {
					sql += "AND EMPLOYEE_ID = '" + eId + "'";
				}
			}

			rs = stmt.executeQuery(sql);

			while(rs.next()){
				retArray.add(rs.getTimestamp("diff"));
			}
		} catch (SQLException e) {
			return null;
		}
		return null;
	}

	/*
	 * Parameters: timestamp id and out timestamp and String Hour type
	 * Returns: Updated: if timestamp is found and updated
	 * Use in conjunction with getTimestamp and getOutTimeStamp
	 */
	public String updateTimestamp(int tId,Timestamp in,Timestamp out, String type){
		try
		{
			con = DriverManager.getConnection(url,user,password);
			stmt = con.createStatement();

			String sql = "UPDATE timestamp SET IN_TIMESTAMP = ?, OUT_TIMESTAMP = ?, HOUR_TYPE = ? WHERE ID = ?";
			pstmt = con.prepareStatement(sql);

			pstmt.setTimestamp(1, in);
			pstmt.setTimestamp(2, out);
			pstmt.setString(3, type);
			pstmt.setInt(4,tId);

			pstmt.executeUpdate();

			return "Updated";
		}
		catch(SQLException se)
		{
			System.out.println(se);
			return " Sql Error";
		}
	}
	/*
	 * Parameters: Employee id and in timestamp and type
	 * Returns: clocked in: if inserted, error: if error
	 */
	public String insertIn(int eid, Timestamp timestamp, String type){
		try
		{
			con = DriverManager.getConnection(url,user,password);
			stmt = con.createStatement();

			String sql = "INSERT INTO timestamp (`EMPLOYEE_ID`,`IN_TIMESTAMP`,`HOUR_TYPE`) VALUES (?, ?, ?);";

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, eid);
			pstmt.setTimestamp(2, timestamp);
			pstmt.setString(3, type);
			pstmt.executeUpdate();

			return "Clocked in";
		}
		catch(SQLException se)
		{
			System.out.println(se);
			 return "error";
		}
	}
	/*
	 * Parameter(s) dept id: -1 if not looking by department
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
				at.setFirstName(rs.getString("FNAME") + " ");
				at.setLastName(rs.getString("LNAME"));
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
	/*
	 * Parameters: X
	 * Returns: list of all departments
	 */
	public ArrayList<Department> getDepartments(){
		ArrayList<Department> d = new ArrayList<Department>();
		try
		{
			con = DriverManager.getConnection(url,user,password);
			stmt = con.createStatement();
			String sql  = "SELECT * FROM department";

			rs = stmt.executeQuery(sql);

			while(rs.next())
			{
				Department at = new Department();
				at.setId(rs.getInt("ID"));
				at.setManagerID(rs.getInt("MANAGER_ID"));
				at.setName(rs.getString("NAME"));

				d.add(at);
			}
		}
		catch(SQLException se)
		{
			 return null;
		}
		return d;
	}
	/*
	 * Parameters: everything you need for employee
	 * Returns: "Added" or "error"
	 */
	public String addEmployee(String first_name,String last_name, double pay, double vacationHours, int deptId){
		try
		{
			con = DriverManager.getConnection(url,user,password);
			stmt = con.createStatement();

			String sql = "INSERT INTO employee (`FNAME`,`LNAME`,`PAY`,`VACATION_HOUR`,`DEPT_ID`) VALUES (?, ?, ?, ?, ?);";

			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, first_name);
			pstmt.setString(2, last_name);
			pstmt.setDouble(3, pay);
			pstmt.setDouble(4, vacationHours);
			pstmt.setInt(5, deptId);
			pstmt.executeUpdate();

			return "Added";
		}
		catch(SQLException se)
		{
			System.out.println(se);
			 return "error";
		}
	}
	/*
	 * Parameters: everything you need for department, class is either indirect or production
	 * Returns: "Added" or "error"
	 */
	public String addDepartment(String name, String location, String class, int manager_id){
		try
		{
			con = DriverManager.getConnection(url,user,password);
			stmt = con.createStatement();

			String sql = "INSERT INTO department (`NAME`,`LOCATION`,`CLASS`,`MANAGER_ID`) VALUES (?, ?, ?, ?);";

			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, name);
			pstmt.setString(2, location);
			pstmt.setString(3, class);
			pstmt.setInt(4, manager_id);
			pstmt.executeUpdate();

			return "Added";
		}
		catch(SQLException se)
		{
			System.out.println(se);
			 return "error";
		}
	}
	/*
	 * Parameters: everything you need for timestamp
	 * Returns: "Added" or "error"
	 * Use: for manager to add new timestamp
	 */
	public String addTimestamp(int eid, Timestamp in, Timestamp out, String type){
		try
		{
			con = DriverManager.getConnection(url,user,password);
			stmt = con.createStatement();

			String sql = "INSERT INTO timestamp (`EMPLOYEE_ID`,`IN_TIMESTAMP`,`OUT_TIMESTAMP`,`HOUR_TYPE`) VALUES (?, ?, ?, ?);";

			pstmt = con.prepareStatement(sql);

			pstmt.setInt(1, eid);
			pstmt.setTimestamp(2, in);
			pstmt.setTimestamp(3, out);
			pstmt.setString(4, type);
			pstmt.executeUpdate();

			return "Added";
		}
		catch(SQLException se)
		{
			System.out.println(se);
			 return "error";
		}
	}
	public static void main(String[] args)
	{
		DBTrial db = new DBTrial();
		System.out.println(db.findId(1, "employee"));
	}

}
