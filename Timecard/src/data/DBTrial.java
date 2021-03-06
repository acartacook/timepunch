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

	private  final static String url = "jdbc:mysql://localhost:3306/timepunch";
	private final static String user = "root";
	private final static String password = "root";

	private  static Connection con = null;
	private  static Statement stmt = null;
	private static PreparedStatement pstmt= null;
	private static ResultSet rs;
	private Scanner input = new Scanner(System.in);

	public int findId(int id, String table)
	{
		try
		{
			try {
			    Class.forName("com.mysql.jdbc.Driver");
			} 
			catch (ClassNotFoundException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}
			con = DriverManager.getConnection(url,user,password);
			stmt = con.createStatement();

			String sql = "SELECT ID FROM "+ table +" WHERE ID = '"+ id +"'";

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

			try {
			    Class.forName("com.mysql.jdbc.Driver");
			} 
			catch (ClassNotFoundException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}
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
				e.setPay(rs.getDouble("HOURLY_PAY"));
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
	public static Department getDepartment(int id){
		try
		{

			try {
			    Class.forName("com.mysql.jdbc.Driver");
			} 
			catch (ClassNotFoundException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}
			con = DriverManager.getConnection(url,user,password);
			stmt = con.createStatement();

			String sql = "SELECT * FROM department WHERE ID = "+ id;

			rs = stmt.executeQuery(sql);

			while(rs.next())
			{
				Department d = new Department();
				 d.setId(rs.getInt("ID"));
				 d.setManagerID(rs.getInt("MANAGER_ID"));
				 d.setName(rs.getString("NAME"));
				 d.setclassy(rs.getString("CLASS"));
				 return d;
			}
		}
		catch(SQLException se)
		{
			 se.printStackTrace();
		}
		return null;
	}
	public ArrayList<Department> getManDept(int eid){
		try
		{

			try {
			    Class.forName("com.mysql.jdbc.Driver");
			} 
			catch (ClassNotFoundException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}
			con = DriverManager.getConnection(url,user,password);
			stmt = con.createStatement();

			String sql = "SELECT * FROM department WHERE MANAGER_ID = '"+ eid +"'";

			rs = stmt.executeQuery(sql);
			ArrayList<Department> depts = new ArrayList<Department>();
			while(rs.next())
			{
				Department d = new Department();
				 d.setId(rs.getInt("ID"));
				 d.setManagerID(rs.getInt("MANAGER_ID"));
				 d.setName(rs.getString("NAME"));
				 depts.add(d);
			}
			return depts;
		}
		catch(SQLException se)
		{
			return null;
		}
	}
	/*
	 * Parameters: Employee id and in timestamp
	 * Returns: timestamps object where eid and timestamp = specified
	 * FOR: updateTimestamp
	 */
	public static Timestamps getTimeStamp(int id){
		Timestamps t = new Timestamps();
		try {

			try {
			    Class.forName("com.mysql.jdbc.Driver");
			} 
			catch (ClassNotFoundException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}
			con = DriverManager.getConnection(url,user,password);

			stmt = con.createStatement();

			String sql = "SELECT * FROM timestamp WHERE ID = '"+ id +"'";

			rs = stmt.executeQuery(sql);

			while(rs.next()){
				t.setID(rs.getInt("ID"));
				t.setEmployeeID(rs.getInt("EMPLOYEE_ID"));
				t.setIn(rs.getTimestamp("IN_TIMESTAMP"));
				t.setOut(rs.getTimestamp("OUT_TIMESTAMP"));
				t.setType(rs.getString("HOUR_TYPE"));
			}
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

			try {
			    Class.forName("com.mysql.jdbc.Driver");
			} 
			catch (ClassNotFoundException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}
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

			try {
			    Class.forName("com.mysql.jdbc.Driver");
			} 
			catch (ClassNotFoundException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}
			con = DriverManager.getConnection(url,user,password);
			stmt = con.createStatement();
			String sql = "SELECT ID FROM timestamp WHERE OUT_TIMESTAMP <= '1970-01-01 00:00:01' AND EMPLOYEE_ID = '" +eid+ "' AND IN_TIMESTAMP < '"+ t +
					"' ORDER BY ID DESC LIMIT 1;";
			rs = stmt.executeQuery(sql);

			if (rs.next()){
				int id = rs.getInt("ID");
				sql = "UPDATE timestamp SET `OUT_TIMESTAMP` = ? WHERE `ID` = ?";
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
	public ArrayList<Timestamp> getTimediffs(String type, int eId, Timestamp t){
		ArrayList<Timestamp> retArray = new ArrayList<Timestamp>();
		try {

			try {
			    Class.forName("com.mysql.jdbc.Driver");
			} 
			catch (ClassNotFoundException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}
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
			if(t != null){
				sql += "AND( IN_TIMESTAMP >= '" + t + "' AND OUT_TIMESTAMP >= '" + t +"')";
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
	public static String updateTimestamp(int tId,Timestamp in,Timestamp out, String type){
		try
		{

			try {
			    Class.forName("com.mysql.jdbc.Driver");
			} 
			catch (ClassNotFoundException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}
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

			try {
			    Class.forName("com.mysql.jdbc.Driver");
			} 
			catch (ClassNotFoundException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}
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
	public static ArrayList<Employee> getEmployees(int deptId){
		ArrayList<Employee> e = new ArrayList<Employee>();
		try
		{

			try {
			    Class.forName("com.mysql.jdbc.Driver");
			} 
			catch (ClassNotFoundException el) {
			    // TODO Auto-generated catch block
			    el.printStackTrace();
			}
			con = DriverManager.getConnection(url,user,password);
			stmt = con.createStatement();
			String sql  = "SELECT * FROM employee";
			if(deptId != -1){
				sql += " WHERE DEPT_ID = '" +deptId + "' ";
			}
			sql += " ORDER BY LNAME ASC";
			rs = stmt.executeQuery(sql);
			
			while(rs.next())
			{
				Employee at = new Employee();
				at.setId(rs.getInt("ID"));
				at.setFirstName(rs.getString("FNAME") + " ");
				at.setLastName(rs.getString("LNAME"));
				at.setDeptID(rs.getInt("DEPT_ID"));
				at.setPay(rs.getDouble("HOURLY_PAY"));
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
	public ArrayList<Timestamps> getTimestamps(Timestamp t, int eId){
		ArrayList<Timestamps> d = new ArrayList<Timestamps>();
		try
		{

			try {
			    Class.forName("com.mysql.jdbc.Driver");
			} 
			catch (ClassNotFoundException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}
			con = DriverManager.getConnection(url,user,password);
			stmt = con.createStatement();
			String sql  = "SELECT * FROM timestamp WHERE EMPLOYEE_ID ='" +eId+"' AND( IN_TIMESTAMP >= '" + t +"') AND OUT_TIMESTAMP >= '1970-01-01 00:00:01'";

			rs = stmt.executeQuery(sql);

			while(rs.next())
			{
				Timestamps at = new Timestamps();
				at.setID(rs.getInt("ID"));
				at.setIn(rs.getTimestamp("IN_TIMESTAMP"));
				at.setOut(rs.getTimestamp("OUT_TIMESTAMP"));
				at.setEmployeeID(rs.getInt("EMPLOYEE_ID"));
				at.setType(rs.getString("HOUR_TYPE"));

				d.add(at);
			}
		}
		catch(SQLException se)
		{
			 return null;
		}
		return d;
	}
	@SuppressWarnings("deprecation")
	public static ArrayList<Timestamps> getTimestamps(int eId){
		ArrayList<Timestamps> d = new ArrayList<Timestamps>();
		try
		{
			try {
			    Class.forName("com.mysql.jdbc.Driver");
			} 
			catch (ClassNotFoundException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}
			con = DriverManager.getConnection(url,user,password);
			stmt = con.createStatement();
			String sql  = "SELECT * FROM timestamp WHERE EMPLOYEE_ID ='" +eId+"'";

			rs = stmt.executeQuery(sql);

			while(rs.next())
			{
				Timestamps at = new Timestamps();
				at.setID(rs.getInt("ID"));
				at.setIn(rs.getTimestamp("IN_TIMESTAMP"));
				Timestamp t = new Timestamp(70, 0, 1, 0, 0, 0, 0);
				if(!rs.getTimestamp("OUT_TIMESTAMP").equals(new Timestamp(70, 0, 1, 0, 0, 0, 0))){
					at.setOut(rs.getTimestamp("OUT_TIMESTAMP"));
					
				}
				at.setEmployeeID(rs.getInt("EMPLOYEE_ID"));
				at.setType(rs.getString("HOUR_TYPE"));

				d.add(at);
			}
		}
		catch(SQLException se)
		{
			 return d;
		}
		return d;
	}
	/*
	 * Parameters: X
	 * Returns: list of all departments
	 */
	public static ArrayList<Department> getDepartments(){
		ArrayList<Department> d = new ArrayList<Department>();
		try
		{

			try {
			    Class.forName("com.mysql.jdbc.Driver");
			} 
			catch (ClassNotFoundException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}
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

			try {
			    Class.forName("com.mysql.jdbc.Driver");
			} 
			catch (ClassNotFoundException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}
			con = DriverManager.getConnection(url,user,password);
			stmt = con.createStatement();

			String sql = "INSERT INTO employee (`FNAME`,`LNAME`,`HOURLY_PAY`,`VACATION_HOURS`,`DEPT_ID`) VALUES (?, ?, ?, ?, ?);";

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
	public String addDepartment(String name, String location, String classy, int manager_id){
		try
		{

			try {
			    Class.forName("com.mysql.jdbc.Driver");
			} 
			catch (ClassNotFoundException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}
			con = DriverManager.getConnection(url,user,password);
			stmt = con.createStatement();

			String sql = "INSERT INTO department (`NAME`,`LOCATION`,`CLASS`,`MANAGER_ID`) VALUES (?, ?, ?, ?);";

			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, name);
			pstmt.setString(2, location);
			pstmt.setString(3, classy);
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

			try {
			    Class.forName("com.mysql.jdbc.Driver");
			} 
			catch (ClassNotFoundException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}
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
	
	public ArrayList<Integer> getDeptId() throws SQLException, ClassNotFoundException
	{
		int id = 0;
		ArrayList<Integer> deptId = new ArrayList<Integer>();
		try {
		    Class.forName("com.mysql.jdbc.Driver");
		    con = DriverManager.getConnection(url,user,password);
			stmt = con.createStatement();
			String sql  = "SELECT DISTINCT DEPT_ID FROM department, employee E WHERE D.ID = E.DEPT_ID";

			rs = stmt.executeQuery(sql);

			while(rs.next())
			{
//				Department at = new Department();
				id = rs.getInt("ID");
//				at.setManagerID(rs.getInt("MANAGER_ID"));
//				at.setName(rs.getString("NAME"));

				deptId.add(id);
			}
//			return deptId;
		} 
		catch(SQLException se)
		{
			 return null;
		}
		return deptId;

	}
	
	public String getDeptType(int dept) throws SQLException, ClassNotFoundException
	{
		String id = " ";
	//	ArrayList<Integer> deptId = new ArrayList<Integer>();
		try {
		    Class.forName("com.mysql.jdbc.Driver");
		    con = DriverManager.getConnection(url,user,password);
			stmt = con.createStatement();
			String sql  = "SELECT DISTINCT CLASS FROM department, employee E WHERE D.ID = " + dept;

			rs = stmt.executeQuery(sql);

			while(rs.next())
			{
//				Department at = new Department();
				id = rs.getString("CLASS");
//				at.setManagerID(rs.getInt("MANAGER_ID"));
//				at.setName(rs.getString("NAME"));

//				deptId.add(id);
			}
//			return deptId;
		} 
		catch(SQLException se)
		{
			 return null;
		}
		return id;

	}
	
	public void getReportEmp(){
//		ArrayList<Employee> e = new ArrayList<Employee>();
		try
		{

			try {
			    Class.forName("com.mysql.jdbc.Driver");
			} 
			catch (ClassNotFoundException el) {
			    // TODO Auto-generated catch block
			    el.printStackTrace();
			}
			con = DriverManager.getConnection(url,user,password);
			stmt = con.createStatement();
			String sql  = "SELECT E.ID FNAME, LNAME, DEPT_ID, CLASS, HOUR_TYPE FROM employee E, timestamp T, department D WHERE E.ID = T.ID AND D.ID = E.DEPT_ID ORDER BY LNAME ASC";
			
			rs = stmt.executeQuery(sql);

			while(rs.next())
			{
				Employee at = new Employee();
				int empId = rs.getInt("E.ID");
				String firstName = rs.getString("FNAME");
				String lastName = rs.getString("LNAME");
				int dept = rs.getInt("DEPT_ID");
				String hourType = rs.getString("HOURLY_TYPE");
				String prodType = rs.getString("CLASS");
				timeCalculations t = new timeCalculations();
				Timestamp time = t.totalHours(hourType, empId);
				String hourString = time.toString();
				double hour = Double.parseDouble(hourString);
				at = getEmployee(empId);
				double pay = at.pay();
				double totalPay = hour*pay;
				System.out.println(empId + "|" + firstName + "|" + lastName + "|" + dept + "|" + hourType + "|" + hourString + "|" + totalPay + "|" + prodType);
			}
		}
		catch(SQLException se)
		{
			 System.out.println(se);
		}
	}
	
	
	public static void main(String[] args)
	{
		Department d = getDepartment(101);
		System.out.println(d.toString());
	}

}
